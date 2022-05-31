<template>
  <FishingInstructorNavbar :username=email />
  <div>
   <div style="margin-top: 1%; width: 100%; height: 100;" class="row">
       <div style="padding-left: 3%;" class="col-sm-6">

        <div class="row" style="padding-top: 2%;"> 
             
             <h1 style="text-align: left;">{{adventureDto.name.toUpperCase()}}</h1>
              <h6 style="text-align: left; color: gray;"> <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-geo-alt" viewBox="0 0 16 16">
                         <path d="M12.166 8.94c-.524 1.062-1.234 2.12-1.96 3.07A31.493 31.493 0 0 1 8 14.58a31.481 31.481 0 0 1-2.206-2.57c-.726-.95-1.436-2.008-1.96-3.07C3.304 7.867 3 6.862 3 6a5 5 0 0 1 10 0c0 .862-.305 1.867-.834 2.94zM8 16s6-5.686 6-10A6 6 0 0 0 2 6c0 4.314 6 10 6 10z"/>
                         <path d="M8 8a2 2 0 1 1 0-4 2 2 0 0 1 0 4zm0 1a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
                         </svg> {{getFullAddress()}}
                         </h6>


        </div>

        <div  style="height: 70%; width:100%; " id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel">
         <div   class="carousel-indicators">
             <button class="active" @click="clickedImage(index)" v-for="(image,index) in adventureDto.images" :key="index" type="button" data-bs-target="#carouselExampleIndicators" ></button>
         </div>
        <div class="carousel-inner">
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


        <div style="text-align: left; padding-left: 1%; border: 2px solid #bfbfbf; ">
              <p>Instructor biography: {{adventureDto.instructorsBiography}}</p>
            <p>{{adventureDto.description}}</p>
            <p>Free equipment: {{adventureDto.equipment}}</p>
             <p>Rules: {{adventureDto.rules}}</p>

          

        </div>





       </div>

        <div  style=" text-align: left; border: 2px solid #bfbfbf; padding-left: 2%; margin-top: 6.5%;" class="col-sm-4">



             <div class="row" > 
                 <div style="text-align: left; font-size: 200%; " class="col">
                            <span  class="badge bg-warning text-light"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-star-fill" viewBox="0 0 16 16">
                            <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                            </svg> {{fishingInstructorDto.rating}}</span>
             </div>
                 <div class="col">
                    <h4 style="text-align: right; padding-top: 5%" >Booking information</h4>
                 </div>
             </div>

            <hr>
            <div class="row"> 
                 <div class="col">
                 <p>Fishing instructor:</p>
                 </div>
                 <div class="col">
                 <p><b>{{adventureDto.fishingInstructorUsername}}</b></p>
                 </div>
             </div>

            <div class="row"> 
                 <div class="col">
                 <p>Price:</p>
                 </div>
                 <div class="col" style="color: green">
                 <p><b>{{adventureDto.price}}$</b></p>
                 </div>
             </div>

            <div class="row"> 
                <div class="col">
                <p>People limit: </p>
                </div>
                <div class="col">
                 <p>{{adventureDto.maxPeople}}</p>
                 </div>
            </div>
             <div class="row"> 
                 <div class="col">
                 <p>Canceling condition: </p>
                 </div>
                 <div class="col">
                 <p>{{adventureDto.cancelingCondition}}</p>
                 </div>
            </div>



            <p>Additional services:</p>
            <div v-for="(service,index) in adventureDto.additionalServices" :key="index" class="group" role="group" aria-label="Basic outlined example">

                <span v-if="service.price==0" style="background-color: #59d47a;" class="badge rounded-pill text-light">{{service.name}} - Free</span>
                <span v-else style="background-color: #703636;" class="badge rounded-pill text-light">{{service.name}} - {{service.price}}$ </span>
            </div>




        </div>

       <div  class="col" style="margin-top: 3%;">
           <button @click="editProfile()" style="background-color: #1d7ac9; width: 100%; " type="button" class="btn text-light rounded-pill">EDIT PROFILE</button>
           <button @click="statistics()" style="background-color: #1d7ac9; width: 100%; " type="button" class="btn text-light rounded-pill">SEE STATISTICS</button>
       </div>

        

       

       <div style=" text-align: left; border: 2px solid #bfbfbf; padding-left: 2%; margin-top: 6%; width: 80.5%; margin-left: 3%;" class="row-cols-sm-1"> 
             <OpenLayersMap :coordinates=[adventureDto.address.latitude,adventureDto.address.longitude] />
       </div>


    </div>

 
    


  </div>
  <br>

  <div  style="text-align: left; padding-left: 3%; padding-right: 11%;">
    <h1>Comments about instructor</h1>
    <hr>
      <div v-for="(comment,index) in comments" :key="index" class="row">
            <div class="col-sm-1">
              <svg viewBox="0 0 36 36" fill="none" role="img" xmlns="http://www.w3.org/2000/svg" width="80" height="80"><title>Coretta Scott</title><mask id="mask__beam" maskUnits="userSpaceOnUse" x="0" y="0" width="36" height="36"><rect width="36" height="36" rx="72" fill="#FFFFFF"></rect></mask><g mask="url(#mask__beam)"><rect width="36" height="36" fill="#737777"></rect><rect x="0" y="0" width="36" height="36" transform="translate(5 -1) rotate(55 18 18) scale(1.1)" fill="#0e0043" rx="6"></rect><g transform="translate(7 -6) rotate(-5 18 18)"><path d="M15 20c2 1 4 1 6 0" stroke="#FFFFFF" fill="none" stroke-linecap="round"></path><rect x="14" y="14" width="1.5" height="2" rx="1" stroke="none" fill="#FFFFFF"></rect><rect x="20" y="14" width="1.5" height="2" rx="1" stroke="none" fill="#FFFFFF"></rect></g></g></svg>                
            </div>
            <div class="col" style="text-align: left;">
            <div class="row">  
                <div class="col-sm-9">  
                  <p  style="font-size: 1em; "  ><b>{{comment.clientUsername}} </b> 
                  rated <svg xmlns="http://www.w3.org/2000/svg" style="color: orange;" width="16" height="16" fill="currentColor" class="bi bi-star-fill" viewBox="0 0 16 16">
            <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
            </svg> {{comment.grade}}</p>
                </div>
                 <div class="col">  
                  <button @click="visitProfile(comment.clientUsername)" type="button" style="background-color: #3d6b51; color: white;" class="btn rounded-pill">
                      <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" fill="currentColor" class="bi bi-box-arrow-up-left" viewBox="0 0 16 16">
  <path fill-rule="evenodd" d="M7.364 3.5a.5.5 0 0 1 .5-.5H14.5A1.5 1.5 0 0 1 16 4.5v10a1.5 1.5 0 0 1-1.5 1.5h-10A1.5 1.5 0 0 1 3 14.5V7.864a.5.5 0 1 1 1 0V14.5a.5.5 0 0 0 .5.5h10a.5.5 0 0 0 .5-.5v-10a.5.5 0 0 0-.5-.5H7.864a.5.5 0 0 1-.5-.5z"/>
  <path fill-rule="evenodd" d="M0 .5A.5.5 0 0 1 .5 0h5a.5.5 0 0 1 0 1H1.707l8.147 8.146a.5.5 0 0 1-.708.708L1 1.707V5.5a.5.5 0 0 1-1 0v-5z"/>
