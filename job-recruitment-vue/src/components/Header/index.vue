<template>
  <div>
    <div :class="{ 'header-area': true, isSticky: isFix }">
      <div class="container">
        <!-- <div class="logo">
          <img src="@/assets/slogan.png" alt="" />
        </div> -->
        <div class="nav">
          <ul>
            <li
              v-for="(nav, index) in filteredNavs"
              :key="index"
              @click="goNav(nav.value)"
              :class="{ active: activeIndex == nav }"
            >
              {{ nav.name }}
            </li>
          </ul>
        </div>
        <div class="headers-link">
          <div v-if="!identity">
            <a @click="goLogin">登录</a>
            <span>或</span>
            <a @click="goRegister">注册</a>
          </div>
          <el-dropdown v-else-if="identity == 'student'">
            <span class="el-dropdown-link">
              {{ userInfo.name }}
              <i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="goUserInfo"
                >个人中心</el-dropdown-item
              >
              <el-dropdown-item @click.native="goResume"
                >上传简历</el-dropdown-item
              >
              <el-dropdown-item @click.native="goSetting"
                >设置</el-dropdown-item
              >
              <el-dropdown-item divided @click.native="uerLogout"
                >退出</el-dropdown-item
              >
            </el-dropdown-menu>
          </el-dropdown>
          <el-dropdown v-else-if="identity == 'enterprise'">
            <span class="el-dropdown-link">
              {{ userInfo.name }}

              <i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="goEnInfo"
                >信息管理</el-dropdown-item
              >
              <el-dropdown-item @click.native="goEnTitle"
                >职位信息</el-dropdown-item
              >
              <el-dropdown-item @click.native="goEnSetting"
                >设置</el-dropdown-item
              >
              <el-dropdown-item divided @click.native="uerLogout"
                >退出</el-dropdown-item
              >
            </el-dropdown-menu>
          </el-dropdown>
        </div>
        <div class="resume-feedback">
          <el-dialog
            title="反馈能力评价"
            :visible.sync="dialogVisibleh"
            width="400"
            :before-close="dialogBeforeClose"
          >
            <div>
              <div
                style="
                  background: url(https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/subscribe.png)
                    top right no-repeat;
                  background-size: 60% 100%;
                "
              >
                <span
                  >学历评价：<el-rate v-model="edu" show-text> </el-rate
                ></span>
                <span
                  >专业技能评价：<el-rate v-model="ski" show-text> </el-rate
                ></span>
                <span
                  >工作经验评价：<el-rate v-model="exp" show-text> </el-rate
                ></span>
                <span
                  >个人特质评价：<el-rate v-model="per" show-text> </el-rate
                ></span>
                <span
                  >交往特质评价：<el-rate v-model="com" show-text> </el-rate
                ></span>
                <span
                  >决策能力评价：<el-rate v-model="des" show-text> </el-rate
                ></span>
                <span
                  >系统总评分：<el-rate :max="10" v-model="userForm.totalScore">
                  </el-rate
                ></span>
                <el-form
                  :model="userForm"
                  :rules="rules"
                  ref="ruleForm"
                  label-width="100px"
                  class="demo-ruleForm"
                >
                  <el-form-item label="反馈信息" prop="text">
                    <el-input
                      v-model="userForm.remarkText"
                      type="textarea"
                      placeholder="写下您的宝贵意见"
                    ></el-input> </el-form-item
                ></el-form>
              </div>
            </div>
            <div slot="footer">
              <el-button @click="cancelRemark">取 消</el-button>
              <el-button type="primary" @click="upRemark">确 定</el-button>
            </div>
          </el-dialog>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getIdentity } from "@/utils/token";
