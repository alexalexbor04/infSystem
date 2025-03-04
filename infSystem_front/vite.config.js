import { defineConfig } from 'vite';

export default defineConfig({
    server: {
        port: 3000,
        proxy: {
            '/auth/register': {
                target: 'http://localhost:3000',
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/auth\/register$/, '/src/register.html'),
            },
            '/admin/users': {
                target: 'http://localhost:3000',
                changeOrigin: true,
                rewrite: (path) =>  path.replace(/^\/admin\/users$/, '/src/users.html'),
            },
            '/about': {
                target: 'http://localhost:3000',
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/about$/, '/src/about.html'),
            },
            '/addresses': {
                target: 'http://localhost:3000',
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/addresses$/, '/src/addresses.html'),
            },
            '/data': {
                target: 'http://localhost:3000',
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/data$/, '/src/data.html'),
            },
            '/api': {
                target: 'http://localhost:3000',
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/api$/, ''),
            },
            '/chart': {
                target: 'http://localhost:3000',
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/chart$/, '/src/chart.html'),
            },
        },
    },
});