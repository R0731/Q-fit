<template>
  <div class="footer-container">
    <!-- 첫 번째 아이콘 및 텍스트 (회원 관리) -->
    <div 
      class="footer-item"
      :class="{ 'selected': route.name === 'MyTrainees' }" 
      @click="navigateTo('MyTrainees')"
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

// 라우터를 이용해 페이지 이동 (name 기반)
const navigateTo = (routeName) => { 
  if (route.name !== routeName) { // 경로가 다를 경우만 이동
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
