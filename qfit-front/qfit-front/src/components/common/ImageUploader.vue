<template>
  <div>
    <h5>이미지 업로드 기능용</h5>
    <!-- 파일 선택 -->
    <input type="file" @change="onFileSelect"/>
    <!-- 업로드 버튼 -->
    <button @click="uploadImage" :disabled="!selectedFile">업로드</button>

    <!-- 업로드된 URL 표시 -->
    <!-- 업로드가 성공적으로 완료된 경우, 파일의 URL을 화면에 표시합니다 -->
    <div v-if="uploadedFileUrl">
      <p>업로드된 URL:</p>
      <!-- 업로드된 URL을 클릭하면 새 탭에서 파일을 확인할 수 있습니다 -->
      <a :href="uploadedFileUrl" target="_blank">{{ uploadedFileUrl }}</a>

      <button @click="deleteImage">이미지 삭제</button>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useImageStore } from '@/stores/imageStore';

const imageStore = useImageStore();
const selectedFile = ref(null);
const uploadedFileUrl = computed(() => imageStore.uploadedFileUrl);

// 파일 선택
const onFileSelect = (event) => {
  selectedFile.value = event.target.files[0];
};

// 파일 업로드
const uploadImage = async() => {
  if(selectedFile.value){
    try{
      const res = await imageStore.uploadFile(selectedFile.value);
    }catch(err){
      console.error(err);
    }
  }
}

// 파일 삭제
const deleteImage = async() => {
  try{
    const res = 1;
  }catch(err){
    console.error(err);
  }
}

</script>

<style scoped>

</style>