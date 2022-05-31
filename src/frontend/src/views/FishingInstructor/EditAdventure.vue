<template>
  <FishingInstructorNavbar :username=email />
  <div>
      <div class="container"  >
      
      <div class="row justify-content-center"  >
      <div class="col-md-12" >
      <div class="card" >
      <header id="header" class="card-header" >
          <h4 class="card-title mt-2">Edit adventure</h4>
      </header>
      <article class="card-body"  >
      <form @submit="editAdventure" method='post' class="was-validated">

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
                    <input v-model="adventureDto.name" type="text" class="form-control" disabled>
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

         
        
          <hr style="color: white;">
              <div class="col form-group">
                  <label id="label">Canceling condition </label>   
                    <input v-model="adventureDto.cancelingCondition" type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
              </div> 
              

        <div class="mb-3">
             <label id="label" for="formFileMultiple" class="form-label">Import pictures</label>
                  <input @change="onFileSelected" class="form-control" type="file" id="formFileMultiple" multiple>
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


          <table  class="table">
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
         
           <div class="row">
            <div id="ConfirmButton"  class="col">
        <button type="button" style="width: 80%; " class="btn btn-lg btn-danger" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
                   Delete cabin forever
        </button>
        </div>
        <div id="ConfirmButton"  class="col">
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
        <p>Are you sure you want to delete adventure?</p>

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button @click="deleteAdventure()" type="button" data-bs-dismiss="modal" class="btn btn-danger">Delete</button>
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
      FishingInstructorNavbar,
    PickLocationMap
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
          prices: '',
           names: '',
          adventureName: '',
         idx: 0,
         imagesSelected: false,
         imagesSelectedEvent: null,
         tableHidden: true,
         additionalServicesAdded: false,
         selectedCancelling: 0
           
       
       }
     },
     mounted() {
       this.email = this.$route.params.email
       this.adventureDto.fishingInstructorUsername= this.email;
       this.adventureDto.name= this.$route.params.adventureName;
       this.getAdventure();

   

     },
     methods: {
        addService: function(){   
              if(this.names!='' && this.prices!=''){
              this.additionalServicesAdded=true
              this.adventureDto.additionalServices[this.idx]={
              name: this.names,
              price: this.prices }
              this.idx++
              this.names=''
              this.prices=''
              }
       },
       editAdventure: function(event){
            event.preventDefault();
      
             if(this.imagesSelected==true)
                this.adventureDto.images=null

                if(this.selectedCancelling==0)
                 this.adventureDto.cancelingCondition='FREE'
               else
                  this.adventureDto.cancelingCondition='NOT FREE'
            axios.post(process.env.VUE_APP_BACKEND_URL+"adventures/canBeEditedOrDeleted/"+this.adventureDto.id)
                  .then(response => {
                     if(response.data == true){

                        axios.post(process.env.VUE_APP_BACKEND_URL+"adventures/edit",this.adventureDto)
                        .then(response => {
                        if(this.imagesSelected==true){
                        this.saveImages()
                      }else{
                          this.$swal.fire({
                          position: 'top-end',
                          icon: 'success',
                          title: 'Changes saved',
                          showConfirmButton: false,
                          timer: 1500
                          })
                          this.$router.push('/adventureProfile/'+ this.email+'/'+this.adventureDto.name);
                    }
                    return response;
              })

                     }else {
                            this.$swal.fire({
                            position: 'top-end',
                            icon: 'error',
                            title: 'This adventure has future reservations and can not be edited!',
                            showConfirmButton: false,
                            timer: 2500
                            })

                     }
              })
           
           

       },
       removeService: function(tableIndex) {
              this.adventureDto.additionalServices.splice(tableIndex,1)
              this.idx--;
       },
      
        getAdventure: function(){
                
             axios.post(process.env.VUE_APP_BACKEND_URL+"adventures/findByName",this.adventureDto)
             .then(response => {
              this.tableHidden=false;
              this.adventureDto=response.data
                this.idx=this.adventureDto.additionalServices.length

                if(this.adventureDto.additionalServices.length>0){
                            this.tableHidden=false;
                            this.additionalServicesAdded=true
                }
                if(this.adventureDto.cancelingCondition=='FREE')
                    this.selectedCancelling=0
                else
                    this.selectedCancelling=1
            

             });      
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
                       axios.post(process.env.VUE_APP_BACKEND_URL+"firebase/uploadAdventureImage/"+this.adventureDto.name,formData)
                    .then(response => {
                       this.$swal.fire({
                       position: 'top-end',
                       icon: 'success',
                       title: 'Changes saved',
                       showConfirmButton: false,
                       timer: 1500
                       })
                       this.$router.push('/adventureProfile/'+ this.email+'/'+this.adventureDto.name);
                      return response;
                    })
              
              }                
        },
        deleteAdventure: function(){
                 axios.post(process.env.VUE_APP_BACKEND_URL+"adventures/canBeEditedOrDeleted/"+this.adventureDto.id)
                  .then(response => {
                     if(response.data == true){
                         axios.post(process.env.VUE_APP_BACKEND_URL+"adventures/deleteAdventure",this.adventureDto)
                        .then(response => {
                          this.$router.push('/fishingInstructorHome/'+ this.email);
                          return response   

                        });   
                     } else{
                           this.$swal.fire({
                            position: 'top-end',
                            icon: 'error',
                            title: 'This adventure has future reservations and can not be deleted!',
                            showConfirmButton: false,
                            timer: 2500
                            })

                     }
              })
          
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
