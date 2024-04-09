<template>
  <div>
    <!-- style="display:flex;hight:100hv;flex-wrap: wrap;" -->
    <el-card v-for="(i, index) in list" :key="index">
      <div class="inner" @mouseenter="enter(i.id)" @mouseleave="leave">
        <transition enter-active-class="animate__animated animate__slideInLeft">
          <div class="treat"  v-if="hoverId == i.id">
            <el-checkbox  @click.native.once="cancelFavorite(i.id)"
              >取消收藏</el-checkbox
            >
            <el-button @click="goJobDetail(i.id)" size="mini" type="primary"
              >查看</el-button
            >
          </div>
        </transition>
        <div class="enterpriseInfo">
          <h3>
            {{ i.title
            }}<el-tag type="success"
              >￥{{ i.salaryMin }}-{{ i.salaryMax }}/月</el-tag
            ><el-tag type="success">{{ i.titleRequire }}</el-tag>
          </h3>
          <!-- <span>{{ i.hiringManager }}</span><br> -->
          <i class="el-icon-location-outline">{{ i.address || "暂无信息" }}</i>
        </div>
        <div class="logo">
          <img
            :src="i.logo || 'defaultEnAvatar.jpg'"
            style="width: 50px"
            alt=""
          />
          <div>
            <span>{{ i.name }}</span>
            <span><i class="el-icon-suitcase"></i> {{ i.hiringManager }}</span>
            <!-- <span><i class="el-icon-phone-outline"></i> {{i.phone}}</span> -->
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "FavoriteCard",
  props: ["list"],
  data() {
    return {
      checked: '',
      hoverId: -1,
    };
  },
  methods: {
    goJobDetail(id) {
      this.$router.push({ path: "/job", query: { id } });
    },
    enter(id) {
      this.hoverId = id;
    },
    leave() {
      this.hoverId = -1;
    },
    cancelFavorite(id) {
      this.$API.user
        .deleteFavoriteTitle(id)
        .then((resp) => {
          if (resp.data.code == 0) {
            this.$notify({
              type: "success",
              message: "删除成功",
            });
            this.$bus.$emit("freshFavotite");
          } else this.$notify.error("删除失败");
        })
        .catch((err) => {
          console.log(err);
        });
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
  // width: 400px;

  .inner {
    height: 70px;
    display: flex;
    padding: 0;
    justify-content: space-between;
    transition: 1s linear all;
    .logo {
      // border: 1px rgb(175, 39, 24) solid;
      width: 35%;
      display: flex;
      justify-content: space-around;
      img {
        width: 100%;
        height: 100%;
        object-fit: scale-down;
      }
      span {
        display: block;
        margin: 10px 0;
      }
    }

    .enterpriseInfo {
      width: 70%;
      text-align: left;
      // border: 1px rgb(175, 39, 24) solid;
      position: relative;
      h3 {
        // position: absolute;
        // left: 20px;
        padding: 3px 0;
        span {
          font-weight: 500;
          font-size: 15px;
        }
        .el-tag {
          // display: block;
          margin-left: 10px;
        }
      }
      i {
        margin-top: 10px;
        // position: absolute;
        // left: 20px;
        // top: 50px;
      }
    }

    .treat {
      width: 10%;
      // padding: 0;
      // left: -100px;
      margin-right: 3px;
      // border: 1px rgb(175, 39, 24) solid;
      position: relative;
      // transition: 0.5s linear all;
      h5 {
        position: absolute;
        // left: 10%;
        // top: 20px;
        // font-size: 20px;
      }
      .el-checkbox {
        margin-top: 7px;
      }
      .el-button {
        margin-top: 15px;
        // position: absolute;
        // left: 30px;
        // top: 20px;
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