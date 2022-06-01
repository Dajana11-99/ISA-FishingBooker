<template>
<AdminNav :username=email />
 <div>
<br>
<h1>ALL COMPLAINTS </h1>
&nbsp;  
<h2  v-if="complaints.length ==0"> No complaints.</h2>
<table v-else class="table">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">Complaint type</th>
      <th scope="col">Client</th>
      <th scope="col">Comment</th>
      <th scope="col">Date</th>
      <th scope="col">Owner</th>
       <th>&nbsp;</th>
      

    </tr>
  </thead>
   
  <tbody>
    <tr  v-for="(rep,index) in complaints" :key="index">
       <th scope="row">{{index +1}}</th>
      <td>{{rep.complaintType}}</td>
      <td>{{rep.clientUsername}}</td>
      <td>{{rep.text}}</td>
      <td>{{setandFormatDate(rep.date)}}</td>
      <td>{{rep.ownersUsername}}</td>
      <td>
        <tr>
         <div class="row">
            <div class="col "><button @click="accept(rep)"
               type="button" class="btn btn-outline-success" >SEND MAIL</button>       
        </div>
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
      </div>


  
                 <label style="color: red; text-align: right;" v-if="comment===''">Please fill in all fields.</label>
      
  </div>
  <hr>
  <button type="button" @click="sendEmail()" class="btn btn-success">Send</button>
</vue-modality>
</template>

<script>
import AdminNav from './AdminNav.vue'
import VueModality from 'vue-modality-v3'
import dayjs from 'dayjs';
import axios from 'axios'
   export default{
     components:{
       AdminNav,
       VueModality
       
     },
     data(){
       return{
         email: '', 
         complaints: [],
         comment: '',
        complaintId: null

       }
     },
     mounted() {
       this.email = this.$route.params.email
        this.getAllComplaints();
     },
     methods: {

            getAllComplaints: function(){
                    axios.get(process.env.VUE_APP_BACKEND_URL+"complaints/getAll")
            .then(response => {this.complaints = response.data
              
              })
             .catch(error => {
                 this.errorMessage = error.message;
                 console.error("There was an error!", error);
           });
         },
           setandFormatDate: function(newDate){
           var date= new Date()
           var splits =newDate.toString().split(",")
           date.setDate( splits[1],splits[2], splits[0])
           var newData= new Date( parseInt(splits[0]), parseInt(splits[1])-1, parseInt(splits[2]),parseInt(splits[3]),parseInt(splits[4]))
            const dateee = dayjs(newData);
           return dateee.format('YYYY-MM-DD HH:mm:ss');
           },
        accept: function(res){
            console.log("asas"+res)
            this.complaintId = res.id;
             this.$refs.sendResponse.open();
        },
        sendEmail: function(){
          
                  this.$refs.sendResponse.hide();
                  this.loader = this.$loading.show({
                    container: this.fullPage ? null : this.$refs.formContainer,
                    canCancel: true,
                    onCancel: this.onCancel,
                   });
                   console.log("AAAA"+this.comment)
                    axios.post(process.env.VUE_APP_BACKEND_URL+"complaints/answerComplaint/"+ this.complaintId,this.comment)
            .then(response => {
                 console.log(response)
                 this.$swal.fire({
                                          position: 'top-end',
                                          icon: 'success',
                                          title: 'Email successfully sent!',
                                          showConfirmButton: false,
                                           timer: 2500
                                       })
                                         this.loader.hide();
                                         this.loader=null
                                         this.comment = ""
                                         this.getAllComplaints();

              })
             .catch(error => {
                  console.log(error)
                   this.$swal.fire({
                  icon: "error",
                  title: "Something went wrong!",
                  text:" Complaint has already been reviewed!",
          });
                  this.loader.hide();
                  this.loader=null
                  this.comment = ""
                  this.getAllComplaints();

              });

        }
     
     },
   
    computed: {

  },
  }

</script> 

<style>

</style>
