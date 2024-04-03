<template>
  <div class="bk">
    <el-collapse class="inner" v-model="activeNames">
            <el-collapse-item title="登录/注册帮助" name="0">
        <div>
          注册帮助：导航栏的右侧即是注册入口，首先需要通过选择注册的身份（默认学生），再完成注册表的信息即可完成注册（昵称/公式名称用于登录后的显示）
        </div>
        <div>
          登录帮助：导航栏右侧也是登录的入口，填写完成自己的正确信息之后选择身份，点击登录既可登入进入个人/公司信息中心
        </div></el-collapse-item
      >
      <el-collapse-item title="首页信息帮助" name="1">
        <div>
          首页中包含显示系统名称的轮播图，可以清楚明确的知道系统的大概用途。
        </div>
        <div>
          首页可以进行下拉，关于不同的用户看到的信息有微小的差别：学生用户中没有寻找优秀人才的模块，即不会显示优秀学生的信息和查看学生信息的入口
        </div>
      </el-collapse-item>
      <el-collapse-item title="推荐帮助" name="2">
        <div>
          推荐帮助需要登录之后才能使用，对于不同的用户，对讲的内容各不相同，给学生用户推荐职位信息，给企业用户推荐学生信息。用户可以在左侧选择栏中点击不同的推荐对象，对象的具体信息将会出现在右侧大信息栏中，点击查看信息即可跳转该对象信息中心。
        </div>
      </el-collapse-item>
      <el-collapse-item title="搜索帮助" name="3">
        <div>
          搜索功能需要登录之后才能使用，对于不同的用户，能搜索的范围不同
        </div>

        <div>
          企业用户可以搜索职位信息和学生信息，职位搜索功能与学生一致，搜索学生功能帮助企业用户快速遭到心仪的学生，可以点击右侧查看按钮，进入学生信息中心，并可以下载该学生的简历信息
        </div>
      </el-collapse-item>
      <el-collapse-item title="企业个人中心帮助" name="4">
        <div>
          企业用户点击登录跳转企业信息中心，左侧是企业功能导航栏，可以完善和修改企业基础信息，同时查看企业职位相关信息，并做出修改，同时能完成密码的修改等功能
        </div>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
export default {
  data() {
    return {
      activeNames: ['0',"1", "2", "3", "4"],
      innerActive: "",
    };
  },
};
</script>

<style lang='less' scoped>
.bk {
  margin: 20px 10px;
  // padding: 10px 100px;
  // height: 90%;
  // border: 1px solid salmon;
  // display: flex;
  // flex-wrap: wrap;
  // margin: 0 auto;
  .el-collapse,
  .el-collapse-item__wrap {
    border: none;
  }
  .inner {
    display: flex;
    flex-wrap: wrap;
    .el-collapse-item {
      flex: 0 0 30%;
      margin: 15px 20px;
      text-align: left;
    }
    .el-collapse-item__arrow,
    .el-icon-arrow-right {
      display: none;
    }

    /deep/ .collapse-title {
      flex: 1 0 90%;
      order: 1;
      .el-collapse-item__header {
        flex: 1 0 auto;
        order: -1;
      }
    }
  }
  // .el-collapse {
  //   /deep/.el-collapse-item__header {
  //     padding-left: 50px;
  //     position: relative;
  //   }
  //   /deep/.el-collapse-item__content {
  //     padding: 0 50px;
  //   }
  //   /deep/.el-collapse-item__arrow {
  //     position: absolute;
  //     top: 18px;
  //     left: 170px;
  //   }
  //   // padding: 10px 10px;
  //   .el-collapse-item {
  //     // padding: 0 10px;
  //   }
  // }

  // .el-card {
  //   width: 300px;
  //   // height: 200px;
  //   // display: inline-block;
  //   margin: 10px 20px;
  // }
  // .el-card__body {
  //   display: inline-block;
  // }
}
</style>