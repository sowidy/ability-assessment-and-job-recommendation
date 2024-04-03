<template>
  <div class="bg">
    <Header class="fix-head"></Header>
    <!--首页轮播图-->
    <div style="height: 100%; width: 100%; position: relative">
      <video
        src="https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/%E7%99%BE%E5%BA%A6%E6%99%BA%E8%83%BD%E4%BA%91%E5%8D%83%E5%B8%86%E5%A4%A7%E6%A8%A1%E5%9E%8B%E5%B9%B3%E5%8F%B0ModelBuilder.mp4"
        autoplay
        loop
        muted
        style="
          width: 100%;
          height: 100%;
          object-fit: cover;
          position: absolute;
          top: 0;
          left: 0;
          z-index: 0;
        "
      ></video>
      <div class="indexContain">
        <div class="cardBox">
          <el-carousel trigger="click" height="500px" style="position: sticky">
            <el-carousel-item v-for="(item, key) in crouselImg" :key="key">
              <img :src="item.img" class="boxImg" />
            </el-carousel-item>
          </el-carousel>
        </div>
      </div>
    </div>
    <!--    热门企业-->
    <!-- <div style="background-color: rgba(0, 167, 172, 0.15)"> -->
    <div class="division">
      <h2>热门企业</h2>
      <h3 style="color: #888; font-weight: 400">--- Hot ---</h3>
    </div>
    <Carsousel :list="hotTitle" />
    <!-- </div> -->

    <!--推荐-->
    <!-- <div class="division" v-if="true">
      <h3>{{ isHr ? "热门职位" : "热门候选人" }}</h3>
      <h3 style="color: #888; font-weight: 400">---- Hot ----</h3>
    </div> -->
    <!-- 推荐职位v-if="identity != 'enterprise'" -->
    <el-divider></el-divider>

    <!-- <div class="hot">
      <div class="hot-student">
        <el-card>
          <div class="title">最受欢迎的学生</div>
          <div>
            <el-card>
              <div><span>陈云</span> <span>北京大学</span> <span>计算机科学</span></div>
            </el-card>
            <el-card>
              <div>body</div>
            </el-card>
            <el-card>
              <div>body</div>
            </el-card>
          </div>
        </el-card>
      </div>
      <div class="hot-enterprise">
        <el-card>
          <div class="title">最受欢迎的职位</div>
          <div>
            <el-card>
              <div>body</div>
            </el-card>
            <el-card>
              <div>body</div>
            </el-card>
            <el-card>
              <div>body</div>
            </el-card>
          </div>
        </el-card>
      </div>
    </div> -->
    <div class="section-padding">
      <div class="container">
        <div class="section-title">
          <h2 class="title">热门职位</h2>
          <h3 style="color: #888; font-weight: 400">--- Hot ---</h3>
        </div>
        <div class="job-list-wrap">
          <JobCard :list="hotRecomment"></JobCard>
        </div>
        <div class="text-center mt-4 mt-lg-5">
          <el-button round type="primary"
            ><router-link class="link" to="/search"
              >查看更多职位</router-link
            ></el-button
          >
        </div>
      </div>
    </div>
    <!--推荐人才-->
    <div class="recommentJob" v-if="identity == 'enterprise'">
      <div class="container">
        <div class="leftbar">
          <div class="col">
            <div
              class="image aos-init aos-animate"
              data-aos="fade-right"
              data-aos-delay="50"
              data-aos-duration="500"
            >
              <img
                src="https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/candidates-12.jpg"
                alt=""
                style=""
              />
            </div>
          </div>
          <div class="col">
            <div
              class="image aos-init aos-animate"
              data-aos="fade-down"
              data-aos-delay="70"
              data-aos-duration="700"
            >
              <img
                src="https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/recruitment.webp"
                alt=""
              />
            </div>
          </div>
          <div class="col">
            <div
              class="image aos-init aos-animate"
              data-aos="fade-left"
              data-aos-delay="70"
              data-aos-duration="700"
            >
              <img
                src="https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/candidates-7.jpg"
                alt=""
              />
            </div>
          </div>
          <div class="col">
            <div
              class="image aos-init aos-animate"
              data-aos="fade-right"
              data-aos-delay="70"
              data-aos-duration="700"
            >
              <img
                src="https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/candidates-11.jpg"
                alt=""
              />
            </div>
          </div>
          <div class="col">
            <div
              class="image aos-init aos-animate"
              data-aos="fade-up"
              data-aos-delay="80"
              data-aos-duration="800"
            >
              <img
                src="https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/candidates-9.jpg"
                alt=""
              />
            </div>
          </div>
          <div class="col">
            <div
              class="image aos-init aos-animate"
              data-aos="fade-left"
              data-aos-delay="90"
              data-aos-duration="900"
            >
              <img
                src="https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/candidates-8.jpg"
                alt=""
              />
            </div>
          </div>
        </div>
        <div class="rightbar">
          <div class="featured-candidates-item">
            <div class="featured-candidates-content">
              <h3>为你的工作寻找有特色的人才</h3>
              <p>开启人才招募之旅，与您一起成就梦想！</p>
            </div>
            <div class="featured-candidates-box">
              <StuCard :list="hotWorker"></StuCard>
            </div>
          </div>
        </div>
      </div>
      <div class="featured-candidates-shape">
        <img
          src="https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/shape-1.png"
          alt=""
        />
      </div>
    </div>
    <!--用户反馈列表-->
    <!-- <div class="tk-main-section">
      <div class="container">
        <div class="row">
          <div class="col-md-12">
            <div class="tk-feedbackwrap">
              <div class="tk-main-title-holder" data-aos="fade-right">
                <div class="tk-maintitle">
                  <h2>认真、积极反馈让我们充满动力</h2>
                </div>
                <div class="tk-main-description">
                  <p>挑战我们，让我们迸发更强动力！</p>
                </div>
              </div>
              <el-button
                round
                data-aos="fade-left"
                type="primary"
                @click="$router.push('/login')"
                >加入我们<i class="el-icon-right"></i
              ></el-button>
            </div>
          </div>
        </div>
      </div>
      <remark
        :list="remarkList"
        data-aos="fade-up"
        data-aos-delay="70"
        data-aos-duration="800"
        data-aos-anchor-placement="top-bottom"
      ></remark>
    </div> -->
    <!--关于我们-->
    <div class="aboutus" data-aos="fade-up">
      <div id="aboutusInfo">
        <h2>关于我们</h2>
        <p>面向互联网求职招聘，提供更多的实习和工作机会</p>
        <p>即刻起，点赞你的生活，从这一份工作开始。</p>
      </div>
    </div>
    <Footer></Footer>
  </div>
