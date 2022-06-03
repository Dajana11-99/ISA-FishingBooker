<template>
  <div class="page-content page-container" id="page-content">
    <div class="padding">
      <div class="row container d-flex justify-content-center">
        <div class="col">
          <div class="card user-card-full">
            <div class="row m-l-0 m-r-0">
              <div class="col-sm-4 bg-c-lite-green user-profile">
                <div class="card-block text-center text-white">
                  <div class="m-b-25">
                    <svg
                      viewBox="0 0 36 36"
                      fill="none"
                      role="img"
                      xmlns="http://www.w3.org/2000/svg"
                      width="80"
                      height="80"
                    >
                      <title>Betty Ford</title>
                      <mask
                        id="mask__beam"
                        maskUnits="userSpaceOnUse"
                        x="0"
                        y="0"
                        width="36"
                        height="36"
                      >
                        <rect
                          width="36"
                          height="36"
                          rx="72"
                          fill="#FFFFFF"
                        ></rect>
                      </mask>
                      <g mask="url(#mask__beam)">
                        <rect width="36" height="36" fill="#708585"></rect>
                        <rect
                          x="0"
                          y="0"
                          width="36"
                          height="36"
                          transform="translate(-1 -1) rotate(205 18 18) scale(1.1)"
                          fill="#dedde8"
                          rx="36"
                        ></rect>
                        <g transform="translate(-5 -3) rotate(5 18 18)">
                          <path
                            d="M15 20c2 1 4 1 6 0"
                            stroke="#000000"
                            fill="none"
                            stroke-linecap="round"
                          ></path>
                          <rect
                            x="14"
                            y="14"
                            width="1.5"
                            height="2"
                            rx="1"
                            stroke="none"
                            fill="#000000"
                          ></rect>
                          <rect
                            x="20"
                            y="14"
                            width="1.5"
                            height="2"
                            rx="1"
                            stroke="none"
                            fill="#000000"
                          ></rect>
                        </g>
                      </g>
                    </svg>
                  </div>
                  <h6 class="f-w-600">{{ fullName }}</h6>
                  <p>{{ MyUser.username }}</p>
                  <i
                    class="
                      mdi mdi-square-edit-outline
                      feather
                      icon-edit
                      m-t-10
                      f-16
                    "
                  ></i>
                  <p v-if="userRole != 'ROLE_CLIENT'" style="color: orange">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      style="color: orange"
                      width="32"
                      height="32"
                      fill="currentColor"
                      class="bi bi-star-fill"
                      viewBox="0 0 16 16"
                    >
                      <path
                        d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"
                      />
                    </svg>
                    {{ user.rating }}
                  </p>
                  <i
                    class="
                      mdi mdi-square-edit-outline
                      feather
                      icon-edit
                      m-t-10
                      f-16
                    "
                  ></i>
                </div>
              </div>
              <div class="col-sm-8">
                <div class="card-block">
                  <h6 class="m-b-20 p-b-5 b-b-default f-w-600">Information</h6>
                  <div class="row">
                    <div class="col-sm-6">
                      <p class="m-b-10 f-w-600">Location</p>
                      <h6 class="text-muted f-w-400">
                        {{ MyUser.address.city }}, {{ MyUser.address.country }}
                      </h6>
                    </div>
                    <div class="col-sm-6">
                      <p class="m-b-10 f-w-600">Account type</p>
                      <h6 v-if="MyUser.role" class="text-muted f-w-400">
                        {{ getRoleWithoutPrefix(MyUser.role) }}
                      </h6>
                    </div>
                  </div>
                  <h6 class="m-b-20 m-t-40 p-b-5 b-b-default f-w-600">
                    Account rank
                  </h6>
                  <div class="row">
                    <div class="col-sm-6">
                      <p class="m-b-10 f-w-600">Status</p>
                      <h6 class="text-muted f-w-400">{{ user.rankType }}</h6>
                    </div>
                    <div class="col-sm-6">
                      <p class="m-b-10 f-w-600">Points</p>
                      <h6 class="text-muted f-w-400">{{ user.points }}</h6>
                    </div>
                  </div>
                  <template v-if="showDiscount">
                    <div class="row">
                      <div class="col-sm-6">
                        <p class="m-b-10 f-w-600">Discount %</p>
                        <h6 class="text-muted f-w-400">
                          {{ getDiscountPercentage(user.rankType) }}
                        </h6>
                      </div>
                      <div class="col-sm-6">
                        <p class="m-b-10 f-w-600">Progress to next rank</p>
                        <h6 class="text-muted f-w-400">
                          {{ progressPercentageToNextLevel(user.points) }}
                        </h6>
                      </div>
                    </div>
                  </template>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div
    v-if="showComments == true"
    style="text-align: left; padding-left: 13%; padding-right: 11%"
  >
    <h1>Comments about user</h1>
    <hr />
    <h3 v-if="comments.length == 0">No comments to show.</h3>
    <template v-if="comments.length != 0">
      <div v-for="(comment, index) in comments" :key="index" class="row">
        <div class="col-sm-1">
          <svg
            viewBox="0 0 36 36"
            fill="none"
            role="img"
            xmlns="http://www.w3.org/2000/svg"
            width="80"
            height="80"
          >
            <title>Coretta Scott</title>
            <mask
              id="mask__beam"
              maskUnits="userSpaceOnUse"
              x="0"
              y="0"
              width="36"
              height="36"
            >
              <rect width="36" height="36" rx="72" fill="#FFFFFF"></rect>
            </mask>
            <g mask="url(#mask__beam)">
              <rect width="36" height="36" fill="#737777"></rect>
              <rect
                x="0"
                y="0"
                width="36"
                height="36"
                transform="translate(5 -1) rotate(55 18 18) scale(1.1)"
                fill="#0e0043"
                rx="6"
              ></rect>
              <g transform="translate(7 -6) rotate(-5 18 18)">
                <path
                  d="M15 20c2 1 4 1 6 0"
                  stroke="#FFFFFF"
                  fill="none"
                  stroke-linecap="round"
                ></path>
                <rect
                  x="14"
                  y="14"
                  width="1.5"
                  height="2"
                  rx="1"
                  stroke="none"
                  fill="#FFFFFF"
                ></rect>
                <rect
                  x="20"
                  y="14"
                  width="1.5"
                  height="2"
                  rx="1"
                  stroke="none"
                  fill="#FFFFFF"
                ></rect>
              </g>
            </g>
          </svg>
        </div>
        <div class="col" style="text-align: left">
          <div class="row">
            <div class="col-sm-9">
              <p style="font-size: 1em">
                <b>{{ comment.clientUsername }} </b> rated
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  style="color: orange"
                  width="16"
                  height="16"
                  fill="currentColor"
                  class="bi bi-star-fill"
                  viewBox="0 0 16 16"
                >
                  <path
                    d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"
                  />
                </svg>
                {{ comment.grade }}
              </p>
            </div>
            <div class="col">
              <button
                @click="visitProfile(comment.clientUsername)"
                type="button"
                style="background-color: #3d6b51; color: white"
                class="btn rounded-pill"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="12"
                  height="12"
                  fill="currentColor"
                  class="bi bi-box-arrow-up-left"
                  viewBox="0 0 16 16"
                >
                  <path
                    fill-rule="evenodd"
                    d="M7.364 3.5a.5.5 0 0 1 .5-.5H14.5A1.5 1.5 0 0 1 16 4.5v10a1.5 1.5 0 0 1-1.5 1.5h-10A1.5 1.5 0 0 1 3 14.5V7.864a.5.5 0 1 1 1 0V14.5a.5.5 0 0 0 .5.5h10a.5.5 0 0 0 .5-.5v-10a.5.5 0 0 0-.5-.5H7.864a.5.5 0 0 1-.5-.5z"
                  />
                  <path
                    fill-rule="evenodd"
                    d="M0 .5A.5.5 0 0 1 .5 0h5a.5.5 0 0 1 0 1H1.707l8.147 8.146a.5.5 0 0 1-.708.708L1 1.707V5.5a.5.5 0 0 1-1 0v-5z"
                  />
                </svg>
                Visit profile
              </button>
            </div>
          </div>
          <p></p>
          <p style="font-size: 1em">{{ comment.comment }}</p>
          <p style="color: dark-gray">{{ setDate(comment.date) }}</p>
        </div>
        <hr />
        <br />
      </div>
    </template>
  </div>
