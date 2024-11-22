import { defineStore } from 'pinia';
import axios from 'axios';

const REST_API_URL = `http://localhost:8080/s3`;

export const useImageStore = defineStore('image', () => {
  /**
   * 파일 업로드
   * @param {File} file - 업로드할 파일 객체
   * @returns {Promise<string>} - 업로드된 파일 URL
   */
  const uploadFile = async (file) => {
    const formData = new FormData();
    formData.append('file', file);
    const response = await axios.post(`${REST_API_URL}/upload`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    return response.data; // 업로드된 파일 URL 반환
  };

  /**
   * 파일 삭제
   * @param {string} fileName - 삭제할 파일 이름
   * @returns {Promise<string>} - 삭제 성공 메시지
   */
  const deleteFile = async (fileName) => {
    const response = await axios.delete(`${REST_API_URL}/delete`, {
      params: { fileName: fileName },
      responseType: 'blob',
    });
    return response.data; // 삭제 성공 메시지 반환
  };

  /**
   * 파일 로드
   * @param {string} fileName - 로드할 파일 이름
   * @returns {Promise<Blob>} - 파일 데이터 Blob
   */
  const loadFile = async (fileName) => {
    const response = await axios.get(`${REST_API_URL}/load`, {
      params: { fileName },
      responseType: 'blob',
    });
    return response.data; // Blob 데이터 반환
  };

  return {
    uploadFile,
    deleteFile,
    loadFile,
  };
});
