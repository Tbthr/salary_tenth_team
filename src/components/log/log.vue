<template>
    <div>
    <el-card style="height:;">
      <!-- 面包屑导航区 -->
      <my-bread level1='系统工具' level2='查看日志'></my-bread>
      <el-row>
        <el-col :span="6">
          <download-excel
          :data = "json_data"
          :fields = "json_fields"
          type = "xls"
          worksheet = "My Worksheet"
          name = "日志.xls"
          >
            <el-button type="success" size="small" @click="importLogs()">导出信息</el-button>
            <el-button type="danger" size="small" @click="clearLogs()">删除日志</el-button>
          </download-excel>
        </el-col>
        <br>
        <br>
        <el-col :span="18">
          <el-form label-width = "80px" :inline= 'true' :rules= "rules">
            <div class="block" style="display:inline">
              <label>年</label>
              <el-date-picker
                v-model="yearm"
                align="left"
                type="year"
                placeholder="请选择"
                style="width:100px;">
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
            <el-form-item label="日志等级">
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
                <el-input v-model="userid"  placeholder="请输入职工号或姓名" style="width:160px;" ></el-input>
            </el-form-item>
              <el-button type="primary"  icon = "iconfont icon-sousuo" style="margin-left: 15px" @click="search1();">搜索</el-button>
          </el-form>
          <!-- <el-input v-model="query.other" clearable @clear="getLogList" placeholder="请输入内容" style="width:350px;float:right">
            <el-button slot="append" icon="el-icon-search" @click="getLogList"></el-button>
          </el-input> -->
        </el-col>
      </el-row>
      <el-table :header-cell-style= "{background:'#f3f6fc',color:'#000'}" ref="multipleTable" class="table" :data="logList" border stripe height="475" @selection-change= "selectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column type="index" label="#"></el-table-column>
      <el-table-column label="操作时间" prop="date"></el-table-column>
      <el-table-column label="操作者" prop="username"></el-table-column>
      <el-table-column label="ip" prop="ip"></el-table-column>
      <el-table-column label="操作" prop="module"></el-table-column>
      <el-table-column label="日志类型" prop="info">
          <template slot-scope="scope">
          <el-tag v-if="scope.row.info === 'LOGIN'">LOGIN</el-tag>
          <el-tag type="success" v-else-if="scope.row.info === 'SELECT'">SELECT</el-tag>
          <el-tag type="info" v-else-if="scope.row.info === 'DELETE'">DELETE</el-tag>
          <el-tag type="warning" v-else-if="scope.row.info === 'WARN'">WARN</el-tag>
          <el-tag type="danger" v-else>ERROR</el-tag>
          </template>
      </el-table-column>
      <el-table-column label="日志描述" prop="content"></el-table-column>
      <!-- <el-table-column label="内容" prop="content"></el-table-column> -->
    </el-table>
    <el-pagination
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page="query.page"
      :page-sizes="[5, 10, 15]"
      :page-size="query.rows"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    ></el-pagination>
    </el-card>
    </div>
</template>

<script>
export default {
  data () {
    return {
      total: 0,
      logList: [],
      searchList: [],
      ids: [],
      query: {
        page: 1,
        rows: 10,
        other: ''
      },

      json_data: [],
      json_fields: {
        '操作时间': 'date',
        '操作者': 'username',
        'ip': 'ip',
        '操作': 'module',
        '日志类型': 'info',
        '日志描述': 'content'
      },
      multipleSelection: []
    }
  },
  created () {
    this.getLogList()
  },
  methods: {
    async getLogList () {
      const { data: res } = await this.$axios.get('sys/log', {params: this.query})
      console.log(res)
      if (res.code === 200) {
        this.logList = res.data.list
        this.$message.success(res.msg)
      } else if (res.code === 403) {
        return this.$message.waring(res.msg)
      } else {
        return this.$message.error(res.msg)
      }
      this.total = res.data.total
      console.log(this.total)
    },
    // 监听pageSize
    sizeChange (newSize) {
      this.query.rows = newSize
      this.getLogList()
    },
    // 监听pagenum改变
    currentChange (newPage) {
      this.query.page = newPage
      this.getLogList()
    },
    selectionChange (selection) {
      this.multipleSelection = selection
      for (var i = 0; i < selection.length; i++) {
        this.ids[i] = selection[i].id
      }
      console.log(this.ids)
    },
    async clearLogs () {
      this.$axios({
        url: 'sys/log/delete',
        method: 'POST',
        data: {
          ids: this.ids
        },
        transformRequest: [
          function (data) {
            // Do whatever you want to transform the data
            let ret = ''
            for (let it in data) {
              ret +=
                  encodeURIComponent(it) +
                  '=' +
                  encodeURIComponent(data[it]) +
                  '&'
            }
            return ret
          }
        ]
      }).then((res) => {
        console.log(res)
        if (res.data.code !== 200) {
          this.$message.error(res.data.msg)
        } else {
          this.$message.success(res.data.msg)
        }
        this.ids = []
        this.getLogList()
      })
    },
    async importLogs1 () {
      if (this.logList.length <= 0) {
        this.$message({
          messages: '当前没有可导出的日志',
          type: 'warning'
        })
      }
    },
    importLogs () { // 打印工资条
      if (this.multipleSelection.length === 0) {
        alert('please select')
      } else {
        for (var i = 0; i < this.multipleSelection.length; i++) {
          var jdata = []
          jdata['date'] = this.multipleSelection[i].date
          jdata['username'] = this.multipleSelection[i].username
          jdata['ip'] = this.multipleSelection[i].ip
          jdata['module'] = this.multipleSelection[i].module
          jdata['info'] = this.multipleSelection[i].info
          jdata['content'] = this.multipleSelection[i].content
          this.json_data.push(jdata)
        }
        this.clearSelect()
        this.multipleSelection = []
      }
    },
    clearSelect () {
      this.$refs.multipleTable.clearSelection()
    }

  }
}

</script>
<style scoped>
.table{
  top: 10px;
}
.el-pagination{
  padding: 15px 0 0 0;
}
</style>
