<template>
  <div>
    <RouterView />
    <component :is="currentComponent" />
  </div>
</template>

<script setup>
import { computed, watch, ref } from 'vue';
import { useViewStore } from '@/stores/viewStore';
import RegistAgreement from '@/components/RegistAgreement.vue';
import RegistForm from '@/components/RegistForm.vue';

const viewStore = useViewStore();

const componentMap = {
  RegistAgreement,
  RegistForm,
};

const currentComponent = computed(() => {
  const componentName = viewStore.currentView;
  console.log("현재 컴포넌트:", componentName);
  return componentMap[componentName] || null;
});

// const currentComponent = ref('TraineeList');

// const currentComponent = computed(()=> viewStore.currentView);

// console.log("현재 컴포넌트:", currentComponent.value);

watch(
  () => viewStore.currentView,
  (newView) => {
    console.log("변경", newView);
  }
)

</script>

<style scoped>

</style>