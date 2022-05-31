<template>
  <BoatOwnerNav :username=email />
   <div class="card bg-light text-light" style="margin: 2%">
  <div class="content" >
      <FullCalendar :options="calendarOptions"    @select="handleSelect" class="calendar"  />
      <div class="info">
        <h5>{{email}}'s timetable</h5>

      </div>
    </div>
   </div>

   <vue-modality ref="reservationInfo" title="Reservation information" hide-footer centered>

   <br>
        <div class="row">
          <div class="col-sm-3" style="padding-top: 1%; text-align: left; color: gray;">
            <p>Boat</p>
          </div>
          <div class="col-sm-9" style="padding: 1%; text-align: left; ">
             
                  <p ><b>{{boatName}}</b></p>
                
          </div>
        </div>
         <div class="row">
          <div class="col-sm-3" style="padding-top: 1%; text-align: left; color: gray;">
            <p>Captain</p>
          </div>
          <div class="col-sm-9" style="padding: 1%; text-align: left; ">
             
                 <p v-if="captainIsRequired==true"><b>YES</b></p>
                  <p v-else><b>NO</b></p>
                
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
          <div class="col" style="padding-top: 1%; text-align: left; color: gray;">
            <p>Additional services</p>
          </div>
        </div>
        <div class="row">
          <div   class="col" style="padding: 4%; text-align: left; ">
                 
                 <ul v-for="(ads,index) in adServices" :key="index" class="list-group">
                  <li class="list-group-item"><b>{{ads}}</b></li>
                 </ul>
                
          </div>
        
        </div>
        
        

  
  <hr>
        <div >
           <button type="button" @click="openCalendar(boatName)" class="btn btn-primary" >Open calendar of this boat</button>
        </div>
</vue-modality>

<vue-modality ref="quickReservationInfo" title="Quick reservation information" hide-footer centered >

   <br>
        <div class="row">
          <div class="col-sm-3" style="padding-top: 1%; text-align: left; color: gray;">
            <p>Boat</p>
          </div>
          <div class="col-sm-9" style="padding: 1%; text-align: left; ">
             
                  <p ><b>{{boatNameQuick}}</b></p>
                
          </div>
        </div>
        <div class="row">
          <div class="col-sm-3" style="padding-top: 1%; text-align: left; color: gray;">
            <p>Captain</p>
          </div>
          <div class="col-sm-9" style="padding: 1%; text-align: left; ">
             
                 <p v-if="captainIsRequiredQuick==true"><b>YES</b></p>
                  <p v-else><b>NO</b></p>
                
          </div>
        </div>
        <div class="row">
          <div class="col" style="padding-top: 2%; text-align: left; color: gray;" >
            <p>Start</p>
          </div>
          <div class="col-sm-9" style="padding: 1%;" >
             <Datepicker   
           v-model="startQuickInfo" 
                
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
             <Datepicker  v-model="endQuickInfo" disabled></Datepicker>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-3" style="padding-top: 1%; text-align: left; color: gray;">
            <p>Username</p>
          </div>
          <div class="col-sm-9" style="padding: 1%; text-align: left;">
             <p><b>{{usernameQuickInfo}}</b></p>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-3" style="padding-top: 1%; text-align: left; color: gray;">
            <p>Full name</p>
          </div>
          <div class="col-sm-9" style="padding: 1%; text-align: left; ">
             <p><b>{{clientQuickFullNameInfo}}</b></p>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-3" style="padding-top: 1%; text-align: left; color: gray;">
            <p>Full price</p>
          </div>
          <div class="col-sm-9" style="padding: 1%; text-align: left; ">
             
                 <p><b>{{priceQuickInfo}}$</b></p>
                
          </div>
        </div>
         <div class="row">
          <div class="col-sm-3" style="padding-top: 1%; text-align: left; color: gray;">
            <p>Discount</p>
          </div>
          <div class="col-sm-9" style="padding: 1%; text-align: left; ">
             
                 <p><b>{{discountInfo}}%</b></p>
                
          </div>
        </div>
            <div class="row">
          <div class="col-sm-3" style="padding-top: 1%; text-align: left; color: gray;">
            <p>Owners part</p>
          </div>
          <div class="col-sm-9" style="padding: 1%; text-align: left; ">
             
                 <p><b>{{ownersPart}}%</b></p>
                
          </div>
        </div>
        <div class="row">
          <div class="col" style="padding-top: 1%; text-align: left; color: gray;">
            <p>Additional services</p>
          </div>
        </div>
        <div class="row">
          <div  class="col" style="padding: 4%; text-align: left; ">
                 
                 <ul v-for="(ads,index) in adServicesQuick" :key="index" class="list-group">
                  <li class="list-group-item"><b>{{ads}}</b></li>
                </ul>
                
          </div>
        </div>
         <hr>
         
        <div >
           <button type="button" @click="openCalendar(boatNameQuick)" class="btn btn-primary" >Open calendar of this boat</button>
        </div>
  
 
