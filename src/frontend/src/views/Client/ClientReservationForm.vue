<template>
  <ClientNavbar />
  <template v-if="!hideForm">
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-md-12">
          <div class="card">
            <header id="header" class="card-header">
              <h4 class="card-title mt-2">Reservation</h4>
            </header>
            <article class="card-body">
              <form class="was-validated">
                <div class="row">
                  <div class="col">
                    <div class="row">
                      <div class="form-row" style="text-align: left">
                        <div class="col form-group">
                          <label id="label">Choose entity</label>
                          <select
                            v-model="selectedEntity"
                            class="form-select form-select-sm"
                            aria-label=".form-select-sm example"
                            required
                            @change="valueChanged"
                          >
                            <option>CABINS</option>
                            <option>BOATS</option>
                            <option>ADVENTURES</option>
                          </select>
                        </div>
                        <div class="row form-group">
                          <label id="label">Start</label>
                          <Datepicker v-model="start" required></Datepicker>
                        </div>
                        <div class="row form-group">
                          <label id="label">End</label>
                          <Datepicker v-model="end" required></Datepicker>
                        </div>
                      </div>
                    </div>
                  </div>

                  <div class="col">
                    <div class="row">
                      <template v-if="selectedEntity === 'CABINS'">
                        <div class="col form-group">
                          <label id="label">Price per night ($)</label>
                          <input
                            v-model="price"
                            type="text"
                            pattern="[0-9]+\.?[0-9]*"
                            class="form-control"
                          />
                        </div>

                        <div class="col form-group">
                          <label id="label">Number of rooms </label>
                          <input
                            min="1"
                            v-model="numOfRooms"
                            type="number"
                            class="form-control"
                          />
                        </div>

                        <div class="col form-group">
                          <label id="label">Number of beds </label>
                          <input
                            min="1"
                            v-model="numOfBeds"
                            type="number"
                            class="form-control"
                          />
                        </div>
                      </template>
                      <template v-if="selectedEntity !== 'CABINS'">
                        <div class="col form-group">
                          <label id="label">Price per hour($)</label>
                          <input
                            v-model="price"
                            type="text"
                            pattern="[0-9]+\.?[0-9]*"
                            class="form-control"
                          />
                        </div>

                        <div class="col form-group">
                          <label id="label">Max people</label>
                          <input
                            v-model="maxPeople"
                            type="number"
                            class="form-control"
                          />
                        </div>
                      </template>
                    </div>

                    <div id="ConfirmButton" class="form-group">
                      <br />
                      <button
                        @click="submitReservationParams"
                        class="btn btn-lg btn-success"
                      >
                        Confirm
                      </button>
                    </div>
                  </div>
                </div>
              </form>
            </article>
          </div>
        </div>
      </div>
    </div>
  </template>
  <template v-if="display === 'CABINS'">
    <ClientCabins
      :reservationProcess="true"
      :availableCabins="availableCabins"
      :showReservationForm="showReservationForm"
      :startDate="start"
      :endDate="end"
    />
  </template>
  <template v-if="display === 'BOATS'">
    <ClientBoats
      :reservationProcess="true"
      :availableBoats="availableBoats"
      :showReservationForm="showReservationForm"
      :startDate="start"
      :endDate="end"
    />
  </template>
  <template v-if="display === 'ADVENTURES'">
    <ClientAdventures
      :reservationProcess="true"
      :availableAdventures="availableAdventures"
      :showReservationForm="showReservationForm"
      :startDate="start"
      :endDate="end"
    />
  </template>
</template>

<script>
import axios from "axios";
import ClientNavbar from "./ClientNavbar";
import Datepicker from "vue3-date-time-picker";
import ClientCabins from "./ClientCabins.vue";
import ClientBoats from "./ClientBoats.vue";
import ClientAdventures from "./ClientAdventures.vue";
import dayjs from "dayjs";

