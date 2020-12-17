<template>
  <div>
    <my-bread level1='账单审批' level2='已审核'></my-bread>
      <el-radio-group v-model="radioButton" @change="goto1pg()" size="medium" >
        <el-radio-button :label="true" >已审批</el-radio-button>
        <el-radio-button :label="false" >未审批</el-radio-button>
      </el-radio-group>
    <div v-show="radioButton">
      <el-card class="box-card">

      <div>
        <!--筛选-->

        <div class="block" style="display:inline">

          <el-form label-width = "80px" :inline= 'true' :rules= "rules">
            <div class="block" style="display:inline">
          <label>年</label>
          <el-date-picker
            v-model="yearm"
            align="left"
            type="year"
            placeholder="请选择"
            style="width:120px;">
          </el-date-picker>
        </div>

        <div class="block" style="display:inline">
          <label>月</label>
          <el-select v-model="mothm" placeholder="请选择" style="width:90px;" clearable>
            <el-option
              v-for="item in this.opmoth"
              :key="item"
              :label="item.label"
              :value="item">
            </el-option>
          </el-select>
        </div>
            <el-form-item label="部门">
                <!-- <el-input v-model="bumen" placeholder="请输入部门"></el-input> -->
                <el-select v-model="position"
                            placeholder="请选择"
                            style="width:130px;"
                            clearable

                            >
                  <el-option  v-for = "(item) in id"
                              :key="item.id "
                              :label="item.departName"
                              :value="item.id"
                              ></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="审核结果">
                <el-select v-model="state"
                           placeholder="请选择结果"
                           style="width:130px;"
                           clearable
                           :options="checkselect">
                  <el-option label="通过" value="pass"></el-option>
                  <el-option label="不通过" value="nopass"></el-option>

                </el-select>
              </el-form-item>
              <!--职工号筛选-->
            <el-form-item label="关键词:" >
                <el-input v-model="userid"  placeholder="请输入职工号或姓名" style="width:170px;" ></el-input>
            </el-form-item>

              <el-button type="primary"  icon = "iconfont icon-sousuo" style="margin-left: 15px" @click="search1();">搜索</el-button>

          </el-form>

        </div>

        <!-- table -->
        <div class="table">
          <el-table
            :data="
              tabledata1.slice(
                (currentPage - 1) * PageSize,
                currentPage * PageSize
              )
            "
            @selection-change="handleSelectionChange"
            :row-key="getRowKeys"
            @sort-change="sortChange"
            border
            style="width: 100%"
          >
          <!--已审批表头-->
            <el-table-column label="基本信息" align="center" >
              <el-table-column fixed type="selection" width="50" reserve-selection ></el-table-column>
              <el-table-column fixed label="#" width="30" type="index"></el-table-column>
              <!-- <el-table-column label="年月" sortable width="100" prop="date"  ></el-table-column> -->
              <el-table-column fixed label="年"  sortable prop="b_year" align="center" width="70px" style="background-color:red;"></el-table-column>
              <el-table-column fixed label="月"  sortable prop="b_moth" align="center" width="60px"></el-table-column>
              <el-table-column fixed label="工号" sortable width="110" prop="userId"></el-table-column>
              <el-table-column fixed label="姓名" width="120  " prop="user.name"></el-table-column>
              <el-table-column fixed label="部门" width="150" prop="department.departName"></el-table-column>
              <!--<el-table-column label="职位" width="90" prop="b_work"></el-table-column>-->
              <!--<el-table-column label="年龄" width="70" prop="b_age"></el-table-column>-->

            </el-table-column>

            <el-table-column label="基本工资" align="center">
              <el-table-column label="基本工资" width="90" prop="basePay"></el-table-column>
              <el-table-column label="工龄工资" width="90" prop="seniorityPay"></el-table-column>
              <el-table-column label="职位工资" width="90" prop="postPay"></el-table-column>
            </el-table-column>

            <el-table-column label="补贴" align="center">
              <el-table-column label="餐饮补贴" width="90" prop="foodAllow"></el-table-column>
              <el-table-column label="交通补贴" width="90" prop="trafficAllow"></el-table-column>
              <el-table-column label="出差补贴" width="90" prop="travelAllow"></el-table-column>
              <el-table-column label="加班补贴" width="90" prop="overtimeAllow"></el-table-column>
              <el-table-column label="其他补贴" width="90" prop="otherAllow"></el-table-column>
            </el-table-column>

            <el-table-column label="扣款" align="center">
              <el-table-column label="五险一金" width="90" prop="fiof" ></el-table-column>
              <el-table-column label="个人税" width="90" prop="incomeTax"></el-table-column>
              <el-table-column label="缺勤扣款" width="90" prop="absenceCut"></el-table-column>
              <el-table-column label="其他扣款" width="90" prop="otherCut"></el-table-column>
            </el-table-column >

            <el-table-column label="应发工资" align="center" prop="shouldPay" >

            </el-table-column>

            <el-table-column label="审核结果" align="center" fixed="right">
              <el-table-column label="通过/不通过" align="center" prop="checkStatus"></el-table-column>
            </el-table-column>

          </el-table>
        </div>
        </div>
          <!--分页-->
          <div class="tabListPage" >
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-size="PageSize"
              layout="total, prev, pager, next, jumper"
              :total="tabledata1.length"

            >
            </el-pagination>
          </div>

    </el-card>
    </div>
    <div v-show="!radioButton">
      <el-card class="box-card">

      <!--筛选-->

         <div class="block" style="display:inline">

          <el-form label-width = "80px" :inline= 'true' :rules= "rules">
            <div class="block" style="display:inline">
          <label>年</label>
          <el-date-picker
            v-model="yearm"
            align="left"
            type="year"
            placeholder="请选择"
            style="width:120px;">
          </el-date-picker>
        </div>

        <div class="block" style="display:inline">
          <label>月</label>
          <el-select v-model="mothm" placeholder="请选择" style="width:90px;" clearable>
            <el-option
              v-for="item in this.opmoth"
              :key="item"
              :label="item.label"
              :value="item">
            </el-option>
          </el-select>
        </div>
            <el-form-item label="部门">
                <!-- <el-input v-model="bumen" placeholder="请输入部门"></el-input> -->
                <el-select v-model="position"
                            placeholder="请选择"
                            style="width:130px;"
                            clearable

                            >
                  <el-option  v-for = "(item) in id"
                              :key="item.id "
                              :label="item.departName"
                              :value="item.id"
                              ></el-option>
                </el-select>
              </el-form-item>
            <!--<el-form-item label="筛选：">-->
              <!--级联选择器     options用来指定数据源-->
              <!--<el-cascader
                v-model = "value"
                :options = "options"
                @change = "handleChange" placeholder="请选择部门" :show-all-levels = "false" ></el-cascader>
            </el-form-item>-->

            <el-form-item label="关键词:" >
                <el-input v-model="userid"  placeholder="请输入职工号或姓名" style="width:170px;" ></el-input>
            </el-form-item>
            <!-- <el-form-item label="姓名:">
                <el-input v-model="username"  placeholder="请输入姓名" style="width:130px;" ></el-input>
            </el-form-item> -->

            <el-button type="primary"  icon = "iconfont icon-sousuo" style="width:90px;" @click="search2()">搜索</el-button>

          </el-form>

        </div>

        <!-- table -->
        <div class="table">
            <el-table
            :data="
              tabledata2.slice(
                (currentPage - 1) * PageSize,
                currentPage * PageSize
              )
            "
            @selection-change="handleSelectionChange"
            :row-key="getRowKeys"
            border
            @sort-change="sortChange"
            style="width: 100%">
          <!--表头-->
            <el-table-column label="基本信息" align="center" >
              <el-table-column fixed type="selection" width="50"  reserve-selection ></el-table-column>
              <el-table-column  fixed label="#" width="30" type="index"></el-table-column>
              <!-- <el-table-column label="年月" sortable width="100" prop="date"></el-table-column> -->
              <el-table-column fixed label="年"  sortable prop="b_year" align="center" width="70px" style="background-color:red;"></el-table-column>
              <el-table-column fixed label="月"  sortable prop="b_moth" align="center" width="60px"></el-table-column>
              <el-table-column fixed label="工号" sortable width="110" prop="userId"></el-table-column>
              <el-table-column fixed label="姓名" width="120" prop="user.name"></el-table-column>
              <el-table-column fixed label="部门" width="150" prop="department.departName"></el-table-column>
              <!--<el-table-column label="职位" width="90" prop="b_work"></el-table-column>-->
              <!--<el-table-column label="年龄" width="70" prop="b_age"></el-table-column>-->

            </el-table-column>

            <el-table-column label="基本工资" align="center">
              <el-table-column label="基本工资" width="90" prop="basePay"></el-table-column>
              <el-table-column label="工龄工资" width="90" prop="seniorityPay"></el-table-column>
              <el-table-column label="职位工资" width="90" prop="postPay"></el-table-column>
            </el-table-column>

            <el-table-column label="补贴" align="center">
              <el-table-column label="餐饮补贴" width="90" prop="foodAllow"></el-table-column>
              <el-table-column label="交通补贴" width="90" prop="trafficAllow"></el-table-column>
              <el-table-column label="出差补贴" width="90" prop="travelAllow"></el-table-column>
              <el-table-column label="加班补贴" width="90" prop="overtimeAllow"></el-table-column>
              <el-table-column label="其他补贴" width="90" prop="otherCut"></el-table-column>
            </el-table-column>

            <el-table-column label="扣款" align="center">
              <el-table-column label="五险一金" width="90" prop="fiof"></el-table-column>
              <el-table-column label="个人税" width="90" prop="incomeTax"></el-table-column>
              <el-table-column label="缺勤扣款" width="90" prop="absenceCut"></el-table-column>
              <el-table-column label="其他扣款" width="90" prop="otherCut"></el-table-column>
            </el-table-column >
            <el-table-column label="实发工资" align="center" >
              <el-table-column label="总计" align="center" prop="shouldPay"></el-table-column>
            </el-table-column>

            <el-table-column label="审批操作" align="center" width="240" fixed="right">
              <template slot-scope="scope">
                <el-button
                  type="primary"
                  size= "mini"
                  icon = "iconfont icon-gou"
                  @click="handleEdit(scope.$index, scope.row);edit1();"
                  >通过</el-button
                >
                <el-button
                  type="danger"
                  size="mini"
                  icon = "iconfont icon-guanbi"
                  @click="dgVisible2();handleDelete(scope.$index, scope.row);"
                  >不通过</el-button
                >
              </template>
            </el-table-column>

          </el-table>
          </div>

        <el-button class="btn btn-block1" type="primary" icon = "iconfont icon-dingdan" @click="edit2()">批量通过</el-button>
        <el-button class="btn btn-block2" type="danger" icon = "iconfont icon-dingdan" @click="dgVisible4()">批量不通过</el-button>
          <!--分页-->
          <div class="tabListPage" >
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"

              :page-size="PageSize"
              layout="total, prev, pager, next, jumper"
              :total="tabledata2.length"

            >
            </el-pagination>
          </div>

    </el-card>
    <!--对话框提示-->
    <!-- <el-dialog title="提示" :visible.sync = "dialogVisible1" width="30%" >
      <span>审批通过成功</span>
      <span slot = "footer" class= "dialog-footer">
        <el-button type="primary" @click= "dialogVisible1 = false">确 定</el-button>
      </span>
    </el-dialog> -->

    <el-dialog title="提示" :visible.sync = "dialogVisible2" width="30%" >
      <el-form>
        <el-form-item label="未通过原因">
            <el-input
              type="textarea" :rows="2" placeholder="请输入内容" v-model="mark"> </el-input>
        </el-form-item>
      </el-form>
      <span slot = "footer" class= "dialog-footer">
        <el-button type="primary" @click= "dialogVisible2 = false; delete1(); ">提 交</el-button>
      </span>
    </el-dialog>

    <!-- <el-dialog title="提示" :visible.sync = "dialogVisible3" width="30%" >
      <span>确定要审批通过吗？</span>
      <span slot = "footer" class= "dialog-footer">
        <el-button type="primary" @click= "dialogVisible3 = false;dgVisible1() ; ok();">确 定</el-button>
      </span>
    </el-dialog> -->

    <el-dialog title="提示" :visible.sync = "dialogVisible4" width="30%" >
      <el-form>
        <el-form-item label="未通过原因">
            <el-input
              type="textarea" :rows="2" placeholder="请输入内容" v-model="mark"> </el-input>
        </el-form-item>
      </el-form>
      <span slot = "footer" class= "dialog-footer">
        <el-button type="primary" @click= "dialogVisible4 = false;delete2()">确 定</el-button>
      </span>
    </el-dialog>
    </div>

  </div>
