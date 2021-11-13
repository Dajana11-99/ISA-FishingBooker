<template>
  <div id="mainc">
    <nav  class="navbar navbar-fixed-top navbar-expand" style="background-color: #1d7ac9; list-style: none;">
      <div class="container-fluid" style="background-color: #1d7ac9;">
      <a class="navbar-brand" href="http://localhost:8081/">
      <img src="../assets/logoF1.png" alt="" width="194" height="80" >
      </a>
      </div>
    </nav>

   <br>
      <div class="container"  >
      
      <div class="row justify-content-center"  >
      <div class="col-md-6" >
      <div class="card" >
      <header id="header" class="card-header" >
          <h4 class="card-title mt-2">Sign up</h4>
      </header>
      <article class="card-body"  >
      <form method='post' class="was-validated">
          <div class="form-row">
              <div class="col form-group">
                  <label>Name </label>   
                    <input  type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
              </div> 
              <div class="col form-group">
                  <label>Surname</label>
                    <input  type="text" class="form-control" required>
                    <div class="valid-feedback">Valid.</div>
                    <div class="invalid-feedback">Please fill out this field.</div>
              </div> 
          </div>
          <div class="form-group">
              <label>Email</label>
              <input type="email" class="form-control"   required>
              <div class="valid-feedback">Valid.</div>
              <div class="invalid-feedback">Please fill out this field.</div>
          </div>
          <div class="col form-group">
          <label>Password </label>   
            <input type="password" class="form-control"   required>
            <div class="valid-feedback">Valid.</div>
            <div class="invalid-feedback">Please fill out this field.</div>
      </div> 
          <br>
          <div class="form-group">
                  <label class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="gender" value="1"   required>
                <span class="form-check-label"> Male </span>
              </label>
              <label class="form-check form-check-inline">
                <input  class="form-check-input" type="radio" name="gender" value=0   required>
                <span class="form-check-label"> Female</span>
              </label>
              
          </div> 
          <br>
          <div class="form-row">
          <div class="row">
          <div class="col-12">
  <div class="form-group">
   <label >Birthday</label>
   <input  type="date" name="bday" min="1930-01-01"
          max="2003-12-31" class="form-control"  required>
          <div class="valid-feedback">Valid.</div>
          <div class="invalid-feedback">Please fill out this field.</div>
  </div>
    <br>
  </div>
      </div>
          </div> 
          <br>
          <div style="color:red;"></div>
          <br>
          <div class="form-group">
              <button type="submit" class="btn btn-primary"> Sign up  </button>
          </div>                                                
      </form>
      </article> 
      <div class="border-top card-body text-center" >Have an account? <a href="http://localhost:8081/login">Log In</a></div>
      </div> 
      </div> 
      
      </div> 
      
      
      </div> 
     




  </div>

</template>

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
#mainc{

  background-image: linear-gradient(#278ade,black);
}
</style>
