import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router'
import first from '@/components/HelloWorld.vue'
const routes:Array<RouteRecordRaw> = [
    {
        path: '/',
        name: 'home',
        component: first
    }
]
// 创建(用于启动时自动打开页面)
const router = createRouter({
    history: createWebHistory(),
    routes
})
export default router;