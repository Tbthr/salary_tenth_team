<template>
    <div>
      <my-bread level1='账单信息' level2='提交账单'></my-bread>
        <el-card height="100%">
            <div>
                <el-row>
                  <el-col>
                    <div><i class="el-icon-document"></i> 我的提交</div>
                  </el-col>
                  <el-col>
                    <el-button class='sure' type="primary" @click="AddBillForm=true" style="float:right">添加账单</el-button>
                  </el-col>
                </el-row>
            </div>
            <el-tabs v-model="activeName" @tab-click="handleClick" type="border-card">
              <el-tab-pane label="已通过" name="first" :key="'first'">
                <el-table
                v-if="isFirst"
                :data="checkBill"
                :show-header = "false"
                style="width: 100%">
                    <el-table-column
                    prop="user.name" width="90">
                    </el-table-column>
                    <el-table-column
                    prop="date" width="110">
                    </el-table-column>
                </el-table>
              </el-tab-pane>
              <el-tab-pane label="未通过" name="second" :key="'second'">
                <el-table
                v-if="isSecond"
                :data="unCheckBill"
                :show-header = "false"
                style="width: 100%">
                    <el-table-column
                    prop="user.name" width="90">
                    </el-table-column>
                    <el-table-column
                    prop="date" width="110">
                    </el-table-column>
                    <el-table-column width="110">
                       <span>未通过理由：</span>
                    </el-table-column>
                    <el-table-column
                    prop="mark" width="80">
                    </el-table-column>
                    <el-table-column>
                      <template slot-scope="scope">
                        <el-button type="text" class="button" @click="checkMore(scope.$index)">重新编辑</el-button>
                      </template>
                    </el-table-column>
                </el-table>
              </el-tab-pane>
            </el-tabs>
            <!-- 提交表单 -->
            <el-dialog title="添加表单" :visible.sync="AddBillForm" width="90%" @close="AddBillFormClosed" >
            <div>
                  <el-date-picker v-model="date" type="date" placeholder="选择日期" format="yyyy 年 MM 月 dd 日"
                      value-format="yyyy-MM-dd"
                      :picker-options="pickerOptions"></el-date-picker>
                </div>

                <vxe-table
                border
                resizable
                show-overflow
                keep-source
                ref="xTable"
                :edit-rules="validRules"
                :edit-config="{trigger: 'click', mode: 'row', showStatus: true}"
                :data="tableData">
                <vxe-table-column field="userId" title="职工号"></vxe-table-column>
                <vxe-table-column field="name" title="姓名" width="100"></vxe-table-column>
                <vxe-table-column field="basePay" title="基本工资" :edit-render="{name: 'input'}"></vxe-table-column>
                <vxe-table-column field="seniorityPay" title="工龄工资" :edit-render="{name: 'input'}"></vxe-table-column>
                <vxe-table-column field="postPay" title="职位工资" :edit-render="{name: 'input'}"></vxe-table-column>
                <vxe-table-column field="foodAllow" title="餐饮补贴" :edit-render="{name: 'input'}"></vxe-table-column>
                <vxe-table-column field="trafficAllow" title="交通补贴" :edit-render="{name: 'input'}"></vxe-table-column>
                <vxe-table-column field="travelAllow" title="出差补贴" :edit-render="{name: 'input'}"></vxe-table-column>
                <vxe-table-column field="overtimeAllow" title="加班补贴" :edit-render="{name: 'input'}"></vxe-table-column>
                <vxe-table-column field="otherAllow" title="其他补贴" :edit-render="{name: 'input'}"></vxe-table-column>
                <vxe-table-column field="fiof" title="五险一金" :edit-render="{name: 'input'}"></vxe-table-column>
                <vxe-table-column field="absenceCut" title="缺勤扣款" :edit-render="{name: 'input'}"></vxe-table-column>
                <vxe-table-column field="otherCut" title="其他扣款" :edit-render="{name: 'input'}"></vxe-table-column>
                </vxe-table>
            <span slot="footer" class="dialog-footer">
                <el-button @click="AddBillForm = false">取 消</el-button>
                <el-button type="primary" @click="submit">提 交</el-button>
            </span>
          </el-dialog>
          <!-- 编辑表单 -->
            <el-dialog title="编辑表单" :visible.sync="EditBillForm" width="90%" @close="EditBillFormClosed" >
            <div>
                  <el-date-picker v-model="date1" type="date" placeholder="选择日期" format="yyyy 年 MM 月 dd 日"
                      value-format="yyyy-MM-dd"
                      :picker-options="pickerOptions"></el-date-picker>
                </div>

                <vxe-table
                border
                resizable
                show-overflow
                keep-source
                ref="xTable"
                :edit-rules="validRules"
                :edit-config="{trigger: 'click', mode: 'row', showStatus: true}"
                :data="cBill">
                <vxe-table-column field="userId" title="职工号"></vxe-table-column>
                <vxe-table-column field="user.name" title="姓名" width="100"></vxe-table-column>
                <vxe-table-column field="basePay" title="基本工资" :edit-render="{name: 'input'}"></vxe-table-column>
                <vxe-table-column field="seniorityPay" title="工龄工资" :edit-render="{name: 'input'}"></vxe-table-column>
                <vxe-table-column field="postPay" title="职位工资" :edit-render="{name: 'input'}"></vxe-table-column>
                <vxe-table-column field="foodAllow" title="餐饮补贴" :edit-render="{name: 'input'}"></vxe-table-column>
                <vxe-table-column field="trafficAllow" title="交通补贴" :edit-render="{name: 'input'}"></vxe-table-column>
                <vxe-table-column field="travelAllow" title="出差补贴" :edit-render="{name: 'input'}"></vxe-table-column>
                <vxe-table-column field="overtimeAllow" title="加班补贴" :edit-render="{name: 'input'}"></vxe-table-column>
                <vxe-table-column field="otherAllow" title="其他补贴" :edit-render="{name: 'input'}"></vxe-table-column>
                <vxe-table-column field="fiof" title="五险一金" :edit-render="{name: 'input'}"></vxe-table-column>
                <vxe-table-column field="absenceCut" title="缺勤扣款" :edit-render="{name: 'input'}"></vxe-table-column>
                <vxe-table-column field="otherCut" title="其他扣款" :edit-render="{name: 'input'}"></vxe-table-column>

                </vxe-table>
            <span slot="footer" class="dialog-footer">
                <el-button @click="EditBillForm = false">取 消</el-button>
                <el-button type="primary" @click="submit1">提 交</el-button>
            </span>
          </el-dialog>
        </el-card>
    </div>