</svg>
                      Visit profile</button>
                </div>
            </div>
            <p ></p>
            <p style="font-size: 1em;" >{{comment.comment}}</p>
            <p style=" color: dark-gray;"  >{{setAndFormatDate(comment.date)}}</p>
           
            </div>
              <hr> 
              <br>
     </div>
    
</div>

</template>

<script>
import FishingInstructorNavbar from './FishingInstructorNav.vue'
import OpenLayersMap from '../../components/OpenLayersMap.vue'
import axios from "axios";
import dayjs from 'dayjs';
export default{
    components: {
       FishingInstructorNavbar,
       OpenLayersMap
     },
     data(){
       return{
         email: '',
          adventureDto: {
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
         },

       
         adventureLoaded: false,
         currentImageUrl: 'logoF1.png',
         imageIndex: 0,
         maxImageIndex: 0,
         fishingInstructorDto: {
         
            username: '',
          
          rating: 0.0
         },
         comments: []
         
       
       }
     },
     mounted() {
       this.email = this.$route.params.email,
       this.adventureDto.name=this.$route.params.adventureName
       this.adventureDto.fishingInstructorUsername= this.$route.params.email
       this.fishingInstructorDto.username= this.$route.params.email,
      // this.adventureName=this.$route.params.adventureName
       this.getAdventure();
       this.getInstructorRating();
     },
     methods: {
       getAdventure: function(){

             axios.post(process.env.VUE_APP_BACKEND_URL+"adventures/findByName",this.adventureDto)
               .then(response => {
                        this.adventureDto=response.data
                        this.adventureLoaded=true;
                        this.currentImageUrl=this.adventureDto.images[0].url
                        this.maxImageIndex=this.adventureDto.images.length-1
              })

       },
       previousImage: function(){
             if(this.imageIndex>0){
                 this.imageIndex--
                 this.currentImageUrl=this.adventureDto.images[this.imageIndex].url
            
             }


       },
       nextImage: function(){
             if(this.imageIndex<this.maxImageIndex){
                 this.imageIndex++
                 this.currentImageUrl=this.adventureDto.images[this.imageIndex].url
                
             }

       },
          getInstructorRating: function(){
            axios.post(process.env.VUE_APP_BACKEND_URL+"instructors/findInstructorRatingByUsername",this.fishingInstructorDto)
               .then(response => {
                        this.fishingInstructorDto.rating=response.data
              })
      },
       clickedImage: function(index){
            this.imageIndex=index
            this.currentImageUrl=this.adventureDto.images[this.imageIndex].url
       },
       getFullAddress: function(){
               return this.adventureDto.address.streetAndNum + ", " + this.adventureDto.address.city + ", "
               + this.adventureDto.address.country
       },
       editProfile: function(){
            this.$router.push('/editAdventure/'+ this.email+'/'+this.adventureDto.name);
       },
       getComments: function(){
               axios.get(process.env.VUE_APP_BACKEND_URL+"evaluations/instructor/"+this.fishingInstructorDto.id)
               .then(response => {
                       this.comments=response.data
                      
              })
      },
      setAndFormatDate: function(newDate){
           var date= new Date()
           var splits =newDate.toString().split(",")
           date.setDate( splits[1],splits[2], splits[0])
           var newData= new Date( parseInt(splits[0]), parseInt(splits[1])-1, parseInt(splits[2]),parseInt(splits[3]),parseInt(splits[4]))
            const dateee = dayjs(newData);
           return dateee.format('YYYY-MM-DD HH:mm:ss');
           },
      visitProfile: function(username){
            console.log(username)
            this.$router.push('/cabinOwner/viewProfile/'+ this.email + "/"+username);
       
      },
      statistics: function(){
            this.$router.push('/adventureStatistics/'+ this.email + "/"+this.adventureDto.name);
       
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
