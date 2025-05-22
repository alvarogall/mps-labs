import http from 'k6/http';

/**
 * @author Pablo Gámez Guerrero
 * @author Álvaro Gallardo Rubio
 */

/**
 * @type {import('k6/options').Options}
 */
export const options = {
    stages: [
        { duration: '2m', target: 4855 },
        { duration: '2m', target: 0 },
    ],
    thresholds: {
        http_req_failed: [
            { threshold: 'rate<=0.005', abortOnFail: true },
        ],
    },
};

export default function () {
    http.get('http://localhost:8080/medico/1');
}