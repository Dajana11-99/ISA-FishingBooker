<template>
  <ClientNavbar />
  <div>
    <div class="row justify-content-center" style="width: 100%">
      <div class="col-md-6">
        <div class="card card-outline-secondary">
          <div class="card-header">
            <h3 style="text-align: center" class="mb-0">Complaint</h3>
          </div>
          <div class="card-body">
            <div style="text-align: left" class="form-group">
              <label>Subject of complaint:</label>
              <select
                v-model="subjectRole"
                class="form-select form-select-sm"
                aria-label=".form-select-sm example"
                :disabled="selectedEntity=='Adventure'"
              >
                <option
                  v-for="(item, index) in options"
                  v-bind:key="index"
                  :value="item"
                >
                  {{ item }}
                </option>
              </select>
            </div>

            <br />

            <div style="text-align: left" class="form-group">
              <label>Text:</label>
              <textarea
                v-model="text"
                class="form-control"
                id="exampleFormControlTextarea1"
                rows="3"
              ></textarea>
            </div>

            <br />
            <div class="form-group" style="text-align: right">
              <button
                class="btn btn-outline-dark"
                :disabled= "text.length==0"
                @click="submitComplaint"
              >
                Confirm
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import ClientNavbar from "./ClientNavbar";

export default {
  components: {
    ClientNavbar,
  },
  data() {
    return {
      email: "",
      subjectRole: "",
      subjectName: "",
      text: "",
      reservationId: 0,
      selectedEntity: "",
      options: [],
    };
  },
  mounted() {
    this.email = this.$route.params.email;
    this.selectedEntity = this.capitalizeFirstLetter(this.$route.params.entity);
    this.subjectRole = this.selectedEntity
    this.reservationId = this.$route.params.reservationId;
    this.addOptions();
  },
  methods: {
    addOptions: function () {
      if (this.selectedEntity !== "Adventure"){
        this.options.push(this.capitalizeFirstLetter(this.selectedEntity));
        this.options.push(this.capitalizeFirstLetter(this.selectedEntity + " owner"));
      }else{
        this.options.push("Fishing instructor");
        this.subjectRole = "Fishing instructor";
      }
    },
    capitalizeFirstLetter: function (string) {
      return string.charAt(0).toUpperCase() + string.slice(1);
    },
    submitComplaint: function (event) {
      event.preventDefault();

      axios
        .post(
          process.env.VUE_APP_BACKEND_URL+"complaints/addComplaint",
          {
            text: this.text,
            clientUsername: this.email,
            subjectRole: this.subjectRole.toLowerCase(),
            reservationId: this.reservationId,
          },
          {}
        )
        .then((response) => {
          this.$swal.fire({
            position: "top-end",
            icon: "success",
            title: response.data,
            showConfirmButton: false,
            timer: 2500,
          });
          this.$router.push("/reservations/" + this.email);
        })
        .catch(() => {
          this.loader.hide();
          this.$swal.fire({
            icon: "error",
            title: "Something went wrong!",
            text: "Unsuccessful complaint submit!",
          });
        });
    },
  },
  computed: {},
};
</script> 

<style>
</style>
