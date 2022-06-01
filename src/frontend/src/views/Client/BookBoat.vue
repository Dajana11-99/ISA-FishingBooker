<template>
  <div>
    <div style="margin-top: 1%; width: 100%; height: 100" class="row">
      <div style="padding-left: 3%" class="col-sm-6">
        <div class="row" style="padding-top: 2%">
          <h1 style="text-align: left">{{ boatDto.name.toUpperCase() }}</h1>
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
            {{ getFullAddress() }}
          </h6>
        </div>

        <div
          style="height: 60%; width: 100%"
          id="carouselExampleControls"
          class="carousel slide"
          data-bs-ride="carousel"
        >
          <div class="carousel-indicators">
            <button
              class="active"
              @click="clickedImage(index)"
              v-for="(image, index) in boatDto.images"
              :key="index"
              type="button"
              data-bs-target="#carouselExampleIndicators"
            ></button>
          </div>
          <div class="carousel-inner">
            <div class="carousel-item active">
              <img
                :src="require('@/assets/' + currentImageUrl)"
                class="d-block w-100"
                alt="..."
              />
            </div>
          </div>
          <button
            @click="previousImage()"
            class="carousel-control-prev"
            type="button"
            data-bs-target="#carouselExampleControls"
            data-bs-slide="prev"
          >
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
          </button>
          <button
            @click="nextImage()"
            class="carousel-control-next"
            type="button"
            data-bs-target="#carouselExampleControls"
            data-bs-slide="next"
          >
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
          </button>
        </div>

        <div
          style="
            text-align: left;
            padding-left: 1%;
            padding-top: 1%;
            border: 2px solid #bfbfbf;
          "
        >
          <p>{{ boatDto.description }}</p>
          <p>
            Engine code: {{ boatDto.engineCode }} Engine Power:
            {{ boatDto.enginePower }} Max speed: {{ boatDto.maxSpeed }}
          </p>
          <p>Fishing equipment:{{ boatDto.fishingEquipment }}</p>
          <p>Navigation equipment:{{ boatDto.navigationEquipment }}</p>
          <p>Rules: {{ boatDto.rules }}</p>
        </div>
      </div>

      <div
        style="
          text-align: left;
          border: 2px solid #bfbfbf;
          padding-left: 2%;
          margin-top: 6.5%;
        "
        class="col-sm-4"
      >
        <div class="row">
          <div style="text-align: left; font-size: 200%" class="col">
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
              {{ boatDto.rating }}</span
            >
          </div>
          <div class="col">
            <h4 style="text-align: right; padding-top: 5%">
              Booking information
            </h4>
          </div>
        </div>

        <hr />
        <div class="row">
          <div class="col">
            <p>Owner:</p>
          </div>
          <div class="col">
            <p>
              <template v-if="email"
                ><a href="#" @click="viewProfile()"
                  ><b>{{ boatDto.ownersUsername }}</b></a
                ></template
              >
              <template v-if="!email"
                ><b>{{ boatDto.ownersUsername }}</b></template
              >
            </p>
          </div>
        </div>

        <div class="row">
          <div class="col">
            <p>Price per hour:</p>
          </div>
          <div class="col" style="color: green">
            <p>
              <b>{{ boatDto.price }}$</b>
            </p>
          </div>
        </div>

        <div class="row">
          <div class="col">
            <p>Max people:</p>
          </div>
          <div class="col">
            <p>
              <b>{{ boatDto.maxPeople }}</b>
            </p>
          </div>
        </div>

        <div class="row">
          <div class="col">
            <p>Canceling conditions:</p>
          </div>
          <div class="col">
            <p>
              <b>{{ boatDto.cancelingCondition }}</b>
            </p>
          </div>
        </div>

        <template v-if="bookingProcess">
          <hr />

          <p>Additional services:</p>

          <table class="table" v-show="boatDto.additionalServices != 0">
            <thead>
              <tr>
                <th scope="col">#</th>
                <th scope="col">Service</th>
                <th scope="col">Price per hour ($)</th>
                <th scope="col">&nbsp;</th>
              </tr>
            </thead>

            <tbody>
              <tr
                v-for="(service, index) in availableAdditionalServices"
                :key="index"
              >
                <th scope="row">{{ index + 1 }}</th>
                <td>{{ service.name }}</td>
                <td>{{ service.price }}</td>
                <td>
                  <input
                    @click="addService(service)"
                    type="button"
                    value="add"
                    class="btn btn-outline-success"
                  />
                </td>
              </tr>
            </tbody>
          </table>

          <p v-show="availableAdditionalServices.length == 0">
            No additional services for adding.
          </p>

          <p>Added additional services:</p>
          <template v-if="addedAdditionalServices">
            <table class="table" v-show="addedAdditionalServices.length != 0">
              <thead>
                <tr>
                  <th scope="col">#</th>
                  <th scope="col">Service</th>
                  <th scope="col">Price per hour ($)</th>
                  <th scope="col">&nbsp;</th>
                </tr>
              </thead>

              <tbody>
                <tr
                  v-for="(service, index) in addedAdditionalServices"
                  :key="index"
                >
                  <th scope="row">{{ index + 1 }}</th>
                  <td>{{ service.name }}</td>
                  <td>{{ service.price }}</td>
                  <td>
                    <input
                      @click="removeService(service)"
                      type="button"
                      value="remove"
                      class="btn btn-outline-danger"
                    />
                  </td>
                </tr>
              </tbody>
            </table>
          </template>
          <p
            v-show="
              addedAdditionalServices && addedAdditionalServices.length == 0
            "
          >
            No additional services added.
          </p>
          <hr />
          <div class="row">
            <div class="col">
              <p>Start date:</p>
            </div>
            <div class="col">
              <Datepicker
                @closed="calculatePrice()"
                v-model="start"
                required
              ></Datepicker>
            </div>
          </div>

          <div class="row">
            <div class="col">
              <p>End date:</p>
            </div>
            <div class="col">
              <Datepicker
                @closed="calculatePrice()"
                v-model="end"
                required
              ></Datepicker>
            </div>
          </div>
          <hr />
          <div class="row">
            <div class="col">
              <p>Total price:</p>
            </div>
            <div class="col">
              <p>{{ totalPrice }} $</p>
            </div>
          </div>

          <button
            style="background-color: #1d7ac9; width: 100%"
            type="button"
            class="btn text-light rounded-pill"
            @click="bookBoat()"
          >
            BOOK
          </button>
        </template>

        <template v-if="!bookingProcess">
          <p>Additional services:</p>
          <div
            v-for="(service, index) in boatDto.additionalServices"
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
              >{{ service.name }} - {{ service.price }}$ per hour</span
            >
          </div>
        </template>
      </div>

      <template v-if="bookingProcess">
        <div class="col" style="margin-top: 3%">
          <button
            @click="back()"
            style="background-color: #1d7ac9; width: 100%"
            type="button"
            class="btn text-light rounded-pill"
          >
            BACK
          </button>
        </div>
      </template>
      <template v-if="!bookingProcess && role == 'CLIENT' && this.email">
        <template v-if="!boatDto.subscription && role == 'CLIENT'">
          <div class="col" style="margin-top: 3%">
            <button
              @click="subscribe()"
              style="background-color: #1d7ac9; width: 100%"
              type="button"
              class="btn text-light rounded-pill"
            >
              SUBSCRIBE
            </button>
          </div>
        </template>
        <template v-if="boatDto.subscription && role == 'CLIENT'">
          <div class="col" style="margin-top: 3%">
            <button
              @click="unsubscribe()"
              style="background-color: #1d7ac9; width: 100%"
              type="button"
              class="btn text-light rounded-pill"
            >
              UNSUBSCRIBE
            </button>
          </div>
        </template>
      </template>
      <template v-if="role == 'ADMIN' && this.email">
        <div class="col" style="margin-top: 3%">
          <button
            class="btn btn-lg btn-danger rounded-pill"
            data-bs-toggle="modal"
            data-bs-target="#staticBackdrop"
          >
            DELETE BOAT
          </button>
        </div>
      </template>

      <div
        style="
          text-align: left;
          border: 2px solid #bfbfbf;
          padding-left: 2%;
          margin-top: 6%;
          width: 80.5%;
          margin-left: 3%;
        "
        class="row-cols-sm-1"
      >
        <OpenLayersMap
          :coordinates="[
            boatDto.addressDto.latitude,
            boatDto.addressDto.longitude,
          ]"
        />
      </div>
    </div>
    <!-- Modal -->
    <div
      class="modal fade"
      id="staticBackdrop"
      data-bs-backdrop="static"
      data-bs-keyboard="false"
      tabindex="-1"
      aria-labelledby="staticBackdropLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="staticBackdropLabel"></h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <p>Are you sure you want to delete boat?</p>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              Close
            </button>
            <button
              @click="deleteBoat()"
              type="button"
              data-bs-dismiss="modal"
              class="btn btn-danger"
            >
              Delete
            </button>
          </div>
        </div>
      </div>
    </div>
    <!-- Modal -->
    <!-- Comments -->
    <template v-if="role == 'CLIENT'">
      <br />

      <div style="text-align: left; padding-left: 3%; padding-right: 11%">
        <h1>Comments about cabin</h1>
        <hr />
        <h3 v-if="comments.length == 0">No comments to show.</h3>
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
                  @click="viewProfile(comment.clientUsername)"
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
            <p style="color: dark-gray">{{ setAndFormatDate(comment.date) }}</p>
          </div>
          <hr />
          <br />
        </div>
      </div>
    </template>
    <!-- Comments -->
  </div>
