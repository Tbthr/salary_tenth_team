<template>
  <div>
    <el-card class="el-card">
      <center>
        <el-form inline>
          <el-form-item>
            <div>
              <span style="font-size: 28px;font-weight: bold;">性别分布</span>
            </div>
            <div id="myChart" style="width: 550px; height: 400px"></div>
          </el-form-item>
          <el-form-item>
            <div>
                      <span style="font-size: 28px;font-weight: bold;">年龄分布</span>
            </div>
            <div id="myChart1" style="width: 550px; height: 400px"></div>
          </el-form-item>
        </el-form>
        <el-form inline>
          <el-form-item>
            <div>
                      <span style="font-size: 28px;font-weight: bold;">职位分布</span>
            </div>
            <div id="myChart2" style="width: 550px; height: 400px"></div>
          </el-form-item>
          <el-form-item>
            <div>
                      <span style="font-size: 28px;font-weight: bold;">部门分布</span>
            </div>
            <div id="myChart3" style="width: 550px; height: 400px"></div>
          </el-form-item>
        </el-form>
      </center>
    </el-card>
  </div>
</template>

<script>
export default {

  data () {
    return {
      picListSex: [],
      picListAge: [],
      picListDepart: [],
      picListPosition: []
    }
  },

  mounted () {
    this.getmyCharts()
    this.getmyCharts1()
    this.getmyCharts2()
    this.getmyCharts3()
  },
  methods: {
    // 男女比例
    drawLine () {
      let myChart = this.$echarts.init(document.getElementById('myChart'))
      // data:

      myChart.setOption({
        tooltip: {
          trigger: 'item',
          formatter: '{a}<br/>{b}:{c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          x: 'left',
          data: this.opinion
        },
        series: [
          {
            name: '员工管理',
            type: 'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            label: {
              normal: {
                show: false,
                position: 'center',
                textStyle: {
                  fontSize: 30
                }
              },
              emphasis: {
                show: true,
                label: {
                  show: true,
                  fontSize: '90',
                  fontWeight: 'bold'
                }
              }
            },
            labelLine: {
              normal: {
                show: false
              }
            },
            data: [
              {value: this.picListSex[0].cnt, name: '男'},
              {value: this.picListSex[1].cnt, name: '女'}
            ]
          }
        ]
      })
    },
    // 年龄分布
    drawLine1 () {
      let myChart1 = this.$echarts.init(document.getElementById('myChart1'))
      var data = []
      var legendData = []
      for (let i = 0; i < this.picListAge.length; i++) {
        var obj1 = {}
        obj1.value = this.picListAge[i].cnt
        obj1.name = this.picListAge[i].mark + '岁'
        legendData.push((this.picListAge[i].mark + '岁'))
        data.push(obj1)
      }

      myChart1.setOption({
        tooltip: {
          trigger: 'item',
          formatter: '{a}<br/>{b}:{c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          x: 'left',
          data: this.opinion
        // data:legendData,
        },
        series: [
          {
            name: '员工管理',
            type: 'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            label: {
              normal: {
                show: false,
                position: 'center',
                textStyle: {
                  fontSize: 30
                }

              },
              emphasis: {
                show: true,
                label: {
                  show: true,
                  fontSize: '90',
                  fontWeight: 'bold'
                }
              },

              textStyle: {
                fontSize: 20
              }

            },
            labelLine: {
              normal: {
                show: false
              }
            },
            data: data
          }
        ]
      })
    },
    // 部门比例
    drawLine2 () {
      let myChart2 = this.$echarts.init(document.getElementById('myChart2'))
      var data = []
      for (let i = 0; i < this.picListDepart.length; i++) {
        var obj1 = {}
        obj1.value = this.picListDepart[i].cnt
        obj1.name = this.picListDepart[i].mark
        data.push(obj1)
      }

      myChart2.setOption({
        tooltip: {
          trigger: 'item',
          formatter: '{a}<br/>{b}:{c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          x: 'left',
          data: this.opinion
        },
        series: [
          {
            name: '员工管理',
            type: 'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            label: {
              normal: {
                show: false,
                position: 'center',
                textStyle: {
                  fontSize: 30
                }
              },
              emphasis: {
                show: true,
                label: {
                  show: true,
                  fontSize: '90',
                  fontWeight: 'bold'
                }
              }
            },
            labelLine: {
              normal: {
                show: false
              }
            },
            data: data
          }
        ]
      })
    },
    // 职位比例
    drawLine3 () {
      let myChart3 = this.$echarts.init(document.getElementById('myChart3'))
      var data = []
      for (let i = 0; i < this.picListPosition.length; i++) {
        var obj1 = {}
        obj1.value = this.picListPosition[i].cnt
        obj1.name = this.picListPosition[i].mark
        data.push(obj1)
      }

      myChart3.setOption({
        tooltip: {
          trigger: 'item',
          formatter: '{a}<br/>{b}:{c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          x: 'left',
          data: this.opinion
        },
        series: [
          {
            name: '员工管理',
            type: 'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            label: {
              normal: {
                show: false,
                position: 'center',
                textStyle: {
                  fontSize: 30
                }
              },
              emphasis: {
                show: true,
                label: {
                  show: true,
                  fontSize: '90',
                  fontWeight: 'bold'
                }
              }
            },
            labelLine: {
              normal: {
                show: false
              }
            },
            data: data
          }
        ]
      })
    },
    // 获取性别分布
    async getmyCharts () {
      const { data: res } = await this.$axios.get('/index')
      console.log(res)
      if (res.code === 200) {
        this.picListSex = res.data.gender
        this.picListAge = res.data.age
        this.picListDepart = res.data.depart
        this.picListPosition = res.data.position
        this.$message.success(res.msg)
        this.drawLine()
        this.drawLine1()
        this.drawLine2()
        this.drawLine3()
      } else if (res.code === 403) {
        return this.$message.waring(res.msg)
      } else {
        return this.$message.error(res.msg)
      }
    },
    // 获取年龄分布
    async getmyCharts1 () {
      var token = await localStorage.getItem('token')
      var res = await this.$axios.request(
        'POST',
        'salary/user/info',
        token
      )
      console.log(res)
      var showList = res.data
      // this.typeNameList = res.data;
      // this.moveNumList = res.data;
      var picList1 = []
      for (var i = 0; i < showList.length; i++) {
        var obj = { value: showList[i].Num, name: showList[i].typename }
        picList1.push(obj)
      }
      this.drawLine1(picList1)
    },
    // 获取部门管理
    async getmyCharts2 () {
      var token = await localStorage.getItem('token')
      var res = await this.$axios.request(
        'POST',
        'salary/user/info',
        token
      )
      console.log(res)
      var showList2 = res.data
      // this.typeNameList = res.data;
      // this.moveNumList = res.data;
      var picList2 = []
      for (var i = 0; i < showList2.length; i++) {
        var obj = { value: showList2[i].eNum, name: showList2[i].ePolitical }
        picList2.push(obj)
      }
      this.drawLine2(picList2)
    },
    // 获取职位管理
    async getmyCharts3 () {
      var token = await localStorage.getItem('token')
      var res = await this.$axios.request(
        'POST',
        'salary/user/info',
        token
      )
      console.log(res)
      var showList = res.data
      // this.typeNameList = res.data;
      // this.moveNumList = res.data;
      var picList3 = []
      for (var i = 0; i < showList.length; i++) {
        var obj = { value: showList[i].eNum, name: showList[i].eEducation }
        picList3.push(obj)
      }
      this.drawLine3(picList3)
    }
  }
}
</script>
<style scoped>
.el-card {
  height: 100%;
}
</style>
