module.exports = {
  productionSourceMap: false,
  // 关闭ESLINT校验工具
  lintOnSave: false,
  //配置代理跨域
  devServer: {
    proxy: {
      "/api": {
        target: "http://localhost:9090",
        pathRewrite: { 
          //重写匹配的字段，如果不想出现在请求路径上，可以重写为""
          "^/api": ""
        }
      },
    },
  },
};
