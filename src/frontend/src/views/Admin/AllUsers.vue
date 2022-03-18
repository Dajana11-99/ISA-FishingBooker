<template>
<AdminNavbar :username=email />

  <div>
    
<h1>ALL ACTIVE USERS </h1>
&nbsp;  
<h2 v-if="!userRequestDTO.length"> No requests.</h2>
<table v-else class="table">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">Role</th>
      <th scope="col">First name</th>
      <th scope="col">Last name</th>
      <th scope="col">Username</th>
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
         <div class="row">
            <div class="col "><button @click="setUser(user)" data-bs-toggle="modal" data-bs-target="#staticBackdrop"
               type="button" class="btn btn-outline-danger" >DELETE</button></div>
          <div class="col">   <button @click="redirectDeny(user)"   type="button" class="btn btn-outline-danger" >BLOCKED</button></div>
     
        </div>
       
        </tr>          
                  
      </td>
      
      
    </tr>
  </tbody>
</table>



<!-- Modal -->
<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="staticBackdropLabel"></h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <p>Are you sure you want to delete user?</p>

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button @click="deleteUser()" type="button" data-bs-dismiss="modal" class="btn btn-danger">Delete</button>
      </div>
    </div>
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
         isPredefined: false,
           userRequestDTO:[
            {
               
               username: '',
               firstname: '',
               lastname: '',
               role: '',
           }],
               pomUserRequestDTO:
            {username: '',},
                 selectedUser:
            {
               
               username: '',
               firstname: '',
               lastname: '',
               role: '',
           },
       
       }
     },
     mounted() {

       this.email = this.$route.params.email
    this.pomUserRequestDTO.username=this.email
         axios.get("http://localhost:8081/userc/getAllUsers")
            .then(response => {this.userRequestDTO = response.data
              
              })
             .catch(error => {
                 this.errorMessage = error.message;
                 console.error("There was an error!", error);
           });

          axios
               .post("http://localhost:8081/admins/isPredefined",this.pomUserRequestDTO)
               .then((response) => {
                  this.isPredefined=response.data;

                   return response; 
               });
   
   

     },
     methods: {
       setUser: function(user){
           this.selectedUser= user;
       },
       deleteUser: function(){
            
             axios
               .post("http://localhost:8081/userc/deleteUser",this.selectedUser)
               .then((response) => {
                this.$router.go();
                   return response; 
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
