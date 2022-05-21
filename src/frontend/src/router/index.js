import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import LandingPage from '../views/LandingPage.vue'
import Login from '../views/Login.vue'
import Signup from '../views/Signup.vue'
import AccountAlert from '../views/AccountAlert.vue'
import Activation from '../views/Activation.vue'
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
import ClientComplaintForm from '../views/Client/ClientComplaintForm'
import UserRank from '../views/Admin/UserRank'
import ClientActivation from '../views/Client/ClientActivation'
import ClientAccountAlert from '../views/Client/ClientAccountAlert'
import Review from '../views/Admin/Review'
import CabinBusinessReport from '../views/CabinOwner/CabinBusinessReport'
import InstructorsBusinessReport from '../views/FishingInstructor/InstructorsBusinessReport'
import BoatOwnerBusinessReport from '../views/BoatOwner/BoatOwnerBusinessReport'
import ClientPenalties from '../views/Client/ClientPenalties'
import ClientBoatProfile from '../views/Client/ClientBoatProfile'
import AdminBusinessReport from '../views/Admin/AdminBusinessReport'
import ClientAdventureProfile from '../views/Client/ClientAdventureProfile'
import Evaluations from '../views/Admin/Evaluations'
import ClientSubscriptionsList from '../views/Client/ClientSubscriptionsList'
import ClientAvailableQuickReservations from '../views/Client/ClientAvailableQuickReservations'
import Complaints from '../views/Admin/Complaints'
import CabinOwnerProfile from '../views/CabinOwner/CabinOwnerProfile'
import BoatOwnerProfile from '../views/BoatOwner/BoatOwnerProfile'
import FishingInstructorProfile from '../views/FishingInstructor/FishingInstructorProfile'
import ClientProfile from '../views/Client/ClientProfile'
import CabinStatistics from '../views/CabinOwner/CabinStatistics'
import BoatStatistics from '../views/BoatOwner/BoatStatistics'
import AdventureStatistics from '../views/FishingInstructor/AdventureStatistics'
const routes = [
  
  {
    path: '/',
    name: 'LandingPage',
    component: LandingPage
  },
  {
    path: '/home',
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
    path: '/clientAccountAlert/:email',
    name: 'ClientAccountAlert',
    component: ClientAccountAlert
  },
  {
    path: '/activation/:activationCode/:email',
    name: 'Activation',
    component: Activation
  },
  {
    path: '/accountActivation/:activationCode/:email',
    name: 'accountActivation',
    component: ClientActivation
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
   
    path: '/complaints/:email',
    name: 'Complaints',
    component: Complaints,
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
    path: '/reports/:email',
    name: 'AdminBusinessReport',
    component: AdminBusinessReport,
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
    path: '/reviews/:email',
    name: 'Review',
    component: Review,
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
    path: '/evaluations/:email',
    name: 'Evaluations',
    component: Evaluations,
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
    path: '/userRank/:email',
    name: 'UserRank',
    component: UserRank,
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
    path: '/cabinStatistics/:email/:cabin',
    name: 'CabinStatistics',
    component: CabinStatistics,
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
    path: '/cabinOwner/viewProfile/:email/:personsProfile',
    name: 'CabinOwnerProfile',
    component: CabinOwnerProfile,
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
    path: '/boatOwner/viewProfile/:email/:personsProfile',
    name: 'BoatOwnerProfile',
    component: BoatOwnerProfile,
    beforeEnter: (to, from,next) => {
      // reject the navigation
  
      store.dispatch('refreshToken')
      if(localStorage.token == 'empty' || localStorage.role !='BOATOWNER' || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       }                                                            
 
  },
  {
    path: '/boatStatistics/:email/:boatName',
    name: 'BoatStatistics',
    component: BoatStatistics,
    beforeEnter: (to, from,next) => {
      // reject the navigation
  
      store.dispatch('refreshToken')
      if(localStorage.token == 'empty' || localStorage.role !='BOATOWNER' || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       }                                                            
 
  },
  {
    path: '/cabinBusinessReport/:email',
    name: 'CabinBusinessReport',
    component: CabinBusinessReport,
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
    path: '/boatOwnerBusinessReport/:email',
    name: 'BoatOwnerBusinessReport',
    component: BoatOwnerBusinessReport,
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
    path: '/adventureStatistics/:email/:adventureName',
    name: 'AdventureStatistics',
    component: AdventureStatistics,
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
    path: '/instructor/viewProfile/:email/:personsProfile',
    name: 'FishingInstructorProfile',
    component: FishingInstructorProfile,
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
    path: '/insctructorsBusinessReport/:email',
    name: 'InstructorsBusinessReport',
    component: InstructorsBusinessReport,
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
    path: '/subscriptions/:email',
    name: 'ClientSubscriptions',
    component: ClientSubscriptionsList,
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
    path: '/penalties/:email',
    name: 'ClientPenalties',
    component: ClientPenalties,
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
    path: '/client/viewProfile/:email/:personsProfile',
    name: 'ClientProfile',
    component: ClientProfile,
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
    component: ClientReservationForm,
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
    path: '/availableCabins/:email',
    name: 'ClientCabins',
    component: ClientCabins,
    props: true,
    beforeEnter: (to, from,next) => {
      store.dispatch('refreshToken')
      if(localStorage.token == 'empty' || localStorage.role !='CLIENT' || localStorage.role=='ADMIN' || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       } 
  },
  
  {
    path: '/reservations/:email',
    name: 'ClientReservations',
    component: ClientReservations,
    props: true,
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
    path: '/availableQuickReservations/:email',
    name: 'ClientAvailableQuickReservations',
    component: ClientAvailableQuickReservations,
    props: true,
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
    path: '/cabin/:email/:cabinName',
    name: 'Cabin',
    component: ClientCabinProfile,
    props: true,
    beforeEnter: (to, from,next) => {
      store.dispatch('refreshToken')
      if(localStorage.token == 'empty' || localStorage.role =='CABINOWNER' ||  localStorage.role =='FISHINGINSTRUCTOR' || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       } 
  },
  {
    path: '/cabin/:cabinName',
    name: 'CabinProfileUnindentifiedUser',
    component: ClientCabinProfile,
    props: true,
    beforeEnter: (to, from,next) => {
          next()
       } 
  },
  {
    path: '/boat/:email/:boatId',
    name: 'Boat',
    component: ClientBoatProfile,
    props: true,
    beforeEnter: (to, from,next) => {
      store.dispatch('refreshToken')
      if(localStorage.token == 'empty' || localStorage.role =='CABINOWNER' ||  localStorage.role =='FISHINGINSTRUCTOR' || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       } 
  },
  {
    path: '/boat/:boatId',
    name: 'BoatProfileUnindentifiedUser',
    component: ClientBoatProfile,
    props: true,
    beforeEnter: (to, from,next) => {
           next()
       } 
  },
  {
    path: '/adventure/:email/:adventureId',
    name: 'Adventure',
    component: ClientAdventureProfile,
    props: true,
    beforeEnter: (to, from,next) => {
      store.dispatch('refreshToken')
      if(localStorage.token == 'empty' || localStorage.role =='CABINOWNER' ||  localStorage.role =='FISHINGINSTRUCTOR' || localStorage.logged == false){
           next('/')
         }else {
           next()
         }
       } 
  },
  {
    path: '/adventure/:adventureId',
    name: 'AdventureProfileUnindentifiedUser',
    component: ClientAdventureProfile,
    props: true,
    beforeEnter: (to, from,next) => {
          next()
       } 
  },
  {
    path: '/evaluation/:email/:entity/:reservationId',
    name: 'ClientEvaluationForm',
    component: ClientEvaluationForm,
    props: true,
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
    path: '/complaint/:email/:entity/:reservationId',
    name: 'ClientComplaintForm',
    component: ClientComplaintForm,
    props: true,
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
      if(localStorage.token == 'empty' || localStorage.role !='CABINOWNER'   || localStorage.logged == false){
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





