<template>
  <div>
    <el-card class="card">
      <!-- 面包屑导航区 -->
      <my-bread level1='账单信息' level2='提交账单'></my-bread>
      <!-- 添加角色按钮 -->
      <el-row>
        <el-col>
        <el-button class='sure' type="primary" @click="AddBillForm=true" float-right>{{item}}</el-button>
        <!-- <el-button :disabled= "isDisabled" class='multi' type="danger" icon="el-icon-delete" @click= "removeMulRoleById()" float-right>批量删除</el-button> -->
        </el-col>
      </el-row>
      <!-- 展示账单 -->
      <div style="margin-left:1%;margin-right:1%">
        <el-row>
          <el-col :span="5" v-for="(item) in bill" :key="item.id" :offset="1" >
            <div style="margin-top:15px">
              <el-card :body-style="{ padding: '0px'}">
                <div>
                  <span><strong>{{item.date}}</strong></span>
                  <div><strong>{{item.user.name}}的工资条</strong></div>
                  <div><strong>实发工资: {{item.shouldPay}}</strong></div>
                  <div><time class="time"><strong>审核状态: {{status[item.checkStatus]}}</strong></time></div>
                  <div><el-button type="text" class="button" @click="checkMore(item)" style="float:right">{{type[item.checkStatus]}}</el-button></div>
                </div>
              </el-card>
            </div>
          </el-col>
        </el-row>
      </div>
      <!-- 添加表单对话框 -->
      <el-dialog title="添加表单" :visible.sync="AddBillForm" width="90%" @close="AddBillFormClosed" >
        <el-button size="mini" type="primary" @click="addRow">增加行</el-button>
        <el-date-picker v-model="date" type="date" placeholder="选择日期" format="yyyy 年 MM 月 dd 日"
      value-format="yyyy-MM-dd"
      :picker-options="pickerOptions"></el-date-picker>
            <el-table :data="billDatas" border style="width: 100%">
                <!-- 额外添加的编号项（可删除） -->
                <el-table-column v-if="billCols.length > 0" type="index" :label="'编号'" :width="50"></el-table-column>
                <el-table-column v-for="(column, idx) in billCols" :key="idx" :index="idx">
                    <!-- 表头，取代':label' -->
                    <template slot="header">
                        <!-- 段落：show为true -->
                        <p align="center">{{column.txt}}</p>
                    </template>

                    <!-- 内容绑定，取代':prop' -->
                    <template slot-scope="scope">
                        <!-- 双击文字或点击修改图标以更改"show"属性 -->
                        <!-- scope.row为元数据，column.col为该列的'键' -->
                        <p v-show="scope.row[column.col].show" @dblclick="scope.row[column.col].show=false" align="center">
                            {{scope.row[column.col].content}}
                            <i class="el-icon-plus" @click="scope.row[column.col].show=false"></i>
                        </p>
                        <!-- 失去焦点时更改"show"属性，显示文本 -->
                        <el-input
                            type="textarea"
                            :autosize="{minRows:2,maxRows:4}"
                            v-show="!scope.row[column.col].show"
                            v-model="scope.row[column.col].content"
                            @blur="scope.row[column.col].show=true">
                        </el-input>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="90px">
                    <template>
                        <el-button type="primary" icon="el-icon-document-checked" size="mini" @click="submit">提交</el-button>
                    </template>
                </el-table-column>
            </el-table>
        <span slot="footer" class="dialog-footer">
            <el-button @click="AddBillForm = false">取 消</el-button>
            <el-button type="primary" @click="submit">提 交</el-button>
        </span>
      </el-dialog>
      <!-- 编辑表单 -->
      <el-dialog title="编辑表单" :visible.sync="EditBillForm" width="90%" @close="EditBillFormClosed" >
        <el-date-picker v-model="date" type="date" placeholder="选择日期" format="yyyy 年 MM 月 dd 日"
      value-format="yyyy-MM-dd"
      :picker-options="pickerOptions"></el-date-picker>
            <el-table :data="newbillDatas" border style="width: 100%">
                <!-- 额外添加的编号项（可删除） -->
                <el-table-column v-if="billCols.length > 0" type="index" :label="'编号'" :width="50"></el-table-column>
                <el-table-column v-for="(column, idx) in billCols" :key="idx" :index="idx">
                    <!-- 表头，取代':label' -->
                    <template slot="header">
                        <!-- 段落：show为true -->
                        <p align="center">{{column.txt}}</p>
                    </template>

                    <!-- 内容绑定，取代':prop' -->
                    <template slot-scope="scope">
                        <!-- 双击文字或点击修改图标以更改"show"属性 -->
                        <!-- scope.row为元数据，column.col为该列的'键' -->
                        <p v-show="scope.row[column.col].show" @dblclick="scope.row[column.col].show=false" align="center">
                            {{scope.row[column.col].content}}
                            <i class="el-icon-plus" @click="scope.row[column.col].show=false"></i>
                        </p>
                        <!-- 失去焦点时更改"show"属性，显示文本 -->
                        <el-input
                            type="textarea"
                            :autosize="{minRows:2,maxRows:4}"
                            v-show="!scope.row[column.col].show"
                            v-model="scope.row[column.col].content"
                            @blur="scope.row[column.col].show=true">
                        </el-input>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="90px">
                    <template>
                        <el-button type="primary" icon="el-icon-document-checked" size="mini" @click="submit2">提交</el-button>
                    </template>
                </el-table-column>
                <el-table-column label="不通过原因" width="90px" prop="mark"></el-table-column>
            </el-table>
        <span slot="footer" class="dialog-footer">
            <el-button @click="EditBillForm = false">取 消</el-button>
            <el-button type="primary" @click="submit2">重新提交</el-button>
        </span>
      </el-dialog>
      <!-- 查看详情对话框 -->
      <el-dialog title="详细信息" :visible.sync="moreInfo" width="60%" @close="moreInfoClosed" >
        <el-table :data='infoData'>
          <el-table-column label="日期" width="100" prop="date"></el-table-column>
          <el-table-column label="姓名" prop="user.name"></el-table-column>
          <el-table-column label="职位" prop="user.position"></el-table-column>
          <el-table-column label="职工号" width="110" prop="user.id"></el-table-column>

          <el-table-column label="基本工资" align="center">
            <el-table-column label="基本工资" width="80" prop="basePay"></el-table-column>
            <el-table-column label="工龄工资" width="80" prop="seniorityPay"></el-table-column>
            <el-table-column label="职位工资" width="80" prop="postPay"></el-table-column>
          </el-table-column>

          <el-table-column label="工资补贴" align="center">
            <el-table-column label="餐饮补贴" width="80" prop="foodAllow"></el-table-column>
            <el-table-column label="交通补贴" width="80" prop="trafficAllow"></el-table-column>
            <el-table-column label="出差补贴" width="80" prop="travelAllow"></el-table-column>
            <el-table-column label="加班补贴" width="80" prop="overtimeAllow"></el-table-column>
            <el-table-column label="其他补贴" width="80" prop="otherAllow"></el-table-column>
          </el-table-column>

          <el-table-column label="扣款" align="center">
            <el-table-column label="五险一金" width="80" prop="fiof"></el-table-column>
            <el-table-column label="缺勤扣款" width="80" prop="absenceCut"></el-table-column>
            <el-table-column label="其他扣款" width="80" prop="otherCut"></el-table-column>
          </el-table-column >

          <el-table-column label="个人税" prop="incomeTax"></el-table-column>

          <el-table-column label="实发工资" prop="shouldPay"></el-table-column>
        </el-table>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
