<template>
  <div v-if="trainee">
    <p>{{ trainee.userName || '정보 없음' }} 회원님의 {{ viewStore.selectedDate }} 퀘스트</p>
    <Exercise/>
    <Review/>
    <FeedbackUser/>
    
  </div>
</template>

<script setup>
import { useTrainerStore } from '@/stores/trainer';
import { useViewStore } from '@/stores/viewStore';
import Exercise from './Exercise.vue';
import FeedbackUser from './FeedbackUser.vue';
import Review from './TrainerReview.vue';
import { computed } from 'vue';

const trainerStore = useTrainerStore();
const viewStore = useViewStore();

const trainee = computed(() => trainerStore.selectedTrainee);
</script>

<style scoped>

</style>