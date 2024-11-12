import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '@/views/LoginView.vue'
import HomeView from '@/views/HomeView.vue'

const isAuth = false;

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: LoginView
    },
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
  ],
})

// router.beforeEach((to, from)=>{
//   if(!isAuth && to.name !== 'login'){
//     console.log('로그인이 필요합니다.')
//     return {name : 'login'}
//   }
// })

export default router
