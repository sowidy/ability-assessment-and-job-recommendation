<template>
  <div class="resume-evalution">
    <h1>能力评价</h1>
    <div class="empty" v-if="!userInfo.resumeId">
      <el-empty
        default="您还没有上传简历信息"
        description="您还没有上传简历信息"
      >
        <el-button type="primary" round>请先上传简历</el-button>
      </el-empty>
      <el-divider></el-divider>
    </div>
    <div v-else class="not-empty">
      <div class="charts" ref="charts"></div>
      <div class="sugges">
        <div class="title">
          <h2>能力评价详情</h2>
          <el-button type="primary" round @click="sendShow">反馈</el-button>
        </div>
        <div class="exp">
          <div class="edu">
            <span
              >能力评价：该学生表现出色，展现了扎实的学术能力，在专业知识的掌握和研究方面表现突出。他/她具备优秀的沟通能力，能够清晰表达自己的想法，并倾听他人的观点。在团队合作方面，该学生表现积极，能够有效地与他人合作，达成共同目标。此外，他/她展现出良好的组织和时间管理能力，在面对多项任务和压力时依然能够保持高效。该学生对未来职业有明确规划，并已经采取行动来为之做好准备，包括实习经历和职业技能的培养。</span
            >
          </div>
          <!-- <div class="ski"><span>专业技能评价：</span></div>
          <div class="exp"><span>工作经验评价：</span></div>
          <div class="per"><span>个人特质评价：</span></div>
          <div class="com"><span>交往特质评价：</span></div>
          <div class="com"><span>决策能力评价：</span></div> -->
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import { mapState } from "vuex";
export default {
  name: "ResumeEvaluate",
  data() {
    return {
      colors: ["#99A9BF", "#F7BA2A", "#FF9900"],
    };
  },
  computed: {
    ...mapState("user", ["userInfo", "identity"]),
  },
  methods: {
    drawChart() {
      let mychart = this.$echarts.init(this.$refs.charts);
      mychart.setOption({
        title: {
          text: "个人能力评价图",
          textStyle: {
            color: "rgba(221,221,221,1)", //标题颜色
            fontSize: 14,
            lineHeight: 20,
          },
          // 标题的位置，此时放在图的底边
          // left: "center",
          // top: "left",
        },
        legend: {
          data: ["技能评分"],
        },
        radar: {
          // shape: "circle",
          splitNumber: 5, // 雷达图圈数设置
          name: {
            textStyle: {
              color: "#838D9E",
            },
          },
          axisLine: {
            // 设置雷达图中间射线的颜色
            lineStyle: {
              color: "#c0c0c0",
            },
          },
          splitArea: {
            //设置图表颜色，show的值为true
            show: true,
            areaStyle: {
              // color:"#c1ddf8", //一般设置方式
              //设置渐变背景色 new echarts.graphic.LinearGradient(a,b,c,d,arr)
              //a ,b,c,d值可为0，1 a:1表示arr中的颜色右到左；c:1 arr中的颜色左到右
              //b:1表示arr中的颜色下到上；d:1表示arr中的颜色上到下
              color: new this.$echarts.graphic.LinearGradient(
                0,
                1,
                0,
                0,
                [
                  { offset: 0, color: "#c1ddf8" }, // 0% 处的颜色
                  { offset: 1, color: "#1e83e4" }, // 100% 处的颜色
                ],
                false
              ),
            },
          },
          splitLine: {
            //网格颜色设置
            show: true,
            lineStyle: {
              width: 1,
              color: "#1e83e4",
            },
          },
          indicator: [
            {
              name: "学历",
              max: 10,
              axisLabel: {
                show: true,
                fontSize: 12,
                color: "#000",
                showMaxLabel: false, //不显示最大值，即外圈不显示数字30
                showMinLabel: true, //显示最小数字，即中心点显示0
              },
            },
            { name: "专业技能", max: 10 },
            { name: "工作经验", max: 10 },
            { name: "个人特质", max: 10 },
            { name: "交往特质", max: 10 },
            { name: "决策能力", max: 10 },
          ],
        },
        series: [
          {
            name: "能力评分",
            type: "radar",
            label: {
              normal: {
                show: true, // 单个拐点文本的样式设置。[ default: false ]
                position: "top", // 标签的位置。[ default: top ]
                distance: 5, // 距离图形元素的距离。当 position 为字符描述值（如 'top'、'insideRight'）时候有效。[ default: 5 ]
                // color: "rgba(255,0,0,1)", // 文字的颜色。如果设置为 'auto'，则为视觉映射得到的颜色，如系列色。[ default: "#fff" ]
                fontSize: 14, // 文字的字体大小
              },
            },
            areaStyle: {
              //设置区域背景颜色透明度
              normal: {
                width: 1,
                opacity: 0.8,
              },
            },
            itemStyle: {
              //该数值区域样式设置
              normal: {
                color: "rgba(255,225,0,.3)", //背景颜色，还需设置areaStyle
                lineStyle: {
                  color: "rgba(255,225,0,.3)", //边框颜色
                },
              },
              emphasis: {
                // 高亮时的样式
                lineStyle: {
                  width: 5,
                },
                opacity: 1,
              },
            },
            data: [
              {
                value: [5, 7, 2, 6, 9, 4],
                name: "能力评价",
              },
            ],
          },
        ],
      });
      //绑定事件
      // mychart.on("mouseover", (params) => {
      //   //获取鼠标移上去那条数据
      //   const { name, value } = params.data;
      //   //重新设置标题
      //   mychart.setOption({
      //     title: {
      //       text: name,
      //       subtext: "value",
      //     },
      //   });
      // });
    },
    sendShow() {
      this.$bus.$emit("showDialog");
    },
  },
  mounted() {
    console.log(this.userInfo);
    this.drawChart();
  },
};
</script>

<style lang="less" scoped>
.resume-evalution {
  margin: 10px 0;
  // padding-left: 150px;
  .empty {
    padding: 20px 0;
  }
  .not-empty {
    margin: 10px 0;
    .sugges {
      .title {
        display: flex;
        justify-content: space-between;
      }
      .card {
        width: 50%;
      }
      .exp {
        div {
          margin: 10px;
        }
      }
    }
  }
}
</style>
<style scoped>
.category-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.header {
  border-bottom: 1px solid #eee;
}
.charts {
  width: 100%;
  height: 100%;
  height: 300px;
}
</style>