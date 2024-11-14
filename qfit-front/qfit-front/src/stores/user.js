import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_API_URL = `http://localhost:8080/user`
export const useUserStore = defineStore('user', () => {
  const loginUser = ref(null);

  const userLogin = async(id, password)=>{
    // console.log(id, password);
    try{
      const res = await axios.post(`${REST_API_URL}/login`,{
        userId: id,
        userPassword: password,
      });

      console.log(res.data);

      sessionStorage.setItem('access-token', res.data['access-token'])

      const token = res.data['access-token'].split('.')
      const userId = JSON.parse(atbo(token[1]))
      const name = JSON.parse(atbo(token[2]))
      const userType = JSON.parse(atbo(token[3]))
      loginUser.value = {userId:userId, name:name, userType, userType}
      
      await router.push({name: 'traineeList'})

    } catch(err){
      console.log(err)
      await router.push({name: 'trainerLogin'})

    }
  }
  return { loginUser, userLogin }
})
