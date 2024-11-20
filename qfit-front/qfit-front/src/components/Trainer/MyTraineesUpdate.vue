<template>
  <div>
      <h2>새 회원 추가하기</h2>
      <div>
          <input 
            type="text" 
            v-model="searchUserId" 
            placeholder="회원ID로 검색하세요,(ex. user123)"
            class="search-input"
          />
          <button class="search-btn" @click="searchTrainee">검색</button>
      </div>

      <!-- 검색 결과 -->
      <div class="search-result">
        <p v-if="!searchResult">검색된 유저가 없습니다.</p>
        <div v-else class="result-card">
          <p class="result-name">{{ searchResult.userName }} ({{ searchResult.age }}세)</p>
          <button class="add-btn" @click="addTrainee">추가하기</button>
        </div>
      </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useTraineeStore } from '@/stores/trainee'
import router from '@/router';
import { useUserStore } from '@/stores/user';

const searchUserId = ref('');
const searchResult = ref(null);

const traineeStore = useTraineeStore();

// 검색 로직
const searchTrainee = async () => {
if (!searchUserId.value.trim()) {
  alert('검색어를 입력하세요.');
  return;
}

try {
  await traineeStore.searchTrainee(searchUserId.value);
  searchResult.value = traineeStore.searchResult || null;
  
  // 디버깅용 로그
  console.log('searchResult:', searchResult.value);
} catch (error) {
  console.error('검색 실패:', error);
  searchResult.value = null;
}
};


const userStore = useUserStore();
const trainerId = userStore.loginUser.numberId;

// 회원 추가 로직
const addTrainee = async () => {
if (!searchResult.value) return;

try {
  await traineeStore.addTrainerToTrainee(searchResult.value.id, trainerId);
  console.log(traineeStore.trainerId);
  alert('회원이 성공적으로 추가되었습니다.');
  searchUserId.value = ''; // 입력창 초기화
  searchResult.value = null; // 검색 결과 초기화
  router.push({ name: 'MyTrainees' });
} catch (error) {
  console.error('회원 추가 실패:', error);
  alert('회원 추가에 실패했습니다.');
}
};

</script>

<style scoped>
/* 컨테이너 스타일 */
.container {
padding: 16px;
font-family: Arial, sans-serif;
}

/* 제목 스타일 */
h2 {
font-size: 18px;
color: #4a4a4a;
text-align: center;
margin-bottom: 20px;
}

/* 검색 바 스타일 */
.search-bar {
display: flex;
justify-content: space-between;
align-items: center;
margin-bottom: 16px;
}

.search-input {
flex: 1;
padding: 10px;
border: 1px solid #ddd;
border-radius: 8px;
font-size: 14px;
margin-right: 10px;
}

.search-btn {
padding: 10px 16px;
background-color: #4caf50;
color: #fff;
border: none;
border-radius: 8px;
font-size: 14px;
cursor: pointer;
}

.search-btn:hover {
background-color: #43a047;
}

/* 검색 결과 스타일 */
.search-result {
text-align: center;
}

.result-card {
display: flex;
justify-content: space-between;
align-items: center;
padding: 16px;
background-color: #f9f9f9;
border: 1px solid #ddd;
border-radius: 8px;
margin-top: 16px;
}

.result-name {
font-size: 16px;
font-weight: bold;
color: #333;
}

.add-btn {
padding: 10px 16px;
background-color: #4caf50;
color: #fff;
border: none;
border-radius: 8px;
font-size: 14px;
cursor: pointer;
}

.add-btn:hover {
background-color: #43a047;
}
</style>