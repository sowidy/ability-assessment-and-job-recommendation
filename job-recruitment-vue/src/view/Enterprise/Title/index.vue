<template>
  <div>
    <el-descriptions class="margin-top" title="职位信息" :column="1" border>
      <template slot="extra">
        <el-button
          type="primary"
          size="small"
          @click="centerDialogVisible = true"
          >添加职位</el-button
        >
      </template>
      <el-descriptions-item>
        <template slot="label">
          <i class="iconfont icon-zhiwei"></i>
          职位名称
        </template>
        <el-tag type="success">{{ userInfo.title || "暂无" }}</el-tag>
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          地点
        </template>
        {{ userInfo.address || "暂无" }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="iconfont icon-xinzifanwei"></i>
          薪资
        </template>
        {{ userInfo.salaryMin }}-{{ userInfo.salaryMax }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="iconfont icon-yijibumenfuzeren"></i>
          负责人
        </template>
        {{ userInfo.hiringManager || "暂无" }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          联系电话
        </template>
        {{ userInfo.hirePhone || "暂无" }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="iconfont icon-bianjibanbenmiaoshu_bianjibanbenmiaoshu"></i>
          描述
        </template>
        {{ userInfo.titleDescription || "暂无" }}
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">
          <i class="iconfont icon-yaoqiu "></i>
          要求
        </template>
        {{ userInfo.titleRequire || "暂无" }}
      </el-descriptions-item>
    </el-descriptions>
    <el-dialog
      title="添加职位"
      :modal-append-to-body="false"
      :visible.sync="centerDialogVisible"
      width="40%"
      center
    >
      <span>
        <el-form ref="userForm" :model="userFormTmp" :rules="rules">
          <el-row>
            <el-col :span="10"
              ><el-form-item label="职位" prop="title">
                <el-input
                  v-model="userFormTmp.title"
                  label-width="120"
                ></el-input>
              </el-form-item>

              <el-form-item label="负责人" prop="hiringManager">
                <el-input v-model="userFormTmp.hiringManager"></el-input>
              </el-form-item>
              <el-form-item label="联系电话" prop="hirePhone">
                <el-input v-model="userFormTmp.hirePhone"></el-input>
              </el-form-item>
              <el-form-item label="描述" prop="titleDescription">
                <el-input
                  v-model="userFormTmp.titleDescription"
                  type="textarea"
                  rows="4"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="最低薪资" prop="salaryMin">
                <el-select
                  v-model="userFormTmp.salaryMin"
                  placeholder="选择最低薪资"
                  filterable
                  allow-create
                >
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option
                ></el-select>
              </el-form-item>
              <el-form-item label="最高薪资" prop="salaryMax">
                <el-select
                  v-model="userFormTmp.salaryMax"
                  placeholder="选择最高薪资"
                  filterable
                  allow-create
                >
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="要求" prop="titleRequire">
                <el-input
                  v-model="userFormTmp.titleRequire"
                  type="textarea"
                  rows="4"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" @click="addNewTitle">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "Title",
  data() {
    var validatePhone = (rule, value, callback) => {
      if (!value || /^1\d{10}$/.test(value)) {
        callback();
      } else {
        callback(new Error("请输入以1开头的11位数字"));
      }
    };
    return {
      centerDialogVisible: false,
      userForm: "",
      userFormTmp: {
        hirePhone: "",
        hiringManager: "",
        titleRequire: "",
        titleDescription: "",
        title: "",
        salaryMin: "",
        salaryMax: "",
      },
      options: [
        {
          value: "1000",
          name: "1000",
        },
        {
          value: "2000",
          name: "2000",
        },
        {
          value: "3000",
          name: "3000",
        },
        {
          value: "4000",
          name: "4000",
        },
        {
          value: "5000",
          name: "5000",
        },
        {
          value: "6000",
          name: "6000",
        },
        {
          value: "7000",
          name: "7000",
        },
        {
          value: "8000",
          name: "8000",
        },
        {
          value: "9000",
          name: "9000",
        },
        {
          value: "10000",
          name: "10000",
        },
        {
          value: "11000",
          name: "11000",
        },
        {
          value: "12000",
          name: "12000",
        },
        {
          value: "13000",
          name: "13000",
        },
      ],
      rules: {
        hirePhone: [
          { required: true, message: "请输入电话", trigger: "blur" },
          { min: 11, max: 11, message: "电话号码不正确", trigger: "blur" },
          { validator: validatePhone, trigger: "blur" },
        ],
        hiringManager: [
          { required: true, message: "请输入联系人", trigger: "blur" },
        ],
        titleRequire: [
          { required: true, message: "请输入职位要求", trigger: "blur" },
        ],
        titleDescription: [
          { required: true, message: "请输入职位描述", trigger: "blur" },
        ],
        title: [{ required: true, message: "请输入职位名称", trigger: "blur" }],
        salaryMin: [{ required: true, message: "请选择", trigger: "blur" }],
        salaryMax: [{ required: true, message: "请选择", trigger: "blur" }],
      },
    };
  },
  computed: {
    ...mapState("user", ["userInfo", "identity"]),
  },
  mounted() {
    this.userForm = { ...this.userInfo };
  },
  methods: {
    handleCancel() {
      this.$refs.userForm.resetFields();
      this.centerDialogVisible = false;
    },
    addNewTitle() {
      this.$refs["userForm"].validate((valid) => {
        if (valid) {
          // Form is valid, proceed with submission
          this.$API.enterprise
            .uploadEnterpriseInfo(this.userFormTmp)
            .then(async (resp) => {
              if (resp.data.code === 0) {
                await this.$store.dispatch("user/getUserInfo", {
                  identity: this.identity,
                });
                this.$notify({
                  type: "success",
                  message: "添加成功",
                });
                this.centerDialogVisible = false;
              }
            })
            .catch((err) => {
              console.log(err);
            });
        } else {
          // Form is invalid, display error messages
          this.$notify.error("信息填写不完整或有误");
          return false;
        }
      });
    },
  },
};
</script>

<style lang="less" scoped>
/deep/ .el-descriptions .is-bordered .el-descriptions-item__cell {
  height: 80px;
}
/deep/ .el-descriptions-item__label {
  width: 20%;
}
.el-dialog {
  .el-col {
    margin: 0 10px;
    .el-input__inner {
      width: 200px;
    }
  }
}
</style>