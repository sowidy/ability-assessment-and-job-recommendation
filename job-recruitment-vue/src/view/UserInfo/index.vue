<template>
  <div class="bg">
    <Header></Header>
    <div class="wrapper">
      <el-card class="box-card">
        <div class="inner">
          <div class="avatar-card">
            <el-upload
              class="avatar-uploader"
              action="http://localhost:8080/api/upload/avatar"
              name="image"
              :headers="{ token }"
              :show-file-list="false"
              :on-error="handleError"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
            >
              <img v-if="imageUrl" class="avatar" :src="imageUrl" />
              <img v-else class="img" :src="setDefault" />
            </el-upload>
          </div>
          <div class="info-detail">
            <div>
              <span
                ><i class="el-icon-s-custom"></i>
                {{ userInfo ? userInfo.name : "" }}</span
              >
              <span><i class="el-icon-phone"></i> {{ userInfo.phone }}</span>
              <span v-if="userInfo.school"
                ><i class="el-icon-school"></i> {{ userInfo.school }}</span
              >
            </div>
            <div>
              <!-- <span>{{ userInfo.major }}</span>
              <span>{{ userInfo.education }}</span>
              <span>{{ userInfo.email }}</span> -->
            </div>
          </div>
          <div class="resume-card">
            <div class="resumeViewSection" v-if="resumeDetail">
              <div class="resumeViewItem resumeViewItem__resumeAttachment">
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
        </div>
      </el-card>
      <el-card
        class="info-card"
        style="width: 1200px; margin: 14px auto auto auto; position: sticky"
      >
        <div class="info-nav">
          <el-col :span="4">
            <el-menu
              :default-active="getActiveIndex()"
              class="el-menu-vertical-demo"
              ><router-link class="link" to="/userinfo/detail">
                <el-menu-item index="1">
                  <span slot="title"> 个人信息 </span>
                </el-menu-item></router-link
              ><router-link class="link" to="/userinfo/resume">
                <el-menu-item index="2">
                  <span slot="title"> 上传简历 </span>
                </el-menu-item></router-link
              >
              <router-link class="link" to="/evaluation-recommendations">
                <el-menu-item index="3">
                  <span slot="title"> 能力评估与建议 </span>
                </el-menu-item>
              </router-link>
              <router-link  class="link" to="/recommend">
                <el-menu-item index="4">
                  <span slot="title"> 推荐列表 </span>
                </el-menu-item>
              </router-link>

              <router-link class="link" to="/userinfo/setting">
                <el-menu-item index="5">
                  <span slot="title"> 设置 </span>
                </el-menu-item></router-link
              >
            </el-menu>
          </el-col>
          <el-col :span="20">
            <div class="info-detail">
              <router-view></router-view>
            </div>
          </el-col>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
// import PersonalDia from "./PersonalDia.vue";
import { getToken } from "@/utils/token";
import { mapState } from "vuex";
import FileIcon from "@/components/File-Icon";
export default {
  name: "UserInfo",
  data() {
    return {
      imageUrl: "",
      resumeDetail: "",
    };
  },
  components: { FileIcon },
  mounted() {
    console.log(this.$route);
    this.getResumeDetail();
  },
  computed: {
    token() {
      return getToken();
    },
    ...mapState("user", ["userInfo", "identity"]),
    setDefault() {
      return this.userInfo.avatar
        ? this.userInfo.avatar
        : "defaultStuAvatar.jpg";
    },
    resumeFileType() {
      const pathArr = this.resumeDetail.originName.split(".");
      return pathArr[pathArr.length - 1];
    },
  },
  methods: {
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      this.imageUrl = res.data;
      this.$API.user
        .uploadStudentAvatar(this.imageUrl, this.identity)
        .then((resp) => {
          if (resp.data.code == 0) {
            // this.$notify({
            //   type: "success",
            //   message: "上传成功",
            // });
          } else {
            this.imageUrl = "";
            this.handleError();
          }
        })
        .catch((err) => {
          this.$notify.error(err.message);
        });
    },
    handleError() {
      this.$notify.error("上传失败");
    },
    beforeAvatarUpload(file) {
      // const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      // if (!isJPG) {
      //   this.$notify.error("上传头像图片只能是 JPG 格式!");
      // }
      if (!isLt2M) {
        this.$notify.error("上传头像图片大小不能超过 2MB!");
      }
      return isLt2M; //&& isJPG  ;
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    getActiveIndex() {
      switch (this.$route.path) {
        case "/userinfo/detail":
          return "1";
        case "/userinfo/resume":
          return "2";
        // case "/userinfo/evaluate":
        //   return "3";
        case "/userinfo/setting":
          return "5";
        default:
          return "1";
      }
    },
    async getResumeDetail() {
      console.log(this.userInfo, "userinfo");
      if (this.userInfo.resumeId) {
        await this.$API.user
          .getResume()
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



<style lang='less' scoped>
* {
  box-sizing: border-box;
}
// .bg{
//   min-height: 742px;
// }
.wrapper {
  background-image: url("~@/assets/bg.png");
  background-color: #f2f2f2;
  background-position: center center;
  margin: 0;
  padding: 1% 0;
  .box-card {
    width: 1200px;
    margin: 0px auto auto auto;
    border-radius: 20px;
    height: 120px;
    // background: url("~@/assets/user_bg.webp");
    // background-image: linear-gradient(
    //   135deg,
    //   rgba(0, 181, 188, 0.89) 0%,
    //   rgba(62, 142, 240, 0.89) 100%
    // );
    background: url(https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/user_back.png)
      no-repeat;
    background-size: 100% 200%;
    background-position: top -20px left;
    .inner {
      display: flex;
      justify-content: space-around;
      height: 100%;
      .avatar-uploader {
        // border: #409eff 1px solid;
        width: 20%;
        border-radius: 50%;
        width: 70px;
        height: 70px;
        margin-bottom: 14px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
        .avatar {
          width: 5rem;
          height: 5rem;
          display: block;
        }
        .img {
          border-radius: 50%;
          width: 70px;
          height: 70px;
        }
      }
      .avatar-uploader:hover {
        border-color: #409eff;
      }
      .info-detail {
        width: 40%;
        height: 100%;
        // border: #409eff 1px solid;
        display: flex;
        div {
          width: 50%;
          .username {
            padding: 0 30px;
          }
          span {
            // border: #409eff 1px solid;
            margin: 1% 0;
            display: block;
            font-size: 21px;
          }
        }
      }
      .resume-card {
        width: 30%;
        // border: #409eff 1px solid;
        .resumeViewSection {
          width: 400px;
          // border: #3370ff 1px solid;
          margin-top: -30px;
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
</style>

<style lang="less" scoped>
.info-card {
  border-radius: 20px;
  min-height: 515px;
  .info-nav {
    // display: flex;
  }
  .el-menu {
    .link {
      text-decoration: none;
      color: black;
    }
  }
  .info-detail {
    padding: 1% 5%;
    // width: 90%;
  }
}
</style>