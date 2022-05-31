<template>
<AdminNavbar :username=email />
  <div>
  
<h1>ALL REQUESTS </h1>
&nbsp;  
<h2 v-if="userRequestDTO.length==0"> No requests.</h2>
<table v-else class="table">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">Role</th>
      <th scope="col">First name</th>
      <th scope="col">Last name</th>
      <th scope="col">Username</th>
      <th scope="col">Reason for deleting account</th>
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
       <td>{{user.reasonForDeleting}}</td>
      <td>
        <tr>
         <div class="row">
            <div class="col "><button @click="redirectAccept(user)" 
               type="button" class="btn btn-outline-success"  data-bs-toggle="modal" data-bs-target="#staticBackdrop2" >ACCEPT</button></div>
          <div class="col">   <button @click="redirectDeny(user)" data-bs-toggle="modal" data-bs-target="#staticBackdrop"  type="button" class="btn btn-outline-danger" >DENY</button></div>
     
        </div>
       
        </tr>          
                          
      
      
      
      
      
      </td>
      
      
    </tr>
  </tbody>
</table>




<!-- Modal for deny -->
<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="staticBackdropLabel"></h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
     <div  style="text-align: left;" class="form-group">
            <label>Reason for denying request:</label>
                <textarea v-model="reasonDeny" class="form-control" id="exampleFormControlTextarea1" rows="3" ></textarea>
               <label v-if="reasonDeny===''" style="color: red;">Please enter reason for denying request.</label>
        </div>

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button @click="denyDelete()" type="button" data-bs-dismiss="modal" class="btn btn-success">Send email</button>
      </div>
    </div>
  </div>
</div>






<!-- Modal for accept -->
<div class="modal fade" id="staticBackdrop2" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="staticBackdropLabel"></h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
     <div  style="text-align: left;" class="form-group">
            <label>Reason for accept request:</label>
                <textarea v-model="reasonAccept" class="form-control" id="exampleFormControlTextarea1" rows="3" ></textarea>
               <label v-if="reasonAccept===''" style="color: red;">Please enter reason for accept request.</label>
        </div>

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button @click="acceptDelete()" type="button" data-bs-dismiss="modal" class="btn btn-success">Send email</button>
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
          userRequestPom:
            {username: '',},
             userRequestDTO:[
            {
               
               username: '',
               firstname: '',
               lastname: '',
               role: '',
               reasonForDeleting: ''
           }],
               userRequest:
            {
               
               username: '',
               firstname: '',
               lastname: '',
               role: '',
               reasonForDeleting: ''
           },
           reasonDeny: '',
           reasonAccept: '',
           mailDto: {
               response: '',
               recipient: ''
           }
       
       }
     },
     mounted() {
       this.email = this.$route.params.email
       this.getAllRequests();
     },
     methods: {
     
       getAllRequests: function(){
              axios.get(process.env.VUE_APP_BACKEND_URL+"userc/getAllRequests")
                  .then(response => {
                      this.userRequestDTO=response.data
                     
                   })
       },
       denyDelete: function(){
         this.loader = this.$loading.show({
                    container: this.fullPage ? null : this.$refs.formContainer,
                    canCancel: true,
                    onCancel: this.onCancel,
                   });
           this.mailDto.response= this.reasonDeny;
           this.mailDto.recipient= this.userRequest.username;
             axios.post(process.env.VUE_APP_BACKEND_URL+"account/sendDenyReasonForDeletingAccount",this.mailDto)
                  .then(response => {
                      this.$swal('User has been deleted');
                         this.$swal.fire(
                            'Delete account!',
                            'User has been deleted!',
                            'success'
                         )
                      this.loader.hide();
                      this.loader=null
                      this.getAllRequests();
                    return response;
                     
                   }).catch(error => {
                 console.log(error)
                   this.$swal.fire({
                  icon: "error",
                  title: "Something went wrong!",
                  text: error.response.data,
          });
                 this.loader.hide();
                 this.loader=null
                 this.getAllRequests();
              });


       },
       redirectDeny: function(user){
           this.userRequest=user
       },
       redirectAccept: function(user){
             this.userRequest=user
       },
       acceptDelete: function(){
            this.loader = this.$loading.show({
                    container: this.fullPage ? null : this.$refs.formContainer,
                    canCancel: true,
                    onCancel: this.onCancel,
                   });
           this.mailDto.response= this.reasonAccept;
           this.mailDto.recipient= this.userRequest.username;
            axios.post(process.env.VUE_APP_BACKEND_URL+"account/sendAcceptReasonForDeletingAccount",this.mailDto)
                     .then(response => {
                        this.$swal('User has been deleted');
                         this.$swal.fire(
                            'Delete account!',
                            'User has been deleted!',
                            'success'
                         )
                          this.loader.hide();
                          this.loader=null
                           this.getAllRequests()

                        return response
                     
                   })  .catch(error => {
                 console.log(error)
                   this.$swal.fire({
                  icon: "error",
                  title: "Something went wrong!",
                  text: error.response.data,
          });
                 this.loader.hide();
                 this.loader=null
                  this.getAllRequests()
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
