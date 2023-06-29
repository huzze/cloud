import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import {resolve} from "path";
// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    host: '0.0.0.0',
    port: 8080,
    open: true
  },
  resolve: {
    /* 路径别名设置 ==========start=======与tsconfig.json配合改*/
    alias: {
      '@': resolve(__dirname, './src')
    }
    /* 路径别名设置 ==========end=======与tsconfig.json配合改*/
  },
})
