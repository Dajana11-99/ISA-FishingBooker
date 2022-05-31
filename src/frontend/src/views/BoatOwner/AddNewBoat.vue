<template>
 <BoatOwnerNav :username=email />
  <div>
    
     <div class="container"  >
      
      <div class="row justify-content-center"  >
      <div class="col-md-12" >
      <div class="card" >
      <header id="header" class="card-header" >
          <h4 class="card-title mt-2">Add new boat</h4>
      </header>
      <article class="card-body"  >
      <form @submit="addNewBoat" method='post' class="was-validated">

         <div class="row">  
             <div class="col">  
                  <div class="row"> 
          <div class="form-row">
              <div class="col form-group">
                  <label id="label">Owner</label>
                    <input   v-model="boatDto.ownersUsername" type="text" class="form-control" disabled>
              </div> 
              <div class="col form-group">
                  <label id="label">Name </label>   
                    <input v-model="boatDto.name" type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
              </div> 
             
              
          </div>
      
         <div class="col form-group">
                  <label id="label">Type </label>   
                    <input v-model="boatDto.type" type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
              </div> 
        <div class="col form-group">
                  <label id="label">Length(m)</label>   
                    <input v-model="boatDto.length"  type="text"  pattern="[0-9]+\.?[0-9]*" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
        </div> 
        <hr >
        <div class="col form-group">
                  <label id="label">Engine code</label>   
                    <input  v-model="boatDto.engineCode"  type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
        </div> 
        <div class="col form-group">
                  <label id="label">Engine power</label>   
                    <input  v-model="boatDto.enginePower"  type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
        </div> 
        <div class="col form-group">
                  <label id="label">Max speed</label>   
                    <input  v-model="boatDto.maxSpeed"  type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
        </div> 

          <hr >

       <div class="col form-group">
                  <label id="label">Street and number </label>   
                    <input  v-model="boatDto.addressDto.streetAndNum"  type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
        </div> 

        <div class="col form-group">
                  <label id="label">City </label>   
                    <input  v-model="boatDto.addressDto.city"  type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
         </div> 
        <hr style="background-color: white;">
        <div class="col form-group">
                  <label id="label">Country </label>   
                    <input   v-model="boatDto.addressDto.country" type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
        </div> 

        
        <div class="col form-group">
                  <label id="label">Longitude </label>   
                    <input   v-model="boatDto.addressDto.longitude" type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
        </div> 
        <div class="col form-group">
                  <label id="label">Latitude </label>   
                    <input   v-model="boatDto.addressDto.latitude" type="text" class="form-control" required>
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
              <label id="label">Price per hour($)</label>
              <input v-model="boatDto.price" type="text" pattern="[1-9]+\.?[0-9]*" class="form-control" required>
              <div class="valid-feedback">Valid.</div>
              <div class="invalid-feedback">Please fill out this field.</div>
          </div>
          <div class="col form-group">
              <label id="label">Hour captain service ($)</label>
              <input  v-model="captainServicePrice" type="text" pattern="[1-9]+\.?[0-9]*" class="form-control" required >
              <div class="valid-feedback">Valid.</div>
              <div class="invalid-feedback">Please fill out this field.</div>
          </div>

           <div class="col form-group">
              <label id="label">Max people</label>
              <input min="1" v-model="boatDto.maxPeople" type="number" class="form-control" required>
              <div class="valid-feedback">Valid.</div>
              <div class="invalid-feedback">Please fill out this field.</div>
          </div>


                

        <div class="mb-3">
             <label id="label" for="formFileMultiple" class="form-label">Import pictures</label>
                  <input @change="onFileSelected" class="form-control" type="file" id="formFileMultiple" multiple required>
        </div>
     
        <div  class="form-group">
          <label id="label">Description</label>
          <textarea  v-model="boatDto.description" class="form-control" id="exampleFormControlTextarea1" rows="3" required></textarea>
        </div>
        <div  class="form-group">
          <label id="label">Navigation equipment</label>
          <textarea  v-model="boatDto.navigationEquipment" class="form-control" id="exampleFormControlTextarea1" rows="3" required></textarea>
        </div>
       
        <div  class="form-group">
          <label id="label">Fishing equipment</label>
          <textarea  v-model="boatDto.fishingEquipment" class="form-control" id="exampleFormControlTextarea1" rows="3" required></textarea>
        </div>
       
       
         <div  class="form-group">
          <label id="label">Rules</label>
         <textarea  v-model="boatDto.rules" class="form-control" id="exampleFormControlTextarea1" rows="3" required></textarea>
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
                    <label id="label">Price per hour($)</label>   
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
                <tr  v-for="(service,index) in boatDto.additionalServices" :key="index" >
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
  import PickLocationMap from '../../components/PickLocationMap'
  import BoatOwnerNav from './BoatOwnerNav.vue'
  import axios from "axios";


   export default{
    components: {
    PickLocationMap,
    BoatOwnerNav
    },
     data(){
       return{
         email: '',
         captainServicePrice: 1,
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
         additionalServices: [],
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
           ownerId: 0,
           
       userRequestDto: {
          username: '',
       },
       selectedCancelling: 0
       }

     },
     mounted() {
       this.email = this.$route.params.email
       this.boatDto.ownersUsername= this.email
       this.userRequestDto.username=this.email
       axios.post(process.env.VUE_APP_BACKEND_URL+"auth/findByEmail", this.userRequestDto)
               .then(response => {
                        
                        this.ownerId=response.data.id 
              })
              
     },
     methods: {
       addService: function(){   
              if(this.names!='' && this.prices!=''){
              this.additionalServicesAdded=true
              this.tableHidden=false  
              this.boatDto.additionalServices[this.idx]={
              name: this.names,
              price: this.prices }
              this.idx++
              this.names=''
              this.prices=''
              }
       },
       removeService: function(tableIndex) {
              this.boatDto.additionalServices.splice(tableIndex,1)
              this.idx--;
       },
       onFileSelected: function(event){
              console.log(event)
              this.imagesSelected=true
              this.imagesSelectedEvent=event

       },
       updateLocation: function(latitude,longitude){

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
                    this.boatDto.addressDto.city = address.town;
                }
                else if (flag && address.city) {
                    this.boatDto.addressDto.city = address.city;
                }
                else if (address.country) {
                    this.boatDto.addressDto.country = address.country;
                }
                this.boatDto.addressDto.streetAndNum= street + ' ' +number
                this.boatDto.addressDto.longitude=longitude
                this.boatDto.addressDto.latitude=latitude
                }
               })
             
        },
        addNewBoat: function(event){
               event.preventDefault();
            
                  this.boatDto.additionalServices.push({
                        id: null,
                        name: "Captain service",
                        price:  this.captainServicePrice
                  })
                  if(this.selectedCancelling==0){
                    this.boatDto.cancelingCondition='FREE'
                  }else{
                    this.boatDto.cancelingCondition='NOT FREE'
                  }
                   this.loader = this.$loading.show({
                    container: this.fullPage ? null : this.$refs.formContainer,
                    canCancel: true,
                    onCancel: this.onCancel,
                   });
                          axios.post(process.env.VUE_APP_BACKEND_URL+"boats/save",this.boatDto)
                          .then(response => {
                                  
                                    this.saveImages()
                                    
                                 
                                    return response;   
                          })
                          .catch(err => {
                                this.loader.hide();
                                this.loader=null
                                this.$swal.fire({
                                      position: 'top-end',
                                      icon: 'error',
                                      title: "Boat with that name already exists.",
                                      showConfirmButton: false,
                                      timer: 1500
                                })
                                this.resetForm()
                                console.log(err)
                          }
                          )
        },
        saveImages: function(){
            
               for( var i = 0; i <  this.imagesSelectedEvent.target.files.length; i++ ){
                    let formData = new FormData();
                    let file =  this.imagesSelectedEvent.target.files[i];
                    formData.append('file', file);
                       axios.post(process.env.VUE_APP_BACKEND_URL+"firebase/uploadBoatImage/"+this.boatDto.name+"/"+this.ownerId,formData)
                       .then(response => {
                                    this.loader.hide();
                                    this.loader=null
                                    this.$router.push('/boatOwnerHome/'+ this.email);    
                                    
                      return response;
                    })
              
              }                
        },
        resetForm: function(){
          this.captainServicePrice=1
           this.boatDto={

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
         additionalServices: [],
         cancelingCondition: '',
         rating: '', 
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
