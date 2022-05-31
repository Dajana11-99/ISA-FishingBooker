<template>
  <ClientNavbar />
  <div>
    <h1>SUBSCRIPTIONS</h1>
    <hr />
    <template v-if="!loaded">
      <h3>Loading...</h3>
    </template>
    <template v-if="loaded">
      <div style="padding: 2%">
        <div style="padding-left: 2%; padding-right: 2%">
          <ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item" role="presentation">
              <button
                @click="changeView(1)"
                class="nav-link active"
                id="home-tab"
                data-bs-toggle="tab"
                data-bs-target="#home"
                type="button"
                role="tab"
                aria-controls="home"
                aria-selected="true"
              >
                <b>Adventures</b>
              </button>
            </li>
            <li class="nav-item" role="presentation">
              <button
                @click="changeView(2)"
                class="nav-link"
                id="contact-tab"
                data-bs-toggle="tab"
                data-bs-target="#contact"
                type="button"
                role="tab"
                aria-controls="contact"
                aria-selected="false"
              >
                <b>Boats</b>
              </button>
            </li>
            <li class="nav-item" role="presentation">
              <button
                @click="changeView(3)"
                class="nav-link"
                id="cabins-tab"
                data-bs-toggle="tab"
                data-bs-target="#cabins"
                type="button"
                role="tab"
                aria-controls="cabins"
                aria-selected="false"
              >
                <b>Cabins</b>
              </button>
            </li>
          </ul>
          <div class="tab-content" id="myTabContent">
            <div
              class="tab-pane fade show active"
              id="home"
              role="tabpanel"
              aria-labelledby="home-tab"
            >
              <template v-if="adventureSubs.length == 0">
                <h3>No adventure subscriptions to show.</h3>
              </template>
              <template v-if="adventureSubs.length != 0">
                <table class="table">
                  <thead>
                    <tr>
                      <th scope="col">#</th>
                      <th scope="col">Adventure name</th>
                      <th scope="col">Fishing instructor</th>
                      <th scope="col">Profile page</th>
                      <th scope="col">&nbsp;</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr
                      v-for="(adventureSub, index) in adventureSubs"
                      :key="index"
                    >
                      <th scope="row">{{ index + 1 }}</th>
                      <td>{{ adventureSub.adventureDto.name }}</td>
                      <td>
                        {{
                          adventureSub.adventureDto.fishingInstructorUsername
                        }}
                      </td>
                      <td>
                        <button
                          @click="
                            adventureProfile(adventureSub.adventureDto.id)
                          "
                          type="button"
                          class="btn btn-info"
                        >
                          Profile
                        </button>
                      </td>
                      <td>
                        <button
                          @click="unsubscribeAdventure(adventureSub)"
                          type="button"
                          class="btn btn-primary"
                        >
                          Unsubscribe
                        </button>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </template>
            </div>

            <div
              class="tab-pane fade"
              id="contact"
              role="tabpanel"
              aria-labelledby="conact-tab"
            >
              <template v-if="boatSubs.length == 0">
                <h3>No boat subscriptions to show.</h3>
              </template>
              <template v-if="boatSubs.length != 0">
                <table class="table">
                  <thead>
                    <tr>
                      <th scope="col">#</th>
                      <th scope="col">Boat name</th>
                      <th scope="col">Owner</th>
                      <th scope="col">Profile page</th>
                      <th scope="col">&nbsp;</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(boatSub, index) in boatSubs" :key="index">
                      <th scope="row">{{ index + 1 }}</th>
                      <td>{{ boatSub.boatDto.name }}</td>
                      <td>{{ boatSub.boatDto.ownersUsername }}</td>
                      <td>
                        <button
                          @click="boatProfile(boatSub.boatDto.id)"
                          type="button"
                          class="btn btn-info"
                        >
                          Profile
                        </button>
                      </td>
                      <td>
                        <button
                          @click="unsubscribeBoat(boatSub)"
                          type="button"
                          class="btn btn-primary"
                        >
                          Unsubscribe
                        </button>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </template>
            </div>

            <div
              class="tab-pane fade"
              id="cabins"
              role="tabpanel"
              aria-labelledby="cabins-tab"
            >
              <template v-if="cabinSubs.length == 0">
                <h3>No cabin subscriptions to show.</h3>
              </template>
              <template v-if="cabinSubs.length != 0">
                <table class="table">
                  <thead>
                    <tr>
                      <th scope="col">#</th>
                      <th scope="col">Cabin name</th>
                      <th scope="col">Owner</th>
                      <th scope="col">Profile page</th>
                      <th scope="col">&nbsp;</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(cabinSub, index) in cabinSubs" :key="index">
                      <th scope="row">{{ index + 1 }}</th>
                      <td>{{ cabinSub.cabinDto.name }}</td>
                      <td>{{ cabinSub.cabinDto.ownerUsername }}</td>
                      <td>
                        <button
                          @click="cabinProfile(cabinSub.cabinDto.name)"
                          type="button"
                          class="btn btn-info"
                        >
                          Profile
                        </button>
                      </td>
                      <td>
                        <button
                          @click="unsubscribeCabin(cabinSub)"
                          type="button"
                          class="btn btn-primary"
                        >
                          Unsubscribe
                        </button>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </template>
            </div>
          </div>
        </div>
      </div>
    </template>
  </div>
  <vue-modality
    ref="cancellation"
    title="Unsubscribe?"
    hide-footer
    centered
    width="1000px"
  >
    <br />
    <div class="row">
      <div
        class="sm-3"
        style="padding-top: 1%; text-align: center; color: gray"
      >
        <h5>
          Are you sure you want to cancel subscription for
          {{ entityForCanceling.name }} {{ typeOfEntityForCancelling }}?
        </h5>
      </div>
    </div>
    <hr />
    <button type="button" @click="cancelSubscription()" class="btn btn-success">
      Cancel subscription
    </button>
  </vue-modality>
