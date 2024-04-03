<template>
  <div>
    <header>
      <el-input v-model="input" placeholder="请输入内容" />
      <el-button type="primary" round @click="search">搜索</el-button>
      <el-button type="danger" round @click="handleDeleteByIds"
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
              <el-button type="primary" size="mini" round>修改</el-button>
              <el-button type="danger" @click="handleDeleteById(scope.row)" size="mini" round>删除</el-button>
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
  </div>
</template>

<script>
// import { conditionQuery, deleteByIds } from "@/api/enterprise";
export default {
  name: "enterprise",
  data() {
    return {
      input: "",
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
      this.multipleSelection = val;
    },
    handleDeleteByIds() {
      const selectedIds = this.multipleSelection.map((row) => row.id);
      // console.log(selectedIds)
      this.$API.enterprise.deleteByIds(selectedIds)
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
    getEnterpriseList() {
      this.$API.enterprise.conditionQuery(this.condition)
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
      this.$API.enterprise.deleteByIds(row.id)
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
    search(){
      this.condition.name = this.input
      this.getEnterpriseList()
    }
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
</style>

