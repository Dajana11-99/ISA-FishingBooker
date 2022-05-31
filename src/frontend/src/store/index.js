import { createStore } from 'vuex'
import axios from 'axios'
import router from '../router'
const store = createStore({
    state: {
        token: null,
        role: null
    },
    getters:{
        getToken : (state) => state.token,
        getRole : (state) => state.role,
    },
    mutations: {
        SET_TOKEN (state,token){
            state.token = token.accessToken
        },
        SET_ROLE (state,token){
            state.role = token.userType
        },
    },
    actions: {

       async logIn ({dispatch},credentials){
           try {
            let response =  await axios.post(process.env.VUE_APP_BACKEND_URL+"auth/login",credentials)
                localStorage.clear();
                localStorage.setItem('token',response.data.accessToken)
                localStorage.setItem('role',response.data.userType)
                localStorage.setItem('logged',true)
                axios.defaults.headers['Authorization']="Bearer "+ localStorage.token
                

               dispatch('attempt',response.data)

               return  response
           } catch (error) {

            
                console.log(error)
           }
            
        
        },
        async attempt ({commit},token){
            try {
                commit('SET_TOKEN',token)
                commit('SET_ROLE',token)
            } catch (error) {
                commit('SET_TOKEN',null)
            }
         
        },
        async logOut (){
            localStorage.setItem('token','empty')
            localStorage.removeItem('role');
            localStorage.setItem('logged',false)
        },
        
       async refreshToken (){
           try{
                
            let response = await axios.post(process.env.VUE_APP_BACKEND_URL+"auth/refresh")
            localStorage.clear()
            localStorage.setItem('token',response.data.accessToken)
            localStorage.setItem('logged',true)
            localStorage.setItem('role',response.data.userType.toUpperCase())
            axios.defaults.headers['Authorization']="Bearer "+ localStorage.token
                
           }
           catch{
            console.log("ISTEKAOOO TI TOKEEEN")
            localStorage.setItem('logged',false)
            localStorage.setItem('token','empty')
            localStorage.removeItem('role')
            router.push("/")

           }
          
          
            
        },
      
    },
    modules: {
         
    }
  
})
export default 
    store

