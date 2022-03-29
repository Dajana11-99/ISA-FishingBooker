<template>
  <BoatOwnerNav :username=email />
  <div>
         <div class="card bg-light text-light" style="margin: 5%">
    <div class="content">
      <FullCalendar :options="calendarOptions"  @select="handleSelect" class="calendar"  />
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
         
         <div style="padding: 5%;">
       <button style="width: 80%; " type="button" @click="this.$refs.makeReservation.open()" class="btn btn-primary btn-lg">Make reservation</button>
       </div>
       
       <div style="padding: 5%;">
       <button style="width: 80%;" type="button" @click="this.$refs.makeQuickReservation.open()" class="btn btn-primary btn-lg">Create quick action</button>
      </div>
      
      </div>
    </div>
</div>

<vue-modality ref="myRef" title="Edit available period" hide-footer hide-header centered no-close-on-esc=true no-close-on-backdrop=true>
  <h4><b>Edit available period</b></h4>
  <hr>
   <br>
        <div class="row">
          <div class="col" style="padding-top: 2%; text-align: left;" >
            <h6>From</h6>
          </div>
          <div class="col-sm-9" style="padding: 1%;" >
             <Datepicker   
           v-model="startEdit" 
                
         disabled >
          </Datepicker>
          </div>
        </div>
        <div class="row">
          <div class="col" style="padding-top: 2%; text-align: left;">
            <h6>To</h6>
          </div>
          <div class="col-sm-9" style="padding: 1%;">
             <Datepicker  v-model="endEdit" disabled ></Datepicker>
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
             <Datepicker  v-model="unavailableEnd"></Datepicker>
          </div>
        </div>
        <div class="row">
                  <label v-if="reservationExists==true" style="color: red;">Reservation in this period already exists!</label>
        </div>
        
  <br>
  <label style="color: red;" v-if="editDataIsNotValid==true">Please enter valid dates</label>
  <hr>
   <div class="row">
        <div class="col">
           <button type="button" @click="clearModalEdit()" class="btn btn-secondary">Close</button>
        </div>
        <div class="col">
           <button type="button" @click="deleteAvailablePeriod()" class="btn btn-danger">Delete</button>
        </div>
        <div class="col">
           <button type="button" @click="editAvailablePeriod()" class="btn btn-primary" >Save</button>
        </div>
    </div>
</vue-modality>




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
           availableBoatPeriod: {
                 id: null,
                startDate: null,
                endDate: null,
                username: '',
                propertyId: null
            },
            start: null,
            end: null,
            startEdit: null,
            endEdit: null,
            unavailableStart: null,
            unavailableEnd: null,
            editDataIsNotValid: false,
            argEventDeleting: null,
           
       }

     },
     mounted() {
       this.email = this.$route.params.email
       this.boatName= this.$route.params.boatName
       this.getBoat()
     },
     methods: {
       getBoat: function(){
            axios.post("http://localhost:8081/boats/findByNameAndOwnersUsername/"+this.boatName+"/"+this.email+"/")
                  .then(response => {
                        this.boatDto=response.data
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
                                this.calendarOptions.events.push({id: newData.id ,title: 'Available', start: newData.startDate , end: newData.endDate , color: '#6f9681' })
                            }
                      
                      
              })


         },
        formatDate(formatDate) {
            const date = dayjs(formatDate);
           return date.format('YYYY-MM-DDTHH:mm:ss');
        },
        setDate: function(newDate){
           var date= new Date()
           var splits =newDate.toString().split(",")
           date.setDate( splits[1],splits[2], splits[0])
           return new Date( parseInt(splits[0]), parseInt(splits[1])-1, parseInt(splits[2]),parseInt(splits[3]),parseInt(splits[4]))
        },
        setPeriod: function(){
           if(!this.dataIsValid(this.start,this.end)){
             this.$swal.fire({
                 position: 'top-end',
                  icon: 'error',
                 title: 'Please choose valid dates',
                 showConfirmButton: false,
                 timer: 1500
                })
             return;
           }
            this.availableBoatPeriod={
              startDate:  this.formatDate(this.start),
              endDate:  this.formatDate(this.end),
              username: this.email,
              propertyId: this.boatDto.id
             }
    
          axios.post("http://localhost:8081/boatsPeriod/setAvailableBoatsPeriod",this.availableBoatPeriod)
               .then(response => {
                      this.calendarOptions.events.push({id: this.boatDto.id ,title: 'Available', start: this.start , end: this.end , color: '#6f9681' })
                      this.start='',
                      this.end=''
                      this.$swal.fire({
                         position: 'top-end',
                         icon: 'success',
                         title: 'Available period successfully added!',
                         showConfirmButton: false,
                         timer: 2500
                      })
             
                 return response;    
                })
                .catch(err => {
                   console.log(err)
                   this.$swal.fire({
                   position: 'top-end',
                   icon: 'error',
                   title: 'Available period overlaps with existing period',
                   showConfirmButton: false,
                   timer: 2500
                   })
                })


        },
        dataIsValid(start,end){
              const date1 = new Date(start);
              const date2 = new Date(end);
              const currentDate = new Date();
              if((date1.getTime() - date2.getTime()) > 0){
                return false;
              }
              if(currentDate.getTime() - (date1.getTime()) > 0){
                return false;
              }
              return true;
         },
         editAvailablePeriod: function(){
                 if(!this.dataIsValid(this.unavailableStart,this.unavailableEnd)){
                    this.editDataIsNotValid=true
                    return
                 }

                 console.log("un start    "+this.unavailableStart)
                 console.log("un start   f "+this.formatDate(this.unavailableStart))
                  console.log("un start    "+this.unavailableEnd)
                 console.log("un start   f "+this.formatDate(this.unavailableEnd))
                  axios.post("http://localhost:8081/boatsPeriod/editAvailableBoatsPeriod",[
               {
                 id: null,
                startDate: this.formatDate(this.startEdit),
                endDate: this.formatDate(this.endEdit),
                username: this.email,
                propertyId: this.cabinId
                },
                {
                 id: null,
                startDate: this.formatDate(this.unavailableStart),
                endDate: this.formatDate(this.unavailableEnd),
                username: this.email,
                propertyId: this.cabinId
                }]

              )
              .then(response => {
                   this.calendarOptions.events=[]
                   this.getBoatsAvailablePeriod()
                  /* this.getQuickReservations()
                   this.getCabinReservations()*/
                   this.$swal.fire({
                    position: 'top-end',
                    icon: 'success',
                    title: 'Available period successfully edited!',
                    showConfirmButton: false,
                    timer: 2500
                   })
                    this.clearModalEdit()
                 return response;
              })
              .catch(err => {
                   console.log("usao u catch")
                   console.log(err)
                   this.$swal.fire({
                   position: 'top-end',
                   icon: 'error',
                   title: 'Available period can not be edited',
                   showConfirmButton: false,
                   timer: 2500
                   })
                   this.clearModalEdit()
                })

            },
            clearModalEdit: function(){
                 this.$refs.myRef.hide()
                 this.unavailableStart=null
                 this.unavailableEnd=null
                 this.editDataIsNotValid=false
                 this.argEventDeleting=null

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
