<template>
  <div>
    <Header></Header>
    <div class="main">
      <header class="header_01 header_inner">
        <div class="header_main header_job_single_main">
          <div class="header_btm header_job_single">
            <div class="header_job_single_inner container">
              <div class="poster_company">
                <img :src="enterprise.logo || 'defaultEnAvatar.jpg'" alt="" />
                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="like ? '已收藏' : '点击收藏职位'"
                  placement="top"
                >
                  <span class="like" @click="addOrDeleteFavorite">
                    <transition
                      enter-active-class="animate__animated animate__bounce"
                    >
                      <i
                        v-if="like == true"
                        class="el-icon-star-on"
                        style="color: #e6a23c"
                      />
                    </transition>
                    <i v-if="like == false" class="el-icon-star-off" />
                  </span>
                </el-tooltip>
              </div>
              <div class="poster_details">
                <h2>
                  {{ enterprise.title
                  }}<el-tag
                    :type="
                      enterprise.enterpriseAuth == 0 ? 'success' : 'warning'
                    "
                    >{{
                      enterprise.enterpriseAuth == 0 ? "已认证" : "未认证"
                    }}</el-tag
                  >
                </h2>
                <h5>企业经理：{{ enterprise.hiringManager }}</h5>
                <ul>
                  <li>
                    <a href="#"
                      ><i class="el-icon-location"></i
                      >{{ enterprise.address }}</a
                    >
                  </li>
                </ul>
              </div>
              <div class="poster_action">
                <!-- <el-button type="primary" round>跳转</el-button> -->

                <el-card>
                  <div>
                    <div
                      class="salary"
                      style="
                        text-align: center;
                        color: #66b1ff;
                        font-size: 20px;
                      "
                    >
                      薪 资
                    </div>
                    <br />
                    <span
                      >{{ enterprise.salaryMin }}——{{
                        enterprise.salaryMax
                      }}/月</span
                    >
                  </div>
                </el-card>
              </div>
            </div>
          </div>
        </div>
      </header>
      <main>
        <div class="single_job">
          <div class="container">
            <div class="row">
              <el-col :span="9" class="col-md-9">
                <div class="col-md-12 single_job_main">
                  <!-- <h2 class="require">
                    学历要求:
                    <p style="display:inline">
                      {{ enterprise.titleRequire }}
                    </p>
                  </h2> -->

                  <h2>职位描述</h2>
                  <p>
                    {{ enterprise.titleDescription }}
                  </p>
                </div>
                <div class="simple">
                  <h2>其他相似职位</h2>
                  <!-- <div class="simple-item"> -->
                  <JobCard :list="hotTitle"></JobCard>
                  <!-- </div> -->
                </div>
                <!-- <iframe
                :src="enterprise.titleLink"
                id="inlineFrameExample"
                title="Inline Frame Example"
                width="600"
                height="400"
              ></iframe> -->
              </el-col>
              <el-col :span="3" class="col-md-3">
                <el-card class="card" style="background-color: #f5f7fc">
                  <div slot="header">职位总结</div>
                  <div>
                    <ul>
                      <li>
                        <span
                          ><i class="el-icon-price-tag"></i
                          >{{ enterprise.titleRequire }}</span
                        >
                      </li>
                      <li>
                        <span
                          ><i class="el-icon-location"></i
                          >{{ enterprise.address }}</span
                        >
                      </li>
                      <li>
                        <span
                          ><i class="el-icon-user-solid"></i
                          >{{ enterprise.hiringManager }}</span
                        >
                      </li>
                      <li>
                        <span
                          ><i class="el-icon-phone"></i
                          >{{ enterprise.phone }}</span
                        >
                      </li>
                      <li>
                        <span
                          ><i class="el-icon-wallet"></i
                          >{{ enterprise.salaryMin }}-{{
                            enterprise.salaryMax
                          }}</span
                        >
                      </li>
                    </ul>
                  </div>
                </el-card>
                <el-card class="card" style="background-color: #f5f7fc">
                  <div slot="header">标签</div>
                  <div>
                    <el-tag type="success">PHP</el-tag>
                    <el-tag type="success">Java</el-tag>
                    <el-tag type="success">Python</el-tag>
                  </div>
                </el-card>
              </el-col>
            </div>
          </div>
        </div>
      </main>
    </div>

    <Footer></Footer>
  </div>
</template>

