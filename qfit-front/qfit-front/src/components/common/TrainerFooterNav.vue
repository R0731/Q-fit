<template>
  <div class="footer-container">
      <!-- 임시 아이콘 및 텍스트 (로그인) -->
      <div 
        class="footer-item"
        :class="{ 'selected': route.name === 'trainerLogin' }" 
        @click="navigateTo('trainerLogin')"
      >
      <i class="bi bi-people"></i>
      <span>임시로그인용</span>
    </div>
    
    <!-- 첫 번째 아이콘 및 텍스트 (회원 관리) -->
    <div 
      class="footer-item"
      :class="{ 'selected': route.name === 'trainerLogin' }" 
      @click="navigateTo('trainerLogin')"
    >
      <i class="bi bi-people"></i>
      <span>회원 관리</span>
    </div>

    <!-- 두 번째 아이콘 및 텍스트 (홈) -->
    <div 
      class="footer-item"
      :class="{ 'selected': route.name === 'traineeList' }" 
      @click="navigateTo('traineeList')"
    >
      <i class="bi bi-house-door"></i>
      <span>홈</span>
    </div>

    <!-- 세 번째 아이콘 및 텍스트 (마이 페이지) -->
    <div 
      class="footer-item"
      :class="{ 'selected': route.name === 'trainerInfo' }" 
      @click="navigateTo('trainerInfo')"
    >
      <i class="bi bi-person-circle"></i>
      <span>마이 페이지</span>
    </div>
  </div>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router';

const router = useRouter();
const route = useRoute();

/* 라우터를 이용해 페이지 이동 (name 기반) */
// route.name(현재경로)와 routeName(이동할경로) 비교 후 다를 경우만 이동
const navigateTo = (routeName) => { 
  if (route.name !== routeName) {
    // console.log('이동경로', routeName)
    router.push({ name: routeName });
  }
};
</script>

<style scoped>
/* 선택된 항목 스타일 */
.selected {
  color: var(--theme-color);
}

.selected i {
  color: var(--theme-color);
}
</style>
