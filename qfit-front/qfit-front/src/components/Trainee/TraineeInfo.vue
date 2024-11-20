<template>
  <div>
    <h5>{{ userName }} 회원님의 마이페이지</h5>
    <br>
    <div class="container">
      <span>내 정보 수정하기</span>
      
      <button @click="editInfo">수정하기</button>
    </div>
    <h5>나의 트레이너</h5>
    <hr>
    <div class="container">
      <span>김큐핏</span> <span>트레이너</span>

      <button @click="editInfo">수정하기</button>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useUserStore } from '@/stores/user';
import { useRouter } from 'vue-router';

const store = useUserStore();
const router = useRouter();

const userName = computed(()=>store.loginUser.name)

const editInfo = function(){
  router.push({name : 'editInfo'})

}

</script>


<style scoped>
button {
  float: right;
}

.container {
  padding-top: 20px;
  padding-bottom: 20px;
  border: 1px solid gainsboro;
  width: 100%;
  margin-bottom: 40px;
}

h5 {
  text-align: center;
}
</style>