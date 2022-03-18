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

   
   <div class="row justify-content-center" style="width: 100%;">
<div class="col-md-6">
<div class="card card-outline-secondary">
<div class="card-header">
    <h3 style="text-align:center;" class="mb-0">Send request for deleting account</h3>
</div>
<div class="card-body">
        <div  style="text-align: left;" class="form-group">
            <label>Reason for deleting account:</label>
                <textarea v-model="userRequestDto.reasonForDeleting" class="form-control" id="exampleFormControlTextarea1" rows="3" ></textarea>
               <label v-if="userRequestDto.reasonForDeleting===''" style="color: red;">Please enter reason for deleting account.</label>
        </div>


        <br>
        
             
             <div class="form-group"  style="text-align: right;">
             <button data-bs-toggle="modal" data-bs-target="#staticBackdrop"  class="btn btn-outline-dark">Confirm</button>
             </div>
             
        
</div>
</div>
</div>
</div>


<!-- Modal -->
<div v-if="userRequestDto.reasonForDeleting!=''"  class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="staticBackdropLabel"></h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <p>Are you sure you want to send this request?</p>

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button @click="sendRequest()" type="button" data-bs-dismiss="modal" class="btn btn-danger">Confirm</button>
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
            role: '',
            reasonForDeleting: '',
           },
       
       }
     },
     mounted() {
          this.email = this.$route.params.email
          this.userRequestDto.username=this.email
      },
     methods: {
       sendRequest: function(){
            if(this.userRequestDto.reasonForDeleting===""){
                this.showLabel=true;
            }else{
            axios.post("http://localhost:8081/auth/saveDeleteAccountRequest",this.userRequestDto)
            .then(response => {
                        this.$swal.fire(
                          'Request sent!',
                          'Your request for deleting account has been successfully sent to admin! You will be informed via email about the state of your account.',
                          'success'
                         )
                        this.$router.push('/'); 
                        return response;
            })
            }

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
