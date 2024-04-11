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
                  展示 1 – {{condition.pageSize  }} 条中的
                  {{ pageList.total }} 结果
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
        resumeId:'1',
        skills:''
      },
    };
  },
  mounted() {
    this.getStudentList();
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
      // console.log(1111);
      console.log(this.condition);
      this.getStudentList();
    },
  },
};
</script>

<style lang="less" scoped>
.job_container {
  // background-image: url("~@/assets/bg.png");
  // background-color: #f2f2f2;
  // position: relative;
  // background-size: 1920px 1920px;
  // background-position: center center;
  // padding-top: 60px;
  // padding-bottom: 60px;
  .container {
    width: 100%;
    // max-width: 1230px;
    // height: 1000px;
    margin: 0 auto;
    // border: 1px solid sandybrown;
    .job_main {
      position: relative;
      // background: #fafafa;
      // border-radius: 10px;
      // box-shadow: 1px 0 20px -9px rgba(0, 0, 0, 0.3);
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
          // .search {
          //   display: flex;
          //   padding: 0 10%;
          //   .pass_input {
          //     // float: left;
          //     // width: 215px;
          //     margin-left: 10px;
          //     position: relative;
          //     // el-input__inner是el-input的input类名
          //     & /deep/ .el-input__inner {
          //       // border: none;
          //       border-radius: 20px;
          //       // height: 101%;
          //     }
          //     i {
          //       position: absolute;
          //       top: 13px;
          //     }
          //     .pass_button {
          //       margin-top: 1px;
          //       // border: #f2f2f2;
          //       border: none;
          //       border-radius: 20px;
          //     }
          //   }
          // }
        }
      }
    }
  }
}
</style>