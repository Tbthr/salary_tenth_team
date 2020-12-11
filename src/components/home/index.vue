<template>
  <div class="div_body">
    <el-row gutter="24">
      <el-col :span="16">
        <el-card class="card_r">

          <div class="block">
            <span class="demonstration"></span>
            <el-carousel trigger="click" height="300px">
              <el-carousel-item v-for="item in 4" :key="item">
              </el-carousel-item>
            </el-carousel>
          </div>
          <el-col span="11" class="cl">
            <label style="font-size:18px;">党的方针</label>
            <el-divider></el-divider>
            <el-link type="info">坚定不移坚持党的领导方针 </el-link>
            <el-divider></el-divider>
          </el-col>
          <el-col span="2">
            <div class="verticalBar"></div>
          </el-col>
          <el-col span="11" class="cl">
            <label style="font-size:18px;">系统消息</label>
            <el-divider></el-divider>
            <el-link type="info">今晚8点系统维护,届时请勿登录进行任何操作</el-link>
            <el-divider></el-divider>
          </el-col>
        </el-card>
      </el-col>

      <el-col :span="8" >
        <el-card class="card_r">
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
<style scoped>
  .cl{
    margin: 50px auto;

  }
  .cr{
    margin: 50px auto;

  }
  .card_r{
    height: 700px;
  }
  .div_body{
    margin-left:10% ;
    margin-right:10% ;
  }
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
  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 150px;
    margin: 0;
  }

  .el-carousel__item:nth-child(4) {
     background-image: url('../../assets/images/2.jpg');
  }
  .el-carousel__item:nth-child(3) {
     background-image: url('../../assets/images/1.png');
  }
  .el-carousel__item:nth-child(6) {
     background-image: url('../../assets/images/3.jpg');
  }
  .el-carousel__item:nth-child(5) {
     background-image: url('../../assets/images/4.jpg');
  }
  .verticalBar {
        width: 2px;
        height: 320px;
        background:grey;
        display: inline-block;
        margin-top: 31px;
        vertical-align: top;
        margin-left: 8px;

    }

</style>
