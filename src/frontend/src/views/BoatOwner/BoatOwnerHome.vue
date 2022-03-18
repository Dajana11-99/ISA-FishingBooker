<template>
  <div>
    <nav  class="navbar navbar-fixed-top navbar-expand" style="background-color: #1d7ac9; list-style: none;">
      <div class="container-fluid" style="background-color: #1d7ac9;">
      <a class="navbar-brand"  href="#" >
      <img src="../../assets/logoF1.png" alt="" width="194" height="80" >
      </a>
      <li class="nav-item dropdown">
          <a style="color: white;" class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            {{email}}
          </a>
          <ul class="dropdown-menu" style="width: 100%" aria-labelledby="navbarDropdownMenuLink">
            <li><a @click="myProfile()" class="dropdown-item" href="#">Edit profile</a></li>
            <li><a @click="logout()" class="dropdown-item" href="#">Log out</a></li>
          </ul>
        </li>
    
    
    
      </div>
    
    </nav>

    <ul class="nav justify-content-center" style="background-color: #0b477b;">

      <li class="nav-item">
      <a style="color: white;" class="nav-link active" href="#"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-house" viewBox="0 0 16 16">
      <path fill-rule="evenodd" d="M2 13.5V7h1v6.5a.5.5 0 0 0 .5.5h9a.5.5 0 0 0 .5-.5V7h1v6.5a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 13.5zm11-11V6l-2-2V2.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5z"/>
      <path fill-rule="evenodd" d="M7.293 1.5a1 1 0 0 1 1.414 0l6.647 6.646a.5.5 0 0 1-.708.708L8 2.207 1.354 8.854a.5.5 0 1 1-.708-.708L7.293 1.5z"/>
    </svg> MY BOATS</a>
      </li>
      <li @click="addNewBoat()" class="nav-item">
      <a style="color: white;" class="nav-link active" href="#"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus-square" viewBox="0 0 16 16">
  <path d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"/>
  <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
</svg> ADD NEW  BOAT</a>
      </li>

      <li class="nav-item">
      <a @click="myCalendar()" style="color: white;" class="nav-link active" href="#"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-calendar3" viewBox="0 0 16 16">
  <path d="M14 0H2a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zM1 3.857C1 3.384 1.448 3 2 3h12c.552 0 1 .384 1 .857v10.286c0 .473-.448.857-1 .857H2c-.552 0-1-.384-1-.857V3.857z"/>
  <path d="M6.5 7a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm-9 3a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm-9 3a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
</svg> MY CALENDAR</a>
      </li>
       <li class="nav-item">
      <a style="color: white;"  class="nav-link active" href="#"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil" viewBox="0 0 16 16">
  <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>
</svg> WRITE A REVIEW</a>
      </li>

      <li class="nav-item">
      <a style="color: white;"  class="nav-link active" href="#"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bar-chart-line" viewBox="0 0 16 16">
  <path d="M11 2a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v12h.5a.5.5 0 0 1 0 1H.5a.5.5 0 0 1 0-1H1v-3a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v3h1V7a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v7h1V2zm1 12h2V2h-2v12zm-3 0V7H7v7h2zm-5 0v-3H2v3h2z"/>
