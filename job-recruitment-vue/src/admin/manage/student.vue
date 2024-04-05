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
        :data="studentList.list"
        tooltip-effect="dark"
        style="width: 100%"
        size="medium"
        fit
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column type="index" width="55" label="序号" />
        <el-table-column prop="avatar" label="头像" width="120" align="center">
          <template slot-scope="scope">
            <div>
              <img :src="scope.row.avatar" alt="" style="width: 50px" />
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="姓名" width="120" align="center">
          <template slot-scope="scope">
            <div>
              <el-tag type="success">{{ scope.row.name }}</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="phone"
          label="电话号码"
          width="120"
          align="center"
        />
        <el-table-column
          prop="bornYear"
          label="年龄"
          width="120"
          align="center"
        >
          <template slot-scope="scope">
            <div>{{ getCurrentAge(scope.row.bornYear) }}</div>
          </template>
        </el-table-column>
        <el-table-column
          prop="gender"
          label="性别"
          width="120"
          align="center"
        />
        <el-table-column
          prop="education"
          label="学历"
          width="100"
          align="center"
        />
        <el-table-column prop="school" label="学校" align="center" />
        <el-table-column prop="major" label="专业" width="120" align="center" />
        <el-table-column label="操作" width="140" align="center">
          <template slot-scope="scope">
            <div class="opration">
              <el-button
                type="primary"
                size="mini"
                round
                style="margin-right: 10px"
                @click="openDialog(scope.row)"
                >修改</el-button
              >

              <el-popconfirm
                confirm-button-text="确定"
                cancel-button-text="取消"
                icon="el-icon-info"
                icon-color="red"
                :title="`删除学生 ${scope.row.name}`"
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
        :total="studentList.total"
        @current-change="handleCurrentChange"
      />
    </main>

    <el-dialog title="修改" :visible.sync="dialogVisible" width="50%">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="10">
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
                <img v-if="form.avatar" :src="form.avatar" class="avatar" />
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
            <el-form-item label="姓名" prop="name">
              <el-input v-model="form.name" placeholder="填写姓名"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="gender">
              <el-select v-model="form.gender" placeholder="请选择性别">
                <el-option
                  v-for="item in genderOption"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="出生年月" prop="bornYear">
              <el-date-picker
                type="date"
                placeholder="选择日期"
                v-model="form.bornYear"
                style="width: 100%"
              ></el-date-picker>
            </el-form-item>
            <el-form-item label="电话号码" prop="phone">
              <el-input
                v-model="form.phone"
                placeholder="请输入电话号码"
              ></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" placeholder="填写邮箱"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
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
                :rows="9"
              ></el-input>
            </el-form-item>
            <el-form-item label="技能">
              <el-input
                type="textarea"
                v-model="form.skills"
                placeholder=""
                :rows="4"
              ></el-input>
            </el-form-item>
          </el-col>
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
  name: "student",
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
      studentList: [],
      dialogVisible: false,
      condition: {
        isAsc: "true",
        name: "",
        pageNo: 1,
        pageSize: 8,
        sortBy: "",
        education: "",
        gender: "",
        major: "",
        school: "",
      },
      form: {
        avatar: "",
        experience: "",
        skills: "",
        name: "",
        bornYear: "",
        gender: "",
        email: "",
        phone: "",
        school: "",
        major: "",
        education: "",
      },
      genderOption: [
        { value: "男", label: "男" },
        { value: "女", label: "女" },
      ],
      eduOption: [
        { value: "专科", label: "专科" },
        { value: "本科", label: "本科" },
        { value: "硕士", label: "硕士" },
        { value: "博士", label: "博士" },
      ],
      rules: {
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        gender: [{ required: true, message: "请选择性别", trigger: "blur" }],
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          { type: "email", message: "请输入合法的邮箱", trigger: "blur" },
        ],
        bornYear: [{ required: true, message: "请输入年龄", trigger: "blur" }],
        education: [{ required: true, message: "请选择学历", trigger: "blur" }],
        school: [{ required: true, message: "请选择学历", trigger: "blur" }],
        major: [{ required: true, message: "请选择学历", trigger: "blur" }],
        phone: [
          { required: true, message: "请输入电话", trigger: "blur" },
          { min: 11, max: 11, message: "电话号码不正确", trigger: "blur" },
          { validator: validatePhone, trigger: "blur" },
        ],
      },
    };
  },
  mounted() {
    this.getStudentList();
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
    getStudentList() {
      this.$API.user
        .queryStudent(this.condition)
        .then((resp) => {
          console.log(resp);
          if (resp.data.code === 0) {
            this.studentList = resp.data.data;
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
      this.getStudentList();
    },
    getCurrentAge(bornYear) {
      const birthDate = new Date(bornYear);
      const currentDate = new Date();
      const age = currentDate.getFullYear() - birthDate.getFullYear();

      // 如果当前月份小于出生月份，或者当前月份等于出生月份但是当前日期小于出生日期，则年龄减一
      if (
        currentDate.getMonth() < birthDate.getMonth() ||
        (currentDate.getMonth() === birthDate.getMonth() &&
          currentDate.getDate() < birthDate.getDate())
      ) {
        return age - 1;
      }

      return age;
    },
    handleDeleteByIds() {
      // console.log(selectedIds)

      this.$confirm("此操作将永久删除选择的学生信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          const selectedIds = this.multipleSelection.map((row) => row.id);
          this.$API.user
            .deleteByIds(selectedIds)
            .then((resp) => {
              // console.log(resp.data);
              if (resp.data.code === 0) {
                this.getStudentList();
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
    handleDeleteById(row) {
      // this.$notify({
      //   type:'success',
      //   message:`${row.name}`,
      // })
      // console.log(row)
      this.$API.user
        .deleteByIds(row.id)
        .then((resp) => {
          // console.log(resp.data);
          if (resp.data.code === 0) {
            this.getStudentList();
            this.$notify({
              type: "success",
              message: "删除成功",
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
      this.getStudentList();
    },
    cancelHandleDeleteById() {
      this.$notify({
        type: "success",
        message: "取消",
      });
    },
    openDialog(row) {
      this.form = {
        avatar: "",
        experience: "",
        skills: "",
        name: "",
        bornYear: "",
        gender: "",
        email: "",
        phone: "",
        school: "",
        major: "",
        education: "",
      };
      // 如果有传入数据，则填充表单数据
      if (row) {
        this.form = { ...this.form, ...row };
      }
      this.dialogVisible = true;
    },
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      this.form.avatar = res.data;
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
      const isLt2M = file.size / 1024 / 1024 < 2;
      // if (!isJPG) {
      //   this.$notify.error("上传头像图片只能是 JPG 格式!");
      // }
      if (!isLt2M) {
        this.$notify.error("上传头像图片大小不能超过 2MB!");
      }
      return  isLt2M//isJPG &&;
    },
    confirmUpdateUser() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          console.log(this.form);
          this.$API.user
            .updateUser(this.form, "student")
            .then((resp) => {
              if (resp.data.code == 0) {
                this.$notify({
                  type: "success",
                  message: "修改成功",
                });
                this.dialogVisible = false;
                this.getStudentList()
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
      this.dialogVisible = false;
      this.$refs.form.resetFields();
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
    margin: 3% 0;
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
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
