<template>
  <div>
    <RouterView />
    <!-- 동적으로 컴포넌트 렌더링 -->
    <component :is="currentComponent" />
  </div>
</template>

<script setup>
import { computed, watch } from 'vue';
import { useViewStore } from '@/stores/viewStore';
import RegistAgreement from '@/components/RegistAgreement.vue';
import RegistForm from '@/components/RegistForm.vue';

const viewStore = useViewStore();

// 컴포넌트 매핑
const componentMap = {
  RegistAgreement,
  RegistForm,
};

// 현재 컴포넌트
const currentComponent = computed(() => {
  const componentName = viewStore.currentView;
  return componentMap[componentName] || null;
});

watch(
  () => viewStore.currentView,
)
</script>

<style scoped>
</style>