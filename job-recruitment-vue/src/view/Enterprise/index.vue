<template>
  <div>
    <Header></Header>
    <div class="wrapper">
      <el-card class="box-card">
        <div>
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
          <span class="username">{{ userInfo ? userInfo.name : "" }}</span>
          <div class="tips">
            <el-tag
              class="verify"
              @click="openFileSelector"
              :type="userInfo.enterpriseAuth == 0 ? 'success' : 'warning'"
              >{{ Authentication }}</el-tag
            >
            <el-upload
              ref="upload"
              action="http://localhost:8080/api/upload/material"
              :on-success="handermaterialSuccess"
              :on-error="handermaterialError"
              :multiple="true"
              :show-file-list="false"
              v-if="userInfo.enterpriseAuth != 0"
              :headers="{ token }"
              name="file"
            >
              <el-button class="mupload" size="small" round>点击上传</el-button>
            </el-upload>
          </div>
          <div class="moveAvatar">
            <!-- <img src="@/assets/enterprise-avtar.png" alt="" /> -->
          </div>
        </div>
      </el-card>
      <el-card
        class=""
        style="width: 1200px; margin: 14px auto auto auto; position: sticky;"
      >
        <div class="info-nav" >
          <el-col :span="4">
            <el-menu
              :default-active="getActiveIndex()"
              class="el-menu-demo"
              background-color="#fff"
              text-color="#000"
              active-text-color="#ffd04b"
              ><router-link class="link" to="/enterprise/detail">
                <el-menu-item index="1">
                  <span slot="title"> 基本信息 </span>
                </el-menu-item></router-link
              >
              <router-link class="link" to="/enterprise/titles">
                <el-menu-item index="2">
                  <span slot="title"> 职位信息 </span>
                </el-menu-item></router-link
              >
              <router-link class="link" to="/search">
                <el-menu-item index="3">
                  <span slot="title"> 搜索 </span>
                </el-menu-item></router-link
              >
              <router-link class="link" to="/enterprise/setting">
                <el-menu-item index="4">
                  <span slot="title"> 设置 </span>
                </el-menu-item></router-link
              >
            </el-menu>
          </el-col>
          <el-col :span="20"
            ><div class="info-detail">
              <router-view></router-view></div
          ></el-col>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import { getToken } from "@/utils/token";
import { mapState } from "vuex";
export default {
  name: "Enterprise",
  data() {
    return {
      imageUrl: "",
      dialogFormVisible: false,
      material: "",
      form: {
        name: "",
        region: "",
      },
      userForm: "",
    };
  },
  computed: {
    token() {
      return getToken();
    },
    ...mapState("user", ["userInfo", "identity"]),
    setDefault() {
      return this.userInfo.logo ? this.userInfo.logo : "defaultEnAvatar.jpg";
    },
    Authentication() {
      if (this.userInfo.enterpriseAuth == 0) return "已认证";
      else if (this.userInfo.enterpriseAuth == 1)
        return "待认证 点击更新认证信息";
      else return "未认证 点击上传认证信息";
    },
  },
  mounted() {
    this.userForm = { ...this.userInfo };
  },
  methods: {
    handleSelect() {},
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      this.imageUrl = res.data;
      this.$API.user
        .uploadStudentAvatar(this.imageUrl, this.identity)
        .then((resp) => {
          if (resp.data.code == 0) {
            this.$notify({
              type: "success",
              message: "上传成功",
            });
          } else {
            this.imageUrl = "";
            this.handleError();
          }
        })
        .catch((err) => {
          this.$message.error(err.message);
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
      return  isLt2M//isJPG &&;
    },
    openFileSelector() {
      // this.$refs.upload.$refs.input.click();
    },
    handermaterialSuccess({ data }) {
      console.log(data);
      this.material = data;
      this.$API.enterprise
        .addOrUploadMaterial(this.material)
        .then((resp) => {
          console.log(resp.data);
          if (resp.data.code == 0) {
            this.$notify({
              type: "success",
              message: "上传成功",
            });
            this.$store.dispatch("user/getUserInfo", {
              identity: this.identity,
            });
          } else this.$message.error(resp.data.message);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    handermaterialError() {
      this.$notify.error("上传失败");
    },
    getActiveIndex() {
      switch (this.$route.path) {
        case "/enterprise/detail":
          return "1";
        case "/enterprise/titles":
          return "2";
        case "/search":
          return "3";
        case "/userinfo/setting":
          return "4";
        default:
          return "1"; // Set a default value if no match found
      }
    },
  },
};
</script>

<style scoped>
* {
  box-sizing: border-box;
}
.wrapper {
  padding-top: 14px;
  background-image: url("~@/assets/bg.png");
  background-color: #f2f2f2;
  padding: 1% 0;
  background-position: center center;
}
.box-card {
  width: 1200px;
  height: 120px;
  margin: 0px auto auto auto;
  background-image: url("~@/assets/enterprise_back.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  position: relative;
}
.box-card .addtitle {
  position: absolute;
  top: 50px;
  right: 50px;
}
.img {
  border-radius: 50%;
  width: 70px;
  height: 70px;
}
.el-card .username {
  float: left;
  margin-left: 14px;
  font-size: 21px;
}
.avatar-uploader {
  float: left;
}
.avatar-uploader .el-upload {
  border-radius: 50%;
  width: 70px;
  height: 70px;
  margin-bottom: 14px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar {
  width: 5rem;
  height: 5rem;
  display: block;
}
.hr {
  margin: 20px auto auto auto;
}
.verify {
  margin: 0 20px;
}
.tips {
  display: flex;
}
</style>

<style lang="less" scoped>
@keyframes movebounce {
  0% {
    transform: translateY(0px);
  }
  50% {
    transform: translateY(10px);
  }
  100% {
    transform: translateY(0px);
  }
}
.box-card {
  position: relative;
  .moveAvatar {
    position: absolute;
    right: 100px;
    top: 5px;
    animation: movebounce 3.9s linear infinite;
    img {
      width: 100px;
      height: 100px;
    }
  }
}
.el-card {
  border-radius: 20px;
  .info-nav {
    min-height: 475px;
    // display: flex;
  }
  .el-menu {
    // width: 10%;

    .link {
      text-decoration: none;
      color: black;
    }
  }
  .info-detail {
    // padding: 5% 5%;
    padding: 1% 5%;
    // width: 90%;
  }
}
</style>