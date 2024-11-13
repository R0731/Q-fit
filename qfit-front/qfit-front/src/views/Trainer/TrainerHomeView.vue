<template>
  <div>
    <TheHeaderNav/>
    <h2>trainerhome</h2>
    <RouterLink :to="{ name: 'trainerLogin' }">로그인 페이지로 이동</RouterLink>
    <RouterView/>
    <TheFooterNav/>
  </div>
</template>

<script setup>
import TheHeaderNav from "@/components/common/TheHeaderNav.vue";
import TheFooterNav from "@/components/common/TheFooterNav.vue";
import TheCalender from "@/components/common/TheCalender.vue";
</script>

<style scoped>
</style>
