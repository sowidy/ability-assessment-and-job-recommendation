<template>
  <div>
    <el-container>
      <el-header>
        <span
          style="
            background: linear-gradient(to right, #cdffd8, #4884b1);
            display: inline-block;
            padding: 0px;
          "
          >大学生能力评估与职位推荐系统</span
        >

        <div class="info">
          <el-dropdown>
            <el-avatar icon="el-icon-user-solid" :src="avatar"></el-avatar>
            <el-dropdown-menu>
              <el-dropdown-item @click.native="$router.push('/admin/dashboard')"
                >主页</el-dropdown-item
              >
              <el-dropdown-item @click.native="logout"
                >退出登录</el-dropdown-item
              >
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <el-col :span="24">
            <el-menu
              default-active="1"
              class="el-menu-vertical-demo"
              @open="handleOpen"
              @close="handleClose"
              background-color="#333f4f"
              text-color="#fff"
              active-text-color="#ffd04b"
            >
              <router-link to="/admin/dashboard">
                <el-menu-item index="1">
                  <i class="el-icon-data-line"></i>
                  <span slot="title">信息管理</span>
                </el-menu-item>
              </router-link>

              <el-submenu index="2">
                <template slot="title">
                  <i class="el-icon-user-solid"></i>
                  <span>用户管理</span>
                </template>
                <el-menu-item-group>
                  <router-link to="/admin/manage/student">
                    <el-menu-item index="1-1">学生</el-menu-item>
                  </router-link>
                  <router-link to="/admin/manage/enterprise">
                    <el-menu-item index="1-2">企业</el-menu-item>
                  </router-link>
                </el-menu-item-group>
              </el-submenu>
              <router-link to="/admin/application">
                <el-menu-item index="3">
                  <i class="el-icon-s-claim"></i>
                  <span slot="title">企业认证申请</span>
                </el-menu-item>
              </router-link>
              <router-link to="/admin/remark">
                <el-menu-item index="4">
                  <i class="el-icon-notebook-2"></i>
                  <span slot="title">学生反馈详情</span>
                </el-menu-item>
              </router-link>
            </el-menu>
          </el-col>
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "AdminLatout",
  computed: {
    ...mapState("admin", ["avatar"]),
  },
  methods: {
    async logout() {
      await this.$store.dispatch("admin/logout");
      this.$router.push(`/admin/login?redirect=${this.$route.fullPath}`);
    },
    handleOpen() {},
    handleClose() {},
  },
};
</script>



<style lang="less" scoped>
.el-header {
  position: relative;
  span {
    // float: left;
    font-size: 24px;
    // padding: 0 10px;
  }
  img {
    width: 200px;
  }
  .info {
    position: absolute;
    top: 10px;
    right: 40px;
  }
}
</style>
<style scoped>
.el-header {
  background-color: #4884b1;
  color: #333;
  /* text-align: center; */
  padding: 0;
  line-height: 60px;
}

.el-aside {
  background-color: #333f4f;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  /* line-height: 160px; */
  height: 682px;
  /* padding: 10px; */
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
</style>