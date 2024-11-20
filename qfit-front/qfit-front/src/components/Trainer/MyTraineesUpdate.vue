<template>
    <div>
        <h2>새 회원 추가하기</h2>
        <div>
            <input v-model="searchUserId" placeholder="회원ID로 검색하세요,(ex. user123)"/>
            <button @click="searchTraineeHandler(searchUserId)">검색아이콘</button>
        </div>

        <div v-if="searchResult">
            <p><strong>User ID:</strong> {{ searchResult.value.userId }}</p>
            <p><strong>Name:</strong> {{ searchResult.userName }}</p>
            <p><strong>Email:</strong> {{ searchResult.email }}</p>
            <p><strong>Age:</strong> {{ searchResult.age }}</p>
            <button @click="addTrainerToTrainee(searchResult.id)">추가하기</button>
        </div>

        <div v-else>
            검색 결과가 없습니다.
        </div>
    </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useTraineeStore } from '@/stores/trainee'

const searchUserId = ref('');  // 검색할 유저의 userId
const { searchTrainee, searchResult, addTrainerToTrainee } = useTraineeStore()
const errorMessage = ref(null)

// 검색 버튼을 클릭하면 호출되는 메서드
// const searchTraineeHandler = async () => {
//   if (searchUserId.value.trim()) {
//     await searchTrainee(searchUserId.value);
//     if (!searchResult.value) {
//       errorMessage.value = 'Trainee not found or already assigned to a trainer.';
//     } else {
//       errorMessage.value = null;  // 결과가 있으면 에러 메시지 제거
//     }
//   } else {
//     errorMessage.value = 'Please enter a user ID to search.';
//   }
// }
const searchTraineeHandler = async () => {
  if (searchUserId.value.trim()) {
    await searchTrainee(searchUserId.value);
    // searchResult가 null이 아닐 때만 참조하도록 수정
    if (searchResult && searchResult.value) {
      if (!searchResult.value) {
        errorMessage.value = 'Trainee not found or already assigned to a trainer.';
      } else {
        errorMessage.value = null;  // 결과가 있으면 에러 메시지 제거
      }
    } else {
      errorMessage.value = 'Trainee not found or search failed.';
    }
  } else {
    errorMessage.value = 'Please enter a user ID to search.';
  }
}

watch(
  () => searchResult?.value, // `searchResult`가 null일 경우 안전하게 접근
  (newValue, oldValue) => {
    if (newValue) {
      console.log('검색 결과 업데이트:', newValue); // 검색 결과 출력
      // 필요한 추가 작업 수행
    } else {
      console.log('검색 결과 없음 또는 초기화됨');
    }
  },
  { immediate: true } // 컴포넌트 초기화 시에도 실행
);

</script>

<style scoped>

</style>