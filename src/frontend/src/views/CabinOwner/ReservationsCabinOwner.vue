<template>

<CabinOwnerNav :username=email />
<div > 
<h1>RESERVATIONS</h1>
<div  class="header" >
      <form>
        <h1 style="text-align: left; color: #0b477b;  padding-left: 2%;">Search reservations</h1>
        <br>
        <div class="row" >
           
          <div class="col-sm" style="padding-left: 45%;">
          <input
            class="form-control rounded-pill"
            type="text"
            style="height: 90%; width: 90%; padding-left: 5%;"
            id="search-field"
            placeholder="CABIN NAME"
            :value="searchCabinNameReservations" 
            @input="searchCabinNameReservations = $event.target.value.toUpperCase()"
          />
          </div>

          <div class="col">
          <input
            class="form-control rounded-pill"
            type="text"
            style="height: 90%; width: 90%; "
            id="search-field"
            placeholder="CLIENT"
            :value="searchClientReservations" 
            @input="searchClientReservations = $event.target.value.toUpperCase()"
          />
          </div>

          <div class="col" >
          <button
            @click="resetSearch(activePage)"
            style="height: 90%; width: 90%; background-color: #0b477b; color: white;"
            type="button"
            class="btn  rounded-pill"
          >
            RESET SEARCH
          </button>
          </div>
          
        </div>
      </form>
    </div>
    <hr>
<div style="  padding: 2%;">
    <div style="padding-left: 2%; padding-right:2%; ">
  <ul class="nav nav-tabs" id="myTab" role="tablist">
  <li class="nav-item" role="presentation">
    <button @click="resetSearch(1)" class="nav-link active" id="home-tab" data-bs-toggle="tab" data-bs-target="#home" type="button" role="tab" aria-controls="home" aria-selected="true"><b>Current/Upcoming</b></button>
  </li>
  <li class="nav-item" role="presentation">
    <button @click="resetSearch(2)" class="nav-link" id="contact-tab" data-bs-toggle="tab" data-bs-target="#contact" type="button" role="tab" aria-controls="contact" aria-selected="false"><b>Past</b></button>
  </li>
  </ul>
<div class="tab-content" id="myTabContent">
    <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                        <table class="table">
                        <thead>
                            <tr>
                            <th scope="col">#</th>
                            <th scope="col">Cabin name</th>
                            <th scope="col">Start date</th>
                            <th scope="col">End date</th>
                            <th scope="col">Client</th>
                            <th scope="col">&nbsp;</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(ads,index) in filteredRes" :key="index">
                            <th scope="row">{{index+1}}</th>
                            <td>{{ads.cabinDto.name}}</td>
                            <td>{{setandFormatDate(ads.startDate)}}</td>
                            <td>{{setandFormatDate(ads.endDate)}}</td>
                            <td>{{ads.clientUsername}}</td>
                            <td><button @click="reservationInformation(ads)" type="button" class="btn btn-info">Details</button></td>
                            </tr>
                        </tbody>
                        </table>
    </div>
   
    <div class="tab-pane fade" id="contact" role="tabpanel" aria-labelledby="conact-tab">
                        <table class="table">
                        <thead>
                            <tr>
                            <th scope="col">#</th>
                            <th scope="col">Cabin name</th>
                            <th scope="col">Start date</th>
                            <th scope="col">End date</th>
                             <th scope="col">Client</th>
                            <th scope="col">&nbsp;</th>
                            <th scope="col">&nbsp;</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(ads1,index) in filteredPastRes" :key="index">
                            <th scope="row">{{index+1}}</th>
                            <td>{{ads1.cabinDto.name}}</td>
                            <td>{{setandFormatDate(ads1.startDate)}}</td>
                            <td>{{setandFormatDate(ads1.endDate)}}</td>
                            <td>{{ads1.clientUsername}}</td>
                            <td><button @click="reservationInformation(ads1)" type="button" class="btn btn-info">Details</button></td>
                            <td ><button v-if="ads1.ownerWroteAReport==false " @click="writeAReview(ads1)" type="button" class="btn btn-success">Review</button></td>

                            </tr>
                        </tbody>
                        </table>

    </div>
    
    </div>
