import http from 'k6/http';
import { sleep } from 'k6';
import { expect } from "https://jslib.k6.io/k6-testing/0.5.0/index.js";

export const options = {
  scenarios : {
    server8080: {
        executor : 'constant-vus',
        vus: 200,
        duration: '30s',
        exec: 'test8080',
    },
    server8081: {
        executor: 'constant-vus',
        vus: 200,
        duration: '30s',
        exec: 'test8081',
    },
  },
  thresholds: {
      // Separate thresholds for 8080 (will show isolated stats in summary)
      'http_req_duration{scenario:server8080}': ['p(95)<500'],  // Example: 95th percentile under 500ms
      'http_req_failed{scenario:server8080}': ['rate<0.01'],    // Example: Failure rate under 1%
      'http_reqs{scenario:server8080}': ['count>0'],            // Example: At least some requests (always true, just to trigger stats)

      // Separate thresholds for 8081 (will show isolated stats in summary)
      'http_req_duration{scenario:server8081}': ['p(95)<500'],
      'http_req_failed{scenario:server8081}': ['rate<0.01'],
      'http_reqs{scenario:server8081}': ['count>0'],
    },
};

export function test8080() {
  let res = http.get('http://localhost:8080');
  expect.soft(res.status).toBe(200);
  sleep(0.1);
}

export function test8081() {
  let res = http.get('http://localhost:8081');
  expect.soft(res.status).toBe(200);
  sleep(0.1);
}
