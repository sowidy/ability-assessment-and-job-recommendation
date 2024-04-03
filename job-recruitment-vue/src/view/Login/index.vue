<template>
  <div>
    <Header></Header>
    <div class="login-form">
      <div class="center middle">
        <el-card>
          <div class="front">
            <div class="formtable">
              <h3>欢迎加入我们</h3>
              <el-form
                :model="userForm"
                :rules="rules"
                ref="userForm"
                label-width="100px"
              >
                <h2>登 录</h2>
                <el-form-item prop="phone">
                  <el-input
                    v-model="userForm.phone"
                    placeholder="手机号"
                    prefix-icon="el-icon-user"
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
                <el-form-item>
                  <el-switch
                    :width="100"
                    active-text="学生"
                    inactive-text="企业"
                    active-value="student"
                    inactive-value="enterprise"
                    v-model="status"
                    style="width: 100%"
                    active-color="#13ce66"
                    inactive-color="#409EFF"
                  >
                  </el-switch>
                </el-form-item>
                <div class="op">
                  <el-button
                    type="primary"
                    style="width: 60%"
                    round
                    @click="submitForm('userForm')"
                    >登录</el-button
                  >
                </div>
                <div class="tips">
                  <div class="quickRe" @click="goRegister">
                    <el-button type="success">快速注册</el-button>
                  </div>
                  <el-button
                    type="primary"
                    @click="$router.push('/admin/login')"
                    >管理员登录<i class="el-icon-right"></i
                  ></el-button>
                </div>
              </el-form>
            </div>
          </div>
        </el-card>
      </div>
    </div>
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
    return {
      status: "",
      userForm: {
        phone: "17574892870",
        password: "123456",
      },
      rules: {
        phone: [
          { required: true, message: "请输入电话", trigger: "blur" },
          { min: 11, max: 11, message: "电话号码不正确", trigger: "blur" },
          { validator: validatePhone, trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 6, max: 18, message: "6~18位字符", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          try {
            await this.$store.dispatch("user/userLogin", {
              userForm: this.userForm,
              identity: this.status,
            });
            this.$notify({
              type: "success",
              message: "登录成功",
            });
            this.$router.push(
              this.status == "student" ? "userinfo" : "enterprise"
            );
          } catch (error) {
            this.$notify.error(error.message);
          }
        } else {
          this.$notify.error("请填写正确信息");
          return false;
        }
      });
    },
    goRegister() {
      this.$router.push("register");
    },
  },
};
</script>

<style lang='less' scoped>
.login-form {
  height: 742px;
  width: 100%;
  top: 0;
  background-image: url("~@/assets/bg.png");
  background-color: #f2f2f2;
  background-position: center center;
  .center {
    display: flex;
    justify-content: center;
    align-items: center;

    .el-card {
      // border: none;
      width: 700px;
      height: 600px;

      // box-shadow: none;
      margin-top: 5%;
      // padding: 0 20%;
      background-image: url("~@/assets/bg.png");
      // background-color: #f2f2f2;
      background: white;
      background-position: center center;
      .front {
        .formtable {
          padding: 0 10%;
          z-index: 99;
          .el-form-item {
            margin-left: 5%;
            margin-bottom: 5%;
            width: 70%;
            .el-switch {
              margin-left: 17%;
              width: 100%;
            }
            .el-switch__core {
              width: 200px !important;
            }
          }
          h3,
          h2,
          .op {
            text-align: center;
            margin: 40px 0;
            z-index: 99;
          }
          h3 {
            letter-spacing: 20px;
          }
          h2 {
            word-spacing: 100px;
          }
          .tips {
            display: flex;
            align-items: center;
            justify-content: center;
            text-align: center;
            .quickRe {
              color: rgb(20, 219, 17);
              margin-right: 30px;
            }
          }
        }
      }
    }
  }
}
</style>