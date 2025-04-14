import http from 'k6/http';
import { scenario } from 'k6/execution';
export let options = {
    vus: 6, // Virtual Users
    duration: '15s', // Test duration
};

export default function () {
    const value = __VU; // Generate unique user IDs

    const res = http.post(
        'http://localhost:8080/api/v1/transactions',
        JSON.stringify({ userId: __VU, invoiceId: scenario.iterationInTest}),
        { headers: { 'Content-Type': 'application/json' } }
    );
}