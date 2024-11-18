<template>
  <div class="header-container">
    <!-- 왼쪽 화살표 아이콘 -->
    <i class="header-bi bi-chevron-left" @click="goBack"></i>

    <!-- 가운데 로고 -->
    <img src="@/assets/logo.png" alt="logo" class="header-logo" />

    <!-- 오른쪽 벨 아이콘 -->
    <i class="header-bi bi-bell"></i>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

/* Vue Router 훅을 사용하여 라우터 객체를 가져옴 */
const router = useRouter();

/* 뒤로 가기 함수 정의 */
const goBack = () => {
  // 만약 이전 페이지가 있으면 그 페이지로 이동.
  // 브라우저 히스토리에 이전 기록이 없으면 기본적으로 작동 안 함.
  router.back();
};
</script>

<style scoped>

</style>
