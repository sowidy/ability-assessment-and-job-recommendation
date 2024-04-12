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
      <div style="display:flex;margin-left:20%;margin-bottom:5%">
        您的总评分为：
        <el-rate
        
          v-model="entryRod"
          disabled
          text-color="#ff9900"
          :max=10
          :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
        >
        </el-rate>
        <animate-number
          from="1"
          style="color:#ff9900"
          :to="entryRod"
          :key="entryRod"
        ></animate-number>
      </div>
      <!-- <el-divider></el-divider> -->

      <div class="sugges">
        <div class="title">
          <h2>能力评价详情</h2>
          <el-button type="primary" round @click="sendShow">反馈</el-button>
        </div>
        <div class="exp">
          <div class="edu">
            <span>{{ scoreForm.suggest }}</span>
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
      entryRod: 5,
      rodNumber: null,
      scoreForm: {
        // educationScore:'',
        // certificateScore:'',
        // skillsScore:'',
        // experienceScore:'',
        // personalScore:'',
        // communicationScore:'',
        // totalScore:'',
        // suggest:''
      },
    };
  },
  created() {
    //数字定时器动态循环设置
    // this.rodNumber = setInterval(() => {
    //   this.entryRod++;
    // }, 5000); // 每隔 1 秒更新数字的值
  },
  beforeDestroy() {
    clearInterval(this.rodNumber); // 在组件销毁前清除定时器
  },
  computed: {
    ...mapState("user", ["userInfo", "identity"]),
  },
  methods: {
    async getScore() {
      await this.$API.user
        .getAbilityScoreByStudentId()
        .then((resp) => {
          if (resp.data.code == 0) {
            this.scoreForm = resp.data.data;
            this.entryRod = Number(this.scoreForm.totalScore)
            this.drawChart();
          } else this.$notify.error("获取失败,请重新上传");
        })
        .catch((err) => {
          console.log(err);
        });
    },
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
            { name: "证书评估", max: 10 },
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
                value: [
                  this.scoreForm.educationScore,
                  this.scoreForm.skillsScore,
                  this.scoreForm.experienceScore,
                  this.scoreForm.personalScore,
                  this.scoreForm.communicationScore,
                  this.scoreForm.certificateScore,
                ],
                name: "能力评价",
              },
            ],
          },
        ],
      });
    },
    sendShow() {
      this.$bus.$emit("showDialog");
    },
  },
  async mounted() {
    await this.getScore();
    console.log(this.scoreForm, "score");
    this.$bus.$on("getScore", this.getScore);
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