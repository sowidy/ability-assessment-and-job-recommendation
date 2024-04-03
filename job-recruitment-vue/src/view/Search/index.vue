<template>
  <div>
    <Header></Header>

    <div class="container">
      <div>
        <el-tabs
          v-model="activeName"
          @tab-click="handleClick"
          type="card"
          stretch
        >
          <el-tab-pane name="first">
            <span slot="label">
              <img
                src="https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/process-2.png"
                style="width: 35px; hight: 35px"
                alt=""
              />
              <p>搜索职位</p>
            </span>
            <enterprise></enterprise>
          </el-tab-pane>
          <el-tab-pane name="second" v-if="identity == 'enterprise'">
            <span slot="label">
              <img
                src="https://job-test.oss-cn-hangzhou.aliyuncs.com/2024-03-17/process-3.png"
                style="width: 32px; hight: 32px"
                alt=""
              />
              <p class="student">搜索学生</p>
            </span>
            <student></student>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>

    <Footer></Footer>
  </div>
</template>

<script>
import student from "./student.vue";
import enterprise from "./enterprise.vue";
import { mapState } from "vuex";
export default {
  data() {
    return {
      activeName: "first",
    };
  },
  computed: {
    ...mapState("user", ["identity"]),
  },
  components: {
    student,
    enterprise,
  },
  methods: {
    handleClick() {},
  },
};
</script>

<style lang='less' scoped>
.container {
  // margin: 5% 10%;
  background-image: url("~@/assets/bg.png");
  // background-color: #f2f2f2;
  padding: 3% 5%;
  // border: 1px sandybrown solid;
  .el-tabs__item {
    span {
      position: relative;
      p {
        position: absolute;
        margin-left: 20px;
        top: -17px;
        left: 90;
        display: inline-block;
      }
      .student {
        top: -24px;
      }
    }
  }
}

</style>