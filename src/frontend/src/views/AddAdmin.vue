 <template>
  <div>
    <nav  class="navbar navbar-fixed-top navbar-expand" style="background-color: #1d7ac9; list-style: none;">
      <div class="container-fluid" style="background-color: #1d7ac9;">
      <a class="navbar-brand"  href="http://localhost:8080/" >
      <img src="../assets/logoF1.png" alt="" width="194" height="80" >
      </a>
    
    
    
      </div>
    
    </nav>

    <ul class="nav justify-content-center" style="background-color: #0b477b;">

      <li class="nav-item">
      <a style="color: white;" @click="home()" class="nav-link"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-house" viewBox="0 0 16 16">
      <path fill-rule="evenodd" d="M2 13.5V7h1v6.5a.5.5 0 0 0 .5.5h9a.5.5 0 0 0 .5-.5V7h1v6.5a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 13.5zm11-11V6l-2-2V2.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5z"/>
      <path fill-rule="evenodd" d="M7.293 1.5a1 1 0 0 1 1.414 0l6.647 6.646a.5.5 0 0 1-.708.708L8 2.207 1.354 8.854a.5.5 0 1 1-.708-.708L7.293 1.5z"/>
    </svg> HOME</a>
      </li>
      <li class="nav-item">
      <a style="color: white;" @click="users()" class="nav-link"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-people" viewBox="0 0 16 16">
      <path d="M15 14s1 0 1-1-1-4-5-4-5 3-5 4 1 1 1 1h8zm-7.978-1A.261.261 0 0 1 7 12.996c.001-.264.167-1.03.76-1.72C8.312 10.629 9.282 10 11 10c1.717 0 2.687.63 3.24 1.276.593.69.758 1.457.76 1.72l-.008.002a.274.274 0 0 1-.014.002H7.022zM11 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4zm3-2a3 3 0 1 1-6 0 3 3 0 0 1 6 0zM6.936 9.28a5.88 5.88 0 0 0-1.23-.247A7.35 7.35 0 0 0 5 9c-4 0-5 3-5 4 0 .667.333 1 1 1h4.216A2.238 2.238 0 0 1 5 13c0-1.01.377-2.042 1.09-2.904.243-.294.526-.569.846-.816zM4.92 10A5.493 5.493 0 0 0 4 13H1c0-.26.164-1.03.76-1.724.545-.636 1.492-1.256 3.16-1.275zM1.5 5.5a3 3 0 1 1 6 0 3 3 0 0 1-6 0zm3-2a2 2 0 1 0 0 4 2 2 0 0 0 0-4z"/>
    </svg> USERS</a>
      </li>
      <li class="nav-item">
      <a style="color: white;" @click="addAdmin()" class="nav-link"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus-square" viewBox="0 0 16 16">
  <path d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"/>
  <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
</svg>
    ADD ADMIN</a>
      </li>
      <li class="nav-item">
      <a style="color: white;" @click="reviews()" class="nav-link"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil" viewBox="0 0 16 16">
      <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>
    </svg> REVIEWS</a>
      </li>
       <li class="nav-item">
      <a style="color: white;" @click="requests()" class="nav-link"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-plus" viewBox="0 0 16 16">
  <path d="M6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0zm4 8c0 1-1 1-1 1H1s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C9.516 10.68 8.289 10 6 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10z"/>
  <path fill-rule="evenodd" d="M13.5 5a.5.5 0 0 1 .5.5V7h1.5a.5.5 0 0 1 0 1H14v1.5a.5.5 0 0 1-1 0V8h-1.5a.5.5 0 0 1 0-1H13V5.5a.5.5 0 0 1 .5-.5z"/>
</svg> REGISTRATION REQUESTS</a>
      </li>
      <li class="nav-item">
      <a style="color: white;" @click="deleteAccount()"  class="nav-link"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-dash" viewBox="0 0 16 16">
  <path d="M6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0zm4 8c0 1-1 1-1 1H1s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C9.516 10.68 8.289 10 6 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10z"/>
  <path fill-rule="evenodd" d="M11 7.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5z"/>
</svg> DELETING ACCOUNT REQUESTS</a>
      </li>
       <li class="nav-item">
      <a @click="redirectMyAccount()" style="color: white;" class="nav-link"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person" viewBox="0 0 16 16">
  <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0zm4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10z"/>
</svg> MY ACCOUNT</a>
      </li>
       
     </ul>
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

import axios from "axios";
   export default{
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
         
       
       }
     },
     mounted() {
  // GET request using axios with error handling
          
          this.email = this.$route.params.email

          this.loadData();
   
          
      

      },
     methods: {
        register: function(event) {
            event.preventDefault();
                 axios
               .post("http://localhost:8081/account/signUpAdmin",this.user,{
                  headers: {
                  "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
                  "Authorization": "Bearer " + localStorage.jwt ,
                  }
             })
               .then((response) => {

                    this.$router.go();

                   return response; 
               });
        
        },
       redirectDeny: function(user){
         this.$router.push('/reasonForDenying/'+this.email+"/"+user.email,{
            headers: {
            "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
            "Authorization": "Bearer " + localStorage.jwt ,
            }
             });
                  
       },
       users: function(){
           this.$router.push('/allUsers/'+this.email);
       },
       requests: function(){
            this.$router.push('/requests/'+this.email);
       },
       redirectMyAccount: function(){
           this.$router.push('/editProfile/'+'ADMIN/'+this.email);
       },
       home: function(){
           this.$router.push('/profileAdmin/'+this.email);
       },
        addAdmin: function(){
          this.$router.push('/addAdmin/'+ this.email);
       },
       loadData: function(){
               axios
               .get("http://localhost:8081/account/getAllAdmins",{
                  headers: {
                  "Access-Control-Allow-Origin": process.env.VUE_APP_URL,
                  "Authorization": "Bearer " + localStorage.jwt ,
                  }
             })
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
  background-image:  url("../assets/IMG_3872.jpeg"); 
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
