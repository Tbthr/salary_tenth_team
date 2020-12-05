<template>
  <div>
    <my-bread level1='查看账单' level2='已审核'></my-bread>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <i class="el-icon-s-tools"></i><span>已审批</span>
      </div>
      <div>
        <!--筛选-->
        <div class="block" :inline = "true">
          <el-form label-width = "80px" :inline= 'true'>
            <el-form-item label="筛选：">

              <!--级联选择器-->
              <el-cascader
                v-model = "value"
                :options = "options"
                @change = "handleChange" placeholder="请选择部门" :show-all-levels = "false" ></el-cascader>
              </el-form-item>

              <!--职工号筛选-->
              <el-form-item label="职工号:">
                <el-input v-model="id"  placeholder="请输入职工号"  ></el-input>
              </el-form-item>

              <!--审核结果筛选-->
              <el-form-item label="审核结果">
                <el-select v-model = "result" placeholder="请选择审核结果">
                  <el-option
                    label="通过"
                    value="通过"
                  ></el-option>
                  <el-option label="不通过" value="不通过"></el-option>
                </el-select>
              </el-form-item>

              <el-button type="primary" style="margin-left: 15px">搜索</el-button>

          </el-form>

        </div>

        <!-- table -->
        <div class="table">
          <el-table
            :data="
              tabledata3.slice(
                (currentPage - 1) * PageSize,
                currentPage * PageSize
              )
            "
            border
            style="width: 100%"
          >
          <!--表头-->
            <el-table-column label="基本信息" align="center">
              <el-table-column type="selection" width="50" ></el-table-column>
              <el-table-column label="#" width="30" type="index"></el-table-column>
              <el-table-column label="年" width="70" prop="b_year"></el-table-column>
              <el-table-column label="月" width="70" prop="b_moth" :filters="this.guolv"></el-table-column>
              <el-table-column label="姓名" width="70" prop="b_name"></el-table-column>
              <el-table-column label="部门" width="90" prop="b_class"></el-table-column>
              <el-table-column label="职位" width="90" prop="b_work"></el-table-column>
              <el-table-column label="年龄" width="70" prop="b_age"></el-table-column>
              <el-table-column label="职工号" width="90" prop="b_workid"></el-table-column>
            </el-table-column>

            <el-table-column label="基本工资" align="center">
              <el-table-column label="底薪" width="90" prop="b_basem"></el-table-column>
              <el-table-column label="工龄工资" width="90" prop="b_agem"></el-table-column>
            </el-table-column>

            <el-table-column label="工资补贴" align="center">
              <el-table-column label="交通补贴" width="90" prop="b_trasal"></el-table-column>
              <el-table-column label="加班补贴" width="90" prop="b_owsal"></el-table-column>
            </el-table-column>

            <el-table-column label="扣款" align="center">
              <el-table-column label="迟到" width="90" prop="b_otime"></el-table-column>
              <el-table-column label="早退" width="90" prop="b_mtime"></el-table-column>
            </el-table-column >

            <el-table-column label="实发工资" align="center" >
              <el-table-column label="总计" align="center" prop="b_realsal"></el-table-column>
            </el-table-column>

            <el-table-column label="审核结果" align="center" >
              <el-table-column label="通过/不通过" align="center" prop="b_result"></el-table-column>
            </el-table-column>

          </el-table>

          <!--分页-->
          <div class="tabListPage">
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="pageSizes"
              :page-size="PageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="tabledata3.length"
            >
            </el-pagination>
          </div>

        </div>

      </div>

    </el-card>

  </div>
</template>

