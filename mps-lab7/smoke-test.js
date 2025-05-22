import http from "k6/http";
import { check } from "k6";

/**
 * @author Pablo Gámez Guerrero
 * @author Álvaro Gallardo Rubio
 */

/**
 * @type {import('k6/options').Options}
 */
export const options  = {
    vus: 5,
    duration: '1m',

    thresholds: {
        http_req_failed: ["rate==0"], // No debe haber fallos
        http_req_duration: ['avg<100'], // Promedio < 100ms
    },
};

export default function () {

    const url = "http://localhost:8080/medico/1";

    const res = http.get(url);

    console.log(res.body);

    check(res, {
        "response code was 200": (res) => res.status == 200,
    });

}