</svg> STATISTCS</a>
      </li>
       
     </ul>

   <br>
     <!-- search-->
   
    <div class="header" >
      <form>
        <h1 style="text-align: left; color: #0b477b;  padding-left: 2%;">Search boats</h1>
        <br>
        <div style="padding-left: 2%; width: 100%;" class="row" >
          <div class="col">
          <input
            class="form-control rounded-pill"
            type="text"
            style="height: 90%; width:110%; padding-left: 5%;"
            id="search-field"
            placeholder="NAME"
            :value="searchName" 
            @input="searchName = $event.target.value.toUpperCase()"
          />
          </div>

          <div class="col">
          <input
            class="form-control rounded-pill"
            type="text"
            style="height: 90%; width:110%; padding-left: 5%;"
            id="search-field"
            placeholder="TYPE"
            :value="searchType" 
            @input="searchType = $event.target.value.toUpperCase()"
          />
          </div>

          <div class="col">
          <input
            class="form-control rounded-pill"
            type="text"
            style="height: 90%; width:110%; padding-left: 5%;"
            placeholder="ADDRESS"
            :value="searchAddress"
            @input="searchAddress = $event.target.value.toUpperCase()"
          />
          </div>

          <div class="col">
          <input
            class="form-control rounded-pill"
            style="height: 90%; width:110%; padding-left: 5%;"
            type="text"
            placeholder="CITY"
            :value="searchCity"
            @input="searchCity = $event.target.value.toUpperCase()"
          />
          </div>

          <div class="col">
          <input
            class="form-control rounded-pill"
            type="text"
            style="height: 90%; width:110%; padding-left: 5%;"
            placeholder="COUNTRY"
            :value="searchCountry"
            @input="searchCountry = $event.target.value.toUpperCase()"
          />
          </div>

          <div class="col">
          <input
            class="form-control rounded-pill"
            type="text"
            style="height: 90%; width:110%; padding-left: 5%;"
            placeholder="PRICE"
            :value="searchPrice"
            @input="searchPrice = $event.target.value"
          />
          </div>

          <div class="col">
          <input
            class="form-control rounded-pill"
            type="text"
            style="height: 90%; width:110%; padding-left: 5%;"
            id="search-field"
            placeholder="MAX PEOPLE"
            :value="searchMaxPeople" 
            @input="searchMaxPeople = $event.target.value.toUpperCase()"
          />
          </div>

          <div class="col">
          <select
             style="height: 90%; width: 110%; color: #5f7280;"
            v-model="searchRating"
            class="form-select rounded-pill"
            aria-label="Default select example"
            placeholder="Rating"
          >
            <option  disabled value="">RATING</option>
            <option v-bind:value="5">FIVE STARS</option>
            <option v-bind:value="4">FOUR STARS</option>
            <option v-bind:value="3">THREE STARS</option>
            <option v-bind:value="2">TWO STARS</option>
            <option v-bind:value="1">ONE STAR</option>
          </select>
          </div>

          <div class="col" >
          <button
            @click="resetSearch()"
            style="height: 90%; width: 110%; background-color: #0b477b; color: white;"
            type="button"
            class="btn  rounded-pill"
          >
            RESET SEARCH
          </button>
          </div>
          
        </div>
      </form>
    </div>

    <!--search-->
     <hr/>

    
      <!-- Carousel wrapper -->
       <div v-if="boatsLoaded==true"
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
                 <div   v-for="(boatDto,index) in filteredBoats" :key="index"   class="col-lg-4">
                     <div style="width: 100%; height:95%;" class="card">
                     <img style="width: 100%; height:100%;" :src="require('@/assets/' + getImageUrl(index))"/>
                      
                     <div class="card-body">
                       
                         <div class=row> 
                            <div class="col-10">
                            <h2 style="text-align: left;" class="card-title">{{boatDto.name.toUpperCase()}}</h2>
                            </div>
                            <div style="vertical-align: bottom;" class="col">
                            <span  class="badge bg-warning text-light"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-star-fill" viewBox="0 0 16 16">
                            <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                            </svg> {{boatDto.rating}}</span>
                            </div>
                         </div>
                         <hr>
                         <h6 style="text-align: left; color: gray;"> <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-geo-alt" viewBox="0 0 16 16">
                         <path d="M12.166 8.94c-.524 1.062-1.234 2.12-1.96 3.07A31.493 31.493 0 0 1 8 14.58a31.481 31.481 0 0 1-2.206-2.57c-.726-.95-1.436-2.008-1.96-3.07C3.304 7.867 3 6.862 3 6a5 5 0 0 1 10 0c0 .862-.305 1.867-.834 2.94zM8 16s6-5.686 6-10A6 6 0 0 0 2 6c0 4.314 6 10 6 10z"/>
                         <path d="M8 8a2 2 0 1 1 0-4 2 2 0 0 1 0 4zm0 1a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
                         </svg> {{getFullAddress(index)}}
                         </h6>
                       
                         <h6 style="text-align: left;">{{boatDto.description}}</h6>
                         <h6 style="text-align: left;">{{boatDto.type.toUpperCase()}}</h6>
                         <div class="row">
                         <div class="col">
                            <h6 style="text-align: left; ">Max people: {{boatDto.maxPeople}}</h6>
                     
                         </div>
                         <div class="col">
                              <div class="row">
                              <div class="col">
                              <h6 style="text-align: left; ">Price: </h6>
                              </div>
                              <div class="col">
                              <h6 style="text-align: left; color: green;">{{boatDto.price}}$</h6>
                              </div>
                              </div>
                         </div>
                         </div>
                        
                         <div class="row"  >
                           <div class="col" style="text-align: right;">
                         <button @click="seeProfile(boatDto.name)" type="button" class="btn btn-outline-dark rounded-pill">SEE PROFILE</button>
                         </div>
                         
                         </div>
                    
                      
                     </div>
                     
                   </div>
                   <hr style="color: white;">
                    <hr style="color: white;">
                 </div>
                 </div> </div> </div> </div>
         </div>
         



  </div>