</template>



<script>
import axios from "axios";
import dayjs from "dayjs";

export default {
  data() {
    return {
      user: {
        username: "",
        firstname: "",
        lastname: "",
        phoneNum: "",
        address: "",
        rating: "",
        role: "",
      },
      fullName: "",
      comments: [],
      ranks: [],
      showComments: true,
      showDiscount: false,
      userRole: ""
    };
  },
  props: {
    emailProp: String,
    personsProfile: String,
  },
  components: {},
  mounted() {
    //this.getUser()
  },
  methods: {
    progressPercentageToNextLevel: function (points) {
      var nextLevelRequiredPoints = 0;
      this.ranks.forEach((rank) => {
        if (rank.points >= points && nextLevelRequiredPoints == 0) {
          nextLevelRequiredPoints = rank.points;
        }
      });
      if (nextLevelRequiredPoints == 0) return "MAXIMUM LEVEL ACHIEVED!";
      else
        return (
          this.twoDecimales((points / nextLevelRequiredPoints) * 100) + "%"
        );
    },
    twoDecimales: function (number) {
      return number.toFixed(2);
    },
    getDiscountPercentage: function (role) {
      var discountPercentage = 0;
      this.ranks.forEach((rank) => {
        if (rank.rank == role) {
          discountPercentage = rank.discountPercentage;
        }
      });
      return discountPercentage;
    },
    getRoleWithoutPrefix: function (role) {
      if (role.substring(5) == "FISHING_INSTRUCTOR")
        return "FISHING INSTRUCTOR";
      else if (role.substring(5) == "CABINOWNER") return "CABIN OWNER";
      else if (role.substring(5) == "BOATOWNER") return "BOAT OWNER";
      else return role.substring(5);
    },
    getUser: function (username) {
      this.email = username;
      if (username != "") {
        axios
          .get(process.env.VUE_APP_BACKEND_URL+"userc/userByUsername/" + username + "/")
          .then((response) => {
            this.user = response.data;
            this.fullName = this.user.firstname + " " + this.user.lastname;
            console.log("rolee od useraaa  "+ this.user.role)
            this.userRole=this.user.role
            this.getComments(username);
          });
      }
      return this.user;
    },
    getComments: function (username) {
      if (username != "") {
        var role = this.user.role;
        console.log("roleeeee  " + this.user.role);
        if (role == "ROLE_CABINOWNER") {
          console.log("trazim kom od cabin ownera");
          axios
            .get(
              process.env.VUE_APP_BACKEND_URL+"evaluations/cabinOwner/" + username + "/"
            )
            .then((response) => {
              this.comments = response.data;
            });
        } else if (role == "ROLE_BOATOWNER") {
          console.log("trazim kom od boat ownera");
          axios
            .get(
              process.env.VUE_APP_BACKEND_URL+"evaluations/boatOwner/" + username + "/"
            )
            .then((response) => {
              this.comments = response.data;
            });
        } else if (role == "ROLE_FISHING_INSTRUCTOR") {
          console.log("trazim kom od instructora");
          axios
            .get(
              process.env.VUE_APP_BACKEND_URL+"evaluations/instructor/" + username + "/"
            )
            .then((response) => {
              this.comments = response.data;
            });
        } else if (role == "ROLE_CLIENT") {
          this.showComments = false;
          this.showDiscount = true;
            axios.get(process.env.VUE_APP_BACKEND_URL+"ranks/getAll").then((response) => {
              this.ranks = response.data;
            });
        }
      }
    },
    setDate: function (newDate) {
      var date = new Date();
      var splits = newDate.toString().split(",");
      date.setDate(splits[1], splits[2], splits[0]);
      var newData = new Date(
        parseInt(splits[0]),
        parseInt(splits[1]) - 1,
        parseInt(splits[2]),
        parseInt(splits[3]),
        parseInt(splits[4])
      );
      const dateee = dayjs(newData);
      return dateee.format("YYYY-MM-DD HH:mm:ss");
    },
    visitProfile: function (username) {
      console.log("ovo ispisujemmmm  " + username);
      this.$props.personsProfile = username;
      console.log("izmenio sammmmm   " + this.$props.personsProfile);
      if(this.userRole=="ROLE_CABINOWNER"){
      this.$router.push("/cabinOwner/viewProfile/" + this.$props.emailProp + "/" + username);

      }else if(this.userRole=="ROLE_BOATOWNER"){
      this.$router.push("/boatOwner/viewProfile/" + this.$props.emailProp + "/" + username);

      }else if(this.userRole=="ROLE_FISHING_INSTRUCTOR"){
      this.$router.push("/instructor/viewProfile/" + this.$props.emailProp + "/" + username);

      }
      this.getUser(username);
    },
  },

  computed: {
    MyUser: function () {
      if (this.user.username == "" && this.$props.personsProfile != undefined)
        this.getUser(this.$props.personsProfile);
      return this.user;
    },
  },
};
</script>


