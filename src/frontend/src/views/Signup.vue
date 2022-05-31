<template>
  <div id="mainc">
    <nav  class="navbar navbar-fixed-top navbar-expand" style="background-color: #1d7ac9; list-style: none;">
      <div class="container-fluid" style="background-color: #1d7ac9;">
      <a class="navbar-brand" href="http://localhost:8080/">
      <img src="../assets/logoF1.png" alt="" width="194" height="80" >
      </a>
      </div>
    </nav>

   <br>
      <div class="container"  >
      
      <div class="row justify-content-center"  >
      <div class="col-md-6" >
      <div class="card" >
      <header id="header" class="card-header" >
          <h4 class="card-title mt-2">Sign up</h4>
      </header>
      <article class="card-body"  >
      <form @submit="register" method='post' class="was-validated">
          <div class="form-row">
              <div class="col form-group">
                  <label id="label">First name </label>   
                    <input v-model="user.firstname" type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
              </div> 
              <div class="col form-group">
                  <label id="label">Last name</label>
                    <input   v-model="user.lastname" type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
              </div> 
          </div>
          <div class="form-group">
              <label id="label">Email</label>
              <input  v-model="user.username" type="email" class="form-control" placeholder="someone@gmail.com"   required>
              <div class="valid-feedback">Valid.</div>
              <div class="invalid-feedback">Please fill out this field.</div>
          </div>
          <div class="col form-group">
          <label id="label">Password </label>   
            <input  v-model="user.password" type="password" class="form-control"   required>
            <div class="valid-feedback">Valid.</div>
            <div class="invalid-feedback">Please fill out this field.</div>
      </div> 
          <div class="col form-group">
          <label id="label">Confirm password </label>   
            <input v-model="confirmPassword" type="password" class="form-control"   required>
            <div class="valid-feedback">Valid.</div>
            <div class="invalid-feedback">Please fill out this field.</div>
      </div> 

       <div class="col form-group">
                  <label id="label">Street and number </label>   
                    <input  v-model="user.address.streetAndNum"  type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
        </div> 

        <div class="col form-group">
                  <label id="label">City </label>   
                    <input  v-model="user.address.city"  type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
         </div> 

        <div class="col form-group">
                  <label id="label">Country </label>   
                    <input   v-model="user.address.country" type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
        </div> 

        <div class="col form-group">
                  <label id="label">Phone number </label>   
                    <input v-model="user.phoneNum"  type="text" class="form-control" required>
                    <div  class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
        </div> 

        <div>

        <label id="label">Choose account type</label>   
        <select v-model="selectedClient" class="form-select form-select-sm" aria-label=".form-select-sm example" required>
                <option >CLIENT</option>
                <option >CABIN OWNER</option>
                <option >BOAT OWNER</option>
                <option >FISHING INSTRUCTOR</option>
        </select>
             </div>

   <div v-if="selectedClient != 'CLIENT'"  class="form-group">
    <label for="exampleFormControlTextarea1">Registration reason</label>
    <textarea  v-model="user.registrationReason" class="form-control" id="exampleFormControlTextarea1" rows="3" required></textarea>
  </div>
   
          <br>
          <div class="form-row">
          <div class="row">
          <div class="col-12">
 
  
  </div>
      </div>
          </div> 
         
          <div style="color:red;" v-if="notMatching==true"><label>Passwords are not matching!</label></div>
           <div style="color:red;" v-if="flag==true"><label>Email already in use.</label></div>
        
          <br>

          <div class="form-group">
              <button type="submit" class="btn btn-primary"> Sign up  </button>
          </div>                                                
      </form>
      </article> 
      <div class="border-top card-body text-center" >Have an account? <a href="http://localhost:8080/login">Log In</a></div>
      </div> 
      </div> 
      
      </div> 
      
      
      </div> 
     




  </div>

</template>

<script>

import axios from "axios";

export default {
  data() {
    return {
       selectedClient: 'CLIENT',
       confirmPassword: '',
       notMatching: false,
       existEmail:'',
       flag: false,
       user: {
         id: null,
         username:'',
         password: '',
         firstname: '',
         lastname: '',
         phoneNum: '',
         address: {
              longitude: 0,
             latitude: 0,
             country: '',
             city: '',
           streetAndNum: ''
         },
         registrationReason: ''
        

       },
       loader: null
    };
  },
  mounted() {
    
  },
  methods: {
    register: function(event) {
      event.preventDefault();
        if(this.confirmPassword === this.user.password){
        if(this.selectedClient === 'CABIN OWNER'){
               axios
               .post(process.env.VUE_APP_BACKEND_URL+"auth/signUpCabinOwner",this.user)
               .then((response) => {
                 console.log("RESPONSEE"+response.data)
                 if(response.data =='Email already in use.'){
                   this.flag= true
                 }else {
                      this.$router.push('/accountAlert/'+this.user.username);
                 }
                 
                 return response;
                
               })
            
        }else if(this.selectedClient === 'BOAT OWNER'){
               axios
               .post(process.env.VUE_APP_BACKEND_URL+"auth/signUpBoatOwner",this.user)
               .then((response) => {
                   this.$router.push('/accountAlert/'+this.user.username);
                    return response;
               })

        }else if(this.selectedClient === 'FISHING INSTRUCTOR'){
              axios
               .post(process.env.VUE_APP_BACKEND_URL+"auth/signUpFishingInstructor",this.user)
               .then((response) => {
                   this.$router.push('/accountAlert/'+this.user.username);
                    return response;
               })

        }else if(this.selectedClient === 'CLIENT'){
              axios
               .post(process.env.VUE_APP_BACKEND_URL+"auth/signUpClient",this.user)
               .then((response) => {
                   this.$router.push('/clientAccountAlert/'+this.user.username);
                    return response;
               })

        }
        }else{
             this.notMatching=true;
        }
    }
  }
};
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

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#label{
  float: left;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
#mainc{

  background-image: linear-gradient(#278ade,black);
}

#password
{
color:red
}

</style>