</div>

</div>

<vue-modality ref="reservationInfo" title="Reservation information" hide-footer centered>

   <br>
        <div class="row">
          <div class="col-sm-3" style="padding-top: 1%; text-align: left; color: gray;">
            <p>Cabin </p>
          </div>
          <div class="col-sm-9" style="padding: 1%; text-align: left;">
             <p><b>{{cabinInfo}}</b></p>
          </div>
        </div>
        <div class="row">
          <div class="col" style="padding-top: 2%; text-align: left; color: gray;" >
            <p>Start</p>
          </div>
          <div class="col-sm-9" style="padding: 1%;" >
             <Datepicker   
           v-model="startInfo" 
                
         disabled >
          </Datepicker>
          </div>
        </div>
        <br>
        <div class="row">
          <div class="col" style="padding-top: 2%; text-align: left; color: gray;">
            <p>End</p>
          </div>
          <div class="col-sm-9" style="padding: 1%;">
             <Datepicker  v-model="endInfo" disabled></Datepicker>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-3" style="padding-top: 1%; text-align: left; color: gray;">
            <p>Username</p>
          </div>
          <div class="col-sm-9" style="padding: 1%; text-align: left;">
             <p><b>{{usernameInfo}}</b></p>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-3" style="padding-top: 1%; text-align: left; color: gray;">
            <p>Full name</p>
          </div>
          <div class="col-sm-9" style="padding: 1%; text-align: left; ">
             <p><b>{{clientFullNameInfo}}</b></p>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-3" style="padding-top: 1%; text-align: left; color: gray;">
            <p>Full price</p>
          </div>
          <div class="col-sm-9" style="padding: 1%; text-align: left; ">
             
                 <p><b>{{priceInfo}}$</b></p>
                
          </div>
        </div>
         <div class="row">
          <div class="col-sm-3" style="padding-top: 1%; text-align: left; color: gray;">
            <p>Captain</p>
          </div>
          <div class="col-sm-9" style="padding: 1%; text-align: left; ">
             
                 <p v-if="captainInfo==true"><b>YES</b></p>
                  <p v-else><b>NO</b></p>
                
          </div>
        </div>
          <div class="row">
          <div class="col-sm-3" style="padding-top: 1%; text-align: left; color: gray;">
            <p>Cancelling</p>
          </div>
          <div class="col-sm-9" style="padding: 1%; text-align: left; ">
             
                 <p><b>{{cancelingInfo}}</b></p>
                
          </div>
        </div>
         <div class="row">
          <div class="col" style="padding-top: 1%; text-align: left; color: gray;">
            <p>Additional services</p>
          </div>
        </div>
        <div class="row">
          <div   class="col" style="padding: 4%; text-align: left; ">
                 
                 <ul v-for="(ads,index) in adServicesInfo" :key="index" class="list-group">
                  <li class="list-group-item"><b>{{ads}}</b></li>
                 </ul>
                
          </div>
        
        </div>
        

  
  <hr>
  <button type="button" @click="openCalendarOfBoat()" class="btn btn-primary">Open calendar of this boat</button>
</vue-modality>

