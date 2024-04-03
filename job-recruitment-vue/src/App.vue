<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "App",
  mounted() {
    // this.$store.dispatch('user/getUserInfo');
  },
};
</script>

<style lang='less'>
* {
  margin: 0;
  padding: 0;
}

// .el-button--primary {
//   color: #92a8be;
//   background: rgba(0, 181, 188, 0.9) !important;
//   // background-color: rgb(240, 248, 255) !important;
//   border-color: rgb(148, 197, 238);
//   font-size: 14px;
//   &:hover {
//     color: rgb(240, 248, 255);
//     background-color: rgb(148, 197, 238);
//     border-color: rgb(148, 197, 238);
//   }
//   &:active {
//     color: rgb(240, 248, 255);
//     background-color: rgb(148, 197, 238);
//     border-color: rgb(148, 197, 238);
//   }
// }
</style>
