// quest.js
import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

const REST_API_URL = 'http://localhost:8080/quest';

export const useQuestStore = defineStore('quest', () => {
  const quest = ref(null); // Quest 데이터
  const tasks = ref([]); // Task 목록

  /**
   * Date 또는 문자열을 'YYYY-MM-DD' 형식으로 변환
   */
  // const formatDateToYMD = (date) => {
  //   if (date instanceof Date) {
  //     return date.toISOString().split('T')[0];
  //   }
  //   if (typeof date === 'string') {
  //     const parsedDate = new Date(date);
  //     if (isNaN(parsedDate.getTime())) {
  //       throw new Error('Invalid date format: Unable to parse startDate');
  //     }
  //     return parsedDate.toISOString().split('T')[0];
  //   }
  //   throw new Error('Invalid startDate format: Expected Date or string');
  // };

  /**
   * 퀘스트 데이터를 로드하는 함수
   */
  const getQuestByIdAndStartDate = async (traineeId, startDate) => {
    // 상태 초기화
    quest.value = null;
    tasks.value = [];

    try {
      // startDate 포맷팅
      // const formattedDate = formatDateToYMD(startDate);

      // 요청 로그
      console.log('API 요청 URL:', REST_API_URL, { traineeId, startAt: startDate });

      // API 호출
      const res = await axios.get(REST_API_URL, {
        params: { traineeId, startAt: startDate },
      });

      // 상태 업데이트
      if (res.status === 200 && res.data) {
        quest.value = res.data;
        tasks.value = res.data.tasks || [];
        console.log('퀘스트 불러오기 성공:', tasks.value);
      } else {
        console.warn('퀘스트 데이터 없음');
      }
    } catch (error) {
      console.error('퀘스트 불러오기 실패:', error.message);
      throw error;
    }
  };
  
  /**
   * Task 목록 반환
  */
 const getTasks = computed(() => tasks.value);
 
 const getQuestsByTraineesAndDate = async (traineeIds, startDate) => {
   const quests = {};
   
   try {
     // 요청 로그
     console.log('Batch Quest Fetch:', { traineeIds, startDate });
     
     // 여러 퀘스트를 동시에 조회
     const promises = traineeIds.map(async (traineeId) => {
       try {
         const res = await axios.get(REST_API_URL, {
           params: { traineeId, startAt: startDate },
          });
          
          if (res.status === 200 && res.data) {
            quests[traineeId] = res.data.status || "미등록"; // 퀘스트 상태 저장
          } else {
            quests[traineeId] = "미등록";
          }
        } catch (err) {
          console.error(`Failed to fetch quest for Trainee ID ${traineeId}:`, err);
          quests[traineeId] = "미등록";
        }
      });
      
      await Promise.all(promises); // 모든 요청 완료 대기
    } catch (error) {
      console.error('Batch Quest Fetch Error:', error);
    }
    
    return quests; // 퀘스트 상태 객체 반환
  };

  const questCompletionRates = ref([]); // 퀘스트 상태 (달성률, 리뷰, 피드백)

  const getTraineeQuestCompletionRate = async (traineeId, startDate, endDate) => {
    try {
      const res = await axios.get(`${REST_API_URL}/${traineeId}`, {
        params: {startAt: startDate, endAt: endDate },
      });
      questCompletionRates.value = res.data;
    } catch (err) {
      console.error(err);
    }
  };
  
  /**
   * 퀘스트 등록 함수
   */
  const createQuest = async (questData) => {
  
    try {
  
      // 요청 로그
      console.log('API 요청 URL:', `${REST_API_URL}/`,  questData );
  
      // API 호출
      const res = await axios.post(`${REST_API_URL}/`, questData );
  
      if (res.status === 201) {
        console.log('퀘스트 등록 성공:', res.data);
      } else {
        console.error('퀘스트 등록 실패');
      }
    } catch (error) {
      console.error('퀘스트 등록 중 오류 발생:', error);
    }
  };

  // 스토어에 제공할 함수 및 상태
  return { 
    quest, 
    tasks, 
    getQuestByIdAndStartDate, 
    getQuestsByTraineesAndDate,
    getTasks, 
    questCompletionRates, 
    getTraineeQuestCompletionRate, 
    createQuest, 
    // formatDateToYMD 
  };
});
