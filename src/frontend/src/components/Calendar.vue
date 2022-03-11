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
    
<!--<shapla-modal :active="modalActive" title="" content-size="medium" @close="closeModal">
    <h2 style="color: blue;">Define new available period</h2>
    <div class="row">
        <div class="col">
           <button type="button" class="btn btn-secondary" @click="modalActive=false">Close</button>
        </div>
        <div class="col">
           <button type="button" class="btn btn-primary" @click="modalActive=false">Save</button>
        </div>
    </div>
</shapla-modal>-->

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
//import ShaplaModal from '@shapla/vue-modal';
import '@shapla/vue-modal/dist/style.css';
import VueModality from 'vue-modality-v3'

import axios from "axios";

axios.defaults.baseURL = process.env.VUE_APP_URL;
export default {
   props: {
       role: null,
   },
  components: {
    FullCalendar, // make the <FullCalendar> tag available
    Datepicker,
    //ShaplaModal,
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
         // this.modalActive=true
         this.$refs.myRef.open()
         
// or close it by calling:
        //  this.$refs.myRef.hide()
          this.start=arg.event.start
          this.end=arg.event.end
          console.log("JAAAAAAS"+arg.event.title)
           console.log("JAAAAAAS"+arg.event.id)
          console.log("JAAAAAAS"+arg.event.start)
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
            availableInstructorPeriodDtoSet: [{
                 id: null,
                startDate: null,
                endDate: null,
                instructorUsername: ''
            }]

          },
           availableInstructorPeriod: [{
                 id: null,
                startDate: null,
                endDate: null,
                instructorUsername: ''
            }],
            state: [],
            modalActive: false
         
           
      
     
    };
  },
 
  mounted() {


               
     if(this.$props.role ==='instructor'){
             axios.get("http://localhost:8081/userc/getUsername",{
            headers: {
            "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
            "Authorization": "Bearer " + localStorage.jwt ,
            }
             })
               .then(response => {
                       this.userRequestDto.username= response.data
                       this.fishingInstructorDtos.username=response.data
                       axios.post("http://localhost:8081/instructorsPeriod/getAvailablePeriod",this.userRequestDto,{
                        headers: {
                        "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
                        "Authorization": "Bearer " + localStorage.jwt ,
                        }
                    
                        })
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


     

            
   


   /* axios
      .get("/users/getRole", {
        headers: {
          "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
          Authorization: "Bearer " + localStorage.refreshToken,
        },
      })
      .then((res) => {
        let loggedInRole = res.data;
        if (loggedInRole == "ROLE_VACATION_HOME_OWNER") {
          this.entityType = "cottage";
          axios
            .get("/vacationHome/getNamesByUser", {
              headers: {
                "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
                Authorization: "Bearer " + localStorage.refreshToken,
              },
            })
            .then((res) => {
              this.selectData = res.data;
              for (let data of this.selectData) {
                axios
                  .get(
                    "/availabilityDate/getByServiceProfile/" +
                      data.code,
                    {
                      headers: {
                        "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
                        Authorization: "Bearer " + localStorage.refreshToken,
                      },
                    }
                  )
                  .then((res) => {
                    for (let newData of res.data) {
                      newData.title = data.label;
                      newData.url = "dateRange";
                      newData.defId = newData.id;
                      newData.startDate = new Date(newData.start);
                      newData.endDate = new Date(newData.end);
                      newData.serviceId = data.code;
                      this.calendarOptions.events.push(newData);
                    }
                  });
                  
              }
            });
        } else if (loggedInRole == "ROLE_BOAT_OWNER") {
          this.entityType = "boat";
          axios
            .get("/boat/getNamesByUser", {
              headers: {
                "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
                Authorization: "Bearer " + localStorage.refreshToken,
              },
            })
            .then((res) => {
              this.selectData = res.data;
              for (let data of this.selectData) {
                axios
                  .get(
                    "/availabilityDate/getByServiceProfile/" +
                      data.code,
                    {
                      headers: {
                        "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
                        Authorization: "Bearer " + localStorage.refreshToken,
                      },
                    }
                  )
                  .then((res) => {
                    for (let newData of res.data) {
                      newData.title = data.label;
                      newData.url = "dateRange";
                      newData.defId = newData.id;
                      newData.serviceId = data.code;
                      newData.startDate = new Date(newData.start);
                      newData.endDate = new Date(newData.end);
                      this.calendarOptions.events.push(newData);
                    }
                  });
              }
            });
        }else if (loggedInRole == "ROLE_FISHING_INSTRUCTOR") {
           this.entityType = "adventure";
            axios
              .get("/fishingInstructor/getAllAvailabilitiesForInstructor", {
                headers: {
                  "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
                  Authorization: "Bearer " + localStorage.refreshToken,
                },
              })
              .then((res) => {
                                   console.log(res.data)
                for (let newData of res.data) {
                      newData.title = "Availability";
                      newData.url = "dateRange";
                      newData.defId = newData.id;
                      newData.serviceId =newData.id;
                      newData.start = new Date(newData.startDate);
                      newData.end = new Date(newData.endDate);
                      this.calendarOptions.events.push(newData);
                    }
              });
        } else {
          window.location.href = "/";
        }
        axios
          .get(
            "/reservation/allByAdvertiser/",
              {
                headers: {
                "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
                Authorization: "Bearer " + localStorage.refreshToken,
                },
              }
              )
                  .then((res) => {
                    for (let newData of res.data) {
                      newData.title = "Reservation: " + newData.name + " by " +  newData.clientName + " " +  newData.clientSurname;
                      newData.url = "reservation";
                      newData.defId = "re" + newData.reservationId;
                      newData.start = new Date(newData.startDate);
                      newData.end = new Date(newData.endDate);
                      newData.serviceId = newData.reservationId;
                      newData.color = "#4B0082";
                      this.calendarOptions.events.push(newData);
                    }
                  });
        axios
          .get(
            "/appointment/getOffersByAdvertiser/",
              {
                headers: {
                "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
                Authorization: "Bearer " + localStorage.refreshToken,
                },
              }
              )
                  .then((res) => {
                    for (let newData of res.data) {
                      newData.title = "Special offer: " + newData.serviceProfileName + " (" + newData.discount + "%)";
                      newData.defId = "so" + newData.offerId;
                      newData.start = new Date(newData.startDate);
                      newData.end = new Date(newData.endDate);
                      newData.serviceId = newData.serviceProfileId;
                      newData.color = "#8B0000";
                      this.calendarOptions.events.push(newData);
                    }
                  });
      });
      */
  },
  methods: {
     handleSelect(e){
      console.log("AAAAAAAAAAAAAAAAAAA"+e);
    },
     formatDate(formatDate) {
            console.log("preeformat"+formatDate)
            const date = dayjs(formatDate);
           return date.format('YYYY-MM-DDTHH:mm:ss');
        },setPeriod: function(){
          console.log("starttt"+this.start)
           console.log("endd"+this.end)
         
            this.fishingInstructorDtos.availableInstructorPeriodDtoSet[0]=({
              startDate:  this.formatDate(this.start),
              endDate:  this.formatDate(this.end),
              instructorUsername: this.fishingInstructorDtos.username})
              this.start='',
              this.end=''
    
          
          axios.post("http://localhost:8081/instructorsPeriod/setAvailableInstructorPeriod",this.fishingInstructorDtos,{
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


  /*  clearAll: function () {
      this.selectDisabled = false;
      this.disabledPickers = false;
      this.startDate = "";
      this.endDate = "";
      document.getElementById("saveBtn").style.display = "block";
      document.getElementById("deleteBtn").style.display = "block";
      document.getElementById("startPicker").style.display = "block";
      document.getElementById("endPicker").style.display = "block";
      document.getElementsByTagName("select")[0].value = "";
      for (let ev of this.calendarOptions.events) {
        if (ev.id == this.currentEvent.id) {
          ev.color = "";
          break;
        }
      }
      this.currentEvent = "";
      window.location.reload()
    },
    event: function (info) {
      this.selectDisabled = true;
      info.jsEvent.preventDefault(); // don't let the browser navigate
      this.currentEvent = info.event;
       for (let ev of this.calendarOptions.events) {
          if (ev.id == this.currentEvent.id) {
            ev.color = "#434c54";
          } else if ((typeof ev.id) == "number") {
            ev.color = "";
          }
        }
      
      if (info.event.url == "dateRange") {
        if (this.entityType != "adventure")
          document.getElementsByTagName("select")[0].value = info.event.title;
        this.startDate = info.event.start;
        this.endDate = info.event.end;
        if (this.endDate < new Date()) {
          document.getElementById("saveBtn").style.display = "none";
          document.getElementById("deleteBtn").style.display = "none";
          document.getElementById("startPicker").style.display = "none";
          document.getElementById("endPicker").style.display = "none";
          this.disabledPickers = true;
        } else {
          document.getElementById("saveBtn").style.display = "block";
          document.getElementById("deleteBtn").style.display = "block";
          document.getElementById("startPicker").style.display = "block";
          document.getElementById("endPicker").style.display = "block";
          this.disabledPickers = false;
        }
      }
      else if (info.event.url == "reservation") {
        this.clearAll();
      }
    },
    saveDate: function () {
      let newAvailabilityDate;
      if (this.currentEvent) {
        if (this.entityType != "adventure") {
          newAvailabilityDate = {
            start: this.startDate,
            end: this.endDate,
            title: this.currentEvent.title,
          };
          let profileId;
          let profileLabel = document.getElementsByTagName("select")[0].value;
          for (let data of this.selectData) {
            if (data.label == profileLabel) {
              profileId = data.code;
            }
          }
          axios
            .put(
              "/availabilityDate/update/" +
                this.currentEvent.id +
                "/" +
                profileId,
              newAvailabilityDate,
              {
                headers: {
                  "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
                  Authorization: "Bearer " + localStorage.refreshToken,
                },
              }
            )
            .then((res) => {
              this.copyOldAndSaveNewData(res, profileId, profileLabel);
              this.clearAll();
            });
        } else {
          newAvailabilityDate = {
            startDate: this.startDate,
            endDate: this.endDate,
            id: this.currentEvent.id,
          };
          axios
            .put(
              "/fishingInstructor/updateAvailability/", newAvailabilityDate,
              {
                headers: {
                  "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
                  Authorization: "Bearer " + localStorage.refreshToken,
                },
              }
            )
            .then(window.location.reload());
        }
      } else {
        if (this.entityType != "adventure") {
          newAvailabilityDate = {
            start: this.startDate,
            end: this.endDate,
            title: this.currentEvent.title,
          };
          let profileLabel = document.getElementsByTagName("select")[0].value;
          let profileId;
          for (let data of this.selectData) {
            if (data.label == profileLabel) {
              profileId = data.code;
            }
          }
          axios
            .post(
              "/availabilityDate/save/" + profileId,
              newAvailabilityDate,
              {
                headers: {
                  "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
                  Authorization: "Bearer " + localStorage.refreshToken,
                },
              }
            )
            .then((res) => {
              this.pushOneData(res.data, profileId, profileLabel);
              this.clearAll();
            });
        } else {
           newAvailabilityDate = {
            startDate: this.startDate,
            endDate: this.endDate,
            id: -1,
          };
          axios
            .put(
              "/fishingInstructor/addAvailability/", newAvailabilityDate,
              {
                headers: {
                  "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
                  Authorization: "Bearer " + localStorage.refreshToken,
                },
              }
            )
            .then(window.location.reload());
        }
      }
    },
    deleteDate: function () {
      if (this.entityType != "adventure") {
        let profileLabel = document.getElementsByTagName("select")[0].value;
        let profileId;
        for (let data of this.selectData) {
          if (data.label == profileLabel) {
            profileId = data.code;
          }
        }
        axios
          .delete(
            "/availabilityDate/" +
              this.currentEvent.extendedProps.defId +
              "/" +
              profileId,
            {
              headers: {
                "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
                Authorization: "Bearer " + localStorage.refreshToken,
              },
            }
          )
          .then((res) => {
            this.copyOldAndSaveNewData(res, profileId, profileLabel);
            this.clearAll();
            this.$toast.show(
              "Availability dates that include reserved appointments cannot be deleted.",
              {
                duration: 4000,
              }
            );
          });
      }
      else {
         axios
            .get(
              "/fishingInstructor/deleteAvailability?id=" + this.currentEvent.id,
              {
                headers: {
                  "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
                  Authorization: "Bearer " + localStorage.refreshToken,
                },
              }
            )
            .then(window.location.reload());
      }
    },
    pushOneData: function (newData, profileId, profileLabel) {
      newData.title = profileLabel;
      newData.url = "dateRange";
      newData.defId = newData.id;
      newData.serviceId = profileId;
      newData.startDate = new Date(newData.start);
      newData.endDate = new Date(newData.end);
    },
    copyOldAndSaveNewData: function (res, profileId, profileLabel) {
      let copyEvents = [];
      for (let ev of this.calendarOptions.events) {
        copyEvents.push(ev);
      }
      this.calendarOptions.events.length = 0;
      for (let ev of copyEvents) {
        if (ev.serviceId != profileId) {
          this.calendarOptions.events.push(ev);
        }
      }
      for (let newData of res.data) {
        this.pushOneData(newData, profileId, profileLabel);
        this.calendarOptions.events.push(newData);
      }
    },*/
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