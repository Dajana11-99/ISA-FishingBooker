<template>
<AdminNav :username=email />

 <div>
     
<h1>ALL EVALUATIONS </h1>
&nbsp;  
<h2  v-if="evaluations.length ==0"> No evaluations.</h2>
<table v-else class="table">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">Evaluation type</th>
      <th scope="col">Client</th>
      <th scope="col">Comment</th>
      <th scope="col">Date</th>
      <th scope="col">Grade</th>
      <th scope="col">Owner</th>
       <th>&nbsp;</th>
      

    </tr>
  </thead>
   
  <tbody>
    <tr  v-for="(rep,index) in evaluations" :key="index">
       <th scope="row">{{index +1}}</th>
      <td>{{rep.type}}</td>
      <td>{{rep.clientUsername}}</td>
      <td>{{rep.comment}}</td>
      <td>{{setandFormatDate(rep.date)}}</td>
      <td>{{rep.grade}}</td>
      <td>{{rep.ownersUsername}}</td>
      <td>
        <tr>
         <div class="row">
            <div class="col "><button @click="accept(rep)"
               type="button" class="btn btn-outline-success" >APPROVE</button>
               
            </div>

               <div class="col "><button @click="deny(rep)"
               type="button" class="btn btn-outline-danger" >DISAPPROVE</button>
               </div>
               
   
               
        </div>
       
       
        </tr>          
                  
      </td>
      
      
    </tr>
  </tbody>
</table>


  </div>

   
</template>

<script>
import AdminNav from './AdminNav.vue'
import axios from 'axios'
import dayjs from 'dayjs';

   export default{
     components:{
       AdminNav,
     },
     data(){
       return{
         email: '',  
         evaluations: [],
         id: null,

       }
     },
     mounted() {
       this.email = this.$route.params.email

       this.getAllEvaluations();

     },
     methods: {
         getAllEvaluations: function(){
                    axios.get(process.env.VUE_APP_BACKEND_URL+"evaluations/getAllEvaluations")
            .then(response => {this.evaluations = response.data
              
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
           this.id= res.id;
               this.loader = this.$loading.show({
                    container: this.fullPage ? null : this.$refs.formContainer,
                    canCancel: true,
                    onCancel: this.onCancel,
                   });
                    axios.get(process.env.VUE_APP_BACKEND_URL+"evaluations/setEvaluationStatus/"+this.id)
            .then(response => {
                console.log(response)
                    this.$swal.fire({
                                          position: 'top-end',
                                          icon: 'success',
                                          title: 'Successfully reviewed evaluation',
                                          showConfirmButton: false,
                                           timer: 2500
                                       })
                                       
                                this.loader.hide();
                                this.loader=null

                                       this.getAllEvaluations();

              
              })
             .catch(error => {
                 this.errorMessage = error.message;
                 console.error("There was an error!", error);
           });
       },
        deny: function(res){
           this.id= res.id;
                  this.loader = this.$loading.show({
                    container: this.fullPage ? null : this.$refs.formContainer,
                    canCancel: true,
                    onCancel: this.onCancel,
                   });
                    axios.get(process.env.VUE_APP_BACKEND_URL+"evaluations/unapproved/"+this.id)
            .then(response => {
                console.log(response)
                    this.$swal.fire({
                                          position: 'top-end',
                                          icon: 'success',
                                          title: 'Successfully reviewed evaluation',
                                          showConfirmButton: false,
                                           timer: 2500
                                       })
                                       
                                         this.loader.hide();
                                         this.loader=null
                                         this.getAllEvaluations();

              
              })
             .catch(error => {
                 this.errorMessage = error.message;
                 console.error("There was an error!", error);
           });
       },
         
   
     }

    }
    

</script> 

<style>

</style>
