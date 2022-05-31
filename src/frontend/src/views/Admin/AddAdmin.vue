 <template>
  <AdminNavbar :username=email />
  <div>
  
    &nbsp;  &nbsp;

&nbsp;  

    
      <div class="container"  >
      <div class="row">
           <div class="col">
      <div class="container"  >
      
      <div class="row justify-content-center"  >
      <div class="col-md-12" >
      <div class="card" >
      <header id="header" class="card-header" >
          <h4 class="card-title mt-2">ADD NEW ADMIN</h4>
      </header>
      <article class="card-body"  >
      <form @submit="register" method='post' class="was-validated">
          <div class="form-row">
              <div class="col form-group">
                  <label id="label">First name </label>   
                    <input v-model="user.firstname" type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
              </div> 
              <div class="col form-group">
                  <label id="label">Last name</label>
                    <input   v-model="user.lastname" type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
              </div> 
          </div>
          <div class="form-group">
              <label id="label">Email</label>
              <input  v-model="user.username" type="email" class="form-control" placeholder="someone@gmail.com"   required>
              <div class="valid-feedback">Valid.</div>
              <div class="invalid-feedback">Please fill out this field.</div>
          </div>
          <div class="col form-group">
          <label id="label">Password </label>   
            <input  v-model="user.password" type="password" class="form-control"   required>
            <div class="valid-feedback">Valid.</div>
            <div class="invalid-feedback">Please fill out this field.</div>
      </div> 
          <div class="col form-group">
          <label id="label">Confirm password </label>   
            <input type="password" class="form-control"   required>
            <div class="valid-feedback">Valid.</div>
            <div class="invalid-feedback">Please fill out this field.</div>
      </div> 

       <div class="col form-group">
                  <label id="label">Street and number </label>   
                    <input  v-model="user.address.streetAndNum"  type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
        </div> 

        <div class="col form-group">
                  <label id="label">City </label>   
                    <input  v-model="user.address.city"  type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
         </div> 

        <div class="col form-group">
                  <label id="label">Country </label>   
                    <input   v-model="user.address.country" type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
        </div> 

        <div class="col form-group">
                  <label id="label">Phone number </label>   
                    <input v-model="user.phoneNum"  type="text" class="form-control" required>
                    <div  class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
        </div> 
          <br>
          <div class="form-row">
          <div class="row">
          <div class="col-12">
 
  
  </div>
      </div>
          </div> 
         
          <div style="color:red;"></div>
          <br>
          <div class="form-group">
              <button type="submit" class="btn btn-primary"> Add  </button>
          </div>                                                
      </form>
      </article> 
      </div> 
       </div> 
      </div> 
      
      
      </div> 
     
           </div>
           <div class="col">
<table  class="table">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">First name</th>
      <th scope="col">Last name</th>
      <th scope="col">Username</th>
       <th>&nbsp;</th>
      

    </tr>
  </thead>
   
  <tbody>
    <tr  v-for="(admin,index) in userRequestDTO" :key="index">
       <th scope="row">{{index +1}}</th>
      <td>{{admin.firstname}}</td>
      <td>{{admin.lastname}}</td>
      <td>{{admin.username}}</td>
      <td>
                 
                          
      
      
      
      
      
      </td>
      
      
    </tr>
  </tbody>
</table>
               </div>
           </div>
        </div>
  
  </div>


</template>

<script>
import AdminNavbar from './AdminNav.vue'
import axios from "axios";
   export default{
     components: {
    AdminNavbar 
    },
     data(){
       return{
           email: '',
            user: {
         id: null,
         username:'',
         password: '',
         firstname: '',
         lastname: '',
         phoneNum: '',
         address: {
              longitude: 0,
             latitude: 0,
             country: '',
             city: '',
           streetAndNum: ''
         },
      
       },
         userRequestDTO:
          [{
               username: '',
               firstname: '',
               lastname: '',
           }],
           loader: null,
            pomUserRequestDTO:
            {username: '',},
            isPredefined: false,
         
       
       }
     },
     mounted() {
  // GET request using axios with error handling
          
          this.email = this.$route.params.email
          this.pomUserRequestDTO.username=this.email
          this.loadData();
           
          
      

      },
     methods: {
        register: function(event) {
            event.preventDefault();
              this.loader = this.$loading.show({
                    // Optional parameters
                    container: this.fullPage ? null : this.$refs.formContainer,
                    canCancel: true,
                    onCancel: this.onCancel,
                });

                 axios
               .post(process.env.VUE_APP_BACKEND_URL+"admins/signUpAdmin",this.user)
               .then((response) => {
                    this.loader.hide()
                    this.$router.go();

                   return response; 
               });
        
        },
       redirectDeny: function(user){
         this.$router.push('/reasonForDenying/'+this.email+"/"+user.email);
                  
       },
       loadData: function(){
               axios
               .get(process.env.VUE_APP_BACKEND_URL+"admins/getAllAdmins")
               .then((response) => {

                     this.userRequestDTO=response.data

                   
               });
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
