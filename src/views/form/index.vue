<template>
  <el-main>
    <el-tabs type="border-card">
      <el-tab-pane v-for="(tabItem,i) in tabList" :key="i" :label="tabItem.title">
        <el-row :gutter="16">
          <el-col v-for="(tableItem,j) in tabItem.table_list" :key="j" :span="6">
            <div class="grid-content bg-purple">
              <el-card class="box-card">
                <div slot="header" class="clearfix">
                  <span :class="tableItem.table_status !== '空闲' ? 'blue' : ''">{{ tableItem.table_name }} ({{tableItem.table_size}}人桌)</span>
                  <el-button style="float: right; padding: 3px 0" type="text">查看详情</el-button>
                </div>
                <div class="text item">
                  <p>状态：{{ tableItem.table_status }}</p>
                  <p>价格：{{ tableItem.table_price }}</p>
                  <p>时间：{{ tableItem.created_time }}</p>
                  <p>更新：{{ tableItem.update_time }}</p>
                </div>
              </el-card>
            </div>
          </el-col>
        </el-row>
        <el-divider/>
        <el-pagination
          background
          layout="prev, pager, next"
          :total="10">
        </el-pagination>
      </el-tab-pane>
    </el-tabs>
  </el-main>
</template>

<script>
export default {
  data() {
    return {
      tabList: [
        {
          title: '大厅',
          table_list: [
            {
              table_id: 0,
              table_price: '0',
              table_name: '餐桌1',
              table_rid: '0',
              table_size: '4',
              update_time: '2021-02-02 11:22:22',
              table_status: '空闲',
              created_time: '2021-02-02 11:22:22'
            }, {
              table_id: 0,
              table_price: '0',
              table_name: '餐桌1',
              table_rid: '0',
              table_size: '4',
              update_time: '2021-02-02 11:22:22',
              table_status: '使用',
              created_time: '2021-02-02 11:22:22'
            }, {
              table_id: 0,
              table_price: '0',
              table_name: '餐桌1',
              table_rid: '0',
              table_size: '4',
              update_time: '2021-02-02 11:22:22',
              table_status: '等待',
              created_time: '2021-02-02 11:22:22'
            }, {
              table_id: 0,
              table_price: '0',
              table_name: '餐桌1',
              table_rid: '0',
              table_size: '4',
              update_time: '2021-02-02 11:22:22',
              table_status: '空闲',
              created_time: '2021-02-02 11:22:22'
            }, {
              table_id: 0,
              table_price: '0',
              table_name: '餐桌1',
              table_rid: '0',
              table_size: '4',
              update_time: '2021-02-02 11:22:22',
              table_status: '等待',
              created_time: '2021-02-02 11:22:22'
            }, {
              table_id: 0,
              table_price: '0',
              table_name: '餐桌1',
              table_rid: '0',
              table_size: '4',
              update_time: '2021-02-02 11:22:22',
              table_status: '空闲',
              created_time: '2021-02-02 11:22:22'
            }, {
              table_id: 0,
              table_price: '0',
              table_name: '餐桌1',
              table_rid: '0',
              table_size: '4',
              update_time: '2021-02-02 11:22:22',
              table_status: '空闲',
              created_time: '2021-02-02 11:22:22'
            }, {
              table_id: 0,
              table_price: '0',
              table_name: '餐桌1',
              table_rid: '0',
              table_size: '4',
              update_time: '2021-02-02 11:22:22',
              table_status: '等待',
              created_time: '2021-02-02 11:22:22'
            }
          ]
        }
      ]
    }
  },
  methods: {
    onSubmit() {
      this.$message('submit!')
    },
    onCancel() {
      this.$message({
        message: 'cancel!',
        type: 'warning'
      })
    }
  }
}
</script>

<style>
.box-card{
  margin-bottom: 16px;
}

span.blue {
  color: #409EFF;
}
</style>
