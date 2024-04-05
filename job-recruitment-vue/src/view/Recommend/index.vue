<template>
  <div>
    <Header></Header>
    <div class="container">
      <div class="headbar">
        <div>
          <!-- <videostyle="height: 100%; width: 100%; position: relative"
            src="https://job-test.oss-cn-hangzhou.aliyuncs.com/%E7%99%BE%E5%BA%A6%E6%99%BA%E8%83%BD%E4%BA%91%E5%8D%83%E5%B8%86%E5%A4%A7%E6%A8%A1%E5%9E%8B%E5%B9%B3%E5%8F%B0ModelBuilder2.mp4"
            autoplay
            loop
            muted
            style="
              width: 100%;
              height: 101%;
              object-fit: cover;
              position: absolute;
              top: 0;
              left: 0;
              z-index: 0;
            "
          ></video> -->
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
                <el-select v-model="value" placeholder="企业类型">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>

                <el-select v-model="value" placeholder="学历要求">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>

                <el-select v-model="value" placeholder="薪资待遇">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>

                <el-select v-model="value" placeholder="地点">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>

                <el-button type="info" plain icon="el-icon-delete">清空筛选条件</el-button>
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
                <div class="salary">{{ i.salaryMin }}-{{ i.salaryMax }}k</div>
              </div>
              <div class="tag">
                <el-tag type="">HTML</el-tag>
                <el-tag type="">Java</el-tag>
                <el-tag type="">Python</el-tag>
              </div>
              <div class="name">
                <img :src="i.logo" alt="" />
                <div>{{ i.name }}</div>
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
                  <el-button round @click="goJobDetail(aDatailTitle.id)"
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
      selectID: 1,
      recommentList: [],
      aDatailTitle: "",
      dynamicTags: [],
      inputVisible: false,
      inputValue: "",
      // dialogVisible: false,
      // rate: null,
      // userForm: {
      //   remark: "",
      // },
      condition: {
        address: "",
        isAsc: "true",
        enterpriseAuth: "0",
        name: "",
        title: "",
        pageNo: 1,
        pageSize: 10,
        salaryMax: "",
        salaryMin: "",
        sortBy: "",
        type: "",
      },
    };
  },
  computed: {
    // ...mapState('user',['identity'])
    _identity() {
      return getIdentity() || "student";
    },
  },
  watch: {
    // Watch for changes in dynamicTags array
    dynamicTags: {
      handler: "handleDynamicTagsChange", // Call the method when dynamicTags change
      deep: true, // Deep watch for changes inside the array
    },
  },
  methods: {
    // hoverCard(isHover, id) {
    //   if (isHover) {
    //     // Add hover class when mouse enters
    //     document.querySelector(`.recommendCard .el-card[data-id="${id}"]`).classList.add('hover');
    //   } else {
    //     // Remove hover class when mouse leaves
    //     document.querySelector(`.recommendCard .el-card[data-id="${id}"]`).classList.remove('hover');
    //   }
    // },
    getList() {
      // console.log(this.identity);
      this.$API.enterprise
        .reGetHotRecomment(
          this._identity == "student" ? "enterprise" : "student"
        )
        .then((resp) => {
          // console.log(resp);
          this.recommentList = resp.data.data;
          // console.log(this.recommentList);
        });
    },
    changeSelectID(id, identity) {
      this.selectID = id;
      this.getATitleDetail(this.selectID, identity);
    },
    getATitleDetail(
      id = 1,
      identity = this._identity == "student" ? "Enterprise" : "Student"
    ) {
      // console.log(identity);
      this.aDatailTitle = [];
      this.$API.enterprise.findHotById(id, identity).then((resp) => {
        this.aDatailTitle = resp.data.data;
        // console.log(this.aDatailTitle, "$$$$$");
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
      // console.log(this.dynamicTags);
    },
    handleDynamicTagsChange() {
      if (this.dynamicTags.length === 0) {
        this.getList();
        return;
      }

      // Create an array to store all promises
      const promises = [];

      // Create a temporary array to store recommendations
      const tempRecommendations = [];

      // Iterate over dynamicTags
      this.dynamicTags.forEach((tag) => {
        // Push each API call promise to promises array
        promises.push(this.$API.enterprise.queryByName(tag));
      });

      // Wait for all promises to resolve
      Promise.all(promises)
        .then((responses) => {
          // Flatten the array of responses
          const recommendationLists = responses.map(
            (response) => response.data.data
          );
          // Concatenate all recommendation lists into a single array
          tempRecommendations.push(...[].concat(...recommendationLists));

          // Update recommentList with the new recommendations
          this.recommentList = tempRecommendations;
        })
        .catch((error) => {
          console.error("Error fetching recommendations:", error);
        });
    },
    // handleDynamicTagsChange() {
    //   if (this.dynamicTags.length === 0) {
    //     this.getList();
    //     return;
    //   }

    //   // Create an array to store all promises
    //   const promises = [];

    //   // Create a temporary array to store recommendations
    //   const tempRecommendations = [];

    //   // Iterate over dynamicTags
    //   this.dynamicTags.forEach((tag) => {
    //     // Push each API call promise to promises array
    //     promises.push(this.$API.enterprise.queryByName(tag));
    //   });

    //   // Wait for all promises to resolve
    //   Promise.all(promises)
    //     .then((responses) => {
    //       // Flatten the array of responses
    //       const recommendationLists = responses.map(
    //         (response) => response.data.data
    //       );
    //       // Concatenate all recommendation lists into a single array
    //       const flattenedRecommendations = [].concat(...recommendationLists);
    //       // Push the results to the temporary array
    //       tempRecommendations.push(...flattenedRecommendations);
    //       // Update recommentList with the new recommendations
    //       this.recommentList = [...this.recommentList, ...tempRecommendations];
    //     })
    //     .catch((error) => {
    //       console.error("Error fetching recommendations:", error);
    //     });
    // },
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
    // dialogBeforeClose() {
    //   this.dialogVisible = false;
    // },
    freshList() {
      this.getList();
    },
    cancelRemark() {
      this.rate = null;
      this.userForm.remark = "";
      this.dialogVisible = false;
    },
    feedback() {
      this.$bus.$emit("showDialog");
    },
    // upRemark() {
    //   let identity = getIdentity();
    //   // console.log(this.rate);
    //   // console.log(this.userForm.remark);
    //   this.userForm.remark = this.rate + this.userForm.remark;
    //   // console.log(this.userForm.remark);
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
    //         this.rate = null;
    //         this.userForm.remark = "";
    //       } else console.log(resp.data);
    //     })
    //     .catch((err) => {
    //       console.log(err);
    //     });
    // },
  },
  mounted() {
    this.getList();
    this.getATitleDetail();
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