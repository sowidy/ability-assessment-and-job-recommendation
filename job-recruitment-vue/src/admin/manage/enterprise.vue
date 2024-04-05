<template>
  <div>
    <header>
      <el-input v-model="input" placeholder="请输入内容" />
      <el-button type="primary" round @click="search">搜索</el-button>
      <el-button
        type="danger"
        round
        @click="handleDeleteByIds"
        :disabled="multipleSelection.length == 0"
        >批量删除</el-button
      >
    </header>
    <main>
      <el-table
        ref="multipleTable"
        :data="enterprises.list"
        tooltip-effect="dark"
        style="width: 100%"
        size="medium"
        fit
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column type="index" width="55" label="序号" />
        <el-table-column prop="avatar" label="logo" width="120">
          <template slot-scope="scope">
            <div>
              <img :src="scope.row.logo" alt="" style="width: 50px" />
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="名称">
          <template slot-scope="scope">
            <div>
              <el-tag type="success">{{ scope.row.name }}</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="hiringManager" label="负责人" width="120" />
        <el-table-column prop="phone" label="电话号码" width="120" />
        <el-table-column prop="type" label="类型" width="120" />
        <el-table-column prop="address" label="地址" />
        <el-table-column prop="enterpriseAuth" label="认证" width="120">
          <template slot-scope="scope">
            <div>
              <el-tag v-if="scope.row.enterpriseAuth == 0" type="success"
                >已认证</el-tag
              >
              <el-tag v-else-if="scope.row.enterpriseAuth == 1" type="warning"
                >待认证</el-tag
              >
              <el-tag v-else type="danger">未认证</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <div class="opration">
              <el-button
                style="margin-right: 10px"
                type="primary"
                size="mini"
                round
                @click="openDialog(scope.row)"
                >修改</el-button
              >
              <!-- <el-button type="danger" @click="handleDeleteById(scope.row)" size="mini" round>删除</el-button> -->

              <el-popconfirm
                confirm-button-text="确定"
                cancel-button-text="取消"
                icon="el-icon-info"
                icon-color="red"
                :title="`删除企业 ${scope.row.name}`"
                @confirm="handleDeleteById(scope.row)"
                @cancel="cancelHandleDeleteById"
              >
                <el-button slot="reference" type="danger" size="mini" round
                  >删除</el-button
                >
                <!-- @click="handleDeleteById(scope.row)" -->
              </el-popconfirm>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        :current-page.sync="condition.pageNo"
        background
        class="pagination"
        :page-size="condition.pageSize"
        layout="prev, pager, next,->,total"
        :total="enterprises.total"
        @current-change="handleCurrentChange"
      />
    </main>

    <el-dialog title="修改企业信息" :visible.sync="dialogVisible" width="40%">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <!-- <el-col :span="10"> -->
          <el-form-item label="Logo">
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
              <img v-if="form.logo" :src="form.logo" class="avatar" />
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="姓名" prop="name">
            <el-input v-model="form.name" placeholder="填写姓名"></el-input>
          </el-form-item>
          <el-form-item label="类型" prop="type">
            <el-input v-model="form.type" placeholder="填写企业类型"></el-input>
          </el-form-item>
          <el-form-item label="地址" prop="address">
            <el-input
              v-model="form.address"
              placeholder="填写企业地址"
            ></el-input>
          </el-form-item>
          <el-form-item label="电话号码" prop="phone">
            <el-input
              v-model="form.phone"
              placeholder="请输入电话号码"
            ></el-input>
          </el-form-item>
          <el-form-item label="企业描述" prop="enterpriseDescription">
            <el-input
              type="textarea"
              v-model="form.enterpriseDescription"
              placeholder="填写企业描述信息"
            ></el-input>
          </el-form-item>
          <!-- <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" placeholder="填写邮箱"></el-input>
            </el-form-item> -->
          <!-- </el-col> -->
          <!-- <el-col :span="10">
            <el-form-item label="学历" prop="education">
              <el-select v-model="form.education" placeholder="请选择学历">
                <el-option
                  v-for="item in eduOption"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="学校" prop="school">
              <el-input v-model="form.school" placeholder="填写学校"></el-input>
            </el-form-item>
            <el-form-item label="专业" prop="major">
              <el-input v-model="form.major" placeholder="填写专业"></el-input>
            </el-form-item>
            <el-form-item label="工作经历">
              <el-input
                type="textarea"
                v-model="form.experience"
                placeholder=""
              ></el-input>
            </el-form-item>
            <el-form-item label="技能">
              <el-input
                type="textarea"
                v-model="form.skills"
                placeholder=""
              ></el-input>
            </el-form-item>
          </el-col> -->
        </el-row>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelUpdateUser">取 消</el-button>
        <el-button type="primary" @click="confirmUpdateUser">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
