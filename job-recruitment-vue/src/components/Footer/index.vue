<template>
  <div class="footer">
    <!-- <a href=""><img src="" /><span></span></a>
    <a href=""><img src="" /><span></span></a> -->
  </div>
</template>

<script>
export default {
  name: "Footer",
};
</script>

<style scoped>
.footer img {
  width: 25px;
  height: 25px;
  margin-right: 10px;
}

.footer span {
  margin-right: 20px;
}
.footer {
  text-align: center;
  width: 100%;
  height: 60px;
  /* background: #66b1ff; */
      background-image: linear-gradient(
      225deg,
      rgba(0, 181, 188, 0.89) 0%,
      rgba(62, 142, 240, 0.89) 100%
    );
  padding-top: 20px;
}

.footer a {
  color: white;
  text-decoration: none;
}
</style>