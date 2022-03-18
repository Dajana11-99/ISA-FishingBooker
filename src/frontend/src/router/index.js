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
    component: ProfileAdmin
  },
  {
    path: '/requests/:email',
    name: 'RequestsFromUsers',
    component: RequestsFromUsers
  },
  {
    path: '/reasonForDenying/:email/:person',
    name: 'ReasonForDenying',
    component: ReasonForDenying
  },
  {
    path: '/cabinOwnerHome/:email',
    name: 'CabinOwnerHome',
    component: CabinOwnerHome
  },
  {
    path: '/boatOwnerHome/:email',
    name: 'BoatOwnerHome',
    component: BoatOwnerHome
  },
  {
    path: '/fishingInstructorHome/:email',
    name: 'FishingInstructorHome',
    component: FishingInstructorHome
  },
  {
    path: '/clientHome/:email',
    name: 'ClientHome',
    component: ClientHome
  },
  {
    path: '/editProfile/:role/:email',
    name: 'EditProfile',
    component: EditProfile
  },
  {
    path: '/allUsers/:email',
    name: 'AllUsers',
    component: AllUsers
  },
  {
    path: '/addAdmin/:email',
    name: 'AddAdmin',
    component: AddAdmin
  },
  {
    path: '/changedPasswordInfo/:email',
    name: 'ChangedPasswordInfo',
    component: ChangedPasswordInfo
  },
  {
    path: '/changedPassword/:email',
    name: 'ChangedPassword',
    component: ChangedPassword
  },
  {
    path: '/deleteAccount/:email',
    name: 'DeleteAccount',
    component: DeleteAccount
  },
  {
    path: '/addNewCabin/:email',
    name: 'AddNewCabin',
    component: AddNewCabin
  },
  {
    path: '/cabinProfile/:email/:cabinName',
    name: 'CabinProfile',
    component: CabinProfile
  },
  {
    path: '/editCabinProfile/:email/:cabinName',
    name: 'EditCabinProfile',
    component: EditCabinProfile
  },
  {
    path: '/addNewAdventure/:email',
    name: 'AddNewAdventure',
    component: AddNewAdventure
  },
  {
    path: '/adventureProfile/:email/:adventureName',
    name: 'AdventureProfile',
    component: AdventureProfile
  },
  {
    path: '/editAdventure/:email/:adventureName',
    name: 'EditAdventure',
    component: EditAdventure
  },
  {
    path: '/addNewBoat/:email',
    name: 'AddNewBoat',
    component: AddNewBoat
  },
  {
    path: '/boatProfile/:email/:boatName',
    name: 'BoatProfile',
    component: BoatProfile
  },
  {
    path: '/editBoatProfile/:email/:boatName',
    name: 'EditBoatProfile',
    component: EditBoatProfile
  },
  {
    path: '/deletingAccountRequests/:email',
    name: 'DeletingAccountRequests',
    component: DeletingAccountRequests
  },
  {
    path: '/MyCalendar/:email',
    name: 'MyCalendar',
    component: MyCalendar
  },
  {
    path: '/CabinCalendar/:email/:cabinName',
    name: 'CabinCalendar',
    component: CabinCalendar
  },
  {
    path: '/BoatCalendar/:email/:boatName',
    name: 'BoatCalendar',
    component: BoatCalendar
  },
  

]

const router = createRouter({
  history: createWebHistory(process.env.VUE_APP_URL),
  routes,
})

export default router
