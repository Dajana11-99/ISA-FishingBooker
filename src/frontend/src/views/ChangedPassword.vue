<template>
  <div >
    <nav  class="navbar navbar-fixed-top navbar-expand" style="background-color: #1d7ac9; list-style: none;">
      <div class="container-fluid" style="background-color: #1d7ac9;">
      <a class="navbar-brand" href="http://localhost:8080/">
      <img src="../assets/logoF1.png" alt="" width="194" height="80" >
      </a>
      </div>
    </nav>

   <br>
   <br>
   <div class="alert alert-primary" style="margin-top: 3%;
  margin-bottom: 10%;
  margin-right: 10%;
  margin-left: 10%;" role="alert">
  
       <div class="container"  >
      
      <div class="row justify-content-center"  >
      <div class="col-md-6" >
      <div class="card" >
      <header id="header" class="card-header" >
          <h4 class="card-title mt-2">Change password</h4>
      </header>
      <article class="card-body"  >
      <form @submit="changePassword" method='post' class="was-validated">
          <div class="form-row">
              <div class="col form-group">
                  <label id="label">Old password</label>   
                    <input v-model="changePasswordDto.oldPassword" type="password" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
              </div> 
              
              <div class="col form-group">
                  <label id="label">New password</label>
                    <input v-model="changePasswordDto.newPassword"   type="password" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
              </div> 

                <div class="col form-group">
                  <label id="label">Confirm password</label>
                    <input v-model="confirmPassword" type="password" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
              </div> 
          </div>
 

      

  
   
          <br>
          <div class="form-row">
          <div class="row">
          <div class="col-12">
 
  
  </div>
      </div>
          </div> 
         
          <div style="color:red;"></div>
           <label id="password" v-if="passwordsArentMatching==true" >Passwords are not matching!</label>
          <br>
           <br>
          <div class="form-group">
              <button type="submit" class="btn btn-primary"> Confirm change </button>
          </div>                                                
      </form>
      </article> 
  
      </div> 
      </div>
      </div> 
      
      
      </div> 
     
   </div>



   

  
  </div>

</template>

<script>
import axios from 'axios';



export default {
  data() {
    return {
       email: '',
       passwordsArentMatching: false,
       confirmPassword: '',
       changePasswordDto:{
           oldPassword: '',
           newPassword: ''
       },
      
    };
  },
  mounted() {
    this.email = this.$route.params.email
  },
  methods: {
    changePassword: function(event){
         event.preventDefault();
         this.changePasswordDto.username=this.email
         
                      if(this.confirmPassword===this.changePasswordDto.newPassword){
                         axios
                         .post("http://localhost:8081/auth/changePassword",this.changePasswordDto,{
                          headers: {
                          "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
                          "Authorization": "Bearer " + localStorage.jwt ,
                          }
                          })
                         .then((response) => {
                              this.$router.push('/login');
                              return response
                          })
                       }else {
                          this.passwordsArentMatching=true

                       }
               


    },
  }
};
</script>


<style>
#password
{
color:red
}
</style>
