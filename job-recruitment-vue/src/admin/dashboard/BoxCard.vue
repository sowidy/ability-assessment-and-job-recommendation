<template>
  <el-card class="box-card-component" style="margin-left: 8px">
    <div slot="header" class="box-card-header">
      <img
        src="https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/back-person.png"
      />
    </div>
    <div style="position: relative; text-align: left">
      <pan-thumb :image="avatar" class="panThumb" />
      <mallki class-name="mallki-text" text="能力评估与职位推荐系统" />
      <div style="padding-top: 35px" class="progress-item">
        <!-- <span>Vue</span> -->
        <!-- <el-progress :percentage="70" /> -->
        <span>管理员姓名：{{ name }}</span>
      </div>
      <div class="progress-item">
        <!-- <span>JavaScript</span>
        <el-progress :percentage="18" /> -->
        <span>电话号码：{{ adminInfo.phone }}</span>
      </div>
      <div class="progress-item">
        <span>邮箱：{{ adminInfo.email }}</span>
        <!-- <span>CSS</span> -->
        <!-- <el-progress :percentage="12" /> -->
      </div>
      <div class="progress-item">
        <el-button type="primary" plain @click="showDialog"
          >添加管理员</el-button
        >
        <el-button type="success" plain @click="showDialog2"
          >编辑信息</el-button
        >
        <!-- <span>ESLint</span>
        <el-progress :percentage="100" status="success" /> -->
      </div>
    </div>

    <el-dialog title="添加新管理员" :visible.sync="dialogVisible" width="30%">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="账号" prop="phone">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelAdd">取 消</el-button>
        <el-button type="primary" @click="addAdmin">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="编辑信息" :visible.sync="dialogVisible2" width="30%">
      <el-form ref="form2" :model="form2" :rules="rules2" label-width="80px">
        <el-form-item label="头像">
          <el-upload
            class="avatar-uploader"
            action="http://localhost:8080/api/upload/avatar"
            :show-file-list="false"
            name="image"
            :headers="{ token }"
            :on-error="handleError"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="form2.avatar" :src="form2.avatar" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form2.name"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form2.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form2.email"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelAdd2">取 消</el-button>
        <el-button type="primary" @click="editAdmin">确 定</el-button>
      </span>
    </el-dialog>
  </el-card>
</template>

<script>
import { mapState } from "vuex";
import { getToken } from "@/utils/auth";
import PanThumb from "./PanThumb";
import Mallki from "./TextHoverEffect/Mallki";

export default {
  components: { PanThumb, Mallki },

  filters: {
    statusFilter(status) {
      const statusMap = {
        success: "success",
        pending: "danger",
      };
      return statusMap[status];
    },
  },
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
      dialogVisible: false,
      dialogVisible2: false,
      statisticsData: {
        article_count: 1024,
        pageviews_count: 1024,
      },
      form: {
        phone: "",
        password: "",
      },
      form2: {
        name: "",
        email: "",
        phone: "",
        avatar: "",
      },
      rules: {
        phone: [
          { required: true, message: "请输入电话", trigger: "blur" },
          { min: 11, max: 11, message: "电话号码不正确", trigger: "blur" },
          { validator: validatePhone, trigger: "blur" },
        ],
        password: [
          { required: true, trigger: "blur", validator: validatePassword },
        ],
      },
      rules2: {
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          { type: "email", message: "请输入合法的邮箱", trigger: "blur" },
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
    ...mapState("admin", ["name", "avatar", "adminInfo"]),
    token() {
      return getToken();
    },
  },
  methods: {
    showDialog() {
      this.dialogVisible = true;
    },
    showDialog2() {
      this.dialogVisible2 = true;
      this.form2 = this.adminInfo;
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
      return isLt2M; //isJPG &&;
    },
    addAdmin() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$API.admin
            .addAdmin(this.form)
            .then((resp) => {
              if (resp.data.code == 0) {
                this.$notify({
                  type: "success",
                  message: "添加成功",
                });
                this.dialogVisible = false;
              } else this.$notify.error(resp.data.message);
            })
            .catch((err) => {
              console.log(err);
            });
        } else this.$notify.error("请填写完整信息");
      });
    },
    cancelAdd() {
      this.$refs.form.resetFields();
      this.form = {
        phone: "",
        password: "",
      };
      this.dialogVisible = false;
    },
    cancelAdd2() {
      this.$refs.form2.resetFields();
      this.form2 = {};
      this.dialogVisible2 = false;
    },
    editAdmin() {
      this.$refs.form2.validate((valid) => {
        if (valid) {
          this.$API.admin
            .updateAdmin(this.form2)
            .then((resp) => {
              if (resp.data.code == 0) {
                this.$notify({
                  type: "success",
                  message: "修改成功",
                });
                this.dialogVisible2 = false;
                this.$store.dispatch("admin/getInfo");
              }
            })
            .catch((err) => {
              console.log(err);
            });
        } else this.$notify.error("请输入完整信息");
      });
    },
    handleAvatarSuccess() {},
    handleError() {this.$notify.error("头像上传失败")},
  },
};
</script>

<style lang="less" scoped>
.box-card-component {
  .el-card__header {
    padding: 0px !important;
  }
}

.box-card-component {
  .box-card-header {
    position: relative;
    height: 220px;
    img {
      width: 100%;
      height: 100%;
      transition: all 0.2s linear;
      &:hover {
        transform: scale(1.1, 1.1);
        filter: contrast(130%);
      }
    }
  }
  .mallki-text {
    position: absolute;
    top: 0px;
    right: 0px;
    font-size: 20px;
    font-weight: bold;
  }
  .panThumb {
    z-index: 100;
    height: 70px !important;
    width: 70px !important;
    position: absolute !important;
    top: -45px;
    left: 0px;
    border: 5px solid #ffffff;
    background-color: #fff;
    margin: auto;
    box-shadow: none !important;
    &::v-deep .pan-info {
      box-shadow: none !important;
    }
  }
  .progress-item {
    margin-bottom: 10px;
    font-size: 14px;
  }
  @media only screen and (max-width: 1510px) {
    .mallki-text {
      display: none;
    }
  }
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 100px;
  height: 100px;
  line-height: 100px;
  text-align: center;
}
.avatar {
  width: 100px;
  height: 100px;
  display: block;
}
</style>