<vue-modality ref="writeAReview" title="Write a review" hide-footer centered width="900px">

   <br>
   <div class="row"> 
     <div class="col">
        <div class="row">
          <div class="col-sm-3" style="padding-top: 1%; text-align: left; color: gray;">
            <p>Cabin </p>
          </div>
          <div class="col-sm-9" style="padding: 1%; text-align: left;">
             <p><b>{{cabinInfo}}</b></p>
          </div>
        </div>
        <div class="row">
          <div class="col" style="padding-top: 2%; text-align: left; color: gray;" >
            <p>Start</p>
          </div>
          <div class="col-sm-9" style="padding: 1%;" >
             <Datepicker   
           v-model="startInfo" 
                
         disabled >
          </Datepicker>
          </div>
        </div>
        <br>
        <div class="row">
          <div class="col" style="padding-top: 2%; text-align: left; color: gray;">
            <p>End</p>
          </div>
          <div class="col-sm-9" style="padding: 1%;">
             <Datepicker  v-model="endInfo" disabled></Datepicker>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-3" style="padding-top: 1%; text-align: left; color: gray;">
            <p>Username</p>
          </div>
          <div class="col-sm-9" style="padding: 1%; text-align: left;">
             <p><b>{{usernameInfo}}</b></p>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-3" style="padding-top: 1%; text-align: left; color: gray;">
            <p>Full name</p>
          </div>
          <div class="col-sm-9" style="padding: 1%; text-align: left; ">
             <p><b>{{clientFullNameInfo}}</b></p>
          </div>
        </div>
     </div>
         
         <div class="col">
        <div class="row" style="padding-bottom: 1%;">
          <div class="col-sm-3" style="padding-top: 1%; text-align: left; color: gray;">
            <p>Review</p>
          </div>
        </div>
        <div class="row" style=" padding-left: 3%; padding-right: 3%; height: 40%;">
                 <textarea v-model="comment" required ></textarea>
                 <label style="color: red;" v-if="comment===''">Please write a review.</label>
               
        </div>
        
          
       
  <br>
         <div class="row">
             <div class="col-sm-6" style="text-align: left;">
                <p>Client showed up</p>
             </div>

             <div class="col-sm-6" style="text-align: left;">
                <p>Suggest client gets 1 penal</p>
             </div>
         </div>
         <div class="row">
             <div class="col-sm-6">
                    <select
                    style="color: #5f7280;"
                    v-model="selectedClientShowedUp"
                    class="form-select "
                    aria-label="Default select example"
                    placeholder="Rating"
                  >
                    <option v-bind:value="0">YES</option>
                    <option v-bind:value="1">NO</option>
                   </select>
             </div>
             
             <div class="col-sm-6">
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



      
  </div>
  <hr>
  <button type="button" @click="createAReview()" class="btn btn-success">Send report to system admins</button>
</vue-modality>
</div>
</template>
<script>

