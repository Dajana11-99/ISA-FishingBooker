import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { initializeApp } from "firebase/app";
import { getDatabase } from "firebase/database";
import {getAuth} from "firebase/auth"
//mape
import OpenLayersMap from 'vue3-openlayers'
import 'vue3-openlayers/dist/vue3-openlayers.css'
//bootstrap
import "bootstrap/dist/css/bootstrap.min.css";
import 'bootstrap';
//alertovi
import VueSweetalert2 from 'vue-sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css'; 
//loading
import VueLoading from 'vue-loading-overlay';
import 'vue-loading-overlay/dist/vue-loading.css';
//calendars
import Datepicker from 'vue3-date-time-picker';
import 'vue3-date-time-picker/dist/main.css';
import VCalendar from 'v-calendar';

import store from '../src/store/index'

const firebaseConfig = {
    apiKey: "AIzaSyAwNdI28zKlIT2GG3pRLpH9aMmcbUftl04",
    authDomain: "isa15-d7c5c.firebaseapp.com",
    projectId: "isa15-d7c5c",
    storageBucket: "isa15-d7c5c.appspot.com",
    messagingSenderId: "16155551868",
    appId: "1:16155551868:web:4b30cbf90676b3a8a681f9",
    measurementId: "G-EN45NNPT4M"
  };
  
  // Initialize Firebase
const app = initializeApp(firebaseConfig)

getDatabase(app);
getAuth();
createApp(App).use(router).use(VueSweetalert2).use(VCalendar).use(VueLoading).use(Datepicker).use(store).use(OpenLayersMap).mount('#app');



