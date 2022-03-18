<template>
  <div>
    <nav  class="navbar navbar-fixed-top navbar-expand" style="background-color: #1d7ac9; list-style: none;">
      <div class="container-fluid" style="background-color: #1d7ac9;">
      <a class="navbar-brand"  href="#" >
      <img src="../../assets/logoF1.png" alt="" width="194" height="80" >
      </a>

         <li class="nav-item dropdown">
          <a style="color: white;" class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            {{email}}
          </a>
          <ul class="dropdown-menu" style="width: 100%" aria-labelledby="navbarDropdownMenuLink">
            <li><a @click="myProfile()" class="dropdown-item" href="#">Edit profile</a></li>
            <li><a @click="logout()" class="dropdown-item" href="#">Log out</a></li>
          </ul>
        </li>

      </div>

    </nav>

    <ul class="nav justify-content-center" style="background-color: #0b477b;">

      <li @click="home()" class="nav-item">
      <a style="color: white;" class="nav-link active" href="#"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-house" viewBox="0 0 16 16">
      <path fill-rule="evenodd" d="M2 13.5V7h1v6.5a.5.5 0 0 0 .5.5h9a.5.5 0 0 0 .5-.5V7h1v6.5a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 13.5zm11-11V6l-2-2V2.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5z"/>
      <path fill-rule="evenodd" d="M7.293 1.5a1 1 0 0 1 1.414 0l6.647 6.646a.5.5 0 0 1-.708.708L8 2.207 1.354 8.854a.5.5 0 1 1-.708-.708L7.293 1.5z"/>
    </svg> MY CABINS</a>
      </li>
      <li @click="addNewCabin()" class="nav-item">
      <a style="color: white;" class="nav-link active" href="#"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus-square" viewBox="0 0 16 16">
  <path d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"/>
  <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
</svg> ADD NEW CABIN</a>
      </li>

      <li class="nav-item">
      <a style="color: white;" class="nav-link active" href="#"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-calendar3" viewBox="0 0 16 16">
  <path d="M14 0H2a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zM1 3.857C1 3.384 1.448 3 2 3h12c.552 0 1 .384 1 .857v10.286c0 .473-.448.857-1 .857H2c-.552 0-1-.384-1-.857V3.857z"/>
  <path d="M6.5 7a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm-9 3a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm-9 3a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
</svg> UPCOMING RESERVATIONS</a>
      </li>
       <li class="nav-item">
      <a style="color: white;" @click="requests()" class="nav-link active" href="#"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil" viewBox="0 0 16 16">
  <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>
</svg> WRITE A REVIEW</a>
      </li>

      <li class="nav-item">
      <a style="color: white;" @click="requests()" class="nav-link active" href="#"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bar-chart-line" viewBox="0 0 16 16">
  <path d="M11 2a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v12h.5a.5.5 0 0 1 0 1H.5a.5.5 0 0 1 0-1H1v-3a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v3h1V7a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v7h1V2zm1 12h2V2h-2v12zm-3 0V7H7v7h2zm-5 0v-3H2v3h2z"/>
</svg> STATISTCS</a>
      </li>

     </ul>
    

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
               axios.post("http://localhost:8081/cabinsPeriod/getAvailablePeriod",this.cabinDto,{
            headers: {
            "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
            "Authorization": "Bearer " + localStorage.jwt ,
            }
             })
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
       myProfile: function(){
         this.$router.push('/editProfile/'+'cabinOwner/'+ this.email);
       },
       logout: function(){
         this.$router.push('/');
       },
       addNewCabin: function(){
         this.$router.push('/addNewCabin/'+ this.email);

       },
       home: function(){
         this.$router.push('/cabinOwnerHome/'+ this.email);

       },
       editProfile: function(){
        this.$router.push('/editCabinProfile/'+ this.email+'/'+this.cabinName);
       },
       getCabin: function(){
             this.cabinDto.name=this.cabinName
            
             axios.post("http://localhost:8081/cabins/findByName",this.cabinDto,{
            headers: {
            "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
            "Authorization": "Bearer " + localStorage.jwt ,
            }
             })
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
    
          axios.post("http://localhost:8081/cabinsPeriod/setAvailableCabinsPeriod",this.availableCabinPeriod,{
            headers: {
            "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
            "Authorization": "Bearer " + localStorage.jwt ,
            }
             })
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
      console.log("VREDNOSTII"+newDate.toString())
          var splits =newDate.toString().split(",")
          date.setDate( splits[1],splits[2], splits[0])
          console.log("0-"+splits[0])
          console.log("1-"+splits[1])
          console.log("2-"+splits[2])
          console.log("3-"+splits[3])
          console.log("4-"+splits[4])
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