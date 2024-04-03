<template>
  <div>
    <el-collapse>
      <el-collapse-item title="修改手机号" name="1" class="set">
        <el-form
          :model="phoneForm"
          status-icon
          :rules="phonerules"
          ref="phoneForm"
          label-width="100px"
          class="demo-ruleForm"
        >
          <el-form-item label="手机号" prop="phone" class="settinginput">
            <el-input v-model="phoneForm.phone" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="btn" @click="changePhone('phoneForm')"
              >确定</el-button
            >
          </el-form-item>
        </el-form>
      </el-collapse-item>
      <el-collapse-item title="修改密码" name="2" class="set">
        <el-form
          :model="passForm"
          status-icon
          :rules="passwordrules"
          ref="passForm"
          label-width="100px"
          class="demo-ruleForm"
        >
          <el-form-item label="原密码" prop="old_pwd" class="settinginput">
            <el-input
              type="password"
              v-model="passForm.old_pwd"
              auto-complete="off"
            ></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="new_pwd" class="settinginput">
            <el-input
              type="password"
              v-model="passForm.new_pwd"
              auto-complete="off"
            ></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="re_pwd" class="settinginput">
            <el-input
              type="password"
              v-model="passForm.re_pwd"
              auto-complete="off"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="btn" @click="changePass('passForm')"
              >确定</el-button
            >
          </el-form-item>
        </el-form>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>
<style>
.demo-ruleForm {
  margin-top: 20px;
}

.set {
  margin-top: 21px;
  font-size: 14px;
  color: #909399;
}

.btn {
  position: relative;
  top: -62px;
  left: 555px;
}

.settinginput {
  width: 75%;
}
</style>

<script>
import { mapState } from "vuex";
export default {
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
      } else if (value !== this.passForm.new_pwd) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback(); //自定义校验 callback 必须被调用
      }
    };
    return {
      passForm: {
        old_pwd: "",
        new_pwd: "",
        re_pwd: "",
      },
      phoneForm: {
        phone: "",
      },
      emailForm: {
        email: "",
      },
      phonerules: {
        phone: [
          { required: true, message: "请输入电话", trigger: "blur" },
          { min: 11, max: 11, message: "电话号码不正确", trigger: "blur" },
          { validator: validatePhone, trigger: "blur" },
        ],
      },
      passwordrules: {
        old_pwd: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 6, max: 18, message: "6~18位字符", trigger: "blur" },
        ],
        new_pwd: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 6, max: 18, message: "6~18位字符", trigger: "blur" },
        ],
        re_pwd: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { validator: confirmPassword, trigger: "blur" },
        ],
      },
    };
  },
  computed: {
    ...mapState("user", ["userInfo", "identity"]),
  },
  methods: {
    changePhone(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.phoneForm.phone == this.userInfo.phone) {
            return this.$notify.error("请输入与当前不同的电话号码");
          }
          this.$API.user
            .updatePhone(this.identity, this.phoneForm)
            .then((resp) => {
              if (resp.data.code == 0) {
                this.$store.commit("user/USER_LOGOUT");
                this.$notify({
                  type: "success",
                  message: "修改成功,请重新登录",
                });
                this.$router.replace("/login");
              }
            })
            .catch((err) => {
              console.log(err);
            });
        } else {
          console.log("error submit!!");
        }
      });
    },
    changePass(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$API.user
            .updateUserPassword(this.identity, this.passForm)
            .then((resp) => {
              if (resp.data.code == 0) {
                this.$store.commit("user/USER_LOGOUT");
                this.$notify({
                  type: "success",
                  message: "修改成功,请重新登录",
                });
                this.$router.replace("/login");
              }
            })
            .catch((err) => {
              console.log(err);
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
