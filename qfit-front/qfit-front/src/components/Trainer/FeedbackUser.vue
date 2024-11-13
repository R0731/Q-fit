<template>
  <div>
    <h3>피드백</h3>
    <p>아직 피드백이 없습니다</p>
    <button @click="goCreateFeedback">작성하기</button>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const goCreateFeedback = () => {
  router.push({name: 'createFeedback'});
}
</script>

<style scoped>

</style>