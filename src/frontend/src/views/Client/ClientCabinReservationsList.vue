<template>
  <!-- header -->

  <div class="header">
    <form>
      <h1 style="text-align: left; color: #0b477b; padding-left: 7.2%">
        <template v-if="!availableQuickReservations">
          <template v-if="upcomingReservations">
            Upcoming reservations
          </template>
          <template v-if="!upcomingReservations">
            Reservation history
          </template>
        </template>
        <template v-if="availableQuickReservations"
          >Available quick reservations</template
        >
      </h1>
    </form>

    <!-- sort -->
    <template
      v-if="
        !upcomingReservations &&
        !availableQuickReservations &&
        cabinReservationDtos.length != 0
      "
    >
      <hr />
      <form>
        <h1 style="text-align: left; color: #0b477b; padding-left: 7.2%">
          Sort cabin reservations
        </h1>
        <br />
        <div
          style="padding-left: 7.2%; padding-right: 7.2%; width: 100%"
          class="row"
        >
          <div class="col">
            <button
              type="button"
              @click="sort('date')"
              v-bind:class="[
                sortBy === 'date' ? sortDirection : '',
                'form-control rounded-pill fa fa-sort',
              ]"
            >
              Date
            </button>
          </div>

          <div class="col">
            <button
              type="button"
              @click="sort('price')"
              v-bind:class="[
                sortBy === 'price' ? sortDirection : '',
                'form-control rounded-pill fa fa-sort',
              ]"
            >
              Price
            </button>
          </div>

          <div class="col">
            <button
              type="button"
              @click="sort('duration')"
              v-bind:class="[
                sortBy === 'duration' ? sortDirection : '',
                'form-control rounded-pill fa fa-sort',
              ]"
            >
              Duration
            </button>
          </div>
        </div>
      </form>
    </template>
    <!--sort-->
  </div>

  <!--header-->

  <hr />

  <template v-if="!reservationsLoaded">
    <h3>Loading...</h3>
  </template>

  <template v-if="sortedReservations">
    <template v-if="sortedReservations.length == 0">
      <h3>
        No
        <template v-if="upcomingReservations">upcoming</template>
        <template v-if="availableQuickReservations">available quick</template>
        cabin reservations to show.
      </h3>
    </template>
  </template>
  <!-- Carousel wrapper -->
  <div
    v-if="reservationsLoaded == true"
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
              v-for="(cabinReservationDto, index) in sortedReservations"
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
                        {{ formatDate(setDate(cabinReservationDto.startDate)) }}
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
                  <div
                    class="row"
                    v-if="
                      cabinReservationDto.addedAdditionalServices.length != 0
                    "
                  >
                    <div
                      class="col"
                      style="padding-top: 2%; text-align: left; color: black"
                    >
                      <p>Added additional services:</p>
                    </div>
                    <div class="col-sm-9" style="padding: 1%; text-align: left">
                      <template
                        v-for="(
                          service, index
                        ) in cabinReservationDto.addedAdditionalServices"
                        :key="index"
                        class="group"
                        role="group"
                        aria-label="Basic outlined example"
                      >
                        <span
                          v-if="service.price == 0"
                          style="background-color: #59d47a"
                          class="badge rounded-pill text-light"
                          >{{ service.name }} - Free</span
                        >
                        <span
                          v-else
                          style="background-color: #703636"
                          class="badge rounded-pill text-light"
                          >{{ service.name }} - {{ service.price }}$ per
                          day</span
                        >
                      </template>
                    </div>
                  </div>
                  <template
                    v-if="
                      !availableQuickReservations &&
                      !cabinReservationDto.discount
                    "
                  >
                    <h6 style="text-align: left; color: green">
                      Total price:
                      {{
                        cabinReservationDto.paymentInformationDto.totalPrice
                      }}
                      $
                    </h6>
                  </template>
                  <template
                    v-if="availableQuickReservations"
                  >
                    <h6 style="color: red; text-align: left">
                      Previous price:
                      <a style="text-decoration: line-through"
                        >{{
                          twoDecimales(
                            cabinReservationDto.paymentInformationDto.totalPrice
                          )
                        }}
                        $
                      </a>
                    </h6>
                    <h6 style="text-align: left; color: green">
                      Discount: <i>-{{ cabinReservationDto.discount }}%</i>
                    </h6>
                    <h6 style="text-align: left; color: green">
                      Discounted price:
                      <b
                        >{{
                          twoDecimales(getDiscountedPrice(cabinReservationDto))
                        }}
                        $</b
                      >
                    </h6>
                  </template>
                  <template
                    v-if="
                      !availableQuickReservations && cabinReservationDto.discount
                    "
                  >
                    <h6 style="color: red; text-align: left">
                      Previous price:
                      <a style="text-decoration: line-through"
                        >{{
                          twoDecimales(
                            getPriceBeforeDiscount(cabinReservationDto)
                          )
                        }}
                        $
                      </a>
                    </h6>
                    <h6 style="text-align: left; color: green">
                      Discount: <i>-{{ cabinReservationDto.discount }}%</i>
                    </h6>
                    <h6 style="text-align: left; color: green">
                      Discounted price:
                      <b
                        >{{
                          twoDecimales(cabinReservationDto.paymentInformationDto.totalPrice)
                        }}
                        $</b
                      >
                    </h6>
                  </template>
                  <div class="row">
                    <template v-if="!availableQuickReservations">
                      <div class="col" style="text-align: right">
                        <button
                          @click="writeComplaint(cabinReservationDto)"
                          type="button"
                          class="btn btn-outline-dark rounded-pill"
                        >
                          WRITE COMPLAINT
                        </button>
                      </div>
                      <div class="col" style="text-align: right">
                        <template v-if="upcomingReservations">
                          <button
                            @click="cancelReservationModal(cabinReservationDto)"
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
                        <template
                          v-if="
                            !upcomingReservations &&
                            !cabinReservationDto.evaluated
                          "
                        >
                          <button
                            @click="evaluateReservation(cabinReservationDto)"
                            type="button"
                            class="btn btn-outline-dark rounded-pill"
                          >
                            EVALUATE
                          </button>
                        </template>
                      </div>
                    </template>
                    <template v-if="availableQuickReservations">
                      <button
                        @click="quickReservationModal(cabinReservationDto)"
                        type="button"
                        class="btn btn-outline-dark rounded-pill"
                      >
                        BOOK
                      </button>
                    </template>
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
  <!-- Cancellation modal -->
  <vue-modality
    ref="cancellation"
    title="Reservation cancellation"
    hide-footer
    centered
    width="900px"
  >
    <br />
    <div class="row">
      <div class="col">
        <div class="row">
          <div
            class="col-sm-3"
            style="padding-top: 1%; text-align: left; color: gray"
          >
            <p>Cabin</p>
          </div>
          <div class="col-sm-9" style="padding: 1%; text-align: left">
            <p>
              <b>{{ cabinForCancellation.cabinDto.name }}</b>
            </p>
          </div>
        </div>
        <div class="row">
          <div
            class="col"
            style="padding-top: 2%; text-align: left; color: gray"
          >
            <p>Start</p>
          </div>
          <div class="col-sm-9" style="padding: 1%">
            <Datepicker v-model="startDate" disabled> </Datepicker>
          </div>
        </div>
        <br />
        <div class="row">
          <div
            class="col"
            style="padding-top: 2%; text-align: left; color: gray"
          >
            <p>End</p>
          </div>
          <div class="col-sm-9" style="padding: 1%">
            <Datepicker v-model="endDate" disabled></Datepicker>
          </div>
        </div>
        <div class="row">
          <div
            class="col-sm-3"
            style="padding-top: 1%; text-align: left; color: gray"
          >
            <p>Cancelling condition</p>
          </div>
          <div class="col-sm-9" style="padding: 1%; text-align: left">
            <p>
              <b>{{ cabinForCancellation.cabinDto.cancellingConditions }}</b>
            </p>
          </div>
        </div>
        <div class="row">
          <div
            class="col-sm-3"
            style="padding-top: 1%; text-align: left; color: gray"
          >
            <p>Total price</p>
          </div>
          <div class="col-sm-9" style="padding: 1%; text-align: left">
            <p>
              <b
                >{{
                  twoDecimales(
                    cabinForCancellation.paymentInformationDto.totalPrice
                  )
                }}
                $</b
              >
            </p>
          </div>
        </div>
      </div>
    </div>
    <hr />
    <button type="button" @click="cancelReservation()" class="btn btn-success">
      Cancel reservation
    </button>
  </vue-modality>
  <!-- Cancellation modal -->
  <!-- Quick reservation modal -->
  <vue-modality
    ref="quickReservation"
    title="Quick reservation"
    hide-footer
    centered
    width="900px"
  >
    <br />
    <div class="row">
      <div class="col">
        <div class="row">
          <div
            class="col-sm-3"
            style="padding-top: 1%; text-align: left; color: gray"
          >
            <p>Cabin</p>
          </div>
          <div class="col-sm-9" style="padding: 1%; text-align: left">
            <p>
              <b>{{ quickReservationCabin.cabinDto.name }}</b>
            </p>
          </div>
        </div>
        <div class="row">
          <div
            class="col"
            style="padding-top: 2%; text-align: left; color: gray"
          >
            <p>Start</p>
          </div>
          <div class="col-sm-9" style="padding: 1%">
            <Datepicker v-model="startDate" disabled> </Datepicker>
          </div>
        </div>
        <br />
        <div class="row">
          <div
            class="col"
            style="padding-top: 2%; text-align: left; color: gray"
          >
            <p>End</p>
          </div>
          <div class="col-sm-9" style="padding: 1%">
            <Datepicker v-model="endDate" disabled></Datepicker>
          </div>
        </div>
        <div class="row">
          <div
            class="col"
            style="padding-top: 2%; text-align: left; color: gray"
          >
            <p>Added additional services:</p>
          </div>
          <div class="col-sm-9" style="padding: 1%">
            <div
              v-for="(
                service, index
              ) in quickReservationCabin.addedAdditionalServices"
              :key="index"
              class="group"
              role="group"
              aria-label="Basic outlined example"
            >
              <span
                v-if="service.price == 0"
                style="background-color: #59d47a"
                class="badge rounded-pill text-light"
                >{{ service.name }} - Free</span
              >
              <span
                v-else
                style="background-color: #703636"
                class="badge rounded-pill text-light"
                >{{ service.name }} - {{ service.price }}$ per day</span
              >
            </div>
          </div>
        </div>
        <div class="row">
          <div
            class="col-sm-3"
            style="padding-top: 1%; text-align: left; color: gray"
          >
            <p>Cancelling condition</p>
          </div>
          <div class="col-sm-9" style="padding: 1%; text-align: left">
            <p>
              <b>{{ quickReservationCabin.cabinDto.cancelingCondition }}</b>
            </p>
          </div>
        </div>
        <div class="row">
          <div
            class="col-sm-3"
            style="padding-top: 1%; text-align: left; color: red"
          >
            <p>Previous price</p>
          </div>
          <div
            class="col-sm-9"
            style="
              padding: 1%;
              text-align: left;
              color: red;
              text-decoration: line-through;
            "
          >
            <p>
              <b
                >{{
                  twoDecimales(
                    quickReservationCabin.paymentInformationDto.totalPrice
                  )
                }}
                $</b
              >
            </p>
          </div>
          <div
            class="col-sm-3"
            style="padding-top: 1%; text-align: left; color: green"
          >
            <p>Discounted price</p>
          </div>
          <div
            class="col-sm-9"
            style="padding: 1%; text-align: left; color: green"
          >
            <p>
              <b
                >{{
                  twoDecimales(getDiscountedPrice(quickReservationCabin))
                }}
                $</b
              >
            </p>
          </div>
        </div>
      </div>
    </div>
    <hr />
    <button
      type="button"
      @click="quickReservationConfirm()"
      class="btn btn-success"
    >
      Confirm quick reservation
    </button>
  </vue-modality>
  <!-- Quick reservation modal -->
