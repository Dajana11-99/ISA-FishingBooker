<template>
<AdminNavbar :username=email />
<div style="padding: 5%;">
<select v-model="selectEntity" class="form-select" aria-label="Default select example">
               
                <option value="1" selected>ALL</option>
                <option value="2">CABINS</option>
                <option value="3">BOATS</option>
                 <option value="4">ADVENTURES</option>
                </select>
<br>
                <button @click="createIncomeReport()" type="button" class="btn btn-primary">generate report</button>
<br><br>
<div class="col">
        <h3>APP INCOME</h3>
        <BarChart :chart-data="data" css-classes="chart-container" />
        
</div>
</div>        
</template>

<script>
import AdminNavbar from './AdminNav.vue'
import {BarChart} from 'vue-chart-3'
import {Chart, BarController, CategoryScale, LinearScale,BarElement} from 'chart.js'
Chart.register(BarController,CategoryScale,LinearScale,BarElement)
import axios from "axios";
   export default{
     components:{
       AdminNavbar,
        BarChart,
     },
     data(){
       return{
         email: '', 
         allToday: 0.0,
         allWeek: 0.0,
         allMonth: 0.0,
         allYear: 0.0,
           

         selectEntity: 1,
           data:
                  {
                    labels: ["Reservation profit ($)"],
                    datasets: [{
                        label: "Today",
                        data:  [], 
                        backgroundColor: "#f505c9"
                    },
                    {
                        label: "This week",
                        data:  [], 
                        backgroundColor: "#d5f505"
                    },
                    {
                        label: "This month",
                        data:  [], 
                        backgroundColor: "#f5a105"
                    },
                    {
                        label: "This year",
                        data:  [], 
                        backgroundColor: "#edf505"
                    },
                    ]

                },
        

       }
     },
     mounted() {
         this.email = this.$route.params.email
     },
     methods: {
          createIncomeReport: function(){
         
           
            
            if(this.selectEntity==1){
                  this.resetData()
                  this.getAllProfit()
                    

            }else if(this.selectEntity==2){
                    console.log("usao u cabins")
                    this.resetData()
                    this.getCabinsProfit()
               

            }else if(this.selectEntity==3){
                    console.log("usao u boats")
                      this.resetData()
                    this.getBoatsProfit()
              
            }else if(this.selectEntity==4){
                    console.log("usao u adventures")
                      this.resetData()
                    this.getAdventuresProfit()
              
            }

               
          },
          resetData: function(){
             console.log("usao u resetData")
                     this.data.datasets[0].data=[]
                     this.data.datasets[1].data=[]
                     this.data.datasets[2].data=[]
                     this.data.datasets[3].data=[]
                   /*  this.allToday=0.0;
                     this.allMonth=0.0;
                     this.allWeek=0.0
                     this.allYear=0.0*/
                     
          },
          getCabinsProfit: function(){
             console.log("usao u getCabinsProfit")

             axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"cabinStatisticsReport/sumTodaysProfitOfAllCabinsForAdmin")
                .then((response) => {
                       console.log("usao u then1"+response.data)
                       this.data.datasets[0].data.push(response.data)
                   

                });

               axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"cabinStatisticsReport/sumWeekProfitOfAllCabinsForAdmin")
                .then((response) => {
                   console.log("usao u then2 "+response.data)
                       this.data.datasets[1].data.push(response.data)
                 

                });

                axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"cabinStatisticsReport/sumMonthProfitOfAllCabinsForAdmin")
                .then((response) => {
                   console.log("usao u then3"+response.data)
                       this.data.datasets[2].data.push(response.data)
                

                });

                axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"cabinStatisticsReport/sumYearProfitOfAllCabinsForAdmin")
                .then((response) => {
                   console.log("usao u then4"+response.data)
                       this.data.datasets[3].data.push(response.data)
                      

                });

          },
           getBoatsProfit: function(){
             console.log("usao u getBoatsProfit")
             axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"boatStatisticsReport/sumTodaysProfitOfAllBoatsForAdmin")
                .then((response) => {
                       console.log("usao u then1"+response.data)
                       this.data.datasets[0].data.push(response.data)
                    

                });

               axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"boatStatisticsReport/sumWeekProfitOfAllBoatsForAdmin")
                .then((response) => {
                   console.log("usao u then2 "+response.data)
                       this.data.datasets[1].data.push(response.data)
                   

                });

                axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"boatStatisticsReport/sumMonthProfitOfAllBoatsForAdmin")
                .then((response) => {
                   console.log("usao u then3"+response.data)
                       this.data.datasets[2].data.push(response.data)
                  

                });

                axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"boatStatisticsReport/sumYearProfitOfAllBoatsForAdmin")
                .then((response) => {
                   console.log("usao u then4"+response.data)
                       this.data.datasets[3].data.push(response.data)
                  

                });

          },
           getAdventuresProfit: function(){
             console.log("usao u getAdventuresProfit")
             axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"adventureStatisticsReport/sumTodaysProfitOfAllAdventuresForAdmin")
                .then((response) => {
                       console.log("usao u then1"+response.data)
                       this.data.datasets[0].data.push(response.data)
                  

                });

               axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"adventureStatisticsReport/sumWeekProfitOfAllAdventuresForAdmin")
                .then((response) => {
                   console.log("usao u then2 "+response.data)
                       this.data.datasets[1].data.push(response.data)
                    

                });

                axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"adventureStatisticsReport/sumMonthProfitOfAllAdventuresForAdmin")
                .then((response) => {
                   console.log("usao u then3"+response.data)
                       this.data.datasets[2].data.push(response.data)
                   

                });

                axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"adventureStatisticsReport/sumYearProfitOfAllAdventuresForAdmin")
                .then((response) => {
                   console.log("usao u then4"+response.data)
                       this.data.datasets[3].data.push(response.data)
                     
                       

                });

          },
           getAllProfit: function(){
          
              axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"cabinStatisticsReport/sumTodaysProfitOfAllCabinsForAdmin")
                .then((response) => {
                       this.allToday+=response.data
                
                });

               axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"cabinStatisticsReport/sumWeekProfitOfAllCabinsForAdmin")
                .then((response) => {
                       this.allWeek+=response.data
                
                });

                axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"cabinStatisticsReport/sumMonthProfitOfAllCabinsForAdmin")
                .then((response) => {
                       this.allMonth+=response.data
                   

                });

                axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"cabinStatisticsReport/sumYearProfitOfAllCabinsForAdmin")
                .then((response) => {
                       this.allYear+=response.data
                
                });
                  axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"boatStatisticsReport/sumTodaysProfitOfAllBoatsForAdmin")
                .then((response) => {
                       this.allToday+=response.data
               

                });

               axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"boatStatisticsReport/sumWeekProfitOfAllBoatsForAdmin")
                .then((response) => {
                       this.allWeek+=response.data
           

                });

                axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"boatStatisticsReport/sumMonthProfitOfAllBoatsForAdmin")
                .then((response) => {
                       this.allMonth+=response.data
                  

                });

                axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"boatStatisticsReport/sumYearProfitOfAllBoatsForAdmin")
                .then((response) => {
                       this.allYear+=response.data
                 
                });

               axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"adventureStatisticsReport/sumTodaysProfitOfAllAdventuresForAdmin")
                .then((response) => {
                       this.allToday+=response.data
                       this.data.datasets[0].data.push(this.allToday)

                });

               axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"adventureStatisticsReport/sumWeekProfitOfAllAdventuresForAdmin")
                .then((response) => {
                       this.allWeek+=response.data
                       this.data.datasets[1].data.push(this.allWeek)

                });

                axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"adventureStatisticsReport/sumMonthProfitOfAllAdventuresForAdmin")
                .then((response) => {
                       this.allMonth+=response.data
                       this.data.datasets[2].data.push(this.allMonth)

                });

                axios
                .get(
                process.env.VUE_APP_BACKEND_URL+"adventureStatisticsReport/sumYearProfitOfAllAdventuresForAdmin")
                .then((response) => {
                       this.allYear+=response.data
                       console.log("usaooooo"+ this.allYear)
                       this.data.datasets[3].data.push(this.allYear)
                });
                     
                     
          }
     

    },
    computed: {

  },
  }

</script> 

<style>

</style>
