<template>
  <FishingInstructorNavbar :username=email />
  <div>
    <VCalendar :role="'instructor'"></VCalendar>
  </div>
</template>

<script>
 import axios from "axios";
import VCalendar from "@/components/Calendar.vue";
import FishingInstructorNavbar from './FishingInstructorNav.vue'



   export default{
       props: ['role'],
        components: {  FishingInstructorNavbar , VCalendar },
     data(){
       return{
         email: '',
          fishingInstructorDtos: {
               id: null,
              username: '',
              password: '',
              firstname: '',
             lastname: '',
              phoneNum: '',
             address: {
                longitude: 0,
                latitude: 0,
                country: '',
                city: '',
                streetAndNum: ''
             },
             registrationReason: '',
             role: '',
             rating: '',
            availablePeriodDtoSet: [{
                 id: null,
                startDate: null,
                endDate: null,
                instructorUsername: ''
            }]

          },
         adventureLoaded: false,
         currentImageUrl: 'logoF1.png',
         imageIndex: 0,
         maxImageIndex: 0,
         fishingInstructorDto: {
         
            username: '',
          
          rating: 0.0
         },
         startDate: null,
         endDate: null,
       
         
       
       }
     },
     mounted() {
       this.email = this.$route.params.email;
       
       this.fishingInstructorDtos.username= this.email
            axios.post("http://localhost:8081/instructors/findByUsername",this.fishingInstructorDtos)
               .then(response => {
                        this.fishingInstructorDtos=response.data
                        console.log("userrrnameee iz "+this.fishingInstructorDtos.username)
                        
                   
                      
              })


    
     },
    
  }

</script> 

<style lang="postcss" scoped>
::-webkit-scrollbar {
  width: 0px;
}
::-webkit-scrollbar-track {
  display: none;
}
/deep/ .custom-calendar.vc-container {
  --day-border: 1px solid #b8c2cc;
  --day-border-highlight: 1px solid #b8c2cc;
  --day-width: 90px;
  --day-height: 90px;
  --weekday-bg: #f8fafc;
  --weekday-border: 1px solid #eaeaea;
  border-radius: 0;
  width: 100%;
  & .vc-header {
    background-color: #f1f5f8;
    padding: 10px 0;
  }
  & .vc-weeks {
    padding: 0;
  }
  & .vc-weekday {
    background-color: var(--weekday-bg);
    border-bottom: var(--weekday-border);
    border-top: var(--weekday-border);
    padding: 5px 0;
  }
  & .vc-day {
    padding: 0 5px 3px 5px;
    text-align: left;
    height: var(--day-height);
    min-width: var(--day-width);
    background-color: white;
    &.weekday-1,
    &.weekday-7 {
      background-color: #eff8ff;
    }
    &:not(.on-bottom) {
      border-bottom: var(--day-border);
      &.weekday-1 {
        border-bottom: var(--day-border-highlight);
      }
    }
    &:not(.on-right) {
      border-right: var(--day-border);
    }
  }
  & .vc-day-dots {
    margin-bottom: 5px;
  }
}
</style>