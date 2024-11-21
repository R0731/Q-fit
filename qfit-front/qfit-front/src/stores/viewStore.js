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
  
  // 날짜 포맷 함수 (YYYY-MM-DD)
  const formatDate = (date) => {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  };

  // 선택된 날짜 (YYYY-MM-DD 형식)
  const selectedDate = ref(formatDate(new Date()));
    
  // 선택된 날짜 설정 함수
  const setSelectedDate = (date) => {
    selectedDate.value = formatDate(date); // 포맷 변환 후 저장
  };

  return { currentView, setView, resetView, selectedDate, setSelectedDate };
});