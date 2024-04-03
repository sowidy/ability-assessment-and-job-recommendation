<template>
  <div>
    <div>
      <el-descriptions class="margin-top" title="简介" :column="2" border>
        <template slot="extra">
          <el-button
            type="primary"
            size="small"
            @click="centerDialogVisible = true"
            >编辑</el-button
          >
          <el-button type="primary" size="small" @click="centerDialogVisible2 = true">添加经历/技能</el-button>
        </template>
        <el-descriptions-item>
          <template slot="label"
            ><div><i class="el-icon-picture-outline"></i> 头像</div>
          </template>
          <img
            class="img"
            v-if="userInfo.avatar"
            :src="userInfo.avatar"
            style="width:70px;hight:70px"
            alt=""
          />
          <span v-else>暂无</span>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            账户名
          </template>
          {{ userInfo.phone }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-s-custom"></i>
            昵称
          </template>
          {{ userInfo.name }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-odometer"></i>
            年龄
          </template>
          {{ age || "暂无" }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-male"></i>
            <i class="el-icon-female"></i>
            性别
          </template>
          <el-tag size="small">{{ userInfo.gender || "暂无" }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-message"></i>
            邮箱Email
          </template>
          {{ userInfo.email || "暂无" }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            手机号码
          </template>
          {{ userInfo.phone }}
        </el-descriptions-item>
        <!-- <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-location-outline"></i>
            地区
          </template>
          {{ area }}
        </el-descriptions-item> -->
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-date"></i>
            注册日期
          </template>
          {{ userInfo.createTime | formatDate }}
        </el-descriptions-item>
        <!-- <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-magic-stick"></i>
            个性签名
          </template>
          {{ design }}
        </el-descriptions-item> -->
      </el-descriptions>
      <el-descriptions class="margin-top" :column="1" border>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-office-building"></i>
            技能
          </template>
          {{ userInfo.skills || "暂无" }}
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-basketball"></i>
            经历
          </template>
          {{ userInfo.experience || "暂无" }}
        </el-descriptions-item>
      </el-descriptions>
    </div>
    <!-- <div class="resumeEditor">
      <div class="resume-editor" ref="editorFrom">
        <h1>个人信息</h1>
        <el-button round type="primary" @click="changeMod">编辑</el-button>
      </div>
      <div class="detail">
        基础信息
        <div class="resumeSection">
          <div>
            <h2 class="resumeSection__title">基础信息</h2>
          </div>
          <div class="resumeSection__form">
            <el-form ref="basicForm" :rules="rules" :model="userForm">
              <el-form-item label="姓名" prop="name">
                <h2 v-if="!modify">{{ userInfo.name }}</h2>
                <el-input v-if="modify" v-model="userForm.name"></el-input>
              </el-form-item>
              <el-form-item label="性别" prop="gender">
                <h2 v-if="!modify">{{ userInfo.gender }}</h2>
                <el-select v-if="modify" v-model="userForm.gender">
                  <el-option label="男" value="男"></el-option>
                  <el-option label="女" value="女"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="邮箱" prop="email">
                <h2 v-if="!modify">{{ userInfo.email }}</h2>
                <el-input v-if="modify" v-model="userForm.email"></el-input>
              </el-form-item>
              <el-form-item label="年龄" prop="bornYear">
                <h2 v-if="!modify">
                  {{ age }}
                </h2>
                <el-date-picker
                  v-if="modify"
                  v-model="userForm.bornYear"
                  type="date"
                  placeholder="选择日期"
                >
                </el-date-picker>
              </el-form-item>
            </el-form>
          </div>
        </div>
        教育经历
        <div class="resumeSection">
          <div>
            <h2 class="resumeSection__title">教育经历</h2>
          </div>
          <div class="resumeSection__form">
            <el-form ref="basicForm" :model="userForm">
              <el-form-item label="学历" prop="">
                <h2 v-if="!modify">{{ userInfo.education }}</h2>
                <el-select v-if="modify" v-model="userForm.education">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="学校" prop="">
                <h2 v-if="!modify">{{ userInfo.school }}</h2>
                <el-input v-if="modify" v-model="userForm.school"></el-input>
              </el-form-item>
              <el-form-item label="专业" prop="">
                <h2 v-if="!modify">{{ userInfo.major }}</h2>
                <el-input v-if="modify" v-model="userForm.major"></el-input>
              </el-form-item>
            </el-form>
          </div>
        </div>
        技能展示
        <div class="resumeSection">
          <div>
            <h2 class="resumeSection__title">技能展示</h2>
          </div>
          <div class="resumeSection__form">
            <el-form ref="basicForm" :model="userForm">
              <el-form-item label="经历" prop="">
                <h2 v-if="!modify">{{ userInfo.experience || "暂无" }}</h2>
                <el-input
                  v-if="modify"
                  v-model="userForm.experience"
                  type="textarea"
                ></el-input>
              </el-form-item>
              <el-form-item label="技能" prop="">
                <h2 v-if="!modify">{{ userInfo.skills || "暂无" }}</h2>
                <el-input
                  v-if="modify"
                  v-model="userForm.skills"
                  type="textarea"
                ></el-input>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>
    </div> -->
    <!-- <div class="foot" v-if="false">
      <div
        ref="footerAction"
        :class="{ 'resumeEditor-footerAction-fix': footerActionFixed }"
        class="resumeEditor-footerAction"
      >
        <el-button round @click="modify = false">取消</el-button>
        <el-button
          round
          size="medium"
          @click="submit"
          type="primary"
          :disabled="!modify"
          >保存</el-button
        >
      </div>
    </div> -->
    <el-dialog
      title="修改个人信息"
      :visible.sync="centerDialogVisible"
      width="40%"
      :modal-append-to-body="false"
    >
      <el-form ref="basicForm" :rules="rules" :model="userForm">
        <el-row>
          <el-col :span="10">
            <el-row>
              <el-col :span="24">
                <el-form-item label="姓名" prop="name">
                  <el-input v-model="userForm.name"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="性别" prop="gender"
                  ><br />
                  <el-select v-model="userForm.gender">
                    <el-option label="男" value="男"></el-option>
                    <el-option label="女" value="女"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="邮箱" prop="email">
                  <el-input v-model="userForm.email"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="年龄" prop="bornYear"
                  ><br />
                  <el-date-picker
                    v-model="userForm.bornYear"
                    type="date"
                    placeholder="选择日期"
                  ></el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>
          <el-col :span="10">
            <el-row>
              <el-col :span="24">
                <el-form-item label="学历" prop="education"
                  ><br />
                  <el-select v-model="userForm.education">
                    <el-option
                      v-for="item in options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col> </el-row
            ><el-row>
              <el-col :span="24">
                <el-form-item label="学校" prop="school">
                  <el-input v-model="userForm.school"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="专业" prop="major">
                  <el-input v-model="userForm.major"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </span>
    </el-dialog>
      
    <el-dialog :modal-append-to-body="false" title="" :visible.sync="centerDialogVisible2" width="40%" >
      <div>
        <el-form ref="basicForm" :rules="rules" :model="userForm">
        <el-row>
          <el-col :span="20">
            <el-row>
              <el-col :span="24">
                <el-form-item label="经历" prop="experience">
                  <el-input
                    v-model="userForm.experience"
                    type="textarea"
                    :rows="10"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="技能" prop="skills">
                  <el-input
                    v-model="userForm.skills"
                    type="textarea"
                    :rows="4"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>
        </el-row>
        </el-form>
      </div>
      <div slot="footer">
        <el-button @click="centerDialogVisible2 = false">取 消</el-button>
        <el-button type="primary" @click="submit" :disabled="userForm.experience =='' ||userForm.skills == ''"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  data() {
    return {
      footerActionFixed: false,
      centerDialogVisible: false,
      centerDialogVisible2:false,
      options: [
        {
          value: "专科",
          label: "专科",
        },
        {
          value: "本科",
          label: "本科",
        },

        {
          value: "硕士",
          label: "硕士",
        },
        {
          value: "博士",
          label: "博士",
        },
      ],
      userForm: "",
      rules: {
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        gender: [{ required: true, message: "请选择性别", trigger: "change" }],
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          { type: "email", message: "请输入合法的邮箱", trigger: "blur" },
        ],
        bornYear: [{ required: true, message: "请输入年龄", trigger: "blur" }],
        education: [{ required: true, message: "请选择学历", trigger: "blur" }],
        school: [{ required: true, message: "请选择学历", trigger: "blur" }],
        major: [{ required: true, message: "请选择学历", trigger: "blur" }],
      },
    };
  },
  computed: {
    ...mapState("user", ["userInfo", "identity"]),
    age() {
      const currentYear = new Date().getFullYear();
      return this.userInfo.bornYear ?currentYear - this.userInfo.bornYear.slice(0, 4):'';
    },
  },
  mounted() {
    this.userForm = { ...this.userInfo };
  },
  methods: {
    changeMod() {
      this.modify = true;
    },
    submitLoading() {},
    handleCancel(){
      this.$refs.basicForm.resetFields();
      this.centerDialogVisible = false;
    },
    submit() {
      this.$refs.basicForm.validate((valid) => {
        console.log("##########");
        if (valid) {
          // console.log("Validation passed");
          this.$API.user
            .updateUser(this.userForm, this.identity)
            .then((resp) => {
              console.log(resp);
              if (resp.data.code === 0) {
                this.$store.dispatch("user/getUserInfo", {
                  identity: this.identity,
                });
                this.$notify({
                  type: "success",
                  message: "修改成功",
                });
                this.centerDialogVisible = false;
                this.centerDialogVisible2 = false;
              }
            })
            .catch((err) => {
              console.log(err);
            });
        } else {
          this.$notify.error("请填写正确的表单信息");
          console.log("Validation failed");
          return false;
        }
      });
    },
  },
};
</script>

<style lang='less' scoped>
.el-descriptions-item{
  .img{
    width: 100px;
    height: 100px;
  }
}
/deep/ .el-descriptions .is-bordered .el-descriptions-item__cell {
  height: 80px;
}
.el-dialog {
  .el-col {
    margin: 0 10px;
    .el-input__inner {
      width: 200px;
    }
  }
}
.detail {
  display: flex;
  height: 500px;
}
.resume-editor {
  width: 1000px;
  margin: auto;
  padding: 10px 0;
  position: relative;
  // border: 1px solid sandybrown;
  .el-button {
    position: absolute;
    top: 30px;
    right: 10px;
  }
}
.resumeEditor-footerAction {
  padding: 30px 190px;
  z-index: 33;
  text-align: right;
  &-fix {
    width: 100%;
    background: #fff;
    position: fixed;
    bottom: 0;
    left: 0;

    box-shadow: 0 -2px 10px #eee;
  }
}
.resumeSection {
  width: 33.33%;
  padding: 10px 0;
  margin: 0 4%;
  // display: flex;
  justify-content: space-between;
  border-bottom: 1px solid #dcdfe6;

  &__title {
    // margin-right: 115px;
    text-align: center;
    padding-bottom: 3px;
    border-bottom: 2px solid #3370ff;
  }
  &__form {
    // width: 450px;
    // margin-right: 50px;
    // h2 {
    // width: 300px;
    //   word-wrap: break-word;
    // }
    /deep/.el-input {
      width: 75%;
    }
    /deep/ .el-form-item__label {
      text-align: left;
      float: none;
      word-break: break-word;
    }
  }
}
.resumeSection {
  .el-select,
  .el-date-editor {
    width: 100%;
  }
  .bottom-action {
    display: flex;

    .el-icon-plus,
    .el-icon-delete {
      font-size: 22px;
      cursor: pointer;
      &-top {
        margin-left: auto;
      }
    }

    .el-icon-delete {
      margin-left: auto;
    }
  }
  .el-form {
    margin-bottom: 40px;
    &:not(:last-child) {
      border-bottom: 1px solid #e4e7ed;
    }
  }
}
</style>