<template>
  <el-main>
    <el-tabs class="main" type="border-card">
      <!-- tabs -->
      <el-tab-pane v-for="(tabItem,i) in tabList" :key="i" :label="tabItem.title">
        <el-row :gutter="16">
          <!-- tables -->
          <el-table :data="tabItem.list" style="width: 100%">
            <el-table-column label="预定时间" width="170">
              <template slot-scope="scope">
                <i class="el-icon-time" />
                <span>{{ scope.row.date }} {{ scope.row.time }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="reserveId" width="170" label="预定号" />
            <el-table-column prop="count" label="就餐人数" />
            <el-table-column prop="unit" label="订餐单位" />
            <el-table-column prop="name" label="订餐人" />
            <el-table-column prop="phone" label="手机号" />
            <el-table-column prop="remark" width="200" label="备注" />
            <el-table-column label="状态">
              <template slot-scope="scope">
                {{ handleStatus(scope.row.date, scope.row.time) }}
              </template>
            </el-table-column>
          </el-table>
        </el-row>
      </el-tab-pane>
    </el-tabs>
  </el-main>
</template>

<script>
import { getRestaurantInfo } from '@/utils/auth'
import { GetScheduledOrderList } from '@/api/order'

export default {
  data: () => ({
    info: {
      restaurantId: -1
    },
    tabList: [
      {
        title: '全部订单',
        list: []
      }
    ]
  }),
  mounted() {
    this.info = getRestaurantInfo()
    this.handleList()
  },
  methods: {
    handleList() {
      GetScheduledOrderList(this.info.restaurantId)
        .then(resp => {
          if (resp.data['flag']) {
            const oList = resp.data['data']['list']
            oList.forEach(x => {
              x.statusText = this.handleStatus(x.date, x.time)
            })

            this.tabList = [{
              title: '全部订单',
              list: oList
            }, {
              title: '未完成',
              list: oList.filter(x => x.statusText === '未完成')
            }, {
              title: '已完成',
              list: oList.filter(x => x.statusText === '已完成')
            }]
          } else {
            this.$notify.error({
              title: '错误',
              message: resp.data['message']
            })
          }
        })
    },
    handlePrice: (num) => num.toLocaleString('zh-CN', {
      style: 'currency',
      currency: 'CNY',
      maximumFractionDigits: 2
    }),
    handleStatus(date, time) {
      const finishTime = new Date(`${date} ${time}`)
      const nowTime = new Date()

      return (nowTime - finishTime > 0 ? '已' : '未') + '完成'
    }
  }
}
</script>
