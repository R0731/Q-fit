import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useViewStore = defineStore('viewStore', () => {

  const currentView = ref('RegistAgreement');

  /**
   * 현재 뷰 변경
   * @param {string} viewName - 변경할 뷰 이름
   */
  const setView = (viewName) => {
    currentView.value = viewName;
  };
  
  // 뷰 상태 초기화
  const resetView = () => {
    currentView.value = 'RegistAgreement';
  };
  
  /**
   * 날짜를 YYYY-MM-DD 형식으로 변환
   * @param {Date} date - 변환할 날짜 객체
   * @returns {string} - 변환된 날짜 문자열
   */
  const formatDate = (date) => {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  };
  
  /**
   * 선택된 날짜 변경
   * @param {Date} date - 변경할 날짜 객체
  */
 const setSelectedDate = (date) => {
   selectedDate.value = formatDate(date); // 포맷 변환 후 저장
  };
  
  // 선택된 날짜 (YYYY-MM-DD 형식)
  const selectedDate = ref(formatDate(new Date()));

  return { currentView, setView, resetView, selectedDate, setSelectedDate };
});