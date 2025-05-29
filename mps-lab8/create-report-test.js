
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

    await page.locator('input[name="nombre"]').type('nombre');
    await page.locator('input[name="DNI"]').type('123');

    const submitButton = page.locator('button[name="login"]');

    await Promise.all([page.waitForNavigation(), submitButton.click()]);

    // Acceder imagen del paciente
    let pacienteRow = page.locator("table tbody tr:first-child");

    await Promise.all([page.waitForNavigation(), pacienteRow.click()]);

    const viewButton = page.locator('table tbody tr:first-child button[name="view"]');

    await Promise.all([page.waitForNavigation(), viewButton.click()]);

    await Promise.all([page.waitForNavigation(), submitButton.click()]);

    // Realizar predicción imagen del paciente
    const addButton = page.locator('button[name="add"]');

    await Promise.all([page.waitForNavigation(), addButton.click()]);

    await Promise.all([page.waitForNavigation(), submitButton.click()]);

    /*
    await check(page.locator('table'), {
        nombre: async (lo) => (await parseInt(lo.$$("table tbody tr")[len-1].$('td[name="nombre"]').textContent())) == nombre-paciente,
        dni: async (lo) => (await parseInt(lo.$$("table tbody tr")[len-1].$('td[name="dni"]').textContent())) == dni-paciente
    });
    */
  } finally {
    await page.close();
  }
}