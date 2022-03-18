<template>
  <CabinOwnerNavBar :username=email />
  <div>
      <div class="container"  >
      
      <div class="row justify-content-center"  >
      <div class="col-md-12" >
      <div class="card" >
      <header id="header" class="card-header" >
          <h4 class="card-title mt-2">Add new cabin</h4>
      </header>
      <article class="card-body"  >
      <form @submit="addNewCabin" method='post' class="was-validated">

         <div class="row">  
             <div class="col">  
                  <div class="row"> 
          <div class="form-row">
              <div class="col form-group">
                  <label id="label">Owner</label>
                    <input   v-model="cabinDto.ownerUsername" type="text" class="form-control" disabled>
              </div> 
              <div class="col form-group">
                  <label id="label">Name </label>   
                    <input v-model="cabinDto.name" type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
              </div> 
              
          </div>
          

       <div class="col form-group">
                  <label id="label">Street and number </label>   
                    <input  v-model="cabinDto.addressDto.streetAndNum"  type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
        </div> 

        <div class="col form-group">
                  <label id="label">City </label>   
                    <input  v-model="cabinDto.addressDto.city"  type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
         </div> 
        <hr style="background-color: white;">
        <div class="col form-group">
                  <label id="label">Country </label>   
                    <input   v-model="cabinDto.addressDto.country" type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
        </div> 

        
        <div class="col form-group">
                  <label id="label">Longitude </label>   
                    <input   v-model="cabinDto.addressDto.longitude" type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
        </div> 
        <div class="col form-group">
                  <label id="label">Latitude </label>   
                    <input   v-model="cabinDto.addressDto.latitude" type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
        </div> 
        <hr style="color: white;">
        <div class="form-group" align="center" vertical-align="center" style=" width: 100%; height: 400px">
              <PickLocationMap :coordinates=[21.0059,44.0165] />
        </div>

      </div>
      </div>


   <div class="col">
       <div class="row">
        <div class="col form-group">
              <label id="label">Price per night ($)</label>
              <input v-model="cabinDto.price" type="text" pattern="[1-9]+\.?[0-9]*" class="form-control" >
              <div class="valid-feedback">Valid.</div>
              <div class="invalid-feedback">Please fill out this field.</div>
          </div>

          <div class="col form-group">
                    <label id="label">Number of rooms </label>   
                    <input min="1" v-model="cabinDto.numOfRooms"  type="number" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
                    </div> 

          <div class="col form-group">
                    <label id="label">Beds per room </label>   
                    <input min="1" v-model="cabinDto.bedsPerRoom"  type="number" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
          </div> 
                

        <div class="mb-3">
             <label id="label" for="formFileMultiple" class="form-label">Import pictures</label>
                  <input @change="onFileSelected" class="form-control" type="file" id="formFileMultiple" multiple required>
        </div>
     
        <div  class="form-group">
          <label id="label">Description</label>
          <textarea  v-model="cabinDto.description" class="form-control" id="exampleFormControlTextarea1" rows="3" required></textarea>
       </div>
       
         <div  class="form-group">
          <label id="label">Rules</label>
         <textarea  v-model="cabinDto.rules" class="form-control" id="exampleFormControlTextarea1" rows="3" required></textarea>
      </div>

       <hr style="color: white;">
            
       </div>     
       
        <hr>
        
          <div class="row">
          <div class="col form-group">
              <label id="label">Additional service</label>
              <input v-model="names" type="text"  class="form-control" >
          </div>

          <div class="col form-group">
                    <label id="label">Price per night ($)</label>   
                    <input v-model="prices"  type="text" pattern="[0-9]+\.?[0-9]*" class="form-control" >
                  
          </div> 
              <div id="AddButton" class=" col form-group">
                    <br>
              <input @click="addService()" type="button" value="add" class="btn btn-outline-success" >
              </div> 
           
          </div>


          <table v-if="tableHidden==false" class="table">
                <thead>
                  <tr>
                    <th scope="col">#</th>
                    <th scope="col">Service</th>
                    <th scope="col">Price ($)</th>
                    <th scope="col">&nbsp;</th>
                  </tr>
                 </thead>
   
                <tbody>
                <tr  v-for="(service,index) in cabinDto.additionalServices" :key="index" >
                <th scope="row">{{index +1}}</th>
                <td>{{service.name}}</td>
                <td>{{service.price}}</td>
                <td>

                  <input @click="removeService(index)" type="button" value="remove" class="btn btn-outline-danger" >
                </td>
                </tr>          
                </tbody>
          </table>
         
        

        <div id="ConfirmButton"  class="form-group"><br>
              <button type="submit"  class="btn btn-lg btn-success"> Confirm  </button>
        </div>   
          
        

        </div>
        </div>   
        </form>
      </article> 
      </div>
      </div> 
      </div> 
      </div>

  </div>

