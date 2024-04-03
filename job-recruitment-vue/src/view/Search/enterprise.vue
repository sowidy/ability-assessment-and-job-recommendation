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
              展示 1 – {{ condition.pageSize }} 中的 {{ pageList.total }} 条结果
            </div>
            <div class="sort">
              排序：
              <el-select
                v-model="condition.sortBy"
                placeholder="请选择"
                @change="searchByCondition"
              >
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
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
                  :max="1000000"
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
      tagValues: ["Java", "php", "python", " Android"],
      options: [
        {
          value: "name",
          label: "名称",
        },
        {
          value: "salary_min",
          label: "薪资升序",
        },
        {
          value: "salary_max",
          label: "薪资降序",
        },
        {
          value: "address",
          label: "地址",
        },
        {
          value: "type",
          label: "类型",
        },
      ],
      salaryValue: [4000, 400000],
      currentPage: 1,
      condition: {
        address: "",
        enterpriseAuth: "0",
        isAsc: "true",
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
  },
};
</script>

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
          margin-top: 5px;
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
        height: 57px;
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