<template>
  <div class="dashboard-container">
    <div>
      <div>
        <el-row :gutter="30">
          <el-col :span="8">
            <div>
              <el-card>
                <div class="statistic">
                  <el-statistic
                    group-separator=","
                    :precision="0"
                    
                    title="总用户人数"
                  />
                </div>
                <span>
                  <animate-number
                    from="1"
                    :to="totalCount"
                    :key="totalCount"
                  ></animate-number>
                </span>
              </el-card>
            </div>
          </el-col>
          <el-col :span="8">
            <div>
              <el-card>
                <div class="statistic">
                  <el-statistic title="企业 vs 学生">
                    <template slot="formatter">
                      <animate-number
                        from="1"
                        :to="enterpriseCount"
                        :key="enterpriseCount"
                      ></animate-number>
                      /
                      <animate-number
                        from="1"
                        :to="studentCount"
                        :key="studentCount"
                      ></animate-number>
                    </template>
                  </el-statistic>
                </div>
              </el-card>
            </div>
          </el-col>
          <el-col :span="8">
            <div>
              <el-card>
                <div class="statistic">
                  <el-statistic
                    :value="like ? remarkCount : remarkCount"
                    title="反馈总记录数"
                  >
                    <template slot="suffix">
                      <span class="like" @click="like = !like">
                        <i
                          v-show="!!like"
                          class="el-icon-star-on"
                          style="color: #e6a23c"
                        />
                        <i v-show="!like" class="el-icon-star-off" />
                      </span>
                    </template>
                  </el-statistic>
                </div>
              </el-card>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>

    <el-card class="charts-card">
      <div>
        <h2><span>关于用户反馈的收集：</span></h2>
        <div ref="charts" class="charts" />
      </div>
    </el-card>

    <div class="status">
      <div class="chartsbar" ref="charts-bar" />
      <div class="chartsline" ref="charts-line" />
    </div>
    <div class="person">
      <BoxCard />
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import BoxCard from "./BoxCard";
export default {
  name: "dashboard",
  data() {
    return {
      like: true,

      studentCount: "0",
      enterpriseCount: "0",
      remarkCount: "",
      chartNameData: [],
      lineNameData: [],
      chartData: {
        eduData: [],
        skiData: [],
        expData: [],
        perData: [],
        comData: [],
        desData: [],
      },
      lineData: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    };
  },
  async mounted() {
    this.getRemarkCount();
    await this.getTotalCount();
    this.calculateChartData();
  },
  computed: {
    ...mapState("admin", ["name", "avatar"]),
    totalCount() {
      return this.studentCount + this.enterpriseCount || 0;
    },
  },
  methods: {
    async calculateChartData() {
      try {
        const resp = await this.$API.user.getAllRemark();
        if (resp.data.code === 0) {
          const remarkData = resp.data.data;
          for (let i = 0; i < remarkData.length; i++) {
            this.chartNameData.push(remarkData[i].name);
            this.lineNameData.push(remarkData[i].name);
            this.lineData[remarkData[i].totalScore - 1]++;
            for (let j = 0; j < remarkData[i].remark.length; j++) {
              let propertyName = Object.keys(this.chartData)[j];
              this.chartData[propertyName].push(remarkData[i].remark[j]);
            }
          }
          this.drawChart();
          this.drawBar();
          this.drawLine();
        }
      } catch (err) {
        console.log(err);
      }
    },
    drawChart() {
      const mychart = this.$echarts.init(this.$refs.charts);
      // const sortedChartData = [...this.chartData];
      // sortedChartData.sort((a, b) => a - b);
      mychart.setOption({
        tooltip: {
          trigger: "axis",
          axisPointer: {
            // Use axis to trigger tooltip
            type: "shadow", // 'shadow' as default; can also be 'line' or 'shadow'
          },
        },
        legend: {},
        grid: {
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true,
        },
        xAxis: {
          type: "value",
        },
        yAxis: {
          type: "category",
          data: this.chartNameData,
        },
        series: [
          {
            name: "学历评价反馈评分",
            type: "bar",
            stack: "total",
            label: {
              show: true,
            },
            emphasis: {
              focus: "series",
            },
            data: this.chartData.eduData,
          },
          {
            name: "专业技能评价反馈评分",
            type: "bar",
            stack: "total",
            label: {
              show: true,
            },
            emphasis: {
              focus: "series",
            },
            data: this.chartData.skiData,
          },
          {
            name: "工作经验评价反馈评分",
            type: "bar",
            stack: "total",
            label: {
              show: true,
            },
            emphasis: {
              focus: "series",
            },
            data: this.chartData.expData,
          },
          {
            name: "个人特质评价反馈评分",
            type: "bar",
            stack: "total",
            label: {
              show: true,
            },
            emphasis: {
              focus: "series",
            },
            data: this.chartData.perData,
          },
          {
            name: "交往特质评价反馈评分",
            type: "bar",
            stack: "total",
            label: {
              show: true,
            },
            emphasis: {
              focus: "series",
            },
            data: this.chartData.comData,
            // data: [1, 1, 1, 1, 1, 1],
          },
          {
            name: "决策能力评价反馈评分",
            type: "bar",
            stack: "total",
            label: {
              show: true,
            },
            emphasis: {
              focus: "series",
            },
            data: this.chartData.desData,
          },
        ],
      });
    },
    drawBar() {
      const mychart = this.$echarts.init(this.$refs["charts-bar"]);
      mychart.setOption({
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "cross",
            crossStyle: {
              color: "#999",
            },
          },
        },
        title: {
          text: "学生注册人数与企业注册人数图",
        },
        xAxis: {
          type: "category",
          data: ["学生注册数", "企业注册数"],
        },
        yAxis: {
          type: "value",
        },
        series: [
          {
            data: [this.studentCount, this.enterpriseCount],
            type: "bar",
            barWidth: "50%",
            itemStyle: {
              normal: {
                color: function (params) {
                  var colorList = ["#c23531", "#749f83"];
                  return colorList[params.dataIndex];
                },
              },
            },
          },
        ],
      });
    },
    drawLine() {
      const mychart = this.$echarts.init(this.$refs["charts-line"]);
      mychart.setOption({
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "cross",
            crossStyle: {
              color: "#999",
            },
          },
        },
        toolbox: {
          feature: {
            dataView: { show: true, readOnly: false },
            magicType: { show: true, type: ["line", "bar"] },
            restore: { show: true },
            saveAsImage: { show: true },
          },
        },
        legend: {
          data: ["系统反馈总分数"],
        },
        xAxis: [
          {
            type: "category",
            data: [
              "1分",
              "2分",
              "3分",
              "4分",
              "5分",
              "6分",
              "7分",
              "8分",
              "9分",
              "10分",
            ],
            axisPointer: {
              type: "shadow",
            },
          },
        ],
        yAxis: [
          {
            type: "value",
            name: "反馈人数",
            axisLabel: {
              formatter: "{value} ",
            },
          },
          {},
        ],
        series: [
          {
            name: "反馈人数",
            type: "bar",
            tooltip: {
              valueFormatter: function (value) {
                return value;
              },
            },
            data: this.lineData,
          },
          {
            name: "反馈人数",
            type: "line",
            yAxisIndex: 1,
            tooltip: {
              valueFormatter: function (value) {
                return value;
              },
            },
          },
        ],
      });
    },
    async getTotalCount() {
      await this.$API.user
        .getTotalCount("student")
        .then(async (resp) => {
          if (resp.data.code == 0) {
            this.studentCount = resp.data.data;
            await this.$API.user
              .getTotalCount("enterprise")
              .then((resp2) => {
                if (resp2.data.code === 0) {
                  this.enterpriseCount = resp2.data.data;
                }
              })
              .catch((err) => {
                console.log(err);
              });
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getRemarkCount() {
      this.$API.user
        .getRemarkCount()
        .then((resp) => {
          if (resp.data.code == 0) {
            this.remarkCount = resp.data.data;
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  components: {
    BoxCard,
  },
};
</script>

<style lang="less" scoped>
.dashboard-container {
  .el-card {
    padding: 5px;
    .persen-info {
      display: flex;
    }
  }
  .statistic {
    height: 100px;
    height: 100%;
  }
  .charts-card {
    margin-top: 10px;
    h2 {
      text-align: center;
    }
    .charts {
      margin: 20px 0;
      width: 100%;
      height: 100%;
      height: 300px;
    }
  }
  .status {
    margin: 15px 0;
    display: flex;

    .number {
      height: 400px;
      margin-left: 10px;
    }
    .chartsbar {
      // border: 1px solid saddlebrown;
      background-color: #fff;
      width: 30%;
      height: 100%;
      height: 400px;
      margin-right: 1%;
      padding: 0 1%;
    }
    .chartsline {
      // border: 1px solid saddlebrown;
      background-color: #fff;
      width: 69%;
      // width: 500px;
      height: 100%;
      height: 400px;
    }
  }
  .person {
    padding: 5px;
    height: 100%;
    width: 350px;
  }
}
</style>
<style lang="less" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
.like {
  cursor: pointer;
  font-size: 25px;
  display: inline-block;
}
</style>