</template>

<script>
import axios from "axios";
import ClientNavbar from "./ClientNavbar.vue";
import VueModality from "vue-modality-v3";

export default {
  components: {
    ClientNavbar,
    VueModality,
  },
  data() {
    return {
      email: "",
      adventureSubs: [],
      boatSubs: [],
      cabinSubs: [],
      entityForCanceling: {},
      typeOfEntityForCancelling: "",
      activePage: 1,
      loaded: false,
    };
  },
  mounted() {
    this.email = this.$route.params.email;
    this.getSubscriptions();
  },
  methods: {
    changeView: function (num) {
      this.activePage = num;
    },
    getSubscriptions: function () {
      this.getAdventureSubscriptions();
      this.getBoatSubscriptions();
      this.getCabinSubscriptions();
    },
    getAdventureSubscriptions: function () {
      axios
        .get(
          process.env.VUE_APP_BACKEND_URL+"adventureSubscription/getByClientUsername/" +
            this.email +
            "/"
        )
        .then((response) => {
          this.adventureSubs = response.data;
          this.loaded = true;
        });
    },
    getBoatSubscriptions: function () {
      axios
        .get(
          process.env.VUE_APP_BACKEND_URL+"boatSubscription/getByClientUsername/" +
            this.email +
            "/"
        )
        .then((response) => {
          this.boatSubs = response.data;
        });
    },
    getCabinSubscriptions: function () {
      axios
        .get(
          process.env.VUE_APP_BACKEND_URL+"cabinSubscription/getByClientUsername/" +
            this.email +
            "/"
        )
        .then((response) => {
          this.cabinSubs = response.data;
        });
    },
    adventureProfile: function (id) {
      this.$router.push("/adventure/" + this.email + "/" + id);
    },
    boatProfile: function (id) {
      this.$router.push("/boat/" + this.email + "/" + id);
    },
    cabinProfile: function (name) {
      this.$router.push("/cabin/" + this.email + "/" + name);
    },
    unsubscribeAdventure: function (adventureSub) {
      this.entityForCanceling = adventureSub.adventureDto;
      this.typeOfEntityForCancelling = "adventure";
      this.$refs.cancellation.open();
    },
    unsubscribeBoat: function (boatSub) {
      this.entityForCanceling = boatSub.boatDto;
      this.typeOfEntityForCancelling = "boat";
      this.$refs.cancellation.open();
    },
    unsubscribeCabin: function (cabinSub) {
      this.entityForCanceling = cabinSub.cabinDto;
      this.typeOfEntityForCancelling = "cabin";
      this.$refs.cancellation.open();
    },
    cancelSubscription: function () {
      var dtoForPostRequest = {};
      if (this.typeOfEntityForCancelling == "adventure") {
        dtoForPostRequest = {
          adventureDto: this.entityForCanceling,
          clientUsername: this.email,
        };
      } else if (this.typeOfEntityForCancelling == "boat") {
        dtoForPostRequest = {
          boatDto: this.entityForCanceling,
          clientUsername: this.email,
        };
      } else {
        dtoForPostRequest = {
          cabinDto: this.entityForCanceling,
          clientUsername: this.email,
        };
      }
      axios
        .post(
          process.env.VUE_APP_BACKEND_URL+"" +
            this.typeOfEntityForCancelling +
            "Subscription/removeSubscription",
          dtoForPostRequest,
          {}
        )
        .then((response) => {
          this.getSubscriptions();
          this.$refs.cancellation.hide();
          this.$swal.fire({
            position: "top-end",
            icon: "success",
            title: response.data,
            showConfirmButton: false,
            timer: 2000,
          });
        })
        .catch((error) => {
          this.getSubscriptions();
          this.$refs.cancellation.hide();
          this.loader.hide();
          this.$swal.fire({
            icon: "error",
            title: "Something went wrong!",
            text: error.response.data,
          });
        });
    },
  },
  computed: {},
};
</script>
