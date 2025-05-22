import http from 'k6/http';

/**
 * @type {import('k6/options').Options}
 */
export const options = {
    scenarios: {
        spike: {
            executor: 'ramping-arrival-rate',
            preAllocatedVUs: 1000,
            maxVUs: 1e7,
            stages: [
                { duration: '2m', target: 4855 },
                { duration: '2m', target: 0 },
            ],
        }
    },
    thresholds: {
        http_req_failed: [
            { threshold: 'rate<=0.005', abortOnFail: true },
        ],
    },
};

export default function () {
    http.get('http://localhost:8080/medico/1');
}