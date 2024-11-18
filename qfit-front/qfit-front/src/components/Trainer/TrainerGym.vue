<template>
  <div>
    <p>체육관 업데이트</p>
    <form @submit.prevent="updateGym">
      <div>
        <label for="gym">나의 체육관</label>
        <input type="text" id="gym" v-model="gym" />
      </div>
      <button type="submit">수정</button>
    </form>
  </div>
</template>

<script setup>
import { useTrainerStore } from '@/stores/trainer';
import { useUserStore } from '@/stores/user';
import { ref, onMounted } from 'vue';

const userStore = useUserStore();
const trainerStore = useTrainerStore();

const gym = ref('');

onMounted(async () => {
  await loadData();
});

const numberId = userStore.loginUser.numberId;
const loadData = async () => {
  try{
    await trainerStore.getGym(numberId);
    gym.value = trainerStore.trainer.gym;
  }catch(err){
    console.log('체육관 정보 읽기 실패', err);
  }
}

const updateGym = async() =>{
  const updatedGym = {
    gym: gym.value,
  }

  try{
    await trainerStore.updateGym(numberId, updatedGym);
    alert('수정 완료')
    console.log('체육관 업데이트 성공')
  }catch(err){
    console.log('체육관 업데이트 실패', err)
  }
}
</script>

<style scoped>

</style>