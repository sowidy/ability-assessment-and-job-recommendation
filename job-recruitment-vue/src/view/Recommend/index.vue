<template>
  <div>
    <Header></Header>
    <div class="container">
      <div class="headbar">
        <div>
          <el-card>
            <div>
              <div class="expectation">
                <div class="expectName"></div>
                <div class="tags">
                  <h2 v-if="_identity === 'student'">求职期望:</h2>
                  <h2 v-else>寻找求职者：</h2>
                  <div class="tag-list">
                    <el-tag
                      :key="tag"
                      v-for="tag in dynamicTags"
                      closable
                      :disable-transitions="false"
                      @close="handleClose(tag)"
                      @click="tagClick(tag)"
                    >
                      {{ tag }}
                    </el-tag>
                    <el-input
                      class="input-new-tag"
                      v-if="inputVisible"
                      v-model="inputValue"
                      ref="saveTagInput"
                      size="small"
                      @keyup.enter.native="handleInputConfirm"
                      @blur="handleInputConfirm"
                    >
                    </el-input>
                    <el-button
                      v-else
                      class="button-new-tag"
                      size="small"
                      @click="showInput"
                      >{{
                        _identity === "student"
                          ? "+添加新期望"
                          : "+添加技能要求"
                      }}</el-button
                    >
                  </div>
                </div>
                <div class="fresh">
                  <el-button round>
                    <i class="el-icon-refresh" @click="freshList">刷新</i>
                  </el-button>
                  <el-button
                    round
                    type="info"
                    plain
                    @click="feedback"
                    v-if="_identity != 'enterprise'"
                    >不满意?反馈系统</el-button
                  >
                </div>
              </div>
              <!-- <div class="tags-list">
                <span>筛选</span>
                <el-input
                  v-model="condition.title"
                  v-if="_identity == 'student'"
                  placeholder="职位名称"
                  @change="input_1_change"
                />
                <el-input
                  v-model="studentCondition.name"
                  v-else
                  @change="input_1_else_change"
                  placeholder="学生姓名"
                />
                <el-select
                  v-model="salaryValue"
                  v-if="_identity == 'student'"
                  @change="input_2_change"
                  placeholder="薪资待遇"
                >
                  <el-option
                    v-for="item in salaryOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>

                <el-select
                  v-model="studentCondition.education"
                  @change="input_2_else_change"
                  v-else
                  placeholder="学历要求"
                >
                  <el-option
                    v-for="item in eduOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>

                <el-input
                  v-model="condition.name"
                  v-if="_identity == 'student'"
                  @change="input_3_change"
                  placeholder="企业名称"
                />

                <el-input
                  v-model="studentCondition.school"
                  @change="input_3_else_change"
                  v-else
                  placeholder="学校"
                />

                <el-input
                  v-model="condition.address"
                  v-if="_identity == 'student'"
                  @change="input_4_change"
                  placeholder="工作地点"
                />

                <el-input
                  v-model="studentCondition.major"
                  @change="input_4_else_change"
                  v-else
                  placeholder="专业"
                />

                <el-button
                  type="info"
                  plain
                  icon="el-icon-delete"
                  @click="removeCondition"
                  >清空筛选条件</el-button
                >
              </div> -->
            </div>
          </el-card>
        </div>
      </div>

      <div class="recomment">
        <div class="list">
          <div class="recommendCard">
            <!-- 给企业推荐学生 -->
            <div v-if="_identity === 'enterprise'">
              <el-card
                class="card"
                v-for="i in recommentList"
                :key="i.id"
                @click.native="changeSelectID(i.id, 'Student')"
              >
                <div class="title">
                  <div class="left">
                    <img :src="i.avatar || 'defaultStuAvatar.jpg'" alt="" />
                    <div class="stu-name">{{ i.name }}</div>
                  </div>
                  <div class="right">
                    <div class="school-info">
                      <el-tag type="success" v-if="i.school"
                        >{{ i.school + "-" }}{{ i.major }}</el-tag
                      ><el-tag v-else type="warning">暂无信息</el-tag>
                    </div>
                    <div class="skills">技能：{{ i.skills }}</div>
                  </div>
                </div>
              </el-card>
            </div>
            <!-- 给游客和学生推荐企业 -->
            <el-card
              v-else
              class="card"
              v-for="i in recommentList"
              :key="i.id"
              @click.native="changeSelectID(i.id, 'Enterprise')"
            >
              <div class="title">
                <div class="title-name">
                  <el-tag type="success">{{ i.title }}</el-tag>
                </div>
                <div class="salary">{{ i.salaryMin }}-{{ i.salaryMax }}/月</div>
              </div>
              <div class="tag">
                <span>{{ i.address }}</span>
                <!-- <el-tag type="">HTML</el-tag>
                <el-tag type="">Java</el-tag>
                <el-tag type="">Python</el-tag> -->
              </div>
              <div class="name">
                <img :src="i.logo" alt="" />
                <div>{{ i.name }}</div>
                <!-- <el-tag type="" style="margin-left:30px;margin-buttom:2px"><span > {{i.titleRequire}}</span></el-tag> -->
                <!-- <div>{{ i.address }}</div> -->
              </div>
            </el-card>
          </div>
          <div class="detalis">
            <!-- 学生展示企业详情 -->
            <el-card v-if="_identity === 'student'">
              <div class="job-detail-header">
                <div class="header-left">
                  <h2>{{ aDatailTitle.title }}</h2>
                  <i class="el-icon-location-outline">{{
                    aDatailTitle.address
                  }}</i>
                </div>
                <div class="header-right">
                  <el-tooltip
                    class="item"
                    effect="dark"
                    :content="like ? '已收藏' : '点击收藏职位'"
                    placement="top"
                  >
                    <span class="like" @click="addOrDeleteFavorite">
                      <transition
                        enter-active-class="animate__animated animate__bounce"
                      >
                        <i
                          v-if="like == true"
                          class="el-icon-star-on"
                          style="color: #e6a23c"
                        />
                      </transition>
                      <i v-if="like == false" class="el-icon-star-off" />
                    </span>
                  </el-tooltip>

                  <el-button
                    type="primary"
                    round
                    @click="goJobDetail(aDatailTitle.id)"
                    >查看</el-button
                  >
                </div>
              </div>
              <el-divider></el-divider>
              <div class="job-detail-body">
                <div class="job-detail">
                  <div class="title"><h2>职位描述：</h2></div>
                  <div class="job-label">
                    <!-- <el-tag>销售经理</el-tag>
                    <el-tag>总管</el-tag> -->
                  </div>
                  <div class="info">
                    {{ aDatailTitle.titleDescription }}
                  </div>
                </div>
              </div>
            </el-card>

            <!-- 企业展示学生详情 -->
            <el-card v-else>
              <div class="job-detail-header">
                <div class="header-left">
                  <h2>{{ aDatailTitle.name }}</h2>
                  <!-- <i class="el-icon-location-outline">{{aDatailTitle.address}}</i> -->
                </div>
                <div class="header-right">
                  <el-button
                    round
                    type="primary"
                    @click="goJobDetail(aDatailTitle.id)"
                    >查看</el-button
                  >
                </div>
              </div>
              <el-divider></el-divider>
              <div class="job-detail-body">
                <div class="job-detail">
                  <div class="skills"><h2>技能描述：</h2></div>
                  <!-- <div class="job-label"> -->
                  <!-- <el-tag>销售经理</el-tag>
                    <el-tag>总管</el-tag> -->
                  <!-- </div> -->
                  <div class="detail-ski">
                    {{ aDatailTitle.skills }}
                  </div>
                  <div class="exp"><h2>工作经历：</h2></div>
                  <div class="detail-exp">{{ aDatailTitle.experience }}</div>
                </div>
              </div>
            </el-card>
          </div>
        </div>
      </div>
    </div>
    <Footer></Footer>
  </div>
