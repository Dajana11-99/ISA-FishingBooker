<template>
<FishingInstructorNav :username=email />
<div style="width: 90%; padding-left: 3%; ">
<h1>STATISTICS</h1>
<div class="row" style="padding-top: 3%; ">
        <div class="col-sm-6" style="padding-left: 2%;  padding-top: 1%; ">
           
           <div style="margin: 8%; background-color: #a8afbd; " class="card" >
            <div  class="card-body">
               <h5 class="card-title">General information</h5>
               <br>
               <div  class="row">
                 <div class="col-sm-6" style="padding-top: 2%; text-align: left;" >
                 <h6>INSTRUCTOR RATING</h6>
                 </div>
                 <div class="col" style="padding: 1%;" >

                      {{stats.ownerRating}}
                  <svg xmlns="http://www.w3.org/2000/svg" style="color: yellow;" width="18" height="18" fill="currentColor" class="bi bi-star-fill" viewBox="0 0 16 16">
  <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
</svg>
                </div>
                </div>


                <div  class="row">
                 <div class="col-sm-6" style="padding-top: 2%; text-align: left;" >
                 <h6>APP FEE </h6>
                 </div>
                
                 <div class="col" style="padding: 1%;" >
                       {{stats.reservationsPointsDto.appProfitPercentage}}
                     <svg xmlns="http://www.w3.org/2000/svg" style="color: blue;" width="18" height="18" fill="currentColor" class="bi bi-percent" viewBox="0 0 16 16">
  <path d="M13.442 2.558a.625.625 0 0 1 0 .884l-10 10a.625.625 0 1 1-.884-.884l10-10a.625.625 0 0 1 .884 0zM4.5 6a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm0 1a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5zm7 6a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm0 1a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5z"/>
</svg>
                 
                </div>
                </div>

                <div  class="row">
                 <div class="col-sm-6" style="padding-top: 2%; text-align: left;" >
                 <h6>CANCELATION FEE </h6>
                 </div>
                 <div class="col" style="padding: 1%;" >
                                         {{stats.reservationsPointsDto.cancelationFeePercentage}}
                      <svg xmlns="http://www.w3.org/2000/svg" style="color: blue;" width="18" height="18" fill="currentColor" class="bi bi-percent" viewBox="0 0 16 16">
  <path d="M13.442 2.558a.625.625 0 0 1 0 .884l-10 10a.625.625 0 1 1-.884-.884l10-10a.625.625 0 0 1 .884 0zM4.5 6a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm0 1a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5zm7 6a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm0 1a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5z"/>
</svg>
                </div>
                </div>

                <div  class="row">
                 <div class="col-sm-6" style="padding-top: 2%; text-align: left;" >
                 <h6>POINTS FOR RESERVATION </h6>
                 </div>
                 <div class="col" style="padding: 1%;" >
                                                           {{stats.reservationsPointsDto.clientPoints}}
                  P
                     
                </div>
                </div>
                
               <br>
                <br>
        </div>
        </div>
  </div> 

        <div class="col">
        <h3>SUCCESSFULL RESERVATIONS</h3>
        <BarChart :chart-data="data" css-classes="chart-container" />
        </div>
</div>
<div class="row" style="padding-top: 3%;">

            <div class="col-sm-6" style="padding-left: 2%;   padding-top: 1%;">
           
           <div style="margin: 8%; " class="card" >
            <div  class="card-body">
               <h5 class="card-title">Generate income graph</h5>
               <br>
               <br>
               <div  class="row">
                 <div class="col" style="padding-top: 2%; text-align: left;" >
                 <h5>From</h5>
                 </div>
                 <div class="col-sm-9" style="padding: 1%; padding-right: 2.5%;" >
                 <Datepicker v-model="start"></Datepicker>
                </div>
                </div>
                <div class="row">
                <div class="col" style="padding-top: 2%; text-align: left;">
                <h5>To</h5>
                </div>
                <div class="col-sm-9" style="padding: 1%; padding-right: 2.5%;">
                <Datepicker v-model="end" ></Datepicker>
                </div>
                </div>
                 &nbsp;
                <div class="col" style="text-align: right; width: 100%; padding-top: 1%;">
                <button type="button" @click="createIncomeReport()" class="btn btn-light">Save</button>
                </div>
                <br>
                <br>
        </div>
        </div>
  </div> 

              <div class="col"  >
                             <h3>INSTRUCTORS INCOME</h3>
        <BarChart :chart-data="dataIncome" :options="options" css-classes="chart-container" />
              </div>

        </div>
    
     

     

