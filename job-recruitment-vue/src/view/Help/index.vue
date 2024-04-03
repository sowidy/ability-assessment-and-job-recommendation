<template>
  <div class="outer">
    <Header></Header>
    <div class="back">
      <el-container>
        <el-aside width="200px">
          <el-menu
            default-active="1"
            class="el-menu-vertical-demo"
            text-color="#fff"
            active-text-color="#ffd04b"
          >
            <router-link class="link" to="/help/student">
              <el-menu-item index="1">
                <i class="el-icon-location"></i>
                <span slot="title">学生帮助</span>
              </el-menu-item>
            </router-link>
            <router-link class="link" to="/help/enterprise">
              <el-menu-item index="2">
                <i class="el-icon-menu"></i>
                <span slot="title">企业帮助</span>
              </el-menu-item>
            </router-link>
          </el-menu>
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </div>
  </div>
</template>
<script>
export default {

};
</script>

<style lang="less" scoped>
.el-aside {
  // background-color: #d3dce6;
  // background-image: linear-gradient(270deg, #39bffb, #2468f2);
  background-image: linear-gradient(135deg, rgba(0, 181, 188, 0.89) 0%, rgba(62, 142, 240, 0.89) 100%);
  color: #333;
  text-align: center;
  line-height: 200px;
  .el-menu {
    // background-image: linear-gradient(270deg, #39bffb, #2468f2);
    // background-image: linear-gradient(135deg, rgba(0, 181, 188, 0.89) 0%, rgba(62, 142, 240, 0.89) 100%);
    background:transparent;
    .el-menu-item {
      background: transparent;
    }
  }
  /deep/.el-menu-item:hover {
    outline: 0;
    background-color: rgba(246, 246, 246, 0.122);
  }
  .el-menu-item.is-active {
    background-color: rgba(246, 246, 246, 0.122);
  }
    .el-menu {
    .link {
      text-decoration: none;
      color: black;
    }
  }
}

.el-main {
  // background-color: #e9eef3;
  margin: 0;
  padding: 0;
  color: #333;
  text-align: center;
  height: 680px;
  line-height: 160px;
}
body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
.outer {
  // background-image: url("~@/assets/bg.png");
  // background-color: #f2f2f2;
  // background-position: center center;
  // min-height: 742px;
  .back {
    // width: 1200px;
    // margin: 0 auto;
    
  }
}
</style>