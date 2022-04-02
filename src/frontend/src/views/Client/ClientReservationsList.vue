<template>
  <template v-if="!bookCabinOpen">
    <!-- header -->

    <div class="header">
      <form>
        <h1 style="text-align: left; color: #0b477b; padding-left: 7.2%">
          <template v-if="upcomingCabinReservations">
            Upcoming reservations
          </template>
          <template v-if="!upcomingCabinReservations">
            Reservation history
          </template>
        </h1>
      </form>
    </div>

    <!--header-->

    <hr />

    <template v-if="!cabinReservationsLoaded">
      <h3>Loading...</h3>
    </template>

    <template v-if="sortedCabinReservations">
      <template v-if="sortedCabinReservations.length == 0">
        <h3>
          No
          <template v-if="upcomingCabinReservations">upcoming</template> cabin
          reservations to show.
        </h3>
      </template>
    </template>
    <!-- Carousel wrapper -->
    <div
      v-if="cabinReservationsLoaded == true"
      id="carouselMultiItemExample"
      class="carousel slide carousel-dark text-center"
      data-mdb-ride="carousel"
    >
      <!-- Inner -->
      <div class="carousel-inner py-4">
        <!-- Single item -->
        <div class="carousel-item active">
          <div class="container">
            <div class="row">
              <div
                v-for="(cabinReservationDto, index) in sortedCabinReservations"
                :key="index"
                class="col-lg-4"
              >
                <div style="width: 100%; height: 95%" class="card">
                  <img
                    style="width: 100%; height: 100%"
                    :src="require('@/assets/' + getImageUrl(index))"
                  />

                  <div class="card-body">
                    <div class="row">
                      <div class="col-10">
                        <h2 style="text-align: left" class="card-title">
                          {{ cabinReservationDto.cabinDto.name.toUpperCase() }}
                        </h2>
                      </div>
                      <div class="col-10">
                        <h4 style="text-align: left" class="card-title">
                          {{
                            formatDate(setDate(cabinReservationDto.startDate))
                          }}
                          -
                          {{ formatDate(setDate(cabinReservationDto.endDate)) }}
                        </h4>
                      </div>
                      <div style="vertical-align: bottom" class="col">
                        <span class="badge bg-warning text-light"
                          ><svg
                            xmlns="http://www.w3.org/2000/svg"
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
                          {{ cabinReservationDto.cabinDto.rating }}</span
                        >
                      </div>
                    </div>
                    <hr />
                    <h6 style="text-align: left; color: gray">
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        width="16"
                        height="16"
                        fill="currentColor"
                        class="bi bi-geo-alt"
                        viewBox="0 0 16 16"
                      >
                        <path
                          d="M12.166 8.94c-.524 1.062-1.234 2.12-1.96 3.07A31.493 31.493 0 0 1 8 14.58a31.481 31.481 0 0 1-2.206-2.57c-.726-.95-1.436-2.008-1.96-3.07C3.304 7.867 3 6.862 3 6a5 5 0 0 1 10 0c0 .862-.305 1.867-.834 2.94zM8 16s6-5.686 6-10A6 6 0 0 0 2 6c0 4.314 6 10 6 10z"
                        />
                        <path
                          d="M8 8a2 2 0 1 1 0-4 2 2 0 0 1 0 4zm0 1a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"
                        />
                      </svg>
                      {{ getFullAddress(index) }}
                    </h6>

                    <h6 style="text-align: left">
                      {{ cabinReservationDto.cabinDto.description }}
                    </h6>
                    <h6 style="text-align: left; color: green">
                      {{ cabinReservationDto.cabinDto.price }} $ per night
                    </h6>
                    <div class="row">
                      <div class="col" style="text-align: right">
                        <template v-if="upcomingCabinReservations">
                          <button
                            @click="cancelReservation(cabinReservationDto)"
                            type="button"
                            class="btn btn-outline-dark rounded-pill"
                            :disabled="
                              !possibleCancellation(
                                cabinReservationDto.startDate
                              )
                            "
                          >
                            CANCEL
                          </button>
                        </template>
                        <template v-if="(!upcomingCabinReservations) && !cabinReservationDto.evaluated">
                          <button
                            @click="evaluateReservation(cabinReservationDto)"
                            type="button"
                            class="btn btn-outline-dark rounded-pill"
                          >
                            EVALUATE
                          </button>
                        </template>
                      </div>
                    </div>
                  </div>
                </div>
                <hr style="color: white" />
                <hr style="color: white" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Inner -->
  </template>
</template>

<script>
import axios from "axios";
import dayjs from "dayjs";

