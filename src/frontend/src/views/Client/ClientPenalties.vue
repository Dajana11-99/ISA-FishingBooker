<template>
  <ClientNavbar />
  <div>
    <h1>PENALTIES</h1>
    <hr />
    <div style="padding: 2%">
      <div style="padding-left: 2%; padding-right: 2%">
        <div class="tab-content" id="myTabContent">
          <div
            class="tab-pane fade show active"
            id="home"
            role="tabpanel"
            aria-labelledby="home-tab"
          >
            <template v-if="!penaltiesLoaded">
              <h3>Loading...</h3>
            </template>
            <template v-if="penaltiesLoaded">
              <template v-if="penalties.length == 0">
                <h3>No penalties to show.</h3>
              </template>
              <template v-if="penalties.length != 0">
                <table class="table">
                  <thead>
                    <tr>
                      <th scope="col">#</th>
                      <th scope="col">Date</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(penalty, index) in penalties" :key="index">
                      <th scope="row">{{ index + 1 }}</th>
                      <td>{{ setandFormatDate(penalty.date) }}</td>
                    </tr>
                  </tbody>
                </table>
              </template>
            </template>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import ClientNavbar from "./ClientNavbar.vue";
import dayjs from "dayjs";
export default {
  components: {
    ClientNavbar,
  },
  data() {
    return {
      email: "",
      penalties: [],
      penaltiesLoaded: false,
    };
  },
  mounted() {
    this.email = this.$route.params.email;
    this.getPenalties();
  },
  methods: {
    setandFormatDate: function (newDate) {
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
    getPenalties: function () {
      axios
        .get(
          process.env.VUE_APP_BACKEND_URL+"penalty/getClientPenalties/" + this.email + "/"
        )
        .then((response) => {
          this.penalties = response.data;
          this.penaltiesLoaded = true;
        });
    },
  },
};
</script>
