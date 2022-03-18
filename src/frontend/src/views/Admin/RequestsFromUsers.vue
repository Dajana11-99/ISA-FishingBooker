<template>
<AdminNavbar :username=email />
  <div>   
    &nbsp;  &nbsp;
<h1>USER REQUESTS </h1>
&nbsp;  
<h2 v-if="!userRequestDTO.length"> No requests.</h2>
<table v-else class="table">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">Role</th>
      <th scope="col">First name</th>
      <th scope="col">Last name</th>
      <th scope="col">Email</th>
      <th scope="col">Registration reason</th>
       <th>&nbsp;</th>
      

    </tr>
  </thead>
   
  <tbody>
    <tr  v-for="(user,index) in userRequestDTO" :key="index">
       <th scope="row">{{index +1}}</th>
      <td>{{user.role}}</td>
      <td>{{user.firstname}}</td>
      <td>{{user.lastname}}</td>
      <td>{{user.username}}</td>
       <td>{{user.registrationReason}}</td>
      <td>
        <tr>
        <button @click="accept(user)"  type="button" class="btn btn-outline-success" >ACCEPT</button>
        <button @click="redirectDeny(user)"   type="button" class="btn btn-outline-danger" >DENY</button>
       
        </tr>          
                          
      
      
      
      
      
      </td>
      
      
    </tr>
  </tbody>
</table>
    
    

  
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
           denyClick: false,
           isPredefined: false,
           userRequestDTO:[
            {
               
               username: '',
               firstname: '',
               lastname: '',
               registrationReason: '',
               role: '',
           }],
              pomUserRequestDTO:
            {username: '',}
       
       }
     },
     mounted() {
  // GET request using axios with error handling
          
          this.email = this.$route.params.email
       this.pomUserRequestDTO.username=this.email
          this.loadData();
            axios
               .post("http://localhost:8081/admins/isPredefined",this.pomUserRequestDTO)
               .then((response) => {
                  this.isPredefined=response.data;

                   return response; 
               });
   
      

      },
     methods: {
       loadData: function(){
             axios.get("http://localhost:8081/userc/getNewUsers")
            .then(response => {this.userRequestDTO = response.data
              
              })
             .catch(error => {
                 this.errorMessage = error.message;
                 console.error("There was an error!", error);
           });
       },
       accept: function(user){
                  axios.post("http://localhost:8081/account/acceptAccount",user)
                  .then(response => {this.userRequestDTO = response.data
                        this.loadData();

                   })
                 .catch(error => {
                  this.errorMessage = error.message;
                   console.error("There was an error!", error);
                  });
       },
       deny: function(user){
                  this.denyClick=true;
                  axios.post("http://localhost:8081/account/denyAccount/"+this.reason,user)
                  .then(response => {this.userRequestDTO = response.data
                        this.loadData();
                   })
                 .catch(error => {
                  this.errorMessage = error.message;
                   console.error("There was an error!", error);
                  });
       },
       redirectDeny: function(user){
         this.$router.push('/reasonForDenying/'+this.email+"/"+user.username);
                  
       },
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
        deleteAccount: function(){
            this.$router.push('/deletingAccountRequests/'+ this.email);
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
