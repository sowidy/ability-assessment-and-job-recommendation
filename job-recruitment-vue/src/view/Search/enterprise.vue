<template>
  <div>
    <div class="container">
      <div class="toolbar">
        <div class="search-hot">
          <b>热门职位：</b>
          <el-tag
            type="success"
            @click="handleTagClick(i)"
            v-for="(i, index) in tagValues"
            :key="index"
            >{{ i }}</el-tag
          >
        </div>
      </div>
      <div class="result">
        <div class="joblist">
          <div class="jobtips">
            <div class="info">
              展示 1 – {{ pageList.total }} 中的 {{ condition.pageSize }} 条结果
            </div>

            <div class="sort">
              <span>排序：</span>

              <!-- <el-select
                v-model="condition.sortBy"
                placeholder="请选择"
                @change="searchByCondition"
              >
                <el-option
                  v-for="item in 1"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option> -->
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
              <!-- </el-select> -->
            </div>
          </div>
          <el-divider></el-divider>
          <JobCard :list="pageList.list"></JobCard>
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
        <div class="navbar">
          <div class="title"><span>筛选职位</span></div>
          <el-divider></el-divider>
          <el-card>
            <div>
              <!-- <el-card> -->
              <!-- <div slot="header"></div> -->
              <div class="block">
                <span class="title-info">职位搜索</span>
                <el-input
                  v-model="condition.title"
                  prefix-icon="el-icon-search"
                  placeholder="输入职位名称..."
                  @keydown.enter.native="searchByCondition"
                ></el-input>
              </div>
              <!-- </el-card> -->
              <!-- <el-card> -->
              <!-- <div slot="header">地点搜索</div> -->
              <div class="block">
                <span class="title-info">地点搜索</span>
                <el-input
                  placeholder="北京、天津..."
                  v-model="condition.address"
                  prefix-icon="el-icon-location-outline"
                  @keydown.enter.native="searchByCondition"
                ></el-input>
              </div>
              <!-- </el-card> -->
              <!-- <el-card> -->
              <!-- <div slot="header"></div> -->
              <div class="block">
                <span class="title-info">企业名称搜索</span>
                <el-input
                  v-model="condition.name"
                  prefix-icon="el-icon-office-building"
                  placeholder="输入企业名称..."
                  @keydown.enter.native="searchByCondition"
                ></el-input>
              </div>
              <!-- </el-card> -->
              <!-- <el-card> -->
              <!-- <div slot="header">薪资范围</div> -->
              <div class="block">
                <span class="title-info">薪资范围</span>
                <el-slider
                  v-model="salaryValue"
                  range
                  :max="100000"
                  @change="searchByCondition"
                  :debounce="1500"
                >
                </el-slider>
              </div>
              <!-- </el-card> -->
            </div>
          </el-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  data() {
    return {
      tagValues: [
        "高级前端工程师助理",
        "前端开发",
        "unity工程师",
        " vue前端开发",
      ],
      options: [
        { lable: "职位名称", status: "0", value: "title" },
        { lable: "最低薪资", status: "0", value: "salary_min" },
        { lable: "最高薪资", status: "0", value: "salary_max" },
        { lable: "地址", status: "0", value: "address" },
      ],
      salaryValue: [0, 100000],
      currentPage: 1,
      condition: {
        address: "",
        enterpriseAuth: "0",
        isAsc: "false",
        name: "",
        title: "",
        pageNo: 1,
        pageSize: 10,
        salaryMax: "",
        salaryMin: "",
        sortBy: "",
        type: "",
      },
      pageList: [],
      tagInput: "",
    };
  },
  computed: {
    ...mapState("user", ["identity"]),
  },
  beforeMount() {
    //在发请求之前，把接口需要传递参数，进行整理（在给服务器发请求之前，把参数整理好，服务器就会返回查询的数据
    this.condition.salaryMin = this.salaryValue[0];
    this.condition.salaryMax = this.salaryValue[1];
    Object.assign(this.condition);
  },
  mounted() {
    this.searchByCondition();
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
    allocationSalary() {
      this.condition.salaryMin = this.salaryValue[0];
      this.condition.salaryMax = this.salaryValue[1];
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.condition.pageNo = val;
      this.searchByCondition();
    },
    searchByCondition() {
      this.allocationSalary();
      // console.log(this.condition);
      this.$API.enterprise.conditionQuery(this.condition).then((resp) => {
        // console.log(resp.data.data);
        this.pageList = resp.data.data;
        // console.log(this.pageList);
      });
    },
    handleTagClick(tag) {
      this.tagInput = tag;
      this.condition.title = this.tagInput;
      this.searchByCondition();
    },
    change(item, index) {
      console.log(item, index);
      this.options[index].status === 0
        ? (this.options[index].status = 1)
        : (this.options[index].status = 0);
      this.condition.sortBy = this.options[index].value;
      this.condition.isAsc = this.options[index].status;
      this.searchByCondition();
    },
  },
};
</script>

<style lang="less" scoped>
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
<style lang='less' scoped>
.container {
  // margin: 5% 10%;
  // background-image: url("~@/assets/bg.png");
  // background-color: #f2f2f2;
  // padding: 3% 10%;
  // border: 1px sandybrown solid;
  .toolbar {
    // border: 1px salmon solid;
    padding: 0% 4%;
    height: 10%;
    b {
      font-size: 24px;
    }
    .el-tag {
      margin-right: 3%;
    }
  }

  .result {
    display: flex;
    text-align: center;
    line-height: 100%;
    justify-content: space-between;

    .joblist {
      padding: 1% 3%;
      width: 70%;

      .jobtips {
        // height: 2%;
        display: flex;
        height: 5%;
        padding: 10px 10px 0 10px;
        border-top-left-radius: 15px;
        border-top-right-radius: 15px;
        justify-content: space-between;
        text-align: center;
        // background: #2dced4;
        background-image: linear-gradient(
          200deg,
          rgb(0, 182, 188) 0%,
          rgba(62, 142, 240, 0.89) 100%
        );

        .info {
          margin-top: 2%;
        }
        .sort {
          width: 500px;

          margin-top: 1%;
          display: flex;
          text-align: center;
          // border: 1px saddlebrown solid;
          span {
            margin-top: 1.5%;
          }
        }
      }

      .block {
        margin-top: 5%;
      }
    }

    .navbar {
      padding: 1 3%;
      width: 30%;
      .title {
        font-size: 25px;
        margin-top: 15px;
        height: 74px;
        // text-align: center;
        // background: #2dced4;
        background-image: linear-gradient(
          135deg,
          rgb(0, 182, 188) 0%,
          rgba(62, 142, 240, 0.89) 100%
        );
        border-top-left-radius: 15px;
        border-top-right-radius: 15px;
        span {
          line-height: 60px;
        }
      }
      .el-card {
        padding: 10% 0;
        // margin: 0 auto;
        // height: 150px;
        text-align: left;

        .block {
          height: 120px;
        }
        .title-info {
          margin: 5% 2%;
          // height: 30px;
          display: block;
          // margin-bottom:100px;
        }
      }
    }
  }
}
</style>