<style scoped>
body {
  background-color: #7070a4;
}

.padding {
  padding: 5% !important;
  padding-left: 10% !important;
}

.user-card-full {
  overflow: hidden;
}

.card {
  border-radius: 5px;
  -webkit-box-shadow: 0 1px 20px 0 rgba(69, 90, 100, 0.08);
  box-shadow: 0 1px 20px 0 rgba(69, 90, 100, 0.08);
  border: none;
  margin-bottom: 30px;
}

.m-r-0 {
  margin-right: 0px;
}

.m-l-0 {
  margin-left: 0px;
}

.user-card-full .user-profile {
  border-radius: 5px 0 0 5px;
}

.bg-c-lite-green {
  background: linear-gradient(to right, #133d70, #030b2e);
}

.user-profile {
  padding: 10px 0;
}

.card-block {
  padding: 1.25rem;
}

.m-b-25 {
  margin-bottom: 25px;
}

.img-radius {
  border-radius: 5px;
}

h6 {
  font-size: 14px;
}

.card .card-block p {
  line-height: 25px;
}

@media only screen and (min-width: 1400px) {
  p {
    font-size: 14px;
  }
}

.card-block {
  padding: 1.25rem;
}

.b-b-default {
  border-bottom: 1px solid #e0e0e0;
}

.m-b-20 {
  margin-bottom: 20px;
}

.p-b-5 {
  padding-bottom: 5px !important;
}

.card .card-block p {
  line-height: 25px;
}

.m-b-10 {
  margin-bottom: 10px;
}

.text-muted {
  color: #919aa3 !important;
}

.b-b-default {
  border-bottom: 1px solid #e0e0e0;
}

.f-w-600 {
  font-weight: 600;
}

.m-b-20 {
  margin-bottom: 20px;
}

.m-t-40 {
  margin-top: 20px;
}

.p-b-5 {
  padding-bottom: 5px !important;
}

.m-b-10 {
  margin-bottom: 10px;
}

.m-t-40 {
  margin-top: 20px;
}

.user-card-full .social-link li {
  display: inline-block;
}

.user-card-full .social-link li a {
  font-size: 20px;
  margin: 0 10px 0 0;
  -webkit-transition: all 0.3s ease-in-out;
  transition: all 0.3s ease-in-out;
}
</style>
