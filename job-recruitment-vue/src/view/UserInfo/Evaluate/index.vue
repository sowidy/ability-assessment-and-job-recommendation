<template>
  <div>
    <div class="empty" v-if="!userInfo.resumeId">
      <el-empty
        default="您还没有上传简历信息"
        description="您还没有上传简历信息"
      >
        <el-button type="primary" round @click="goResumeEdit"
          >点击跳转上传</el-button
        >
      </el-empty>
    </div>
    <div v-else class="not-empty">
      <div class="charts" ref="charts"></div>
      <div class="reupload">
        <h2 style="text-align: center; hignt: 40px" @click="goResumeEdit">
          不满意？点击再次上传获得新建议<i class="el-icon-right"></i>
        </h2>
      </div>
      <!-- <transition leave-active-class="animate__animated animate__fadeOutDown">
        <div class="remark" >
          <el-card>
            <div slot="header">
              <h3>写下您对平台的宝贵一件，您的每条建议我们都会一一细看</h3>
            </div>
            <div>
              <el-input
                type="textarea"
                v-model="userForm.remark"
                placeholder="写下您的宝贵意见"
              ></el-input>
            </div>
            <el-button type="primary" @click="upRemark">提交</el-button>
          </el-card>
        </div>
      </transition> -->
    </div>
  </div>
  <!-- </el-card> -->
</template>


<script>
import { mapState } from "vuex";
export default {
  name: "Evaluate",
  data() {
    return {
      value: "全部渠道",
      value2: "",
      // userForm: {
      //   remark: "",
      // },
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
        // title: {
        //   text: "视频",
        //   subtext: 1048,
        //   left: "center",
        //   top: "center",
        // },
        // tooltip: {
        //   trigger: "item",
        // },
        // series: [
        //   {
        //     name: "技能评价表",
        //     type: "pie",
        //     radius: ["40%", "70%"],
        //     avoidLabelOverlap: false,
        //     itemStyle: {
        //       borderRadius: 10,
        //       borderColor: "#fff",
        //       borderWidth: 2,
        //     },
        //     label: {
        //       show: true,
        //       position: "outsize",
        //     },
        //     labelLine: {
        //       show: true,
        //     },
        //     data: [
        //       { value: 1048, name: "视频" },
        //       { value: 735, name: "Direct" },
        //       { value: 580, name: "Email" },
        //       { value: 484, name: "Union Ads" },
        //       { value: 300, name: "Video Ads" },
        //     ],
        //   },
        // ],

        title: {
          text: "Basic Radar Chart",
        },
        legend: {
          data: ["Allocated Budget", "Actual Spending"],
        },
        radar: {
          // shape: 'circle',
          indicator: [
            { name: "Sales", max: 6500 },
            { name: "Administration", max: 16000 },
            { name: "Information Technology", max: 30000 },
            { name: "Customer Support", max: 38000 },
            { name: "Development", max: 52000 },
            { name: "Marketing", max: 25000 },
          ],
        },
        series: [
          {
            name: "Budget vs spending",
            type: "radar",
            data: [
              {
                value: [4200, 3000, 20000, 35000, 50000, 18000],
                name: "Allocated Budget",
              },
              {
                value: [5000, 14000, 28000, 26000, 42000, 21000],
                name: "Actual Spending",
              },
            ],
          },
        ],
      }); //绑定事件
      mychart.on("mouseover", (params) => {
        //获取鼠标移上去那条数据
        const { name, value } = params.data;
        //重新设置标题
        mychart.setOption({
          title: {
            text: name,
            subtext: "value",
          },
        });
      });
    },
    goResumeEdit() {
      this.$router.push({ name: "Resume" });
    },
    // upRemark() {
    //   this.$API.user
    //     .updateUser(this.userForm, this.identity)
    //     .then((resp) => {
    //       console.log(resp.data);
    //       if (resp.data.code === 0) {
    //         this.$store.dispatch("user/getUserInfo", {
    //           identity: this.identity,
    //         });
    //         this.$message({
    //           type: "success",
    //           message: "感谢您的支持",
    //         });
    //       } else console.log(resp.data);
    //     })
    //     .catch((err) => {
    //       console.log(err);
    //     });
    // },
  },
  mounted() {
    console.log(this.userInfo);
    this.drawChart();
  },
};
</script>

<style lang="less" scoped>
// .remark {
//   height: 400px;
//   width: 100%;
//   // border: 1px solid sandybrown;
//   .el-card {
//     margin-top: 20%;
//     height: 100%;
//     box-shadow: none;
//     border: none;
//     text-align: center;
//     .el-button {
//       margin-top: 5%;
//     }
//   }
// }
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
  /* height: 100%; */
  height: 500px;
}
</style>
