<template>
  <div>
    <!-- 面包屑导航区 -->
    <my-bread level1='个人信息' level2='详细信息'></my-bread>
    <el-card class="box-card">
      <div class="title">
        <el-row>
          <el-col>
            <i class="el-icon-user-solid"></i>
            <span>基本信息</span>
            <el-link icon="el-icon-edit">编辑</el-link>
          </el-col>
        </el-row>
      </div>
      <section>
        <el-table
          :show-header="false"
          :data="ManMsg"
          :span-method="objectSpanMethod"
          border
          :cell-style="columnStyle"
          style="width: 100%; margin-top: 20px">
          <el-table-column width="180" prop="name"></el-table-column>
          <el-table-column prop="amount1"></el-table-column>
          <el-table-column width="180" prop="amount2"></el-table-column>
          <el-table-column prop="amount3"></el-table-column>
        </el-table>
      </section>
    </el-card>
  </div>
</template>
<script>
import myBread from '../cuscom/myBread.vue'
export default {
  components: { myBread },
  data () {
    return {
    // 请求回来的数据
      dataForm: {
        department: {
          departName: ''
        }
      }
    }
  },
  created () {
    this.getDataForm()
  },
  computed: {
  // 因为数据用到了dataform中的数据，所以写在了computed中
    ManMsg () {
      return [
        {
          name: '职工号',
          amount1: this.dataForm.id,
          amount2: '姓名',
          amount3: this.dataForm.name
        },
        {
          name: '性别',
          amount1: this.dataForm.gender === 'm' ? '男' : '女',
          amount2: '身份证号',
          amount3: this.dataForm.idCard
        },
        {
          name: '部门',
          amount1: this.dataForm.department.departName,
          amount2: '职位',
          amount3: this.dataForm.position
        },
        {
          name: '邮箱',
          amount1: this.dataForm.email,
          amount2: '手机号',
          amount3: this.dataForm.phone
        }
      ]
    }
  },
  methods: {
    async getDataForm () {
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
            this.dataForm = res.data.data
            this.$message.success(res.data.msg)
          } else {
            this.$message.error(res.data.msg)
          }
          sessionStorage.setItem('manMsg', JSON.stringify(res.data.data))
          console.log(JSON.parse(sessionStorage.getItem('manMsg')))
        })
    },
    // 自定义列背景色
    columnStyle ({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0 || columnIndex === 2) {
        return 'background:#f3f6fc;'
      } else {
        return 'background:#ffffff;'
      }
    },
    // 和并列
    objectSpanMethod ({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0) {
        if (rowIndex % 4 === 0) {
          return {
            rowspan: 1,
            colspan: 1
          }
        }
      }
    }
  }
}
</script>
