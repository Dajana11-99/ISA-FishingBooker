<template>
<AdminNavbar :username=email />
<br>
  <div class="row" style="padding: 2%;">
      <div class="col-sm-6">
          
   <div class="row justify-content-center" style="width: 100%;">
   <div class="col-md-6" style="width: 100%;">
   <div class="card card-outline-secondary">
   <div class="card-header">
               <h3 style="text-align:center;" class="mb-0">User rank</h3>
   </div>
   <div class="card-body">
    <form  @submit="editData" method='post' class="was-validated">
        <div class="form-group">
            <div class="row" style="padding: 1%;">
                <div class="col-sm-4">Category</div>
                <div class="col-sm-4">Points</div>
                <div class="col-sm-4">Discount(%)</div>
            </div>

              <div v-for="(rank,index) in rankDto" :key="index" class="row" style="padding: 1%;">

                <div  class="col-sm-4">
                      <input  class="form-control" v-model="rank.rank" disabled>
                </div>
                <div class="col-sm-4">
                    <input min=0 class="form-control" v-model="rank.points" required>
                </div>
                <div class="col-sm-4">
                      <input type="number" min=0 max=100 class="form-control" v-model="rank.discountPercentage" required >
                </div>
               
            </div>
            <br>
              <div  class="col">   
                     <button   type="submit" class="btn btn-outline-dark">Save</button>
                </div>
        </div>
        <br>
    </form>
</div>
</div>
</div>
</div>

      </div>
     <div class="col-sm-6">

                  
   <div class="row justify-content-center" style="width: 100%;">
   <div class="col-md-6" style="width: 100%;">
   <div class="card card-outline-secondary">
   <div class="card-header">
               <h3 style="text-align:center;" class="mb-0">Reservation percentage</h3>
   </div>
   <div class="card-body">
    <form  @submit="updatePoints" method='post' class="was-validated">
        <div class="form-group">
           <br>
           <br>
           
              <div class="row" style="padding-left:1%; padding-right:1%; padding-bottom:1%">

                <div  class="col-sm-8">
                      <input  class="form-control" value="Percentage of profit company takes from reservation"  disabled>
                </div>
                <div class="col-sm-4">
                    <input min=1 type="number" v-model="reservationPointsDto.appProfitPercentage" class="form-control" required>
                </div>
               
            </div>

            
              <div class="row" style="padding: 1%;">

                <div  class="col-sm-8">
                      <input  class="form-control" value="Points client gets after successfull reservation"  disabled>
                </div>
                <div class="col-sm-4">
                    <input min=1 type="number"  v-model="reservationPointsDto.clientPoints" class="form-control" required>
                </div>
               
            </div>

            
              <div class="row" style="padding: 1%;">

                <div  class="col-sm-8">
                      <input  class="form-control" value="Points owner/instructor gets after successfull reservation"   disabled>
                </div>
                <div class="col-sm-4">
                    <input min=1 type="number" v-model="reservationPointsDto.ownerPoints" class="form-control" required>
                </div>
               
            </div>

               <div class="row" style="padding-left:1%; padding-right:1%; padding-bottom:1%">

                <div  class="col-sm-8">
                      <input  class="form-control" value="Cancelation fee percentage"  disabled>
                </div>
                <div class="col-sm-4">
                    <input min=1 type="number" v-model="reservationPointsDto.cancelationFeePercentage" class="form-control" required>
                </div>
               
            </div>
            <br>
              <div  class="col">   
                     <button  type="submit" class="btn btn-outline-dark">Save</button>
                </div>
        </div>
        <br>
    </form>
</div>
</div>
</div>
</div>



     </div>
  </div>
 

</template>
 
<script>
//import axios from "axios";

import AdminNavbar from './AdminNav.vue'
import axios from "axios";

   export default{
    components: {
    AdminNavbar 
    },
     data(){
       return{
         email: '',
         rankDto: [{
              id: null,
              rank: 0,
              points: 0,
              discountPercentage: 0
            }],
            reservationPointsDto: {
            id: null,
            clientPoints: 0,
            ownerPoints: 0,
            appProfitPercentage: 0,
            cancelationFeePercentage: 0
            }
       
       }
     },
     mounted() {
       this.email = this.$route.params.email
       this.getRank()
       this.getReservationPoints();
     },
     methods: {
         getRank: function(){
             axios.get(process.env.VUE_APP_BACKEND_URL+"ranks/getAll")
                  .then(response => {
                      this.rankDto=response.data
                      return response;
                   })
                
         },
            getReservationPoints: function(){
             axios.get(process.env.VUE_APP_BACKEND_URL+"reservationPoints/get")
                  .then(response => {
                      this.reservationPointsDto=response.data
                      return response;
                   })
                
         },
         editData: function(event){
                event.preventDefault();
             
                if(this.validate()==false){
                       this.$swal.fire({
                            position: 'top-end',
                            icon: 'error',
                            title: 'Error in defining points or discount percentages!',
                            showConfirmButton: false,
                            timer: 2500
                         })
                }else {
               axios.post(process.env.VUE_APP_BACKEND_URL+"ranks/updatePoints",this.rankDto)
                  .then(response => {
                          this.$swal.fire({
                            position: 'top-end',
                            icon: 'success',
                            title: 'Your work has been saved!',
                            showConfirmButton: false,
                            timer: 1500
                         })
                      return response;
                   })
                }
                
         },
         updatePoints: function(event){
          event.preventDefault();
                  
               axios.post(process.env.VUE_APP_BACKEND_URL+"reservationPoints/update",this.reservationPointsDto)
                  .then(response => {
                          this.$swal.fire({
                            position: 'top-end',
                            icon: 'success',
                            title: 'Your work has been saved!',
                            showConfirmButton: false,
                            timer: 1500
                         })
                      return response;
                   })
         },
         validate: function(){
             if(this.rankDto[0].points > this.rankDto[1].points || this.rankDto[0].points > this.rankDto[2].points || this.rankDto[1].points > this.rankDto[2].points )
                return false;
             if(this.rankDto[0].discountPercentage > this.rankDto[1].discountPercentage || this.rankDto[0].discountPercentage > this.rankDto[2].discountPercentage || this.rankDto[1].discountPercentage > this.rankDto[2].discountPercentage )
                return false;
            return true;
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
