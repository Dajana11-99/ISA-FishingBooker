<template>
  <FishingInstructorNavbar :username=email />
    <div class="card bg-light text-light" style="margin: 5%">
    <div class="content">
      <FullCalendar :options="calendarOptions"    @select="handleSelect" class="calendar"  />
      <div class="info">
        <h2>Timetable</h2>

       
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
  
<vue-modality ref="reservationInfo" title="Reservation information" hide-footer centered>

   <br>
    <div class="row">
          <div class="col-sm-3" style="padding-top: 1%; text-align: left; color: gray;">
            <p>Adventure</p>
          </div>
          <div class="col-sm-9" style="padding: 1%; text-align: left;">
             <p><b>{{adventureNameInfo}}</b></p>
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
</vue-modality>

<vue-modality ref="makeQuickReservation" title="Create quick reservation" hide-footer hide-header centered width="900px" no-close-on-backdrop=true no-close-on-esc=true>

   <br>
         <div style="text-align: left;">
         <h4><b>Create quick reservation</b></h4>
         </div>
         <hr>
         <form @submit="createQuickReservation" method="post" class="was-validated">
 
        
             <div class="row">
           <div class=col>
             <div class="row">
               <div class="col-sm-3" style="color: gray; text-align: left;">Adventure</div>
               <div class="col-sm-10" style=" width: 100%; text-align: left;" > 

                  <select @change="switchSelect($event)" v-model="selected" class="form-select" aria-label="Default select example" required>
                    <option   v-for="(adventure,index) in adventureDto" :key="index" :value="index" >{{adventure.name}}</option>
                  </select>

               </div> 
          </div>
            
            <br>
            
              <div class="row">
          <div class="col" style="padding-top: 2%; padding-left: 2.5%; text-align: left;">
            <h6 style="color: gray;">From</h6>
          </div>
          <div class="col-sm-9"  style="padding: 2.5%;">
             <Datepicker  v-model="startQuickReservation" ></Datepicker>
          </div>
        </div>
               <div class="row">
          <div class="col" style="padding-top: 2%; padding-left: 2.5%; text-align: left;">
            <h6 style="color: gray;">To</h6>
          </div>
          <div class="col-sm-9"  style="padding: 2.5%;">
             <Datepicker  v-model="endQuickReservation"></Datepicker>
          </div>
        </div>

 
  
        </div>
        <div class="col">
                
          
          <div class="row">
              <div class="col form-group">
              <label style="color: gray;  " id="label">Price per hour ($)</label>
              <input type="text" pattern="[1-9]+.?[0-9]*" v-model="adventureDto[selected].price" class="form-control" disabled>

          </div>
          <div class="col form-group">
              <label style="color: gray;  " id="label">Discount (%)</label>
              <input type="text" pattern="[1-9]+.?[0-9]*" v-model="discount" class="form-control" required>
          </div>
          
            <hr style="color: white;">

          <div class="col form-group">
              <label style="color: gray;" id="label">Max people</label>
              <input v-model="adventureDto[selected].maxPeople" class="form-control" disabled>
          </div>

             <div class="col form-group">
                    <label style="color: gray;  " id="label">Canceling</label>   
                    <input v-model="adventureDto[selected].cancelingCondition"   class="form-control" disabled>
          </div> 

          

          </div> 
          <br>
          <div class="col">
                    <label style="color: gray;  padding-top: 1%; " id="label">Additional services </label>  
                     <Multiselect   style="color: gray; padding-bottom: 5%; " 
                           v-model="value"
                          mode="tags"
                          :close-on-select="false"
                          :searchable="true"
                          :create-option="false"
                          :options="options"
                          
                        />
          </div> 

              
     
        </div>
</div>
      <div  style="text-align: left;">
      <label style="color: red;" v-if="startQuickReservation==null || endQuickReservation==null || dateIsNotValidQuick==true">Please enter valid dates.</label>
         </div>
        <hr>
        
        <button @click="clearModalQuick()" type="button" class="btn btn-secondary" style="margin-right: 1%; width: 10%;">Close</button>
      
        <button type="submit"  class="btn btn-success" style="margin-left: 1%; width: 10%;" >Create</button>

       </form>
  
 