import { mapState } from "vuex";
export default {
  name: "Header",
  data() {
    return {
      navs: [
        { value: "home", name: "首页" },

        { value: "evaluation-recommendations", name: "能力评估与建议" },
        { value: "recommend", name: "推荐" },
        { value: "search", name: "搜索" },

        { value: "", name: "反馈" },
        { value: "help", name: "帮助" },
      ],
      input: "",
      scrollY: 0,
      activeIndex: "home",
      isFix: "",
      dialogVisibleh: false,
      edu: "",
      ski: "",
      exp: "",
      per: "",
      com: "",
      des: "",
      totalScore: "",
      // remarkText: {
      //   text: "",
      // },
      userForm: {
        totalScore: "",
        remark: "",
        remarkText: "",
      },
      rules: {
        text: [{ required: true, message: "请输入反馈信息", trigger: "blur" }],
      },
    };
  },
  computed: {
    ...mapState("user", ["userInfo", "identity"]),
    filteredNavs() {
      if (this.identity == "enterprise") {
        // 如果用户身份为企业，则过滤掉"能力评价与建议"导航项
        return this.navs.filter(
          (nav) =>
            nav.value !== "evaluation-recommendations" && nav.value !== ""
        );
      } else {
        return this.navs;
      }
    },
  },
  methods: {
    // handleNavClick(nav) {},
    goLogin() {
      this.$router.push("login");
    },
    goRegister() {
      this.$router.push("register");
    },
    goNav(nav) {
      // console.log(nav);
      if (nav === "") {
        if (this.$store.state.user.userInfo.resumeId) {
          this.dialogVisibleh = true;
          return;
        } else if (!this.$store.state.user.userInfo) {
          this.$notify.error("请您先登录");
          this.$router.push("/login");
        } else {
          this.$notify.error("请您先上传简历信息");
          this.$router.push("/evaluation-recommendations");
        }
        // this.$bus.$emit("showDialog");
      }
      this.$router.push({ name: nav });
    },
    handleScroll() {
      this.scrollY = document.documentElement.scrollTop;
      if (this.scrollY > 400) {
        this.isFix = true;
      } else this.isFix = false;
    },
    goUserInfo() {
      this.$router.push({ name: "Detail" });
    },
    async uerLogout() {
      await this.$store.commit("user/USER_LOGOUT");
      this.$router.replace("/");
      this.$notify({
        type: "success",
        message: "退出成功",
      });
    },
    goResume() {
      this.$router.push({ name: "Resume" });
    },
    goSetting() {
      this.$router.push({ name: "UserSetting" });
    },
    goEnInfo() {
      this.$router.push({ name: "EnterPrise" });
    },
    goEnTitle() {
      this.$router.push({ name: "Titles" });
    },
    goEnSetting() {
      this.$router.push({ name: "EnSeeting" });
    },

    dialogBeforeClose() {
      this.dialogVisibleh = false;
    },
    showDialog() {
      this.dialogVisibleh = true;
    },
    cancelRemark() {
      this.$refs.ruleForm.resetFields();
      this.edu =
        this.ski =
        this.exp =
        this.per =
        this.com =
        this.des =
        this.userForm.totalScore =
        this.userForm.remarkText =
          this.userForm.remark;
      ("");
      this.dialogVisibleh = false;
    },
    upRemark() {
      if (
        !this.userForm.remarkText ||
        !this.userForm.totalScore ||
        !this.edu ||
        !this.ski ||
        !this.exp ||
        !this.per ||
        !this.com ||
        !this.des
      ) {
        this.$notify.error("请填写完整信息");
        return;
      }
      let identity = getIdentity();
      this.userForm.remark =
        this.edu +
        "" +
        this.ski +
        "" +
        this.exp +
        "" +
        this.per +
        "" +
        this.com +
        "" +
        this.des;
      console.log(this.userForm);
      this.dialogVisibleh = false;
      this.$API.user
        .updateUser(this.userForm, identity)
        .then((resp) => {
          console.log(resp.data);
          if (resp.data.code === 0) {
            this.$store.dispatch("user/getUserInfo", { identity });
            this.$notify({
              type: "success",
              message: "感谢您的支持",
            });
            this.edu =
              this.ski =
              this.exp =
              this.per =
              this.com =
              this.des =
              this.remarkText.text =
                "";
            this.userForm.remark = "";
          } else console.log(resp.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  mounted() {
    window.addEventListener("scroll", this.handleScroll, true);
    this.$bus.$on("showDialog", this.showDialog);
  },
};
</script>

<style lang='less' scoped>
.preBefore {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  bottom: 10px;
  /*靠最左边开始*/
  border-bottom: 1px solid black;
  /*设置底部边颜色*/
  width: 0;
  /*宽度为0，这里的宽度是相对与li的宽度*/
  height: 100%;
}
.preHoverAndBefore {
  width: 100%;
  transition: 0.3s all linear;
  /*设置过度时间*/
  transition-delay: 0.05s;
}
.header-area {
  // background: #66b1ff;
  transition: all 0.9s;
  // background: #ebf4ff;
  background-image: linear-gradient(
    135deg,
    rgba(0, 181, 188, 0.89) 0%,
    rgba(62, 142, 240, 0.89) 100%
  );
  // background: rgba(0, 181, 188, 0.9);
  box-shadow: 0 -31px 32px 9px #234dd4;
  // background: rgba(0, 181, 188, 0.9);
  //   box-shadow: 0px 0px 25.5px 4.5px rgba(0, 0, 0, 0.1);
  // border-bottom: 1px solid silver;
  // position: absolute;
  color: black;
  .container {
    display: flex;
    justify-content: space-between;
    padding: 0px 150px;
    height: 60px;
    font-size: 20px;
    // font-weight: 600;
    .logo {
      // border:solid 1px rgb(90, 77, 75);
      // margin-left: -3%;
      width: 27%;
      img {
        width: 400px;
        text-align: center;
        margin: 0 auto;
        margin-top: -2%;
      }
    }
    .headers-link {
      // border:solid 1px rgb(230, 49, 29);
      height: 100%;
      margin-top: 20px;
      width: 40%;
      text-align: right;
      // float: right;
      .el-dropdown-link {
        // margin-right: 20px;
        // width: 100%;
        // height: 100%;
        font-size: 20px;
        cursor: pointer;
        color: black;
      }
      .el-icon-arrow-down {
        font-size: 20px;
      }
      a {
        padding: 10px 10px;
        position: relative;
      }
      a::before {
        .preBefore();
      }
      a:hover::before {
        .preHoverAndBefore();
      }
    }
    .nav {
      width: 60%;
      // float: left;
      margin-top: 10px;
      // border:solid 1px salmon;
      // margin-left: -10%;
      ul {
        // .search-incon {
        //   text-align: center;
        //   margin-top: 14px;
        //   margin-right: 40px;
        // }
        display: flex;
        justify-content: space-around;
        li {
          list-style-type: none;
          margin: 0 25px;
          padding: 10px 5px;
          position: relative;
        }
        li::before {
          .preBefore();
        }
        li:hover::before {
          .preHoverAndBefore();
        }
      }
    }
    .resume-feedback {
      span {
        display: flex;
        margin: 5px 20px;
        padding: 10px 0;
        width: 400px;
      }
    }
  }
}
.isSticky {
  position: fixed;
  top: 0;
  z-index: 99;
  width: 100%;
  height: 60px;
  animation: 500ms ease-in-out 0s normal none 1 running fadeInDown;
}
// .active{
//   color: #358ccf;
// }
</style>