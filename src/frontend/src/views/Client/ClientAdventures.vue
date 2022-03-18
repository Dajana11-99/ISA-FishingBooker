<template>
    <!-- sort -->
   
    <div class="header" >
      <form>
        <h1 style="text-align: left; color: #0b477b;  padding-left: 7.2%;">Sort cabins</h1>
        <br>
        <div style="padding-left: 7.2%; padding-right: 7.2%; width: 100%;" class="row" >
          <div class="col">
          <button class="form-control rounded-pill fa fa-sort">Name</button>
          </div>

          <div class="col">
          <button class="form-control rounded-pill fa fa-sort">Street</button>
          </div>

          <div class="col">
            <button class="form-control rounded-pill fa fa-sort">City</button>
          </div>

          <div class="col">
            <button class="form-control rounded-pill fa fa-sort">Country</button>
          </div>

          <div class="col">
            <button class="form-control rounded-pill fa fa-sort">Rating</button>
          </div>

          <div class="col">
          <button
            @click="resetSort()"
            style="height: 90%; background-color: #0b477b; color: white;"
            type="button"
            class="btn  rounded-pill fa fa-sort"
          >
            RESET SORT
          </button>
          </div>
          
        </div>
      </form>
    </div>

    <!--sort-->


   <hr/>

    <template v-if="!adventureLoaded">
      <h3>Loading...</h3>
    </template>

    <template v-if="sortedAdventures.length==0">
      <h3>No adventures to show.</h3>
    </template>

    <!-- Carousel wrapper -->
       <div v-if="adventureLoaded==true"
         id="carouselMultiItemExample"
         class="carousel slide carousel-dark text-center"
         data-mdb-ride="carousel"
       >
       
         <!-- Inner -->
         <div class="carousel-inner py-4">
           <!-- Single item -->
           <div class="carousel-item active">
             <div class="container">
               <div class="row">
                 <div   v-for="(adventureDto,index) in sortedAdventures" :key="index"   class="col-lg-4">
                     <div style="width: 100%; height:95%;" class="card">
                     <img style="width: 100%; height:100%;" :src="require('@/assets/' + getImageUrl(index))"/>
                      
                     <div class="card-body">
                       
                         <div class=row> 
                            <div class="col">
                            <h2 style="text-align: left;" class="card-title">{{adventureDto.name.toUpperCase()}}</h2>
                            </div>
                            <div style="text-align: right;" class="col">
                            <span  class="badge bg-warning text-light"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-star-fill" viewBox="0 0 16 16">
                            <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                            </svg> {{fishingInstructorDto.rating}}</span>
                            </div>
                         </div>
                         <hr>
                         <h6 style="text-align: left; color: gray;"> <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-geo-alt" viewBox="0 0 16 16">
                         <path d="M12.166 8.94c-.524 1.062-1.234 2.12-1.96 3.07A31.493 31.493 0 0 1 8 14.58a31.481 31.481 0 0 1-2.206-2.57c-.726-.95-1.436-2.008-1.96-3.07C3.304 7.867 3 6.862 3 6a5 5 0 0 1 10 0c0 .862-.305 1.867-.834 2.94zM8 16s6-5.686 6-10A6 6 0 0 0 2 6c0 4.314 6 10 6 10z"/>
                         <path d="M8 8a2 2 0 1 1 0-4 2 2 0 0 1 0 4zm0 1a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
                         </svg> {{getFullAddress(index)}}
                         </h6>
                       
                         <h6 style="text-align: left;">{{adventureDto.description}}</h6>
                            <h6 style="text-align: left;">Free equipment: {{adventureDto.equipment}}</h6>
                              <h6 style="text-align: left;">Canceling condition: {{adventureDto.cancelingCondition}}</h6>
                         <div class="row">
                         <div class="col-sm-2">
                                <h6 style="text-align: left; color: green;">{{adventureDto.price}} $ </h6>
                         </div>
                         <div  class="col">
                               <h6 style="text-align: left; color: black;">People limit: {{adventureDto.maxPeople}}  </h6>

                         </div>
                     
                         </div>
                        

                         <div class="row"  >
                           <div class="col" style="text-align: right;">
                         <button @click="seeProfile(adventureDto.name)" type="button" class="btn btn-outline-dark rounded-pill">SEE PROFILE</button>
                         </div>
                         
                         </div>
                    
                      
                     </div>
                     
                   </div>
                   <hr style="color: white;">
                    <hr style="color: white;">
                 </div>
                 </div> </div> </div> </div>
         </div>
         <!-- Inner -->

</template>

<script>

 import axios from "axios";
   export default{
   
     data(){
       return{
         email: '',
          adventureDtos: [{
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
             instruktorsBiography: '',
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
         adventureLoaded: false,   
       }
     },
     mounted() {
     
       this.email = this.$route.params.email
      this.getAdventures();

     },
     methods: {
        getAdventures: function(){
             axios.get("http://localhost:8081/adventures/getAll")
               .then(response => {
                        this.adventureDtos=response.data
                        this.adventureLoaded=true
              })
       },
        getImageUrl: function(index){
               if(this.adventureLoaded==true){
                 return this.filteredAdventures[index].images[0].url
               }
               return "logoF1.png"
       },
       getFullAddress: function(index){
              if(this.adventureLoaded==true)
               return this.filteredAdventures[index].address.streetAndNum + ", " + this.filteredAdventures[index].address.city + ", "
               + this.filteredAdventures[index].address.country
              return  "logoF1.png"
       },
       seeProfile: function(adventureName){
             this.$router.push('/adventureProfile/'+ this.email+'/'+adventureName);
       },

    },
    computed: {
    sortedAdventures: function () {
      return this.adventureDtos;
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