</template>

<script>
import axios from "axios";
import dayjs from "dayjs";
import VueModality from "vue-modality-v3";
import Datepicker from "vue3-date-time-picker";

export default {
  components: {
    VueModality,
    Datepicker,
  },
  props: {
    upcomingReservations: Boolean,
    availableQuickReservations: Boolean,
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
      reservationsLoaded: false,
      cabinName: "",
      cabinForCancellation: {},
      quickReservationCabin: {},
      startDate: null,
      endDate: null,
      sortBy: "",
      sortDirection: "asc",
    };
  },
  mounted() {
    this.email = this.$route.params.email;
    if (this.availableQuickReservations) {
      this.getAvailableQuickReservations();
    } else {
      this.getReservations();
    }
  },
  methods: {
    getPriceBeforeDiscount: function (quickReservationDto) {
      return (
        (quickReservationDto.paymentInformationDto.totalPrice *
          (100 + quickReservationDto.discount)) /
        100
      );
    },
    sort: function (s) {
      if (s === this.sortBy) {
        this.sortDirection = this.sortDirection === "asc" ? "desc" : "asc";
      }
      this.sortBy = s;
    },
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
    getReservations: function () {
      this.cabinReservationDtos = [];
      if (!this.upcomingReservations) {
        axios
          .post(
            process.env.VUE_APP_BACKEND_URL+"reservationCabin/getReservationsHistory",
            {
              username: this.email,
            },
            {}
          )
          .then((response) => {
            this.cabinReservationDtos = this.cabinReservationDtos.concat(
              response.data
            );
            this.reservationsLoaded = true;
          });
        axios
          .post(
            process.env.VUE_APP_BACKEND_URL+"quickReservationCabin/getReservationsHistory",
            {
              username: this.email,
            },
            {}
          )
          .then((response) => {
            this.cabinReservationDtos = this.cabinReservationDtos.concat(
              response.data
            );
            this.reservationsLoaded = true;
          });
      } else {
        this.user.username = this.email;
        axios
          .post(
            process.env.VUE_APP_BACKEND_URL+"reservationCabin/getUpcomingReservations",
            {
              username: this.email,
            },
            {}
          )
          .then((response) => {
            this.cabinReservationDtos = this.cabinReservationDtos.concat(
              response.data
            );
            this.reservationsLoaded = true;
          });
        axios
          .post(
            process.env.VUE_APP_BACKEND_URL+"quickReservationCabin/getUpcomingReservations",
            {
              username: this.email,
            },
            {}
          )
          .then((response) => {
            this.cabinReservationDtos = this.cabinReservationDtos.concat(
              response.data
            );
            this.reservationsLoaded = true;
          });
      }
    },
    getAvailableQuickReservations: function () {
      axios
        .get(
          process.env.VUE_APP_BACKEND_URL+"quickReservationCabin/getAvailableReservations",
          {},
          {}
        )
        .then((response) => {
          this.cabinReservationDtos = response.data;
          this.reservationsLoaded = true;
        });
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
      if (this.reservationsLoaded == true) {
        return this.sortedReservations[index].cabinDto.images[0].url;
      }
      return "logoF1.png";
    },
    getFullAddress: function (index) {
      if (this.reservationsLoaded == true)
        return (
          this.sortedReservations[index].cabinDto.addressDto.streetAndNum +
          ", " +
          this.sortedReservations[index].cabinDto.addressDto.city +
          ", " +
          this.sortedReservations[index].cabinDto.addressDto.country
        );
      return "logoF1.png";
    },
    getDiscountedPrice: function (quickReservationDto) {
      return (
        (quickReservationDto.paymentInformationDto.totalPrice *
          (100 - quickReservationDto.discount)) /
        100
      );
    },
    twoDecimales: function (number) {
      return number.toFixed(2);
    },
    seeProfile: function (cabinName) {
      this.$router.push("/cabinProfile/" + this.email + "/" + cabinName);
    },
    evaluateReservation: function (reservationDto) {
       if(reservationDto.discount)
      this.$router.push(
        "/quickEvaluation/" + this.email + "/" + "cabin/" + reservationDto.id
      );
      else
      this.$router.push(
        "/evaluation/" + this.email + "/" + "cabin/" + reservationDto.id
      );
    },
    writeComplaint: function (reservationDto) {
      this.$router.push(
        "/complaint/" + this.email + "/" + "cabin/" + reservationDto.id
      );
    },
    quickReservationModal: function (reservationDto) {
      this.quickReservationCabin = reservationDto;
      this.startDate = this.setDate(reservationDto.startDate);
      this.endDate = this.setDate(reservationDto.endDate);
      this.$refs.quickReservation.open();
    },
    quickReservationConfirm: function () {
      this.loader = this.$loading.show({
        // Optional parameters
        container: this.fullPage ? null : this.$refs.formContainer,
        canCancel: true,
        onCancel: this.onCancel,
      });
      this.quickReservationCabin.clientUsername = this.email;
      axios
        .post(
          process.env.VUE_APP_BACKEND_URL+"quickReservationCabin/makeQuickReservation",
          this.quickReservationCabin,
          {}
        )
        .then(() => {
          this.$refs.quickReservation.hide();
          this.loader.hide();
          this.$swal.fire({
            position: "top-end",
            icon: "success",
            title: "Quick cabin reservation successful!",
            showConfirmButton: false,
            timer: 2500,
          });
          this.getAvailableQuickReservations();
        })
        .catch((error) => {
          this.$refs.quickReservation.hide();
          this.loader.hide();
          this.$swal.fire({
            icon: "error",
            title: "Something went wrong!",
            text: error.response.data,
          });
          this.getAvailableQuickReservations();
        });
    },
    cancelReservationModal: function (reservationDto) {
      this.cabinForCancellation = reservationDto;
      this.startDate = this.setDate(reservationDto.startDate);
      this.endDate = this.setDate(reservationDto.endDate);
      this.$refs.cancellation.open();
    },
    cancelReservation: function () {
      this.loader = this.$loading.show({
        // Optional parameters
        container: this.fullPage ? null : this.$refs.formContainer,
        canCancel: true,
        onCancel: this.onCancel,
      });

      var path = "reservationCabin";
      if(this.cabinForCancellation.discount)
        path = "quickReservationCabin";

      axios
        .post(
          process.env.VUE_APP_BACKEND_URL+"" + path + "/cancelReservation",
          this.cabinForCancellation,
          {}
        )
        .then(() => {
          this.$refs.cancellation.hide();
          this.loader.hide();
          this.$swal.fire({
            position: "top-end",
            icon: "success",
            title: "Cabin reservation cancellation successful!",
            showConfirmButton: false,
            timer: 2500,
          });
          this.getReservations();
        })
        .catch((error) => {
          this.$refs.cancellation.hide();
          this.loader.hide();
          this.$swal.fire({
            icon: "error",
            title: "Something went wrong!",
            text: error.response.data,
          });
          this.getReservations();
        });
    },
  },
  computed: {
    sortedReservations: function () {
      if (this.upcomingReservations || this.availableQuickReservations)
        return this.cabinReservationDtos;
      {
        return this.sortedArray;
      }
    },
    sortedArray: function () {
      let sortedEntities = this.cabinReservationDtos;
      sortedEntities = sortedEntities.sort((a, b) => {
        let fa, fb;
        if (this.sortBy === "date") {
          fa = new Date(this.setDate(a.startDate));
          fb = new Date(this.setDate(b.startDate));
        } else if (this.sortBy === "price") {
          fa = a.paymentInformationDto.totalPrice;
          fb = b.paymentInformationDto.totalPrice;
        } else {
          fa =
            new Date(this.setDate(a.endDate)) -
            new Date(this.setDate(a.startDate));
          fb =
            new Date(this.setDate(b.endDate)) -
            new Date(this.setDate(b.startDate));
        }
        let modifier = 1;
        if (this.sortDirection === "desc") modifier = -1;
        if (fa < fb) {
          return -1 * modifier;
        }
        if (fa > fb) {
          return 1 * modifier;
        }
        return 0;
      });
      return sortedEntities;
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

.asc:after {
  content: "\25B2";
}

.desc:after {
  content: "\25BC";
}
</style>