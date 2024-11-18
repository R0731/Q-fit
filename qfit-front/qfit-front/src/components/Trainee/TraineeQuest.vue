<template>
  <div>
    <h3>트레이니 퀘스트</h3>
    <p>{{ formattedDate }}</p>
  </div>
</template>

<script setup>
import { useViewStore } from '@/stores/viewStore';
import { computed } from 'vue';

const viewStore = useViewStore();
const formattedDate = computed(()=>{
  console.log('선택날짜', viewStore.selectedDate)
  return viewStore.selectedDate.toLocaleDateString();
})
</script>

<style scoped>

</style>