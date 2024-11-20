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
import TrainerQuest from '@/components/Trainer/TrainerQuest.vue';
import TrainerInfo from '@/components/Trainer/TrainerInfo.vue';
import TraineeInfo from '@/components/Trainee/TraineeInfo.vue';
import UserUpdate from '@/components/UserUpdate.vue';
import TrainerGym from '@/components/Trainer/TrainerGym.vue';
import TraineeRegist from '@/views/Trainee/TraineeRegist.vue';
import TraineeQuest from '@/components/Trainee/TraineeQuest.vue';
import TraineeReview from '@/components/Trainee/TraineeReview.vue';
import TraineeMain from '@/components/Trainee/TraineeMain.vue';
import Record from '@/components/Trainee/Record.vue';
import QuestAssign from '@/components/Trainer/QuestAssign.vue';
import Calenderetemp from '@/components/common/Calenderetemp.vue';
import TraineeInfoEdit from '@/components/Trainee/TraineeInfoEdit.vue';
import MyTrainees from '@/components/Trainer/MyTrainees.vue';
import MyTraineesUpdate from '@/components/Trainer/MyTraineesUpdate.vue';
import MyTraineesDelete from '@/components/Trainer/MyTraineesDelete.vue';

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
          component: TrainerQuest,
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
        {
          path: 'quest-assign',
          name: 'questAssign',
          component: QuestAssign,
        },
        {
          path: 'calender',
          name: 'Calender',
          component: Calenderetemp,
        },
        {
          path: 'my-trainees',
          name: 'MyTrainees',
          component: MyTrainees,
        },
        {
          path: 'trainee-update',
          name: 'MyTraineesUpdate',
          component: MyTraineesUpdate,
        },
        {
          path: 'trainee-delete',
          name: 'MyTraineesDelete',
          component: MyTraineesDelete,
        },
      ]
    },

    // trainee 회원가입 관련
    {
      path: '/trainee/regist',
      name: 'traineeRegist',
      component: TraineeRegist,
    },
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
        {
          path: '',
          name: 'traineeMain',
          component: TraineeMain,
        },
        {
          path: 'info',
          name: 'traineeInfo',
          component: TraineeInfo,
        },
        {
          path: 'edit-info',
          name: 'editInfo',
          component: TraineeInfoEdit,
        },
        {
          path: 'quest',
          name: 'traineeQuest',
          component: TraineeQuest,
        },
        {
          path: 'review',
          name: 'traineeReview',
          component: TraineeReview,
        },
        {
          path: 'record',
          name: 'record',
          component: Record,
        },
        {
          path: 'update',
          name: 'traineeUpdate',
          component: UserUpdate,
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
