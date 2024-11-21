<!-- app.vue -->
<template>
  <div class="app-container">
    <RouterView />
  </div>
</template>

<script setup>
</script>

<style scoped>
/* 상위 컨테이너 스타일 */
.app-container {
  display: flex;
  position: relative;
  flex-direction: column;
  align-items: center;
  max-width: 480px;
  width: 100%; /* 가로 스크롤 방지 */
  margin: 0 auto;
  height: 100vh;
  overflow-y: visible;
  overflow-x: hidden; /* 👉 가로 스크롤 방지 */
  box-sizing: border-box;
}

/* 상위 요소 스타일 (html, body) */
html, body {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
  overflow-x: hidden; /* 전체 페이지에서 가로 스크롤 방지 */
  box-sizing: border-box;
}
</style>
