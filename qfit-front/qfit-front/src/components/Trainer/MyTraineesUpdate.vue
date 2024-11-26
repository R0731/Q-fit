<template>
  <div class="page-container">
    <!-- 제목 -->
    <h3 class="page-title">새 회원 추가하기</h3>

    <!-- 검색 섹션 -->
    <div class="search-bar">
      <input 
        type="text" 
        v-model="searchUserId" 
        placeholder="회원ID로 검색하세요,(ex. user123)"
        class="search-input"
      />
      <button class="small-btn" @click="searchTrainee">검색</button>
    </div>

    <!-- 검색 결과 -->
    <div class="search-result">
      <p v-if="!searchResult" class="no-result">검색된 유저가 없습니다.</p>
      <div v-else class="result-card">
        <!-- 검색된 회원 정보 -->
        <div class="trainee-info">
          <span class="trainee-name">{{ searchResult.userName }}</span>
          <small class="trainee-age">{{ searchResult.age }}세</small>
        </div>
        <!-- 회원 추가 버튼 -->
        <button class="small-btn add-btn" @click="addTrainee">추가하기</button>
      </div>
    </div>

    <!-- 모달 창 -->
    <div v-if="showModal" class="modal-overlay">
      <div class="modal-content">
        <h2>회원 추가 완료</h2>
        <p>{{ searchResult.userName }} 회원 추가 완료</p>
        <button class="register-btn close-btn" @click="closeModal">확인</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useTraineeStore } from "@/stores/trainee";
import router from "@/router";
import { useUserStore } from "@/stores/user";
import { useNotificationStore } from "@/stores/notification";

const traineeStore = useTraineeStore();
const userStore = useUserStore();
const notificationStore = useNotificationStore();
const trainerId = userStore.loginUser.numberId;

const searchUserId = ref("");
const searchResult = ref(null);
const showModal = ref(false); // 모달 표시 여부

// 검색 로직
const searchTrainee = async () => {
  if (!searchUserId.value.trim()) {
    alert("검색어를 입력하세요.");
    return;
  }

  try {
    await traineeStore.searchTrainee(searchUserId.value);
    searchResult.value = traineeStore.searchResult || null;
  } catch (error) {
    console.error("검색 실패:", error);
    searchResult.value = null;
  }
};

// 회원 추가 로직
const addTrainee = async () => {
  if (!searchResult.value) return;

  try {
    await traineeStore.addTrainerToTrainee(searchResult.value.id, trainerId);
    // 회원 추가 성공 시 모달 표시
    showModal.value = true;
    makeNotification();
    router.push({name: 'MyTrainees'})
  } catch (error) {
    console.error("회원 추가 실패:", error);
    alert("회원 추가에 실패했습니다.");
  }
};

// 모달 닫기 로직
const closeModal = () => {
  showModal.value = false;
  searchUserId.value = ""; // 입력창 초기화
  searchResult.value = null; // 검색 결과 초기화
  router.push({ name: "MyTrainees" }); // 목록 페이지로 이동
};

// 알림 생성
const makeNotification = async() => {
  try{
    const notification = {userId: searchResult.value.id, message: `${userStore.loginUser.name}님이 당신을 회원 목록에 추가하였습니다.`}
    await notificationStore.createNotification(notification)
  }catch(err){
    console.log('프론트 등록 중 오류 발생', err)
  }
}
</script>

<style scoped>
/* 페이지 컨테이너 */
.page-container {
  width: 100%;
  margin: 0 auto;
  padding: 20px;
  text-align: center;
}

/* 제목 스타일 */
.page-title {
  font-size: 1.3rem;
  font-weight: bold;
  margin-bottom: 20px;
  color: var(--text-color);
}

/* 검색 바 스타일 */
.search-bar {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-bottom: 20px;
}

/* 검색 입력 필드 */
.search-input {
  flex: 1;
  max-width: 400px;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 20px;
  font-size: 1rem;
}

/* 버튼 스타일 */
.blue-btn {
  padding: 8px 16px;
  font-size: 0.9rem;
  font-weight: bold;
  border: none;
  border-radius: 20px;
  background: linear-gradient(90deg, #007bff, #0056b3);
  color: #fff;
  cursor: pointer;
  transition: all 0.3s ease;
}

.blue-btn:hover {
  background: #fff;
  color: #007bff;
  border: 1px solid #007bff;
}

/* 검색 결과 스타일 */
.search-result {
  margin-top: 20px;
}

.no-result {
  font-size: 0.9rem;
  color: #777;
}

/* 검색 결과 카드 */
.result-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 20px;
  border-radius: 10px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  margin-top: 15px;
}

/* 검색된 회원 정보 */
.trainee-info {
  display: flex;
  align-items: center;
}

.trainee-name {
  font-weight: bold;
  font-size: 1.1rem;
  color: var(--text-color);
}

.trainee-age {
  font-size: 0.9rem;
  color: #777;
  margin-left: 10px;
}

/* 모달 오버레이 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 999;
}

/* 모달 콘텐츠 */
.modal-content {
  background: #f9f9f9;
  padding: 30px;
  border-radius: 15px;
  text-align: center;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
  max-width: 400px;
  width: 90%;
}

.modal-content h2 {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 20px;
  color: var(--text-color);
}

.modal-content p {
  font-size: 1rem;
  margin-bottom: 20px;
  color: #555;
}

/* 닫기 버튼 */
.close-btn {
  padding: 10px 20px;
  font-size: 1rem;
  font-weight: bold;
  border: none;
  border-radius: 20px;
  background: linear-gradient(90deg, var(--theme-color), #9d47f4); 
  color: #fff;
  cursor: pointer;
  transition: all 0.3s ease;
}

.close-btn:hover {
  background: #fff;
  color: var(--theme-color);
  border: 1px solid var(--theme-color);
}
</style>

