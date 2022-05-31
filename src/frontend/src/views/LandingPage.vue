<template>
  <div class="slikadiv">
    <nav  class="navbar navbar-fixed-top navbar-expand" style="background-color: #1d7ac9; list-style: none; ">
      <div class="container-fluid" style="background-color: #1d7ac9;">
      <a class="navbar-brand"   >
      <img src="../assets/logoF1.png" alt="" width="200" height="80" >
      </a>
      </div>
    <div class="navdiv">
     <button style="width: 80%;" @click="signup()"  class="btn " type="button">SIGN UP</button>
 
      <button style="width: 80%;" @click="signin()"  class="btn btn-in" type="button">SIGN IN</button>

       </div> 
    </nav>

    <div class="container">
    
       <p class="sp" >Welcome to Fisherman, world's newest fishing trip booker.
            The best place to find fishing cabins, boats and instructors.</p>
            <hr>
       <button @click="home()" class="btn" ><b>Start your adventure now</b></button>
    </div>
      <br><br>
       <br><br>
        <br><br>
         <br><br>
         <br><br><br><br>
         
      <div>
           <img src="../assets/1.jpg" width="250" height="200" style="padding: 1%; box-shadow: rgba(9, 30, 66, 0.25) 0px 1px 1px, rgba(9, 30, 66, 0.13) 0px 0px 0px 0px;" class="img" alt="...">
           <img src="../assets/5.jpg" width="250" height="200" style="padding: 1%; box-shadow: rgba(9, 30, 66, 0.25) 0px 1px 1px, rgba(9, 30, 66, 0.13) 0px 0px 0px 0px;" class="img" alt="...">
           <img src="../assets/2.jpg" width="250" height="200" style="padding: 1%; box-shadow: rgba(9, 30, 66, 0.25) 0px 1px 1px, rgba(9, 30, 66, 0.13) 0px 0px 0px 0px;" class="img" alt="...">
           <img src="../assets/4.jpg" width="250" height="200" style="padding: 1%; box-shadow: rgba(9, 30, 66, 0.25) 0px 1px 1px, rgba(9, 30, 66, 0.13) 0px 0px 0px 0px;" class="img" alt="...">
           <img src="../assets/3.jpg" width="250" height="200" style="padding: 1%; box-shadow: rgba(9, 30, 66, 0.25) 0px 1px 1px, rgba(9, 30, 66, 0.13) 0px 0px 0px 0px;" class="img" alt="...">
       </div>
  </div>
  <br><br> <br>
      
       

<div style="padding: 1%; box-shadow: rgba(0, 0, 0, 0.08) 0px 4px 12px;" class="card">
  <div class="card-body">
       <h1 style="text-align: center;">Fisherman familly</h1>
  
  <div style="padding-left: 25%; padding-right: 25%; text-align: center; font-size: 20px;">
      Welcome to Fisherman, world's newest fishing trip booker. 
Fisherman can help you find and plan the perfect fishing adventure. 
Our main goal is to provide a place to connect fishing enthusiasts of all ages.
If you are fishing instructor, captain or cabin/boat owner this is the place to offer your services. Help us provide unforgettable experiences to our clients. 
  </div>
  </div>
</div>

<div class="row " style="padding: 3%; width: 100%;">
    <h1 style="text-align: center;">What we offer</h1> 
    <hr style="background-color: white">
  <div class="col">
    <div class="card ">
      <img src="../assets/cabin.jpg"  width="700" height="300" class="card-img-top" alt="...">
      <div class="card-body">
        <h4 class="card-title">Cabins</h4>
        <p class="card-text">Many owners rent out cabins through Fishermen. You can easily find a cottage that meets your preferences.</p>
    <button @click="home()" style="width: 80%; color: white;" type="button" class="btn btn-info">Explore</button>
      </div>
     
    </div>
  </div>
  <div class="col">
    <div class="card ">
      <img src="../assets/boat.jpg" width="700" height="300" class="card-img-top" alt="...">
      <div class="card-body">
        <h4 class="card-title">Boats</h4>
        <p class="card-text">We partnered with boat owners who provide captain services or rent boats.</p>
      
         <button @click="home()" style="width: 80%; color: white;" type="button" class="btn btn-info">Explore</button>
      </div>
   
    </div>
  </div>
  <div class="col">
    <div class="card ">
      <img src="../assets/adventure.jpg" width="700" height="300" class="card-img-top" alt="...">
      <div class="card-body">
        <h4 class="card-title">Adventures</h4>
        <p class="card-text">Fisherman can help you find the most interesting and suitable fishing adventures offered by licensed instructors.</p>
       <button @click="home()" style="width: 80%; color: white;" type="button" class="btn btn-info">Explore</button>
     
      </div>
     
    </div>
  </div>
 
</div>
<div style="padding: 1%; box-shadow: rgba(0, 0, 0, 0.08) 0px 4px 12px;" class="card">
  <div class="buttonns">
       <h1 style="text-align: center; " >Join us today</h1>
       <br>
     <button @click="signup()"  style="width: 40%; box-shadow: rgba(0, 0, 0, 0.08) 0px 4px 12px;" class="btn btn-lg btn-primary" type="button">SIGN UP</button>
     <hr style="background-color: white;"> 
      <button @click="signin()"  style="width: 40%; box-shadow: rgba(0, 0, 0, 0.08) 0px 4px 12px;" class="btn btn-lg btn-primary" type="button">SIGN IN</button>
  </div>
  <br><br><br><br>
</div>

<div style="background-color: #e6e5e3;" class="footer" >
   <br><br>
    This is academic project.
   <br><br> <br><br>
</div>


       <div>
       </div>
       
</template>


<script>
   export default{
     data(){
       return{
         
         
       }
     },
     methods: {
      signup: function(){
          this.$router.push("/signup")
      },
      signin: function(){
           this.$router.push("/login")
      },
      home: function(){
          this.$router.push("/home")
      }
   
    }
  }

</script> 

<style>

.container img {
  width: 1320px;

}
.container {
    
    width: 1320px;
    text-align: right;
}
.container .btn {
 
  background-color: #1d7ac9;
  color: white;
  font-size: 20px;
  padding: 12px 24px;
  border: none;
  cursor: pointer;
  border-radius: 5px;
  text-align: center;
}
.navdiv .btn
{
  background-color: white;
  border: 1px solid #0b243a;
 color: #0b243a;
 
}
.navdiv .btn-in
{
  background-color: #0b243a;
  color: white;
 
}
.navdiv .btn-in:hover
{
  background-color: white;
  color: #0b243a;
 
}
.navdiv .btn:hover
{
 
   color: white;
  background-color: #0b243a;
  border: 1px solid #0b243a;
}
.buttonns .btn {
 
  color: white ;
  background-color: #0b243a;
  font-size: 20px;
  padding: 12px 24px;
  border: none;
  cursor: pointer;
  border-radius: 5px;
  text-align: center;
}
.container .sp {
     text-align: left;
     font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif;
     margin-top: 10%;
     color: white;
     font-size: 40px;
     font-weight: 670;

}
.sp1 {
     text-align: left;
     font-family: sans-serif;
     color: #0b243a;
     font-size: 40px;
     margin-left: 3%;
    /* font-weight: 800;*/

}

.container .btn:hover {
  background-color: white ;
  color: #0b243a;
}
.buttonns .btn:hover {
  background-color: white ;
  color: #0b243a;
  border: 1px solid #0b243a ;
}

.slikadiv{
  background-image: url('../assets/santorinifishing.jpg');
  background-repeat: no-repeat;
  background-size: 100% 100%;
  height: 900px;
  
  
     
}
</style>
