<template>
  <div>
    <main>
      <div class="job_container">
        <div class="container">
          <div class="row job_main">
            <!-- <h3>寻找最适合的</h3> -->
          </div>
          <div class="list">
            <div class="left-list">
              <div class="jobtips">
                <div class="info">
                  展示 {{ pageList.total == 0 ? "0" : "1" }} –
                  {{ pageList.total }} 条中的 {{ showListSize }} 结果
                </div>
                <div class="sort">
                  <span>排序：</span>
                  <div
                    class="right"
                    @click="change(item, index)"
                    v-for="(item, index) in options"
                    :key="index"
                  >
                    {{ item.lable }}
                    <div class="box-icon">
                      <div
                        class="up"
                        :class="item.status === 0 ? 'opacity-5' : ''"
                      ></div>
                      <div
                        class="down"
                        :class="item.status === 0 ? 'opacity-1' : ''"
                      ></div>
                    </div>
                  </div>
                </div>
              </div>
              <el-divider></el-divider>

              <StuCard :list="pageList.list"></StuCard>
              <div class="block">
                <el-pagination
                  background
                  @current-change="handleCurrentChange"
                  :current-page="condition.pageNo"
                  :page-sizes="[10, 20, 30, 40]"
                  :page-size="condition.pageSize"
                  layout="prev, pager, next,->,total"
                  :total="pageList.total"
                >
                </el-pagination>
              </div>
            </div>
            <div class="right-bar">
              <div class="title"><span>筛选求职者</span></div>
              <el-divider></el-divider>
              <el-card>
                <div>
                  <div class="block">
                    <span>姓名搜索</span>
                    <div class="search">
                      <el-input
                        v-model="condition.name"
                        placeholder="请输入内容"
                        @keydown.enter.native="searchByCondition"
                        class="pass_input"
                      >
                      </el-input>
                    </div>
                  </div>
                  <div class="block">
                    <span>学校</span>
                    <div class="search">
                      <el-input
                        v-model="condition.school"
                        placeholder="请输入学校"
                        class="pass_input"
                        @keydown.enter.native="searchByCondition"
                      >
                      </el-input>
                    </div>
                  </div>
                  <div class="block">
                    <span>学历</span>
                    <div class="search">
                      <el-input
                        v-model="condition.education"
                        placeholder="请输入内容"
                        @keydown.enter.native="searchByCondition"
                        class="pass_input"
                      >
                      </el-input>
                    </div>
                  </div>
                  <div class="block">
                    <span>专业</span>
                    <div class="search">
                      <el-input
                        v-model="condition.major"
                        @keydown.enter.native="searchByCondition"
                        placeholder="请输入内容"
                        class="pass_input"
                      >
                      </el-input>
                    </div>
                  </div>
                </div>
              </el-card>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
