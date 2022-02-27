<template>
  <div>
     <nav  class="navbar navbar-fixed-top navbar-expand" style="background-color: #1d7ac9; list-style: none;">
  <div class="container-fluid" style="background-color: #1d7ac9;">
  <a class="navbar-brand">
  <img src="../assets/logoF1.png" alt="" width="194" height="80" >
</a>

<ul class="navbar-nav me-auto mb-2 mb-lg-0">

<li class="nav-item">
  <a style="color: white;" class="nav-link active" aria-current="page" href="http://localhost:8080/login">LOGIN</a>
</li>
<li class="nav-item">
  <a style="color: white;" class="nav-link active" href="http://localhost:8080/signup">SIGN UP</a>
</li>
</ul>


  


  




  </div>

</nav>
   <button @click="prikazi1()">PRIKAZI 1</button>
   <button @click="prikazi2()">PRIKAZI 2</button>
   <button @click="prikazi3()">PRIKAZI 3</button>
   
  </div>

</template>



<script>
   
  
export default {
 

  data() {
    return {
      
    
       
    };
  },
  mounted() {
    
  },
  methods: {
  prikazi1: function(){
         this.$swal('Hello Vue world!!!');
      
     
    },
    prikazi2: function(){
           this.$swal.fire({
                 position: 'top-end',
                  icon: 'success',
                 title: 'Your work has been saved',
               showConfirmButton: false,
               timer: 1500
    })
    },
    prikazi3: function(){
              this.$swal.fire(
             'Good job!',
              'You clicked the button!',
              'success'
    )

    }
   
 
    
  
  }
};
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

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