<script>
import myBread from '../cuscom/myBread.vue'
export default {
  components: {myBread},
  data () {
    return {
      choose: '',
      id: '',
      bumen: '',
      result: '',
      currentPage: 1,
      // 总条数，根据接口获取数据长度(注意：这里不能为空)
      totalCount: 1,
      // 个数选择器（可修改）
      pageSizes: [5, 10, 15, 20],
      // 默认每页显示的条数（可修改）
      PageSize: 5,
      // 表内数据
      tabledata3: [/* {
          b_year:'2000',
          b_moth:'5月',
          b_age:'45',
          b_class:'食堂',
          b_work:'普通员工',
          b_name:'赵忠',
          b_workid:'20181204',
          b_basem:'3100',
          b_agem:'1000',
          b_realsal:'5000',
          b_trasal:'300',
          b_owsal:'2000',
          b_otime:'200',
          b_mtime:'100',
          b_result:'通过'
        },{
          b_year:'2000',
          b_moth:'5月',
          b_age:'41',
          b_class:'财务部门',
          b_work:'普通员工',
          b_name:'黄莹莹',
          b_workid:'20181303',
          b_basem:'8200',
          b_agem:'1000',
          b_realsal:'5000',
          b_trasal:'300',
          b_owsal:'2000',
          b_otime:'200',
          b_mtime:'100',
          b_result:'通过'
        },{
          b_year:'2000',
          b_moth:'5月',
          b_age:'49',
          b_class:'财务部门',
          b_work:'普通员工',
          b_name:'蒋文文',
          b_workid:'20181302',
          b_basem:'8100',
          b_agem:'1000',
          b_realsal:'5000',
          b_trasal:'300',
          b_owsal:'2000',
          b_otime:'200',
          b_mtime:'100',
          b_result:'不通过'
        },{
          b_year:'2000',
          b_moth:'5月',
          b_age:'43',
          b_class:'财务部门',
          b_work:'普通员工',
          b_name:'陈源',
          b_workid:'20181301',
          b_basem:'7600',
          b_agem:'1000',
          b_realsal:'5000',
          b_trasal:'300',
          b_owsal:'2000',
          b_otime:'200',
          b_mtime:'100',
          b_result:'通过'
        },{
          b_year:'2000',
          b_moth:'5月',
          b_age:'56',
          b_class:'食堂',
          b_work:'普通员工',
          b_name:'韩梅梅',
          b_workid:'20181205',
          b_basem:'3200',
          b_agem:'1000',
          b_realsal:'5000',
          b_trasal:'300',
          b_owsal:'2000',
          b_otime:'200',
          b_mtime:'100',
          b_result:'不通过'
        },{
          b_year:'2000',
          b_moth:'5月',
          b_age:'38',
          b_class:'计算机科学学院',
          b_work:'普通员工',
          b_name:'王晓丽',
          b_workid:'20181101',
          b_basem:'5600',
          b_agem:'1000',
          b_realsal:'5000',
          b_trasal:'300',
          b_owsal:'2000',
          b_otime:'200',
          b_mtime:'100',
          b_result:'不通过'
        },{
          b_year:'2000',
          b_moth:'5月',
          b_age:'43',
          b_class:'计算机科学学院',
          b_work:'普通员工',
          b_name:'夏雨',
          b_workid:'20181103',
          b_basem:'5700',
          b_agem:'1000',
          b_realsal:'5000',
          b_trasal:'300',
          b_owsal:'2000',
          b_otime:'200',
          b_mtime:'100',
          b_result:'通过'
        },{
          b_year:'2000',
          b_moth:'2月',
          b_age:'40',
          b_class:'食堂',
          b_work:'普通员工',
          b_name:'王建国',
          b_workid:'20181201',
          b_basem:'3400',
          b_agem:'1000',
          b_realsal:'5000',
          b_trasal:'200',
          b_owsal:'1000',
          b_otime:'0',
          b_mtime:'0',
          b_result:'通过'

        },{
          b_year:'2000',
          b_moth:'6月',
          b_age:'53',
          b_class:'计算机科学学院',
          b_work:'普通员工',
          b_name:'张帆',
          b_workid:'20181102',
          b_basem:'6200',
          b_agem:'1000',
          b_realsal:'5000',
          b_trasal:'600',
          b_owsal:'3000',
          b_otime:'250',
          b_mtime:'150',
          b_result:'通过'
        },{
          b_year:'2000',
          b_moth:'12月',
          b_age:'36',
          b_class:'食堂',
          b_work:'普通员工',
          b_name:'李梅',
          b_workid:'20181202',
          b_basem:'3400',
          b_agem:'1000',
          b_realsal:'5000',
          b_trasal:'320',
          b_owsal:'230',
          b_otime:'200',
          b_mtime:'100',
          b_result:'不通过'
        }, */],

      // 级联选择器数据
      value: [],
      options: [{
        value: 'jisuanji',
        label: '计算机科学学院',
        children: [{
          value: '20181101',
          label: '王晓丽'
        }, {
          value: '20181102',
          label: '张帆'

        }, {
          value: '20181103',
          label: '夏雨'
        }]
      }, {
        value: 'shitang',
        label: '食堂',
        children: [{
          value: '20181201',
          label: '王建国'
        }, {
          value: '20181202',
          label: '李梅'
        }, {
          value: '20181203',
          label: '陈莉'
        }, {
          value: '20181204',
          label: '赵忠'
        }, {
          value: '20181205',
          label: '韩梅梅'
        }]
      }, {
        value: 'caiwubumen',
        label: '财务部门',
        children: [{
          value: '20181301',
          label: '陈源'
        }, {
          value: '20181302',
          label: '蒋文文'
        }, {
          value: '20181303',
          label: '黄莹莹'
        }]
      }]
    }
  },

  // 方法
  methods: {
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
    // axios请求
    getTableList () {
      this.axios({
        url: '47.92.165.44:8080/ salary/ salaryCheck',
        method: 'get',
        // eslint-disable-next-line no-undef
        data: tabledata3
      })
    }
  }
}
</script>

<style scoped>
.tabListPage {
  float: right;
  margin: 10px;
}
.table {
  margin-top: 20px;
  border:3px;
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
  width: 99%;
  margin: 20px auto;
}
</style>
