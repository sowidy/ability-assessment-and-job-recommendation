<template>
  <div class="swiper-container" ref="cur">
    <div class="swiper-wrapper">
      <div class="swiper-slide" v-for="i in list" :key="i.id">
        <div class="tk-feedbackitem" >
          <figure>
            <img
              :src="i.avatar || 'defaultStuAvatar.jpg'"
              alt=""
            />
          </figure>
          <div class="tk-feedbackinfo">
            <h4>{{i.name}}</h4>
            <!-- <h6><a href="">Designer @ miller &corp</a></h6> -->
            <p>{{i.remark}}</p>
          </div>
        </div>
      </div>
    </div>
    <!-- 如果需要分页器 -->
    <!-- <div class="swiper-pagination"></div> -->

    <!-- 如果需要导航按钮 -->
    <!-- <div class="swiper-button-prev"></div>
    <div class="swiper-button-next"></div> -->
  </div>
</template>

<script>
//引入Swiper
import Swiper from "swiper";
export default {
  name: "Remark",
  props: ["list"],
  watch: {
    list: {
      //立即监听：不管你数据有没有变化，我上来立即监听一次
      //为什么watch监听不大list：因为这个数据从来没有发生变化（数据是父亲给的，父亲给的时候就是一个对象，对象里面该有的数据都是有的）
      immediate: true,
      handler() {
        //只能监听到数据已经有了，但是v-for动态渲染结构我们还是没有办法确定的，因此还是需要用nextTick
        this.$nextTick(() => {
          var mySwiper = new Swiper(this.$refs.cur, {
            autoplay: true,
            loop: true,
            // 如果需要分页器
            // pagination: {
            //   el: ".swiper-pagination",
            //   //点击小球的时候也切换图片
            //   clickable: true,
            // },
            // 如果需要前进后退按钮
            navigation: {
              nextEl: ".swiper-button-next",
              prevEl: ".swiper-button-prev",
            },
            slidesPerView: 3,
            spaceBetween: 20,
          });
        });
      },
    },
  },
};
</script>

<style lang='less' scoped>
.swiper-container {
  height: 260px;
  width: 80%;
  // border: 1px rgb(0, 165, 71) solid;
  // padding:  10% 0;
}
.swiper-slide {
  // border: 1px seagreen solid;
  // margin: 0 auto;
  .tk-feedbackitem {
    max-width: 526px;
    // height: 80%;
    display: flex !important;
    padding: 30px 0;
    width: 100%;
    figure {
      max-width: 60px;
      height: 60px;
      margin: 0 20px 0 0;
      flex: none;
      // border: 1px solid sandybrown;
      img {
        object-fit: cover;
        max-width: 60px;
        height: 60px;
      }
    }
    .tk-feedbackinfo {
      // border: 1px solid rgb(15, 11, 8);
      padding: 30px;
      height: 150px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.04),
        0px 8px 16px rgba(0, 0, 0, 0.08);
      max-width: 446px;
      width: 100%;
      border-left: 2px solid #37f;
      background-color: #fff;
      h4 {
        color: #0a0f26;
        margin: 0;
      }
      p {
        margin: 20px 0 0;
      }
    }
  }
}
</style>