</vue-modality>

  <vue-modality ref="makeReservation" title="Create reservation" hide-footer hide-header centered width="900px" no-close-on-backdrop=true no-close-on-esc=true>

   <br>
         <div style="text-align: left;">
         <h4><b>Create reservation</b></h4>
         </div>
         <hr>

          <form @submit="createReservation" method="post" class="was-validated">
    
        
             <div class="row">
           <div class=col>
             <div class="row">
               <div class="col-sm-3" style="color: gray; text-align: left;">Adventure</div>
               <div class="col-sm-10" style=" width: 100%; text-align: left;" > 

                  <select @change="switchSelect($event)" v-model="selected" class="form-select" aria-label="Default select example" required>
                    <option   v-for="(adventure,index) in adventureDto" :key="index" :value="index" >{{adventure.name}}</option>
                  </select>

               </div> 
          </div>
             
            <br>
            
              <div class="row">
          <div class="col" style="padding-top: 2%; padding-left: 2.5%; text-align: left;">
            <h6 style="color: gray;">From</h6>
          </div>
          <div class="col-sm-9"  style="padding: 2.5%;">
             <Datepicker  v-model="startReservation" ></Datepicker>
          </div>
        </div>
               <div class="row">
          <div class="col" style="padding-top: 2%; padding-left: 2.5%; text-align: left;">
            <h6 style="color: gray;">To</h6>
          </div>
          <div class="col-sm-9"  style="padding: 2.5%;">
             <Datepicker  v-model="endReservation"></Datepicker>
          </div>
        </div>

 
  
        </div>
        <div class="col">
                
              <div class="row">
               <div class="col-sm-3" style="color: gray; text-align: left;">Client</div>
               <div class="col-sm-10" style=" width: 100%; text-align: left;" > 
                  <input class="form-control" type="text"  v-model="client" required>
               </div>
          </div>
          
               <div class="row">
        <div class="col form-group">
              <label style="color: gray; padding-top: 9%; " id="label">Price per hour ($)</label>
              <input v-model="adventureDto[selected].price" class="form-control" disabled>
          </div>

          <div class="col form-group">
                    <label style="color: gray; padding-top: 9%; " id="label">Max people </label>   
                    <input v-model="adventureDto[selected].maxPeople"  class="form-control" disabled>
                    </div> 

          <div class="col form-group">
                    <label style="color: gray; padding-top: 9%;  " id="label">Canceling</label>   
                    <input v-model="adventureDto[selected].cancelingCondition"   class="form-control" disabled>
          </div> 

          

          </div> 
          
          <div class="col">
                    <label style="color: gray;  padding-top: 1%; " id="label">Additional services </label>  
                     <Multiselect   style="color: gray; padding-bottom: 5%; " 
                           v-model="value"
                          mode="tags"
                          :close-on-select="false"
                          :searchable="true"
                          :create-option="false"
                          :options="options"
                          
                        />
          </div> 

              
     
        </div>
</div>
      <div  style="text-align: left;">
      <label style="color: red;" v-if="startReservation==null || endReservation==null || dateIsNotValid==true">Please enter valid dates.</label>
         </div>
     
       <hr>
        <button @click="clearModalReservation()" type="button" class="btn btn-secondary" style="margin-right: 1%; width: 10%;">Close</button>
        <button type="submit"  class="btn btn-success" style="margin-left: 1%; width: 10%;" >Create</button>
    
       </form>
</vue-modality>



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


<vue-modality ref="quickReservationInfo" title="Quick reservation information" hide-footer centered >

   <br>
        <div class="row">
          <div class="col-sm-3" style="padding-top: 1%; text-align: left; color: gray;">
            <p>Adventure</p>
          </div>
          <div class="col-sm-9" style="padding: 1%; text-align: left; ">
             
                  <p ><b>{{adventureNameQuick}}</b></p>
                
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
            <p>Owners part</p>
          </div>
          <div class="col-sm-9" style="padding: 1%; text-align: left; ">
             
                 <p><b>{{discountQuick}}%</b></p>
                
          </div>
        </div>

         <div class="row">
          <div class="col-sm-3" style="padding-top: 1%; text-align: left; color: gray;">
            <p>Discount</p>
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
  
 
</vue-modality>
</template>

<script>import { ref} from "vue";
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

