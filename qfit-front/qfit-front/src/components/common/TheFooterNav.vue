<template>
  <div class="footer-container">
    <!-- 첫 번째 아이콘 및 텍스트 -->
    <div 
      class="footer-item"
      :class="{ 'selected': route.path === '/members' }" 
      @click="navigateTo('/login')"
    >
      <i class="bi bi-people"></i>
      <span>회원 관리</span>
    </div>

    <!-- 두 번째 아이콘 및 텍스트 -->
    <div 
      class="footer-item"
      :class="{ 'selected': route.path === '/' }" 
      @click="navigateTo('traineeList')"
    >
      <i class="bi bi-house-door"></i>
      <span>홈</span>
    </div>

    <!-- 세 번째 아이콘 및 텍스트 -->
    <div 
      class="footer-item"
      :class="{ 'selected': route.path === '/profile' }" 
      @click="navigateTo('/quest')"
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

/* 라우터를 이용해 페이지 이동 */
const navigateTo = (path) => {
  router.push({name: path});
};
</script>

<style scoped>
.footer-container {
  display: flex;
  justify-content: space-around; /* 양 끝에 요소를 배치하고 간격 균등 */
  align-items: center;
  padding: 10px;
  width: 100%;
  box-sizing: border-box;
}

.footer-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
}

.footer-item i {
  font-size: 24px;
  margin-bottom: 4px;
}

.footer-item span {
  font-size: 12px;
  color: #6c757d;
}

/* 선택된 항목 스타일 */
.selected {
  color: #8504e8; /* 보라색으로 강조 */
}

.selected i {
  color: #8504e8;
}
</style>


