<template>
    <div>
        <h2>내 회원 목록</h2>
        <button @click="goToUpdate">회원추가</button>
        <button @click="goToDelete">회원삭제</button>
        <br>
        <ul>
            <li v-for="(trainee, index) in trainees" :key="index">
                <span>{{ trainee.userName }} {{ trainee.age }}세</span>
            </li>
        </ul>
        <RouterView/>
    </div>
</template>

<script setup>
import { useTrainerStore } from "@/stores/trainer";
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from "@/stores/user";
import { useTraineeStore } from "@/stores/trainee";

const router = useRouter();
const goToUpdate = ()=>{
    router.push({ name: 'MyTraineesUpdate' })
}
const goToDelete = ()=>{
    router.push({ name: 'MyTraineesDelete' })
}

const userStore = useUserStore();
const trainerId = userStore.loginUser.numberId;

const traineeStore = useTraineeStore();
// const trainees = ref([]);

onMounted(()=>{
  traineeStore.fetchTraineeList(trainerId)
});

const trainees = traineeStore.trainees;

// const id = ref('1');
// const goQuest = (date, id) => {
//   router.push({name: 'quest', params:{date, id}});
// }
</script>

<style scoped>

</style>