export default {
  data () {
    return {
      item: '+ 添加账单',
      date: '',
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() > Date.now()
        }
      },
      // 提交数组
      data: [],
      data1: [],
      curColumn: null,
      showMenu: false,
      // 表项（头）
      billCols: [ // 以键值存储表头，值可date改变，键不变用以绑定数据
        {col: 'userId', txt: '职工号'},
        {col: 'basePay', txt: '基本工资'},
        {col: 'seniorityPay', txt: '工龄工资'},
        {col: 'postPay', txt: '职位工资'},
        {col: 'foodAllow', txt: '餐饮补贴'},
        {col: 'trafficAllow', txt: '交通补贴'},
        {col: 'travelAllow', txt: '出差补贴'},
        {col: 'overtimeAllow', txt: '加班补贴'},
        {col: 'otherAllow', txt: '其他补贴'},
        {col: 'fiof', txt: '五险一金'},
        {col: 'absenceCut', txt: '缺勤扣款'},
        {col: 'otherCut', txt: '其他扣款'},
        {col: 'incomeTax', txt: '个人税'},
        {col: 'shouldPay', txt: '实发工资'}
      ],
      // 数据
      billDatas: [{
        userId: {content: '', show: true},
        basePay: {content: '', show: true},
        seniorityPay: {content: '', show: true},
        postPay: {content: '', show: true},
        foodAllow: {content: '', show: true},
        trafficAllow: {content: '', show: true},
        travelAllow: {content: '', show: true},
        overtimeAllow: {content: '', show: true},
        otherAllow: {content: '', show: true},
        fiof: {content: '', show: true},
        absenceCut: {content: '', show: true},
        otherCut: {content: '', show: true},
        incomeTax: {content: '', show: true},
        shouldPay: {content: '', show: true}
      }
      ],
      // 编辑表单
      newbillDatas: [{}],
      // 已审核和未通过审核
      bill: [],
      // 添加表单对话框
      AddBillForm: false,
      // 编辑表单对话框
      EditBillForm: false,
      // 查看详情对话框
      moreInfo: false,
      // 添加表单可选项
      addBillForm: [],
      isIndeterminate: true,
      // 提交账单数据
      billForm: [],
      // 查看详情
      infoData: [],
      // 账单状态
      status: {
        'null': '未审批',
        '0': '审核中',
        '1': '通过',
        '-1': '不通过'
      },
      // 操作状态
      type: {
        'null': '查看详情',
        '0': '查看详情',
        '1': '查看详情',
        '-1': '重新编辑'
      }
    }
  },
  inject: ['reload'], // 注入局部刷新
  created () {
    this.getBills()
  },
  methods: {
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
              if (res.data.data[i].checkStatus === 1 || res.data.data[i].checkStatus === -1) {
                this.bill.push(res.data.data[i])
              }
            }
            this.$message.success(res.data.msg)
          } else {
            this.$message.error(res.data.msg)
          }
        })
        .catch((error) => {
          console.log(error)
        })
    },
    // 添加账单的关闭
    AddBillFormClosed () {
      this.AddForm = false
    },
    // 编辑账单的关闭
    EditBillFormClosed () {
      this.EditForm = false
    },
    // 查看详情对话框的关闭
    moreInfoClosed () {
      this.moreInfo = false
    },
    // 查看详情
    checkMore (info) {
      if (info.checkStatus === -1) {
        console.log(info)
        console.log(info.checkStatus)
        // 重新编辑
        this.newbillDatas.length = 0
        this.EditBillForm = true
        this.date = JSON.parse(JSON.stringify(info.date))
        var obj = {}
        obj.userId = {content: info.userId, show: true}
        obj.basePay = {content: JSON.stringify(info.basePay), show: true}
        obj.seniorityPay = {content: JSON.stringify(info.seniorityPay), show: true}
        obj.postPay = {content: JSON.stringify(info.postPay), show: true}
        obj.foodAllow = {content: JSON.stringify(info.foodAllow), show: true}
        obj.trafficAllow = {content: JSON.stringify(info.trafficAllow), show: true}
        obj.travelAllow = {content: JSON.stringify(info.travelAllow), show: true}
        obj.overtimeAllow = {content: JSON.stringify(info.overtimeAllow), show: true}
        obj.otherAllow = {content: JSON.stringify(info.otherAllow), show: true}
        obj.fiof = {content: JSON.stringify(info.fiof), show: true}
        obj.absenceCut = {content: JSON.stringify(info.absenceCut), show: true}
        obj.otherCut = {content: JSON.stringify(info.otherCut), show: true}
        obj.incomeTax = {content: JSON.stringify(info.incomeTax), show: true}
        obj.shouldPay = {content: JSON.stringify(info.shouldPay), show: true}
        obj.mark = JSON.parse(JSON.stringify(info.mark))
        obj.checkStatus = JSON.stringify(info.checkStatus)
        console.log(obj)
        this.newbillDatas.push(obj)
        console.log(this.newbillDatas)
      } else {
        this.moreInfo = true
        console.log(info)
        this.infoData = [info]
        console.log(this.infoData)
      }
    },
    addRow () { // 新增行
      var obj = {}
      this.billCols.map(p => {
        obj[p.col] = {content: '', show: true}
      })
      this.billDatas.push(obj)
    },
    // 提交账单
    submit () {
      // 转换数组
      for (var i = 0; i < this.billDatas.length; i++) {
        this.data1[i] = JSON.parse(JSON.stringify(this.billDatas[i]))
        this.data1[i].userId = JSON.parse(JSON.stringify(this.billDatas[i].userId.content))
        this.data1[i].basePay = JSON.parse(JSON.stringify(this.billDatas[i].basePay.content)) - 0
        this.data1[i].seniorityPay = JSON.parse(JSON.stringify(this.billDatas[i].seniorityPay.content)) - 0
        this.data1[i].postPay = JSON.parse(JSON.stringify(this.billDatas[i].postPay.content)) - 0
        this.data1[i].foodAllow = JSON.parse(JSON.stringify(this.billDatas[i].foodAllow.content)) - 0
        this.data1[i].trafficAllow = JSON.parse(JSON.stringify(this.billDatas[i].trafficAllow.content)) - 0
        this.data1[i].travelAllow = JSON.parse(JSON.stringify(this.billDatas[i].travelAllow.content)) - 0
        this.data1[i].overtimeAllow = JSON.parse(JSON.stringify(this.billDatas[i].overtimeAllow.content)) - 0
        this.data1[i].otherAllow = JSON.parse(JSON.stringify(this.billDatas[i].otherAllow.content)) - 0
        this.data1[i].fiof = JSON.parse(JSON.stringify(this.billDatas[i].fiof.content)) - 0
        this.data1[i].absenceCut = JSON.parse(JSON.stringify(this.billDatas[i].absenceCut.content)) - 0
        this.data1[i].otherCut = JSON.parse(JSON.stringify(this.billDatas[i].otherCut.content)) - 0
        this.data1[i].incomeTax = JSON.parse(JSON.stringify(this.billDatas[i].incomeTax.content)) - 0
        this.data1[i].shouldPay = JSON.parse(JSON.stringify(this.billDatas[i].shouldPay.content)) - 0
      }
      this.$axios({
        url: 'bills/submit',
        method: 'post',
        data: {
          date: this.date,
          bills: this.data
        }
      }).then((res) => {
        console.log(res)
        if (res.data.code !== 200) {
          this.$message.error(res.data.msg)
        } else {
          this.$message.info(res.data.msg)
        }
        this.reload() // 局部刷新
      })
    },
    // 重新提交账单
    submit2 () {
      // 转换数组
      // this.data = JSON.parse(JSON.stringify(this.newbillDatas[0]))
      // console.log(this.data)
      console.log(this.newbillDatas[0].checkStatus)
      this.data.userId = JSON.parse(JSON.stringify(this.newbillDatas[0].userId.content))
      this.data.basePay = JSON.parse(JSON.stringify(this.newbillDatas[0].basePay.content)) - 0
      this.data.seniorityPay = JSON.parse(JSON.stringify(this.newbillDatas[0].seniorityPay.content)) - 0
      this.data.postPay = JSON.parse(JSON.stringify(this.newbillDatas[0].postPay.content)) - 0
      this.data.foodAllow = JSON.parse(JSON.stringify(this.newbillDatas[0].foodAllow.content)) - 0
      this.data.trafficAllow = JSON.parse(JSON.stringify(this.newbillDatas[0].trafficAllow.content)) - 0
      this.data.travelAllow = JSON.parse(JSON.stringify(this.newbillDatas[0].travelAllow.content)) - 0
      this.data.overtimeAllow = JSON.parse(JSON.stringify(this.newbillDatas[0].overtimeAllow.content)) - 0
      this.data.otherAllow = JSON.parse(JSON.stringify(this.newbillDatas[0].otherAllow.content)) - 0
      this.data.fiof = JSON.parse(JSON.stringify(this.newbillDatas[0].fiof.content)) - 0
      this.data.absenceCut = JSON.parse(JSON.stringify(this.newbillDatas[0].absenceCut.content)) - 0
      this.data.otherCut = JSON.parse(JSON.stringify(this.newbillDatas[0].otherCut.content)) - 0
      this.data.incomeTax = JSON.parse(JSON.stringify(this.newbillDatas[0].incomeTax.content)) - 0
      this.data.shouldPay = JSON.parse(JSON.stringify(this.newbillDatas[0].shouldPay.content)) - 0
      console.log(this.data)
      this.$axios({
        url: 'bills/submit',
        method: 'post',
        data: {
          date: this.date,
          bills: this.data
        }
      }).then((res) => {
        console.log(res)
        if (res.data.code !== 200) {
          this.$message.error(res.data.msg)
        } else {
          this.$message.info(res.data.msg)
        }
        this.reload() // 局部刷新
      })
    }
  }
}
</script>
<style scoped>
.el-tag {
  margin: 7px;
}
.multi {
  margin-left: 0;
}
.sure{
  float: right;
  margin-bottom: 10px;
}
.bdtop {
  border-top: 1px solid #eee;
}
.bdbottom {
  border-bottom: 1px solid #eee;
}
.vcenter {
  display: flex;
  align-items: center;
}
</style>
