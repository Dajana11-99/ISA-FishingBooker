<template>
  <div class="card bg-light text-light" style="margin: 5%">
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
       
      </div>
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
//import VueModality from 'vue-modality-v3'

import axios from "axios";

axios.defaults.baseURL = process.env.VUE_APP_URL;
export default {
   props: {
       role: null,
   },
  components: {
    FullCalendar,
    Datepicker,
   // VueModality

  
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
  data() {
      
    return {
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
        userRequestDto: {
            username: ''
        },
        start: null,
        end: null,
         fishingInstructorDtos: {
               id: null,
              username: '',
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
             rating: '',
            availablePeriodDtoSet: [{
                 id: null,
                startDate: null,
                endDate: null,
                username: '',
                propertyId: null
            }]

          },
           availableInstructorPeriod:{
                 id: null,
                startDate: null,
                endDate: null,
                username: ''
            },
            state: [],
            modalActive: false
         
           
      
     
    };
  },
 
  mounted() {


               
     if(this.$props.role ==='instructor'){
             axios.get(process.env.VUE_APP_BACKEND_URL+"userc/getUsername")
               .then(response => {
                       this.userRequestDto.username= response.data
                       this.fishingInstructorDtos.username=response.data
                       console.log("stigao   " +this.userRequestDto.username)
                       axios.post(process.env.VUE_APP_BACKEND_URL+"instructorsPeriod/getAvailablePeriod",this.userRequestDto)
                        .then(response => {
                          this.availableInstructorPeriod=response.data
                           
                            for( let newData of response.data ){
                                var start=newData.startDate
                                var end=newData.endDate
                                newData.startDate=this.setDate(start)
                                newData.endDate=this.setDate(end)
                              this.calendarOptions.events.push({id: newData.id ,title: 'Available', start: newData.startDate , end: newData.endDate })
                              this.state.push( newData.startDate)
                            }
              })   
              })
     }
  },
  methods: {
     formatDate(formatDate) {
            const date = dayjs(formatDate);
           return date.format('YYYY-MM-DDTHH:mm:ss');
        },
     setPeriod: function(){

            this.fishingInstructorDtos.availablePeriodDtoSet[0]=({
            startDate:  this.formatDate(this.start),
            endDate:  this.formatDate(this.end),
            username: this.fishingInstructorDtos.username,
            propertyId: null
            })

            this.start='',
            this.end=''
    
          console.log("pre zahtevA    "+this.fishingInstructorDtos.availablePeriodDtoSet.length)
          console.log("pre zahtevA    "+this.fishingInstructorDtos.availablePeriodDtoSet[0].startDate)
               axios
               .post(process.env.VUE_APP_BACKEND_URL+"instructorsPeriod/setAvailableInstructorPeriod",this.fishingInstructorDtos)
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
       return new Date( parseInt(splits[0]), parseInt(splits[1])-1, parseInt(splits[2]),parseInt(splits[3]),parseInt(splits[4]))

    },

  },

  
};
</script>
<style scoped>
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