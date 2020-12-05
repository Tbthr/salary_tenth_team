<template>
  <div margin:0px auto>
    <el-row :gutter="20">
      <el-col :span="12">
        <div  class="box">
          <el-row >
              <el-image :src="url"></el-image>
          </el-row>
          <el-row >
            <el-link  float: center>喜迎第六次党代会</el-link>
          </el-row>
        </div>
      </el-col>
      <el-col :span="12">
        <el-card class="box-card" >
          <div slot="header" class="clearfix">
            <i class="el-icon-tickets"></i><span>财务讯息</span>
          </div>
          <div margin:0px auto>
            <el-form size="small" label-width="85px">
              <el-link type="info">2020年10月工资已发放完毕请核对详细信息</el-link>
              <el-divider></el-divider>
              <el-link type="info">2020年9月工资已发放完毕请核对详细信息</el-link>
              <el-divider></el-divider>
              <el-link type="info">2020年8月工资已发放完毕请核对详细信息</el-link>
              <el-divider></el-divider>
              <el-link type="info">2020年7月工资已发放完毕请核对详细信息</el-link>
              <el-divider></el-divider>
              <el-link type="info">2020年6月工资已发放完毕请核对详细信息</el-link>
              <el-divider></el-divider>
              <el-link type="info" style="float: right">查看更多...</el-link>
            </el-form>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
// import 1 from "../../assets/images/1.png"
export default {
  data () {
    return {
      url: require('../../assets/images/1.png')
    }
  }
}
</script>
<style>
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }
  .box {
    margin: 60px auto;
    width: 480px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    margin: 20px auto;
    width: 480px;
  }
</style>
