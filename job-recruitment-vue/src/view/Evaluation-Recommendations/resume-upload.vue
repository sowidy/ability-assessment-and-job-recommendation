<template>
  <div>
    <div class="resumeHead" v-if="!haveResume">
      <span>创建简历让更多的人发现你</span>
      <el-button round class="createResume" @click="dialogFormVisible = true"
        >新建简历</el-button
      >
    </div>
    <div class="resumeEditor">
      <div class="resume-editor" ref="editorFrom">
        <h1>编辑简历</h1>
        <div>
          <!-- 简历 -->
          <div class="resumeSection">
            <div>
              <h2 class="resumeSection__title">简历</h2>
            </div>
            <div class="resumeSection__form">
              <el-form>
                <el-form-item>
                  <el-upload
                    class="uploadFile"
                    ref="resumeUploader"
                    drag
                    name="file"
                    accept=".pdf, .doc, .docx, .ppt, .pptx, .png, jpg, .jpeg"
                    :show-file-list="false"
                    action="http://localhost:8080/api/upload/resume"
                    :on-success="handleResumeUploadSuccess"
                    :on-change="handleResumeUploadChange"
                    :on-progress="handleResumeUploadProgress"
                    :on-error="handleResumeUploadError"
                    :headers="{ token }"
                    :before-upload="checkFile"
                  >
                    <div class="uploadFile__resume">
                      <div class="beforeUpload" v-if="!uploadData.id">
                        <h3>将你的简历拖拽到此处</h3>
                        <div class="upload-button">
                          <el-button round type="primary">选择文件</el-button>
                        </div>
                        <div>
                          请上传 .pdf, .doc, .docx,.ppt,.pptx,.png,jepg,jpg 文件
                        </div>
                      </div>
                      <div class="afterUpload" v-else>
                        <div class="fileicon">
                          <file-icon :fileType="resumeFileType"></file-icon>
                        </div>
                        <h3>{{ uploadData.originName }}</h3>
                        <time class="uploadTime">
                          上次上传：{{ uploadData.createTime | formatDate }}
                        </time>
                        <div class="afterUpload__actionButton">
                          <span class="afterUpload__actionButton-update"
                            >更新</span
                          >
                          <span class="afterUpload__actionButton-dividerLine"
                            >|</span
                          >
                          <span @click.stop="handleRemoveUploadResume"
                            >删除</span
                          >
                        </div>
                      </div>
                    </div>
                  </el-upload>
                </el-form-item>
              </el-form>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="foot">
      <div
        ref="footerAction"
        :class="{ 'resumeEditor-footerAction-fix': footerActionFixed }"
        class="resumeEditor-footerAction"
      >
        <el-button round @click="handleRemoveUploadResume">取消</el-button>
        <el-button
          round
          :loading="submitLoading"
          size="medium"
          @click="submit"
          type="primary"
          :disabled="resumeName == ''"
          >获取能力评价</el-button
        >
      </div>
    </div>
  </div>
</template>

