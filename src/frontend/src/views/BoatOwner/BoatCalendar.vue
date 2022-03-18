<template>
  <BoatOwnerNav :username=email />
  <div>
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
        <br>
        <br>
        <h5 style=" text-align: left;">SET UNAVAILABLE PERIOD</h5>
        <br>
        <div class="row">
          <div class="col" style="padding-top: 2%; text-align: left;" >
            <h5>From</h5>
          </div>
          <div class="col-sm-9" style="padding: 1%;" >
             <Datepicker></Datepicker>
          </div>
        </div>
        <div class="row">
          <div class="col" style="padding-top: 2%; text-align: left;">
            <h5>To</h5>
          </div>
          <div class="col-sm-9" style="padding: 1%;">
             <Datepicker></Datepicker>
          </div>
        </div>
           &nbsp;
           <div class="col" style="text-align: right; width: 100%; padding-top: 1%;">
           <button type="button" class="btn btn-light">Save</button>
          </div>


      
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
  import axios from "axios";
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
import BoatOwnerNav from './BoatOwnerNav.vue'


   export default{
    components: {
    BoatOwnerNav,
    FullCalendar,
    Datepicker,
    VueModality
  },  setup() {
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
         boatDto: {

         id: null,
         ownersUsername: '',
         name: '',
         type: '',
         length: '',
         engineCode: '',
         enginePower: '',
         maxSpeed: '',
         navigationEquipment: '',
         addressDto: {
                  longitude: 0.0,
                  latitude: 0.0,
                  country: '',
                  city: '',    
                  streetAndNum: ''
         },
         description: '',
         images:[{
                  id: null,
                  url: '',
                  cabin: ''
         }],
         maxPeople: 1, 
         rules: '',
         fishingEquipment: '',
         price: 1.0,
         additionalServices: [{
                  id: null,
                  name: '',
                  price: 0.0  
         }],
         cancelingCondition: '',
         rating: '', 
         },
         prices: '',
           names: '',
           idx: 0,
           tableHidden: true,
           selectedFile: null,
           imagesSelected: false,
           imagesSelectedEvent: null,
           additionalServicesAdded: false,
           loader: null,
           user:{
            username: ''
           },
           boatName: '',
              availableBoatPeriod: [{
                 id: null,
                startDate: null,
                endDate: null,
                username: '',
                propertyId: null
            }],
            start: null,
            end: null,
            boatId: null,

       }

     },
     mounted() {
       this.email = this.$route.params.email
       this.boatName= this.$route.params.boatName
       this.getBoat()
     },
     methods: {
       getBoat: function(){
                  this.boatDto.name=this.boatName
                  this.boatDto.ownersUsername=this.email
            axios.post("http://localhost:8081/boats/findByName",this.boatDto)
                  .then(response => {
                        this.boatDto=response.data
                        this.boatId = this.boatDto.id
                        this.getBoatsAvailablePeriod();
                  })

       },
         getBoatsAvailablePeriod: function(){
               axios.post("http://localhost:8081/boatsPeriod/getAvailablePeriod",this.boatDto)
               .then(response => {
                  this.availableBoatPeriod=response.data
                     for( let newData of response.data ){
                                var start=newData.startDate
                                var end=newData.endDate
                                newData.startDate=this.setDate(start)
                                newData.endDate=this.setDate(end)
                              this.calendarOptions.events.push({id: newData.id ,title: 'Available', start: newData.startDate , end: newData.endDate })
                            }
                      
                      
              })


         },

        formatDate(formatDate) {
            const date = dayjs(formatDate);
           return date.format('YYYY-MM-DDTHH:mm:ss');
        },setPeriod: function(){
            this.availableBoatPeriod[0]=({
              startDate:  this.formatDate(this.start),
              endDate:  this.formatDate(this.end),
              username: this.email,
              propertyId: this.boatId
             })
              this.start='',
              this.end=''
    
          axios.post("http://localhost:8081/boatsPeriod/setAvailableBoatsPeriod",this.availableBoatPeriod)
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
</style>
