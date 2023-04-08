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
                <span>{{ handlePropDisplay(scope.row.order, 'orderTime') }}</span>
              </template>
            </el-table-column>
            <el-table-column width="170" label="订单号">
              <template slot-scope="scope">
                {{ scope.row.order ? scope.row.order.orderNo : '数据错误' }}
              </template>
            </el-table-column>
            <el-table-column prop="credit.unit" label="挂账单位" />
            <el-table-column prop="credit.name" label="挂账人" />
            <el-table-column label="所选菜品">
              <template slot-scope="scope">
                {{ handleFoodDisplay(scope.row.meals) }}
              </template>
            </el-table-column>
            <el-table-column label="订单金额">
              <template slot-scope="scope">
                <span>{{ scope.row.order ? handlePrice(scope.row.order['price']) : '数据错误' }}</span>
              </template>
            </el-table-column>
            <el-table-column label="实收金额">
              <template slot-scope="scope">
                <span>{{ scope.row.order ? handlePrice(scope.row.order['actualPrice']) : '数据错误' }}</span>
              </template>
            </el-table-column>
            <el-table-column label="状态">
              <template slot-scope="scope">
                {{ handleStatus(scope.row.credit.state) }}
              </template>
            </el-table-column>
            <el-table-column width="160" fixed="right" label="操作">
              <template slot-scope="scope">
                <el-button-group>
                  <el-button size="mini" @click="handleDisplayDetail(scope.row.order)">
                    查看详情
                  </el-button>
                  <el-button
                    type="success"
                    :disabled="scope.row.credit.state === 1"
                    size="mini"
                    @click="handlePay(scope.row)"
                  >结账
                  </el-button>
                </el-button-group>

                <div v-if="scope.row.order">
                  <el-dialog
                    title="订单详情"
                    :visible.sync="displayDetail[scope.row.order.id]"
                    :modal="false"
                  >
                    <el-row type="flex" justify="space-between">
                      <el-col span="10">
                        <el-descriptions :column="1" title="用户信息">
                          <el-descriptions-item label="姓名">{{ scope.row.credit.name }}</el-descriptions-item>
                          <el-descriptions-item label="单位">{{ scope.row.credit.unit }}</el-descriptions-item>
                          <el-descriptions-item label="联系方式">{{ scope.row.order['userPhone'] }}</el-descriptions-item>
                        </el-descriptions>
                      </el-col>
                      <el-col span="8">
                        <el-image style="height: 140px;" fit="contain" :src="scope.row.credit.signature" />
                      </el-col>
                    </el-row>

                    <el-divider />
                    <el-descriptions title="订单信息" :column="3">
                      <el-descriptions-item label="订单编号">{{ scope.row.order.orderNo }}</el-descriptions-item>
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
                </div>

              </template>
            </el-table-column>
          </el-table>
        </el-row>
        <el-divider />
        <el-pagination
          background
          layout="prev, pager, next"
          :total="10"
        />
      </el-tab-pane>
    </el-tabs>
  </el-main>
</template>

<script>
import { getRestaurantInfo } from '@/utils/auth'
import { FinishCreditOrder, GetCreditOrderList } from '@/api/order'
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
      return ((['未支付', '已支付', '等待接收'])[status])
    },
    handleList() {
      GetCreditOrderList(this.info.restaurantId)
        .then(resp => {
          if (resp.data['flag']) {
            const oList = resp.data['data']['orders']
            this.tabList = [{
              title: '全部订单',
              list: oList
            }, {
              title: '未支付',
              list: oList.filter(x => x.credit.state === 0)
            }, {
              title: '已支付',
              list: oList.filter(x => x.credit.state === 1)
            }]
          } else {
            this.$notify.error({
              title: '错误',
              message: resp.data['message']
            })
          }
        })
    },
    handlePay(row) {
      FinishCreditOrder(this.info.restaurantId, row.order.orderNo, row.credit.id)
        .then(resp => {
          const flag = resp.data['flag']
          this.$notify({
            name: `操作${flag ? '成功' : '失败'}`,
            type: flag ? 'success' : 'error',
            message: resp.data['message']
          })

          if (flag) {
            this.handleList()
          }
        })
    },
    handlePrice: (num) => num.toLocaleString('zh-CN', {
      style: 'currency',
      currency: 'CNY',
      maximumFractionDigits: 2
    }),
    handleDisplayDetail(order) {
      if (!order) {
        this.$notify({
          name: '无法查看详情',
          type: 'error',
          message: '订单数据获取为空'
        })
      } else {
        Vue.set(this.displayDetail, order.id, true)
      }
    },
    handlePropDisplay(object, key) {
      if (!object) {
        return 0
      }

      return object[key]
    },
    handleFoodDisplay(mealList) {
      if (!mealList || mealList.length === 0) {
        return '数据错误'
      }

      const list = mealList.map(m => m.name)
      let resultStr = ''
      if (list.length === 0) {
        resultStr = '无'
      } else if (list.length <= 2) {
        resultStr = list.join(',')
      } else {
        resultStr = list.slice(0, 3).join(',') + '...'
      }
      return resultStr
    }
  }
}
</script>
