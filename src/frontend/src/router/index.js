import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Signup from '../views/Signup.vue'
import AccountAlert from '../views/AccountAlert.vue'
import Activation from '../views/Activation.vue'
import ProfileAdmin from '../views/Admin/ProfileAdmin.vue'
import RequestsFromUsers from '../views/Admin/RequestsFromUsers'
import ReasonForDenying from '../views/Admin/ReasonForDenying'
import BoatOwnerHome from '../views/BoatOwner/BoatOwnerHome'
import FishingInstructorHome from '../views/FishingInstructor/FishingInstructorHome'
import EditProfile from '../views/EditProfile'
import AllUsers from '../views/Admin/AllUsers'
import AddAdmin from '../views/Admin/AddAdmin'
import ChangedPasswordInfo from '../views/ChangedPasswordInfo'
import ChangedPassword from '../views/ChangedPassword'
import DeleteAccount from '../views/DeleteAccount'
import CabinOwnerHome from '../views/CabinOwner/CabinOwnerHome.vue'
import AddNewCabin from '../views/CabinOwner/AddNewCabin'
import CabinProfile from '../views/CabinOwner/CabinProfile'
import EditCabinProfile from '../views/CabinOwner/EditCabinProfile'
import AddNewAdventure from '../views/FishingInstructor/AddNewAdventure'
import AdventureProfile from '../views/FishingInstructor/AdventureProfile'
import EditAdventure from '../views/FishingInstructor/EditAdventure'
import AddNewBoat from '../views/BoatOwner/AddNewBoat'
import BoatProfile from '../views/BoatOwner/BoatProfile'
import EditBoatProfile from '../views/BoatOwner/EditBoatProfile'
import DeletingAccountRequests from '../views/Admin/DeletingAccountRequests'
import MyCalendar from '../views/FishingInstructor/MyCalendar'
import ClientHome from '../views/Client/ClientHome.vue'
import CabinCalendar from '../views/CabinOwner/CabinCalendar'
import BoatCalendar from '../views/BoatOwner/BoatCalendar'
//import store from '../store'

import BoatOwnerCalendar from '../views/BoatOwner/BoatOwnerCalendar'
import ClientReservationForm from '../views/Client/ClientReservationForm'
import ClientCabins from '../views/Client/ClientCabins'

