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

    // Crear paciente
    const addButton = page.locator('button[name="add"]');

    await Promise.all([page.waitForNavigation(), addButton.click()]);

    await page.locator('input[name="dni"]').type('123');
    await page.locator('input[name="nombre"]').type('nombre');

    const createButton = page.locator('button[type="submit"]');

    await Promise.all([page.waitForNavigation(), createButton.click()]);

    // Comprobar paciente
    let len = await page.$$("table tbody tr").length;

    await check(page.locator('table'), {
        nombre: async (lo) => (await parseInt(lo.$$("table tbody tr")[len-1].$('td[name="nombre"]').textContent())) == 'nombre',
        dni: async (lo) => (await parseInt(lo.$$("table tbody tr")[len-1].$('td[name="dni"]').textContent())) == 123
    });
  } finally {
    await page.close();
  }
}