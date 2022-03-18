<template>
  <BoatOwnerNav :username=email />
  <div>
   <div style="margin-top: 1%; width: 100%; height: 100;" class="row">
       <div style="padding-left: 3%;" class="col-sm-6">

        <div class="row" style="padding-top: 2%;"> 
             
             <h1 style="text-align: left;">{{boatDto.name.toUpperCase()}}</h1>
              <h6 style="text-align: left; color: gray;"> <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-geo-alt" viewBox="0 0 16 16">
                         <path d="M12.166 8.94c-.524 1.062-1.234 2.12-1.96 3.07A31.493 31.493 0 0 1 8 14.58a31.481 31.481 0 0 1-2.206-2.57c-.726-.95-1.436-2.008-1.96-3.07C3.304 7.867 3 6.862 3 6a5 5 0 0 1 10 0c0 .862-.305 1.867-.834 2.94zM8 16s6-5.686 6-10A6 6 0 0 0 2 6c0 4.314 6 10 6 10z"/>
                         <path d="M8 8a2 2 0 1 1 0-4 2 2 0 0 1 0 4zm0 1a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
                         </svg> {{getFullAddress()}}
                         </h6>


        </div>

        <div  style="height: 60%; width:100%; " id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel">
                <div   class="carousel-indicators">
                    <button class="active" @click="clickedImage(index)" v-for="(image,index) in boatDto.images" :key="index" type="button" data-bs-target="#carouselExampleIndicators" ></button>
                </div>
                <div  class="carousel-inner">
                        <div class="carousel-item active">
                        <img :src="require('@/assets/' + currentImageUrl)" class="d-block w-100" alt="...">
                        </div>
                </div>
                <button @click="previousImage()" class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
                </button>
                <button  @click="nextImage()" class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
                </button>

       </div>

  
        <div style="text-align: left; padding-left: 1%; padding-top: 1%; border: 2px solid #bfbfbf; ">
            <p>{{boatDto.description}}</p>
            <p>Engine code: {{boatDto.engineCode}} Engine Power: {{boatDto.enginePower}} Max speed: {{boatDto.maxSpeed}}</p>
            <p>Fishing equipment:{{boatDto.fishingEquipment}}</p>
            <p>Navigation equipment:{{boatDto.navigationEquipment}}</p>
            <p>Rules: {{boatDto.rules}}</p>
        </div>





       </div>

        <div  style=" text-align: left; border: 2px solid #bfbfbf; padding-left: 2%; margin-top: 6.5%;" class="col-sm-4">



             <div class="row" > 
                 <div style="text-align: left; font-size: 200%; " class="col">
                            <span  class="badge bg-warning text-light"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-star-fill" viewBox="0 0 16 16">
                            <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                            </svg> {{boatDto.rating}}</span>
             </div>
                 <div class="col">
                    <h4 style="text-align: right; padding-top: 5%" >Booking information</h4>
                 </div>
             </div>

            <hr>
            <div class="row"> 
                 <div class="col">
                 <p>Owner:</p>
                 </div>
                 <div class="col">
                 <p><b>{{boatDto.ownersUsername}}</b></p>
                 </div>
             </div>

            <div class="row"> 
                 <div class="col">
                 <p>Price:</p>
                 </div>
                 <div class="col" style="color: green">
                 <p><b>{{boatDto.price}}$</b></p>
                 </div>
             </div>

             <div class="row"> 
                 <div class="col">
                 <p>Max people:</p>
                 </div>
                 <div class="col" >
                 <p><b>{{boatDto.maxPeople}}</b></p>
                 </div>
             </div>

             <div class="row"> 
                 <div class="col">
                 <p>Canceling conditions:</p>
                 </div>
                 <div class="col">
                 <p><b>{{boatDto.cancelingCondition}}</b></p>
                 </div>
             </div>



            <p>Additional services:</p>
            <div v-for="(service,index) in boatDto.additionalServices" :key="index" class="group" role="group" aria-label="Basic outlined example">

                <span v-if="service.price==0" style="background-color: #59d47a;" class="badge rounded-pill text-light">{{service.name}} - Free</span>
                <span v-else style="background-color: #703636;" class="badge rounded-pill text-light">{{service.name}} - {{service.price}}$ per night</span>
            </div>




        </div>

       <div  class="col" style="margin-top: 3%;">
           <button @click="editBoatProfile()" style="background-color: #1d7ac9; width: 100%; " type="button" class="btn text-light rounded-pill">EDIT PROFILE</button>
           <button @click="boatCalendar()" style="background-color: #1d7ac9; width: 100%; margin-top: 10%;" type="button" class="btn text-light rounded-pill">BOAT CALENDAR</button>


       </div>
       

       <div style=" text-align: left; border: 2px solid #bfbfbf; padding-left: 2%; margin-top: 6%; width: 80.5%; margin-left: 3%;" class="row-cols-sm-1"> 
             <OpenLayersMap :coordinates=[boatDto.addressDto.latitude,boatDto.addressDto.longitude] />
       </div>


    </div>
     


  </div>

</template>

<script>
  import BoatOwnerNav from './BoatOwnerNav.vue'
  import OpenLayersMap from '../../components/OpenLayersMap'
  import axios from "axios";


   export default{
    components: {
    BoatOwnerNav,
    OpenLayersMap
    },
     data(){
       return{
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
           boatLoaded: false,
           currentImageUrl: 'logoF1.png',
           imageIndex: 0,
           maxImageIndex: 0

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
                        this.boatLoaded=true;
                        this.currentImageUrl=this.boatDto.images[0].url
                        this.maxImageIndex=this.boatDto.images.length-1
                        
                        console.log("image url "+this.currentImageUrl)
                        console.log("max index"+ this.boatDto.images.length)
                  })

       },
       previousImage: function(){
             if(this.imageIndex>0){
                 this.imageIndex--
                 this.currentImageUrl=this.boatDto.images[this.imageIndex].url
                 console.log("image url "+this.currentImageUrl)
             }


       },
       nextImage: function(){
             if(this.imageIndex<this.maxImageIndex){
                 this.imageIndex++
                 this.currentImageUrl=this.boatDto.images[this.imageIndex].url
               
             }

       },
       clickedImage: function(index){
            this.imageIndex=index
            this.currentImageUrl=this.boatDto.images[this.imageIndex].url
       },
       getFullAddress: function(){
               return this.boatDto.addressDto.streetAndNum + ", " + this.boatDto.addressDto.city + ", "
               + this.boatDto.addressDto.country
       },
       boatCalendar: function(){
         this.$router.push('/boatCalendar/'+ this.email+'/'+this.boatName);

       },
       editBoatProfile: function(){
         this.$router.push('/editBoatProfile/'+ this.email+'/'+this.boatName);

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
</style>
