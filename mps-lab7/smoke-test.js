import http from "k6/http";
import { check } from "k6";

/**
 * @type {import('k6/options').Options}
 */
export const options = {
    vus: 5,
    duration: '1m',
    thresholds: {
        http_req_failed: [
            { threshold: 'rate==0', abortOnFail: true }
        ],
        http_req_duration: [
            { threshold: 'avg<500', abortOnFail: true }
        ],
    },
};

export default function () {
    const url = "http://localhost:8080/medico/1";
    const res = http.get(url);

    check(res, {
        "response code was 200": (r) => r.status === 200,
    });
}