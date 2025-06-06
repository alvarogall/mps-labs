import { browser } from 'k6/browser';
import { check } from 'https://jslib.k6.io/k6-utils/1.5.0/index.js';

/**
 * @author Pablo Gámez Guerrero
 * @author Álvaro Gallardo Rubio
 */

export const options = {
  scenarios: {
    ui: {
      executor: 'shared-iterations', // para realizar iteraciones sin indicar el tiempo
      options: {
        browser: {
          type: 'chromium',
        }
      }
    }
  },
  thresholds: {
    checks: ["rate==1.0"]
  }
}

export default async function () {
  const page = await browser.newPage();
  try {
    // Login
    await page.goto('http://localhost:4200/');

    await page.locator('input[name="nombre"]').type('Manuel');
    await page.locator('input[name="DNI"]').type('123');

    const submitButton = page.locator('button[name="login"]');

    await Promise.all([page.waitForNavigation(), submitButton.click()]);

    // Acceder al paciente
    const firstRow1 = page.locator('table tbody tr:first-child');
    await Promise.all([page.waitForNavigation(), firstRow1.click()]);

    // Acceder a la imagen del paciente
    const viewButton = page.locator('table tbody tr:first-child td button[name="view"]');
    await Promise.all([page.waitForNavigation(), viewButton.click()]);

    // Realizar un informe
    const predictButton = page.locator('button[name="add"]');
    await Promise.all([page.waitForNavigation(), predictButton.click()]);

    await page.locator('textarea').type('Este es un informe de prueba para la creación de un reporte.');

    const saveButton = page.locator('button[name="save"]');
    await Promise.all([page.waitForNavigation(), saveButton.click()]);

    // Comprobar que el informe se ha creado correctamente
    await check(page.locator('span[name="content"]'), {
      'Informe creado correctamente': async (lo) => (await lo.textContent()) == 'Este es un informe de prueba para la creación de un reporte.',
    });
  } finally {
    await page.close();
  }
}
