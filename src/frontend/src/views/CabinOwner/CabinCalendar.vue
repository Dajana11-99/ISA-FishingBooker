<template>
 <CabinOwnerNavBar :username=email />
  <div>
   
    <div class="card bg-light text-light" style=" margin: 5%; margin-top: 2%">
    <div class="content">
      <FullCalendar :options="calendarOptions"    @select="handleSelect" class="calendar"  />
      <div class="info">
        <h2>Timetable</h2>

        <br>
        <br>
        <h5 style=" text-align: left;">SET AVAILABLE PERIOD</h5>
        <br>
        <div class="row">
          <div class="col" style="padding-top: 2%; text-align: left;" >
            <h5>From</h5>
          </div>
          <div class="col-sm-9" style="padding: 1%;" >
             <Datepicker   
           
           v-model="start" 
                
         >
          </Datepicker>
          </div>
        </div>
        <div class="row">
          <div class="col" style="padding-top: 2%; text-align: left;">
            <h5>To</h5>
          </div>
          <div class="col-sm-9" style="padding: 1%;">
             <Datepicker v-model="end"></Datepicker>
          </div>
        </div>
           &nbsp;
           <div class="col" style="text-align: right; width: 100%; padding-top: 1%;">
           <button type="button" @click="setPeriod()" class="btn btn-light">Save</button>
          </div>
        <br>
        <br>
      

      
      </div>
    </div>


<vue-modality ref="myRef" title="Edit available period" hide-footer centered>

   <br>
        <div class="row">
          <div class="col" style="padding-top: 2%; text-align: left;" >
            <h6>From</h6>
          </div>
          <div class="col-sm-9" style="padding: 1%;" >
             <Datepicker   
           :minDate="start"
           :maxDate="end"
           v-model="start" 
                
         >
          </Datepicker>
          </div>
        </div>
        <div class="row">
          <div class="col" style="padding-top: 2%; text-align: left;">
            <h6>To</h6>
          </div>
          <div class="col-sm-9" style="padding: 1%;">
             <Datepicker   :minDate="start" :maxDate="end" v-model="end"></Datepicker>
          </div>
        </div>

  <br>
  <hr>
  <div style="text-align: left;">
  <h6>Add unavailable days</h6>
  </div>
         <br>
        <div class="row">
          <div class="col" style="padding-top: 2%; text-align: left;" >
            <h6>From</h6>
          </div>
          <div class="col-sm-9" style="padding: 1%;" >
             <Datepicker   
           :minDate="start"
           :maxDate="end"
           v-model="unavailableStart" 
                
         >
          </Datepicker>
          </div>
        </div>
        <div class="row">
          <div class="col" style="padding-top: 2%; text-align: left;">
            <h6>To</h6>
          </div>
          <div class="col-sm-9" style="padding: 1%;">
             <Datepicker   :minDate="unavailableStart" :maxDate="end" v-model="unavailableEnd"></Datepicker>
          </div>
        </div>
        <div class="row">
                  <label v-if="reservationExists==true" style="color: red;">Reservation in this period already exists!</label>
        </div>
        <button type="button" class="btn btn-outline-dark">Add</button>
  <br>
  <hr>
   <div class="row">
        <div class="col">
           <button type="button" class="btn btn-secondary">Close</button>
        </div>
        <div class="col">
           <button type="button" class="btn btn-danger">Delete</button>
        </div>
        <div class="col">
           <button type="button" class="btn btn-primary" >Save</button>
        </div>
    </div>
</vue-modality>
    </div>

  </div>

</template>

<script>

import CabinOwnerNavBar from './CabinOwnerNav.vue'
import { ref} from "vue";
import "@fullcalendar/core/vdom"; // solves problem with Vite
import FullCalendar from "@fullcalendar/vue3";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import Datepicker from 'vue3-date-time-picker';
import 'vue3-date-time-picker/dist/main.css';
import dayjs from 'dayjs';
import '@shapla/vue-modal/dist/style.css';
import VueModality from 'vue-modality-v3'

