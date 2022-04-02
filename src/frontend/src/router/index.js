import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Signup from '../views/Signup.vue'
import AccountAlert from '../views/AccountAlert.vue'
import Activation from '../views/Activation.vue'
//import ProfileAdmin from '../views/Admin/ProfileAdmin.vue'
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
import ClientReservationForm from '../views/Client/ClientReservationForm'
import ClientCabins from '../views/Client/ClientCabins'
import store from '../store/index'
import PersonalCalendar from '../views/BoatOwner/PersonalCalendar.vue'
import ClientReservations from '../views/Client/ClientReservations'
import ClientCabinProfile from '../views/Client/ClientCabinProfile'
import ReservationsBoatOwner from '../views/BoatOwner/ReservationsBoatOwner'
import ClientEvaluationForm from '../views/Client/ClientEvaluationForm'
import QuickReservationsBoatOwner from '../views/BoatOwner/QuickReservationsBoat'
import ReservationsCabinOwner from '../views/CabinOwner/ReservationsCabinOwner'
import QuickReservationsCabin from '../views/CabinOwner/QuickReservationsCabin'
import QuickReservationAdventure from '../views/FishingInstructor/QuickReservationsAdventure'
import ReservationAdventure from '../views/FishingInstructor/AdventureReservations'

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
    component: () =>
        import ('../views/Admin/ProfileAdmin.vue'),
    beforeEnter: (to, from, next) => {
      store.dispatch('refreshToken')
     if(localStorage.token == 'empty' || localStorage.role !='ADMIN' || localStorage.logged == false){
          next('/')
        }else {
          next()
        }
      }
  },
  {
    path: '/requests/:email',
    name: 'RequestsFromUsers',
    component: RequestsFromUsers,
    beforeEnter: (to, from, next) => {
      store.dispatch('refreshToken')
     if(localStorage.token == 'empty' || localStorage.role !='ADMIN' || localStorage.logged == false){
          next('/')
        }else {
          next()
        }
      }
  },
  {
    path: '/reasonForDenying/:email/:person',
    name: 'ReasonForDenying',
    component: ReasonForDenying,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      store.dispatch('refreshToken')
      if(localStorage.token == 'empty' || localStorage.role !='ADMIN' || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       }                                                           

  },
  {
    path: '/cabinOwnerHome/:email',
    name: 'CabinOwnerHome',
    component: CabinOwnerHome,
    beforeEnter: (to, from,next) => {
      // reject the navigation
  
      store.dispatch('refreshToken')
      if(localStorage.token == 'empty' || localStorage.role !='CABINOWNER' || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       }                                                            
 
  },
  {
    path: '/boatOwnerHome/:email',
    name: 'BoatOwnerHome',
    component: BoatOwnerHome,
    beforeEnter: (to, from,next) => {
      store.dispatch('refreshToken')
     if(localStorage.token == 'empty' || localStorage.role !='BOATOWNER' || localStorage.logged == false){
          next('/')
        }else {
          next()
        }
      }                                                         

  },
  {
    path: '/reservationsBoatOwner/:email',
    name: 'ReservationsBoatOwner',
    component: ReservationsBoatOwner,
    beforeEnter: (to, from,next) => {
      store.dispatch('refreshToken')
     if(localStorage.token == 'empty' || localStorage.role !='BOATOWNER' || localStorage.logged == false){
          next('/')
        }else {
          next()
        }
      }                                                         

  },
  
  {
    path: '/quickReservationsBoatOwner/:email',
    name: 'QuickReservationsBoatOwner',
    component: QuickReservationsBoatOwner,
    beforeEnter: (to, from,next) => {
      store.dispatch('refreshToken')
     if(localStorage.token == 'empty' || localStorage.role !='BOATOWNER' || localStorage.logged == false){
          next('/')
        }else {
          next()
        }
      }                                                         

  },
  {
    path: '/reservationsCabinOwner/:email',
    name: 'ReservationsCabinOwner',
    component: ReservationsCabinOwner,
    beforeEnter: (to, from,next) => {
      store.dispatch('refreshToken')
     if(localStorage.token == 'empty' || localStorage.role !='CABINOWNER' || localStorage.logged == false){
          next('/')
        }else {
          next()
        }
      }                                                         

  },
  
  {
    path: '/quickReservationsCabinOwner/:email',
    name: 'QuickReservationsCabinOwner',
    component: QuickReservationsCabin,
    beforeEnter: (to, from,next) => {
      store.dispatch('refreshToken')
     if(localStorage.token == 'empty' || localStorage.role !='CABINOWNER' || localStorage.logged == false){
          next('/')
        }else {
          next()
        }
      }                                                         

  },
  {
    path: '/fishingInstructorHome/:email',
    name: 'FishingInstructorHome',
    component: FishingInstructorHome,
    beforeEnter: (to, from,next) => {
      store.dispatch('refreshToken')
      if(localStorage.token == 'empty' || localStorage.role !='FISHINGINSTRUCTOR' || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       }
  },
  {
    path: '/quickReservationAdventure/:email',
    name: 'QuickReservationAdventure',
    component: QuickReservationAdventure,
    beforeEnter: (to, from,next) => {
      store.dispatch('refreshToken')
      if(localStorage.token == 'empty' || localStorage.role !='FISHINGINSTRUCTOR' || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       }
  },
  {
    path: '/reservationAdventure/:email',
    name: 'ReservationAdventure',
    component: ReservationAdventure,
    beforeEnter: (to, from,next) => {
      store.dispatch('refreshToken')
      if(localStorage.token == 'empty' || localStorage.role !='FISHINGINSTRUCTOR' || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       }
  },
  {
    path: '/clientHome/:email',
    name: 'ClientHome',
    component: ClientHome,
    beforeEnter: (to, from,next) => {
      store.dispatch('refreshToken')
      if(localStorage.token == 'empty' || localStorage.role !='CLIENT' || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       }                                                          
 
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
    path: '/reservations/:email',
    name: 'ClientReservations',
    component: ClientReservations,
    props: true
  },
  {
    path: '/cabin/:email/:cabinName',
    name: 'Cabin',
    component: ClientCabinProfile,
    props: true
  },
  {
    path: '/evaluation/:email/:entity/:reservationId',
    name: 'ClientEvaluationForm',
    component: ClientEvaluationForm,
    props: true
  },
  {
    path: '/editProfile/:role/:email',
    name: 'EditProfile',
    component: EditProfile,
    beforeEnter: (to, from,next) => {
 
      if(localStorage.token == 'empty'){
        next('/')
      }
      store.dispatch('refreshToken')
      next();
    }                                                             

  },
  {
    path: '/allUsers/:email',
    name: 'AllUsers',
    component: AllUsers,
    beforeEnter: (to, from,next) => {
      store.dispatch('refreshToken')
      if(localStorage.token == 'empty' || localStorage.role !='ADMIN' || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       }                                                           
  },
  {
    path: '/addAdmin/:email',
    name: 'AddAdmin',
    component: AddAdmin,
    beforeEnter: (to, from,next) => {
      store.dispatch('refreshToken')
      if(localStorage.token == 'empty' || localStorage.role !='ADMIN' || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       }
  },
  {
    path: '/changedPasswordInfo/:email',
    name: 'ChangedPasswordInfo',
    component: ChangedPasswordInfo,
    beforeEnter: (to, from,next) => {
      // reject the navigation

      store.dispatch('refreshToken')
      if(localStorage.token == 'empty'  || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       }                                                          

  },
  {
    path: '/changedPassword/:email',
    name: 'ChangedPassword',
    component: ChangedPassword,
    beforeEnter: (to, from,next) => {
  
      store.dispatch('refreshToken')
      if(localStorage.token == 'empty'  || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       }
  },
  {
    path: '/deleteAccount/:email',
    name: 'DeleteAccount',
    component: DeleteAccount,
    beforeEnter: (to, from,next) => {
      // reject the navigatio
        store.dispatch('refreshToken')
        if(localStorage.token == 'empty' || localStorage.logged == false){
             next('/')
           }else {
             next()
           }
         }
  },
  {
    path: '/addNewCabin/:email',
    name: 'AddNewCabin',
    component: AddNewCabin,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      store.dispatch('refreshToken')
      if(localStorage.token == 'empty' || localStorage.role !='CABINOWNER' || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       }                                                         

  },
  {
    path: '/cabinProfile/:email/:cabinName',
    name: 'CabinProfile',
    component: CabinProfile,
    beforeEnter: (to, from,next) => {
      // reject the navigation
    
      store.dispatch('refreshToken')
      if(localStorage.token == 'empty' || localStorage.role !='CABINOWNER' || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       }
  },
  {
    path: '/editCabinProfile/:email/:cabinName',
    name: 'EditCabinProfile',
    component: EditCabinProfile,
    beforeEnter: (to, from,next) => {
      store.dispatch('refreshToken')
      if(localStorage.token == 'empty' || localStorage.role !='CABINOWNER' || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       }
  },
  {
    path: '/addNewAdventure/:email',
    name: 'AddNewAdventure',
    component: AddNewAdventure,
    beforeEnter: (to, from,next) => {
     
      store.dispatch('refreshToken')
      if(localStorage.token == 'empty' || localStorage.role !='FISHINGINSTRUCTOR' || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       }
  },
  {
    path: '/adventureProfile/:email/:adventureName',
    name: 'AdventureProfile',
    component: AdventureProfile,
    beforeEnter: (to, from,next) => {
   
      store.dispatch('refreshToken')
      if(localStorage.token == 'empty' || localStorage.role !='FISHINGINSTRUCTOR' || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       }
  },
  {
    path: '/editAdventure/:email/:adventureName',
    name: 'EditAdventure',
    component: EditAdventure,
    beforeEnter: (to, from,next) => {
      store.dispatch('refreshToken')
     if(localStorage.token == 'empty' || localStorage.role !='FISHINGINSTRUCTOR' || localStorage.logged == false){
          next('/')
        }else {
          next()
        }
      }
  },
  {
    path: '/addNewBoat/:email',
    name: 'AddNewBoat',
    component: AddNewBoat,
    beforeEnter: (to, from,next) => {
  
      store.dispatch('refreshToken')
     if(localStorage.token == 'empty' || localStorage.role !='BOATOWNER' || localStorage.logged == false){
          next('/')
        }else {
          next()
        }
      }

    
  },
  {
    path: '/personalCalendar/:email',
    name: 'PersonalCalendar',
    component: PersonalCalendar,
    beforeEnter: (to, from,next) => {
  
      store.dispatch('refreshToken')
     if(localStorage.token == 'empty' || localStorage.role !='BOATOWNER' || localStorage.logged == false){
          next('/')
        }else {
          next()
        }
      }

    
  },
  {
    path: '/boatProfile/:email/:boatName',
    name: 'BoatProfile',
    component: BoatProfile,
    beforeEnter: (to, from,next) => {
  
      store.dispatch('refreshToken')
      if(localStorage.token == 'empty' || localStorage.role !='BOATOWNER' || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       }
  },
  {
    path: '/editBoatProfile/:email/:boatName',
    name: 'EditBoatProfile',
    component: EditBoatProfile,
    beforeEnter: (to, from,next) => {
      store.dispatch('refreshToken')
      if(localStorage.token == 'empty' || localStorage.role !='BOATOWNER' || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       }
  },
  {
    path: '/deletingAccountRequests/:email',
    name: 'DeletingAccountRequests',
    component: DeletingAccountRequests,
    beforeEnter: (to, from,next) => {
     
      store.dispatch('refreshToken')
      if(localStorage.token == 'empty' || localStorage.role !='ADMIN' || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       }
  },
  {
    path: '/MyCalendar/:email',
    name: 'MyCalendar',
    component: MyCalendar,
    beforeEnter: (to, from,next) => {
      store.dispatch('refreshToken')
      if(localStorage.token == 'empty' || localStorage.role !='FISHINGINSTRUCTOR' || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       }
  },
 
  {
    path: '/CabinCalendar/:email/:cabinName',
    name: 'CabinCalendar',
    component: CabinCalendar,
    beforeEnter: (to, from,next) => {
      // reject the navigation
      store.dispatch('refreshToken')
      if(localStorage.token == 'empty' || localStorage.role !='CABINOWNER' || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       }
  },
  {
    path: '/BoatCalendar/:email/:boatName',
    name: 'BoatCalendar',
    component: BoatCalendar,
    beforeEnter: (to, from,next) => {
  
      store.dispatch('refreshToken')
      if(localStorage.token == 'empty' || localStorage.role !='BOATOWNER' || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       }
  },
  
  

]

const router = createRouter({
  
  history: createWebHistory(),
  routes,
})

export default router





