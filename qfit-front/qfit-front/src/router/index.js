import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '@/views/Trainer/TrainerLoginView.vue'
import HomeView from '@/views/HomeView.vue'
import TrainerLoginView from '@/views/Trainer/TrainerLoginView.vue';
import TraineeLoginView from '@/views/Trainee/TraineeLoginView.vue';
import TrainerHomeView from '@/views/Trainer/TrainerHomeView.vue';
import TraineeHomeView from '@/views/Trainee/TraineeHomeView.vue';
import TraineeList from '@/components/Trainer/TraineeList.vue';
import FeedbackList from '@/components/Trainer/FeedbackList.vue';
import Quest from '@/components/Trainer/Quest.vue';
import FeedbackCreate from '@/components/Trainer/FeedbackCreate.vue';

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


    // trainer 관련 주소
    {
      path: '/trainer',
      name: 'trainerHome',
      component: TrainerHomeView,
      children: [
        {
          path: 'login',
          name: 'trainerLogin',
          component: TrainerLoginView,
        },
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
      ]
    },


    // trainee 관련 주소
    {
      path: '/trainee',
      name: 'traineeHome',
      component: TraineeHomeView,
      children: [
        {
          path: 'login',
          name: 'traineeLogin',
          component: TraineeLoginView
        },
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
