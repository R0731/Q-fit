<template>
  <div>
    <h2>traineeHome</h2>
    <RouterView/>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();
</script>

<style scoped>

</style>