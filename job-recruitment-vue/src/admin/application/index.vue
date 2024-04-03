<template>
  <div>
    <el-card>
      <div>
        <header>
          <el-input v-model="searchInput" placeholder="请输入内容" />
          <el-select
            v-model="selectValue"
            placeholder="请选择"
            @change="handleSelectChange"
          >
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
          <el-button type="primary" round @click="search">搜索</el-button>
        </header>
        <main>
          <el-table
            ref="multipleTable"
            :data="enterprises.list"
            tooltip-effect="dark"
            style="width: 110%"
            size="medium"
            fit
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection"/>
            <el-table-column type="index" label="序号" />
            <el-table-column prop="avatar" label="logo">
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
            <el-table-column prop="hiringManager" label="负责人" />
            <el-table-column prop="phone" label="电话号码" />
            <el-table-column prop="enterpriseAuth" label="认证">
              <template slot-scope="scope">
                <div>
                  <el-tag v-if="scope.row.enterpriseAuth == 0" type="success"
                    >已认证</el-tag
                  >
                  <el-tag
                    v-else-if="scope.row.enterpriseAuth == 1"
                    type="warning"
                    >待认证</el-tag
                  >
                  <el-tag v-else type="danger">未认证</el-tag>
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="materials" label="申请材料">
              <template slot-scope="scope">
                <div>
                  <el-button
                    type="primary"
                    size="mini"
                    v-if="scope.row.materials"
                    @click="handleDownload(scope.row.materials)"
                  >
                    下载<i class="el-icon-download el-icon--right"></i>
                  </el-button>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <div class="opration">
                  <el-button
                    type="primary"
                    size="mini"
                    round
                    @click="handleModify(scope.row)"
                    >修改</el-button
                  >
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
          <el-dialog
            title="修改认证"
            :visible.sync="centerDialogVisible"
            width="40%"
            center
          >
            <span>认证属性：</span>
            <el-tag type="success">已认证</el-tag>
            <el-tag type="warning">待认证</el-tag>
            <el-tag type="danger">未认证</el-tag><br /><br />
            <span class="cuser">当前用户：</span>
            <img
              :src="currentRow.logo"
              style="width: 50px"
              alt=""
            /><br /><br />
            <span>当前认证状态：</span>
            <el-tag v-if="currentRow.enterpriseAuth == 0" type="success"
              >已认证</el-tag
            >
            <el-tag v-else-if="currentRow.enterpriseAuth == 1" type="warning"
              >待认证</el-tag
            >
            <el-tag v-else type="danger">未认证</el-tag><br /><br />
            <span>修改认证状态为：</span>
            <el-select v-model="modityValue" placeholder="">
              <el-option
                v-for="item in options2"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
            <span slot="footer" class="dialog-footer">
              <el-button
                @click="(centerDialogVisible = false), (modityValue = '')"
                >取 消</el-button
              >
              <el-button type="primary" @click="handleAuthModify"
                >确 定</el-button
              >
            </span>
          </el-dialog>
        </main>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "Application",
  data() {
    return {
      searchInput: "",
      multipleSelection: [],
      enterprises: [],
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
      options: [
        {
          value: "",
          label: "全部",
        },
        {
          value: "0",
          label: "已认证",
        },
        {
          value: "1",
          label: "待认证",
        },
        {
          value: "2",
          label: "未认证",
        },
      ],
      options2: [
        {
          value: "0",
          label: "已认证",
        },
        {
          value: "2",
          label: "未认证",
        },
      ],
      selectValue: "",
      modityValue: "",
      centerDialogVisible: false,
      currentRow: "",
    };
  },
  mounted() {
    this.getEnterpriseList();
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
      const selectedIds = val.map(row => row.id);
      console.log(selectedIds);
      // console.log(val);
      // this.multipleSelection = val;
      // console.log(this.multipleSelection);
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
      // console.log(`当前页: ${val}`);
      this.condition.pageNo = val;
      this.getEnterpriseList();
    },
    handleSelectChange() {
      // console.log(this.selectValue);
      this.condition.enterpriseAuth = this.selectValue;
      this.getEnterpriseList();
    },
    handleDownload(materials) {
      // console.log(materials);
      window.location.href = materials;
    },
    handleModify(row) {
      this.currentRow = row; // 将当前行的数据保存在currentRow中
      this.centerDialogVisible = true; // 打开对话框
    },
    handleAuthModify() {
      // console.log(this.modityValue);
      // console.log(this.currentRow);
      this.currentRow.enterpriseAuth = this.modityValue;
      this.$API.enterprise
        .uploadInfoById(this.currentRow)
        .then((resp) => {
          // console.log(resp.data);
          if (resp.data.code === 0) {
            this.centerDialogVisible = false;
            this.modityValue = "";
            this.$message({
              type: "success",
              message: "修改成功",
            });
            this.getEnterpriseList();
          }
        })
        .catch((err) => {
          console.log(err);
        });
      // console.log(this.currentRow);
    },
    search(){
      this.condition.name = this.searchInput;
      this.getEnterpriseList();
    }
  },
};
</script>

<style lang="less" scoped>
header {
  display: flex;
  padding: 20px;
  height: 100px;
  .el-input {
    width: 300px;
    line-height: 100px;
  }
  .el-select {
    line-height: 100px;
    margin-left: 10px;
  }
  .el-button {
    margin-left: 10px;
    // line-height: 100px;
    margin-top: 2.5%;
    height: 40px;
  }
}
main {
  .table-container {
    // max-height: 400px; /* Adjust the maximum height as needed */
    overflow-y: auto; /* Add vertical scrollbar when content overflows */
  }
  .pagination {
    // margin: 3% auto;
    // padding-left: 5%;
    margin: 2% auto;
    // margin-top:5% ;
  }
  .opration {
    display: flex;
  }
  .el-dialog {
    .el-tag,
    .el-select,
    img {
      margin-left: 20px;
    }
    .cuser {
      text-align: start;
      padding-bottom: 10px;
      margin-bottom: 10px;
    }
  }
}
// .el-table-column .el-button {
//   margin-right: 5px; /* Add some spacing between buttons */
// }
</style>

