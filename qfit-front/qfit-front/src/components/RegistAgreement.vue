<template>
  <div>
    <div class="agreement-container">
      <!-- 개인정보 수집 및 이용 동의 내용 -->
      <h3>개인정보 수집 및 이용 동의</h3>
      <textarea readonly rows="10">
Qfit 주식회사는 최소한의 개인정보만을 필요한 시점에 수집하고, 수집한 개인정보는 고지한 범위 내에서만 사용합니다. 사전 동의 없이 그 범위를 초과하여 이용하거나 외부에 공개하지 않습니다. 

회사는 개인정보 처리방침을 공개하여 회원이 언제나 확인할 수 있도록 하고, 관련 법령 또는 내부 사정에 따라 개정할 수 있으며, 개정 시 이를 공지합니다.
① 이용자 식별 및 본인 여부 확인, 회원가입, 문의사항 또는 불만처리 등 회원 관리 목적
② 회사의 서비스 제공 및 운영
③ 제휴사 포인트 정보 수신 및 제공을 위한 목적
④ 법령 및 약관 위반 방지를 위한 목적
      </textarea>

      <!-- 개인정보 동의 체크박스 및 확인 버튼 -->
      <div class="checkbox-container">
        <label>
          <input type="checkbox" v-model="isAgreed" />
          개인정보 수집 및 이용에 동의합니다.
        </label>
        <button class="register-btn" @click="proceed" :disabled="!isAgreed">확인</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useViewStore } from '@/stores/viewStore';

const viewStore = useViewStore();
const isAgreed = ref(false); // 동의 여부

// 개인정보 수집 및 이용 동의 확인
const proceed = () => {
  if (isAgreed.value) {
    console.log("동의 완료, 다음 단계로 이동");
    viewStore.setView('RegistForm');
    console.log("현재 View 변경:", viewStore.currentView);
  } else {
    alert('개인정보 수집 및 이용에 동의해주세요.');
  }
};
</script>

<style scoped>
/* 글씨 크기는 필요에 따라 조절하세요 */
.agreement-container {
  margin-top: 40px;
}

textarea {
  width: 100%;
  resize: none;
  padding: 10px;
  font-size: 14px; /* 글씨 크기를 조절할 수 있습니다 */
  /* font-size를 변경하여 글씨 크기를 조절하세요 */
  margin-bottom: 20px;
}

.checkbox-container {
  margin-top: 20px;
}

button {
  margin-left: 20px;
  padding: 10px 20px;
  background-color: var(--theme-color);
  color: white;
  border: none;
  cursor: pointer;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>
