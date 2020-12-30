<template>
  <div class="test" fixed>
      <!-- 面包屑导航区 -->
    <my-bread level1='个人信息' level2='查看个人信息'></my-bread>
    <el-card class="box-card" >
      <div slot="header" class="clearfix" style="margin-left:30px">
        <i class="el-icon-user-solid"></i>
        <span>个人信息</span>
      </div>
      <div>
        <center>
          <el-form label-width="85px"
        :inline="true"
        :model="ManMsg">
          <el-form-item label="职工号" style="margin-top:10px" >
            <el-input v-model="ManMsg.id" readonly="readonly" ></el-input>
          </el-form-item>
          <el-form-item label="姓名" style="margin-top:10px">
            <el-input v-model="ManMsg.name" readonly="readonly"></el-input>
          </el-form-item>
          <el-form-item label="性别" style="margin-top:10px">

            <el-input v-model="ManMsg.gender"  readonly="readonly">
              <template slot-scope="scope">
                {{ scope.row.gender === 'f'?'女':'男'}}
              </template>
            </el-input>
          </el-form-item>
          <el-form-item label="年龄（岁）" style="margin-top:10px">
            <el-input v-model="ManMsg.age" readonly="readonly"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" style="margin-top:10px">
            <el-input v-model="ManMsg.email" readonly="readonly"></el-input>
          </el-form-item>
          <el-form-item label="部门" style="margin-top:10px">
            <el-input v-model="ManMsg.department.departName" readonly="readonly"></el-input>
          </el-form-item>
          <el-form-item label="职位" style="margin-top:10px">
            <el-input v-model="ManMsg.position" readonly="readonly"></el-input>
          </el-form-item>
          <el-form-item label="身份证号" style="margin-top:10px">
            <el-input v-model="ManMsg.idCard" readonly="readonly"></el-input>
          </el-form-item>
          <el-form-item label="工龄" style="margin-top:10px">
            <el-input v-model="ManMsg.workYear" readonly="readonly"></el-input>
          </el-form-item>
          <el-form-item label="手机号" style="margin-top:10px">
            <el-input v-model="ManMsg.phone" readonly="readonly"></el-input>
          </el-form-item>

        </el-form>
          <el-button  type="primary"  @click="changepassword" style="display:block; margin:0 0 10 0 auto">修改密码</el-button>
        </center>

      </div>
    </el-card>
  </div>
</template>

<script>
import myBread from '../cuscom/myBread.vue'
export default {
  components: { myBread },
  data () {
    return {
      ManMsg: {},
      screenWidth: document.body.clientWidth,
      sex: ''
    }
  },
  created () {
    this.getMsg()
  },
  /*   mounted () {
    const that = this
    window.onresize = () => {
        return (() => {
            window.screenWidth = document.body.clientWidth
            that.screenWidth = window.screenWidth
        })()
    }
  }, */
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
    la (val) {
      let gen = this.ManMsg.gender
      if (gen === 'f') {
        gen = '女'
      } else if (gen === 'm') {
        gen = '男'
      }
      this.ManMag.gender = gen
    },
    changepassword () {
      this.$router.push({
        path: '/account/edit/psd'
      })
    },
    async getMsg () {
      const manMsg = JSON.parse(sessionStorage.getItem('userData', JSON.stringify(this.userData)))
      this.id = manMsg.id
      this.$axios({
        url: 'account/info',
        method: 'post',
        data: {
          id: manMsg.id
        }
      })
        .then((res) => {
          console.log(res)
          if (res.data.code === 200) {
            this.ManMsg = res.data.data
            this.$message.success(res.data.msg)
          } else {
            this.$message.error(res.data.msg)
          }
          sessionStorage.setItem('manMsg', JSON.stringify(res.data.data))
          console.log(JSON.parse(sessionStorage.getItem('manMsg')))
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
  margin: 10px auto;
  width: 50%;
  height:100% ;
}
.el-button {
  margin-top: 10px;
}
</style>
