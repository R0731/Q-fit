// quest.js
import { ref, computed, reactive } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

const REST_API_URL = 'http://localhost:8080/quest';

export const useQuestStore = defineStore('quest', () => {
  const quest = ref(null); // 퀘스트 데이터
  const tasks = ref([]); // 테스크 목록
  const getTasks = computed(() => tasks.value); // 테스크 목록 반환
  const exerciseData = reactive({}); // 운동 데이터 

   /**
   * 퀘스트 데이터 로드
   * @param {number} traineeId - 트레이니 ID
   * @param {string} startDate - 시작 날짜
   * @returns {void}
   */
  const getQuestByIdAndStartDate = async (traineeId, startDate) => {
    // 상태 초기화
    quest.value = null;
    tasks.value = [];

    try {
      const res = await axios.get(REST_API_URL, {
        params: { traineeId, startAt: startDate },
      });

      if (res.status === 200 && res.data) {
        quest.value = res.data;
        tasks.value = res.data.tasks || [];
      } else {
        console.warn('퀘스트 데이터 없음');
      }
    } catch (error) {
      console.error('퀘스트 불러오기 실패:', error.message);
      throw error;
    }
  };
  
 /**
   * 트레이니 ID 목록으로 퀘스트 상태 조회
   * @param {Array<number>} traineeIds - 트레이니 ID 목록
   * @param {string} startDate - 시작 날짜
   * @returns {Object} - 트레이니 ID별 퀘스트 상태 객체
   */
 const getQuestsByTraineesAndDate = async (traineeIds, startDate) => {
   const quests = {};
   
   try {
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


  // 액션 정의
  const setTasks = (newTasks) => {
    tasks.value = newTasks;
    originalTasks.value = JSON.parse(JSON.stringify(newTasks)); // 깊은 복사
  };

   /**
   * 테스크 업데이트
   * @param { Array<Object> } - 업데이트될 테스크 데이터
   */ 
  const updateTasks = (updatedTasks) => {
    tasks.value = [...updatedTasks]; // 새로운 배열로 대체
  };

  const resetTasks = () => {
    tasks.value = JSON.parse(JSON.stringify(originalTasks.value));
  };

  /**
   * 운동 데이터 로드
   * @returns {void}
   */
  const fetchExerciseData = async () => {
    try {
      // 유니크한 exerciseId 추출
      const exerciseIds = [...new Set(tasks.value.map((task) => task.exerciseId))];

      // exerciseId별 데이터 요청
      for (const id of exerciseIds) {
        if (!exerciseData[id]) {
          const response = await axios.get(`/api/exercise/${id}`);
          const data = response.data;

          // 운동 데이터 저장
          exerciseData[id] = {
            exerciseId: data.exerciseId,
            exerciseName: data.exerciseName,
            exerciseParts: data.exerciseParts,
          };
        }
      }
    } catch (error) {
      console.error('운동 데이터 로드 실패:', error);
    }
  };

  /**
   * 퀘스트 달성률, 리뷰, 피드백 상태 가져옴
   * @param {number} traineeId - 트레이니의 ID
   * @param {string} startDate - 조회 시작 날짜
   * @param {string} endDate - 조회 종료 날짜
   * @returns {void} - 트레이니의 퀘스트 상태를 `questCompletionRates`에 저장
 */
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
   * 퀘스트 등록
   * @param {Object} questData - 등록할 퀘스트 데이터
   * @returns {void} - 퀘스트 등록 결과 로그 출력
   */
  const createQuest = async (questData) => {
  
    try {
      // API 호출
      await axios.post(`${REST_API_URL}/`, questData );
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
    exerciseData,
    setTasks,
    fetchExerciseData,
    updateTasks,
    questCompletionRates, 
    getTraineeQuestCompletionRate, 
    createQuest, 
  };
});