<script>
export default {
  name: "SingleJob",
  data() {
    return {
      like: "",
      hotTitle: [],
      queryId: "",
      enterprise: "",
    };
  },
  watch: {
    $route(to, from) {
      if (to.query.id !== from.query.id) {
        this.queryId = to.query.id;
        this.getEnterpriseDetail();
      }
    },
  },
  beforeMount() {
    this.queryId = this.$route.query.id;
  },
  mounted() {
    this.getHotTitle();
    this.getEnterpriseDetail();
  },
  methods: {
    getHotTitle() {
      this.$API.enterprise.reGetHotTitle(2).then((resp) => {
        this.hotTitle = resp.data.data;
      });
    },
    getEnterpriseDetail() {
      this.$API.enterprise
        .findHotById(this.queryId, "Enterprise")
        .then((resp) => {
          if (resp.data.code == 0) {
            this.enterprise = resp.data.data;
            // console.log(this.enterprise);
            this.checkFavoriteState();
          } else this.$router.push("/");
        })
        .catch((err) => {
          console.log(err);
        });
    },
    checkFavoriteState() {
      this.$API.user
        .checkFavorite(this.$route.query.id)
        .then((resp) => {
          if (resp.data.code == 0) {
            this.like = resp.data.data;
            console.log(this.like);
          } else this.like = false;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    addOrDeleteFavorite() {
      if (!this.like) {
        this.$API.user
          .addFavoriteTitle(this.$route.query.id)
          .then((resp) => {
            if (resp.data.code == 0) {
              this.$notify({
                type: "success",
                message: "收藏成功",
              });
              this.like = true;
            } else this.$notify.error(resp.data.message);
          })
          .catch((err) => {
            console.log(err);
          });
      } else {
        this.$API.user
          .deleteFavoriteTitle(this.$route.query.id)
          .then((resp) => {
            if (resp.data.code == 0) {
              this.$notify({
                type: "success",
                message: "取消收藏",
              });
              this.like = false;
            } else this.$notify.error(resp.data.message);
          })
          .catch((err) => {
            console.log(err);
          });
      }
    },
  },
};
</script>

<style lang="less" scoped>
div {
  // margin: 0 auto;
}
.main {
  background-image: url("~@/assets/bg.png");
  background-position: center center;
  background-color: #f2f2f2;
  .header_main {
    position: relative;
    // overflow: hidden;
    .header_btm {
      // border: 1 solid sandybrown;
      // min-height: 240px;

      // background-color: rgba(188, 165, 222, 0.9);
      // background-size: 100% 100%;

      // background-blend-mode: multiply;
      // background-size: 1800px;
      position: relative;
      // padding-top: 1%;
      padding-bottom: 0px;
      background-image: url("~@/assets/bg-4.png");
      // background: url(https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/cta-employee-list-v1.jpg)
      // no-repeat;
      background-attachment: 100% 100%;
      background-repeat: no-repeat;
      background-position: center;
      // padding: 0 6%;
      .container {
        // border: 1px solid sandybrown;
        width: 100%;
        margin: 0 auto;
        max-width: 1200px;
        display: flex;
        .poster_company {
          // border: 1px solid sandybrown;
          // background: #fff;
          // border-radius: 10px;
          width: 130px;
          padding-top: 5%;
          // margin-right: 15px;
          // margin-right: 30px;
          height: 100%;
          img {
            max-width: 100%;
            border-radius: 10px;

            // float: right;
          }
          .like {
            margin-top: 1px;
            cursor: pointer;
            font-size: 50px;
            display: inline-block;
            margin-left: 30%;
          }
        }
        .poster_details {
          color: black;
          // border: 1px solid sandybrown;
          width: 60%;
          padding-top: 5%;
          padding-left: 5%;
          h2 {
            margin-bottom: 5%;
            font-size: 28px;
            .el-tag {
              height: 100%;
              text-align: center;
              margin-left: 5%;
            }
          }
          h5 {
            color: black;
          }
          ul {
            padding: 0;
            margin: 5% 0;
            li {
              display: inline-block;
              padding-right: 20px;
              a {
                text-decoration: none;
                color: black;
              }
            }
          }
        }
        .poster_action {
          position: absolute;
          top: 100px;
          right: 250px;
        }
      }
    }
  }
  main {
    // height: 1200px;
    .single_job {
      padding: 60px 6%;
      // background-image: url("~@/assets/bg.png");
      // background-color: #f2f2f2;
      // background-position: center center;
      // padding-left: 6%;
      // padding-right: 6%;
      .container {
        width: 100%;
        max-width: 1200px;
        margin: 0 auto;
        .row {
          display: -ms-flexbox;
          display: flex;
          -ms-flex-wrap: wrap;
          flex-wrap: wrap;
          width: 100%;
          // margin-right: -15px;
          // margin-left: -15px;
          .col-md-9 {
            // border: 1px saddlebrown solid;
            width: 70%;
            height: 500px;
            height: 100%;
            // padding: 3% 5%;
            h2 {
              font-size: 24px;
              margin-bottom: 25px;
            }
            .require {
              margin-bottom: 20px;
              font-size: 20px;
              font-weight: 500;
            }
            p {
              margin-top: 0;
              margin-bottom: 5%;
            }
            .simple {
              margin-top: 20%;
              width: 95%;
            }
            iframe {
              border: 1px solid black;
              width: 100%; /* takes precedence over the width set with the HTML width attribute */
            }
          }
          .col-md-3 {
            // border: 1px saddlebrown solid;
            width: 30%;
            padding: 0 1%;
            // overflow: hidden;
            .card {
              // border-radius: 20px;
              margin: 2% 0;

              box-shadow: none;
              /deep/ .el-card_body {
                background: #f5f7fc;
              }
              li {
                text-align: center;
                // margin: 10% 0;
                list-style-type: none;
                // float: left;
                width: 100%;
                span {
                  margin: 5% 2%;
                  width: 100%;
                  float: left;
                  text-align: left;
                  i {
                    margin: 0 10px;
                  }
                  // border: 1px sienna solid;
                }
              }
              .el-tag {
                margin: 2% 10%;
              }
            }
          }
        }
      }
    }
  }
}
</style>