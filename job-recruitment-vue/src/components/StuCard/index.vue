<template>
  <div>
    <el-card v-for="(i, index) in list" :key="index">
      <div class="inner">
        <div class="logo">
          <img :src="i.avatar || 'defaultStuAvatar.jpg'" alt="" />
        </div>
        <div class="detail">
          <h3>{{ i.name }}</h3>
          <el-tag type="success">{{
            i.bornYear
              ? new Date().getFullYear() - i.bornYear.slice(0, 4)
              : ""
          }}</el-tag>
          <i class="iconfont icon-zhuanyezhishijineng edu"
            >{{ i.school }}-{{ i.education }}-{{ i.major }}</i
          >
          <div>
            <i
              class="iconfont icon-bianjibanbenmiaoshu_bianjibanbenmiaoshu ski"
              >{{ i.skills }}</i
            >
          </div>
        </div>
        <div class="opration">
          <el-button round @click="goJobDetail(i.id)" type="primary">
            查看
          </el-button>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "StuCard",
  props: ["list"],
  methods: {
    goJobDetail(id) {
      this.$router.push({ path: "/student", query: { id } });
    },

  },
};
</script>

<style lang="less" scoped>
.el-card {
  border-width: 1px;
  border-style: solid;
  border-color: #ededed transparent;
  border-left: none;
  border-right: none;
  box-shadow: none;
  // margin: 1% 0;
  .inner {
    display: flex;
    height: 70px;
    justify-content: space-between;
    .logo {
      width: 20%;
      // border: 1px saddlebrown solid;
      img {
        width: 100%;
        height: 100%;
        object-fit: scale-down;
      }
    }
    .detail {
      width: 60%;
      // border: 1px saddlebrown solid;
      position: relative;
      h3 {
        position: absolute;
        left: 20px;
      }
      .edu {
        position: absolute;
        left: 140px;
        top: 5px;
      }
      .el-tag{
        position: absolute;
        left: 80px;
        top: 0px;
      }
      .ski {
        position: absolute;

        top: 50px;
        left: 20px;
      }
      h4 {
        position: absolute;
        left: 20px;
        top: 60px;
      }
    }
    .opration {
      height: 100%;
      width: 20%;
      // border: 1px saddlebrown solid;
      position: relative;
      .el-button {
        position: absolute;
        left: 30px;
        top: 20px;
      }
    }
  }
}
.el-card:hover {
  z-index: 9;
  border-color: transparent;
  -webkit-box-shadow: 0 0 30px rgba(0, 0, 0, 0.15);
  box-shadow: 0 0 30px rgba(0, 0, 0, 0.15);
  transform: scaleX(1.05);
}
</style>