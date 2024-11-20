<template>
  <div class="home">
    <!-- 헤더 -->
    <TheHeaderNav class="header" />

    <!-- 메인 콘텐츠 -->
    <div class="content">
      <RouterLink :to="{ name: 'traineeList' }"></RouterLink>
      <RouterView />
    </div>

    <!-- 푸터 -->
    <TheFooterNav class="footer" />
  </div>
</template>

<script setup>
import TheHeaderNav from "@/components/common/TrainerHeaderNav.vue";
import TheFooterNav from "@/components/common/TrainerFooterNav.vue";
</script>

<style scoped>
/* 홈 전체 레이아웃 */
.home {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  width: 100%;
  overflow-x: hidden;
}

</style>
