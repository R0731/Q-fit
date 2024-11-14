import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '@/views/Trainer/TrainerLoginView.vue'
import HomeView from '@/views/HomeView.vue'
import TrainerRegist from '@/views/Trainer/TrainerRegist.vue';
import TrainerLoginView from '@/views/Trainer/TrainerLoginView.vue';
import TraineeLoginView from '@/views/Trainee/TraineeLoginView.vue';
import TrainerHomeView from '@/views/Trainer/TrainerHomeView.vue';
import TraineeHomeView from '@/views/Trainee/TraineeHomeView.vue';
import TraineeList from '@/components/Trainer/TraineeList.vue';
import FeedbackList from '@/components/Trainer/FeedbackList.vue';
import Quest from '@/components/Trainer/Quest.vue';
import FeedbackCreate from '@/components/Trainer/FeedbackCreate.vue';
import TrainerInfo from '@/components/Trainer/TrainerInfo.vue';
import UserUpdate from '@/components/UserUpdate.vue';
import TrainerGym from '@/components/Trainer/TrainerGym.vue';

const isAuth = false;

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    //
    {
      path: '/',
      name: 'HomeView',
      component: HomeView

    },
    // trainer 회원가입 관련
    {
      path: '/trainer/regist',
      name: 'trainerRegist',
      component: TrainerRegist,
    },

    // trainer 관련 주소
    {
      path: '/trainer/login',
      name: 'trainerLogin',
      component: TrainerLoginView,
    },
    {
      path: '/trainer',
      name: 'trainerHome',
      component: TrainerHomeView,
      children: [
        {
          path: '',
          name: 'traineeList',
          component: TraineeList,
        },
        {
          path: 'feedbackList',
          name: 'feedbackList',
          component: FeedbackList,
        },
        {
          path: 'quest',
          name: 'quest',
          component: Quest,
        },
        {
          path: 'createFeedback',
          name: 'createFeedback',
          component: FeedbackCreate,
        },
        {
          path: 'info',
          name: 'trainerInfo',
          component: TrainerInfo,
        },
        {
          path: 'update',
          name: 'trainerUpdate',
          component: UserUpdate,
        },
        {
          path: 'gym',
          name: 'trainerGym',
          component: TrainerGym,
        },
      ]
    },

    // trainee 회원가입 관련

    // trainee 관련 주소
    {
      path: '/trainee/login',
      name: 'traineeLogin',
      component: TraineeLoginView
    },
    {
      path: '/trainee',
      name: 'traineeHome',
      component: TraineeHomeView,
      children: [
      ]
    },
  ]
})

// router.beforeEach((to, from)=>{
//   if(!isAuth && to.name !== 'login'){
//     console.log('로그인이 필요합니다.')
//     return {name : 'login'}
//   }
// })

export default router