<script>
import { getToken } from "@/utils/token";
import { v4 as uuidv4 } from "uuid";
import FileIcon from "@/components/File-Icon";
import { mapState } from "vuex";
export default {
  name: "ResumeUpload",
  data() {
    return {
      submitLoading: false,
      uploadData: {},
      resumeUploadUpdateHintVisible: false,
      shouldEvaluate: true,
      resume: {},
      setting: {},
      withoutCareer: false,
      careerList: [],
      footerActionFixed: false,
      uploadToken: "",
      resumeUploaded: false,
      haveResume: "",
      resumeName: "",
    };
  },
  components: { FileIcon },
  computed: {
    token() {
      return getToken();
    },
    resumeFileType() {
      const patharr = this.uploadData.originName.split(".");
      return patharr[patharr.length - 1];
    },
    ...mapState("user", ["userInfo", "identity"]),
  },
  mounted() {
    this.$API.user
      .getResume()
      .then((resp) => {
        console.log(resp.data);
        this.uploadData = resp.data.data;
      })
      .catch((err) => {
        console.log(err);
      });
  },
  methods: {
    submit() {
      if (this.resumeName) {
        this.$API.user
          .addResume(this.resumeName)
          .then(async (resp) => {
            if (resp.data.code == 0) {
              await this.$store.dispatch("user/getUserInfo", {
                identity: this.identity,
              });
              this.$notify({
                type: "success",
                message: "获取成功",
              });
              this.$bus.$emit('getScore');
            }
          })
          .catch((err) => {
            console.log(err);
          });
      } else {
        this.$notify({
          type: "error",
          message: "请先上传有效文件",
        });
      }
    },
    handleResumeUploadError(err) {
      this.$notify({
        message: err.message || "网络中断",
        dangerouslyUseHTMLString: true,
        type: "error",
        // duration: 0
      });
      this.$resumeUploadPopupProgress.close();
    },
    handleResumeUploadProgress(progressEvent) {
      this.$resumeUploadPopupProgress.value = progressEvent.percent;
    },
    checkFile(file) {
    const acceptedExtensions = [".pdf", ".doc", ".docx", ".png", ".jpg", ".jpeg"];
    const pathArr = file.name.split(".");
    const fileExtension = "." + pathArr[pathArr.length - 1].toLowerCase();

    if (!acceptedExtensions.includes(fileExtension)) {
      this.$notify.error('请上传以下类型的文件：.pdf, .doc, .docx, .png, .jpg, .jpeg');
      return false; // 中断上传
    }

    return true; // 允许上传
  },
    async handleResumeUploadChange(file) {
      if (file.status === "ready") {
        const pathArr = file.name.split(".");

        const acceptedExtensions = [".pdf", ".doc", ".docx", ".ppt", ".pptx", ".png", ".jpg", ".jpeg"];
        const fileExtension = "." + pathArr[pathArr.length - 1].toLowerCase(); // 获取文件扩展名并转换为小写
        if (!acceptedExtensions.includes(fileExtension)) {
            // this.$notify.error('请上传以下类型的文件：.pdf, .doc, .docx, .ppt, .pptx, .png, .jpg, .jpeg');
            return; // 如果文件类型不符合要求，直接返回，不执行后续操作
        }
        this.$resumeUploadPopupProgress = this.$popupProgress({
          title: "上传中...",
          fileicon:fileExtension//pathArr[pathArr.length - 1],
        }).$on("abort", () => {
          this.$refs.resumeUploader.abort();
          this.$notify.warning("已取消上传");
        });
      }
    },
    handleResumeUploadSuccess({ data }) {
      this.resumeUploaded = true;
      this.resumeUploadUpdateHintVisible = true;
      // console.log(data);
      this.uploadData = { ...data, create_time: Date.now(), id: uuidv4() };

      // console.log(this.uploadData);
      this.resumeName = data;
      // console.log(this.resumeName, "resumename");
      this.resume.resume_attachment = data;
      // console.log(this.resume, "rusume");
      this.$resumeUploadPopupProgress.close();
      // this.$message.success({
      //   message: "上传成功",
      //   center: true,
      // });
    },
    handleRemoveUploadResume() {
      this.resumeName = "";
      this.uploadData = {};
      this.resume.resume_attachment = {};
      delete this.resume.portal_attachment_id;
      this.resumeUploaded = false;
      this.resumeUploadUpdateHintVisible = false;
    },
  },
};
</script>

<style lang='less' scoped>
.resumeHead {
  width: 100%;
  border: 1px solid #ededed;
  background: #f4f4f4;
  padding: 11px 0;
  display: flex;
  justify-content: space-around;
  span{
    text-align: center;
    padding-top: 1% ;
  }
  .createResume {
    // position: relative;
    left: 24px;
  }
}
.resume-editor {
  width: 100%;
  margin: auto;
  padding: 10px 0;
  // border: 1px solid sandybrown;
}
.resumeEditor-footerAction {
  padding: 10px 180px;
  z-index: 33;
  text-align: right;
  &-fix {
    width: 100%;
    background: #fff;
    position: fixed;
    bottom: 0;
    left: 0;

    box-shadow: 0 -2px 10px #eee;
  }
}
.resumeSection {
  padding: 10px 0;
  display: flex;
  justify-content: space-between;
  border-bottom: 1px solid #dcdfe6;

  &__title {
    width: 50px;
    margin-right: 120px;

    padding-bottom: 3px;
    border-bottom: 2px solid #3370ff;
  }
  &__form {
    width: 500px;
    margin-right: 200px;
  }
}
.el-upload {
  width: 100%;
  .beforeUpload {
    margin: auto;
  }

  .uploadFile__works {
    .uploadWorks__fileicon {
      width: 50px;
      height: 50px;
      margin: auto;
    }
    .el-icon-warning {
      font-size: 30px;
    }
  }
  &:focus {
    border-color: inherit;
    color: inherit;
  }
  &-dragger {
    display: table-cell;
    vertical-align: middle;

    border: none;
    background: #f5f7fa;
    width: 600px;
    .upload-button {
      display: inline-block;
      width: 100px;
      margin: 10px 0;
    }

    .afterUpload {
      .fileicon {
        width: 50px;
        height: 50px;
        margin: auto;
      }
      time {
        color: #909399;
      }
      &__actionButton {
        color: #3370ff;
        &-dividerLine {
          margin: 0 14px;
        }
      }
    }
  }
}
.upload-success-tip {
  display: flex;
  background: #f5f7fa;
  padding: 0 7px;
  align-items: center;
  .el-icon-warning {
    color: #3370ff;
  }
  .tips-resolve {
    color: #3370ff;
    cursor: pointer;
    margin-left: 6px;
  }
  .el-icon-close {
    cursor: pointer;
    margin-left: auto;
  }
}
.resumeSection {
  .el-select,
  .el-date-editor {
    width: 100%;
  }
  .bottom-action {
    display: flex;

    .el-icon-plus,
    .el-icon-delete {
      font-size: 22px;
      cursor: pointer;
      &-top {
        margin-left: auto;
      }
    }

    .el-icon-delete {
      margin-left: auto;
    }
  }
  .el-form {
    margin-bottom: 40px;
    &:not(:last-child) {
      border-bottom: 1px solid #e4e7ed;
    }
  }
}
</style>