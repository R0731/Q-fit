// trainee.js
import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

const REST_API_URL = `http://localhost:8080/trainee`;
const QUEST_API_URL = `http://localhost:8080/quest`;

export const useTraineeStore = defineStore('trainee', () => {
  const trainees = ref([]);
  const selectedTrainee = ref(null);
  const traineeWithQuests = ref([]);
  const trainer = ref({ trainerId: null, name: null });

  // 나이 계산
  const calculateAge = (birthDate) => {
    const today = new Date();
    const birth = new Date(birthDate);
    
    let age = today.getFullYear() - birth.getFullYear(); // 연도 차이 계산
    const monthDiff = today.getMonth() - birth.getMonth(); // 월 차이 계산

    // 생일이 아직 지나지 않았다면 나이에서 1을 뺍니다.
    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birth.getDate())) {
      age--;
    }

    return age;
  }

  /**
   * 트레이너에 해당하는 트레이니 목록 조회
   * @param {number} trainerId - 트레이너의 ID
   * @returns {void} - 트레이니 목록을 `trainees`에 저장
   */
  const fetchTraineeList = async (trainerId) =>{
    try{
      const response = await axios.get(`${REST_API_URL}/${trainerId}/trainee-list`);
      // trainees.value = response.data || [];
      trainees.value = response.data.map((trainee) => ({
        ...trainee,
        age: calculateAge(trainee.birthdate), // 나이 추가
      }));
    }catch(err){
      console.error('Failed to fetch trainee list:', err)
    }
  }

  /**
   * 트레이니 검색
   * @param {number} userId - 검색할 트레이니의 사용자 ID
   * @returns {void} - 검색된 트레이니 정보를 `searchResult`에 저장
   */
  const searchResult = ref(null);

  const searchTrainee = async (userId) =>{
    try {
      const response = await axios.get(`${REST_API_URL}/search-trainee`, {
        params: {userId},
      });
      searchResult.value = response.data;

      // response.data가 존재할 경우에만 age 계산
    if (response.data) {
      // `searchResult`에 나이(age) 추가
      searchResult.value = {
        ...response.data,
        age: calculateAge(response.data.birthdate), // 나이 추가
      };
    } else {
      // response.data가 없으면 그대로 설정
      searchResult.value = null;
    }

    } catch (err){
      searchResult.value = null;
      console.error('Failed to search trainee: ', err)
    }
  }

  /**
   * 트레이너가 트레이니 추가
   * @param {number} traineeId - 트레이니의 ID
   * @param {number} trainerId - 트레이너의 ID
   * @returns {void} - 트레이니에 트레이너를 추가, DB에 저장
   */
  const addTrainerToTrainee = async (traineeId, trainerId) => {
    try {
      await axios.put(`${REST_API_URL}/${traineeId}/add-trainer/${trainerId}`);
    } catch (err){
      console.error('Failed to add trainer to trainee:', err)
    }
  }

  /**
   * 트레이너가 트레이니 삭제
   * @param {number} traineeId - 삭제할 트레이니의 ID
   * @returns {void} - 트레이니를 삭제하고, 리스트에서 해당 트레이니를 제거
   */
  const deleteTrainee = async (traineeId) => {
    try {
      await axios.delete(`${REST_API_URL}/${traineeId}/delete`);
      trainees.value = trainees.value.filter((t) => t.id !== traineeId);
    } catch (error) {
      console.error(error);
    }
  };

  /**
   * 퀘스트 상태를 지닌 트레이니 목록 조회
   * @param {number} trainerId - 트레이너의 ID
   * @param {string} startAt - 조회 시작 날짜
   * @returns {void} - 트레이니 목록과 퀘스트 상태를 병합하여 `trainees`에 저장
   */
  const fetchTraineesWithQuestStatuses = async (trainerId, startAt) => {
    try {
      // 1. 훈련생 목록 가져오기
      const traineeResponse = await axios.get(`${REST_API_URL}/${trainerId}/trainee-list`);
      const traineeList = traineeResponse.data.map((trainee) => ({
        ...trainee,
        age: calculateAge(trainee.birthdate), // 나이 계산 추가
      }));

      // 2. 퀘스트 상태 가져오기
      const questResponse = await axios.get(`${QUEST_API_URL}/${trainerId}/status`, {
        params: { startAt },
      });
      const questStatuses = questResponse.data;

      // 3. 훈련생 목록과 퀘스트 상태 병합
      trainees.value = traineeList.map((trainee) => ({
        ...trainee,
        questStatus: questStatuses.find((status) => status.trainee_id === trainee.id)?.quest_status || '미등록',
      }));
    } catch (err) {
      console.error('Failed to fetch trainees with quest statuses:', err);
    }
  };

  /**
   * 트레이니 ID로 트레이너 조회
   * @param {number} traineeId - 조회할 트레이니의 ID
   * @returns {object} - 트레이너 정보를 반환 (트레이너 ID 포함)
   */
  const getTrainerId = async(traineeId) => {
    try{
      const url = `${REST_API_URL}/${traineeId}/read-trainer`
      const res = await axios.get(url)
      if(res.status === 200){
        trainer.value = { trainerId: res.data };
      }
      return res.data;
    }catch(err){
      console.log(err)
    }
  }

  /**
   * 트레이니 ID로 트레이너 이름 조회
   * @param {number} traineeId - 조회할 트레이니의 ID
   * @returns {void} - 트레이너 이름을 `trainer`에 저장
   */
  const getTrainerName = async(traineeId) => {
    try{
      const url = `${REST_API_URL}/search-trainer`
      const res = await axios.get(url, {
        params: {traineeId},
      });

      trainer.value = { name: res.data };
    }catch(err){
      console.log(err)
    }
  }

  return {
    trainees,
    fetchTraineeList,
    traineeWithQuests,
    searchResult,
    searchTrainee,
    addTrainerToTrainee,
    selectedTrainee,
    deleteTrainee,
    fetchTraineesWithQuestStatuses,
    getTrainerId,
    trainer,
    getTrainerName,
  };
});
