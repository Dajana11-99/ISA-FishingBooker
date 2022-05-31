<template> 
  <FishingInstructorNavbar :username=email />
  <div>
      <div class="container"  >
      
      <div class="row justify-content-center"  >
      <div class="col-md-12" >
      <div class="card" >
      <header id="header" class="card-header" >
          <h4 class="card-title mt-2">Add new adventure</h4>
      </header>
      <article class="card-body"  >
      <form @submit="addNewAdventure" method='post' class="was-validated">

         <div class="row">  
             <div class="col">  
                  <div class="row"> 
          <div class="form-row">
              <div class="col form-group">
                  <label id="label">Fishing instructor</label>
                    <input   v-model="adventureDto.fishingInstructorUsername" type="text" class="form-control" disabled>
              </div> 

            <div  class="form-group">
          <label id="label">Biography</label>
          <textarea  v-model="adventureDto.instructorsBiography" class="form-control" id="exampleFormControlTextarea1" rows="3" required></textarea>

       </div>
              <div class="col form-group">
                  <label id="label">Name </label>   
                    <input v-model="adventureDto.name" type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
              </div> 
              
          </div>
          

       <div class="col form-group">
                  <label id="label">Street and number </label>   
                    <input  v-model="adventureDto.address.streetAndNum"  type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
        </div> 

        <div class="col form-group">
                  <label id="label">City </label>   
                    <input  v-model="adventureDto.address.city"  type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
         </div> 
        <hr style="background-color: white;">
        <div class="col form-group">
                  <label id="label">Country </label>   
                    <input   v-model="adventureDto.address.country" type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
        </div> 

        
        <div class="col form-group">
                  <label id="label">Longitude </label>   
                    <input   v-model="adventureDto.address.longitude" type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
        </div> 
        <div class="col form-group">
                  <label id="label">Latitude </label>   
                    <input   v-model="adventureDto.address.latitude" type="text" class="form-control" required>
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
              <label id="label">Price ($)</label>
              <input v-model="adventureDto.price" type="text" pattern="[1-9]+\.?[0-9]*" class="form-control" >
              <div class="valid-feedback">Valid.</div>
              <div class="invalid-feedback">Please fill out this field.</div>
          </div>

          <div class="col form-group">
                    <label id="label">Max people allowed  </label>   
                    <input v-model="adventureDto.maxPeople" min="1"   type="number" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
                    </div> 

         
        
         
              

        <div class="mb-3">
             <label id="label" for="formFileMultiple" class="form-label">Import pictures</label>
                  <input @change="onFileSelected" class="form-control" type="file" id="formFileMultiple" multiple required>
        </div>
     
        
        
        <div  class="form-group">
          <label id="label">Description</label>
          <textarea  v-model="adventureDto.description" class="form-control" id="exampleFormControlTextarea1" rows="3" required></textarea>
       </div>

        <div  class="form-group">
          <label id="label">Equipment</label>
         <textarea  v-model="adventureDto.equipment" class="form-control" id="exampleFormControlTextarea1" rows="3" required></textarea>
      </div>
       
         <div  class="form-group">
          <label id="label">Rules</label>
         <textarea  v-model="adventureDto.rules" class="form-control" id="exampleFormControlTextarea1" rows="3" required></textarea>
      </div>

       <hr style="color: white;">
            
       </div>     
       <div class="col form-group">
             <label id="label" for="formFileMultiple" class="form-label">Cancelling</label>

             <select v-model="selectedCancelling" class="form-select form-select-sm" aria-label=".form-select-sm example">
                 <option value="0">FREE</option>
                 <option value="1">OWNER KEEPS PERCENTAGE</option>
             </select>
        </div>
       
        <hr>
        
          <div class="row">
          <div class="col form-group">
              <label id="label">Additional service</label>
              <input v-model="names" type="text"  class="form-control" >
          </div>

          <div class="col form-group">
                    <label id="label">Price ($)</label>   
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
                <tr  v-for="(service,index) in adventureDto.additionalServices" :key="index" >
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
  import FishingInstructorNavbar from './FishingInstructorNav.vue'
  import PickLocationMap from '../../components/PickLocationMap'
  import axios from "axios";
  export default{
    components: {
    FishingInstructorNavbar ,
    PickLocationMap
    },
     data(){
       return{
         email: '',
         selectedCancelling: 0,
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
       this.adventureDto.fishingInstructorUsername= this.email;
   

     },
     methods: {
        addService: function(){   
              if(this.names!='' && this.prices!=''){
              this.additionalServicesAdded=true
              this.tableHidden=false  
              this.adventureDto.additionalServices[this.idx]={
              name: this.names,
              price: this.prices }
              this.idx++
              this.names=''
              this.prices=''
              }
       },
       removeService: function(tableIndex) {
              this.adventureDto.additionalServices.splice(tableIndex,1)
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
                           "accept-language": "en",
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
                    this.adventureDto.address.city = address.town;
                }
                else if (flag && address.city) {
                    this.adventureDto.address.city = address.city;
                }
                else if (address.country) {
                    this.adventureDto.address.country = address.country;
                }
                this.adventureDto.address.streetAndNum= street + ' ' +number
                this.adventureDto.address.longitude=longitude
                this.adventureDto.address.latitude=latitude
                }
               })
             
        },
        addNewAdventure: function(event){
               event.preventDefault();
               if(this.additionalServicesAdded==false)
                  this.adventureDto.additionalServices=null   
                 this.loader = this.$loading.show({
                    // Optional parameters
                    container: this.fullPage ? null : this.$refs.formContainer,
                    canCancel: true,
                    onCancel: this.onCancel,
                });
                
               if(this.selectedCancelling==0)
                 this.adventureDto.cancelingCondition='FREE'
               else
                  this.adventureDto.cancelingCondition='NOT FREE'
               axios.post(process.env.VUE_APP_BACKEND_URL+"adventures/save",this.adventureDto)
               .then(response => {
                        
                        this.saveImages()
                        
                        return response;   
              }).catch(err =>{
                    this.loader.hide();
                    this.loader=null;
                    this.$swal.fire({
                      position: 'top-end',
                      icon: 'error',
                      title: 'Adventure name must be unique!',
                      showConfirmButton: false,
                      timer: 2500
                   })
                      return err
              });

              
        },
        saveImages: function(){
            
               for( var i = 0; i <  this.imagesSelectedEvent.target.files.length; i++ ){
                    let formData = new FormData();
                    let file =  this.imagesSelectedEvent.target.files[i];
                    formData.append('file', file);
                       axios.post(process.env.VUE_APP_BACKEND_URL+"firebase/uploadAdventureImage/"+this.adventureDto.name,formData)
                      .then(response => {
                          this.loader.hide();
                          this.loader=null;
                          this.$router.push('/fishingInstructorHome/'+ this.email);
                       return response;
                    })
              
              }                
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
