<template>
  <div>
    <el-descriptions class="margin-top" title="企业简介" :column="2" border>
      <template slot="extra">
        <el-button
          type="primary"
          size="small"
          @click="centerDialogVisible = true"
          >编辑</el-button
        >
      </template>
      <el-descriptions-item>
        <template slot="label"
          ><div><i class="el-icon-picture-outline"></i> Logo</div>
        </template>
        <img class="img" v-if="userInfo.logo" :src="userInfo.logo" alt=""  style="width:70px;hight:70px"/>
        <span v-else>暂无信息</span>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="iconfont icon-zhanghu"></i>
          账户名
        </template>
        {{ userInfo.phone }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-s-custom"></i>
          公司名称
        </template>
        {{ userInfo.name }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-odometer"></i>
          类型
        </template>
        <el-tag type="success">{{ userInfo.type || "暂无信息" }}</el-tag>
      </el-descriptions-item>
      <!-- <el-descriptions-item>
        <template slot="label">
          <i class="iconfont icon-bianjibanbenmiaoshu_bianjibanbenmiaoshu"></i>
          公司描述
        </template>
        {{ userInfo.enterpriseDescription || "暂无信息" }}
      </el-descriptions-item> -->
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user-solid"></i>
          企业负责人
        </template>
        {{ userInfo.hiringManager || "暂无信息" }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          负责人手机号码
        </template>
        {{ userInfo.phone }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-date"></i>
          企业注册日期
        </template>
        {{ userInfo.createTime | formatDate }}
      </el-descriptions-item> </el-descriptions
    >
    <el-descriptions class="margin-top" title="" :column="1" :size="size" border>
      <el-descriptions-item>
        <template slot="label">
          <i class="iconfont icon-bianjibanbenmiaoshu_bianjibanbenmiaoshu"></i>
          公司描述
        </template>
        {{ userInfo.enterpriseDescription || "暂无信息" }}
      </el-descriptions-item>
    </el-descriptions>
    <el-dialog
      title="编辑企业基础信息"
      :visible.sync="centerDialogVisible"
      :modal-append-to-body="false"
      width="50%"
      center
    >
      <span>
        <el-form ref="basicForm" :model="userForm" :rules="rules">
          <el-row
            ><el-col :span="10"
              ><el-form-item label="名称" prop="name">
                <el-input v-model="userForm.name"></el-input>
              </el-form-item>
              <el-form-item label="类型" prop="type">
                <el-input v-model="userForm.type"></el-input>
              </el-form-item>
              <el-form-item label="地址" prop="address">
                <el-input v-model="userForm.address"></el-input> </el-form-item
            ></el-col>
            <el-col :span="10"
              ><el-form-item label="描述" prop="enterpriseDescription">
                <el-input v-model="userForm.enterpriseDescription"></el-input>
              </el-form-item>
              <el-form-item label="负责人" prop="hiringManager">
                <el-input v-model="userForm.hiringManager"></el-input>
              </el-form-item>
              <el-form-item label="联系电话" prop="phone">
                <el-input
                  v-model="userForm.hirePhone"
                ></el-input> </el-form-item
            ></el-col>
          </el-row>
        </el-form>
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </span>
    </el-dialog>
  </div>

  <!-- <div>
    <div class="resumeEditor">
      <div class="resume-editor" ref="editorFrom">
        <h1>企业信息</h1>
        <el-button round @click="changeMod">编辑</el-button>
      </div>
      <div class="detail">
        基础信息
        <div class="resumeSection">
          <div>
            <h2 class="resumeSection__title">基础信息</h2>
          </div>
          <div class="resumeSection__form">
            <el-form ref="basicForm" :model="userForm" :rules="rules">
              <el-form-item label="名称" prop="name">
                <h2 v-if="!modify">{{ userInfo.name }}</h2>
                <el-input v-if="modify" v-model="userForm.name"></el-input>
              </el-form-item>
              <el-form-item label="类型" prop="type">
                <h2 v-if="!modify">{{ userInfo.type || '暂无信息'}} </h2>
                <el-input v-if="modify" v-model="userForm.type"></el-input>
              </el-form-item>
              <el-form-item label="地址" prop="address">
                <h2 v-if="!modify">{{ userInfo.address|| '暂无信息' }}  </h2>
                <el-input v-if="modify" v-model="userForm.address"></el-input>
              </el-form-item>
              <el-form-item label="描述" prop="enterpriseDescription">
                <h2 v-if="!modify">{{ userInfo.enterpriseDescription|| '暂无信息' }}  </h2>
                <el-input
                  v-if="modify"
                  v-model="userForm.enterpriseDescription"
                ></el-input>
              </el-form-item>
              <el-form-item label="负责人" prop="hiringManager">
                <h2 v-if="!modify">{{ userInfo.hiringManager }}</h2>
                <el-input
                  v-if="modify"
                  v-model="userForm.hiringManager"
                ></el-input>
              </el-form-item>
              <el-form-item label="联系电话" prop="phone">
                <h2 v-if="!modify">{{ userInfo.phone }}</h2>
                <el-input
                  v-if="modify"
                  v-model="userForm.hiringManager"
                ></el-input>
              </el-form-item>
            </el-form>
          </div>
        </div>
        职位信息
        <div class="resumeSection job">
          <div>
            <h2 class="resumeSection__title">职位信息</h2>
          </div>
          <el-empty v-if="!userForm.title">
          </el-empty>
          <div class="resumeSection__form" v-else>
            <el-form ref="basicForm" :model="userForm" :rules="rules">
              <el-form-item label="职位" prop="">
                <h2>{{ userInfo.title }}</h2>
              </el-form-item>
              <el-form-item label="描述" prop="">
                <h2>{{ userInfo.titleDescription }}</h2>
              </el-form-item>
              <el-form-item label="薪资" prop="">
                <h2>{{ userInfo.salaryMin }}-{{userForm.salaryMax}}</h2>
              </el-form-item>
              <el-form-item label="要求" prop="">
                <h2>{{ userInfo.titleRequire }}</h2>
              </el-form-item>
              <el-form-item label="负责人" prop="">
                <h2>{{ userInfo.hiringManager }}</h2>
              </el-form-item>
              <el-form-item label="联系电话" prop="">
                <h2>{{ userInfo.hirePhone }}</h2>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>
    </div>

    <div class="foot" v-if="modify">
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
    </div>
    
  </div> -->
</template>

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
    return {
      footerActionFixed: false,
      modify: false,
      userForm: "",
      centerDialogVisible: false,
      rules: {
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        type: [{ required: true, message: "请输入类型", trigger: "blur" }],
        address: [{ required: true, message: "请输入地址", trigger: "blur" }],
        enterpriseDescription: [
          { required: true, message: "请输入描述内容", trigger: "blur" },
        ],
        hiringManager: [
          { required: true, message: "请输入联系人", trigger: "blur" },
        ],
        phone: [
          { required: true, message: "请输入电话", trigger: "blur" },
          { min: 11, max: 11, message: "电话号码不正确", trigger: "blur" },
          { validator: validatePhone, trigger: "blur" },
        ],
      },
    };
  },
  computed: {
    ...mapState("user", ["userInfo", "identity"]),
  },
  mounted() {
    this.userForm = { ...this.userInfo };
    console.log(this.userForm.title == "");
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
        if (valid) {
          console.log(this.userForm);
          this.$API.user
            .updateUser(this.userForm, this.identity)
            .then((resp) => {
              console.log(resp.data);
              if (resp.data.code === 0) {
                this.$store.dispatch("user/getUserInfo", {
                  identity: this.identity,
                });
                this.$notify({
                  type: "success",
                  message: "修改成功",
                });
                this.centerDialogVisible = false;
              }
            })
            .catch((err) => {
              console.log(err);
            });
        } else {
          // 表单验证不通过，作出提醒
          this.$notify.error("请填写正确的表单信息");
        }
      });
    },
  },
};
</script>

<style lang='less' scoped>
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
.resume-editor {
  width: 1000px;
  margin: auto;
  padding: 10px 0;
  // position: relative;
  display: flex;
  justify-content: space-between;
  // border: 1px solid sandybrown;
  .el-button {
    // margin-right: 10%;
  }
}
.resumeEditor-footerAction {
  margin: 10px 0;
  padding: 10px 190px;
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
.detail {
  display: flex;
  justify-content: space-between;
  .resumeSection {
    margin: 0 72px;
    width: 100%;
    padding: 10px 0;
    // display: flex;
    // justify-content: space-between;
    border-bottom: 1px solid #dcdfe6;

    &__title {
      // margin-right: 115px
      text-align: center;
      width: 70%;
      padding-bottom: 3px;
      border-bottom: 2px solid #3370ff;
    }
    &__form {
      width: 500px;
      margin-right: 50px;
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
}
</style>