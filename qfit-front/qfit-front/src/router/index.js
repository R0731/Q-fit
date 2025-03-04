import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user';
import HomeView from '@/views/HomeView.vue'
import Regist from '@/views/Regist.vue';
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
import TraineeQuest from '@/components/Trainee/TraineeQuest.vue';
import TraineeReview from '@/components/Trainee/TraineeReview.vue';
import TraineeMain from '@/components/Trainee/TraineeMain.vue';
import Record from '@/components/Trainee/Record.vue';
import QuestAssign from '@/components/Trainer/QuestAssign.vue';
import MyTrainees from '@/components/Trainer/MyTrainees.vue';
import MyTraineesUpdate from '@/components/Trainer/MyTraineesUpdate.vue';
import MyTraineesDelete from '@/components/Trainer/MyTraineesDelete.vue';
import QuestSetting from '@/components/Trainer/QuestSetting.vue';
import TraineeBigCalender from '@/components/Trainee/TraineeBigCalender.vue';
import { useNotificationStore } from '@/stores/notification';
import MyTraineesBigCalender from '@/components/Trainer/MyTraineesBigCalender.vue';

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
      component: Regist,
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
      meta: { requiresAuth: 'trainer' }, // meta로 인증검사
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
          path: 'quest-setting',
          name: 'questSetting',
          component: QuestSetting,
        },
        {
          path: 'my-trainees',
          name: 'MyTrainees',
          component: MyTrainees,
        },
        {
          path: 'update-trainee',
          name: 'MyTraineesUpdate',
          component: MyTraineesUpdate,
        },
        {
          path: 'delete-trainee',
          name: 'MyTraineesDelete',
          component: MyTraineesDelete,
        },
        {
          path: 'bigcalender-trainee',
          name: 'MyTraineesBigCalender',
          component: MyTraineesBigCalender,
        },
      ]
    },

    // trainee 회원가입 관련
    {
      path: '/trainee/regist',
      name: 'traineeRegist',
      component: Regist,
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
        {
          path: 'calender',
          name: 'traineeCalender',
          component: TraineeBigCalender,
        },
      ]
    },
  ]
})

router.beforeEach((to, from, next) => {
  const userStore = useUserStore();
  
  if(!userStore.loginUser){
    if (to.path === '/trainer/regist' || to.path === '/trainee/regist') {
      return next(); // Allow navigation to these pages
    }

    if(to.name === 'traineeLogin' || to.name === 'trainerLogin'){
      return next();
    }
    if(to.path.includes('/trainee')){
      console.log('트레이니 로그인 페이지로 이동')
      return next({name: 'traineeLogin'})
      
    }
    if(to.path.includes('/trainer')){
      console.log('트레이너 로그인 페이지로 이동')
      return next({name: 'trainerLogin'})
    }
  }
  
  const userType = userStore.loginUser.userType;

  if(userType !== 2 && to.path.includes('/trainee')){
    console.log('트레이너가 트레이니 페이지에 접근')
    alert('잘못된 접근입니다')
    return next({name: 'traineeList'});
  }
  if(userType !== 1 && to.path.includes('/trainer')){
    console.log('트레이니가 트레이너 페이지에 접근')
    alert('잘못된 접근입니다')
    return next({name: 'traineeMain'})
  }
  next();
})

// 알림 상태 업데이트
router.afterEach(() => {
  const notificationStore = useNotificationStore();
  const userStore = useUserStore();
  const userId = userStore.loginUser?.numberId;

  if(userId){
    notificationStore.fetchUnreadNotifications(userId);
  }
})
export default router
