<template>
  <div class="outer">
    <Header></Header>
    <main>
      <el-card class="upload-card">
        <div class="resume-op">
          <div class="resume-upload">
            <resumeUpload></resumeUpload>
          </div>
        </div>
      </el-card>

      <el-card class="eva-card">
        <div class="resume-evaluation">
          <resumeEvalution></resumeEvalution>
        </div>
      </el-card>

      <!-- <div class="resume-feedback">
        <el-dialog
          title="反馈能力评价"
          :visible.sync="dialogVisible"
          width="400"
          :before-close="dialogBeforeClose"
        >
          <div>
            <div>
              <span>学历评价：</span
              ><el-rate v-model="edu" show-text> </el-rate>
              <span>专业技能评价：</span
              ><el-rate v-model="ski" show-text> </el-rate>
              <span>工作经验评价：</span
              ><el-rate v-model="exp" show-text> </el-rate>
              <span>个人特质评价：</span
              ><el-rate v-model="per" show-text> </el-rate>
              <span>交往特质评价：</span
              ><el-rate v-model="com" show-text> </el-rate>
            </div>
          </div>
          <div slot="footer">
            <el-button @click="cancelRemark">取 消</el-button>
            <el-button type="primary" @click="upRemark">确 定</el-button>
          </div>
        </el-dialog>
      </div> -->
    </main>
    <Footer></Footer>
  </div>
</template>

<script>
import resumeUpload from "./resume-upload.vue";
import resumeEvalution from "./resume-evalution.vue";
// import { getIdentity } from "@/utils/token";
export default {
  name: "evaluation-recommendations",
  components: {
    resumeUpload,
    resumeEvalution,
  },
  data() {
    return {
      // dialogVisible: false,
      // edu: "",
      // ski: "",
      // exp: "",
      // per: "",
      // com: "",
      // userForm: {
      //   remark: "",
      // },
      rate: null,
    };
  },
  methods: {
    // dialogBeforeClose() {
    //   this.dialogVisible = false;
    // },
    // showDialog() {
    //   this.dialogVisible = true;
    // },
    // cancelRemark() {
    //   this.edu = "";
    //   this.ski = "";
    //   this.exp = "";
    //   this.per = "";
    //   this.com = "";
    //   this.dialogVisible = false;
    // },
    // upRemark() {
    //   let identity = getIdentity();
    //   // console.log(this.rate);
    //   // console.log(this.userForm.remark);
    //   this.userForm.remark =
    //     this.edu +
    //     "" +
    //     this.ski +
    //     "" +
    //     this.exp +
    //     "" +
    //     this.per +
    //     "" +
    //     this.com;
    //   console.log(this.userForm.remark);
    //   this.dialogVisible = false;
    //   this.$API.user
    //     .updateUser(this.userForm, identity)
    //     .then((resp) => {
    //       console.log(resp.data);
    //       if (resp.data.code === 0) {
    //         this.$store.dispatch("user/getUserInfo", { identity });
    //         this.$message({
    //           type: "success",
    //           message: "感谢您的支持",
    //         });
    //         this.edu =this.ski = this.exp = this.per = this.com= "" ;
    //         this.userForm.remark = "";
    //       } else console.log(resp.data);
    //     })
    //     .catch((err) => {
    //       console.log(err);
    //     });
    // },
  },
  mounted() {
    this.$bus.$on("showDialog", this.showDialog);
  },
};
</script>

<style lang="less" scoped>
.outer {
  background-image: url("~@/assets/bg.png");
  background-color: #f2f2f2;
  background-position: center center;
  main {
    margin: 60px 6%;
    display: flex;
    .el-card {
      margin-left: 2%;
    }
    // border: #1d9fe0 1px solid;
    .upload-card {
      width: 50%;
      border-radius: 20px;
      .resume-upload {
        width: 100%;
      }
    }
    .eva-card {
      width: 50%;
      border-radius: 20px;
      .resume-evaluation {
        width: 100%;
      }
    }
  }
}
</style>