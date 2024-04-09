<template>
  <div>
    <el-empty
      v-if="FavoriteIds.length == 0"
      description="暂时未有关注的职位，建议尝试查看推荐职位"
    >
      <el-button type="primary" @click="$router.push('/recommend')"
        >查看推荐职位</el-button
      >
    </el-empty>
    <div class="favorite-list" v-else>
      <el-descriptions
        class="margin-top"
        title="收藏职位信息"
        :column="1"
        size="medium"
        border
      >
        <template slot="extra">
          <el-popconfirm
            confirm-button-text="好的"
            cancel-button-text="不用了"
            icon="el-icon-info"
            icon-color="red"
            title="确定清空收藏信息？"
            @confirm="deleteAllFavorite"
            @cancel="cancelConfirm"
          >
            <el-button slot="reference" type="primary" size="small"
              >移除所有收藏</el-button
            >
          </el-popconfirm>
        </template>
      </el-descriptions>
      <FavoriteCard :list="FavoriteList.list"></FavoriteCard>
    </div>
  </div>
</template>

<script>
export default {
  name: "Favotite",
  data() {
    return {
      FavoriteIds: [],
      FavoriteList: [],
    };
  },
  beforeMount() {
    this.getFavoriteIds();
  },
  mounted() {
    this.$bus.$on("freshFavotite", this.getFavoriteIds);
  },
  methods: {
    async getFavoriteIds() {
      await this.$API.user
        .getFavoriteId()
        .then(async (resp) => {
          if (resp.data.code == 0) {
            this.FavoriteIds = resp.data.data;
            await this.$API.enterprise
              .getEnterpriseById(this.FavoriteIds)
              .then((resp2) => {
                if (resp2.data.code == 0) {
                  this.FavoriteList = resp2.data.data;
                  console.log(this.FavoriteList);
                }
              })
              .catch((err) => {
                console.log(err);
              });
          } else this.$notify.error("获取失败");
        })
        .catch((err) => {
          console.log(err);
        });
    },
    deleteAllFavorite() {
      this.$API.user
        .deleteAllFavoriteTitles()
        .then((resp) => {
          if (resp.data.code == 0) {
            this.$notify({
              type: "success",
              message: "清除完成",
            });
            this.getFavoriteIds();
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    cancelConfirm() {},
  },
};
</script>

<style>
</style>