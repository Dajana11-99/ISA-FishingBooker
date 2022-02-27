<template>
  <div>
    <nav  class="navbar navbar-fixed-top navbar-expand" style="background-color: #1d7ac9; list-style: none;">
      <div class="container-fluid" style="background-color: #1d7ac9;">
      <a class="navbar-brand"  href="http://localhost:8080/" >
      <img src="../assets/logoF1.png" alt="" width="194" height="80" >
      </a>
    
    
    
      </div>
    
    </nav>

    <br>
      <br>
      <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
      <br>
      <br>
      <div class="container">
          <div class="row justify-content-center" >
            <div class="col-md-8">
              <div class="card-group mb-0">
                <div class="card p-4">
                  <div class="card-body">
                    <h1>Login</h1>
                    <p class="text-muted">Sign In to your account</p>
                    <div class="input-group mb-3">
                      <span class="input-group-text"><svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
                      <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
                    </svg></span>
                      <input v-model="LogInDto.username" type="email"  class="form-control" placeholder="Email">
                    </div>
                    <div class="input-group mb-4">
                      <span class="input-group-text"> <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" class="bi bi-lock-fill" viewBox="0 0 16 16">
                      <path d="M8 1a2 2 0 0 1 2 2v4H6V3a2 2 0 0 1 2-2zm3 6V3a3 3 0 0 0-6 0v4a2 2 0 0 0-2 2v5a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2z"/>
                    </svg></span>
                      <input v-model="LogInDto.password" type="password" class="form-control" placeholder="Password">
                    </div>
                    <div style="color: red;" id="greska"></div>
                    <br>
                    <div class="row">
                      <div class="col-3">
                        <button @click="logIn()" type="button" class="btn btn-outline-primary  active">Login</button>
                      </div>
                     
                    </div>
                  </div>
                </div>
                <div id="logincard"  class="card text-white py-5 d-md-down-none" >
                  <div class="card-body text-center">
                    <div>
                      <h2>Sign up</h2>
                      <p>Don't have an account?</p>
                      <button @click="goToSignUp()"  type="button" class="btn btn-outline-light active mt-1">Sign up now!</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

   


      

  </div>

</template>

<script>

import axios from "axios";
   export default{
     data(){
       return{
         LogInDto:{
           username: '',
           password:''
         },
         user:{
           username: ''
         }
         
       }
     },
     methods: {
     logIn: function(){
        if(this.email != '' && this.password != ''){
               axios
               .post("http://localhost:8081/auth/login",this.LogInDto,
               {
          headers: {
            'Access-Control-Allow-Origin': 'http://localhost:8080'
          }
      }).then((response) => {
                 this.user.username=this.LogInDto.username
                  localStorage.setItem("jwt", response.data.accessToken);
            
                  if(response.data.userType==='ADMIN'){
                        axios.post("http://localhost:8081/account/passwordStatus",this.user,{
                          headers: {
                          "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
                          "Authorization": "Bearer " + localStorage.jwt ,
                          }
                        }).then(response => {
                            if(response.data==true){
                            this.$router.push('/profileAdmin/'+this.LogInDto.username);
                        }
                        else if(response.data==false) {
                            this.$router.push('/changedPasswordInfo/'+this.LogInDto.username);
                        }
                   })   
                  }
                  else if(response.data.userType==='BOAT OWNER')
                     this.$router.push('/boatOwnerHome/'+this.LogInDto.username);
                  else if(response.data.userType==='CABIN OWNER')
                     this.$router.push('/cabinOwnerHome/'+this.LogInDto.username);
                  else if(response.data.userType==='FISHING INSTRUCTOR')
                     this.$router.push('/fishingInstructorHome/'+this.LogInDto.username);

                   return response; 
               });
        }

      },
      goToSignUp: function() {
           this.$router.push('/signup');
      },
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
  background-image:  url("../assets/IMG_3872.jpeg"); 
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
