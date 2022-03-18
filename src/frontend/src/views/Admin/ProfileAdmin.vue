<template>
<AdminNavbar :username=email />
  <div>
  
  </div>
 

</template>
 
<script>

import AdminNavbar from './AdminNav.vue'
import axios from "axios";
   export default{
     components: {
    AdminNavbar 
    },
     data(){
       return{
         email: '',
         isPredefined: false,
          userRequestDTO:
            {username: '',}
       
       }
     },
     mounted() {
       this.email = this.$route.params.email
         
      this.userRequestDTO.username=this.email
                 axios.post("http://localhost:8081/admins/isPredefined",this.userRequestDTO)
                  .then(response => {
                      this.isPredefined=response.data
                      return response;
                   })
   

     },
      
    
  }

</script> 

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}
#logincard{
  width: 47%;
  background-image:  url("../../assets/IMG_3872.jpeg"); 
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
