<template>
    <div>
        <h2>회원 삭제하기</h2>

        <!-- 회원 목록 -->
        <ul class="trainee-list">
            <li v-for="trainee in trainees" :key="trainee.id" class="trainee-item">
                <div class="trainee-info">
                <div class="profile-placeholder"></div>
                <div>
                    <p class="trainee-name">{{ trainee.user_name }}</p>
                    <p class="trainee-age">{{ trainee.age }}세</p>
                </div>
                </div>
                <button class="delete-btn" @click="deleteTrainee(trainee.id)">삭제하기</button>
            </li>
        </ul>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { useTraineeStore } from '@/stores/trainee';
import { useRouter } from 'vue-router';

// Pinia Store 사용
const traineeStore = useTraineeStore();
const trainees = traineeStore.trainees;
const router = useRouter();

// 회원 삭제 핸들러
const deleteTrainee = async (traineeId) => {
  if (confirm('정말로 이 회원을 삭제하시겠습니까?')) {
    try {
      // 삭제 API 호출
      await traineeStore.deleteTrainee(traineeId);
      alert('회원이 삭제되었습니다.');
      router.push({ name: 'MyTrainees' }); // 'MyTrainees' 라우트로 이동
    } catch (error) {
      console.error('회원 삭제에 실패했습니다:', error);
      alert('삭제 중 오류가 발생했습니다.');
    }
  }
};
</script>

<style scoped>
/* 제목 스타일 */
h2 {
  font-size: 18px;
  color: #4a4a4a;
  text-align: center;
  margin-bottom: 20px;
}

/* 회원 목록 스타일 */
.trainee-list {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

/* 회원 아이템 스타일 */
.trainee-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 16px;
  margin-bottom: 10px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

/* 회원 정보 섹션 */
.trainee-info {
  display: flex;
  align-items: center;
}

/* 프로필 이미지 자리 표시 */
.profile-placeholder {
  width: 40px;
  height: 40px;
  background-color: #ddd;
  border-radius: 50%;
  margin-right: 10px;
}

/* 회원 이름 및 나이 스타일 */
.trainee-name {
  font-size: 16px;
  font-weight: bold;
  color: #333;
  margin: 0;
}

.trainee-age {
  font-size: 14px;
  color: #666;
  margin: 0;
}

/* 삭제 버튼 스타일 */
.delete-btn {
  padding: 8px 12px;
  background-color: #ff4d4f;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
}

.delete-btn:hover {
  background-color: #d9363e;
}
</style>