</template>

<script>
import "aos/dist/aos.css";
import AOS from "aos";
import { getIdentity } from "@/utils/token";
export default {
  data() {
    return {
      crouselImg: [
        // {
        //   img: "https://sxsimg.xiaoyuanzhao.com/3C/09/3C4A275077015CBF398443CC21774709.png",
        // },
        {
          img: "slogan.png",
        },
        {
          img: "slogan2.png",
        },
        {
          img: "slogan3.png",
        },
        // {
        //   img: "https://sxsimg.xiaoyuanzhao.com/FD/0C/FDBBBD21A98136E3054ADDD432A5020C.png",
        // },
      ],
      hotTitle: [],
      hotRecomment: [],
      remarkList: [],
      hotWorker: [],
      isHr: "false",
      value: 5,
    };
  },
  mounted() {
    AOS.init();
    this.getHotTitle();
    this.getHotRecomment();
    // this.getRemarkList();
    this.getHotWorker();
  },
  computed: {
    identity() {
      return getIdentity() || "student";
    },
  },
  methods: {
    getHotTitle() {
      this.$API.enterprise.reGetHotTitle().then((resp) => {
        // console.log(resp.data.data);
        this.hotTitle = resp.data.data;
      });
    },
    getHotRecomment() {
      this.$API.enterprise.reGetHotRecomment("enterprise").then((resp) => {
        this.hotRecomment = resp.data.data.slice(0, 4);
        // console.log(this.hotRecomment, "###");
      });
    },
    // getRemarkList() {
    //   this.$API.user.getRemarkList().then((resp) => {
    //     // console.log(resp.data);
    //     if (resp.data.code === 0) {
    //       this.remarkList = resp.data.data.map((item) => {
    //         if (typeof item === "string" && item.length > 0) {
    //           return item.substring(1);
    //         } else {
    //           return item;
    //         }
    //       });
    //     }
    //   });
    // },
    getHotWorker() {
      this.$API.user
        .getAllStudent()
        .then((resp) => {
          this.hotWorker = resp.data.data.slice(0, 3);
          // console.log(this.hotWorker,'@@@');
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style lang="less" scoped>
.bg {
  // background: #ebf4ff;;
  // background: url(https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/cta-employee-list-v1.jpg)
  //   no-repeat;
  // .fix-head{
  //   width: 100%;
  //   height: 60px;
  //   position: fixed;
  // }
}

.hot {
  // border: 1px saddlebrown solid;
  margin: 0 auto;
  max-width: 1200px;
  display: flex;
  justify-content: space-around;
  &-student {
    // border: 1px rgb(105, 74, 52) solid;
    height: 400px;
    width: 48%;
    .title {
      border: none;
      box-sizing: border-box;
      height: 80px;
      line-height: 55px;
      padding: 15px;
    }
    .el-card {
      border-width: 1px;
      border-style: solid;
      // border-color: #ededed transparent;
      border-left: none;
      border-right: none;
      box-shadow: none;
    }
  }
  &-enterprise {
    // border: 1px rgb(68, 30, 3) solid;
    height: 400px;
    width: 48%;
    .title {
      border: none;
      box-sizing: border-box;
      height: 80px;
      line-height: 55px;
      padding: 15px;
    }
    .el-card {
      border-width: 1px;
      border-style: solid;
      // border-color: #ededed transparent;
      border-left: none;
      border-right: none;
      box-shadow: none;
    }
  }
}
.section-padding {
  // border: 1px saddlebrown solid;
  // padding-top: 120px;
  padding-bottom: 150px;
  padding-top: 30px;
  // background: #fff8ec;

  .container {
    max-width: 1200px;
    margin: 0 auto;
    .section-title {
      width: 100%;
      margin-bottom: 70px;
      text-align: center;
      .title:not(:last-child) {
        margin-bottom: 10px;
      }
      p {
        font-size: 16px;
      }
    }
    .job-list-wrap {
      padding: 1px;
      cursor: pointer;
    }
    .text-center {
      text-align: center !important;
      position: relative;
      .el-button {
        position: absolute;
        top: 50px;
        right: 45%;
        .link {
          text-decoration: none;
          color: white;
        }
      }
    }
  }
}

.recommentJob {
  position: relative;

  .container {
    overflow: hidden;
    width: 1200px;
    height: 950px;
    margin: 0 auto;
    display: flex;

    .leftbar {
      // height: 100%;
      width: 50%;
      display: flex;
      flex: 0 0 auto;
      flex-wrap: wrap;

      .col {
        flex: 0 0 auto;
        width: 29%;
        margin: 0 2%;

        .aos-animate {
          opacity: 1;
          transform: translateZ(0);
        }

        img {
          max-width: 100%;
          height: 100%;
          display: inline-block;
          border-radius: 100px;
        }
      }

      .col:nth-child(1) .image {
        margin-bottom: 25px;
        position: relative;
        // height: 400px;
        top: 150px;
      }

      .col:nth-child(2) .image {
        position: relative;
        top: 30px;
        margin-bottom: 25px;
      }

      .col:nth-child(3) .image {
        // height: 300px;
        margin-bottom: 25px;
        position: relative;
        top: 110px;
      }

      .col:nth-child(4) .image {
        position: relative;
        top: -40px;
      }
      .col:nth-child(5) .image {
        position: relative;
        top: -120px;
      }

      .col:nth-child(6) .image {
        position: relative;
        top: -80px;
      }
    }

    .rightbar {
      width: 50%;
      // border: 1px solid rgb(36, 22, 9);
      height: 100%;

      .featured-candidates-content {
        margin-bottom: 35px;
        position: relative;

        h3 {
          font-size: 42px;
          margin-bottom: 15px;
          line-height: 1.4;
        }

        p {
          margin-bottom: 0px;
          line-height: 1.8;
          color: var(--paragraph-color);
          font-weight: 400;
          font-family: var(--main-font-family);
          font-size: var(--font-size);
        }
      }

      .featured-candidates-box {
        background-color: #fff;
        -webkit-box-shadow: 0 0 15px 0 rgba(0, 0, 0, 0.1);
        box-shadow: 0 0 15px 0 rgba(0, 0, 0, 0.1);
        // padding: 30px;
        border-radius: 10px;

        margin: 0 5%;
        margin-bottom: 25px;
      }
    }
  }

  .featured-candidates-shape {
    position: absolute;
    right: 90px;
    top: -25px;
    z-index: -1;
  }
}

.tk-main-section {
  padding: 100px 0;

  .container {
    max-width: 1320px;
    // border: 1px saddlebrown solid;
    margin: 0 auto;

    .row {
      display: flex;
      // flex-wrap: wrap;
      margin: 1.5rem;
      // border: 1px rgb(79, 182, 27) solid;
      .col-md-12 {
        flex: 0 0 auto;
        width: 100%;

        .tk-feedbackwrap {
          display: flex;
          align-items: center;
          justify-content: space-between;
          position: relative;

          .tk-main-title-holder {
            max-width: 674px;
            padding-bottom: 40px;

            .tk-maintitle {
              h2 {
                font-size: 42px;
              }
            }

            .tk-main-description {
              padding-top: 20px;
            }
          }

          .el-button {
            height: 48px;
            position: absolute;
            // top: 100px;
            right: 100px;
          }
        }
      }
    }
  }
}
</style>
<style scoped>
body {
  background: #ededed;
}
.indexContain {
  margin-bottom: 0;
  padding-top: 70px;
  width: 100%;
  height: 100%;
  height: 620px;
  border-bottom: 1px solid #ededed;
  background: #fff;
}

.cardContain {
  /* width: 100%; */
  /* height: 50%; */
  background: #fff;
}

.cardBox {
  position: relative;
  width: 1200px;
  margin: 20px auto 30px auto;
  box-shadow: 0 10px 15px #888;
  border-radius: 6px;
}

.boxImg {
  width: 100%;
  height: 100%;
  border-radius: 6px;
}

.division {
  width: 100%;
  margin: 30px auto;
  text-align: center;
  /* padding-left: 10px; */
  /* color: #5a5a5a; */
}

.aboutus {
  /* background: linear-gradient(to bottom right, #74c7ff, #a0ffc8); */

  text-align: center;
  width: 100%;
  height: 400px;
  /* background: url("~@/assets/about-us.webp") no-repeat; */
  background-size: 100% 100%;
  filter: grayscale(70%);
  opacity: 0.7;
  /* color: white; */
  font-weight: 600;
  padding-top: 60px;
}

.aboutus p {
  margin-top: 30px;
  font-size: 18px;
}

#aboutusInfo {
  margin-top: 80px;
  animation-delay: 1s;
}
</style>