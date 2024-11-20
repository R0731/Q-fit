<template>
  <div class="home">
    <!-- 헤더 영역 -->
    <TheHeaderNav class="header" />
    
    <!-- 메인 콘텐츠 영역 -->
    <div class="content-trainee">
      <RouterLink :to="{ name: 'traineeList' }"></RouterLink>
      <RouterView />
    </div>

    <!-- 푸터 영역 -->
    <TheFooterNav class="footer" />
  </div>
</template>

<script setup>
import TheHeaderNav from "@/components/common/TraineeHeaderNav.vue";
import TheFooterNav from "@/components/common/TraineeFooterNav.vue";
import { useRouter } from 'vue-router';

const router = useRouter();

</script>

<style scoped>
.content-trainee{
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  align-items: stretch;
  justify-content: flex-start;
  width: 100%;
  padding: 20px;
}
</style>