export default {
  components: {
    ClientNavbar,
    Datepicker,
    ClientCabins,
    ClientBoats,
    ClientAdventures,
  },
  data() {
    return {
      email: "",
      start: null,
      end: null,
      selectedEntity: "CABINS",
      display: "",
      price: "",
      maxPeople: "",
      numOfRooms: "",
      numOfBeds: "",
      availableCabins: [],
      availableBoats: [],
      availableAdventures: [],
      hideForm: false,
    };
  },
  mounted() {
    this.email = this.$route.params.email;
  },
  methods: {
    valueChanged: function () {
      this.display = "";
    },
    showReservationForm(state) {
      this.hideForm = !state;
    },
    dataIsValid() {
      const date1 = new Date(this.start);
      const date2 = new Date(this.end);
      const currentDate = new Date();
      if (this.start == null || this.end == null) {
        this.$swal.fire({
          position: "top-end",
          icon: "error",
          title: "Date field can't be empty!",
          showConfirmButton: false,
          timer: 1500,
        });
        return false;
      }
      if (date1.getTime() - date2.getTime() > 0) {
        this.$swal.fire({
          position: "top-end",
          icon: "error",
          title: "Start date must be before end date!",
          showConfirmButton: false,
          timer: 1500,
        });
        return false;
      }
      if (date1.getTime() - currentDate.getTime() < 0) {
        this.$swal.fire({
          position: "top-end",
          icon: "error",
          title: "Start date can't be before today!",
          showConfirmButton: false,
          timer: 1500,
        });
        return false;
      }
      if (this.price !== "" && !parseFloat(this.price)) {
        this.$swal.fire({
          position: "top-end",
          icon: "error",
          title: "Price must be double precision number!",
          showConfirmButton: false,
          timer: 1500,
        });
        return false;
      }
      if (this.selectedEntity !== "CABINS") {
        if (this.numOfBeds !== "" && !this.isInt(this.numOfBeds)) {
          this.$swal.fire({
            position: "top-end",
            icon: "error",
            title: "Number of beds must be natural number!",
            showConfirmButton: false,
            timer: 1500,
          });
          return false;
        }
        if (this.numOfRooms !== "" && !this.isInt(this.numOfRooms)) {
          this.$swal.fire({
            position: "top-end",
            icon: "error",
            title: "Number of rooms must be natural number!",
            showConfirmButton: false,
            timer: 1500,
          });
          return false;
        }
      } else {
        if (this.maxPeople !== "" && !this.isInt(this.maxPeople)) {
          this.$swal.fire({
            position: "top-end",
            icon: "error",
            title: "Max people must be natural number!",
            showConfirmButton: false,
            timer: 1500,
          });
          return false;
        }
      }
      return true;
    },
    isInt: function (n) {
      return !isNaN(Number(n)) && n % 1 === 0;
    },
    isFloat: function (n) {
      return !isNaN(Number(n)) && n % 1 !== 0;
    },
    formatDate: function (formatDate) {
      const date = dayjs(formatDate);
      return date.format("YYYY-MM-DDTHH:mm:ss");
    },
    submitReservationParams: function (event) {
      event.preventDefault();
      this.display = "";
      if (!this.dataIsValid()) {
        return;
      }
      if (this.selectedEntity === "CABINS") {
        axios
          .post(
            process.env.VUE_APP_BACKEND_URL+"reservationCabin/getAvailableCabins",
            {
              startDate: this.formatDate(this.start),
              endDate: this.formatDate(this.end),
              price: this.price === "" ? 0.0 : this.price,
              numberOfRooms: this.numOfRooms === "" ? 1 : this.numOfRooms,
              numberOfBeds: this.numOfBeds === "" ? 1 : this.numOfBeds,
              username: this.email,
            },
            {}
          )
          .then((response) => {
            this.availableCabins = response.data;
            this.display = "CABINS";
          });
      } else if (this.selectedEntity === "BOATS") {
        axios
          .post(
            process.env.VUE_APP_BACKEND_URL+"reservationBoat/getAvailableBoats",
            {
              startDate: this.formatDate(this.start),
              endDate: this.formatDate(this.end),
              price: this.price === "" ? 0.0 : this.price,
              maxPeople: this.maxPeople === "" ? 1 : this.maxPeople,
              username: this.email,
            },
            {}
          )
          .then((response) => {
            this.availableBoats = response.data;
            this.display = "BOATS";
          });
      } else {
        axios
          .post(
            process.env.VUE_APP_BACKEND_URL+"reservationAdventure/getAvailableAdventures",
            {
              startDate: this.formatDate(this.start),
              endDate: this.formatDate(this.end),
              price: this.price === "" ? 0.0 : this.price,
              maxPeople: this.maxPeople === "" ? 1 : this.maxPeople,
              username: this.email,
            },
            {}
          )
          .then((response) => {
            this.availableAdventures = response.data;
            this.display = "ADVENTURES";
          });
      }
    },
  },
  computed: {},
};
</script> 

<style>
</style>
