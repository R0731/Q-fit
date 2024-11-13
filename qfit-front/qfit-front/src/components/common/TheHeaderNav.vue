<template>
  <div class="header-container">
    <!-- 왼쪽 화살표 아이콘 -->
    <i class="bi bi-chevron-left"></i>

    <!-- 가운데 로고 -->
    <img src="@/assets/logo.png" alt="logo" class="logo" />

    <!-- 오른쪽 벨 아이콘 -->
    <i class="bi bi-bell"></i>
  </div>
</template>

<script setup>
</script>

<style scoped>
/* 플렉스 컨테이너 설정 */
.header-container {
  display: flex;
  align-items: center; /* 세로 가운데 정렬 */
  justify-content: space-between; /* 양 끝 정렬 */
  padding: 0 16px; /* 좌우 여백 추가 */
  height: 60px; /* 헤더 높이 고정 */
  width: 100%;
  box-sizing: border-box;
}

/* 로고 스타일 */
.logo {
  width: 80px;
  height: 80px;
}

/* 아이콘 스타일 */
.bi {
  font-size: 24px; /* 아이콘 크기 조정 */
  color: black; /* 아이콘 색상 설정 */
  cursor: pointer;
}
</style>
