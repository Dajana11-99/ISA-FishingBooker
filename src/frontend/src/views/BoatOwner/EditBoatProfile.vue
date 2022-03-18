<template>
  <BoatOwnerNav :username=email />
  <div>
     <div class="container"  >
      
      <div class="row justify-content-center"  >
      <div class="col-md-12" >
      <div class="card" >
      <header id="header" class="card-header" >
          <h4 class="card-title mt-2">Edit boat profile</h4>
      </header>
      <article class="card-body"  >
      <form @submit="editBoat" method='post' class="was-validated">

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
                    <input v-model="boatDto.name" type="text" class="form-control" disabled>
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
              <PickLocationMap :coordinates=[boatDto.addressDto.latitude,boatDto.addressDto.longitude] />
        </div>

      </div>
      </div>


   <div class="col">
       <div class="row">
        <div class="col form-group">
              <label id="label">Price ($)</label>
              <input v-model="boatDto.price" type="text" pattern="[1-9]+\.?[0-9]*" class="form-control" >
              <div class="valid-feedback">Valid.</div>
              <div class="invalid-feedback">Please fill out this field.</div>
          </div>

           <div class="col form-group">
              <label id="label">Max people</label>
              <input min="1" v-model="boatDto.maxPeople" type="number" class="form-control" >
              <div class="valid-feedback">Valid.</div>
              <div class="invalid-feedback">Please fill out this field.</div>
          </div>

          <div class="form-group">
                    <label id="label">Canceling conditions</label>   
                    <input v-model="boatDto.cancelingCondition"  type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
          </div> 

                

        <div class="mb-3">
             <label id="label" for="formFileMultiple" class="form-label">Import pictures</label>
                  <input @change="onFileSelected" class="form-control" type="file" id="formFileMultiple" multiple>
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
       
        <hr>
        
          <div class="row">
          <div class="col form-group">
              <label id="label">Additional service</label>
              <input v-model="names" type="text"  class="form-control" >
          </div>

          <div class="col form-group">
                    <label id="label">Price($)</label>   
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
         
        

        <div class="row">
        <div id="ConfirmButton"  class="col"><br>
        <button type="button" style="width: 80%; " class="btn btn-lg btn-danger" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
                   Delete boat forever
        </button>
        </div>
        <div id="ConfirmButton"  class="col"><br>
              <button type="submit" style="width: 80%;"  class="btn btn-lg btn-success"> Confirm changes</button>
        </div>   
        </div>
        
          
        

        </div>
        </div>   
        </form>
      </article> 
      </div>
      </div> 
      </div> 
      </div>

      <!-- Modal -->
<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="staticBackdropLabel"></h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <p>Are you sure you want to delete boat?</p>

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button @click="deleteBoat()" type="button" data-bs-dismiss="modal" class="btn btn-danger">Delete</button>
      </div>
    </div>
  </div>
</div>


  </div>

</template>

<script>
  import BoatOwnerNav from './BoatOwnerNav.vue'
  import PickLocationMap from '../../components/PickLocationMap'
  import axios from "axios";


   export default{
    components: {
    BoatOwnerNav,
    PickLocationMap
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
          user:{
           username: ''
         },
         cabinName: '',
         idx: 0,
         imagesSelected: false,
         imagesSelectedEvent: null,
         tableHidden: true,
         additionalServicesAdded: false,
         names: '',
         prices: '',

       }

     },
     mounted() {

        this.email = this.$route.params.email
        this.boatName= this.$route.params.boatName
        this.boatDto.name=this.boatName
        this.boatDto.ownersUsername=this.email
        
        this.getBoat()

     },
     methods: {
       getBoat: function(){
             axios.post("http://localhost:8081/boats/findByName",this.boatDto)
               .then(response => {
                        this.boatDto=response.data
                        this.idx=this.boatDto.additionalServices.length
                        if(this.boatDto.additionalServices.length>0){
                            this.tableHidden=false;
                            this.additionalServicesAdded=true
                        }

                    
              })

       },
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
       deleteBoat: function(){
           axios.post("http://localhost:8081/boats/delete",this.boatDto)
               .then(response => {
                    this.$router.push('/boatOwnerHome/'+ this.email);
                    return response;
              })
       },
       editBoat: function(event){
           event.preventDefault()

           if(this.imagesSelected==true)
               this.boatDto.images=null
           
           axios.post("http://localhost:8081/boats/edit",this.boatDto)
               .then(response => {
                    if(this.imagesSelected==true){
                        this.saveImages()
                    }
                    else{
                        
                       this.$swal.fire({
                       position: 'top-end',
                       icon: 'success',
                       title: 'Changes saved',
                       showConfirmButton: false,
                       timer: 1500
                       })
                       this.$router.push('/boatProfile/'+ this.email+'/'+this.boatName);
                    }
                    return response;
              })

       },
       onFileSelected: function(event){
              this.imagesSelected=true
              this.imagesSelectedEvent=event

       },
       saveImages: function(){
            
               for( var i = 0; i <  this.imagesSelectedEvent.target.files.length; i++ ){
                    let formData = new FormData();
                    let file =  this.imagesSelectedEvent.target.files[i];
                    formData.append('file', file);
                       axios.post("http://localhost:8081/firebase/uploadBoatImage/"+this.boatDto.name,formData)
                    .then(response => {
                        
                       this.$swal.fire({
                       position: 'top-end',
                       icon: 'success',
                       title: 'Changes saved',
                       showConfirmButton: false,
                       timer: 1500
                       })
                       this.$router.push('/boatProfile/'+ this.email+'/'+this.boatName);
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
