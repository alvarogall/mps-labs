import http from 'k6/http';

/**
 * @type {import('k6/options').Options}
 */
export const options = {
    scenarios: {
        breakpoint: {
            executor: 'ramping-arrival-rate',
            preAllocatedVUs: 1000,
            maxVUs: 1000000,
            stages: [
                { duration: '10m', target: 100000 },
            ],
        }
    },
    thresholds: {
        http_req_failed: [{
            threshold: 'rate<=0.01', // 1% de fallos
            abortOnFail: true,
        }],
    }
};

export default function () {
    http.get('http://localhost:8080/medico/1');
}