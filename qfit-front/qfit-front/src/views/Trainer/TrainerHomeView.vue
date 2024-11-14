<template>
  <div class="trainer-home">
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
import TheHeaderNav from "@/components/common/TrainerHeaderNav.vue";
import TheFooterNav from "@/components/common/TrainerFooterNav.vue";
import { useRouter } from 'vue-router';

const router = useRouter();

</script>

<style scoped>
/* 전체 페이지에 대해 box-sizing을 설정 */
.trainer-home {
  display: flex;
  flex-direction: column;
  height: 100vh; 
  width: 100%; /* 👉 부모 요소의 너비를 초과하지 않도록 설정 */
  box-sizing: border-box;
  overflow-x: hidden; /* 👉 가로 스크롤 방지 */
  margin: 0;
  padding: 0;
}

/* 헤더 스타일 */
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 60px;
  background-color: white;
  border-bottom: 1px solid #ddd;
  width: 100%;
  box-sizing: border-box; /* 헤더도 border-box 적용 */
}

/* 메인 콘텐츠 스타일 */
.content {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  width: 100%;
  padding: 20px;
  box-sizing: border-box;
  background-color: #fafafa;
}

/* 푸터 스타일 */
.footer {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 100px;
  height: 60px;
  background-color: white;
  border-top: 1px solid #ddd;
  width: 100%;
}
</style>