import axios from "axios";
import CabinOwnerNav from './CabinOwnerNav.vue'
import dayjs from 'dayjs';
import VueModality from 'vue-modality-v3'
import Datepicker from 'vue3-date-time-picker';
import 'vue3-date-time-picker/dist/main.css';
export default ({
    components: {
         CabinOwnerNav,
         VueModality,
         Datepicker
  
     },
      data(){
       return{
        email: '',
        reservations: [],
        pastReservations: [],
        cabinInfo: '',
        startInfo: '',
        endInfo: '',
        usernameInfo: '',
        clientFullNameInfo: '',
        priceInfo: '',
        adServicesInfo: [],
        searchCabinNameReservations: '',
        searchClientReservations: '',
        activePage: 1,
        cancelingInfo: '',
        reservationId: 0,
        selectedPenalty: 1,
        selectedClientShowedUp: 0,
        comment: '',
        reservation: null

       }
       },
       mounted() {
             this.email = this.$route.params.email
             this.getReservations()
             this.getPastReservations()
       },
       methods: {
           setandFormatDate: function(newDate){
           var date= new Date()
           var splits =newDate.toString().split(",")
           date.setDate( splits[1],splits[2], splits[0])
           var newData= new Date( parseInt(splits[0]), parseInt(splits[1])-1, parseInt(splits[2]),parseInt(splits[3]),parseInt(splits[4]))
            const dateee = dayjs(newData);
           return dateee.format('YYYY-MM-DD HH:mm:ss');
           },
           setDate: function(newDate){
           var date= new Date()
           var splits =newDate.toString().split(",")
           date.setDate( splits[1],splits[2], splits[0])
           return new Date( parseInt(splits[0]), parseInt(splits[1])-1, parseInt(splits[2]),parseInt(splits[3]),parseInt(splits[4]))
           },
           getReservations: function(){
               this.reservations=[]
                axios.get(process.env.VUE_APP_BACKEND_URL+"reservationCabin/getByOwnerUsername/"+this.email+"/")
                .then(response => {
                      this.reservations= response.data; 
                })
               
          },
          getPastReservations: function(){
                this.pastReservations=[]
                axios.get(process.env.VUE_APP_BACKEND_URL+"reservationCabin/getPastReservations/"+this.email+"/")
                .then(response => {
                      this.pastReservations= response.data; 
                })
               
          },
          reservationInformation: function(ads){
              this.adServicesInfo=[]
              if(ads.addedAdditionalServices.length>0){
                      for(let i = 0 ; i < ads.addedAdditionalServices.length ; i++)
                         this.adServicesInfo.push(ads.addedAdditionalServices[i].name)
              }
              this.cabinInfo=ads.cabinDto.name
              this.cancelingInfo=ads.cabinDto.cancellingConditions
              this.startInfo=this.setDate(ads.startDate)
              this.endInfo=this.setDate(ads.endDate)
              this.usernameInfo=ads.clientUsername
              this.clientFullNameInfo=ads.clientFullName
              this.priceInfo=ads.paymentInformationDto.ownersPart
              this.$refs.reservationInfo.open()
         
          },
          writeAReview: function(ads){
              this.cabinInfo=ads.cabinDto.name
              this.startInfo=this.setDate(ads.startDate)
              this.endInfo=this.setDate(ads.endDate)
              this.usernameInfo=ads.clientUsername
              this.clientFullNameInfo=ads.clientFullName
              this.reservation=ads
              this.comment=""
              this.$refs.writeAReview.open()
              
          },
          openCalendarOfBoat: function(){
              this.$router.push("/cabinCalendar/"+this.email+"/"+this.cabinInfo)
          },
          resetSearch: function(num){
              this.activePage=num
              this.searchCabinNameReservations=''
              this.searchClientReservations=''
              this.getReservations();
              this.getPastReservations();
          },
          createAReview: function(){
            if(this.comment=='') return;
            var bad=false
            var success=true
                if(this.selectedPenalty==0)
                   bad=true
                if(this.selectedClientShowedUp==1)
                   success=false

            /*this.reservation.successfull=success
            this.reservation.ownersReportDto.badComment= bad
            this.reservation.ownersReportDto.comment=this.comment*/
                axios.post(process.env.VUE_APP_BACKEND_URL+"reservationCabin/ownerCreatesReview/"+this.reservation.id,
                 {
                            id: 0,
                            success: success,
                            badComment: bad,
                            comment: this.comment,
                            approved: false,
                            ownersUsername: this.email,
                            clientUsername: this.reservation.clientUsername

                    })
                .then(response => {
                      this.$refs.writeAReview.hide()
                      console.log(response)
                      this.$swal.fire({
                      position: 'top-end',
                      icon: 'success',
                      title: 'Your review has been saved',
                      showConfirmButton: false,
                      timer: 2500
                    })
                    this.getReservations()
                    this.getPastReservations()
                      
                })

  
                this.selectedClientShowedUp=0
                this.selectedPenalty=1
                this.comment=''
                this.reservationId=0
               
          },

       },
       computed: {
        filteredRes: function () {
               if(this.activePage==1){
                var temp = this.reservations.filter((res) => {
                    return res.cabinDto.name.toUpperCase().match(this.searchCabinNameReservations) && 
                        res.clientUsername.toUpperCase().match(this.searchClientReservations) ;
                });
               }

           return temp;
        },
        filteredPastRes: function () {
               if(this.activePage==2){
                var temp = this.pastReservations.filter((res) => {
                    return res.cabinDto.name.toUpperCase().match(this.searchCabinNameReservations) && 
                        res.clientUsername.toUpperCase().match(this.searchClientReservations) ;
                });
               }

           return temp;
        },
  },
       
    
})
</script>
