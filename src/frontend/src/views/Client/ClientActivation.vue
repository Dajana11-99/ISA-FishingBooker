<template>
  <div>
    <nav
      class="navbar navbar-fixed-top navbar-expand"
      style="background-color: #1d7ac9; list-style: none"
    >
      <div class="container-fluid" style="background-color: #1d7ac9">
        <a class="navbar-brand" href="http://localhost:8080/">
          <img src="../../assets/logoF1.png" alt="" width="194" height="80" />
        </a>
      </div>
    </nav>
    <br />

    <div class="jumbotron">
      <h1 class="display-4">Hello, {{ this.activationDTO.email }}!</h1>
      <p class="lead">You have successfully verified your account.</p>
      <hr class="my-4" />
      <p>Log in and start using Fisherman.</p>
      <p class="lead">
        <a
          class="btn btn-primary btn-lg"
          href="http://localhost:8080/login"
          role="button"
          >Go to login</a
        >
      </p>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "ConfirmEmail",
  data() {
    return {
      activationDTO: {
        activationCode: "",
        email: "",
      },
    };
  },
  mounted() {
    this.activationDTO.activationCode = this.$route.params.activationCode;
    this.activationDTO.email = this.$route.params.email;
    console.log(this.activationDTO);
    axios
      .post(
        process.env.VUE_APP_BACKEND_URL+"auth/clientAccountActivation",
        this.activationDTO
      )
      .then((response) => {
        this.$swal.fire({
          position: "top-end",
          icon: "success",
          title: response.data,
          showConfirmButton: false,
          timer: 2000,
        });
      })
      .catch(() => {
        this.$swal.fire({
          icon: "error",
          title: "Something went wrong!",
          text: "Unsuccessful account activation!",
        });
      });
  },
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