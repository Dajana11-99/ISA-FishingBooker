<template>
  <BoatOwnerNav :username=email />
   <div class="card bg-light text-light" style="margin: 5%">
  <div class="content" >
      <FullCalendar :options="calendarOptions"    @select="handleSelect" class="calendar"  />
      <div class="info">
        <h5>{{email}}'s timetable</h5>
       
      </div>
    </div>
   </div>

</template>

<script>
 //import axios from "axios";
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
import '@shapla/vue-modal/dist/style.css';
//import VueModality from 'vue-modality-v3'

   export default{
    
     components: {
         BoatOwnerNav,
          FullCalendar,

   // VueModality,

  
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
           if(arg.event.title=='Available'){
                  this.$refs.myRef.open()
                  this.startEdit=arg.event.start
                  this.endEdit=arg.event.end
                  this.argEventDeleting=arg.event
           }/*else if(arg.event.title=='Reservation'){
                  this.$refs.reservationInfo.open()
                  this.startInfo=arg.event.start
                  this.endInfo=arg.event.end
                  this.priceInfo=arg.event.extendedProps.price
                  this.usernameInfo=arg.event.extendedProps.email
                  this.clientFullNameInfo=arg.event.extendedProps.clientFullName
           }else if(arg.event.title=='QuickReservation'){
                  this.$refs.quickReservationInfo.open()
                  this.startQuickInfo=arg.event.start
                  this.endQuickInfo=arg.event.end
                  this.priceQuickInfo=arg.event.extendedProps.price
                  if(arg.event.extendedProps.email==null){
                       this.usernameQuickInfo="Not reservated yet."
                       this.clientQuickFullNameInfo="Not reservated yet."

                  }else{
                  this.usernameQuickInfo=arg.event.extendedProps.email
                  this.clientQuickFullNameInfo=arg.event.extendedProps.clientFullName
                 
                  }
           }*/
        },
        selectMirror: true,
        dayMaxEvents: true,
        initialView: "dayGridMonth",
        events: [],
        },
       }
     },
     mounted() {
       this.email = this.$route.params.email
      
    },
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
