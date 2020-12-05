<template>
  <div>
      <!-- 面包屑导航区 -->
    <my-bread level1='个人信息' level2='修改密码'></my-bread>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <i class="el-icon-s-tools"></i><span>修改密码</span>
      </div>
      <div>
        <el-form v-model="ManMsg" label-width="85px" :inline="true">
          <el-form-item label="职工号:">
            <el-input v-model="ManMsg.id" readonly="readonly"></el-input>
          </el-form-item>
          <el-form-item label="姓名:">
            <el-input v-model="ManMsg.name" readonly="readonly"></el-input>
          </el-form-item>
          <el-form-item label="部门:">
            <el-input v-model="ManMsg.department.departName" readonly="readonly"></el-input>
          </el-form-item>
          <el-form-item label="职位:">
            <el-input v-model="ManMsg.position" readonly="readonly"></el-input>
          </el-form-item>
          <el-form-item label="旧密码:">
            <el-input v-model="ManMsg.oldpsd" placeholder="请输入旧密码"></el-input>
          </el-form-item>
          <el-form-item label="新密码:">
            <el-input v-model="ManMsg.newpsd" placeholder="请输入新密码"></el-input>
          </el-form-item>
          <el-form-item label="确认密码:">
            <el-input v-model="ManMsg.confpsd" placeholder="请再次输入新密码"></el-input>
          </el-form-item>
        </el-form>
        <el-button type="primary" style="display:block;margin:0 auto" @click="changePsd">确认修改</el-button>

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
      dialogVisible: false
    }
  },
  created () {
    this.getManMsg()
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
  width: 100%;
}
</style>
