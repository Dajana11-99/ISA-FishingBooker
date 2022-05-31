<template>
<CabinOwnerNav :username=email />
<div style="width: 90%; padding-left: 3%; ">
<h1>{{cabinName}} &nbsp;STATISTICS</h1>
<div class="row" style="padding-top: 3%; ">
       

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
                             <h3>OWNERS INCOME</h3>
                <BarChart :chart-data="dataIncome" :options="options" css-classes="chart-container" />
              </div>

        </div>
    
     

     

</div>
</template>
<script>
import CabinOwnerNav from './CabinOwnerNav.vue'
import {BarChart} from 'vue-chart-3'
import dayjs from 'dayjs';
import {Chart, BarController, CategoryScale, LinearScale,BarElement} from 'chart.js'
Chart.register(BarController,CategoryScale,LinearScale,BarElement)
import Datepicker from 'vue3-date-time-picker';
import 'vue3-date-time-picker/dist/main.css';
import axios from "axios";


export default ({
    components: {
         CabinOwnerNav,
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
         cabinName: '',
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
             this.cabinName= this.$route.params.cabin
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

           /*    axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"cabinStatisticsReportByCabin/findCabinStatistics/"+this.cabinName)
                .then((response) => {
                    this.stats = response.data;
                });
*/

          },
          getReservations: function(){
                axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"cabinStatisticsReport/countReservationsByCabin/"+this.cabinName)
                .then((response) => {
                    
                    for(let i=0; i< response.data.length; i++)
                       this.data.datasets[0].data.push([response.data[i]])

                    console.log(response.data[2])

                });
          },
          getQuickReservations: function(){
                axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"cabinStatisticsReport/countQuickReservationsByCabin/"+this.cabinName)
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

            var dateS1=this.formatDate(this.start).toString()
            var dateS2=this.formatDate(this.end).toString()
            var splits1=dateS1.split("T")
            var splits2=dateS2.split("T")
            if(splits1[0]==splits2[0]){
               this.$swal('Prices are calculated by day. Minimum difference between 2 dates must be 1 day.');
               this.start=""
               this.end=""
               return;
            }


             var dateRange=[]
             dateRange.push(this.formatDate(this.start))
             dateRange.push(this.formatDate(this.end))
             axios
                .post(
                process.env.VUE_APP_BACKEND_URL+"cabinStatisticsReport/sumProfitByCabin/"+this.cabinName, dateRange
               
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
