<template>
<AdminNavbar :username=email />
  <div>
    &nbsp;  &nbsp;
<h1>Enter reason for denying {{this.user.email}}'s request: </h1>
&nbsp;    

<div class="form-floating" style="margin-top: 3%;
  margin-bottom: 10%;
  margin-right: 10%;
  margin-left: 10%;">
  
  <textarea v-model="reason" class="form-control" placeholder="Leave a comment here" id="floatingTextarea2" style="height: 300px"></textarea>
  <label for="floatingTextarea2">Reason for denying registration:</label>
  
  
  &nbsp;
  <div class="d-grid gap-2">
 <button @click="deny()" class="btn btn-outline-primary" type="button">Send email</button>
  </div>
</div>

      
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
           person: '',
           user:
            {
               
               username: '',
               firstname: '',
               lastname: '',
               registrationReason: '',
               role: '',
           }
       
       }
     },
     mounted() {
          this.email = this.$route.params.email
           this.user.username = this.$route.params.person
          
      },
     methods: {
        users: function(){
           this.$router.push('/allUsers/'+this.email);
       },
       requests: function(){
            this.$router.push('/requests/'+this.email);
       },
       myProfile: function(){
           this.$router.push('/editProfile/'+'ADMIN/'+this.email);
       },
       logout: function(){
          this.$router.push('/');
       },
       home: function(){
           this.$router.push('/profileAdmin/'+this.email);
       },
        addAdmin: function(){
          this.$router.push('/addAdmin/'+ this.email);
       },
       deny: function(){
                  
                  axios.post(process.env.VUE_APP_BACKEND_URL+"account/denyAccount/"+this.reason,this.user)
                  .then(response => {
                      this.$router.push('/requests/'+this.email);
                      return response;
                   })
                 .catch(error => {
                  this.errorMessage = error.message;
                   console.error("There was an error!", error);
                  });

       }
       
      
    }
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