import axios from "axios";
   export default{
     components: {
           CabinOwnerNavBar,
           FullCalendar,
           Datepicker,
           VueModality
   },
   setup() {
          const startDate = ref();
          const endDate = ref();
          const myRef = ref(null)
          const openMyModal = () => { myRef.value.open() }
   
    return {
      myRef,
      openMyModal,
      startDate,
      endDate,
     
    
    };
  },

     data(){
       return{
      unavailableStart: '',
      unavailableEnd: '',
      reservationExists: false,
      closeModal: false,
      disabledPickers: false,
      selectDisabled: false,
      currentEvent: "",
      selectData: [],
      entityType: "",
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
         this.$refs.myRef.open()
          this.start=arg.event.start
          this.end=arg.event.end
        },
        selectMirror: true,
        dayMaxEvents: true,
        initialView: "dayGridMonth",
        events: [],
      },
         email: '',
          user:{
           username: ''
         },
         cabinName: '',
          cabinDto: {
              id: null,
              name: '',
              description: '',
              numOfRooms: 1,
              bedsPerRoom: 1,
              rules: '',
              price: 1.0,
              addressDto: {
                  longitude: 0.0,
                  latitude: 0.0,
                  country: '',
                  city: '',    
                  streetAndNum: ''
              },
             additionalServices: [{
                 id: null,
                 name: '',
                 price: 0.0

             }],
             rating: 0.0,
             images: [{
                 id: null,
                 url: '',
                 cabin: ''

             }],
             ownerUsername: ''
         },
       availableCabinPeriod: [{
                 id: null,
                startDate: null,
                endDate: null,
                username: '',
                propertyId: null
            }],
            start: null,
            end: null,
            cabinId: null,
        

       }
     },
     mounted() {
       this.email = this.$route.params.email
       this.cabinName= this.$route.params.cabinName
       this.availableCabinPeriod.username=this.email
       this.getCabin()
      



     },
     methods: {
         getCabinsAvailablePeriod: function(){
               axios.post("http://localhost:8081/cabinsPeriod/getAvailablePeriod",this.cabinDto)
               .then(response => {
                  this.availableCabinPeriod=response.data
                     for( let newData of response.data ){
                                var start=newData.startDate
                                var end=newData.endDate
                                newData.startDate=this.setDate(start)
                                newData.endDate=this.setDate(end)
                              this.calendarOptions.events.push({id: newData.id ,title: 'Available', start: newData.startDate , end: newData.endDate })
                            }
                      
                      
              })


         },
       getCabin: function(){
             this.cabinDto.name=this.cabinName
            
             axios.post("http://localhost:8081/cabins/findByName",this.cabinDto)
               .then(response => {
                        this.cabinDto=response.data
                         this.cabinId=this.cabinDto.id
                         this.getCabinsAvailablePeriod();
                      
                      
              })

       },
        formatDate(formatDate) {
            console.log("preeformat"+formatDate)
            const date = dayjs(formatDate);
           return date.format('YYYY-MM-DDTHH:mm:ss');
        },setPeriod: function(){
          console.log("starttt"+this.start)
           console.log("endd"+this.end)
         
            this.availableCabinPeriod[0]=({
              startDate:  this.formatDate(this.start),
              endDate:  this.formatDate(this.end),
              username: this.email,
              propertyId: this.cabinId
           
              
             })
              this.start='',
              this.end=''
    
          axios.post("http://localhost:8081/cabinsPeriod/setAvailableCabinsPeriod",this.availableCabinPeriod)
               .then(response => {
                       this.$swal.fire({
                 position: 'top-end',
                  icon: 'success',
                 title: 'Your work has been saved',
               showConfirmButton: false,
               timer: 1500
                })
                this.$router.go()
                 return response;
                      
              })


        },
     
        

    setDate: function(newDate){
      
      var date= new Date()
          var splits =newDate.toString().split(",")
          date.setDate( splits[1],splits[2], splits[0])
    return new Date( parseInt(splits[0]), parseInt(splits[1])-1, parseInt(splits[2]),parseInt(splits[3]),parseInt(splits[4]))

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
  background-image:  url("../../assets/IMG_3872.jpeg"); 
}
#nav a {
  font-weight: bold;
  color: #2c3e50;
}
#nav a.router-link-exact-active {
  color: #42b983;
}
.element {
  margin-bottom: 2rem;
}
.content {
  display: flex;
  justify-content: space-between;
}
.calendar {
  width: 70%;
  color: #1a252f;
  
  
}
.info {
  background: #1a252f;
  width: 28%;
  border-radius: 5px;
  border: 1px solid white;
  padding: 1rem;
}
</style>