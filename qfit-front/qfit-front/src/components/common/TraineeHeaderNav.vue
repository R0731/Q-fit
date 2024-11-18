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

const router = useRouter();

/* 뒤로 가기 함수 정의 */
const goBack = () => {
  router.back();
};
</script>

<style scoped>

</style>
