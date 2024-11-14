<template>
  <div>
    <h4>내 프로필</h4>
    <!-- <p>{{ loginUser.userId }}님 어서오세요.</p> -->
    <button @click="navigateTo('trainerUpdate')">회원정보수정</button>
    <h4>체육관 정보 수정</h4>
    <button @click="navigateTo('trainerGym')">체육관 정보</button>
  </div>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router';
import { useUserStore } from '@/stores/user';

const router = useRouter();
const route = useRoute();
const userStore = useUserStore();

const loginUser = userStore.loginUser;
const navigateTo = (routeName) => {
  if(route.name !== routeName){
    router.push({name: routeName});
  }
}
</script>

<style scoped>

</style>