<template>
  <div>
      <!-- 面包屑导航区 -->
    <my-bread level1='个人信息' level2='查看个人信息'></my-bread>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <i class="el-icon-user-solid"></i>
        <span>个人信息</span>
      </div>
      <div>
        <el-form label-width="85px"
        :inline="true"
        :model="ManMsg">
          <el-form-item label="职工号">
            <el-input v-model="ManMsg.id" readonly="readonly"></el-input>
          </el-form-item>
          <el-form-item label="姓名">
            <el-input v-model="ManMsg.name" readonly="readonly"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-input v-model="ManMsg.gender" readonly="readonly"></el-input>
          </el-form-item>
          <el-form-item label="年龄（岁）">
            <el-input v-model="ManMsg.age" readonly="readonly"></el-input>
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="ManMsg.email" readonly="readonly"></el-input>
          </el-form-item>
          <el-form-item label="部门">
            <el-input v-model="ManMsg.department.departName" readonly="readonly"></el-input>
          </el-form-item>
          <el-form-item label="职位">
            <el-input v-model="ManMsg.position" readonly="readonly"></el-input>
          </el-form-item>
          <el-form-item label="身份证号">
            <el-input v-model="ManMsg.idCard" readonly="readonly"></el-input>
          </el-form-item>
          <el-form-item label="工龄">
            <el-input v-model="ManMsg.workYear" readonly="readonly"></el-input>
          </el-form-item>
          <el-form-item label="手机号">
            <el-input v-model="ManMsg.phone" readonly="readonly"></el-input>
          </el-form-item>

        </el-form>
        <el-button  type="primary"  @click="changepassword" style="display:block;margin:0 auto">修改密码</el-button>
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
      ManMsg: {}
    }
  },
  created () {
    this.getMsg()
  },
  methods: {
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
  margin: 20px auto;
  width: 100%;
}
</style>