export default {
  name: "Staff",
  data() {
    return {
      pageList: [],
      condition: {
        isAsc: "true",
        name: "",
        pageNo: 1,
        pageSize: 5,
        sortBy: "",
        education: "",
        gender: "",
        major: "",
        school: "",
        resumeId: "1",
        skills: "",
      },
      options: [
        { lable: "年龄", status: "0", value: "born_year" },
        // { lable: "最低薪资", status: "0", value: "salary_min" },
        // { lable: "最高薪资", status: "0", value: "salary_max" },
        // { lable: "地址", status: "0", value: "address" },
      ],
    };
  },
  mounted() {
    this.getStudentList();
  },
  computed: {
    showListSize() {
      return this.pageList.total < this.condition.pageSize
        ? this.pageList.total
        : this.condition.pageSize;
    },
  },
  methods: {
    getStudentList() {
      this.$API.user
        .queryStudent(this.condition)
        .then((resp) => {
          // console.log(resp.data);
          if (resp.data.code === 0) {
            this.pageList = resp.data.data;
            // console.log(this.StudentList, "###");
          } else console.log(resp.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`);
      this.condition.pageNo = val;
      this.getStudentList();
    },
    searchByCondition() {
      this.getStudentList();
    },
    change(item, index) {
      console.log(item, index);
      this.options[index].status === 0
        ? (this.options[index].status = 1)
        : (this.options[index].status = 0);
      this.condition.sortBy = this.options[index].value;
      this.condition.isAsc = this.options[index].status;
      console.log(this.condition);
      this.searchByCondition();
    },
  },
};
</script>

<style lang="less" scoped>
.job_container {
  .container {
    width: 100%;
    // max-width: 1230px;
    // height: 1000px;
    margin: 0 auto;
    // border: 1px solid sandybrown;
    .job_main {
      position: relative;

      margin-left: auto;
      margin-right: auto;
      overflow: hidden;
      padding: 0;
      text-align: center;
    }
    .list {
      // height: 100%;
      display: flex;
      // border: 1px solid rgb(22, 12, 3);
      .left-list {
        // border: 1px solid rgb(22, 12, 3);
        width: 70%;
        padding: 1% 3%;
        // padding-left: 5%;

        .jobtips {
          height: 52px;
          display: flex;
          justify-content: space-between;
          // background: #2dced4;
          background-image: linear-gradient(
            200deg,
            rgba(0, 181, 188, 0.89) 0%,
            rgba(62, 142, 240, 0.89) 100%
          );
          border-top-left-radius: 15px;
          border-top-right-radius: 15px;
          .info {
            margin-top: 2%;
            padding: 0 10px;
          }
          .sort {
            // width: 500px;

            margin-top: 1%;
            margin-right: 2%;
            display: flex;
            text-align: center;
            // border: 1px saddlebrown solid;
            span {
              margin-top: 1.5%;
            }
          }
        }
        .block {
          // margin: 0 auto;
          // text-align: center;
          margin-top: 5%;
          margin-left: 45%;
        }
      }

      .right-bar {
        width: 30%;
        // padding: 5% 5%;
        // padding: 1% 3%;
        .title {
          font-size: 25px;
          margin-top: 13px;
          height: 54px;
          text-align: center;
          // background: #2dced4;
          background-image: linear-gradient(
            135deg,
            rgba(0, 181, 188, 0.89) 0%,
            rgba(62, 142, 240, 0.89) 100%
          );
          border-top-left-radius: 15px;
          border-top-right-radius: 15px;
          span {
            line-height: 60px;
            text-align: center;
          }
        }
        // border: 1px solid rgb(22, 12, 3);
        .el-card {
          margin: 5% 0;

          .block {
            height: 120px;
            span {
              margin: 5% 2%;
              display: block;

              // padding-left: 3%;
            }
          }
        }
      }
    }
  }
}
</style>
<style lang="less" scoped>
:root {
  --animate-duration: 500ms;
  --animate-delay: -2s;
}
.right {
  padding: 0 20px;
  height: 32px;
  color: #505363;
  font-weight: 400;
  font-size: 14px;
  margin-left: 10px;
  border: 1px solid rgb(233, 231, 231);
  display: flex;
  justify-content: center;
  line-height: 32px;
  cursor: pointer;
}
.right:hover {
  color: #217aff;
  border: 1px solid #217aff;
  .up {
    border-bottom: 6px solid #217aff;
  }
  .down {
    border-top: 6px solid #217aff;
  }
}
.opacity-5 {
  opacity: 0.5;
}
.opacity-1 {
  opacity: 1 !important;
}
.box-icon {
  height: 30px;
  margin-top: 7px;
  .up {
    width: 0px; /*设置宽高为0，所以div的内容为空，从才能形成三角形尖角*/
    height: 0px;
    border-bottom: 6px solid #a3a5b3;
    border-left: 4px solid transparent; /*transparent 表示透明*/
    border-right: 4px solid transparent;
    margin-bottom: 4px;
  }
  .down {
    width: 0px;
    height: 0px;
    opacity: 0.5;
    border-top: 6px solid #a3a5b3;
    border-left: 4px solid transparent;
    border-right: 4px solid transparent;
  }
}
.box-icon div {
  height: 10px;
  margin-left: 4px;
}
</style>