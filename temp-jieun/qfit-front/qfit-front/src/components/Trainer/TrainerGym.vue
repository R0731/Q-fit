<template>
  <div class="page-container">
    <h2 class="page-title">체육관 업데이트</h2>
    <form @submit.prevent="updateGym" class="form-container">
      <!-- 체육관 이름 입력 -->
      <div class="form-group">
        <label for="gym" class="form-label">나의 체육관</label>
        <input type="text" id="gym" v-model="gym" placeholder="체육관 이름 입력" class="form-input" />
      </div>
      <!-- 수정 버튼 -->
      <button type="submit" class="register-btn">수정</button>
    </form>
  </div>
</template>

<script setup>
import { useTrainerStore } from "@/stores/trainer";
import { useUserStore } from "@/stores/user";
import { ref, onMounted } from "vue";

const userStore = useUserStore();
const trainerStore = useTrainerStore();

const gym = ref("");

onMounted(async () => {
  await loadData();
});

const numberId = userStore.loginUser.numberId;
const loadData = async () => {
  try {
    await trainerStore.getGym(numberId);
    gym.value = trainerStore.trainer.gym;
  } catch (err) {
    console.log("체육관 정보 읽기 실패", err);
  }
};

const updateGym = async () => {
  const updatedGym = {
    gym: gym.value,
  };

  try {
    await trainerStore.updateGym(numberId, updatedGym);
    alert("수정 완료");
    console.log("체육관 업데이트 성공");
  } catch (err) {
    console.log("체육관 업데이트 실패", err);
  }
};
</script>

<style scoped>
/* 페이지 컨테이너 */
.page-container {
  width: 90%;
  margin: 0 auto;
  padding: 20px;
  text-align: center;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

/* 페이지 제목 */
.page-title {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 20px;
  color: #333;
}

/* 폼 컨테이너 */
.form-container {
  display: flex;
  flex-direction: column;
  gap: 30px;
}

/* 폼 그룹 */
.form-group {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

/* 라벨 스타일 */
.form-label {
  font-size: 1rem;
  margin-bottom: 5px;
  color: #555;
}

/* 공통 입력 필드 스타일 */
.form-input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 1rem;
  outline: none;
  transition: border-color 0.3s ease;
}

/* 포커스 시 효과 */
.form-input:focus {
  border-color: #007bff;
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
}

</style>
