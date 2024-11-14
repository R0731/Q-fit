import { ref } from 'vue'
import { defineStore } from 'pinia'

// 주소 변경 없이 컴포넌트 이동용
export const useViewStore = defineStore('viewStore', () => {
  const currentView = ref('RegistAgreement');

  const setView = (viewName) => {
    console.log('다음 단계로 이동')
    currentView.value = viewName;
  };

  const resetView = () => {
    // console.log("회원가입 상태 초기화");
    currentView.value = 'RegistAgreement';
  };

  return { currentView, setView, resetView };
});