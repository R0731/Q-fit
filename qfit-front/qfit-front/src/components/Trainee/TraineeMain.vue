<template>
  <!-- <div class="page-container"> -->
  <div>
    <!-- 캘린더 컴포넌트 -->
    <TheCalender />

    <!-- 환영 메시지 -->
    
    <div class="welcome-container">
      <h5 class="welcome-text">{{ userName }} 회원님 안녕하세요.</h5>
      
      <!-- 달성률에 따른 메세지 변경 -->
      <h5 class="welcome-text" v-if="rate !== '100%'">오늘도 퀘스트 완료까지 화이팅!</h5>
      <h3 class="welcome-text" v-if="rate === '100%'">🎉오늘의 퀘스트 완료🎉</h3>
      <br>
    </div>

    <!-- 달성률 표시 -->
     <div class="completion">
     <h6 v-if="rate">달성률 : {{ rate }}</h6>

  </div>
    
    <!-- 퀘스트 섹션 -->
    <div class="quest-container">
      <TraineeQuest />
      <TraineeReview v-if="hasQuest" />
      <TraineeFeedback v-if="hasQuest" />
    </div>
  </div>
</template>

<script setup>
import TheCalender from "../common/TheCalender.vue";
import TraineeFeedback from "./TraineeFeedback.vue";
import TraineeQuest from "./TraineeQuest.vue";
import TraineeReview from "./TraineeReview.vue";
import { useUserStore } from '@/stores/user';
import { useViewStore } from "@/stores/viewStore";
import { computed, onMounted, ref, watch } from 'vue';
import { useQuestStore } from '@/stores/quest';

const userStore = useUserStore();
const questStore = useQuestStore();
const viewStore = useViewStore();
const userName = computed(() => userStore.loginUser.name);

const rate = ref(null);

// 퀘스트 달성률 상태 반영 메서드
const checkQuest = async () =>{
  try{
    const traineeId = userStore.loginUser.numberId;
    const startDate = viewStore.selectedDate;
    const endDate = viewStore.selectedDate;
    await questStore.getTraineeQuestCompletionRate(traineeId, startDate, endDate);
    rate.value = questStore.questCompletionRates[0].questCompletionRate;
    console.log('여기rate', rate.value);
  }catch(err){
    console.error(err)
  }
}

// quest 상태 확인(quest가 null이 아니면 true)
const hasQuest = computed(() => !!questStore.quest);

onMounted(()=>{
  setTimeout(() => {
    checkQuest();
  }, 200); // 200ms 딜레이
});


watch(
  () => viewStore.selectedDate, 
  async(newDate, oldDate) => {
    console.log('셀렉데이체인지', oldDate, newDate);
    await checkQuest();
  }
);

watch(
  () => questStore.questCompletionRates,
  async(newValue, oldValue) => {
    if(newValue.length > 0){
      console.log('퀘스트체인지', oldValue, newValue);
      rate.value = questStore.questCompletionRates[0]?.questCompletionRate || null;
    }else{
      rate.value = null;
    }
  },
  { deep : true }
)

</script>

<style scoped>
/* 전체 페이지 컨테이너 */
.page-container {
  max-width: 100%;
  margin: 0 auto;
  padding: 0px;
  background-color: #fff;
  border-radius: 10px;
  /* box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); */
}
/* 환영 메시지 컨테이너 */
.welcome-container {
  display: flex;
  justify-content: center; /* 가로 중앙 */
  align-items: center; /* 세로 중앙 */
  flex-direction: column; /* 세로 방향 정렬 */
  width: 100%; /* 부모 컨테이너 크기 기반으로 정렬 */
  margin: 0; /* 상하 좌우 여백 제거 */
  padding: 10px;
  /* background: #eaf5ff; */
  border-radius: 8px;
  /* box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1) */
}

/* 환영 메시지 텍스트 */
.welcome-text {
  font-size: 1.2rem;
  color: #333;
  margin: 5px 0;
  font-weight: bold;
}

/* 퀘스트 컨테이너 */
.quest-container {
  /* margin-top: 20px; */
  padding: 20px;
  background: #ffffff;
  border-radius: 10px;
  /* box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); */
}

/* 공통 컴포넌트 섹션 스타일 */
.quest-container > *:not(:last-child) {
  margin-bottom: 20px;
}

/* 반응형 스타일 */
@media screen and (max-width: 768px) {
  .page-container {
    padding: 10px; /* 작은 화면에서는 패딩 축소 */
  }

  .welcome-text {
    font-size: 1rem; /* 텍스트 크기 조정 */
  }

  .quest-container {
    padding: 15px; /* 퀘스트 컨테이너 패딩 축소 */
  }
}

.completion {
  display: flex;
  justify-content: center; /* 가로 중앙 */
  align-items: center; /* 세로 중앙 */
  flex-direction: column; /* 세로 방향 정렬 */
  width: 100%; /* 부모 컨테이너 크기 기반으로 정렬 */
  justify-content: center;

}
</style>