</template>

<script>
import { getIdentity } from "@/utils/token";
export default {
  name: "Recomment",
  data() {
    return {
      like: false,
      selectID: 0,
      recommentList: [],
      aDatailTitle: "",
      dynamicTags: [],
      inputVisible: false,
      inputValue: "",
      // condition: {
      //   address: "",
      //   isAsc: "true",
      //   enterpriseAuth: "0",
      //   name: "",
      //   title: "",
      //   pageNo: 1,
      //   pageSize: 100,
      //   salaryMax: 5000000,
      //   salaryMin: 0,
      //   sortBy: "",
      //   type: "",
      // },
      // studentCondition: {
      //   isAsc: "true",
      //   name: "",
      //   pageNo: 1,
      //   pageSize: 100,
      //   sortBy: "",
      //   education: "",
      //   gender: "",
      //   major: "",
      //   school: "",
      //   resumeId: "1",
      //   skills: "",
      // },
      // eduOptions: [
      //   { value: "专科", label: "专科" },
      //   { value: "本科", label: "本科" },
      //   { value: "硕士", label: "硕士" },
      //   { value: "博士", label: "博士" },
      // ],
      // salaryValue: "",
      // salaryOptions: [
      //   { value: "不限", label: "不限" },
      //   { value: "3k以下", label: "3k以下" },
      //   { value: "3-5k", label: "3-5k" },
      //   { value: "5-10k", label: "5-10k" },
      //   { value: "10-20k", label: "10-20k" },
      //   { value: "20-50k", label: "20-50k" },
      //   { value: "50k以上", label: "50k以上" },
      // ],
    };
  },
  computed: {
    _identity() {
      return getIdentity() || "student";
    },
  },
  watch: {
    dynamicTags: {
      handler: "handleDynamicTagsChange",
      deep: true,
    },
  },
  async beforeMount() {
    await this.getList();
    await this.getATitleDetail();
  },
  methods: {
    addOrDeleteFavorite() {
      if (!this.like) {
        this.$API.user
          .addFavoriteTitle(this.selectID)
          .then((resp) => {
            if (resp.data.code == 0) {
              this.$notify({
                type: "success",
                message: "收藏成功",
              });
              this.like = true;
            } else this.$notify.error(resp.data.message);
          })
          .catch((err) => {
            console.log(err);
          });
      } else {
        this.$API.user
          .deleteFavoriteTitle(this.selectID)
          .then((resp) => {
            if (resp.data.code == 0) {
              this.$notify({
                type: "success",
                message: "取消收藏",
              });
              this.like = false;
            } else this.$notify.error(resp.data.message);
          })
          .catch((err) => {
            console.log(err);
          });
      }
    },
    checkFavoriteState() {
      this.$API.user
        .checkFavorite(this.selectID)
        .then((resp) => {
          if (resp.data.code == 0) {
            this.like = resp.data.data;
            console.log(this.like);
          } else this.like = false;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    async getList() {
      await this.$API.enterprise
        .reGetHotRecomment(
          this._identity == "student" ? "enterprise" : "student",
          "20"
        )
        .then((resp) => {
          this.recommentList = resp.data.data;
          // console.log(this.recommentList);
        });
    },
    changeSelectID(id, identity) {
      this.selectID = id;
      this.getATitleDetail(this.selectID, identity);
    },
    async getATitleDetail(
      // id = this._identity == "student" ? 0 : 1,
      id =this.recommentList[0].id,
      identity = this._identity == "student" ? "Enterprise" : "Student"
    ) {
      this.aDatailTitle = [];
      await this.$API.enterprise.findHotById(id, identity).then((resp) => {
        this.aDatailTitle = resp.data.data;
        if(this._identity == 'student') this.checkFavoriteState();
      });
    },
    handleClose(tag) {
      this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
    },
    tagClick(tag) {},
    showInput() {
      this.inputVisible = true;
      this.$nextTick(() => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },
    handleDynamicTagsChange() {
      if (this.dynamicTags.length === 0) {
        this.getList();
        return;
      }
      const promises = [];
      const tempRecommendations = [];
      this.dynamicTags.forEach((tag) => {
        if (this._identity === "student") {
          // 如果用户身份是学生，则查询企业信息
          promises.push(this.$API.enterprise.queryByName(tag));
        } else {
          // 如果用户身份不是学生，则作为技能条件查询学生信息
          promises.push(this.$API.user.queryBySkills(tag));
        }
      });
      Promise.all(promises)
        .then((responses) => {
          const recommendationLists = responses.map(
            (response) => response.data.data
          );
          tempRecommendations.push(...[].concat(...recommendationLists));
          this.recommentList = tempRecommendations;
        })
        .catch((error) => {
          console.error("Error fetching recommendations:", error);
        });
    },
    handleInputConfirm() {
      let inputValue = this.inputValue;
      if (inputValue) {
        this.dynamicTags.push(inputValue);
      }
      this.inputVisible = false;
      this.inputValue = "";
    },
    goJobDetail(id) {
      // console.log(this.identity);
      this.$router.push({
        path: `/${this._identity == "student" ? "job" : "student"}`,
        query: { id },
      });
    },
    freshList() {
      this.getList();
    },
    feedback() {
      this.$bus.$emit("showDialog");
    },
    // conditionQueryEnterprise() {
    //   this.$API.enterprise
    //     .conditionQuery(this.condition)
    //     .then((resp) => {
    //       if (resp.data.code == 0) {
    //         this.recommentList = resp.data.data.list;
    //       }
    //     })
    //     .catch((err) => {
    //       console.log(err);
    //     });
    // },
    // conditionQueryStudent() {
    //   this.$API.user
    //     .queryStudent(this.studentCondition)
    //     .then((resp) => {
    //       if (resp.data.code == 0) {
    //         this.recommentList = resp.data.data.list;
    //       }
    //     })
    //     .catch((err) => {
    //       console.log(err);
    //     });
    // },
    // input_1_change() {
    //   this.conditionQueryEnterprise();
    // },
    // input_1_else_change() {
    //   this.conditionQueryStudent();
    // },
    // input_2_change() {
    //   // console.log(this.salaryValue);
    //   switch (this.salaryValue) {
    //     case "3k以下":
    //       this.condition.salaryMin = 0;
    //       this.condition.salaryMax = 3000;
    //       break;
    //     case "3-5k":
    //       this.condition.salaryMin = 3000;
    //       this.condition.salaryMax = 5000;
    //       break;
    //     case "5-10k":
    //       this.condition.salaryMin = 5000;
    //       this.condition.salaryMax = 10000;
    //       break;
    //     case "10-20k":
    //       this.condition.salaryMin = 100000;
    //       this.condition.salaryMax = 200000;
    //       break;
    //     case "20-50k":
    //       this.condition.salaryMin = 200000;
    //       this.condition.salaryMax = 500000;
    //       break;
    //     case "50k以上":
    //       this.condition.salaryMin = 500000;
    //       this.condition.salaryMax = 1000000;
    //       break;
    //     default:
    //       this.condition.salaryMin = 0;
    //       this.condition.salaryMax = 1000000;
    //       break;
    //   }
    //   console.log(this.condition);
    //   this.conditionQueryEnterprise();
    // },
    // input_2_else_change() {
    //   this.conditionQueryStudent();
    // },
    // input_3_change() {
    //   this.conditionQueryEnterprise();
    // },
    // input_3_else_change() {
    //   this.conditionQueryStudent();
    // },
    // input_4_change() {
    //   this.conditionQueryEnterprise();
    // },
    // input_4_else_change() {
    //   this.conditionQueryStudent();
    // },
    // removeCondition() {
    //   this.condition = {
    //     address: "",
    //     isAsc: "true",
    //     enterpriseAuth: "0",
    //     name: "",
    //     title: "",
    //     pageNo: 1,
    //     pageSize: 100,
    //     salaryMax: 5000000,
    //     salaryMin: 0,
    //     sortBy: "",
    //     type: "",
    //   };

    //   this.studentCondition = {
    //     isAsc: "true",
    //     name: "",
    //     pageNo: 1,
    //     pageSize: 100,
    //     sortBy: "",
    //     education: "",
    //     gender: "",
    //     major: "",
    //     school: "",
    //     resumeId: "1",
    //   };
    //   this.salaryValue = "";
    //   this.getList();
    // },
  },
};
</script>

<style>
.el-tag + .el-tag {
  margin-left: 10px;
}
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>
<style lang='less' scoped>
.container {
  padding: 2% 10%;
  background-image: url("~@/assets/bg.png");
  background-color: #f2f2f2;
  background-position: center center;
  // border: 1px solid darkcyan;
  .headbar {
    .el-card {
      background-image: linear-gradient(135deg, #00b5bc 0%, #3e8ef0 100%);
      // margin-left: 5%;
      // margin-right: 2%;
      border-radius: 20px;
      .expectation {
        // border: 1px solid rgb(20, 42, 42);
        padding: 2% 0;
        position: relative;
        .tags {
          display: flex;
          .tag-list {
            display: flex;
            .el-tag {
              margin: 0 20px;
            }
          }
        }
        .fresh {
          position: absolute;
          right: 10px;
          top: 20px;
        }
      }
      .tags-list {
        position: relative;
        .el-select {
          margin: 0 5px;
        }
        .el-input {
          .el-select();
          width: 200px;
        }
      }
    }
  }
  .recomment {
    // margin-bottom: 170px;
    .tips {
      // padding:2% 0;
      margin: 3% 0;
      margin-left: 7%;
      margin-right: 2%;
      display: flex;
      justify-content: space-between;
      .remark {
        // height: 400px;
        width: 100%;
        // border: 1px solid sandybrown;
        .el-card {
          // margin-top: 20%;
          height: 100%;
          box-shadow: none;
          border: none;
          text-align: center;
          .el-button {
            margin-top: 5%;
          }
        }
      }
    }
    .list {
      // border: 1px solid rgb(123, 175, 54);

      display: flex;
      padding: 2% 0%;
      // height: 530px;
      // margin-top: 2%;
      .recommendCard {
        max-height: calc(100vh - 190px);
        min-height: calc(100vh - 190px);

        // height: 100%;
        overflow-y: auto;
        width: 40%;
        // border: 1px solid rgb(175, 54, 54);
        margin: 0 0%;

        .el-card {
          height: 150px;
          margin: 2% 2%;
          border-radius: 20px;
          position: relative;
          // background-image: linear-gradient(135deg, #00b5bc 0%, #3e8ef0 100%);
          .title {
            display: flex;
            justify-content: space-between;
            height: 100%;
            .title-name {
              .el-tag {
                font-size: 17px;
              }
            }
            .salary {
              color: red;
              font-size: 20px;
            }
            .left {
              width: 30%;
              height: 100%;
              // border: 1px solid salmon;
              img {
                width: 70%;
                height: 90px;
                margin: 0 auto;
              }
              .stu-name {
                // text-align: center;
                margin-top: 10px;
                padding-left: 20px;
              }
            }
            .right {
              width: 70%;
              height: 100%;
              // border: 1px solid salmon;
              .school-info {
                height: 50px;
              }
            }
            img {
              width: 40px;
              height: 40px;
              margin: 0 10px;
            }
            // border: 1px solid rgb(175, 54, 54);
          }

          .exp {
            margin: 5px 0;
          }
          .tag {
            position: absolute;
            top: 65px;
            left: 20px;
          }
          .name {
            // margin-top: 15%;
            position: absolute;
            top: 110px;
            left: 10px;
            display: flex;
            text-align: center;
            // justify-content: space-between;
            img {
              width: 20px;
              margin: 0 10px;
            }
            // border: 1px solid rgb(226, 129, 235);
          }
        }
      }
      .detalis {
        max-height: calc(100vh - 190px);
        min-height: calc(100vh - 190px);
        width: 60%;
        // border: 1px solid rgb(34, 77, 168);
        position: relative;
        .el-card {
          // background: linear-gradient(to bottom right, #d39dff, #a0ffc8);
          border-radius: 20px;
          div {
            margin: 2% 0;
          }
          .job-detail-header {
            position: sticky;
            display: flex;
            justify-content: space-between;
            height: 100px;
            position: relative;
            .header-left {
              // border: 1px solid rgb(34, 77, 168);
              width: 70%;
              h2 {
                position: absolute;
                top: 20px;
                left: 30px;
              }
              i {
                position: absolute;
                top: 60px;
                left: 30px;
              }
            }
            .header-right {
              // border: 1px solid rgb(34, 77, 168);
              width: 30%;
              position: relative;
              .el-button {
                position: absolute;
                top: 15px;
                right: 40px;
              }
              .like {
                margin-top: 1px;
                cursor: pointer;
                font-size: 50px;
                display: inline-block;
              }
            }
          }
          .job-detail-body {
            max-height: calc(100vh - 385px);
            min-height: calc(100vh - 385px);
            overflow-x: hidden;
            .el-tag {
              margin-right: 2%;
            }
          }
        }
      }
    }
  }
}
</style>

<style scoped lang='less'>
.recommendCard::-webkit-scrollbar {
  /*滚动条整体样式*/
  width: 4px; /*高宽分别对应横竖滚动条的尺寸*/
  height: 1px;
}
.recommendCard::-webkit-scrollbar-thumb {
  /*滚动条里面小方块*/
  border-radius: 10px;
  background-color: skyblue;
  background-image: -webkit-linear-gradient(
    45deg,
    rgba(255, 255, 255, 0.2) 25%,
    transparent 25%,
    transparent 50%,
    rgba(255, 255, 255, 0.2) 50%,
    rgba(255, 255, 255, 0.2) 75%,
    transparent 75%,
    transparent
  );
}
.recommendCard::-webkit-scrollbar-track {
  /*滚动条里面轨道*/
  box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  background: #ededed;
  border-radius: 10px;
}
.job-detail-body::-webkit-scrollbar {
  /*滚动条整体样式*/
  width: 4px; /*高宽分别对应横竖滚动条的尺寸*/
  height: 1px;
}
.job-detail-body::-webkit-scrollbar-thumb {
  /*滚动条里面小方块*/
  border-radius: 10px;
  background-color: skyblue;
  background-image: -webkit-linear-gradient(
    45deg,
    rgba(255, 255, 255, 0.2) 25%,
    transparent 25%,
    transparent 50%,
    rgba(255, 255, 255, 0.2) 50%,
    rgba(255, 255, 255, 0.2) 75%,
    transparent 75%,
    transparent
  );
}
.job-detail-body::-webkit-scrollbar-track {
  /*滚动条里面轨道*/
  box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  background: #ededed;
  border-radius: 10px;
}
</style>