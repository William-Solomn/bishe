<template>
  <el-main>
    <el-tabs class="main" type="border-card">
      <!-- tabs -->
      <el-tab-pane v-for="(tabItem,i) in tabList" :key="i" :label="tabItem.title">
        <el-row :gutter="16">
          <!-- tables -->
          <el-table :data="tabItem.list" style="width: 100%">
            <el-table-column label="开台时间" width="170">
              <template slot-scope="scope">
                <i class="el-icon-time" />
                <span>{{ scope.row.order['orderTime'] }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="order.orderNo" width="170" label="订单号" />
            <!--            <el-table-column prop="order.username" label="用户" />-->
            <el-table-column label="所选菜品">
              <template slot-scope="scope">
                {{ handleMeals(scope.row.meals) }}
              </template>
            </el-table-column>
            <el-table-column label="订单金额">
              <template slot-scope="scope">
                <span>{{ handlePrice(scope.row.order.price) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="实收金额">
              <template slot-scope="scope">
                <span>{{ handlePrice(scope.row.order['actualPrice']) }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="order.state" label="状态">
              <template slot-scope="scope">
                {{ handleStatus(scope.row.order.state) }}
              </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作">
              <template slot-scope="scope">
                <el-button type="text" @click="handleDisplayDetail(scope.row.order.id)">查看详情</el-button>
                <el-dialog
                  title="订单详情"
                  :visible.sync="displayDetail[scope.row.order.id]"
                  :modal="false"
                >
                  <el-descriptions title="订单信息" :column="3">
                    <el-descriptions-item label="订单编号">{{ scope.row.order['orderNo'] }}</el-descriptions-item>
                    <el-descriptions-item label="桌号/房间号">{{ scope.row.order['tableName'] }}</el-descriptions-item>
                    <el-descriptions-item label="餐桌费">
                      {{ handlePrice(scope.row.order['tableFee']) }}
                    </el-descriptions-item>
                    <el-descriptions-item label="订单金额">{{ handlePrice(scope.row.order.price) }}</el-descriptions-item>
                    <el-descriptions-item label="优惠金额">
                      {{ handlePrice(scope.row.order['discountPrice']) }}
                    </el-descriptions-item>
                    <el-descriptions-item label="实际金额">
                      {{ handlePrice(scope.row.order['actualPrice']) }}
                    </el-descriptions-item>
                    <el-descriptions-item label="支付方式">{{ scope.row.order['payment'] }}</el-descriptions-item>
                  </el-descriptions>
                  <el-divider />
                  <h3>套餐与菜品</h3>
                  <el-row :gutter="16">
                    <el-col :span="12">
                      <el-table max-height="200" :data="scope.row.meals">
                        <el-table-column prop="name" label="菜品名" />
                        <el-table-column prop="count" label="数量" />
                        <el-table-column label="价格">
                          <template slot-scope="scopeInner">
                            {{ handlePrice(scopeInner.row.price) }}
                          </template>
                        </el-table-column>
                      </el-table>
                    </el-col>
                    <el-col :span="12">
                      <el-table max-height="200" :data="scope.row.combos">
                        <el-table-column prop="name" label="套餐名" />
                        <el-table-column prop="count" label="数量" />
                        <el-table-column label="价格">
                          <template slot-scope="scopeInner">
                            {{ handlePrice(scopeInner.row.price) }}
                          </template>
                        </el-table-column>
                      </el-table>
                    </el-col>
                  </el-row>
                </el-dialog>
              </template>
            </el-table-column>
          </el-table>
        </el-row>
      </el-tab-pane>
    </el-tabs>
  </el-main>
</template>

<script>
import { handleCurrent } from '@/utils/displayUtil'
import { getRestaurantInfo } from '@/utils/auth'
import { GetBillingOrderList } from '@/api/order'
import Vue from 'vue'

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
    ],
    displayDetail: {}
  }),
  mounted() {
    this.info = getRestaurantInfo()
    this.handleList()
  },
  methods: {
    handleStatus(status) {
      return ((['未完成', '已完成', '等待接收'])[status])
    },
    handlePrice: (num) => handleCurrent(num),
    handleMeals(mealList) {
      if (!mealList || mealList.length === 0) {
        return '无'
      } else if (mealList.length <= 2) {
        return mealList.map(x => x.name).join(',')
      } else {
        return mealList.slice(0, 2).map(x => x.name).join(',') + '...'
      }
    },
    handleList() {
      GetBillingOrderList(this.info.restaurantId)
        .then(resp => {
          if (resp.data['flag']) {
            const oList = resp.data['data']['orders']
            this.tabList = [{
              title: '全部订单',
              list: oList
            }, {
              title: '未完成',
              list: oList.filter(x => x.order.state === 0)
            }, {
              title: '已完成',
              list: oList.filter(x => x.order.state === 1)
            }]
          } else {
            this.$notify.error({
              title: '错误',
              message: resp.data['message']
            })
          }
        })
    },
    handleDisplayDetail(index) {
      Vue.set(this.displayDetail, index, true)
    }
  }
}
</script>
