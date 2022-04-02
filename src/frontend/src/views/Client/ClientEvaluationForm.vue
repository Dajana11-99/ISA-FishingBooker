<template>
  <ClientNavbar />
    <div>
      <div class="row justify-content-center" style="width: 100%">
        <div class="col-md-6">
          <div class="card card-outline-secondary">
            <div class="card-header">
              <h3 style="text-align: center" class="mb-0">Evaluation</h3>
            </div>
            <div class="card-body">
              <div style="text-align: left" class="form-group">
                <label>Comment:</label>
                <textarea
                  v-model="comment"
                  class="form-control"
                  id="exampleFormControlTextarea1"
                  rows="3"
                ></textarea>
              </div>

              <br />

              <div style="text-align: left" class="form-group">
                <label>Grade:</label>
                <star-rating v-model:rating="grade"></star-rating>
              </div>

              <br />
              <div class="form-group" style="text-align: right">
                <button
                  data-bs-toggle="modal"
                  data-bs-target="#staticBackdrop"
                  class="btn btn-outline-dark"
                  @click="submitEvaluation"
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
import StarRating from 'vue-star-rating'

export default {
  components: {
    ClientNavbar,
    StarRating
  },
  data() {
    return {
      email: "",
      selectedEntity: "",
      comment: "",
      grade: 5,
      reservationId: 0,
    };
  },
  mounted() {
    this.email = this.$route.params.email;
    this.selectedEntity = this.$route.params.entity;
    this.reservationId = this.$route.params.reservationId;
  },
  methods: {
    submitEvaluation: function (event) {
      event.preventDefault();

      if (this.selectedEntity === "cabin") {
        axios
          .post(
            "http://localhost:8081/cabinEvaluation/addEvaluation",
            {
              cabinReservationDto: {
                id: this.reservationId,
              },
              comment: this.comment,
              grade: this.grade,
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
            timer: 2500,
          });
          this.$router.push("/reservations/" + this.email);
          }).catch(() => {
          this.loader.hide();
          this.$swal.fire({
            icon: "error",
            title: "Something went wrong!",
            text: "Unsuccessful evaluation submit!",
          });
        });
      } else if (this.selectedEntity === "boat") {
        console.log("Boats");
      } else {
        console.log("adventures");
      }
    },
  },
  computed: {},
};
</script> 

<style>
</style>
