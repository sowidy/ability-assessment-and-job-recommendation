<template>
  <div>
    <el-card v-for="(i, index) in list" :key="index">
      <div class="inner">
        <div class="logo">
          <img
            :src="i.logo || 'defaultEnAvatar.jpg'"
            style="width: 50%"
            alt=""
          />
        </div>
        <div class="enterpriseInfo">
          <h3>
            <span>{{ i.name }}</span> {{ i.title }}
          </h3>
          <i class="el-icon-location-outline">{{ i.address || '暂无信息' }}</i>
        </div>
        <div class="treat">
          <h5>￥{{ i.salaryMin }}-{{ i.salaryMax }}k/月</h5>
          <el-button round @click="goJobDetail(i.id)" type="primary"
            >查看</el-button
          >
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "JobCard",
  props: ["list"],
  methods: {
    goJobDetail(id) {
      this.$router.push({ path: "/job", query: { id } });
    },
  },
};
</script>

<style lang="less" scoped>
.el-card {
  // margin: 2px;
  border-width: 1px;
  border-style: solid;
  border-color: #ededed transparent;
  border-left: none;
  border-right: none;
  box-shadow: none;
  .inner {
    height: 70px;
    display: flex;
    justify-content: space-between;

    .logo {
      // border: 1px rgb(175, 39, 24) solid;
      width: 15%;
      img {
        width: 100%;
        height: 100%;
        object-fit: scale-down;
      }
    }

    .enterpriseInfo {
      width: 60%;
      text-align: left;
      // border: 1px rgb(175, 39, 24) solid;
      position: relative;
      h3 {
        position: absolute;
        left: 20px;
        padding: 3px 0;
        span {
          font-weight: 500;
          font-size: 15px;
        }
      }
      i {
        position: absolute;
        left: 20px;
        top: 50px;
      }
    }

    .treat {
      width: 25%;
      // border: 1px rgb(175, 39, 24) solid;
      position: relative;

      h5 {
        position: absolute;
        left: 10%;
        // top: 20px;
        // font-size: 20px;
      }
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