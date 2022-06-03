<template>
<AdminNavbar :username=email />
    
  <div>
     
<h1>ALL REPORTS </h1>
&nbsp;  
<h2 v-if="reports.length == 0" > No reports.</h2>
<table v-else class="table">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">Owner</th>
      <th scope="col">Comment</th>
      <th scope="col">Client</th>
      <th scope="col">Suggest client gets 1 penal</th>
       <th>&nbsp;</th>
      

    </tr>
  </thead>
   
  <tbody>
    <tr  v-for="(rep,index) in reports" :key="index">
       <th scope="row">{{index +1}}</th>
      <td>{{rep.ownersUsername}}</td>
      <td>{{rep.comment}}</td>
      <td>{{rep.clientUsername}}</td>
      <td v-if="rep.badComment == true">YES</td>
      <td>
        <tr>
         <div class="row">
            <div class="col "><button @click="answer(rep)"
               type="button" class="btn btn-outline-success" >ANSWER</button></div>
     
        </div>
       
        </tr>          
                  
      </td>
      
      
    </tr>
  </tbody>
</table>


  </div>
 
<vue-modality ref="sendResponse" title="Send email" hide-footer centered width="600px">

         <div class="row">
         <div class="col">
        <div class="row" style="padding-bottom: 1%;">
          <div class="col-sm-3" style="padding-top: 1%; text-align: left; color: gray;">
            <p>Text message</p>
          </div>
        </div>
        <div class="row" style=" padding-left: 3%; padding-right: 3%; height: 40%;">
                 <textarea style="height: 150px" v-model="comment" required ></textarea>
               
        </div>
        
          
       
  <br>
    <br>
      <br>
         

        
         <div class="row">
           <div style="padding-top: 1%; text-align: left; color: gray;">
                 <p>Penal for the client</p>
           </div>
           
          
             <div class="col">
                    <select
                    style="color: #5f7280;"
                    v-model="selectedPenalty"
                    class="form-select "
                    aria-label="Default select example"
                    placeholder="Rating"
                  >
                    <option v-bind:value="0">YES</option>
                    <option v-bind:value="1">NO</option>
                   </select>
             </div>
         </div>



            
      </div>


  
                 <label style="color: red; text-align: right;" v-if="comment==='' || selectedPenalty==null">Please fill in all fields.</label>
      
  </div>
  <hr>
  <button type="button" @click="sendEmail()" class="btn btn-success">Send</button>
</vue-modality>

</template>
 
<script>
//import axios from "axios";

import AdminNavbar from './AdminNav.vue'
import axios from 'axios'

import VueModality from 'vue-modality-v3'

   export default{
    components: {
    AdminNavbar ,
    VueModality
    },
     data(){
       return{
         email: '',
         reports: [],
         selectedPenalty: null,
         comment: '',
         id: null,
         clientUsername: '',
         ownersUsername: ''
       
       }
     },
     mounted() {
       this.email = this.$route.params.email
         this.getAllReports()
     },
     methods: {
       getAllReports: function(){
               axios.get(process.env.VUE_APP_BACKEND_URL+"reports/getAllReports")
            .then(response => {this.reports = response.data
              
              })
             .catch(error => {
                 this.errorMessage = error.message;
                 console.error("There was an error!", error);
           });
       },
       answer: function(res){
         console.log("aaa"+res)
         this.id= res.id;
         this.clientUsername=res.clientUsername,
         this.ownersUsername=res.ownersUsername
           this.$refs.sendResponse.open()
       },
       sendEmail: function(){
         var giveClientPenalty = false;
         if(this.selectedPenalty ==0){
           giveClientPenalty = true;
         }
                 this.loader = this.$loading.show({
                    container: this.fullPage ? null : this.$refs.formContainer,
                    canCancel: true,
                    onCancel: this.onCancel,
                   });
               axios.post(process.env.VUE_APP_BACKEND_URL+"reports/sendReviewResponse",{
                     id:this.id,
                    comment: this.comment,
                    ownersUsername: this.ownersUsername,
                    clientUsername: this.clientUsername,
                    success: giveClientPenalty
               })
            .then(response => 
            {
                  console.log("AAA"+response)
                   this.$refs.sendResponse.hide()
                       this.$swal.fire({
                                          position: 'top-end',
                                          icon: 'success',
                                          title: 'Email has been successfully sent to client and owner!',
                                          showConfirmButton: false,
                                           timer: 2500
                                           
                                       })
                                       this.comment= ''
                                       this.selectedPenalty= null
                                      this.loader.hide();
                                       this.loader=null
                                       this.getAllReports()

            }
              )
             .catch(error => {
               
                this.comment= ''
                 this.selectedPenalty= null
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
