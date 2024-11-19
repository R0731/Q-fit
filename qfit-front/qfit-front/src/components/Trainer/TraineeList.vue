<template>
  <div>
  <TheCalender/>
  <div class="card">
   <h3>트레이너가 관리하는 회원 조회</h3>
   <button @click="goFeedbackList">피드백 남기기</button>
    <ul>
      <li v-for="(trainee,) in trainees" :key="trainee.userId" @click="selectTrainee(trainee)">
        <span>{{ trainee.userName }} {{ trainee.age }}세</span>
        <span>{{ trainee.status }}</span>
      </li>
    </ul>
  </div>
  </div>
</template>

<script setup>
import { useTrainerStore } from "@/stores/trainer";
import TheCalender from "../common/TheCalender.vue";
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from "@/stores/user";

const router = useRouter();

const userStore = useUserStore();
const trainerId = userStore.loginUser.numberId;

const trainerStore = useTrainerStore();
const trainees = ref([]);

onMounted(()=>{
  trainerStore.fetchTraineeList(trainerId)
    .then(()=>{
      trainees.value = trainerStore.trainees;
    })
    .catch(err => {
      console.error(err)
    })
});

const goFeedbackList = () => {
  router.push({ name: 'feedbackList' });
};

const selectTrainee = (trainee) => {
  // 선택한 훈련생 데이터를 상태로 유지 후 화면 전환
  trainerStore.selectedTrainee = trainee; // Store에 선택된 훈련생 저장
  router.push({ name: 'quest' }); // 라우터 이동
};
</script>

<style scoped>
.logo {
  width: 80px;
  height: 80px;
}
.card{
  cursor:pointer;
}
</style>