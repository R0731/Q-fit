<template>
  <div class="home">
    <!-- 헤더 영역 -->
    <TheHeaderNav class="header" />
    
    <!-- 메인 콘텐츠 영역 -->
    <div class="content">
      <RouterLink :to="{ name: 'traineeMain' }"></RouterLink>
      <RouterView />
    </div>

    <!-- 푸터 영역 -->
    <TheFooterNav class="footer" />
  </div>
</template>

<script setup>
import TheHeaderNav from "@/components/common/TraineeHeaderNav.vue";
import TheFooterNav from "@/components/common/TraineeFooterNav.vue";

</script>

<style scoped>
.home {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  width: 100%;
  overflow-x: hidden;
}
</style>