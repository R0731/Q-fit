<template>
  <div class="calendar-cell" v-if="date">
    <div>{{ date }}</div>
    <!-- Pinia에서 받아온 데이터 표시 -->
    <div v-if="hasEvent" class="event-indicator"></div>
    <div v-if="hasTask" class="task-indicator"></div>
    <div v-if="hasNote" class="note-indicator"></div>
  </div>
</template>

<script setup>
import { computed } from 'vue';
import { useCalenderStore } from '../../stores/calender';

const props = defineProps({
  date: Number
});

const store = useCalenderStore();

const hasEvent = computed(() => store.events.includes(props.date));
const hasTask = computed(() => store.tasks.includes(props.date));
const hasNote = computed(() => store.notes.includes(props.date));
</script>

<style scoped>
.calendar-cell {
  height: 100px;
  border: 1px solid #ddd;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  padding: 0.5rem;
}
.event-indicator {
  background-color: purple;
  height: 10px;
  width: 10px;
  border-radius: 50%;
}
.task-indicator {
  background-color: orange;
  height: 10px;
  width: 10px;
  border-radius: 50%;
}
.note-indicator {
  background-color: blue;
  height: 10px;
  width: 10px;
  border-radius: 50%;
}
</style>