</template>

<script>
import axios from "axios";
import OpenLayersMap from "../../components/OpenLayersMap";
import Datepicker from "vue3-date-time-picker";
import dayjs from "dayjs";

export default {
  props: {
    boatId: String,
    back: Function,
    startDate: Date,
    endDate: Date,
    bookingProcess: Boolean,
  },
  components: {
    OpenLayersMap,
    Datepicker,
  },
  data() {
    return {
      email: "",
      role: localStorage.role,
      boatDto: {
        id: null,
        ownersUsername: "",
        name: "",
        type: "",
        length: "",
        engineCode: "",
        enginePower: "",
        maxSpeed: "",
        navigationEquipment: "",
        addressDto: {
          longitude: 0.0,
          latitude: 0.0,
          country: "",
          city: "",
          streetAndNum: "",
        },
        description: "",
        images: [
          {
            id: null,
            url: "",
            cabin: "",
          },
        ],
        maxPeople: 1,
        rules: "",
        fishingEquipment: "",
        price: 1.0,
        additionalServices: [
          {
            id: null,
            name: "",
            price: 0.0,
          },
        ],
        cancelingCondition: "",
        rating: "",
      },
      addedAdditionalServices: [
        {
          id: null,
          name: "",
          price: 0.0,
        },
      ],
      availableAdditionalServices: [
        {
          id: null,
          name: "",
          price: 0.0,
        },
      ],
      prices: "",
      names: "",
      idx: 0,
      tableHidden: true,
      selectedFile: null,
      imagesSelected: false,
      imagesSelectedEvent: null,
      additionalServicesAdded: false,
      loader: null,
      user: {
        username: "",
      },
      boatLoaded: false,
      currentImageUrl: "logoF1.png",
      imageIndex: 0,
      maxImageIndex: 0,
      start: null,
      end: null,
      totalPrice: 0,
      boatIdParam: "",
      comments: [],
    };
  },
  mounted() {
    this.role = localStorage.role;
    this.email = this.$route.params.email;
    this.boatIdParam = this.$route.params.boatId;
    this.start = this.startDate;
    this.end = this.endDate;
    this.getBoat();
    if (this.bookingProcess) {
      this.calculatePrice();
      this.isOwnerAvailable();}
  },
  methods: {
    needsCaptainServices: function(){
      var needsCaptainServices = false;
      if(this.addedAdditionalServices)
      this.addedAdditionalServices.forEach((service)=>{
        if(service.name === "Captain service") needsCaptainServices = true;
      });
      return needsCaptainServices;
    },
    removeCaptainService: function(){
      var availableAdditionalServices = [];
      this.availableAdditionalServices.forEach((service)=>{
        if(service.name !== "Captain service") availableAdditionalServices.push(service);
      });
      this.availableAdditionalServices = availableAdditionalServices;
    },
    isOwnerAvailable: function (){
      axios
        .post(process.env.VUE_APP_BACKEND_URL+"reservationBoat/isOwnerAvailable",{
            boatId: this.boatId,
            startDate: this.formatDate(this.start),
            endDate: this.formatDate(this.end),
        }, {})
        .then((response) => {
          if(response.data == false) this.removeCaptainService();
        });
    },
    setAndFormatDate: function (newDate) {
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
    getComments: function () {
      axios
        .get(process.env.VUE_APP_BACKEND_URL+"evaluations/boat/" + this.boatDto.id)
        .then((response) => {
          this.comments = response.data;
        });
    },
    viewProfile: function () {
      this.$router.push(
        "/client/viewProfile/" + this.email + "/" + this.boatDto.ownersUsername
      );
    },
    calculatePrice: function () {
      let numOfHours = this.getNumberOfHours(this.start, this.end);
      var pricePerHour = this.boatDto.price;
      for (let i = 0; i < this.addedAdditionalServices.length; i++) {
        pricePerHour += this.addedAdditionalServices[i].price;
      }
      this.totalPrice = numOfHours * pricePerHour;
    },
    getNumberOfHours: function (start, end) {
      const date1 = new Date(start);
      const date2 = new Date(end);

      // One hour in milliseconds
      const oneHour = 1000 * 60 * 60;

      // Calculating the time difference between two dates
      const diffInTime = date2.getTime() - date1.getTime();

      // Calculating the no. of hours between two dates
      const diffInHours = Math.ceil(diffInTime / oneHour);

      return diffInHours;
    },
    addService: function (service) {
      if (this.addedAdditionalServices == null)
        this.addedAdditionalServices = [];
      this.addedAdditionalServices.push(service);
      let newList = [];
      for (let i = 0; i < this.availableAdditionalServices.length; i++) {
        if (this.availableAdditionalServices[i].id === service.id) {
          continue;
        }
        newList.push(this.availableAdditionalServices[i]);
      }
      this.availableAdditionalServices = newList;
      this.calculatePrice();
    },
    removeService: function (service) {
      this.availableAdditionalServices.push(service);
      let newList = [];
      for (let i = 0; i < this.addedAdditionalServices.length; i++) {
        if (this.addedAdditionalServices[i].id === service.id) {
          continue;
        }
        newList.push(this.addedAdditionalServices[i]);
      }
      this.addedAdditionalServices = newList;
      this.calculatePrice();
    },
    getBoat: function () {
      if (this.email) this.boatDto.ownersUsername = this.email;
      else this.boatDto.ownersUsername = "";

      if (this.bookingProcess) {
        this.boatDto.id = this.boatId;
      } else {
        this.boatDto.id = this.boatIdParam;
      }
      axios
        .post(process.env.VUE_APP_BACKEND_URL+"boats/findById", this.boatDto, {})
        .then((response) => {
          this.addedAdditionalServices = [];
          this.boatDto = response.data;
          this.boatLoaded = true;
          this.currentImageUrl = this.boatDto.images[0].url;
          this.maxImageIndex = this.boatDto.images.length - 1;
          this.availableAdditionalServices = this.boatDto.additionalServices;
          if (this.bookingProcess) this.calculatePrice();
          if (this.role === "CLIENT") this.getComments();
        });
    },
    previousImage: function () {
      if (this.imageIndex > 0) {
        this.imageIndex--;
        this.currentImageUrl = this.boatDto.images[this.imageIndex].url;
      }
    },
    nextImage: function () {
      if (this.imageIndex < this.maxImageIndex) {
        this.imageIndex++;
        this.currentImageUrl = this.boatDto.images[this.imageIndex].url;
      }
    },
    clickedImage: function (index) {
      this.imageIndex = index;
      this.currentImageUrl = this.boatDto.images[this.imageIndex].url;
    },
    getFullAddress: function () {
      return (
        this.boatDto.addressDto.streetAndNum +
        ", " +
        this.boatDto.addressDto.city +
        ", " +
        this.boatDto.addressDto.country
      );
    },
    dataIsValid() {
      const date1 = new Date(this.start);
      const date2 = new Date(this.end);
      const currentDate = new Date();
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
      return true;
    },
    formatDate: function (formatDate) {
      const date = dayjs(formatDate);
      return date.format("YYYY-MM-DDTHH:mm:ss");
    },
    bookBoat: function () {
      if (!this.dataIsValid()) {
        return;
      }
      if (this.addedAdditionalServices == null)
        this.addedAdditionalServices = null;
      else if (this.addedAdditionalServices.length == 0)
        this.addedAdditionalServices = null;

      this.loader = this.$loading.show({
        // Optional parameters
        container: this.fullPage ? null : this.$refs.formContainer,
        canCancel: true,
        onCancel: this.onCancel,
      });
      var needsCaptainServices = this.needsCaptainServices();
      axios
        .post(
          process.env.VUE_APP_BACKEND_URL+"reservationBoat/makeReservation",
          {
            id: null,
            startDate: this.formatDate(this.start),
            endDate: this.formatDate(this.end),
            paymentInformationDto: {
              totalPrice: this.totalPrice,
              companysPart: 0,
              ownersPart: 0,
            },
            ownerWroteAReport: false,
            ownersUsername: this.boatDto.ownersUsername,
            boatDto: this.boatDto,
            addedAdditionalServices: this.addedAdditionalServices,
            clientUsername: this.email,
            needsCaptainServices: needsCaptainServices,
          },
          {}
        )
        .then(() => {
          this.loader.hide();
          this.$swal.fire({
            position: "top-end",
            icon: "success",
            title: "Boat reservation successful!",
            showConfirmButton: false,
            timer: 2000,
          });
          this.$router.push("/reservations/" + this.email);
        })
        .catch((error) => {
          this.loader.hide();
          this.$swal.fire({
            icon: "error",
            title: "Something went wrong!",
            text: error.response.data,
          });
        });
    },
    subscribe: function () {
      axios
        .post(
          process.env.VUE_APP_BACKEND_URL+"boatSubscription/addSubscription",
          {
            boatDto: this.boatDto,
            clientUsername: this.email,
          },
          {}
        )
        .then((response) => {
          this.$swal.fire({
            position: "top-end",
            icon: "success",
            title: response.data,
            showConfirmButton: false,
            timer: 2000,
          });
          this.getBoat();
        });
    },
    unsubscribe: function () {
      axios
        .post(
          process.env.VUE_APP_BACKEND_URL+"boatSubscription/removeSubscription",
          {
            boatDto: this.boatDto,
            clientUsername: this.email,
          },
          {}
        )
        .then((response) => {
          this.$swal.fire({
            position: "top-end",
            icon: "success",
            title: response.data,
            showConfirmButton: false,
            timer: 2000,
          });
          this.getBoat();
        });
    },
    deleteBoat: function () {
      axios
        .post(
          process.env.VUE_APP_BACKEND_URL+"boats/canBeEditedOrDeleted/" + this.boatDto.id
        )
        .then((response) => {
          if (response.data == true) {
            axios
              .post(process.env.VUE_APP_BACKEND_URL+"boats/delete", this.boatDto)
              .then((response) => {
                this.$swal.fire({
                  position: "top-end",
                  icon: "success",
                  title: "Successfully deleted!",
                  showConfirmButton: false,
                  timer: 2500,
                });
                this.$router.push("/profileAdmin/" + this.email);
                return response;
              });
          } else {
            this.$swal.fire({
              position: "top-end",
              icon: "error",
              title:
                "This boat has future reservations and can not be deleted!",
              showConfirmButton: false,
              timer: 2500,
            });
          }
        });
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
