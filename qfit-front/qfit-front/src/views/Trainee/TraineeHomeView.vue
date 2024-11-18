<template>
  <div class="home">
    <!-- 헤더 영역 -->
    <TheHeaderNav class="header" />
    
    <!-- 메인 콘텐츠 영역 -->
    <div class="content">
      <RouterLink :to="{ name: 'traineeList' }"></RouterLink>
      <RouterView />
    </div>

    <!-- 푸터 영역 -->
    <TheFooterNav class="footer" />
  </div>
</template>

<script setup>
import TheHeaderNav from "@/components/common/TraineeHeaderNav.vue";
import TheFooterNav from "@/components/common/TraineeFooterNav.vue";
import { useRouter } from 'vue-router';

const router = useRouter();

</script>

<style scoped>

</style>