<template>
  <div class="register-bg">
    <!-- <Header></Header> -->
    <div class="register-form">
      <div class="center middle">
        <el-card>
          <div class="page-head">
            <el-page-header
              @click.native="goHome"
              content="首页"
            ></el-page-header>
          </div>
          <div class="front">
            <div class="formtable">
              <el-form
                :model="userForm"
                :rules="rules"
                ref="userForm"
                label-width="100px"
                class="demo-userForm"
              >
                <h2>注 册</h2>
                <el-tabs
                  v-model="state"
                  @tab-click="handleClick"
                  :stretch="true"
                  class="tabs"
                >
                  <el-tab-pane label="找工作" name="student"></el-tab-pane>
                  <el-tab-pane label="招聘" name="enterprise"></el-tab-pane>
                </el-tabs>
                <el-form-item prop="phone">
                  <el-input
                    v-model="userForm.phone"
                    placeholder="手机号"
                    prefix-icon="el-icon-user"
                  ></el-input>
                </el-form-item>
                <el-form-item prop="name">
                  <el-input
                    v-model="userForm.name"
                    placeholder="昵称"
                    prefix-icon="el-icon-edit"
                  ></el-input>
                </el-form-item>
                <el-form-item prop="password">
                  <el-input
                    type="password"
                    v-model="userForm.password"
                    placeholder="密码"
                    prefix-icon="el-icon-lock"
                    show-password
                  ></el-input>
                </el-form-item>
                <el-form-item prop="re_password">
                  <el-input
                    type="password"
                    round
                    v-model="userForm.re_password"
                    placeholder="确认密码"
                    prefix-icon="el-icon-check"
                    show-password
                  ></el-input>
                </el-form-item>
                <div class="op">
                  <el-button
                    type="primary"
                    style="width: 60%"
                    round
                    @click="submitForm('userForm')"
                    >注册</el-button
                  >
                  <!-- <el-button @click="resetForm('userForm')">重置</el-button> -->
                </div>
                <div class="tips">
                  <h3>创建账号，已有账号？<a @click="goLogin">点击登录</a></h3>
                  <div class="quickRe"></div>
                  <div class="forget"></div>
                </div>
              </el-form>
            </div>
          </div>
        </el-card>
        <div class="slogan">
          <h1>基于知识图谱的</h1>
          <h1>大学生能力评价和职位推荐系统</h1>
          <br />
          <span>College students ability evaluation</span><br />
          <span>and job recommendation system based on knowledge graph</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
    var validatePhone = (rule, value, callback) => {
      if (!value || /^1\d{10}$/.test(value)) {
        callback();
      } else {
        callback(new Error("请输入以1开头的11位数字"));
      }
    };
    var confirmPassword = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("密码不能为空"));
      } else if (value !== this.userForm.password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback(); //自定义校验 callback 必须被调用
      }
    };
    return {
      isSelected: "user",
      state: "student",
      userForm: {
        phone: "",
        name: "",
        password: "",
        re_password: "",
      },
      rules: {
        phone: [
          { required: true, message: "请输入电话", trigger: "blur" },
          { min: 11, max: 11, message: "电话号码不正确", trigger: "blur" },
          { validator: validatePhone, trigger: "blur" },
        ],
        name: [
          { required: true, message: "请输入昵称", trigger: "blur" },
          { min: 2, max: 10, message: "昵称不能少于两位", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 6, max: 18, message: "6~18位字符", trigger: "blur" },
        ],
        re_password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            validator: confirmPassword,
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          await this.$API.user
            .reUserRegister(this.userForm, this.state)
            .then((resp) => {
              if (resp.data.code == 0) {
                this.$notify({
                  type: "success",
                  message: "注册成功，请登录",
                });
                this.$router.push("/login");
              } else {
                this.$refs[formName].resetFields();
                this.$notify.error(resp.data.message);
              }
            })
            .catch((err) => {
              console.log(err.message);
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    chooseState(value) {
      this.isSelected = value;
    },
    goLogin() {
      this.$router.push("login");
    },
    handleClick() {
      console.log(this.state);
    },
    goHome() {
      this.$router.push("home");
    },
  },
};
</script>

<style lang='less' scoped>
.register-bg {
  background-image: url(https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/canva_register_back.png);
  background-size: 100% 100%;
  // position: relative;
  // .home {
  //   height: 50px;
  //   width: 50px;
  //   border: 1px silver solid;
  //   position: absolute;
  //   top: 4%;
  //   left: 2%;
  //   z-index: 1;
  // }
  // height: 825px;
  // background-image: url("~@/assets/bg.png");
  // background-color: #f2f2f2;
  .register-form {
    // height: 742px;
    height: 100vh;
    overflow: hidden;
    backdrop-filter: blur(9px);
  }
  .center {
    position: relative;

    // height: 742px;
    height: 80%;
    width: 80%;
    margin: 0 auto;
    margin-top: 5%;
    background: url(https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/canva_register.png);
    background-size: 100% 100%;
    // display: flex;
    // justify-content: center;
    // align-items: center;
    // height: 742px;

    .el-card {
      padding: 20px;
      position: absolute;
      top: 0px;
      left: 0px;
      border: none;
      // z-index: -1;
      background: transparent;
      width: 700px;
      height: 600px;
      box-shadow: none;
      // margin-top: 5%;
      // padding: 0 20%;
      // background-image: url("~@/assets/bg.png");
      // background-color: #f2f2f2;
      // background: white;
      .page-head {
        position: relative;
        .el-page-header {
          position: absolute;
          top: 10px;
          left: 90px;
        }
      }
      .front {
        .formtable {
          // padding: 0 10%;
          .el-form-item {
            margin-left: 7%;
            margin-bottom: 4%;
            width: 70%;
          }
          .tabs {
            padding: 0 20%;
            margin-bottom: 5%;
            .el-tab-pane {
              padding: 0 2%;
            }
          }
          h2,
          .op {
            text-align: center;
            margin: 20px 0;
          }
          h2 {
            word-spacing: 100px;
          }
          .tips {
            display: flex;
            align-items: center;
            justify-content: center;
            text-align: center;
          }
        }
      }
    }
    .slogan {
      position: absolute;
      top: 80px;
      right: 60px;
    }
  }
}
</style>