</template>
<script>
export default {
  data () {
    return {
      validRules: {
        basePay: [
          { required: true, message: '费用必须填写' },
          { pattern: /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/, message: '格式不正确' }
        ],
        seniorityPay: [
          { required: true, message: '费用必须填写' },
          { pattern: /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/, message: '格式不正确' }
        ],
        postPay: [
          { required: true, message: '费用必须填写' },
          { pattern: /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/, message: '格式不正确' }
        ],
        foodAllow: [
          { required: true, message: '费用必须填写' },
          { pattern: /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/, message: '格式不正确' }
        ],
        trafficAllow: [
          { required: true, message: '费用必须填写' },
          { pattern: /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/, message: '格式不正确' }
        ],
        travelAllow: [
          { required: true, message: '费用必须填写' },
          { pattern: /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/, message: '格式不正确' }
        ],
        overtimeAllow: [
          { required: true, message: '费用必须填写' },
          { pattern: /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/, message: '格式不正确' }
        ],
        otherAllow: [
          { required: true, message: '费用必须填写' },
          { pattern: /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/, message: '格式不正确' }
        ],
        fiof: [
          { required: true, message: '费用必须填写' },
          { pattern: /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/, message: '格式不正确' }
        ],
        absenceCut: [
          { required: true, message: '费用必须填写' },
          { pattern: /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/, message: '格式不正确' }
        ],
        otherCut: [
          { required: true, message: '费用必须填写' },
          { pattern: /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/, message: '格式不正确' }
        ]
      },
      date: '',
      temp: [],
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() > Date.now()
        }
      },
      date1: [],
      cBill: [],
      sub: [],
      useridList: [],
      tableData: [],
      // 添加表单对话框
      AddBillForm: false,
      // 重新表单对话框
      EditBillForm: false,
      checkBill: [],
      unCheckBill: [],
      activeName: 'first',
      isFirst: true,
      isSecond: false
    }
  },
  created () {
    this.getBills()
    this.getUserId()
  },
  methods: {
    getUserId () {
      this.$axios.get('bills/latest', {params: {userId: JSON.parse(sessionStorage.getItem('userData')).id}})
        .then((res) => {
          console.log(res)
          if (res.data.code === 200) {
            this.tableData = res.data.data
          }
        })
    },
    // 添加账单的关闭
    AddBillFormClosed () {
      this.AddForm = false
    },
    // 重新提交账单的关闭
    EditBillFormClosed () {
      this.EditForm = false
    },
    handleClick (tab) {
      if (tab.name === 'first') {
        this.isFirst = true
        this.isSecond = false
      } else if (tab.name === 'second') {
        this.isFirst = false
        this.isSecond = true
      }
    },
    // 获取已发放的工资清单
    async getBills () {
      this.$axios({
        url: 'bills/info/origin',
        method: 'get'
      })
        .then((res) => {
          console.log(res)
          if (res.data.code === 200) {
            for (var i = 0; i < res.data.data.length; i++) {
              if (res.data.data[i].checkStatus === 1) {
                this.checkBill[i] = res.data.data[i]
              } else if (res.data.data[i].checkStatus === -1) {
                this.unCheckBill.push(res.data.data[i])
              }
            }
            this.$message.success(res.data.msg)
          } else {
            this.$message.error(res.data.msg)
          }
        })
    },
    submit () {
      if (this.date === '') {
        this.$message.error('请选择日期')
      } else {
        this.data = JSON.parse(JSON.stringify(this.tableData))
        for (var i = 0, len = this.data.length; i < len; i++) {
          delete this.data[i].age
          delete this.data[i].name
          delete this.data[i].gender
          delete this.data[i].departId
          delete this.data[i].departName
          delete this.data[i].position
          delete this.data[i].userFlag
          delete this.data[i]._XID
        }
        console.log(this.data)
        this.$axios.post('bills/submit', {date: this.date, bills: this.data})
          .then((res) => {
            console.log(res)
            if (res.data.code === 200) {
              this.$message.success(res.data.msg + ',请耐心等待审核')
              this.AddBillForm = false
            }
          })
      }
    },
    submit1 () {
      if (this.date1 === '') {
        this.$message.error('请选择日期')
      } else {
        this.$axios.post('bills/submit', {date: this.date1, bills: this.sub})
          .then((res) => {
            console.log(res)
            if (res.data.code === 200) {
              this.$message.success(res.data.msg + ',请耐心等待审核')
              this.EditBillForm = false
            }
          })
      }
      this.getBills()
    },
    // 查看详情
    checkMore (info) {
      console.log(info)
      this.date1 = this.unCheckBill[info].date
      this.cBill = [JSON.parse(JSON.stringify(this.unCheckBill[info]))]
      this.cBill[0].basePay = String(this.unCheckBill[info].basePay)
      this.cBill[0].seniorityPay = String(this.unCheckBill[info].seniorityPay)
      this.cBill[0].postPay = String(this.unCheckBill[info].postPay)
      this.cBill[0].fiof = String(this.unCheckBill[info].fiof)
      this.cBill[0].travelAllow = String(this.unCheckBill[info].travelAllow)
      this.cBill[0].trafficAllow = String(this.unCheckBill[info].trafficAllow)
      this.cBill[0].foodAllow = String(this.unCheckBill[info].foodAllow)
      this.cBill[0].overtimeAllow = String(this.unCheckBill[info].overtimeAllow)
      this.cBill[0].otherCut = String(this.unCheckBill[info].otherCut)
      this.cBill[0].otherAllow = String(this.unCheckBill[info].otherAllow)
      this.cBill[0].absenceCut = String(this.unCheckBill[info].absenceCut)
      this.sub = JSON.parse(JSON.stringify(this.cBill))
      for (var i = 0; i < this.cBill.length; i++) {
        delete this.sub[i].mark
        delete this.sub[i].user
        delete this.sub[i].department
        delete this.sub[i].checkTime
        delete this.sub[i].date
        delete this.sub[i].incomeTax
        delete this.sub[i].checkStatus
        delete this.sub[i].shouldPay
      }
      this.EditBillForm = true
    }
  }
}
</script>
<style scoped>

</style>
