<template>
  <div>
    <!-- 面包屑 -->
    <my-bread level1='个人信息' level2='详细信息'></my-bread>
    <el-card>
      <!-- 基本信息 -->
      <div class="header">
        <span>基本信息</span>
        <el-link
          class="edit"
          icon="el-icon-edit"
          :underline="false"
          :v-model="isedit"
          v-if="!isedit"
          @click="iseditshow"
          >编辑</el-link
        >
        <el-link
          class="edit"
          icon="el-icon-edit"
          :underline="false"
          v-if="isedit"
          @click="exitedit"
          >取消编辑</el-link
        >
      </div>
      <!-- 个人基本信息 -->
      <el-table
        :show-header="false"
        :data="manMsg"
        border
        style="margin-bottom: 50px; font-size: 13px; color: #303133"
        :cell-style="columnStyle"
        :header-cell-style="{ background: '#f8f9fb' }"
        :row-style="{ height: '40px' }"
      >
        <el-table-column prop="key1" width="100%"> </el-table-column>
        <el-table-column show-overflow-tooltip min-width="120">
          <template slot-scope="scope">
            <p v-if="scope.row.value1 != null && !isedit">
              {{ scope.row.value1 }}
            </p>
            <el-input
              v-if="scope.row.value1 != null && isedit"
              v-model="scope.row.value1"
              style="width: 100%"
              :disabled="isdisable(scope.row.key1)"
            >
            </el-input>
          </template>
        </el-table-column>
        <el-table-column prop="key2" width="100%"> </el-table-column>
        <el-table-column show-overflow-tooltip min-width="120">
          <template slot-scope="scope">
            <p v-if="scope.row.value2 != null && !isedit">
              {{ scope.row.value2 }}
            </p>
            <el-input
              v-if="scope.row.value2 != null && isedit"
              v-model="scope.row.value2"
              size="small"
              style="width: 100%"
              :disabled="isdisable(scope.row.key2)"
            >
            </el-input>
          </template>
        </el-table-column>
        <el-table-column prop="key3" width="100%"> </el-table-column>
        <el-table-column show-overflow-tooltip min-width="120">
          <template slot-scope="scope">
            <p v-if="scope.row.value3 != null && !isedit">
              {{ scope.row.value3 }}
            </p>
            <el-input
              v-if="scope.row.value3 != null && isedit"
              v-model="scope.row.value3"
              size="small"
              style="width: 100%"
              :disabled="isdisable(scope.row.key3)"
            >
            </el-input>
          </template>
        </el-table-column>
      </el-table>

      <!-- 职位信息
      <div class="header">
        <span>职位信息</span>
        <el-link
          class="edit"
          icon="el-icon-edit"
          :underline="false"
          :v-model="isedit"
          v-if="!isedit"
          @click="iseditshow"
          >编辑</el-link
        >
        <el-link
          class="edit"
          icon="el-icon-edit"
          :underline="false"
          v-if="isedit"
          @click="exitedit"
          >取消编辑</el-link
        >
      </div> -->
      <!-- 职位基本信息 -->
      <!-- <el-table
        :show-header="false"
        :data="tableData2"
        border
        style="margin-bottom: 30px; font-size: 13px; color: #303133"
        :cell-style="columnStyle"
        :header-cell-style="{ background: '#f8f9fb' }"
        :row-style="{ height: '40px' }"
      >
        <el-table-column prop="key1" style="font-weight: 700" width="100%" >
        </el-table-column>
        <el-table-column show-overflow-tooltip min-width="120">
          <template slot-scope="scope">
            <p v-if="scope.row.value1 != null && !isedit">
              {{ scope.row.value1 }}
            </p>
            <el-input
              v-if="scope.row.value1 != null && isedit"
              v-model="scope.row.value1"
              size="small"
              style="width: 100%"
              :disabled="isdisable(scope.row.key1)"
            >
            </el-input>
          </template>
        </el-table-column>
        <el-table-column prop="key2" width="100%"> </el-table-column>
        <el-table-column show-overflow-tooltip min-width="120">
          <template slot-scope="scope">
            <p v-if="scope.row.value2 != null && !isedit">
              {{ scope.row.value2 }}
            </p>
            <el-input
              v-if="scope.row.value2 != null && isedit"
              v-model="scope.row.value2"
              size="small"
              style="width: 100%"
              :disabled="isdisable(scope.row.key2)"
            >
            </el-input>
          </template>
        </el-table-column >
        <el-table-column prop="key3" width="100%"> </el-table-column>
        <el-table-column show-overflow-tooltip min-width="120">
          <template slot-scope="scope">
            <p v-if="scope.row.value3 != null && !isedit">
              {{ scope.row.value3 }}
            </p>
            <el-input
              v-if="scope.row.value3 != null && isedit"
              v-model="scope.row.value3"
              size="small"
              style="width: 100%"
              :disabled="isdisable(scope.row.key3)"
            >
            </el-input>
          </template>
        </el-table-column>
      </el-table> -->

      <!-- 操作按钮 -->
      <div class="button">
        <el-button type="danger" v-if="isedit" @click="exitedit"
          >取消</el-button
        >
        <el-button
          v-if="isedit"
          style="margin-left: 50px"
          type="primary"
          @click="editUserInfo"
          >确定</el-button
        >
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
      // 个人信息表单
      userForm: {
        department: {
          departName: ''
        }
      },

      // 是否为编辑模式
      isedit: false,

      // 可修改信息
      editList: [
        '邮箱',
        '联系方式'
      ],

      //   // 政治面貌选择项
      //   options: [
      //     {
      //       value: '群众',
      //       label: '群众'
      //     },
      //     {
      //       value: '共青团员',
      //       label: '共青团员'
      //     },
      //     {
      //       value: '中共预备党员',
      //       label: '中共预备党员'
      //     },
      //     {
      //       value: '中共党员',
      //       label: '中共党员'
      //     }
      //   ],
      //   value: '',

      //   // 驾驶证是否持有选择项
      //   driveroptions: [
      //     {
      //       drivervalue: 1,
      //       label: '持有'
      //     },
      //     {
      //       drivervalue: 0,
      //       label: '未持有'
      //     }
      //   ],
      drivervalue: '',
      loading: true
    }
  },

  created () {
    this.getUserForm()
  },

  computed: {
    // 个人基本信息
    manMsg () {
      return [
        {
          key1: '姓名',
          value1: this.userForm.name,
          key2: '性别',
          value2: this.userForm.gender,
          key3: '职工号',
          value3: this.userForm.id
        },
        {
          key1: '证件号码',
          value1: this.userForm.idCard,
          key2: '部门',
          value2: this.userForm.department.departName,
          key3: '职位',
          value3: this.userForm.position
        },
        {
          key1: '邮箱',
          value1: this.userForm.email,
          key2: '联系方式',
          value2: this.userForm.phone,
          key3: '',
          value3: ''
        }
      ]
    },

    // 职位信息
    tableData2 () {
      return [
        {
          key1: '合同期限',
          value1: this.userForm.eWorktime,
          key2: '工种',
          value2: this.userForm.wName,
          key3: '单位',
          value3: this.userForm.pName
        },
        {
          key1: '技能工资',
          value1: this.userForm.eSkillsalary,
          key2: '档案工资',
          value2: this.userForm.eFilesalry,
          key3: '岗位工资',
          value3: this.userForm.ePostsalary
        },
        {
          key1: '累计工资',
          value1: this.userForm.eTotalsalry
        }
      ]
    }
  },

  methods: {
    // 获取当前账号的档案信息
    async getUserForm () {
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
            this.userForm = res.data.data
            this.userForm.gender = res.data.gender === 'm' ? '男' : '女'
            this.$message.success(res.data.msg)
          } else {
            this.$message.error(res.data.msg)
          }
          sessionStorage.setItem('manMsg', JSON.stringify(res.data.data))
          console.log(JSON.parse(sessionStorage.getItem('manMsg')))
        })
    },

    // 修改个人档案信息
    // async editUserInfo () {
    //   const res = await this.$request.request3(
    //     'POST',
    //     '/employee/updateEmployee',
    //     params
    //   )
    //   console.log(res)
    //   if (res.code === '200') {
    //     this.getUserForm()
    //     this.$message.success('更新档案信息成功！')
    //     this.isedit = false
    //   } else {
    //     this.$message.error('更新档案信息失败！')
    //   }
    // },

    // 自定义列背景色
    columnStyle ({ columnIndex }) {
      if (columnIndex === 0 || columnIndex === 2 || columnIndex === 4) {
        return 'background:#f3f6fc;'
      } else {
        return 'background:#ffffff;'
      }
    },

    // 开启编辑模式
    iseditshow () {
      this.isedit = true
    },

    // 取消编辑模式
    exitedit () {
      this.isedit = false
    },

    // 是否禁用输入
    isdisable (data) {
      if (this.editList.indexOf(data) !== -1) {
        return false
      } else {
        return true
      }
    }

  }
}
</script>
<style scoped>
.header {
  margin-bottom: 20px;
  font-size: 16px;
  font-weight: 700;
  border-left: 4px solid #c5d6f8;
  padding-left: 8px;
  line-height: 18px;
  color: #606266;
}
.header .edit {
    background-color: transparent;
    color: #3c94c4;
    text-decoration: none;
    font-size: 15px;
    margin-left: 10px;
}

.button {
  text-align: center;
}

p {
  margin-bottom: 0px;
}

</style>
