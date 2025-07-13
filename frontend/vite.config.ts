import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import { resolve } from 'path'

const backendStaticDir = resolve(__dirname, '../src/main/resources/static')

export default defineConfig({
  plugins: [react()],
  build: {
    outDir: 'dist',
    emptyOutDir: true
  },
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: `@import "@fullcalendar/core/main.css"; @import "@fullcalendar/daygrid/main.css"; @import "@fullcalendar/timegrid/main.css"; @import "@fullcalendar/list/main.css";`
      }
    }
  }
})
