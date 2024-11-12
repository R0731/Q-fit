<template>
  <div>
    <h3>App.</h3>
    <RouterLink to="/">home</RouterLink> | 
    <RouterLink to="/login">로그인</RouterLink>
    <RouterView></RouterView>
  </div>
</template>

<script setup>
import { RouterView } from 'vue-router';


</script>

<style scoped>

</style>