export default {
  components: {},
  props: {
    upcomingCabinReservations: Boolean,
  },
  data() {
    return {
      email: "",
      cabinReservationDtos: [
        {
          id: null,
          name: "",
          description: "",
          numOfRooms: 1,
          bedsPerRoom: 1,
          rules: "",
          price: 1.0,
          addressDto: {
            longitude: 0.0,
            latitude: 0.0,
            country: "",
            city: "",
            streetAndNum: "",
          },
          additionalServices: [
            {
              id: null,
              name: "",
              price: 0.0,
            },
          ],
          rating: 0.0,
          images: [
            {
              id: null,
              url: "",
              cabin: "",
            },
          ],
          ownerUsername: "",
        },
      ],
      user: {
        username: "",
      },
      cabinReservationsLoaded: false,
      bookCabinOpen: false,
      cabinName: "",
    };
  },
  mounted() {
    this.email = this.$route.params.email;
    this.getCabinReservations();
  },
  methods: {
    getNumberOfDays: function (start, end) {
      const date1 = new Date(start);
      const date2 = new Date(end);

      // One day in milliseconds
      const oneDay = 1000 * 60 * 60 * 24;

      // Calculating the time difference between two dates
      const diffInTime = date2.getTime() - date1.getTime();

      // Calculating the no. of days between two dates
      const diffInDays = Math.round(diffInTime / oneDay);

      return diffInDays;
    },
    possibleCancellation: function (date) {
      const currentDate = new Date();
      if (this.getNumberOfDays(currentDate, this.setDate(date)) < 3)
        return false;
      return true;
    },
    getCabinReservations: function () {
      if (!this.upcomingCabinReservations) {
        axios
          .post(
            "http://localhost:8081/reservationCabin/getReservationsHistory",
            {
              username: this.email,
            },
            {}
          )
          .then((response) => {
            this.cabinReservationDtos = response.data;
            this.cabinReservationsLoaded = true;
          });
      } else {
        this.user.username = this.email;
        axios
          .post(
            "http://localhost:8081/reservationCabin/getUpcomingReservations",
            {
              username: this.email,
            },
            {}
          )
          .then((response) => {
            this.cabinReservationDtos = response.data;
            this.cabinReservationsLoaded = true;
          });
      }
    },
    formatDate(formatDate) {
      const date = dayjs(formatDate);
      return date.format("DD.MM.YYYY. HH:mm");
    },
    setDate: function (newDate) {
      var date = new Date();
      var splits = newDate.toString().split(",");
      date.setDate(splits[1], splits[2], splits[0]);
      return new Date(
        parseInt(splits[0]),
        parseInt(splits[1]) - 1,
        parseInt(splits[2]),
        parseInt(splits[3]),
        parseInt(splits[4])
      );
    },
    getImageUrl: function (index) {
      if (this.cabinReservationsLoaded == true) {
        return this.sortedCabinReservations[index].cabinDto.images[0].url;
      }
      return "logoF1.png";
    },
    getFullAddress: function (index) {
      if (this.cabinReservationsLoaded == true)
        return (
          this.sortedCabinReservations[index].cabinDto.addressDto.streetAndNum +
          ", " +
          this.sortedCabinReservations[index].cabinDto.addressDto.city +
          ", " +
          this.sortedCabinReservations[index].cabinDto.addressDto.country
        );
      return "logoF1.png";
    },
    seeProfile: function (cabinName) {
      this.$router.push("/cabinProfile/" + this.email + "/" + cabinName);
    },
    evaluateReservation: function (cabinReservationDto) {
      this.$router.push("/evaluation/" + this.email + "/" + "cabin/" + cabinReservationDto.id);
    },
    cancelReservation: function (cabinReservationDto) {
      this.loader = this.$loading.show({
        // Optional parameters
        container: this.fullPage ? null : this.$refs.formContainer,
        canCancel: true,
        onCancel: this.onCancel,
      });
      axios
        .post(
          "http://localhost:8081/reservationCabin/cancelReservation",
          cabinReservationDto,
          {}
        )
        .then((response) => {
          this.availableCabins = response.data;
          this.display = "CABINS";
          this.loader.hide();
          this.$swal.fire({
            position: "top-end",
            icon: "success",
            title: "Cabin reservation cancellation successful!",
            showConfirmButton: false,
            timer: 2500,
          });
          this.getCabinReservations();
        })
        .catch(() => {
          this.loader.hide();
          this.$swal.fire({
            icon: "error",
            title: "Something went wrong!",
            text: "Unsuccessful cancellation! Less then 3 days left to start date.",
          });
           this.getCabinReservations();
        });
    },
  },
  computed: {
    sortedCabinReservations: function () {
      return this.cabinReservationDtos;
    },
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