</div>
</template>
<script>
import FishingInstructorNav from './FishingInstructorNav.vue'
import {BarChart} from 'vue-chart-3'
import dayjs from 'dayjs';
import {Chart, BarController, CategoryScale, LinearScale,BarElement} from 'chart.js'
Chart.register(BarController,CategoryScale,LinearScale,BarElement)
import Datepicker from 'vue3-date-time-picker';
import 'vue3-date-time-picker/dist/main.css';
import axios from "axios";


export default ({
    components: {
         FishingInstructorNav,
         BarChart,
         Datepicker
  
     },

    data(){
       return{
           data:
                  {
                    labels: ["This week","This month","This year"],
                    datasets: [{
                        label: "Reservations",
                        data:  [], 
                        backgroundColor: "#268bd2"
                    },
                    {
                        label: "Quick actions",
                        data:  [], 
                        backgroundColor: "#244bd2"

                    }]

                },
         dataIncome: 
                {
                    labels: ["Profit ($)"],
                    datasets: [{
                        label: "Reservations",
                        data: [], 
                        backgroundColor: "#cafc03"
                    },
                    {
                        label: "Quick actions",
                        data: [], 
                        backgroundColor: "#ffd000"

                    }]
                    

                },
         options: {
               plugins: {
                   title: {
                       text: "Profit ($)"
                   }
               }
         },
        email: '',
        start: '',
        end: '',
        stats: {
            ownerRating: 0.0,
            avgRating: 0.0,
            reservationsPointsDto: {
                id: 0,
                clientPoints: 0,
                ownerPoints: 0,
                appProfitPercentage: 0,
                cancelationFeePercentage: 0,
            },
        }
       }
       },
       mounted() {
             this.email = this.$route.params.email
             this.getStats()
             this.getReservations()
             this.getQuickReservations()
               
       },
       methods:{
          formatDate(formatDate) {
            const date = dayjs(formatDate);
            return date.format('YYYY-MM-DDTHH:mm:ss');
          },
          dataIsValid(start,end){
              const date1 = new Date(start);
              const date2 = new Date(end);
              
              if((date1.getTime() - date2.getTime()) > 0){
                this.start=""
                this.end=""
                return false;
              }
              return true;
          },
          getStats: function (){

               axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"adventureStatisticsReport/findAdventureStatistics/"+this.email+"/")
                .then((response) => {
                    this.stats = response.data;
                });


          },
          getReservations: function(){
                axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"adventureStatisticsReport/countReservations/"+this.email+"/")
                .then((response) => {
                    
                    for(let i=0; i< response.data.length; i++)
                       this.data.datasets[0].data.push([response.data[i]])

                    console.log(response.data[1])

                });
          },
          getQuickReservations: function(){
                axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"adventureStatisticsReport/countQuickReservations/"+this.email+"/")
                .then((response) => {
                       for(let i=0; i< response.data.length; i++)
                       this.data.datasets[1].data.push([response.data[i]])

                });
          },
          createIncomeReport: function(){
             if(this.start=="" || this.end=="") return;
             if(!this.dataIsValid(this.start,this.end)){
                 this.$swal.fire({
                 position: 'top-end',
                 icon: 'error',
                 title: 'Please enter valid dates',
                 showConfirmButton: false,
                 timer: 2500
                })
                 return;
             }
             var dateRange=[]
             
             dateRange.push(this.formatDate(this.start))
             dateRange.push(this.formatDate(this.end))
             axios
                .post(
                process.env.VUE_APP_BACKEND_URL+"adventureStatisticsReport/sumProfit/"+this.email+"/", dateRange
               
                )
                .then((response) => {
                       this.dataIncome.datasets[0].data=[]
                       this.dataIncome.datasets[1].data=[]
                       this.dataIncome.datasets[0].data.push([response.data[0]])
                       this.dataIncome.datasets[1].data.push([response.data[1]])
                  

                });
               
          }

       }

    
       

       
    
})
</script>