</template>

<script>
 import axios from "axios";

   export default{
     data(){
       return{
         email: '',
        boatDtos: [{

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
         }],
          user:{
           username: ''
         },
         boatsLoaded: false,
         searchName: "",
         searchRating: "",
         searchAddress: "",
         searchPrice: "",
         searchCity: "",
         searchCountry: "",
         searchType: "",
         searchMaxPeople: ""
       
       }
     },
     mounted() {
       this.email = this.$route.params.email
       this.getOwnersBoats()

     },
     methods: {
       myProfile: function(){
          this.$router.push('/editProfile/'+'boatOwner/'+ this.email);
       },
       myCalendar: function(){
          this.$router.push('/BoatOwnerCalendar/'+this.email);
       },
       logout: function(){
          this.$router.push('/');
       },
       addNewBoat: function(){
          this.$router.push('/addNewBoat/'+ this.email);
       },
       getOwnersBoats: function(){
          this.user.username=this.email
             axios.post("http://localhost:8081/boats/findBoatsByOwnersUsername",this.user,{
            headers: {
            "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
            "Authorization": "Bearer " + localStorage.jwt ,
            }
             })
               .then(response => {
                        this.boatDtos=response.data
                        this.boatsLoaded=true
              })
       
       },
       getImageUrl: function(index){
               if(this.boatsLoaded==true){
                 return this.filteredBoats[index].images[0].url
               }
               return "logoF1.png"
       },
       getFullAddress: function(index){
              if(this.boatsLoaded==true)
               return this.filteredBoats[index].addressDto.streetAndNum + ", " + this.filteredBoats[index].addressDto.city + ", "
               + this.filteredBoats[index].addressDto.country
              return  "logoF1.png"
       },
       seeProfile: function(boatName){
              console.log(boatName)
              this.$router.push('/boatProfile/'+ this.email+'/'+boatName);
       },
       resetSearch: function(){
             this.searchName= ""
             this.searchRating= ""
             this.searchAddress= ""
             this.searchCity= ""
             this.searchCountry=""
             this.searchPrice= ""
             this.searchType=""
             this.searchMaxPeople=""
       }
       
      
    },
    computed: {
    filteredBoats: function () {
      var temp = this.boatDtos.filter((boat) => {
        return boat.name.toUpperCase().match(this.searchName) && 
               boat.type.toUpperCase().match(this.searchType) &&
               boat.addressDto.streetAndNum.toUpperCase().match(this.searchAddress) &&
               boat.addressDto.city.toUpperCase().match(this.searchCity) &&
               boat.addressDto.country.toUpperCase().match(this.searchCountry) && 
               boat.rating.toString().match(this.searchRating) && 
               boat.price.toString().startsWith(this.searchPrice) &&
               boat.maxPeople.toString().startsWith(this.searchMaxPeople);
      });

      return temp;
    },
  },
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
