<template>
  <div>
    <nav  class="navbar navbar-fixed-top navbar-expand" style="background-color: #1d7ac9; list-style: none;">
      <div class="container-fluid" style="background-color: #1d7ac9;">
      <a class="navbar-brand"  href="http://localhost:8080/" >
      <img src="../assets/logoF1.png" alt="" width="194" height="80" >
      </a>
    
    
    
      </div>
    
    </nav>

    
    &nbsp;  &nbsp;

   
   <div class="row justify-content-center">
<div class="col-md-6">
<div class="card card-outline-secondary">
<div class="card-header">
    <h3 style="text-align:center;" class="mb-0">Delete account</h3>
</div>
<div class="card-body">
    <form  @submit="editData" method='post' class="was-validated">
        <div class="form-group">
            <label>Reason for deleting account:</label>
                <textarea class="form-control" id="exampleFormControlTextarea1" rows="3" required></textarea>
          
        </div>


        <br>
        
             
             <div class="form-group">
             <button @click="editData()" type="submit" class="btn btn-outline-dark">Save changes</button>
             </div>
             
        
    </form>
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
           email: '',
           role: '',

           userRequestDto: {
            id: 0,
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
            registrationReason: '',
            role: ''
           }
       
       }
     },
     mounted() {
          this.role = this.$route.params.role
          this.email = this.$route.params.email
          this.loadData();
      },
     methods: {
       
       loadData: function(){
           console.log(this.role);
           console.log(this.email);
           this.userRequestDto.username=this.email
                 axios.post("http://localhost:8081/auth/findByEmail/",this.userRequestDto,{
                  headers: {
                  "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
                  "Authorization": "Bearer " + localStorage.jwt ,
                  }
                  })
                 .then(response => {
                        this.userRequestDto = response.data
                        this.userRequestDto.role=this.role
                        return response;
                   })

       },
       editData: function(){
            axios.post("http://localhost:8081/auth/editUser/",this.userRequestDto,{
            headers: {
            "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
            "Authorization": "Bearer " + localStorage.jwt ,
            }
             })
                 .then(response => {
                        return response;
                   })

       },
       changePassword: function(){
            this.$router.push('/changedPassword/'+this.email); 
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