export default {
  name: "enterprise",
  data() {
    var validatePhone = (rule, value, callback) => {
      if (!value || /^1\d{10}$/.test(value)) {
        callback();
      } else {
        callback(new Error("请输入以1开头的11位数字"));
      }
    };
    return {
      input: "",
      multipleSelection: [],
      enterprises: [],
      dialogVisible: false,
      condition: {
        address: "",
        enterpriseAuth: "",
        isAsc: "true",
        name: "",
        title: "",
        pageNo: 1,
        pageSize: 10,
        salaryMax: "10000000000",
        salaryMin: "0",
        sortBy: "",
        type: "",
      },
      form: {
        logo: "",
        name: "",
        type: "",
        address: "",
        enterpriseDescription: "",
      },
      rules: {
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        type: [{ required: true, message: "请输入企业类型", trigger: "blur" }],
        address: [{ required: true, message: "请输入地址", trigger: "blur" }],
        enterpriseDescription: [
          { required: true, message: "请输入企业描述信息", trigger: "blur" },
        ],
        phone: [
          { required: true, message: "请输入电话", trigger: "blur" },
          { min: 11, max: 11, message: "电话号码不正确", trigger: "blur" },
          { validator: validatePhone, trigger: "blur" },
        ],
      },
    };
  },
  mounted() {
    this.getEnterpriseList();
  },
  computed: {
    token() {
      return getToken();
    },
  },
  methods: {
    toggleSelection(rows) {
      if (rows) {
        rows.forEach((row) => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleDeleteByIds() {
      // console.log(selectedIds)

      this.$confirm("此操作将永久删除选择的企业信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          const selectedIds = this.multipleSelection.map((row) => row.id);
          this.$API.enterprise
            .deleteByIds(selectedIds)
            .then((resp) => {
              // console.log(resp.data);
              if (resp.data.code === 0) {
                this.getEnterpriseList();
                this.$notify({
                  type: "success",
                  message: "操作成功",
                });
              } else this.$notify.error(resp.data.message);
              // 在这里处理删除成功后的逻辑，例如刷新数据列表等
            })
            .catch((err) => {
              console.error(err);
              // 在这里处理删除失败后的逻辑
            });
        })
        .catch(() => {
          this.$notify({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    getEnterpriseList() {
      this.$API.enterprise
        .conditionQuery(this.condition)
        .then((resp) => {
          console.log(resp);
          if (resp.data.code === 0) {
            this.enterprises = resp.data.data;
            // console.log(this.studentList, '@@@@@')
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.condition.pageNo = val;
      this.getEnterpriseList();
    },
    handleDeleteById(row) {
      // console.log(row)
      this.$API.enterprise
        .deleteByIds(row.id)
        .then((resp) => {
          // console.log(resp.data);
          if (resp.data.code === 0) {
            this.getEnterpriseList();
            this.$notify({
              type: "success",
              message: "操作成功",
            });
          } else this.$notify.error(resp.data.message);
          // 在这里处理删除成功后的逻辑，例如刷新数据列表等
        })
        .catch((err) => {
          console.error(err);
          // 在这里处理删除失败后的逻辑
        });
    },
    search() {
      this.condition.name = this.input;
      this.getEnterpriseList();
    },
    cancelHandleDeleteById() {
      this.$notify({
        type: "success",
        message: "取消",
      });
    },
    openDialog(row) {
      this.form = {
        logo: "",
        name: "",
        type: "",
        address: "",
        enterpriseDescription: "",
      };
      // 如果有传入数据，则填充表单数据
      if (row) {
        this.form = { ...this.form, ...row };
      }
      this.dialogVisible = true;
    },
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      this.form.logo = res.data;
      // this.$API.user
      //   .uploadStudentAvatar(this.imageUrl)
      //   .then((resp) => {
      //     if (resp.data.code == 0) {
      //       this.$notify({
      //         type: "success",
      //         message: "上传成功",
      //       });
      //     } else {
      //       this.imageUrl = "";
      //       this.handleError();
      //     }
      //   })
      //   .catch((err) => {
      //     this.$notify.error(err.message);
      //   });
    },
    handleError() {
      this.$notify.error("上传失败");
    },
    beforeAvatarUpload(file) {
      // const isJPG = file.type === "image/jpeg";
      // const isLt2M = file.size / 1024 / 1024 < 2;
      // if (!isJPG) {
      //   this.$notify.error("上传头像图片只能是 JPG 格式!");
      // }
      // if (!isLt2M) {
      //   this.$notify.error("上传头像图片大小不能超过 2MB!");
      // }
      // return isJPG && isLt2M;
    },
    confirmUpdateUser() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          console.log(this.form);
          this.$API.user
            .updateUser(this.form, "enterprise")
            .then((resp) => {
              if (resp.data.code == 0) {
                this.$notify({
                  type: "success",
                  message: "修改成功",
                });
                this.dialogVisible = false;
                this.getEnterpriseList();
              }
            })
            .catch((err) => {
              console.log(err);
            });
        } else {
          this.$notify.error("填写完整信息");
        }
      });
    },
    cancelUpdateUser() {
      this.$refs.form.resetFields();
      this.dialogVisible = false;
    },
  },
};
</script>

<style lang="less" scoped>
header {
  display: flex;
  padding: 20px;
  .el-input {
    width: 300px;
  }
  .el-button {
    margin-left: 10px;
  }
}
main {
  .table-container {
    // max-height: 400px; /* Adjust the maximum height as needed */
    overflow-y: auto; /* Add vertical scrollbar when content overflows */
  }
  .pagination {
    margin: 3% auto;
    // padding-left: 45%;
    // margin-top:5% ;
  }
  .opration {
    display: flex;
  }
}
// .el-table-column .el-button {
//   margin-right: 5px; /* Add some spacing between buttons */
// }
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