import Multiselect from '@vueform/multiselect'
import FishingInstructorNavbar from './FishingInstructorNav.vue'
import axios from "axios";
axios.defaults.baseURL = process.env.VUE_APP_URL;
export default {
   props: {
       role: null,
   },
  components: {
    FullCalendar,
    Datepicker,
    VueModality,
    Multiselect,
   FishingInstructorNavbar

  
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
      startQuickReservation: null,
      endQuickReservation:null,
      dateIsNotValidQuick: false,
      clientQuickFullNameInfo: '',
      editDataIsNotValid: false,
      unavailableStart: null,
      unavailableEnd:  null,
      startEdit: null,
      endEdit: null,
      discount: 0,
      adventureNameInfo: '',
      priceQuickInfo: 0,
      usernameQuickInfo: '',
      startQuickInfo: null,
      endQuickInfo: null,
      discountQuick: 0,
      adventureNameQuick: '',
      startInfo:null,
      endInfo:null,
      clientFullNameInfo: '',
      usernameInfo: '',
      currentEvent: "",
      priceInfo: 0,
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
          }else if(arg.event.title=='Reservation'){
                  this.$refs.reservationInfo.open()
                  this.startInfo=arg.event.start
                  this.endInfo=arg.event.end
                  this.priceInfo=arg.event.extendedProps.paymentInformation.ownersPart
                  this.usernameInfo=arg.event.extendedProps.email
                  this.adServices=arg.event.extendedProps.adServices
                  this.adventureNameInfo=arg.event.extendedProps.adventureName
                  this.clientFullNameInfo=arg.event.extendedProps.clientFullName
           }else if(arg.event.title=='QuickReservation'){
                  this.$refs.quickReservationInfo.open()
                  this.adventureNameQuick=arg.event.extendedProps.adventureName
                  this.startQuickInfo=arg.event.start
                  this.endQuickInfo=arg.event.end
                  this.priceQuickInfo=arg.event.extendedProps.paymentInformation.totalPrice
                  this.ownersPart=arg.event.extendedProps.paymentInformation.ownersPart
                  this.discountQuick=arg.event.extendedProps.discount
                  if(arg.event.extendedProps.email==null || arg.event.extendedProps.email==''){
                       this.usernameQuickInfo="Not reservated yet."
                       this.clientQuickFullNameInfo="Not reservated yet."

                  }else{
                  this.usernameQuickInfo=arg.event.extendedProps.email
                  this.clientQuickFullNameInfo=arg.event.extendedProps.clientFullName
                  }
                  this.adServicesQuick=arg.event.extendedProps.adServices

           }
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
     
           availableInstructorPeriod:{
                 id: null,
                startDate: null,
                endDate: null,
                username: ''
            },
            state: [],
            modalActive: false,
            email: '',
                   adventureDto: [{
              id: null,
              name: '',
                address: {
                  longitude: 0.0,
                  latitude: 0.0,
                  country: '',
                  city: '',    
                  streetAndNum: ''
              },
              description: '',

             instructorsBiography: '',

                images: [{
                 id: null,
                 url: '',
                 cabin: ''

             }],
             maxPeople: 1,
             price: 1,
             rules: '',
             equipment: '',
             additionalServices: [{
                 id: null,
                 name: '',
                 price: 0.0

             }],
             cancelingCondition: '',
          
             fishingInstructorUsername: ''
         }],
         
        selected: 0,
        startReservation: null,
        endReservation: null,
        dateIsNotValid: false,
        adventureName: '',
        client: '',
        value: null,
        options: [ ], 
        additionalServicesToSend: [],
        newPrice: 0,
        totalPrice: 0.0,
        cancelingCondition : '',
        maxPeople: 0,
        adServices: [],
        adServicesQuick: [],
        argEventDeleting: null,
        ownersPart: 0
    };
  },
 
     mounted() {
       this.email = this.$route.params.email;
         this.getAdventure()
    
     },
     methods: {
         getAdventure: function(){
            axios.get(process.env.VUE_APP_BACKEND_URL+"adventures/findInstructorsAdventure/"+this.email+"/")
               .then(response => {
                        this.adventureDto=response.data
                        if(this.adventureDto.length> 0){
                        for(let i=0; i< this.adventureDto[0].additionalServices.length ; i++){
                               this.options.push({ 
                                  value: this.adventureDto[0].additionalServices[i].id,
                                  label: this.adventureDto[0].additionalServices[i].name+"-"+this.adventureDto[0].additionalServices[i].price+"$ per hour",
                                })
                            this.newPrice= this.adventureDto[0].price
                        }
                        
                        this.getInstructorsReservation()
                        this.getInstructorsQuickReservation()

                         }
                        this.getAvailableInstructorsPeriod();
                       
              })

       },
       switchSelect(event){
        this.options=[]
        this.value= []
        let idx=event.target.value
             for(let i =0; i< this.adventureDto[idx].additionalServices.length; i++){
                               this.options.push({ 
                                  value: this.adventureDto[idx].additionalServices[i].id,
                                  label: this.adventureDto[idx].additionalServices[i].name+"-"+this.adventureDto[idx].additionalServices[i].price+"$ per hour",
                                })
                                this.newPrice=this.adventureDto[idx].price

              }
       },
        getAvailableInstructorsPeriod: function(){
              axios.get(process.env.VUE_APP_BACKEND_URL+"instructorsPeriod/getAvailablePeriod/"+this.email+"/")
                        .then(response => {
                            for( let newData of response.data ){
                                var start=newData.startDate
                                var end=newData.endDate
                                newData.startDate=this.setDate(start)
                                newData.endDate=this.setDate(end)
                              this.calendarOptions.events.push({id: newData.id ,title: 'Available', start: newData.startDate , end: newData.endDate,color: '#6f9681' })
                              this.state.push( newData.startDate)
                            }
              })   
        },
         getInstructorsReservation: function(){
                axios.get(process.env.VUE_APP_BACKEND_URL+"reservationAdventure/getByInstructorUsername/"+this.email+"/")
                        .then(response => {
                            for( let newData of response.data ){
                                var start=newData.startDate
                                var end=newData.endDate
                                newData.startDate=this.setDate(start)
                                newData.endDate=this.setDate(end)
                                  var temp=[]
                                  for(let i=0;i<newData.addedAdditionalServices.length;i++)
                                     temp.push(newData.addedAdditionalServices[i].name)

                              this.calendarOptions.events.push({id: newData.id , extendedProps: {paymentInformation:newData.paymentInformationDto,adventureName: newData.adventureDto.name,email: newData.clientUsername, price: newData.price, clientFullName: newData.clientFullName,adServices: temp} ,title: 'Reservation', start: newData.startDate , end: newData.endDate})
                              this.state.push( newData.startDate)
                            }
              })   
          }  , 
           getInstructorsQuickReservation: function(){
                axios.get(process.env.VUE_APP_BACKEND_URL+"quickReservationAdventure/getByInstructorId/"+this.email+"/")
                        .then(response => {
                            for( let newData of response.data ){
                                var start=newData.startDate
                                var end=newData.endDate
                                newData.startDate=this.setDate(start)
                                newData.endDate=this.setDate(end)
                                  var temp=[]
                                  for(let i=0;i<newData.addedAdditionalServices.length;i++)
                                     temp.push(newData.addedAdditionalServices[i].name)
                              this.calendarOptions.events.push({id: newData.id ,color: '#ffd04f', extendedProps: {paymentInformation:newData.paymentInformationDto,adventureName: newData.adventureDto.name,discount: newData.discount,email: newData.clientUsername, price: newData.price, clientFullName: newData.clientFullName,adServices: temp} ,title: 'QuickReservation', start: newData.startDate , end: newData.endDate})
                              this.state.push( newData.startDate)

                            }
              })   
          }  , 
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
            this.availableInstructorPeriod={
            startDate:  this.formatDate(this.start),
            endDate:  this.formatDate(this.end),
            username: this.email,
            propertyId: null
            }
               axios
               .post(process.env.VUE_APP_BACKEND_URL+"instructorsPeriod/setAvailableInstructorPeriod",this.availableInstructorPeriod)
               .then(response => {
                       
                  this.calendarOptions.events.push({title: 'Available', start: this.start , end: this.end , color: '#6f9681' })
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
           createReservation: function(event){
                    event.preventDefault()
                    if(!this.dataIsValid(this.startReservation,this.endReservation)){
                      this.dateIsNotValid=true
                      return;
                    }
                    if(this.startReservation != null && this.endReservation!=null){
                                 this.additionalServicesAdded()
                                 this.calculatePrice(this.startReservation,this.endReservation,this.adventureDto[this.selected].price)
                                axios
                                .post(
                                process.env.VUE_APP_BACKEND_URL+"reservationAdventure/instructorCreates",
                                {
                                id: null,
                                startDate: this.formatDate(this.startReservation),
                                endDate: this.formatDate(this.endReservation),
                                paymentInformationDto:{
                                  totalPrice: this.totalPrice,
                                  companysPart: 0,
                                  ownersPart: 0
                                },
                                ownerWroteAReport: false,
                                ownersUsername: this.email,
                                adventureDto: this.adventureDto[this.selected],
                                addedAdditionalServices: this.additionalServicesToSend,
                                clientUsername: this.client,
                                })
                              .then((response) => {
                                      console.log(response)
                                    
                                       this.$swal.fire({
                                          position: 'top-end',
                                          icon: 'success',
                                          title: 'Successfull reservation!',
                                          showConfirmButton: false,
                                           timer: 1500
                                       })
                                       
                                       this.clearModalReservation()
                                })
                              .catch((err) =>{
                                     console.log(err)
                                       this.$swal.fire({
                                        icon: 'error',
                                        title: 'Oops...',
                                        text: 'Something went wrong!',
                               })
                                    this.clearModalReservation()
                              })
                              
                                 
                    }
           },   createQuickReservation: function(event){
                    event.preventDefault()
          
                    if(!this.dataIsValid(this.startQuickReservation,this.endQuickReservation)){
                      this.dateIsNotValidQuick=true
                      return;
                    }

                  
                    if(this.startQuickReservation != null && this.startQuickReservation!=null){
                                 this.additionalServicesAdded()
                                 this.calculatePrice(this.startQuickReservation,this.endQuickReservation,this.adventureDto[this.selected].price)
                                   this.loader = this.$loading.show({
                                    container: this.fullPage ? null : this.$refs.formContainer,
                                    canCancel: true,
                                    onCancel: this.onCancel,
                                  });
                                axios
                                .post(
                                process.env.VUE_APP_BACKEND_URL+"quickReservationAdventure/instructorCreates/",
                                {
                                id: null,
                                startDate: this.formatDate(this.startQuickReservation),
                                endDate: this.formatDate(this.endQuickReservation),
                                paymentInformationDto:{
                                  totalPrice: this.totalPrice,
                                  companysPart: 0,
                                  ownersPart: 0
                                },
                                ownerWroteAReport: false,
                                ownersUsername: this.email,
                                adventureDto: this.adventureDto[this.selected],
                                addedAdditionalServices: this.additionalServicesToSend,
                                clientUsername: this.client,
                                discount: this.discount
                                })
                              .then((response) => {
                                    console.log(response)
                                    
                                     this.$swal.fire({
                                          position: 'top-end',
                                          icon: 'success',
                                          title: 'Successfully created quick reservation!',
                                          showConfirmButton: false,
                                           timer: 1500
                                       })
                                           this.loader.hide();
                                          this.loader=null
                                         this.clearModalQuick()
                                })
                              .catch((err) =>{
                                      this.$refs.makeQuickReservation.hide()
                                     console.log(err)
                                       this.$swal.fire({
                                        icon: 'error',
                                        title: 'Oops...',
                                        text: 'Something went wrong!',
                                      })
                                    this.clearModalQuick()
                              })
                              
                                 
                    }

           },
              editAvailablePeriod: function(){
                 if(!this.dataIsValid(this.unavailableStart,this.unavailableEnd)){
                    this.editDataIsNotValid=true
                    return
                 }

                  axios.post(process.env.VUE_APP_BACKEND_URL+"instructorsPeriod/editAvailableInstructorsPeriod",[
               {
                 id: null,
                startDate: this.formatDate(this.startEdit),
                endDate: this.formatDate(this.endEdit),
                username: this.email,
                },
                {
                 id: null,
                startDate: this.formatDate(this.unavailableStart),
                endDate: this.formatDate(this.unavailableEnd),
                username: this.email,
                }]

              )
              .then(response => {
                   this.calendarOptions.events=[]
                   
                    this.getAvailableInstructorsPeriod();
                    this.getInstructorsReservation()
                    this.getInstructorsQuickReservation()
                   
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
               additionalServicesAdded: function(){
                  
                    this.additionalServicesToSend=[]
                    if(this.value != null){
                    for(let i=0; i < this.value.length ; i++){
                          for(let j=0; j < this.adventureDto[this.selected].additionalServices.length ; j++){
                            if(this.value[i] == this.adventureDto[this.selected].additionalServices[j].id ){
                               this.additionalServicesToSend.push(this.adventureDto[this.selected].additionalServices[j])
                            }
                          }
                    }
                    }

           },
           calculatePrice: function(start,end,pricePerHour) {
              let numOfHours = this.getNumberOfHours(start, end);
              this.totalPrice = numOfHours * pricePerHour;
              for (let i = 0; i < this.additionalServicesToSend.length; i++) {
                this.totalPrice += this.additionalServicesToSend[i].price*numOfHours;
              }
            },
            getNumberOfHours: function(start,end) {
               const date1 = new Date(start);
               const date2 = new Date(end);
               let diffInHours= Math.abs(date1-date2)/36e5
               return diffInHours;
           },
           clearModalReservation: function(){
                 this.calendarOptions.events=[]
                 this.getAvailableInstructorsPeriod();
                 this.getInstructorsReservation()
                 this.getInstructorsQuickReservation()
                 this.$refs.makeReservation.hide()
                 this.startReservation=null
                 this.endReservation=null
                 this.dateIsNotValid=false
                 this.client=''
                 this.value=[]
                 this.options =[]
                 this.totalPrice=0
                 this.selected= 0
                     let idx=0
                 for(let i =0; i< this.adventureDto[idx].additionalServices.length; i++){

                               this.options.push({ 
                                  value: this.adventureDto[idx].additionalServices[i].id,
                                  label: this.adventureDto[idx].additionalServices[i].name+"-"+this.adventureDto[idx].additionalServices[i].price+"$ per hour",
                                })
              }
            },
             clearModalQuick: function(){
                this.calendarOptions.events=[]
                this.getAvailableInstructorsPeriod();
                this.getInstructorsReservation()
                this.getInstructorsQuickReservation()
                 this.$refs.makeQuickReservation.hide()
                 this.startQuickReservation=null
                 this.endQuickReservation=null
                 this.dateIsNotValidQuick=false
                 this.value=[]
                 this.options =[]
                 this.totalPrice=0
                 this.selected= 0,
                 this.discount =0
                 let idx=0
                 for(let i =0; i< this.adventureDto[idx].additionalServices.length; i++){
                               this.options.push({ 
                                  value: this.adventureDto[idx].additionalServices[i].id,
                                  label: this.adventureDto[idx].additionalServices[i].name+"-"+this.adventureDto[idx].additionalServices[i].price+"$ per hour",
                                })
                        }
                 this.newPrice=this.adventureDto[0].price;
            },
                 deleteAvailablePeriod: function(){
                  axios.post(process.env.VUE_APP_BACKEND_URL+"instructorsPeriod/deleteAvailableInstructorsPeriod",
                  {
                    id: null,
                    startDate: this.formatDate(this.startEdit),
                    endDate: this.formatDate(this.endEdit),
                    username: this.email,
                    }
                  )
                  .then(response => {
                      this.argEventDeleting.remove()
                      this.$swal.fire({
                        position: 'top-end',
                        icon: 'success',
                        title: 'Available period successfully deleted!',
                        showConfirmButton: false,
                        timer: 2500
                      })
                        this.clearModalEdit()
                    return response;
                  })
                  .catch(err => {
                    
                      console.log(err)
                      this.$swal.fire({
                      position: 'top-end',
                      icon: 'error',
                      title: 'Available period can not be deleted',
                      showConfirmButton: false,
                      timer: 2500
                      })
                      this.clearModalEdit()
                    })

             
            }
     }
    
  }

</script> 

<style lang="postcss" scoped>
::-webkit-scrollbar {
  width: 0px;
}
::-webkit-scrollbar-track {
  display: none;
}
/deep/ .custom-calendar.vc-container {
  --day-border: 1px solid #b8c2cc;
  --day-border-highlight: 1px solid #b8c2cc;
  --day-width: 90px;
  --day-height: 90px;
  --weekday-bg: #f8fafc;
  --weekday-border: 1px solid #eaeaea;
  border-radius: 0;
  width: 100%;
  & .vc-header {
    background-color: #f1f5f8;
    padding: 10px 0;
  }
  & .vc-weeks {
    padding: 0;
  }
  & .vc-weekday {
    background-color: var(--weekday-bg);
    border-bottom: var(--weekday-border);
    border-top: var(--weekday-border);
    padding: 5px 0;
  }
  & .vc-day {
    padding: 0 5px 3px 5px;
    text-align: left;
    height: var(--day-height);
    min-width: var(--day-width);
    background-color: white;
    &.weekday-1,
    &.weekday-7 {
      background-color: #eff8ff;
    }
    &:not(.on-bottom) {
      border-bottom: var(--day-border);
      &.weekday-1 {
        border-bottom: var(--day-border-highlight);
      }
    }
    &:not(.on-right) {
      border-right: var(--day-border);
    }
  }
  & .vc-day-dots {
    margin-bottom: 5px;
  }
}
</style>