const routes = [
  
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup
  },
  {
    path: '/accountAlert/:email',
    name: 'AccountAlert',
    component: AccountAlert
  },
  {
    path: '/activation/:activationCode/:email',
    name: 'Activation',
    component: Activation
  },
  {
    path: '/profileAdmin/:email',
    name: 'ProfileAdmin',
    component: ProfileAdmin,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty' || localStorage.role !='ADMIN'){
        next('/')
      }else{
        next();
      }
    },
    
  },
  {
    path: '/requests/:email',
    name: 'RequestsFromUsers',
    component: RequestsFromUsers,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty' || localStorage.role !='ADMIN'){
        next('/')
      }
      next();
    
    },
  },
  {
    path: '/reasonForDenying/:email/:person',
    name: 'ReasonForDenying',
    component: ReasonForDenying,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty' || localStorage.role !='ADMIN'){
        next('/')
      }
      next();
    },
  },
  {
    path: '/cabinOwnerHome/:email',
    name: 'CabinOwnerHome',
    component: CabinOwnerHome,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty' || localStorage.role !='CABIN OWNER'){

        next('/')
      }
      next();
    
    },
  },
  {
    path: '/boatOwnerHome/:email',
    name: 'BoatOwnerHome',
    component: BoatOwnerHome,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty' || localStorage.role !='BOAT OWNER'){
        next('/')
      }
      next();
    
    },
  },
  {
    path: '/fishingInstructorHome/:email',
    name: 'FishingInstructorHome',
    component: FishingInstructorHome,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty' || localStorage.role !='FISHING INSTRUCTOR'){
        next('/')
      }
      next();
    
    },
  },
  {
    path: '/clientHome/:email',
    name: 'ClientHome',
    component: ClientHome,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty' || localStorage.role !='CLIENT'){

        next('/')
      }
      next();
    
    },
  },
  {
    path: '/reservation/:email',
    name: 'ClientReservationForm',
    component: ClientReservationForm
  },
  {
    path: '/availableCabins/:email',
    name: 'ClientCabins',
    component: ClientCabins,
    props: true
  },
  {
    path: '/editProfile/:role/:email',
    name: 'EditProfile',
    component: EditProfile,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty'){
        next('/')
      }
      next();
    
    },
  },
  {
    path: '/allUsers/:email',
    name: 'AllUsers',
    component: AllUsers,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty' || localStorage.role !='ADMIN'){
        next('/')
      }
      next();
    
    },
  },
  {
    path: '/addAdmin/:email',
    name: 'AddAdmin',
    component: AddAdmin,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty' || localStorage.role !='ADMIN'){
        next('/')
      }
      next();
    
    },
  },
  {
    path: '/changedPasswordInfo/:email',
    name: 'ChangedPasswordInfo',
    component: ChangedPasswordInfo,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty'){
        next('/')
      }
      next();
    
    },
  },
  {
    path: '/changedPassword/:email',
    name: 'ChangedPassword',
    component: ChangedPassword,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty'){
        next('/')
      }
      next();
    
    },
  },
  {
    path: '/deleteAccount/:email',
    name: 'DeleteAccount',
    component: DeleteAccount,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty'){
        next('/')
      }
      next();
    
    },
  },
  {
    path: '/addNewCabin/:email',
    name: 'AddNewCabin',
    component: AddNewCabin,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty' || localStorage.role !='CABIN OWNER'){
        next('/')
      }
      next();
    
    },
  },
  {
    path: '/cabinProfile/:email/:cabinName',
    name: 'CabinProfile',
    component: CabinProfile,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty' || localStorage.role !='CABIN OWNER'){
        next('/')
      }
      next();
    
    },
  },
  {
    path: '/editCabinProfile/:email/:cabinName',
    name: 'EditCabinProfile',
    component: EditCabinProfile,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty' || localStorage.role !='CABIN OWNER'){
        next('/')
      }
      next();
    
    },
  },
  {
    path: '/addNewAdventure/:email',
    name: 'AddNewAdventure',
    component: AddNewAdventure,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty' || localStorage.role !='FISHING INSTRUCTOR'){
        next('/')
      }
      next();
    
    },
  },
  {
    path: '/adventureProfile/:email/:adventureName',
    name: 'AdventureProfile',
    component: AdventureProfile,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty' || localStorage.role !='FISHING INSTRUCTOR'){
        next('/')
      }
      next();
    
    },
  },
  {
    path: '/editAdventure/:email/:adventureName',
    name: 'EditAdventure',
    component: EditAdventure,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty' || localStorage.role !='FISHING INSTRUCTOR'){
        next('/')
      }
      next();
    
    },
  },
  {
    path: '/addNewBoat/:email',
    name: 'AddNewBoat',
    component: AddNewBoat,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty' || localStorage.role !='BOAT OWNER'){

        next('/')
      }
      next();
    
    },
  },
  {
    path: '/boatProfile/:email/:boatName',
    name: 'BoatProfile',
    component: BoatProfile,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty' || localStorage.role !='BOAT OWNER'){
        next('/')
      }
      next();
    
    },
  },
  {
    path: '/editBoatProfile/:email/:boatName',
    name: 'EditBoatProfile',
    component: EditBoatProfile,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty' || localStorage.role !='BOAT OWNER'){
        next('/')
      }
      next();
    
    },
  },
  {
    path: '/deletingAccountRequests/:email',
    name: 'DeletingAccountRequests',
    component: DeletingAccountRequests,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty' || localStorage.role !='ADMIN'){
        next('/')
      }
      next();
    
    },
  },
  {
    path: '/MyCalendar/:email',
    name: 'MyCalendar',
    component: MyCalendar,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty' || localStorage.role !='FISHING INSTRUCTOR'){
        next('/')
      }
      next();
    
    },
  },
  {
    path: '/CabinCalendar/:email/:cabinName',
    name: 'CabinCalendar',
    component: CabinCalendar,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty' || localStorage.role !='CABIN OWNER'){
        next('/')
      }
      next();
    
    },
  },
  {
    path: '/BoatCalendar/:email/:boatName',
    name: 'BoatCalendar',
    component: BoatCalendar,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty' || localStorage.role !='BOAT OWNER'){
        next('/')
      }
      next();
    
    },
  },
  {
    path: '/BoatOwnerCalendar/:email',
    name: 'BoatOwnerCalendar',
    component: BoatOwnerCalendar,
       beforeEnter: (to, from,next) => {
      // reject the navigation
      if(localStorage.token == 'empty' || localStorage.role !='BOAT OWNER'){
        next('/')
      }
      next();
    
    },
  },
  

]

const router = createRouter({
  history: createWebHistory(process.env.VUE_APP_URL),
  routes,
})

export default router

