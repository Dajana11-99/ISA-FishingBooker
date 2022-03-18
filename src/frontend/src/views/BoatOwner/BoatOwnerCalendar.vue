<template>
  <BoatOwnerNav :username=email />

</template>

<script>
import BoatOwnerNav from './BoatOwnerNav.vue'

   export default{
     components:{
       BoatOwnerNav,
     },
     data(){
       return{
         email: '',  

       }
     },
     mounted() {
       this.email = this.$route.params.email

     },
  }

</script> 

<style>

</style>
