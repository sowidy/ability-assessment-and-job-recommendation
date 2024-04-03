<template>
  <div>
    <Header></Header>
    <div class="main">
      <header class="header_01 header_inner">
        <div class="header_main header_job_single_main">
          <div class="header_btm header_job_single">
            <div class="header_job_single_inner container">
              <div class="poster_company">
                <img :src="userInfo.avatar || 'defaultEnAvatar.jpg'" alt="" />
              </div>
              <div class="poster_details">
                <h2>
                  {{ userInfo.name }}
                </h2>
                <!-- <h5>企业经理：{{ enterprise.hiringManager }}</h5> -->
                <ul>
                  <li>
                    <a href="#"
                      ><i class="el-icon-location"></i>{{ userInfo.school }}</a
                    >
                  </li>
                </ul>
              </div>
              <div class="poster_action">
                <div class="resume-card">
                  <!-- <el-button type="primary" round>下载简历</el-button> -->
                  <div class="resumeViewSection" v-if="resumeDetail">
                    <!-- <h2 class="resumeViewSection__title">简历</h2> -->
                    <div
                      class="resumeViewItem resumeViewItem__resumeAttachment"
                    >
                      <div class="fileIcon">
                        <file-icon :file-type="resumeFileType"></file-icon>
                      </div>
                      <div class="content">
                        <h3>{{ resumeDetail.originName }}</h3>
                        <time>
                          上传时间：{{ resumeDetail.createTime | formatDate }}
                        </time>
                      </div>
                      <div class="download-link">
                        <a ref="resumeDownloadLink" @click="download">
                          <i class="el-icon-download"></i>
                        </a>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- <el-button type="primary" round>跳转</el-button> -->
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
                  <h2>工作经验</h2>
                  <p>{{ userInfo.experience }}</p>
                  <h2>技能展示</h2>
                  <p>{{ userInfo.skills }}</p>
                </div>
              </el-col>
              <el-col :span="3" class="col-md-3">
                <el-card class="card" style="background-color: #f5f7fc">
                  <div slot="header">个人总结</div>
                  <div>
                    <ul>
                      <li>
                        <span><i class="el-icon-phone"></i
                        >{{ userInfo.phone }}</span>
                      </li>
                      <li>
                        <span><i class="el-icon-connection"></i
                        >{{ userInfo.email }}</span>
                      </li>
                      <li>
                        <span><i class="el-icon-s-promotion"></i
                        >{{ userInfo.education }}</span>
                      </li>
                      <li>
                        <span><i class="el-icon-school"></i
                        >{{ userInfo.school }}-{{ userInfo.major }}</span>
                      </li>
                    </ul>
                  </div>
                </el-card>
                <el-card class="card" style="background-color: #f5f7fc">
                  <div slot="header">技术栈</div>
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
import FileIcon from "@/components/File-Icon";
export default {
  name: "SingleStudent",
  data() {
    return {
      resumeDetail: "",
      queryId: "",
      userInfo: "",
    };
  },
  components: { FileIcon },
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
  computed: {
    resumeFileType() {
      const pathArr = this.resumeDetail.originName.split(".");
      return pathArr[pathArr.length - 1];
    },
  },
  async mounted() {
    await this.getStudentDetrail();
    this.getResumeDetail();
  },
  methods: {
    getHotTitle() {
      this.$API.enterprise.reGetHotTitle().then((resp) => {
        this.hotTitle = resp.data.data.slice(0, 2);
      });
    },
    async getStudentDetrail() {
      await this.$API.user
        .findStudentById(this.queryId)
        .then((resp) => {
          if (resp.data.code === 0) {
            this.userInfo = resp.data.data;
            // console.log(this.userInfo);
          } else console.log(resp.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getResumeDetail() {
      // console.log(this.userInfo, "userinfo");
      if (this.userInfo.resumeId) {
        this.$API.user
          .getResumeById(this.userInfo.resumeId)
          .then((resp) => {
            console.log(resp, "resp");
            this.resumeDetail = resp.data.data;
          })
          .catch((err) => {
            console.log(err);
          });
      } else console.log("未上传简历");
    },
    download() {
      this.$API.user
        .download(this.resumeDetail.linkName)
        .then((resp) => {
          // console.log(resp);
          if (resp.data.code === 0) {
            console.log("ok");
          }
        })
        .catch((err) => {
          // console.log(err);
        });
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
          top: 50px;
          right: 210px;
          .resume-card {
            width: 30%;
            // border: #409eff 1px solid;
            .resumeViewSection {
              width: 400px;
              // border: #3370ff 1px solid;
              margin-top: -20px;
              min-height: 200px;
              margin-bottom: 30px;
              &__title {
                margin-bottom: 20px;
                // margin-right: 115px;
                // padding-bottom: 3px;
                // border-bottom: 2px solid #3370ff;
              }
              .resumeViewForm {
                margin-bottom: 30px;
              }
              .resumeViewItem {
                // float: left;
                width: 100%;
                // margin-left: 200px;
                margin-bottom: 20px;
                margin-top: 10%;
                border-radius: 20px;
                min-height: 50px;
                &__resumeAttachment {
                  display: flex;
                  padding: 10px;
                  background-color: #f5f7fa;
                  align-items: center;
                  time {
                    color: #606266;
                    font-size: 14px;
                  }
                  .fileIcon {
                    margin-right: 10px;
                    width: 50px;
                    height: 50px;
                  }
                  .download-link {
                    margin-left: auto;

                    .el-icon-download {
                      font-size: 32px;
                      border-radius: 50%;
                      border: 1px solid #dcdfe6;
                      padding: 4px;
                    }
                    &:hover {
                      color: #3370ff;
                    }
                  }
                }
                &__content.is-link {
                  color: #3370ff;
                  cursor: pointer;
                }
                &__label {
                  color: #909399;
                  font-size: 14px;
                  margin-bottom: 7px;
                }
                &__content-link {
                  color: #3370ff;
                }
                &-desc {
                  overflow: hidden;
                  width: 70%;
                  white-space: pre-line;
                }
              }
            }
          }
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
            p {
              margin-top: 0;
              margin-bottom: 5%;
            }
            .simple {
              margin-top: 20%;
              width: 80%;
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
                width: 100%;
                list-style-type: none;
                span {
                  margin: 5% 2%;
                  width: 100%;
                  float: left;
                  text-align: left;
                  i{
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