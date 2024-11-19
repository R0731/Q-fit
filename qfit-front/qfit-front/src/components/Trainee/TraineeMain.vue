<template>
  <div>
    <TheCalender/>
    <h5>{{ userName }} 회원님 안녕하세요.</h5> 
    <h5>오늘도 퀘스트 완료까지 화이팅!</h5>
    <TraineeQuest/>
    <TraineeReview v-if="hasQuest"/>
    <TraineeFeedback v-if="hasQuest"/>
  </div>
</template>

<script setup>
import TheCalender from "../common/TheCalender.vue";
import TraineeFeedback from "./TraineeFeedback.vue";
import TraineeQuest from "./TraineeQuest.vue";
import TraineeReview from "./TraineeReview.vue";
import { useUserStore } from '@/stores/user';
import { computed } from 'vue';
import { useQuestStore } from '@/stores/quest';

const userStore = useUserStore();
const questStore = useQuestStore();

const userName = computed(() => userStore.loginUser.name);

// quest 상태 확인(quest가 null이 아니면 true)
const hasQuest = computed(() => !!questStore.quest);
</script>

<style scoped>

</style>