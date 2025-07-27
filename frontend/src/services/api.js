// src/services/api.js
export function getApiUrl(path) {
    if (import.meta.env.DEV) {
        return `http://localhost:8080${path}`; // en desarrollo
    } else {
        return `${path}`; // en producción (ya dentro del backend)
    }
}