<template>
<template v-if="!bookBoatOpen">
  <div v-if="role=='CLIENT' && !unidentifiedUser" class="header">
    <!-- search-->
      <form class="was-validated" v-if="!reservationProcess">
        <h1 style="text-align: left; color: #0b477b; padding-left: 7.2%">
          Search boats
        </h1>
        <br />
        <div style="padding-left: 7.2%; width: 100%" class="row">
          <div class="col">
            <Datepicker v-model="start" placeholder="Start date"></Datepicker>
          </div>

          <div class="col">
            <Datepicker v-model="end" placeholder="End date"></Datepicker>
          </div>

          <div class="col">
            <input
              class="form-control rounded-pill"
              type="text"
              style="
                height: 90%;
                width: 110%;
                padding-left: 5%;
                background-color: #fff;
              "
              placeholder="ADDRESS"
              readonly
              :value="searchAddress"
              @click="openLocationForm()"
            />
          </div>

          <div class="col">
            <input
              class="form-control rounded-pill"
              style="
                height: 90%;
                width: 110%;
                padding-left: 5%;
                background-color: #fff;
              "
              type="text"
              placeholder="CITY"
              :value="searchCity"
              readonly
              @click="openLocationForm()"
            />
          </div>

          <div class="col">
            <input
              class="form-control rounded-pill"
              type="text"
              style="
                height: 90%;
                width: 110%;
                padding-left: 5%;
                background-color: #fff;
              "
              placeholder="COUNTRY"
              :value="searchCountry"
              readonly
              @click="openLocationForm()"
            />
          </div>

          <div class="col">
            <input
              class="form-control rounded-pill"
              type="text"
              style="height: 90%; width: 110%; padding-left: 5%"
              placeholder="PRICE PER HOUR"
              :value="searchPrice"
              pattern="[0-9]+\.?[0-9]*"
              @input="searchPrice = $event.target.value"
            />
          </div>

          <div class="col">
            <input
              class="form-control rounded-pill"
              type="number"
              min="1"
              style="height: 90%; width: 110%; padding-left: 5%"
              placeholder="MAX PEOPLE"
              :value="searchMaxPeople"
              @input="searchMaxPeople = $event.target.value"
            />
          </div>

          <div class="col">
            <input
              class="form-control rounded-pill"
              type="text"
              style="height: 90%; width: 110%; padding-left: 5%"
              placeholder="RATING"
              :value="searchRating"
              pattern="[0-5]\.?[0-9]*"
              @input="searchRating = $event.target.value"
            />
          </div>

          <div class="col">
            <button
              @click="resetSearch()"
              style="
                height: 90%;
                width: 110%;
                background-color: #0b477b;
                color: white;
              "
              type="button"
              class="btn rounded-pill"
              :disabled="isResetDisabled()"
            >
              RESET SEARCH
            </button>
          </div>

          <div class="col">
            <button
              @click="submitSearchParams"
              style="
                height: 90%;
                width: 110%;
                background-color: #0b477b;
                color: white;
              "
              type="button"
              class="btn rounded-pill"
              :disabled="isSearchDisabled()"
            >
              SEARCH
            </button>
          </div>
        </div>
      </form>
      <!--search-->
      <!--location-->
      <template v-if="locationOpen">
        <form>
        <hr>
          <h1 style="text-align: left; color: #0b477b; padding-left: 7.2%">
            Choose location
          </h1>
          <br />
          <div
            style="padding-left: 7.2%; padding-right: 7.2%; width: 100%"
            class="row"
          >
            <div class="col" style="height: 300px">
              <PickLocationMap :coordinates="[21.0059, 44.0165]" />
            </div>
          </div>
          <br />
          <div
            style="padding-left: 7.2%; padding-right: 7.2%; width: 100%"
            class="row"
          >
            <div class="col">
              <input
                class="form-control rounded-pill"
                type="text"
                style="height: 90%; width: 90%; padding-left: 5%"
                id="search-field"
                placeholder="ADRESS"
                :value="locationAddress"
                @input="locationAddress = $event.target.value"
              />
            </div>

            <div class="col">
              <input
                class="form-control rounded-pill"
                type="text"
                style="height: 90%; width: 90%; padding-left: 5%"
                id="search-field"
                placeholder="CITY"
                :value="locationCity"
                @input="locationCity = $event.target.value"
              />
            </div>

            <div class="col">
              <input
                class="form-control rounded-pill"
                type="text"
                style="height: 90%; width: 90%; padding-left: 5%"
                id="search-field"
                placeholder="COUNTRY"
                :value="locationCountry"
                @input="locationCountry = $event.target.value"
              />
            </div>

            <div class="col">
              <button
                @click="closeLocation()"
                style="
                  height: 90%;
                  width: 90%;
                  background-color: #0b477b;
                  color: white;
                "
                type="button"
                class="btn rounded-pill"
              >
                CANCEL
              </button>
            </div>

            <div class="col">
              <button
                @click="confirmLocation()"
                style="
                  height: 90%;
                  width: 90%;
                  background-color: #0b477b;
                  color: white;
                "
                type="button"
                class="btn rounded-pill"
              >
                CONFIRM
              </button>
            </div>
          </div>
        </form>
      </template>
      <!--location-->
    <!-- sort-->
    <form v-if="boatDtos.length!=0">
      <hr>
      <h1 style="text-align: left; color: #0b477b; padding-left: 7.2%">
        Sort boats
      </h1>
      <br />
              <div
          style="padding-left: 7.2%; padding-right: 7.2%; width: 100%"
          class="row"
        >
          <div class="col">
            <button
              type="button"
              @click="sort('name')"
              v-bind:class="[
                sortBy === 'name' ? sortDirection : '',
                'form-control rounded-pill fa fa-sort',
              ]"
            >
              Name
            </button>
          </div>

          <div class="col">
            <button
              type="button"
              @click="sort('location')"
              v-bind:class="[
                sortBy === 'location' ? sortDirection : '',
                'form-control rounded-pill fa fa-sort',
              ]"
            >
              Location
            </button>
          </div>

          <div class="col">
            <button
              type="button"
              @click="sort('instructorRating')"
              v-bind:class="[
                sortBy === 'instructorRating' ? sortDirection : '',
                'form-control rounded-pill fa fa-sort',
              ]"
            >
              Rating
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
              Price per hour
            </button>
          </div>
        </div>
    </form>
    <!--sort-->
    <!-- filter -->
      <form v-if="boatDtos.length!=0">
        <hr>
        <h1 style="text-align: left; color: #0b477b; padding-left: 7.2%">
          Filter boats
        </h1>
        <br />
        <div
          style="padding-left: 7.2%; padding-right: 7.2%; width: 100%"
          class="row"
        >
          <div class="col">
            <select
              v-model="filterCancellingCondition"
              class="form-select form-select-sm"
              aria-label=".form-select-sm example"
              style="height: 100%"
            >
              <option value="" disabled selected>
                Select cancelling condition
              </option>
              <option value="FREE">FREE</option>
              <option value="NOT FREE">
                NOT FREE
              </option>
            </select>
          </div>

          <div class="col">
            <Multiselect
              style="color: gray; height: 90%"
              v-model="filterRules"
              mode="tags"
              :close-on-select="false"
              :searchable="true"
              :create-option="false"
              :options="rules"
              placeholder="Select rules"
            />
          </div>

          <div class="col">
            <Multiselect
              style="color: gray; height: 90%"
              v-model="filterFishingEquipment"
              mode="tags"
              :close-on-select="false"
              :searchable="true"
              :create-option="false"
              :options="fishingEquipment"
              placeholder="Select fishing equipment"
            />
          </div>

          <div class="col">
            <Multiselect
              style="color: gray; height: 90%"
              v-model="filterNavigationEquipment"
              mode="tags"
              :close-on-select="false"
              :searchable="true"
              :create-option="false"
              :options="navigationEquipment"
              placeholder="Select navigation equipment"
            />
          </div>

          <div class="col">
            <Multiselect
              style="color: gray; height: 90%"
              v-model="filterAdditionalServices"
              mode="tags"
              :close-on-select="false"
              :searchable="true"
              :create-option="false"
              :options="filterAdditionalServicesOptions"
              placeholder="Select additional services"
            />
          </div>

          <div class="col">
            <button
              @click="resetFilter()"
              style="
                height: 90%;
                width: 110%;
                background-color: #0b477b;
                color: white;
              "
              type="button"
              class="btn rounded-pill"
              :disabled="isResetFilterDisabled()"
            >
              RESET FILTER
            </button>
          </div>
        </div>
      </form>
      <!--filter-->
  </div>

  <div v-if="role != 'CLIENT' || unidentifiedUser" class="header">
      <!-- search-->
      <form v-if="!reservationProcess">
        <h1 style="text-align: left; color: #0b477b; padding-left: 7.2%">
          Search boats
        </h1>
        <br />
        <div style="padding-left: 7.2%; width: 100%" class="row">
          <div class="col">
            <input
              class="form-control rounded-pill"
              type="text"
              style="
                height: 90%;
                width: 110%;
                padding-left: 5%;
                background-color: #fff;
              "
              placeholder="NAME"
              :value="searchName"
              @input="searchName = $event.target.value.toUpperCase()"
            /> 
          </div>

          <div class="col">
          <input
            class="form-control rounded-pill"
            type="text"
            style="height: 90%; width:110%; padding-left: 5%;"
            id="search-field"
            placeholder="TYPE"
            :value="searchType" 
            @input="searchType = $event.target.value.toUpperCase()"
          />
          </div>

          <div class="col">
            <input
              class="form-control rounded-pill"
              type="text"
              style="
                height: 90%;
                width: 110%;
                padding-left: 5%;
                background-color: #fff;
              "
              placeholder="ADDRESS"
              :value="searchAddress"
              @input="searchAddress = $event.target.value.toUpperCase()"
            />
          </div>

          <div class="col">
            <input
              class="form-control rounded-pill"
              style="
                height: 90%;
                width: 110%;
                padding-left: 5%;
                background-color: #fff;
              "
              type="text"
              placeholder="CITY"
              :value="searchCity"
              @input="searchCity = $event.target.value.toUpperCase()"
            />
          </div>

          <div class="col">
            <input
              class="form-control rounded-pill"
              type="text"
              style="
                height: 90%;
                width: 110%;
                padding-left: 5%;
                background-color: #fff;
              "
              placeholder="COUNTRY"
              :value="searchCountry"
              @input="searchCountry = $event.target.value.toUpperCase()"
            />
          </div>

          <div class="col">
            <input
              class="form-control rounded-pill"
              type="text"
              style="height: 90%; width: 110%; padding-left: 5%"
              placeholder="PRICE PER DAY"
              :value="searchPrice"
              @input="searchPrice = $event.target.value"
            />
          </div>

          <div class="col">
          <input
            class="form-control rounded-pill"
            type="number"
            min="1"
            style="height: 90%; width:110%; padding-left: 5%;"
            id="search-field"
            placeholder="MAX PEOPLE"
            :value="searchMaxPeople" 
            @input="searchMaxPeople = $event.target.value"
          />
          </div>

          <div class="col">
          <select
             style="height: 90%; width: 110%; color: #5f7280;"
            v-model="searchRating"
            class="form-select rounded-pill"
            aria-label="Default select example"
            placeholder="Rating"
          >
            <option  disabled value="">RATING</option>
            <option v-bind:value="5">FIVE STARS</option>
            <option v-bind:value="4">FOUR STARS</option>
            <option v-bind:value="3">THREE STARS</option>
            <option v-bind:value="2">TWO STARS</option>
            <option v-bind:value="1">ONE STAR</option>
          </select>
          </div>

          <div class="col">
            <button
              @click="resetSearch()"
              style="
                height: 90%;
                width: 110%;
                background-color: #0b477b;
                color: white;
              "
              type="button"
              class="btn rounded-pill"
              :disabled="isResetDisabled()"
            >
              RESET SEARCH
            </button>
          </div>
        </div>
      </form>
      <!--search-->
    </div>

  <hr v-if="role=='CLIENT' || unidentifiedUser" />

  <template v-if="!boatsLoaded">
    <h3>Loading...</h3>
  </template>

  <template v-if="sortedBoats.length == 0">
    <h3>No boats to show.</h3>
  </template>

  <!-- Carousel wrapper -->
  <div
    v-if="boatsLoaded == true"
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
              v-for="(boatDto, index) in sortedBoats"
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
                        {{ boatDto.name.toUpperCase() }}
                      </h2>
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
                        {{ boatDto.rating }}</span
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

                  <h6 style="text-align: left">{{ boatDto.description }}</h6>
                  <h6 style="text-align: left">
                    {{ boatDto.type.toUpperCase() }}
                  </h6>
                  <div class="row">
                    <div class="col">
                      <h6 style="text-align: left">
                        Max people: {{ boatDto.maxPeople }}
                      </h6>
                    </div>
                    <div class="col">
                      <div class="row">
                        <div class="col">
                          <h6 style="text-align: left">Price:</h6>
                        </div>
                        <div class="col">
                          <h6 style="text-align: left; color: green">
                            {{ boatDto.price }}$
                          </h6>
                        </div>
                      </div>
                    </div>
                  </div>

                  <div class="row">
                    <div class="col" style="text-align: right">
                      <template v-if="!reservationProcess && !searchResultDisplay">
                        <button
                          @click="
                            seeProfile(boatDto.id) &&
                              showReservationForm(false)
                          "
                          type="button"
                          class="btn btn-outline-dark rounded-pill"
                        >
                          SEE PROFILE
                        </button>
                      </template>
                      <template v-if="reservationProcess || searchResultDisplay">
                        <button
                          @click="bookBoat(boatDto.id)"
                          type="button"
                          class="btn btn-outline-dark rounded-pill"
                        >
                          BOOK
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
</template>
  <template v-if="bookBoatOpen">
    <BookBoat
      :bookingProcess="true"
      :boatId="boatId"
      :back="back"
      :startDate="start"
      :endDate="end"
    ></BookBoat>
  </template>
