<template>
  <div>
  <TheCalender/>
  <div class="card" @click="goQuest">
   <h3>트레이너가 관리하는 회원 조회</h3>
   <button @click="goFeedbackList">피드백 남기기</button>
    <ul>
      <li v-for="(trainee, index) in trainees" :key="index">
        <span>{{ trainee.name }} {{ trainee.age }}세</span>
        <span>{{ trainee.status }}</span>
      </li>
    </ul>
  </div>
  </div>
</template>

<script setup>
import TheCalender from "../common/TheCalender.vue";
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const goFeedbackList = () => {
  router.push({ name: 'feedbackList' });
};

const date = ref('2021-04-21');
const id = ref('1');
const goQuest = (date, id) => {
  router.push({name: 'quest', params:{date, id}});
}

const trainees = ref([
  { name: '김큐핏', age: 28, status: '퀘스트 미등록' },
  { name: '박트레이니', age: 30, status: '퀘스트 수행중' },
  { name: '이퀘스트', age: 25, status: '퀘스트 완료' },
]);
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