<template>
  <div>
      <!-- 面包屑导航区 -->
    <my-bread level1='个人信息' level2='修改密码'></my-bread>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <i class="el-icon-s-tools"></i><span>修改密码</span>
      </div>
      <div>
        <center>
          <el-form v-model="ManMsg" label-width="85px" :inline="true">
            <el-form-item label="职工号:" style="margin-top:10px">
              <el-input v-model="ManMsg.id" readonly="readonly"></el-input>
            </el-form-item>
            <el-form-item label="姓名:" style="margin-top:10px">
              <el-input v-model="ManMsg.name" readonly="readonly"></el-input>
            </el-form-item>
            <el-form-item label="部门:" style="margin-top:10px">
              <el-input v-model="ManMsg.department.departName" readonly="readonly"></el-input>
            </el-form-item>
            <el-form-item label="职位:" style="margin-top:10px">
              <el-input v-model="ManMsg.position" readonly="readonly"></el-input>
            </el-form-item>
            <el-form-item label="旧密码:" style="margin-top:10px">
              <el-input v-model="ManMsg.oldpsd" placeholder="请输入旧密码" type="password"></el-input>
            </el-form-item>
            <el-form-item label="新密码:" style="margin-top:10px">
              <el-input v-model="ManMsg.newpsd" placeholder="请输入新密码" type="password"></el-input>
            </el-form-item>
            <el-form-item label="确认密码:" style="margin-top:10px">
              <el-input v-model="ManMsg.confpsd" placeholder="请再次输入新密码" type="password"></el-input>
            </el-form-item>
          </el-form>
        <el-button type="primary" style="display:block;margin:0 auto" @click="changePsd">确认修改</el-button>

        </center>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  data () {
    return {
      ManMsg: {},
      dialogVisible: false
    }
  },
  created () {
    this.getManMsg()
  },
  mounted () {
    // 1、数据首次加载完后 → 获取图片（或外层框）宽度，并设置其高度
    this.$nextTick(() => {
      // 获取图片（或外层框）
      let test = this.$refs.test
      // 获取其宽度
      let wtest = test[0].getBoundingClientRect().width
      // 设置其高度（以宽度的60%为例）
      this.test.height = 0.6 * wtest + 'px'
    })
    // 2、挂载 reisze 事件 → 屏幕缩放时监听宽度变化
    const that = this
    window.onresize = () => {
      return (() => {
        window.screenWidth = document.body.clientWidth
        that.screenWidth = window.screenWidth
        console.log(that.screenWidth)
        this.$nextTick(() => {
          let test = this.$refs.test
          let wtest = test[0].getBoundingClientRect().width
          this.test.height = 0.6 * wtest + 'px'
        })
      })()
    }
  },
  watch: {
    screenWidth (val) {
      // 为了避免频繁触发resize函数导致页面卡顿，使用定时器
      if (!this.timer) {
        // 一旦监听到的screenWidth值改变，就将其重新赋给data里的screenWidth
        this.screenWidth = val
        this.timer = true
        let that = this
        setTimeout(function () {
          // 打印screenWidth变化的值
          console.log(that.screenWidth)
          that.timer = false
        }, 400)
      }
    }
  },
  methods: {
    // ManMsg{} = JSON.parse(sessionStorage.getItem('manMsg')),
    getManMsg () {
      const AccountMsg = JSON.parse(sessionStorage.getItem('manMsg'))
      this.ManMsg = AccountMsg
      console.log(this.ManMsg)
    },
    async changePsd () {
      this.$axios({
        url: 'account/edit/psd',
        method: 'post',
        data: {
          id: this.ManMsg.id,
          psd: this.ManMsg.newpsd,
          oldPsd: this.ManMsg.oldpsd
        }
      })
        .then((res) => {
          console.log(res)
          if (res.data.code === 200) {
            this.ManMsg = res.data.data
            this.$message.success(res.data.msg)
            this.$router.push({name: 'login'})
          } else {
            this.$message.error(res.data.msg)
          }
        })
        .catch((error) => {
          console.log(error)
        })
    }
  }
}
</script>

<style scoped>
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.box-card {
  margin: 20px auto;
  width: 50%;
  height: 100%;
}
.el-button{
  margin-top: 10px;
}
</style>
