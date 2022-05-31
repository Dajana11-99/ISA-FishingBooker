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
             <Datepicker v-model="end" ></Datepicker>
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

<vue-modality ref="reservationInfo" title="Reservation information" hide-footer centered>

   <br>
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

<vue-modality ref="quickReservationInfo" title="Quick reservation information" hide-footer centered >

   <br>
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
             
                 <p><b>{{discountQuick}}%</b></p>
                
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
               <div class="col-sm-3" style="color: gray; text-align: left;">Cabin</div>
               <div class="col-sm-10" style=" width: 100%; text-align: left;" > 
                  <input class="form-control"  v-model="cabinName" readonly>
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
              <label style="color: gray;  " id="label">Price per night ($)</label>
              <input type="text" pattern="[1-9]+.?[0-9]*" v-model="cabinDto.price" class="form-control" disabled>
          </div>
      
              <div class="col form-group">
              <label style="color: gray;  " id="label">Discount (%)</label>
              <input type="number" min=1 max=100 v-model="discount" class="form-control" required>
          </div>
          
            <hr style="color: white;">
          <div class="col form-group">
                    <label style="color: gray;" id="label">Number of rooms </label>   
                    <input v-model="cabinDto.numOfRooms"  class="form-control" disabled>
                    </div> 

          <div class="col form-group">
                    <label style="color: gray; " id="label">Beds per room </label>   
                    <input v-model="cabinDto.bedsPerRoom"   class="form-control" disabled>
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
               <div class="col-sm-3" style="color: gray; text-align: left;">Cabin</div>
               <div class="col-sm-10" style=" width: 100%; text-align: left;" > 
                  <input class="form-control"  v-model="cabinName" readonly>
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
              <label style="color: gray; padding-top: 9%; " id="label">Price per night ($)</label>
              <input v-model="cabinDto.price" class="form-control" disabled>
              <div class="valid-feedback">Valid.</div>
              <div class="invalid-feedback">Please fill out this field.</div>
          </div>

          <div class="col form-group">
                    <label style="color: gray; padding-top: 9%; " id="label">Number of rooms </label>   
                    <input v-model="cabinDto.numOfRooms"  class="form-control" disabled>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
                    </div> 

          <div class="col form-group">
                    <label style="color: gray; padding-top: 9%;  " id="label">Beds per room </label>   
                    <input v-model="cabinDto.bedsPerRoom"   class="form-control" disabled>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
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
import Multiselect from '@vueform/multiselect'
import "@vueform/multiselect/themes/default.css"
import axios from "axios";
   export default{
     components: {
           CabinOwnerNavBar,
           FullCalendar,
           Datepicker,
           VueModality,
           Multiselect
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
         newPrice: 0,
         totalPriceQuickReservation: 0,
         startQuickReservation: null,
         endQuickReservation: null,
         startReservation: null,
         endReservation: null,
         startInfo: null,
         endInfo: null,
         startEdit: null,
         endEdit: null,
         value: null,
        options: [
        ],
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
        priceInfo: 0,
        usernameInfo: '',
        clientFullNameInfo: '',
        startQuickInfo: null,
        adServicesQuick: [],
        adServices: [],
        endQuickInfo: null,
        priceQuickInfo: null,
        usernameQuickInfo: null,
        clientQuickFullNameInfo: null,
        discountQuick: 0,
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
                  this.clientFullNameInfo=arg.event.extendedProps.clientFullName
                  this.adServices=arg.event.extendedProps.adServices
           }else if(arg.event.title=='QuickReservation'){
                  this.$refs.quickReservationInfo.open()
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
                  this.adServicesQuick=arg.event.extendedProps.adServicesQuick
           }
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
       availableCabinPeriod: {
                 id: null,
                startDate: null,
                endDate: null,
                username: '',
                propertyId: null
            },
            
            start: null,
            end: null,
            cabinId: null,
            additionalServicesToSend: [],
            totalPrice: 0,
            client: '',
            dateIsNotValid: false,
            editDataIsNotValid: false,
            dateIsNotValidQuick: false,
            argEventDeleting: null,
            discount: 0,
            ownersPart: 0
       }
     },
     mounted() {
       this.email = this.$route.params.email
       this.cabinName= this.$route.params.cabinName
       this.availableCabinPeriod.username=this.email
       this.getCabin()
      
       this.newPrice = this.cabinDto.price
       
     },
     methods: {
         getCabinsAvailablePeriod: function(){
               axios.post(process.env.VUE_APP_BACKEND_URL+"cabinsPeriod/getAvailablePeriod",this.cabinDto)
               .then(response => {
                  this.availableCabinPeriod=response.data
                     for( let newData of response.data ){
                                var start=newData.startDate
                                var end=newData.endDate
                                newData.startDate=this.setDate(start)
                                newData.endDate=this.setDate(end)
                              this.calendarOptions.events.push({id: newData.id ,title: 'Available', start: newData.startDate , end: newData.endDate , color: '#6f9681' })
                            }   
              })

         },
         getCabinReservations: function(){
               axios.get(process.env.VUE_APP_BACKEND_URL+"reservationCabin/getByCabinId/"+this.cabinId)
               .then(response => {
                     for( let newData of response.data ){
                                var start=newData.startDate
                                var end=newData.endDate
                                newData.startDate=this.setDate(start)
                                newData.endDate=this.setDate(end)
                                
                                  var temp=[]
                                  for(let i=0;i<newData.addedAdditionalServices.length;i++)
                                     temp.push(newData.addedAdditionalServices[i].name)
                                this.calendarOptions.events.push({ id: newData.id , extendedProps: {paymentInformation:newData.paymentInformationDto,adServices: temp, email: newData.clientUsername, price: newData.price, clientFullName: newData.clientFullName} ,title: 'Reservation', start: newData.startDate , end: newData.endDate})
                      }   
              })

         },
         getQuickReservations: function(){
               axios.get(process.env.VUE_APP_BACKEND_URL+"quickReservationCabin/getByCabinId/"+this.cabinId)
               .then(response => {
                     for( let newData of response.data ){
                                var start=newData.startDate
                                var end=newData.endDate
                                newData.startDate=this.setDate(start)
                                newData.endDate=this.setDate(end)
                                console.log("ja sam popust    "+newData.discount.toString())
                                
                                  var temp=[]
                                  for(let i=0;i<newData.addedAdditionalServices.length;i++)
                                     temp.push(newData.addedAdditionalServices[i].name)
                                this.calendarOptions.events.push({id: newData.id ,color: '#ffd04f', extendedProps: {paymentInformation:newData.paymentInformationDto,adServicesQuick: temp, discount: newData.discount, email: newData.clientUsername, price: newData.price, clientFullName: newData.clientFullName} ,title: 'QuickReservation', start: newData.startDate , end: newData.endDate})
                      }   
              })

         },
         getCabin: function(){
             this.cabinDto.name=this.cabinName
            
             axios.post(process.env.VUE_APP_BACKEND_URL+"cabins/findByName",this.cabinDto)
               .then(response => {
                         this.cabinDto=response.data
                         this.cabinId=this.cabinDto.id
                         for(let i =0; i< this.cabinDto.additionalServices.length; i++){
                               this.options.push({ 
                                  value: this.cabinDto.additionalServices[i].id,
                                  label: this.cabinDto.additionalServices[i].name+"-"+this.cabinDto.additionalServices[i].price+"$",
                                })
                         }
                         this.getCabinsAvailablePeriod();
                         this.getCabinReservations();
                         this.getQuickReservations();

             })
          },
          formatDate(formatDate) {
            const date = dayjs(formatDate);
            return date.format('YYYY-MM-DDTHH:mm:ss');
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
            this.availableCabinPeriod={
              startDate:  this.formatDate(this.start),
              endDate:  this.formatDate(this.end),
              username: this.email,
              propertyId: this.cabinId
             
             }
              axios.post(process.env.VUE_APP_BACKEND_URL+"cabinsPeriod/setAvailableCabinsPeriod",this.availableCabinPeriod)
              .then(response => {
                   this.calendarOptions.events.push({id: this.cabinId ,title: 'Available', start: this.start , end: this.end , color: '#6f9681' })
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
           setDate: function(newDate){
                    var date= new Date()
                    var splits =newDate.toString().split(",")
                    date.setDate( splits[1],splits[2], splits[0])
                    return new Date( parseInt(splits[0]), parseInt(splits[1])-1, parseInt(splits[2]),parseInt(splits[3]),parseInt(splits[4]))
           },
           createReservation: function(event){
                    event.preventDefault()
                    
          
                    if(!this.dataIsValid(this.startReservation,this.endReservation)){
                      this.dateIsNotValid=true
                      return;
                    }


                    if(this.startReservation != null && this.endReservation!=null){
                                 this.additionalServicesAdded()
                                 this.calculatePrice(this.startReservation,this.endReservation,this.cabinDto.price)
                                axios
                                .post(
                                process.env.VUE_APP_BACKEND_URL+"reservationCabin/ownerCreates",
                                {
                                id: null,
                                startDate: this.formatDate(this.startReservation),
                                endDate: this.formatDate(this.endReservation),
                                paymentInformationDto:{
                                  totalPrice: this.totalPrice,
                                  companysPart: 0,
                                  ownersPart: 0
                                },
                                cabinDto: this.cabinDto,
                                addedAdditionalServices: this.additionalServicesToSend,
                                clientUsername: this.client,
                                ownerWroteAReport: false,
                                ownersUsername: this.email,
                                })
                              .then((response) => {
                                      console.log(response)
                                      this.calendarOptions.events.push({extendedProps: {email: this.client, price: this.totalPrice, clientFullName: ''} ,title: 'Reservation', start: this.startReservation , end: this.endReservation})
                                      this.clearModalReservation()
                                       this.$swal.fire({
                                          position: 'top-end',
                                          icon: 'success',
                                          title: 'Successfull reservation!',
                                          showConfirmButton: false,
                                           timer: 1500
                                       })
                                       
                                     
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
           },
           createQuickReservation: function(event){
                    event.preventDefault()
          
                    if(!this.dataIsValid(this.startQuickReservation,this.endQuickReservation)){
                      this.dateIsNotValidQuick=true
                      return;
                    }


                    if(this.startQuickReservation != null && this.startQuickReservation!=null){
                                 this.additionalServicesAdded()
                                 this.calculatePrice(this.startQuickReservation,this.endQuickReservation,this.cabinDto.price)
                                axios
                                .post(
                                process.env.VUE_APP_BACKEND_URL+"quickReservationCabin/ownerCreates",
                                {
                                id: null,
                                startDate: this.formatDate(this.startQuickReservation),
                                endDate: this.formatDate(this.endQuickReservation),
                                paymentInformationDto:{
                                  totalPrice: this.totalPrice,
                                  companysPart: 0,
                                  ownersPart: 0
                                },
                                discount: this.discount,
                                cabinDto: this.cabinDto,
                                addedAdditionalServices: this.additionalServicesToSend,
                                clientUsername: this.client,
                                ownerWroteAReport: false,
                                ownersUsername: this.email,

                              
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
           additionalServicesAdded: function(){
                  
                    this.additionalServicesToSend=[]
                    if(this.value != null){
                    for(let i=0; i < this.value.length ; i++){
                          for(let j=0; j < this.cabinDto.additionalServices.length ; j++){
                            if(this.value[i] == this.cabinDto.additionalServices[j].id )
                               this.additionalServicesToSend.push(this.cabinDto.additionalServices[j])
                          }
                    }
                    }

           },
           calculatePrice: function(start,end,pricePerNight) {
              let numOfDays = this.getNumberOfDays(start, end);
              this.totalPrice = numOfDays * pricePerNight;
              for (let i = 0; i < this.additionalServicesToSend.length; i++) {
                this.totalPrice += this.additionalServicesToSend[i].price*numOfDays;
              }
            },
            getNumberOfDays: function(start,end) {
               const date1 = new Date(start);
               const date2 = new Date(end);
               const oneDay = 1000 * 60 * 60 * 24;
               const diffInTime = date2.getTime() - date1.getTime();
               const diffInDays = Math.round(diffInTime / oneDay);
               return diffInDays;
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
            clearModalReservation: function(){
                 this.calendarOptions.events=[]
                   this.getCabinsAvailablePeriod()
                   this.getQuickReservations()
                   this.getCabinReservations()
                 this.$refs.makeReservation.hide()
                 this.startReservation=null
                 this.endReservation=null
                 this.dateIsNotValid=false
                 this.client=''
                 this.value=[]
                 this.totalPrice=0
            },
            clearModalQuick: function(){
                this.calendarOptions.events=[]
                   this.getCabinsAvailablePeriod()
                   this.getQuickReservations()
                   this.getCabinReservations()
                 this.$refs.makeQuickReservation.hide()
                 this.startQuickReservation=null
                 this.endQuickReservation=null
                 this.dateIsNotValidQuick=false
                 this.value=[]
                 this.totalPrice=0
                 this.discount=0
            },
            editAvailablePeriod: function(){
                 if(!this.dataIsValid(this.unavailableStart,this.unavailableEnd)){
                    this.editDataIsNotValid=true
                    return
                 }
                  axios.post(process.env.VUE_APP_BACKEND_URL+"cabinsPeriod/editAvailableCabinsPeriod",[
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
                   this.getCabinsAvailablePeriod()
                   this.getQuickReservations()
                   this.getCabinReservations()
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
            save: function(){
                 this.$refs.myRef.hide()
            },
            openQuickReservationModal: function(){
                 this.$refs.makeQuickReservation.open()
            },
            deleteAvailablePeriod: function(){
              axios.post(process.env.VUE_APP_BACKEND_URL+"cabinsPeriod/deleteAvailableCabinsPeriod",
               {
                 id: null,
                startDate: this.formatDate(this.startEdit),
                endDate: this.formatDate(this.endEdit),
                username: this.email,
                propertyId: this.cabinId
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