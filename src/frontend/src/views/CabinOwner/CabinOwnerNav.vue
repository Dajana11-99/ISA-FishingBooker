<template>
  <div>
    <nav
      class="navbar navbar-fixed-top navbar-expand"
      style="background-color: #1d7ac9; list-style: none"
    >
      <div class="container-fluid" style="background-color: #1d7ac9">
        <a class="navbar-brand" href="#">
          <img src="../../assets/logoF1.png" alt="" width="194" height="80" />
        </a>
        <li class="nav-item dropdown">
          <a style="color: white;" class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            {{username}}
          </a>
          <ul class="dropdown-menu" style="width: 100%" aria-labelledby="navbarDropdownMenuLink">
            <li><a @click="myProfile()" class="dropdown-item" href="#">Edit profile</a></li>
            <li><a @click="logout()" class="dropdown-item" href="#">Log out</a></li>
          </ul>
        </li>
      </div>
    </nav>

    <ul class="nav justify-content-center" style="background-color: #0b477b">
      <li class="nav-item">
        <a @click="myCabins" style="color: white" class="nav-link active" href="#"
          ><svg
            xmlns="http://www.w3.org/2000/svg"
            width="16"
            height="16"
            fill="currentColor"
            class="bi bi-house"
            viewBox="0 0 16 16"
          >
            <path
              fill-rule="evenodd"
              d="M2 13.5V7h1v6.5a.5.5 0 0 0 .5.5h9a.5.5 0 0 0 .5-.5V7h1v6.5a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 13.5zm11-11V6l-2-2V2.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5z"
            />
            <path
              fill-rule="evenodd"
              d="M7.293 1.5a1 1 0 0 1 1.414 0l6.647 6.646a.5.5 0 0 1-.708.708L8 2.207 1.354 8.854a.5.5 0 1 1-.708-.708L7.293 1.5z"
            />
          </svg>
          MY CABINS</a
        >
      </li>
      <li @click="addNewCabin()" class="nav-item">
        <a style="color: white" class="nav-link active" href="#"
          ><svg
            xmlns="http://www.w3.org/2000/svg"
            width="16"
            height="16"
            fill="currentColor"
            class="bi bi-plus-square"
            viewBox="0 0 16 16"
          >
            <path
              d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"
            />
            <path
              d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"
            />
          </svg>
          ADD NEW CABIN</a
        >
      </li>


     

      <li class="nav-item">
        <a style="color: white" @click="requests()" class="nav-link active" href="#"
          ><svg
            xmlns="http://www.w3.org/2000/svg"
            width="16"
            height="16"
            fill="currentColor"
            class="bi bi-bar-chart-line"
            viewBox="0 0 16 16"
          >
            <path
              d="M11 2a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v12h.5a.5.5 0 0 1 0 1H.5a.5.5 0 0 1 0-1H1v-3a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v3h1V7a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v7h1V2zm1 12h2V2h-2v12zm-3 0V7H7v7h2zm-5 0v-3H2v3h2z"
            />
          </svg>
          STATISTCS</a
        >
      </li>
    </ul>

   <br>

    
  </div>
</template>

<script>
export default ({
  props: {
    username: String,
  },
  methods: {
    myProfile: function () {
      this.$router.push("/editProfile/" + "cabinOwner/" + this.$props.username);
    },
    logout: function(){
      this.$router.push("/")
    },
    addNewCabin: function () {
      this.$router.push("/addNewCabin/" + this.$props.username);
    },
    myCabins: function(){
      this.$router.push("/cabinOwnerHome/" + this.$props.username);
    }
  }, 
});
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
#logincard {
  width: 47%;
  background-image: url("../../assets/IMG_3872.jpeg");
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
