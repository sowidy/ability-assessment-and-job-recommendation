<template>
  <div class="login-container">
    <!-- <el-card>
      <div> -->
    <div class="left">
      <el-carousel trigger="click" height="550px">
        <el-carousel-item v-for="(item, index) in items" :key="index">
          <!-- <h3 class="small">{{ item }}</h3> -->
          <div>
            <img :src="item.img" style="max-width: 100%;max-height: 100%;" alt="" />
          </div>
        </el-carousel-item>
      </el-carousel>
    </div>
    <div class="right">
      <el-form
        ref="loginForm"
        :model="loginForm"
        :rules="loginRules"
        class="login-form"
        auto-complete="on"
        label-position="left"
      >
        <div class="title-container">
          <h3 class="title">登录</h3>
        </div>

        <el-form-item prop="phone">
          <span class="svg-container">
            <i class="el-icon-user"></i>
          </span>
          <el-input
            ref="username"
            v-model="loginForm.phone"
            placeholder="电话号码"
            name="username"
            type="text"
            tabindex="1"
            auto-complete="on"
          />
        </el-form-item>

        <el-form-item prop="password">
          <span class="svg-container">
            <i class="el-icon-lock"></i>
          </span>
          <el-input
            :key="passwordType"
            ref="password"
            v-model="loginForm.password"
            :type="passwordType"
            placeholder="密码"
            name="password"
            tabindex="2"
            auto-complete="on"
            @keyup.enter.native="handleLogin"
          />
          <span class="show-pwd" @click="showPwd">
            <!-- <svg-icon
              :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"
            /> -->
          </span>
        </el-form-item>

        <el-button
          :loading="loading"
          type="primary"
          style="width: 100%; margin-bottom: 30px"
          @click.native.prevent="handleLogin"
        >
          登录</el-button
        >
        <el-button
          type="primary"
          style="margin: 0 auto"
          @click="$router.push('/home')"
          ><i class="el-icon-back"></i>返回前台</el-button
        >
      </el-form>
    </div>
    <!-- </div>
    </el-card> -->
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    var validatePhone = (rule, value, callback) => {
      if (!value || /^1\d{10}$/.test(value)) {
        callback();
      } else {
        callback(new Error("请输入以1开头的11位数字"));
      }
    };
    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error("密码不能低于6位数"));
      } else {
        callback();
      }
    };
    return {
      loginForm: {
        phone: "",
        password: "",
      },
      loginRules: {
        phone: [
          { required: true, message: "请输入电话", trigger: "blur" },
          { min: 11, max: 11, message: "电话号码不正确", trigger: "blur" },
          { validator: validatePhone, trigger: "blur" },
        ],
        password: [
          { required: true, trigger: "blur", validator: validatePassword },
        ],
      },
      loading: false,
      passwordType: "password",
      redirect: undefined,
      items: [
        {
          img: "https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/admin_car3%28%E5%B7%B2%E5%A4%84%E7%90%86%29.png",
        },
        {
          img: "https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/admin_car2%28%E5%B7%B2%E5%A4%84%E7%90%86%29.png",
        },
        {
          img: "https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/admin_car%28%E5%B7%B2%E5%A4%84%E7%90%86%29.png",
        },
        // {
        //   img: "https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/bannerthumb1.png",
        // },
      ],
    };
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true,
    },
  },
  methods: {
    showPwd() {
      if (this.passwordType === "password") {
        this.passwordType = "";
      } else {
        this.passwordType = "password";
      }
      this.$nextTick(() => {
        this.$refs.password.focus();
      });
    },
    handleLogin() {
      this.$refs.loginForm.validate(async (valid) => {
        if (valid) {
          this.loading = true;
          await this.$store
            .dispatch("admin/adminlogin", this.loginForm)
            .then(() => {
              this.$router.push({ path: this.redirect || "/admin" });
              this.loading = false;
            })
            .catch(() => {
              this.loading = false;
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
  },
};
</script>

<style lang="less" scoped>
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

// $bg: #283443;
// $light_gray: #283443;
// $cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: #fff)) {
  .login-container .el-input input {
    color: #fff;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      // color: #283443;
      height: 47px;
      caret-color: #fff;

      &:-webkit-autofill {
        // box-shadow: 0 0 0px 1000px #283443 inset !important;
        -webkit-text-fill-color: #fff !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    // background: rgba(0, 0, 0, 0.1);
    // background: #fff;
    border-radius: 5px;
    // color: #454545;
  }
}
</style>

<style lang="less" scoped>
// $bg: #2d3a4b;
// $dark_gray: #889aa4;
// $light_gray: #2d3a4b;

.login-container {
  // min-height: 100%;
  // height: 742px;
  height: 100vh;
  width: 100%;
  margin: 0 auto;
  // background-color: $bg;
  overflow: hidden;
  // background: url("~@/assets/bg.png");
  // background-color: #f2f2f2;
  background-position: center center;
  background: url(https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/admin_back.png)
    no-repeat;
  background-size: 100% 100%;
  display: flex;
  justify-content: space-around;
  // background: linear-gradient(
  //   to right bottom;rgb(110, 197, 159),
  //   rgb(65, 148, 173)
  // );
  // .el-card {
  //   width: 80%;
  //   height: 80%;
  //   border: 1px saddlebrown solid;
  //   border-radius: 20px;
  //   // display: flex;
  //   margin: 0 auto;
  //   margin-top: 5%;
  //   div {
  //     display: flex;
  //     justify-content: space-around;
  // padding: 0 10%;
  // padding:0 10%;
  .left {
    // border: 1px saddlebrown solid;
    width: 40%;
    padding-left: 20%;
    padding-top: 5%;
    // margin-left: 10%;

    // margin: 0 auto;
    .el-carousel {
      // margin: 0 auto;
      // margin-top: 10%;
      // max-width: 100%;
      // height: 100%;
    }

    .el-carousel-item {
      // max-width: 100%;
      div {
        // height: 550px;
        // height: 100%;
        // max-width: 100%;
        // display: flex;
        // justify-content: center;
        // align-items: center;
        img {
          // object-fit: cover;
          // max-width: 100%;
          // max-height: 100%;
        }
      }
    }
  }
  .right {
    // border: 1px rgb(146, 124, 108) solid;
    // margin-bottom: 13%;
    // margin-right: 5%;
    height: 100%;
    width: 40%;
    margin: 4%;
    .login-form {
      // background: rgba(0, 0, 0, 0.1);
      background: #fff;
      border-radius: 20px;
      height: 53%;
      position: relative;
      width: 420px;
      max-width: 100%;
      padding: 50px 35px 0;
      margin: 0 auto;
      overflow: hidden;
      margin-top: 13%;
      margin-bottom: -13%;
    }

    .tips {
      font-size: 14px;
      // color: #fff;
      margin-bottom: 10px;

      span {
        &:first-of-type {
          margin-right: 16px;
        }
      }
    }

    .svg-container {
      padding: 6px 5px 6px 15px;
      color: #889aa4;
      vertical-align: middle;
      width: 30px;
      display: inline-block;
    }

    .title-container {
      position: relative;

      .title {
        font-size: 26px;
        color: #283443;
        margin: 0px auto 40px auto;
        text-align: center;
        font-weight: bold;
      }
      h3 {
        color: #283443;
      }
    }

    .show-pwd {
      position: absolute;
      right: 10px;
      top: 7px;
      font-size: 16px;
      color: #889aa4;
      cursor: pointer;
      user-select: none;
    }
    //   }
    // }
  }
}
</style>