</vue-modality>

</template>

<script>
 import axios from "axios";
 import BoatOwnerNav from './BoatOwnerNav.vue'
  //import axios from "axios";
//import { ref} from "vue";
import "@fullcalendar/core/vdom"; // solves problem with Vite
import FullCalendar from "@fullcalendar/vue3";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import 'vue3-date-time-picker/dist/main.css';
//import dayjs from 'dayjs';
import Datepicker from 'vue3-date-time-picker';
import '@shapla/vue-modal/dist/style.css';
import VueModality from 'vue-modality-v3'

   export default{
    
     components: {
         BoatOwnerNav,
         FullCalendar,
         VueModality,
         Datepicker
  
     },
     data(){
       return{
       email: '',
       calendarOptions: {
        plugins: [interactionPlugin, dayGridPlugin, timeGridPlugin],
        initialDate: new Date(),
        headerToolbar: {
          left: "prev,next today",
          center: "title",
          right: "dayGridMonth,timeGridWeek,timeGridDay"
        },
        selectable: true,
         eventClick: (arg)=>{
           if(arg.event.title=='Reservation' || arg.event.title=='Reservation - CAPTAIN'){
                  this.$refs.reservationInfo.open()
                  this.startInfo=arg.event.start
                  this.endInfo=arg.event.end
                  this.priceInfo=arg.event.extendedProps.paymentInformation.ownersPart
                  this.usernameInfo=arg.event.extendedProps.email
                  this.clientFullNameInfo=arg.event.extendedProps.clientFullName
                  this.captainIsRequired=arg.event.extendedProps.captainIsRequired
                  this.boatName=arg.event.extendedProps.boatName
                  this.adServices=arg.event.extendedProps.adServices
           }else if(arg.event.title=='QuickReservation'  || arg.event.title=='QuickReservation - CAPTAIN'){
                  this.$refs.quickReservationInfo.open()
                  this.startQuickInfo=arg.event.start
                  this.endQuickInfo=arg.event.end
                  this.priceQuickInfo=arg.event.extendedProps.paymentInformation.totalPrice
                  this.ownersPart=arg.event.extendedProps.paymentInformation.ownersPart
                  this.boatNameQuick=arg.event.extendedProps.boatName
                  if(arg.event.extendedProps.email==null || arg.event.extendedProps.email==""){
                       this.usernameQuickInfo="Not reservated yet."
                       this.clientQuickFullNameInfo="Not reservated yet."
                  }else{
                  this.usernameQuickInfo=arg.event.extendedProps.email
                  this.clientQuickFullNameInfo=arg.event.extendedProps.clientFullName
                 
                  }
                  this.captainIsRequiredQuick=arg.event.extendedProps.captainIsRequired
                  this.adServicesQuick=arg.event.extendedProps.adServices
                  this.discountInfo=arg.event.extendedProps.discount
           }
        },
        selectMirror: true,
        dayMaxEvents: true,
        initialView: "dayGridMonth",
        events: [],
        startInfo: null,
        endInfo: null,
        startQuickInfo: null,
        endQuickInfo: null,
        priceInfo: null,
        usernameInfo: null,
        clientFullNameInfo: null,
        priceQuickInfo: null,
        usernameQuickInfo: null,
        clientQuickFullNameInfo: null,
        captainIsRequiredQuick: null,
        captainIsRequired: null,
        boatName: null,
        boatNameQuick: null,
        adServices: [],
        adServicesQuick: [],
        discountInfo: 0,
        ownersPart: 0
        },
       }
     },
     mounted() {
       this.email = this.$route.params.email
       this.getBoatReservations();
       this.getQuickReservations();
      
    },
     methods: {
        getBoatReservations: function(){
                axios.get(process.env.VUE_APP_BACKEND_URL+"reservationBoat/getByOwnerUsername/"+this.email+"/")
                .then(response => {
                      for( let newData of response.data ){
                                  if(newData.needsCaptainServices==true){
                                  var start=newData.startDate
                                  var end=newData.endDate
                                  newData.startDate=this.setDate(start)
                                  newData.endDate=this.setDate(end)
                                  var temp=[]
                                  for(let i=0;i<newData.addedAdditionalServices.length;i++)
                                     temp.push(newData.addedAdditionalServices[i].name)


                                  if(newData.needsCaptainServices==false){
                                  this.calendarOptions.events.push({id: newData.id , extendedProps: {paymentInformation:newData.paymentInformationDto,email: newData.clientUsername, boatName: newData.boatDto.name, price: newData.price, clientFullName: newData.clientFullName, captainIsRequired: newData.needsCaptainServices, adServices: temp} ,title: 'Reservation', start: newData.startDate , end: newData.endDate})
                                  }else{
                                  this.calendarOptions.events.push({id: newData.id ,color: '#7910b5', extendedProps: {paymentInformation:newData.paymentInformationDto,email: newData.clientUsername, boatName: newData.boatDto.name, price: newData.price, clientFullName: newData.clientFullName, captainIsRequired: newData.needsCaptainServices, adServices: temp} ,title: 'Reservation - CAPTAIN', start: newData.startDate , end: newData.endDate})
                                  }
                                  }
                        }   
                })

        },
        getQuickReservations: function(){
               axios.get(process.env.VUE_APP_BACKEND_URL+"quickReservationBoat/getByOwnerUsername/"+this.email+"/")
               .then(response => {
                     for( let newData of response.data ){
                                if(newData.needsCaptainServices==true){
                                var start=newData.startDate
                                var end=newData.endDate
                                newData.startDate=this.setDate(start)
                                newData.endDate=this.setDate(end)
                                var temp=[]
                                for(let i=0;i<newData.addedAdditionalServices.length;i++)
                                   temp.push(newData.addedAdditionalServices[i].name)

                                if(newData.needsCaptainServices==false){
                                this.calendarOptions.events.push({id: newData.id ,color: '#ffd04f', extendedProps: {paymentInformation:newData.paymentInformationDto,discount: newData.discount, email: newData.clientUsername, boatName: newData.boatDto.name, price: newData.price, clientFullName: newData.clientFullName, captainIsRequired: newData.needsCaptainServices, adServices: temp} ,title: 'QuickReservation', start: newData.startDate , end: newData.endDate})
                                }else{
                                 this.calendarOptions.events.push({id: newData.id ,color: '#7910b5', extendedProps: {paymentInformation:newData.paymentInformationDto,discount: newData.discount,email: newData.clientUsername, boatName: newData.boatDto.name, price: newData.price, clientFullName: newData.clientFullName, captainIsRequired: newData.needsCaptainServices, adServices: temp} ,title: 'QuickReservation - CAPTAIN', start: newData.startDate , end: newData.endDate})

                                }
                                }
                      }   
              })

        },
        setDate: function(newDate){
           var date= new Date()
           var splits =newDate.toString().split(",")
           date.setDate( splits[1],splits[2], splits[0])
           return new Date( parseInt(splits[0]), parseInt(splits[1])-1, parseInt(splits[2]),parseInt(splits[3]),parseInt(splits[4]))
        },
        openCalendar: function(name){
           this.$router.push("/boatCalendar/"+this.email+"/"+name)
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