</template>

<script>
import axios from "axios";
import BookBoat from "./BookBoat.vue";
import Datepicker from "vue3-date-time-picker";
import PickLocationMap from "../../components/PickLocationMap";
import dayjs from "dayjs";
import Multiselect from "@vueform/multiselect";

export default {
  components: {
    BookBoat,
    Datepicker,
    PickLocationMap,
    Multiselect,
  },
  props: {
    reservationProcess: Boolean,
    availableBoats: Array,
    showReservationForm: Function,
    startDate: Date,
    endDate: Date,
    unidentifiedUser: Boolean
  },
  data() {
    return {
      email: "",
      boatDtos: [
        {
          id: null,
          ownersUsername: "",
          name: "",
          type: "",
          role: '',
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
      ],
      user: {
        username: "",
      },
      boatsLoaded: false,
      bookBoatOpen: false,
      boatId: "",
      searchAddress: "",
      searchPrice: "",
      searchCity: "",
      searchCountry: "",
      searchMaxPeople: "",
      searchRating: "",
      sortBy: "",
      sortDirection: "asc",
      start: null,
      end: null,
      locationAddress: "",
      locationCity: "",
      locationCountry: "",
      locationOpen: false,
      searchResultDisplay: false,
      filterCancellingCondition: "",
      filterRules: [],
      filterFishingEquipment: [],
      filterNavigationEquipment: [],
      filterAdditionalServices: [],
      rules: [],
      fishingEquipment: [],
      navigationEquipment: [],
      filterAdditionalServicesOptions: [],
      searchName: "",
      searchType: "",
    };
  },
  mounted() {
    this.email = this.$route.params.email;
    this.role = localStorage.role;
    if (this.reservationProcess) {
      this.start = this.startDate;
      this.end = this.endDate;
    }
    this.getBoats();
  },
  methods: {
    filterList: function(boatDtos){
      var boats = [];
      boatDtos.forEach((boatDto) => {
        if(this.checkAllFilters(boatDto))
          boats.push(boatDto);
      });
      return boats;
    },
    checkAllFilters: function(boatDto){
      if(this.checkConditionFilter(boatDto)&&this.checkAdditionalServicesFilter(boatDto)
        &&this.checkRulesFilter(boatDto)&&this.checkFishingEquipmentFilter(boatDto)
        &&this.checkNavigationEquipmentFilter(boatDto))
        return true;
      return false;
    },
    checkFishingEquipmentFilter: function(boatDto){
        var missingEquipment = false;
        this.filterFishingEquipment.forEach((fishingEquipment)=> {
          if (!boatDto.fishingEquipment.toLowerCase().includes(fishingEquipment))
              missingEquipment = true;
        });
        return !missingEquipment;
    },
    checkNavigationEquipmentFilter: function(boatDto){
        var missingEquipment = false;
        this.filterNavigationEquipment.forEach((navigationEquipment)=> {
          if (!boatDto.navigationEquipment.toLowerCase().includes(navigationEquipment))
              missingEquipment = true;
        });
        return !missingEquipment;
    },
    checkRulesFilter: function(boatDto){
        var missingRule = false;
        this.filterRules.forEach((rule)=> {
          if (!boatDto.rules.toLowerCase().includes(rule))
              missingRule = true;
        });
        return !missingRule;
    },
    checkAdditionalServicesFilter: function(boatDto){
        var additionalServicesNames = this.getAdditionalServicesNames(boatDto.additionalServices);
        var missingService = false;
        this.filterAdditionalServices.forEach((additionalService)=> {
          if (additionalServicesNames.indexOf(additionalService.toLowerCase()) === -1)
            missingService = true;
        });
        return !missingService;
    },
    getAdditionalServicesNames: function(additionalServices){
      var names = []
      additionalServices.forEach((service)=>{
        names.push(service.name.toLowerCase());
      })
      return names;
    },
    checkConditionFilter: function(boatDto){
      return this.filterCancellingCondition === "" || this.filterCancellingCondition===boatDto.cancelingCondition;
    },
    fillRulesAndFreeEquipment: function (boatDtos) {
      boatDtos.forEach((item) => {
        item.additionalServices.forEach((additionalService) => {
          if (this.filterAdditionalServicesOptions.indexOf(additionalService.name.toLowerCase()) === -1) {
            this.filterAdditionalServicesOptions.push(additionalService.name.toLowerCase());
          }
        });
        this.getEquipment(item.fishingEquipment).forEach((e) => {
          var equipmentString = e.trim();  
          if (equipmentString!==""&&this.fishingEquipment.indexOf(equipmentString.toLowerCase()) === -1) {
            this.fishingEquipment.push(equipmentString.toLowerCase());
            }
        });
        this.getEquipment(item.navigationEquipment).forEach((e) => {
          var equipmentString = e.trim();  
          if (equipmentString!==""&&this.navigationEquipment.indexOf(equipmentString.toLowerCase()) === -1) {
            this.navigationEquipment.push(equipmentString.toLowerCase());
            }
        });
        this.getRules(item.rules).forEach((r) => {
          var ruleString = r.trim();
          if (ruleString!==""&&this.rules.indexOf(ruleString.toLowerCase()) === -1) {
            this.rules.push(ruleString.toLowerCase());
            }
        });
      });
    },
    getEquipment: function (equipmentString) {
      equipmentString = equipmentString.replace(".", "");
      return equipmentString.split(",");
    },
    getRules: function (rulesString) {
      return rulesString.split(".");
    },
    resetFilter: function () {
      this.filterCancellingCondition = "";
      this.filterRules = [];
      this.filterFishingEquipment = [];
      this.filterNavigationEquipment = [];
      this.filterAdditionalServices = [];
    },
    isResetFilterDisabled: function () {
      if (
        this.filterCancellingCondition === "" &&
        this.filterRules.length==0 &&
        this.filterFishingEquipment.length==0 &&
        this.filterNavigationEquipment.length==0 &&
        this.filterAdditionalServices.length==0
      )
        return true;
      return false;
    },
    submitSearchParams: function (event) {
      event.preventDefault();
      if (!this.dataIsValid()) {
        return;
      }
      this.boatsLoaded = false;
      axios
        .post(
          process.env.VUE_APP_BACKEND_URL+"reservationBoat/searchAvailableBoats",
          {
            startDate: this.formatDate(this.start),
            endDate: this.formatDate(this.end),
            streetAndNum: this.searchAddress,
            city: this.searchCity,
            country: this.searchCountry,
            rating: this.searchRating === "" ? 0.0 : this.searchRating,
            price: this.searchPrice === "" ? 0.0 : this.searchPrice,
            maxPeople: this.searchMaxPeople === "" ? 1 : this.searchMaxPeople,
            username: this.email,
          },
          {}
        )
        .then((response) => {
          this.boatDtos = response.data;
          this.boatsLoaded = true;
          this.searchResultDisplay = true;
          this.fillRulesAndFreeEquipment(response.data);
        });
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
      if (this.searchMaxPeople !== "" && !this.isInt(this.searchMaxPeople)) {
        this.$swal.fire({
          position: "top-end",
          icon: "error",
          title: "Max people must be natural number!",
          showConfirmButton: false,
          timer: 1500,
        });
        return false;
      }
      if (this.searchPrice !== "" && !parseFloat(this.searchPrice)) {
        this.$swal.fire({
          position: "top-end",
          icon: "error",
          title: "Price must be double precision number!",
          showConfirmButton: false,
          timer: 1500,
        });
        return false;
      }
      if (this.searchRating !== "" && !parseFloat(this.searchRating)) {
        this.$swal.fire({
          position: "top-end",
          icon: "error",
          title: "Rating must be double precision number!",
          showConfirmButton: false,
          timer: 1500,
        });
        return false;
      }
      if (parseFloat(this.searchRating)<0 || parseFloat(this.searchRating)>5) {
        this.$swal.fire({
          position: "top-end",
          icon: "error",
          title: "Rating must be between 0.0 and 5.0!",
          showConfirmButton: false,
          timer: 1500,
        });
        return false;
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
    closeLocation: function () {
      this.locationAddress = "";
      this.locationCity = "";
      this.locationCountry = "";
      this.locationOpen = false;
    },
    confirmLocation: function () {
      this.searchAddress = this.locationAddress;
      this.searchCity = this.locationCity;
      this.searchCountry = this.locationCountry;
      this.locationOpen = false;
      this.locationAddress = "";
      this.locationCity = "";
      this.locationCountry = "";
    },
    updateLocation: function (latitude, longitude) {
      axios
        .get("https://nominatim.openstreetmap.org/reverse", {
          params: {
            lat: longitude,
            lon: latitude,
            format: "json",
            "accept-language": "en",
          },
        })
        .then((response) => {
          const { address } = response.data;
          var flag = false;
          var street;
          var number;
          if (address) {
            if (address.road) {
              street = address.road;
              flag = true;
            } else if (address.street) {
              street = address.street;
              flag = true;
            }
            if (flag && address["house-number"]) {
              number = address["house-number"];
            } else if (flag && address["house_number"]) {
              number = address["house_number"];
            }
            if (flag && address.town) {
              this.locationCity = address.town;
            } else if (flag && address.city) {
              this.locationCity = address.city;
            }
            if (address.country) {
              this.locationCountry = address.country;
            }
            this.locationAddress = street + " " + number;
          }
        });
    },
    openLocationForm: function () {
      this.locationOpen = true;
    },
    isResetDisabled: function () {
      if (
        this.start == null &&
        this.searchRating === "" &&
        this.searchAddress === "" &&
        this.searchCity === "" &&
        this.searchCountry === "" &&
        this.searchPrice === "" &&
        this.end == null &&
        this.searchMaxPeople === "" &&
        this.searchName === "" &&
        this.searchType === ""
      )
        return true;
      return false;
    },
    isSearchDisabled: function () {
      if (this.start && this.end) return false;
      return true;
    },
    sort: function (s) {
      if (s === this.sortBy) {
        this.sortDirection = this.sortDirection === "asc" ? "desc" : "asc";
      }
      this.sortBy = s;
    },
    back() {
      this.bookBoatOpen = false;
      if (!this.searchResultDisplay) this.showReservationForm(true);
    },
    getBoats: function () {
      if (this.reservationProcess) {
        this.boatDtos = this.availableBoats;
        this.boatsLoaded = true;
        this.fillRulesAndFreeEquipment(this.availableBoats);
      } else {
        this.user.username = this.email;
        axios.get(process.env.VUE_APP_BACKEND_URL+"boats/getAll").then((response) => {
          this.boatDtos = response.data;
          this.boatsLoaded = true;
          if(!this.unidentifiedUser) this.fillRulesAndFreeEquipment(response.data);
        });
      }
    },
    getImageUrl: function (index) {
      if (this.boatsLoaded == true) {
        return this.sortedBoats[index].images[0].url;
      }
      return "logoF1.png";
    },
    getFullAddress: function (index) {
      if (this.boatsLoaded == true)
        return (
          this.sortedBoats[index].addressDto.streetAndNum +
          ", " +
          this.sortedBoats[index].addressDto.city +
          ", " +
          this.sortedBoats[index].addressDto.country
        );
      return "logoF1.png";
    },
    seeProfile: function (boatId) {
      if(!this.unidentifiedUser)
        this.$router.push('/boat/'+ this.email+ '/' + boatId);
      else
        this.$router.push('/boat/' + boatId);
    },
    bookBoat: function (boatId) {
      this.bookBoatOpen=true;
      this.boatId=boatId;
       if (!this.searchResultDisplay) this.showReservationForm(false);
    },
    resetSearch: function () {
      this.searchRating = "";
      this.searchAddress = "";
      this.searchCity = "";
      this.searchCountry = "";
      this.searchPrice = "";
      this.searchMaxPeople = "";
      this.start = null;
      this.end = null;
      this.searchResultDisplay = false;
      this.searchName = "";
      this.searchType = "";
      if(!this.unidentifiedUser) this.getBoats();
    },
  },
  computed: {
    sortedBoats: function () {
      if(!this.unidentifiedUser){
       if (this.sortBy == "") return this.filterList(this.boatDtos);
      else {
        return this.filterList(this.sortedArray);
      }}else{
        return this.searchBoatsUnidentified;
      }
    },
    sortedArray: function () {
      let sortedEntities = this.boatDtos;
      sortedEntities = sortedEntities.sort((a, b) => {
        let fa, fb;
        if (this.sortBy === "name") {
          fa = a[this.sortBy].toLowerCase();
          fb = b[this.sortBy].toLowerCase();
        }else if(this.sortBy === "location"){
          fa = a.addressDto.streetAndNum + a.addressDto.city + a.addressDto.country;
          fb = b.addressDto.streetAndNum + b.addressDto.city + b.addressDto.country;
        } else {
          fa = a[this.sortBy];
          fb = b[this.sortBy];
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
    searchBoatsUnidentified: function () {
      var temp = this.boatDtos.filter((boat) => {
        return boat.name.toUpperCase().match(this.searchName) && 
               boat.type.toUpperCase().match(this.searchType) &&
               boat.addressDto.streetAndNum.toUpperCase().match(this.searchAddress) &&
               boat.addressDto.city.toUpperCase().match(this.searchCity) &&
               boat.addressDto.country.toUpperCase().match(this.searchCountry) && 
               (!parseFloat(this.searchRating) || boat.rating > this.searchRating) && 
               (!parseFloat(this.searchPrice) || boat.price < this.searchPrice) &&
               (!parseInt(this.searchMaxPeople) || boat.maxPeople > this.searchMaxPeople);
      });

      return temp;
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
