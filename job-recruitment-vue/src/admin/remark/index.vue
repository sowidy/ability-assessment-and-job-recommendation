<template>
  <div>
    <header>
      <el-input v-model="input"  placeholder="请输入内容" />
      <el-button type="primary" round @click="search">搜索</el-button>
      <el-button
        type="danger"
        round
      >批量删除</el-button>
    </header>
    <main>
      <el-table
        ref="multipleTable"
        :data="remarkList.list"
        tooltip-effect="dark"
        style="width: 100%"
        size="medium"
        fit
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" />
        <el-table-column type="index" label="序号"  width="100"/>
        <!-- <el-table-column prop="avatar" label="头像" width="120">
          <template slot-scope="scope">
            <div>
              <img :src="scope.row.avatar" alt="" style="width: 50px">
            </div>
          </template>
        </el-table-column> -->
        <el-table-column prop="name" label="姓名" width="200" >
          <template slot-scope="scope">
            <div>
              <el-tag type="success">{{ scope.row.name }}</el-tag>
            </div>
          </template>
        </el-table-column>
        <!-- <el-table-column prop="phone" label="电话号码" width="120" /> -->
        <!-- <el-table-column prop="bornYear" label="年龄" width="120">
          <template slot-scope="scope">
            <div>{{ getCurrentAge(scope.row.bornYear) }}</div>
          </template>
        </el-table-column> -->
        <el-table-column prop="remarkText" label="反馈信息"/>
        <el-table-column prop="totalScore" label="系统总打分" width="200"/>
        <!-- <el-table-column prop="school" label="学校" /> -->
        <!-- <el-table-column prop="major" label="专业" /> -->
        <!-- <el-table-column label="操作">
          <template slot-scope="scope">
            <div class="opration">
              <el-button type="primary" size="mini" round>修改</el-button>
              <el-button
                type="danger"
                size="mini"
                round
                @click="handleDeleteById(scope.row)"
              >删除</el-button>
            </div>
          </template>
        </el-table-column> -->
      </el-table>
      <el-pagination
        :current-page.sync="condition.pageNo"
        background
        class="pagination"
        :page-size="condition.pageSize"
        layout="prev, pager, next,->,total"
        :total="remarkList.total"
        @current-change="handleCurrentChange"
      />
    </main>
  </div>
</template>

<script>
// import { queryStudent, deleteByIds } from '@/api/student'
export default {
  name: 'Remark',
  data() {
    return {
      input: '',
      multipleSelection: [],
      remarkList: [],
      condition: {
        isAsc: 'true',
        name: '',
        pageNo: 1,
        pageSize: 10,
        sortBy: '',
        remark:'',
        remarkText:'',
        totalScore:'',
      }
    }
  },
  mounted() {
    this.getRemarkListList()
  },
  methods: {
    toggleSelection(rows) {
      if (rows) {
        rows.forEach((row) => {
          this.$refs.multipleTable.toggleRowSelection(row)
        })
      } else {
        this.$refs.multipleTable.clearSelection()
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    getRemarkListList() {
      this.$API.user.getRemarkVoByPage(this.condition)
        .then((resp) => {
          console.log(resp)
          if (resp.data.code === 0) {
            this.remarkList = resp.data.data
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`)
      this.condition.pageNo = val
      this.getRemarkListList()
    },
    getCurrentAge(bornYear) {
      const birthDate = new Date(bornYear)
      const currentDate = new Date()
      const age = currentDate.getFullYear() - birthDate.getFullYear()

      // 如果当前月份小于出生月份，或者当前月份等于出生月份但是当前日期小于出生日期，则年龄减一
      if (
        currentDate.getMonth() < birthDate.getMonth() ||
        (currentDate.getMonth() === birthDate.getMonth() &&
          currentDate.getDate() < birthDate.getDate())
      ) {
        return age - 1
      }

      return age
    },
    handleDeleteByIds() {
      const selectedIds = this.multipleSelection.map((row) => row.id)
      // console.log(selectedIds)
      this.$API.user.deleteByIds(selectedIds)
        .then((resp) => {
          // console.log(resp.data);
          if (resp.data.code === 0) {
            this.getRemarkListList()
            this.$message({
              type: 'success',
              message: '操作成功'
            })
          } else this.$message.error(resp.data.message)
          // 在这里处理删除成功后的逻辑，例如刷新数据列表等
        })
        .catch((err) => {
          console.error(err)
          // 在这里处理删除失败后的逻辑
        })
    },
    handleDeleteById(row) {
      // console.log(row)
      this.$API.user.deleteByIds(row.id)
        .then((resp) => {
          // console.log(resp.data);
          if (resp.data.code === 0) {
            this.getRemarkListList()
            this.$message({
              type: 'success',
              message: '操作成功'
            })
          } else this.$message.error(resp.data.message)
          // 在这里处理删除成功后的逻辑，例如刷新数据列表等
        })
        .catch((err) => {
          console.error(err)
          // 在这里处理删除失败后的逻辑
        })
    },
    search(){
      this.condition.name = this.input
      this.getRemarkListList();
    }
  }
}
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
</style>
