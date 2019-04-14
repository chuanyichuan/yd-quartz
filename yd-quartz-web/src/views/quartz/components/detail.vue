<template>
  <div class="createPost-container">
    <el-form ref="postForm" :model="postForm" :rules="rules" class="form-container">
      <sticky :z-index="10" style="margin-top: 10px;margin-right: 20px" align="right">
        <el-button v-loading="loading" style="margin-left: 10px;" type="success" @click="submitForm">
          保存
        </el-button>
      </sticky>
      <div class="createPost-main-container">
        <el-form-item label-width="120px" label="名称:" class="postInfo-container-item">
          <el-input v-model="postForm.name" placeholder="请输入调度任务名称" type="text" />
        </el-form-item>
        <el-form-item label-width="120px" label="负责人:" class="postInfo-container-item">
          <el-input v-model="postForm.author" placeholder="请输入调度任务负责人" type="text"/>
        </el-form-item>
        <el-form-item label-width="120px" label="接口任务地址:" class="postInfo-container-item">
          <el-input v-model="postForm.url" placeholder="请输入接口任务地址" type="text"/>
        </el-form-item>
        <el-form-item label-width="120px" label="调度表达式:" class="postInfo-container-item">
          <el-input v-model="postForm.cornExpression" placeholder="请输入调度时间表达式" />
        </el-form-item>
        <el-form-item label-width="120px" label="开始时间:" class="postInfo-container-item">
          <el-date-picker v-model="postForm.startTime" placeholder="选择日期时间" value-format="yyyy-MM-dd HH:mm:ss" type="datetime" format="yyyy-MM-dd HH:mm:ss"/>
        </el-form-item>
        <el-form-item label-width="120px" label="结束时间:" class="postInfo-container-item">
          <el-date-picker v-model="postForm.endTime" placeholder="选择日期时间" value-format="yyyy-MM-dd HH:mm:ss" type="datetime" format="yyyy-MM-dd HH:mm:ss"/>
        </el-form-item>
        <el-form-item style="margin-bottom: 40px;" label-width="120px" label="描述:">
          <el-input v-model="postForm.description" :rows="1" placeholder="请输入描述信息" type="textarea" class="article-textarea" autosize/>
          <span v-show="contentShortLength" class="word-counter">{{ contentShortLength }}字</span>
        </el-form-item>
      </div>
    </el-form>
  </div>
</template>

<script>
import { validURL } from '@/utils/validate'
import { add, modify, info } from '@/api/quartz'
import Sticky from '@/components/Sticky' // 粘性header组件

const defaultForm = {
  id: '',
  name: '', // 调度任务名称
  author: '', // 调度任务责任人
  url: '', // 接口任务地址
  cornExpression: '0/1 * * * * ?', // 调度执行时间表达式
  startTime: '', // 调度任务运行时间区起期
  endTime: '', // 调度任务运行时间区止期
  description: '' // 调度任务描述
}
export default {
  name: 'Detail',
  components: { Sticky },
  props: {
    isEdit: {
      type: Boolean,
      default: false
    }
  },
  data() {
    const validateRequire = (rule, value, callback) => {
      if (value === '') {
        this.$message({
          message: rule.field + '为必传项',
          type: 'error'
        })
        callback(new Error(rule.field + '为必传项'))
      } else {
        callback()
      }
    }
    const validateSourceUri = (rule, value, callback) => {
      if (value) {
        if (validURL(value)) {
          callback()
        } else {
          this.$message({
            message: '接口任务地址填写不正确',
            type: 'error'
          })
          callback(new Error('外链url填写不正确'))
        }
      } else {
        callback()
      }
    }
    return {
      postForm: Object.assign({}, defaultForm),
      loading: false,
      rules: {
        name: [{ validator: validateRequire }],
        author: [{ validator: validateRequire }],
        url: [{ validator: validateSourceUri, trigger: 'blur' }],
        cornExpression: [{ validator: validateRequire }],
        startTime: [{ validator: validateRequire }],
        endTime: [{ validator: validateRequire }]
      },
      tempRoute: {}
    }
  },
  computed: {
    contentShortLength() {
      return this.postForm.description.length
    }
  },
  created() {
    if (this.isEdit) {
      const id = this.$route.params && this.$route.params.id
      this.fetchData(id)
    } else {
      this.postForm = Object.assign({}, defaultForm)
    }
    this.tempRoute = Object.assign({}, this.$route)
  },
  methods: {
    fetchData(id) {
      info(id).then(response => {
        this.postForm = response.data.data
      })
    },
    submitForm() {
      this.$refs.postForm.validate(valid => {
        if (valid) {
          this.loading = true
          if (this.isEdit) {
            modify(this.postForm).then(response => {
              this.$message({
                message: response.data.message,
                duration: 5 * 1000
              })
            })
          } else {
            add(this.postForm).then(response => {
              this.$message({
                message: response.data.message,
                duration: 5 * 1000
              })
            })
          }
          this.loading = false
        }
      })
    },
    draftForm() {
      if (this.postForm.content.length === 0 || this.postForm.title.length === 0) {
        this.$message({
          message: '请填写必要的标题和内容',
          type: 'warning'
        })
        return
      }
      this.$message({
        message: '保存成功',
        type: 'success',
        showClose: true,
        duration: 1000
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  @import "~@/styles/mixin.scss";

  .createPost-container {
    position: relative;

    .createPost-main-container {
      padding: 40px 45px 20px 50px;

      .postInfo-container {
        position: relative;
        @include clearfix;
        margin-bottom: 10px;

        .postInfo-container-item {
          float: left;
        }
      }
    }

    .word-counter {
      width: 40px;
      position: absolute;
      right: -10px;
      top: 0px;
    }
  }
</style>
