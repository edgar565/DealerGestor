/** @type {import('tailwindcss').Config} */
export default {
    content: [
        "./index.html",
        "./src/**/*.{js,ts,jsx,tsx}",
    ],
    theme: {
        extend: {
            colors: {
                primary: 'var(--btn-color)',
                success: 'var(--btn-success)',
                danger: 'var(--btn-danger)',
            }},
    },
    plugins: [],
}