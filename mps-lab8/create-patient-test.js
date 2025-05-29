
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

    // Create patient
    await page.goto('http://localhost:4200/paciente/create');

    await page.locator('input[name="dni"]').type('1234');
    await page.locator('input[name="nombre"]').type('Paco');
    await page.locator('input[name="edad"]').type('edad');
    await page.locator('input[name="cita"]').type('cita');

    const createButton = page.locator('input[type="submit"]');

    await Promise.all([page.waitForNavigation(), submitButton.click()]);

    let len = await page.$$("table tbody tr").length;

    await check(page.locator('table'), {
        header: async (lo) => (await parseInt(lo.$$("table tbody tr")[len-1].$('td[name="ccc"]').textContent())) == 12345
    });
  } finally {
    await page.close();
  }
}