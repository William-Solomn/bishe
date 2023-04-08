```vue
<template>
  <el-card>
    <div v-if="!table.user_id">
      <div slot="header">点击详情显示车位信息</div>
    </div>
    <div v-else>
      <div slot="header" class="clearfix">{{ table.name }} 信息</div>
      <el-divider />

      <el-row>
        <el-col :span="12">
          <el-descriptions :column="1">
            <el-descriptions-item label="桌名">{{ table['name'] }}</el-descriptions-item>
            <el-descriptions-item label="容量">{{ table['capacity'] }}</el-descriptions-item>
            <el-descriptions-item label="桌费">{{ handlePrice(table['price']) }}</el-descriptions-item>
          </el-descriptions>
          <el-descriptions v-if="table.order" :column="1">
            <el-descriptions-item label="开台时间">{{ handleTime(table.order['orderTime']) }}</el-descriptions-item>
          </el-descriptions>
        </el-col>
        <el-col :span="12">
          <el-descriptions v-if="table.order" :column="1">
            <el-descriptions-item label="单号">{{ table.order['orderNo'] }}</el-descriptions-item>
            <el-descriptions-item label="折扣"> {{ handlePrice(table.order['discountPrice']) }}</el-descriptions-item>
            <el-descriptions-item label="共计"> {{ handlePrice(table.order.price) }}</el-descriptions-item>
            <el-descriptions-item label="实收"> {{ handlePrice(table.order['actualPrice']) }}</el-descriptions-item>
          </el-descriptions>
        </el-col>

      </el-row>
      <el-row>
        <div v-if="table.order">
          <el-table :border="true" max-height="200px" :data="table.order.meals">
            <el-table-column label="出菜" width="78">
              <template slot-scope="scope">
                <el-button
                  type="success"
                  size="mini"
                  :disabled="scope.row.finish === scope.row.count"
                  @click="handleFinish( scope.row, null)"
                >{{ scope.row.finish === scope.row.count ? '已出' : '出菜' }}
                </el-button>
              </template>
            </el-table-column>
            <el-table-column prop="name" label="菜品" />
            <el-table-column label="数量" width="60">
              <template slot-scope="scope">
                <span> {{ scope.row.finish }} / {{ scope.row.count }}</span>
              </template>
            </el-table-column>
            <el-table-column label="价格" width="64">
              <template slot-scope="scope">
                {{ handlePrice(scope.row['withdraw'] ? scope.row.price : 0) }}
              </template>
            </el-table-column>
          </el-table>
          <el-table :border="true" max-height="200px" :data="table.order.combos">
            <el-table-column label="出菜" width="78">
              <template slot-scope="scope">
                <el-button
                  type="success"
                  size="mini"
                  :disabled="scope.row.finish === scope.row.count"
                  @click="handleFinish(null, scope.row)"
                > {{ scope.row.finish === scope.row.count ? '已出' : '出菜' }}
                </el-button>
              </template>
            </el-table-column>
            <el-table-column prop="name" label="套餐" />
            <el-table-column label="数量" width="60">
              <template slot-scope="scope">
                <span> {{ scope.row.finish }} / {{ scope.row.count }}</span>
              </template>
            </el-table-column>
            <el-table-column label="价格" width="64">
              <template slot-scope="scope">
                {{ handlePrice(scope.row.price) }}
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div v-else>
          当前餐桌无订单
        </div>
      </el-row>

      <el-divider />

      <div slot="footer">
        <el-form inline>
          <el-form-item>
            <el-button type="primary" :disabled="!table.order" @click="handleEdit()">
              <el-icon class="el-icon-edit" />
              编辑
            </el-button>
            <el-button type="success" :disabled="!table.order" @click="handlePayment()">
              <el-icon class="el-icon-s-finance" />
              结算
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>

    <el-dialog :fullscreen="paymentType === '挂单'" :visible.sync="showPayment">
      <el-form label-width="80px">
        <el-form-item label="支付方式">
          <el-radio-group v-model="paymentType">
            <el-radio label="现金" />
            <el-radio label="支付宝" />
            <el-radio label="微信" />
            <el-radio label="挂单" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="抹零">
          <el-input-number v-model="payInfo.delta" :precision="2" :step="1" />
        </el-form-item>
        <div v-if="paymentType === '挂单'">
          <Credit :callback="handleCredit" />
        </div>
        <div v-else>
          <el-form-item>
            <el-button type="success" @click="handelPay(paymentType)">提交</el-button>
          </el-form-item>
        </div>
      </el-form>
    </el-dialog>

    <el-dialog :visible.sync="showEdit">
      <el-form label-width="80px">
        <el-form-item label="换桌">
          <el-select v-model="newTable">
            <el-option
              v-for="altTable in alterTableList"
              :key="altTable.id"
              :value="altTable.id"
              :label="altTable.name"
            />
          </el-select>
        </el-form-item>
      </el-form>

      <el-form label-width="70px" inline>
        <SearchBar :callback="handleAdd" />
      </el-form>

      <div v-if="table.order">
        <el-table :border="true" :data="table.order.meals" max-height="200px">
          <el-table-column prop="id" label="序号" width="50" />
          <el-table-column prop="name" label="菜品" />
          <el-table-column label="数量" width="60">
            <template slot-scope="scope">
              <span> {{ scope.row.finish }} / {{ scope.row.count }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="price" label="价格" width="64">
            <template slot-scope="scope">
              {{ handlePrice(scope.row['withdraw'] ? scope.row.price : 0) }}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="64">
            <template slot-scope="scope">
              <!--<el-button type="text" @click="handleEditFoods(scope.row)">修改</el-button>-->
              <el-popconfirm slot="reference" title="确定删除吗" @confirm="handleDelete(scope.row, null)">
                <el-button slot="reference" type="text">删除</el-button>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>

        <el-table :border="true" :data="table.order.combos" max-height="200px">
          <el-table-column prop="id" label="序号" width="50" />
          <el-table-column prop="name" label="套餐" />
          <el-table-column label="数量" width="60">
            <template slot-scope="scope">
              <span> {{ scope.row.finish }} / {{ scope.row.count }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="price" label="价格" width="64">
            <template slot-scope="scope">
              {{ handlePrice(scope.row.price) }}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="64">
            <template slot-scope="scope">
              <!--<el-button type="text" @click="handleEditFoods(scope.row)">修改</el-button>-->
              <el-popconfirm slot="reference" title="确定删除吗" @confirm="handleDelete(null, scope.row)">
                <el-button slot="reference" type="text">删除</el-button>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <el-form inline>
        <el-form-item>
          <el-button type="primary" @click="handleEditSave">保存修改</el-button>
          <el-button @click="showEdit = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </el-card>
</template>

<script>
import { handleCurrent } from '@/utils/displayUtil'
import SearchBar from '@/components/Search/searchBar'
import { getRestaurantInfo } from '@/utils/auth'
import {
  addFoodToOrder,
  deleteFoodOfOrder, GetOrderDetail,
  UpdateMealOrComboFinish,
  UpdateTable
} from '@/api/order'
import { requestPost } from '@/api/base'
import Credit from '@/views/main/credit'

export default {
  name: 'Detail',
  components: { Credit, SearchBar },
  props: {
    table: {
      type: Object,
      default: () => ({})
    },
    alterTableList: {
      type: Array,
      default: () => ([])
    },
    onSave: {
      type: Function,
      default: () => console.log('default')
    }
  }, data: () => ({
    info: {
      restaurantId: Number
    },
    newTable: '',
    payInfo: {},
    showPayment: false,
    showEdit: false,
    paymentType: String
  }),
  mounted() {
    this.info = getRestaurantInfo()
  },
  methods: {
    handleTime(timeStr) {
      const d = new Date(timeStr)
      return d.toLocaleTimeString('zh-CN')
    },
    handleFinish(mealItem, comboItem) {
      // 出菜
      UpdateMealOrComboFinish(this.info.restaurantId, this.table.order.id, this.table.order.orderNo, mealItem?.id, comboItem?.id)
        .then(resp => {
          const flag = resp.data['flag']

          if (!flag) {
            this.$notify.error({
              title: '出菜错误',
              message: resp.data['message']
            })
          } else {
            // 成功
            this.handleOrderUpdate()
          }
        })
    },
    handleOrderUpdate() {
      GetOrderDetail(this.info.restaurantId, this.table.order.orderNo)
        .then(oResp => {
          const oFlag = oResp.data['flag']
          if (oFlag) {
            this.table.order = oResp.data['data'].order
            this.table.order.meals = oResp.data['data'].meals
            this.table.order.combos = oResp.data['data'].combos
          }
        })
    },
    handleEdit() {
      // 占用中
      if (this.table.status !== 0) {
        this.showEdit = true
      } else {
        this.$notify.info({
          title: '消息',
          message: '当前餐桌无订单'
        })
      }
    },
    handleEditSave() {
      if (this.newTable !== '') {
        UpdateTable(this.info.restaurantId, this.table.order.id, this.newTable)
          .then(resp => {
            const flag = resp.data['flag']
            this.$notify({
              name: `操作${flag ? '成功' : '失败'}`,
              type: flag ? 'success' : 'error',
              message: resp.data['message']
            })

            if (flag) {
              this.table.id = 0
              this.handleOrderUpdate()
              this.onSave()
            }
          })
      }

      this.showEdit = false
    },
    handleCredit(info) {
      const creditInfo = {
        payment: '挂单',
        credit: true,
        delta: this.payInfo.delta
      }
      for (const key in info) {
        creditInfo[key] = info[key]
      }
      this.payInfo = creditInfo

      this.handelPay('挂单')
    },
    handlePayment() {
      // 占用中
      if (this.table.order) {
        this.showPayment = true
      } else {
        this.$notify.info({
          title: '消息',
          message: '当前餐桌无订单'
        })
      }
    },
    handelPay(payment) {
      let reqBody = {
        unit: null,
        signature: null
      }

      if (payment === '挂单') {
        reqBody = this.payInfo
        if (!reqBody.signature) {
          this.$notify({
            title: '签名未保存',
            message: '请先保存签名'
          })
          return
        }
      } else {
        reqBody['name'] = this.table.order['username'] ?? '匿名用户'
        reqBody['phone'] = this.table.order['userPhone'] ?? '匿名用户'
        reqBody['credit'] = false
      }

      reqBody['payment'] = payment
      reqBody['id'] = this.table.order['id']
      reqBody['orderNo'] = this.table.order['orderNo']
      reqBody['delta'] = this.payInfo.delta

      requestPost(this.info.restaurantId, '/order/finish', reqBody)
        .then(resp => {
          const flag = resp.data['flag']
          this.$notify({
            name: `操作${flag ? '成功' : '失败'}`,
            type: flag ? 'success' : 'error',
            message: resp.data['message']
          })

          if (flag) {
            this.table.order = null
            this.table.status = 0
            this.showPayment = false
          }
        })
    },
    handlePrice(num) {
      return handleCurrent(num)
    },
    handleAdd(item) {
      const reqBody = {
        id: item.id,
        count: 1,
        taste: item.tastes[0].id,
        specification: item.specifications[0].id
      }
      item['count'] = 1
      addFoodToOrder(this.info.restaurantId, this.table.order.id, this.table.order.orderNo, [reqBody], [])
        .then(resp => {
          if (!resp.data['flag']) {
            this.$notify.error({
              title: '错误',
              message: '添加失败，请求错误'
            })
          } else {
            this.handleOrderUpdate()
          }
        })
    },
    handleDelete(meal, combo) {
      const mealList = []
      const comboList = []
      if (meal) {
        mealList.push({
          id: meal.id,
          count: 1
        })
      }
      if (combo) {
        comboList.push({
          id: combo.id,
          count: 1
        })
      }

      deleteFoodOfOrder(this.info.restaurantId, this.table.order.id, this.table.order.orderNo, mealList, comboList)
        .then(resp => {
          if (!resp.data['flag']) {
            this.$notify.error({
              title: '错误',
              message: resp.data['message']
            })
          } else {
            this.handleOrderUpdate()
          }
        })
    }
  }
}
</script>

```