</template>

<script src="./iconfont.js"></script>

<script>
import myBread from '../cuscom/myBread.vue'
import axios from 'axios'

export default {
  components: {myBread},

  data () {
    //审批状态显示
    var checkstatus = (rule, value, callback) => {
      const checkstatus=null
      if (checkStatus==1) {
        return callback('通过')
      }else if(checkStatus==-1){
        return callback('不通过')
      }else if(checkStatus==0){
        return callback('审核中')
      }else if(checkStatus==null){
        return callback('未审核')
      }

    }
    return {
      yearm:null,
      mothm:null,
      opmoth:['01','02','03','04','05','06','07','08','09','10','11','12'],
      drow:[],
      multipleSelection:[],
      reltable1:[],
      reltable2:[],
      updateTime:"",
      position: '',
      id:[],
      department:[],
      result: '',
      checkStatus:'',
      mark:'',
      checkselect:[],
      dialogVisible1 : false,
      dialogVisible2 : false,
      dialogVisible3 : false,
      dialogVisible4 : false,
      radioButton : true,
      choose: '',
      userId: [],
      userid:'',
      username:'',
      state:'',
      name:[],
      b_year:[],
      b_moth:[],
      currentPage: 1,
      // 总条数，根据接口获取数据长度(注意：这里不能为空)
      totalCount: 1,
      // 个数选择器（可修改）

      PageSize: 6,
      tabledata2:[],
      // 表内数据
      tabledata1:[],
      date : Date.parse(new Date()),
      rules:{
        checkStatus:[//审批状态
          {validator : checkstatus,trigger:'bl'}
        ]

      },
    }

  },

  // 方法
  methods: {
    goto1pg(){
      this.currentPage = 1
    },

    sortChange(column, prop, order){
      console.log(column.order)
      var table=[];

      if(this.radioButton == true) table = this.tabledata1
      else table = this.tabledata2
      console.log(this.radioButton)
        var n = table.length

        if(column.prop == "b_year"){
          if(column.order == "ascending"){ //升序排序
            var temp=null;
            for (var i=0; i<n; i++){
              for (var j = 0;j<n-1-i;j++){
                if (table[j+1].b_year < table[j].b_year){
                  temp = table[j + 1];
                  table[j + 1] = table[j];
                  table[j] = temp;
                }
              }
            }
          }else if(column.order == "descending"){//降序排序
            var temp=null;
            for (var i=0; i<n; i++){
              for (var j = 0;j<n-1-i;j++){
                if (table[j+1].b_year > table[j].b_year){
                  temp = table[j + 1];
                  table[j + 1] = table[j];
                  table[j] = temp;
                }
              }
            }
          }
        }else if(column.prop == "b_moth"){
          if(column.order == "ascending"){ //升序排序
            var temp=null;
            var tp = null;
            for (var i=0; i<n; i++){
              for (var j = 0;j<n-1-i;j++){
                if (table[j+1].b_moth < table[j].b_moth){
                  temp = table[j + 1];
                  table[j + 1] = table[j];
                  table[j] = temp;

                }
              }
            }
          }else if(column.order == "descending"){ //降序排序
            var temp=null;
            for (var i=0; i<n; i++){
              for (var j = 0;j<n-1-i;j++){
                if (table[j+1].b_moth > table[j].b_moth){
                  temp = table[j + 1];
                  table[j + 1] = table[j];
                  table[j] = temp;
                }
              }
            }
          }
        }else if(column.prop == "userId"){
          if(column.order == "ascending"){ //升序排序
            var temp=null;
            var tp = null;
            for (var i=0; i<n; i++){
              for (var j = 0;j<n-1-i;j++){
                if (table[j+1].userId< table[j].userId){
                  temp = table[j + 1];
                  table[j + 1] = table[j];
                  table[j] = temp;

                }
              }
            }
          }else if(column.order == "descending"){ //降序排序
            var temp=null;
            for (var i=0; i<n; i++){
              for (var j = 0;j<n-1-i;j++){
                if (table[j+1].userId > table[j].userId){
                  temp = table[j + 1];
                  table[j + 1] = table[j];
                  table[j] = temp;
                }
              }
            }
          }
        }
        if(this.radioButton == true) table = this.tabledata1
      else table = this.tabledata2

    },
    edit2(){
      var data =[];
      var userId;
      var date;
      var checkStatus = 1
      var checkTime = this.updateTime

      var list =[]
      for(var i=0;i<this.multipleSelection.length;i++){

        userId = this.multipleSelection[i].userId
        date = this.multipleSelection[i].date
        list.push({date,userId,checkTime,checkStatus})
        this.tabledata1.push(this.multipleSelection[i])
        data[i] = this.multipleSelection[i]
      }
      axios.post('bills/check', list)
        .then(function (res) {

        })
        .catch(function (error) {
          console.log(error);
        });

      console.log(this.tabledata2)
      var table=this.tabledata2;
      for(var i=0;i<table.length;i++){
        for(var j=0;j<data.length;j++){
          if(table[i] == data[j]){
            table.splice(i,1)
          }
        }
      }

      this.tabledata2 = table

      this.reltable2 = this.tabledata2
      this.reltable1 = this.tabledata1
    },
    delete2(){
      var mark = this.mark
      if(mark){
      var data =[];
      var userId;
      var date;
      var checkStatus = -1
      var checkTime = this.updateTime

      var list =[]
      for(var i=0;i<this.multipleSelection.length;i++){

        userId = this.multipleSelection[i].userId
        date = this.multipleSelection[i].date
        list.push({date,userId,checkTime,checkStatus,mark})
        this.tabledata1.push(this.multipleSelection[i])
        data[i] = this.multipleSelection[i]
      }
      axios.post('bills/check', list)
        .then(function (res) {

        })
        .catch(function (error) {
          console.log(error);
        });

      console.log(this.tabledata2)
      var table=this.tabledata2;
      for(var i=0;i<table.length;i++){
        for(var j=0;j<data.length;j++){
          if(table[i] == data[j]){
            table.splice(i,1)
          }
        }
      }

      this.tabledata2 = table

      this.reltable2 = this.tabledata2
      this.reltable1 = this.tabledata1

      }else{
        alert('请输入原因')
      }

    },
    edit1(){
      var row = this.drow
      var userId=row.userId
      var date = row.date
      var checkStatus = -1
      var checkTime=this.updateTime

      var list =[{date,userId,checkTime,checkStatus}];
      console.log(list)
      axios.post('bills/check', list)
        .then(function (res) {

        })
        .catch(function (error) {
          console.log(error);
        });

      this.tabledata1.push(row)
      var table=[]
      for(var i=0;i<this.tabledata2.length;i++){
        if(this.tabledata2[i]==row) ;
        else table.push(this.tabledata2[i])
      }
      this.tabledata2 = table;

      this.reltable2 = this.tabledata2
      this.reltable1 = this.tabledata1

    },
    delete1(){
      var mark = this.mark
      if(mark){
        var row = this.drow
        var userId=row.userId
        var date = row.date
        var checkStatus = -1
        var checkTime=this.updateTime

        var list =[{date,userId,checkTime,checkStatus,mark}];
        console.log(list)
        axios.post('bills/check', list)
          .then(function (res) {

          })
          .catch(function (error) {
            console.log(error);
          });

        this.tabledata1.push(row)
        var table=[]
        for(var i=0;i<this.tabledata2.length;i++){
          if(this.tabledata2[i]==row) ;
          else table.push(this.tabledata2[i])
        }
        this.tabledata2 = table;

        this.reltable2 = this.tabledata2
        this.reltable1 = this.tabledata1
      }else{
        alert('请输入原因')
      }
    },

    tableRowClassName({row, rowIndex}) {
        if (rowIndex === 1) {
          return 'warning-row';
        } else if (rowIndex === 3) {
          return 'success-row';
        }else if (rowIndex === 5) {
          return 'success-row';
        }
        return '';
      },

      selectall(){  //选择全部数据
        var table;
        if(this.level == 1)  table =this.tabledata1;
        else if(this.level == 2) table =this.tabledata2;
        else if(this.level == 3) table =this.tabledata3;
        for(var i=0;i<table.length;i++){
          this.toggleSelection([table[i]]);
        }
      },
      toggleSelection(rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row);
          });
        }
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;

      },
      getRowKeys(row) {
        return row.date+row.userId;
      },

      clearSelect() {
        this.$refs.multipleTable.clearSelection();
      },

    handleDelete: function(index, row) {
      console.log(row)
      this.drow = row
    },

    handleEdit: function(index, row) {
      this.drow = row
      // var userId=row.userId
      // var date = row.date
      // var checkStatus = 1
      // var checkTime=this.updateTime

      // var list =[{date,userId,checkTime,checkStatus}];
      //  axios.post('bills/check', list)
      //   .then(function (res) {

      //   })
      //   .catch(function (error) {
      //     console.log(error);
      //   });

      //   this.tabledata1.push(row)
      //   var table=[]
      //   for(var i=0;i<this.tabledata2.length;i++){
      //     if(this.tabledata2[i]==row) ;
      //     else table.push(this.tabledata2[i])
      //   }
      //   this.tabledata2 = table;
      },

    // 分页
    // 每页显示的条数
    handleSizeChange (val) {
      // 改变每页显示的条数
      this.PageSize = val
      // 注意：在改变每页显示的条数时，要将页码显示到第一页
      this.currentPage = 1
    },
    // 显示第几页
    handleCurrentChange (val) {
      // 改变默认的页数
      this.currentPage = val
    },
    handleChange (value) {
      console.log(value)
    },

    //获取当前日期
    addDate(){
      const nowDate = new Date();
            const date = {
                year: nowDate.getFullYear(),
                month: nowDate.getMonth() + 1,
                date: nowDate.getDate(),
            }
            const newmonth = date.month>10?date.month:'0'+date.month
            const day = date.date>10?date.date:'0'+date.date
            this.updateTime = date.year + '-' + newmonth + '-' + day

    },
    //页面切换
    tabChange:function(e){
          let tabid = e.target.dataset.id
          this.tab = tabid
          },
    //已审批的工号和姓名模糊搜索
    search1(){
          // 支持模糊查询
          // this.xmgcqkJsonsData：用于搜索的总数据
         // toLowerCase():用于把字符串转为小写，让模糊查询更加清晰\
        this.tabledata1 = this.reltable1

        let position = this.position;
        let state = this.state;
        let mothm = this.mothm;
        let yearm = this.yearm;
        if(state == "pass") state = 1
          if(state == "nopass") state = -1
          if(position == 1) position = "计算机科学学院"
          if(position == 2) position = "文学院"
          if(position == 3) position = "化学与材料科学学院"
          if(position == 4) position = "财务处"
          if(position == 5) position = "校医院"
          if(position == 6) position = "教务处"
          let _searchId = this.userid.toLowerCase();
          let newListData = []; // 用于存放搜索出来数据的新数组
          let newListData2 = []
          let newListData3 = []
          let newListData4 =[]
          let newListData5 =[]
          if(yearm == undefined || yearm == null){

          }else{
            var str=yearm.toString();
            var st =[];
            st= str.split(' ');
            yearm = st[3];
          }

          if (_searchId) {
            this.tabledata1.filter(item => {
            if (item.userId.toLowerCase().indexOf(_searchId) !== -1||item.user.name.indexOf(_searchId) !== -1) {
              newListData.push(item);
            }
            })
          }else newListData = this.tabledata1
          if(position){
            for(var i=0;i<newListData.length;i++){
              if(newListData[i].department.departName == position){
                newListData2.push(newListData[i])
              }
            }

          }else newListData2 = newListData

          if(state){
            for(var i=0;i<newListData2.length;i++){
              if(newListData2[i].checkStatus == state){
                newListData3.push(newListData2[i])
                console.log('ok')
              }
            }
          }else newListData3 = newListData2

          if(mothm){
            for(var i=0;i<newListData3.length;i++){
              if(newListData3[i].b_moth == mothm){
                newListData4.push(newListData3[i])
                console.log('ok')
              }
            }
          }else newListData4 = newListData3

          if(yearm){
            for(var i=0;i<newListData4.length;i++){
              if(newListData4[i].b_year == yearm){
                newListData5.push(newListData4[i])
                console.log('ok')
              }
            }
          }else newListData5 = newListData4

          this.tabledata1 = newListData5;
        },

      //未审批的工号和姓名模糊搜索
        search2(){
          // 支持模糊查询
          // this.xmgcqkJsonsData：用于搜索的总数据
         //toLowerCase():用于把字符串转为小写，让模糊查询更加清晰
        this.tabledata2 = this.reltable2
        console.log(this.reltable2)
          let _searchId = this.userid.toLowerCase();
          let position = this.position;
          let mothm = this.mothm;
          let yearm = this.yearm;
          if(position == 1) position = "计算机科学学院"
          if(position == 2) position = "文学院"
          if(position == 3) position = "化学与材料科学学院"
          if(position == 4) position = "财务处"
          if(position == 5) position = "校医院"
          if(position == 6) position = "教务处"
          let newListData = []; // 用于存放搜索出来数据的新数组
          let newListData2 = [];
          let newListData3 = [];
          let newListData4 = [];

          if (_searchId) {
            this.tabledata2.filter(item => {
              if (item.userId.toLowerCase().indexOf(_searchId) !== -1||item.user.name.indexOf(_searchId) !== -1) {
                newListData.push(item);
              }
            })
          }else newListData=this.tabledata2

          if(position){
            for(var i=0;i<newListData.length;i++){
              if(newListData[i].department.departName == position){
                newListData2.push(newListData[i])
              }
            }
          }else newListData2 = newListData

          if(mothm){
            for(var i=0;i<newListData2.length;i++){
              if(newListData2[i].b_moth == mothm){
                newListData3.push(newListData2[i])
                console.log('ok')
              }
            }
          }else newListData3 = newListData2

          if(yearm){
            for(var i=0;i<newListData3.length;i++){
              if(newListData3[i].b_year == yearm){
                newListData4.push(newListData3[i])
                console.log('ok')
              }
            }
          }else newListData4 = newListData3

          this.tabledata2 = newListData4;
          console.log('新数组',newListData2);
        },

    //对话框
    dgVisible2(){
      this.dialogVisible2 = true;
    },
    dgVisible4(){
      this.dialogVisible4 = true;
    },

    ok(){
      this.checkStatus = 1;
    },
    fail(){
      this.checkStatus = -1;
    },

    // axios   get请求获取表单 成功
    async getTablelist1 () {
      const { data: res } = await this.$axios.get('bills/info/origin')
      if (res.code === 200) {
        var rdata1=res.data
        var index1  = 0
        var index2  = 0
        var table1=[]
        var table2=[]
        var table3=[]
        var table4=[]

        for(var i=0;i<rdata1.length;i++){
          if(rdata1[i].checkStatus == 1 || rdata1[i].checkStatus==-1){
            table1.push(rdata1[i])
            for(var j=0;j<table1.length;j++){
              table3[j] = table1[j]
              var date1 =[]
              date1[j] =table1[j].date.split('-')
              table3[j].b_year = date1[j][0]
              table3[j].b_moth = date1[j][1]
            }
          }
          else{
            table2.push(rdata1[i])
            for(var j=0;j<table2.length;j++){
              table4[j] = table2[j]
              var date2 =[]
              date2[j] = table2[j].date.split('-')
              table4[j].b_year = date2[j][0]
              table4[j].b_moth = date2[j][1]
            }
          }
        }
        for(var i=0;i<this.tabledata1.length;i++){

        }

        this.reltable1= table3
        this.reltable2= table4
        this.tabledata1 = table3
        this.tabledata2 = table4

        this.userId=res.data
        this.$message.success(res.msg)

      } else if (res.code === 403) {
        return this.$message.waring(res.msg)
      } else {
        return this.$message.error(res.msg)
      }

    },
    //获取部门  成功
    async getPosition () {
      const { data: res } = await this.$axios.get('user/add/departInfo')
      console.log(res.code)
      if (res.code === 200) {
        this.id= res.data
        this.$message.success(res.msg)
      } else if (res.code === 403) {
        return this.$message.waring(res.msg)
      } else {
        return this.$message.error(res.msg)
      }

    },
  },

  created(){
    this.getPosition();
    this.getTablelist1();
    // this.search1();
    // this.search2();
    this.addDate();

  }
}
</script>

<style scoped>
.tabListPage {
  margin-top:30px;

  float: right;
  margin: 10px;
}

.table {
  height: 450px;
  margin-top: 20px;

}
.heard {
  display: flex;
  align-items: center;
  margin-left: 20px;
}
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.box-card {
  width: 100%;
  margin: 10px auto;
  height: 594px;

}

.icon {
  width: 1em;
  height: 1em;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
}

.btn-block{
    position: fixed;
    bottom: 5px;
}

</style>
