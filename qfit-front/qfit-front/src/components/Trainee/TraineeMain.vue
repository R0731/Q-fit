<template>
  <div>
    <TheCalender/>
    <h5>{{ userName }} 회원님 안녕하세요.</h5> 
    <h5>오늘도 퀘스트 완료까지 화이팅!</h5>
    <TraineeQuest/>
    <TraineeReview/>
    <TraineeFeedback/>
  </div>
</template>

<script setup>
import TheCalender from "../common/TheCalender.vue";
import TraineeFeedback from "./TraineeFeedback.vue";
import TraineeQuest from "./TraineeQuest.vue";
import TraineeReview from "./TraineeReview.vue";
import { useUserStore } from '@/stores/user';
import { ref, computed } from 'vue';

const userStore = useUserStore();

const userName = computed(() => userStore.loginUser.name);

</script>

<style scoped>

</style>