

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import './assets/main.css'
// import axios from 'axios';

import App from './App.vue'
import router from './router'
import { useUserStore } from './stores/user'

const app = createApp(App)


app.use(createPinia())
app.use(router)

// 세션에 저장된 토큰으로 유저 정보 갱신
const userStore = useUserStore();
userStore.loadUserFromToken();

app.mount('#app')