</template>

<script>
   import CabinOwnerNavBar from './CabinOwnerNav.vue'
   import PickLocationMap from '../../components/PickLocationMap'
   import axios from "axios";
    
   export default{
    components: {
    CabinOwnerNavBar,
    PickLocationMap
    },
     data(){
       return{
         email: '',
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
           prices: '',
           names: '',
           idx: 0,
           tableHidden: true,
           selectedFile: null,
           imagesSelected: false,
           imagesSelectedEvent: null,
           additionalServicesAdded: false,
           loader: null
       
       }
     },
     mounted() {
       this.email = this.$route.params.email
       this.cabinDto.ownerUsername=this.email
     },
     methods: {
       myProfile: function(){
         this.$router.push('/editProfile/'+'cabinOwner/'+ this.email);
       },
       myCabins: function(){
         this.$router.push('/cabinOwnerHome/'+ this.email);
       },
       logout: function(){
         this.$router.push('/');
       },
       addService: function(){   
              if(this.names!='' && this.prices!=''){
              this.additionalServicesAdded=true
              this.tableHidden=false  
              this.cabinDto.additionalServices[this.idx]={
              name: this.names,
              price: this.prices }
              this.idx++
              this.names=''
              this.prices=''
              }
       },
       removeService: function(tableIndex) {
              this.cabinDto.additionalServices.splice(tableIndex,1)
              this.idx--;
       },
       onFileSelected: function(event){
              console.log(event)
              this.imagesSelected=true
              this.imagesSelectedEvent=event

       },
       updateLocation: function(latitude,longitude){

           console.log("POGODIO");
                 axios.get("https://nominatim.openstreetmap.org/reverse", {
                           params: {
                           lat: longitude,
                           lon: latitude,
                           format: "json",
                 },
                 })
                 .then((response) => {
                       const { address } = response.data;
                       var flag = false;
            var street
            var number
                if (address) {
    
                if (address.road) {
                    street = address.road;
      
                    flag = true;
                } else if (address.street) {
                    street = address.street;
                    flag = true;
                }
                if (flag && address["house-number"]) {
                    number = address["house-number"];
                }
                else if (flag && address["house_number"]) {
                    number = address["house_number"];
                }
                if (flag && address.town) {
                    this.cabinDto.addressDto.city = address.town;
                }
                else if (flag && address.city) {
                    this.cabinDto.addressDto.city = address.city;
                }
                else if (address.country) {
                    this.cabinDto.addressDto.country = address.country;
                }
                this.cabinDto.addressDto.streetAndNum= street + ' ' +number
                this.cabinDto.addressDto.longitude=longitude
                this.cabinDto.addressDto.latitude=latitude
                }
               })
             
        },
        addNewCabin: function(event){
               event.preventDefault();
               if(this.additionalServicesAdded==false)
                  this.cabinDto.additionalServices=null   

                   this.loader = this.$loading.show({
                    // Optional parameters
                    container: this.fullPage ? null : this.$refs.formContainer,
                    canCancel: true,
                    onCancel: this.onCancel,
                });
               axios.post("http://localhost:8081/cabins/save",this.cabinDto)
               .then(response => {
                        
                        if(this.imagesSelected==true)
                        this.saveImages()
                        else{
                        this.loader.hide();
                        this.$router.push('/cabinOwnerHome/'+ this.email);
                        }
                        return response;   
              })
        },
        saveImages: function(){
            
               for( var i = 0; i <  this.imagesSelectedEvent.target.files.length; i++ ){
                    let formData = new FormData();
                    let file =  this.imagesSelectedEvent.target.files[i];
                    formData.append('file', file);
                    axios.post("http://localhost:8081/firebase/uploadCabinImage/"+this.cabinDto.name,formData)
                    .then(response => {
                       this.loader.hide();
                       this.loader=null
                       this.$router.push('/cabinOwnerHome/'+ this.email);
                       return response;
                    })
                    


              
              }                
        }
      
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
#ConfirmButton{

   text-align: right;

}
#AddButton{

   text-align: left;

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
