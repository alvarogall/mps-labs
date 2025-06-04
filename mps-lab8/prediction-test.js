import { browser } from 'k6/browser';
import { check } from 'https://jslib.k6.io/k6-utils/1.5.0/index.js';

export const options = {
  scenarios: {
    ui: {
      executor: 'shared-iterations',
      options: {
        browser: {
          type: 'chromium',
        },
      },
    },
  },
  thresholds: {
    checks: ['rate==1.0'],
  },
};

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

    // Realizar predicción imagen del paciente
    const predictButton = page.locator('button[name="predict"]');
    await Promise.all([page.waitForTimeout(5), predictButton.click()]);

    // Comprobar que la predicción se ha realizado correctamente
    await page.waitForSelector('span[name="predict"]');
    await check(page.locator('span[name="predict"]'), {
      prediction: async (lo) => (await lo.textContent())?.includes('Probabilidad de cáncer: '),
    });
  } finally {
    await page.close();
  }
}