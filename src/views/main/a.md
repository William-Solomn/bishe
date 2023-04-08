```vue
<template>
  <el-main>
    <el-row :gutter="16">
      <el-col :span="16">
        <el-tabs class="main" type="border-card">
          <!-- tabs -->
          <el-tab-pane v-for="(area,i) in areaList" :key="i" :label="area.name">
            <el-row :gutter="16">
              <!-- tables -->
              <el-col v-for="(tableItem,j) in tabList[area.name]" :key="j" :span="6">
                <div class="grid-content">
                  <!-- table card -->
                  <el-card :style="`color: ${handleStatusColor(tableItem.status)}`" class="box-card">
                    <!-- table card header -->
                    <div slot="header" class="clearfix">
                      <span>{{ tableItem.name }}</span>
                      <el-button style="float: right; padding: 0" type="text" @click="handleDetailShow(tableItem)">详情
                      </el-button>

                    </div>
                    <div style="text-align: center">
                      <span>消费：{{
                        handlePrice(tableItem.order && tableItem.order['price'] ? tableItem.order['price'] : 0)
                      }}</span>
                      <h3> {{ handleStatus(tableItem.status) }}</h3>
                      <!-- <p>{{ handleDateTime(tableItem) }}</p> -->
                    </div>
                  </el-card>
                </div>
              </el-col>
            </el-row>
          </el-tab-pane>
        </el-tabs>
      </el-col>
      <el-col :span="8">
        <Detail class="main" :on-save="handleTableEditSave" :alter-table-list="altTableList" :table="detailTableItem" />
      </el-col>
    </el-row>
  </el-main>
</template>

<script>


import Detail from '@/views/main/detail'
import { handleCurrent } from '@/utils/displayUtil'
import { GetOrderDetail } from '@/api/order'
import { getAreaList, getTableList } from '@/api/tables'
import { getRestaurantInfo } from '@/utils/auth'
import { getAllParkingInfo, postParkingInfo } from '@/api/parkinginfo'

export default {
  components: { Detail },
  data: () => ({
    info: {
      restaurantId: -1
    },
    id: 1,
    areaList: [],
    tabList: {
      default: []
    },
    detailTableItem: {},
    altTableList: [],
    carList:[]
  }),
  mounted() {
    this.info = getRestaurantInfo()
    this.handleCarList()
  },
  methods: {
    handleDateTime(tableItem) {
      return `${new Date(tableItem.order['orderTime']).getHours()}:${new Date(tableItem.order['orderTime']).getMinutes()}`
    },
    handleCarList() {
      getAllParkingInfo().then(resp => {
          this.carList = resp.data
      })
    },
    handleTableEditSave() {
      this.handleTableList()
    },
    handlePrice(num) {
      return handleCurrent(num)
    },
    handleStatus(statusNumber) {
      switch (statusNumber) {
        case 0:
          return '空闲'
        case 1:
          return '占用'
        case 2:
          return '等待'
      }
    },
    handleStatusColor(statusNumber) {
      switch (statusNumber) {
        case 0:
          return '#0000FF'
        case 1:
          return '#FF0000'
      }
    },
    handleDetailShow(tableItem) {
      if (tableItem.order) {
        GetOrderDetail(this.info.restaurantId, tableItem.order.orderNo)
          .then(resp => {
            const flag = resp.data['flag']
            if (flag) {
              const result = resp.data.data

              // update order
              tableItem.order = result.order

              // update status
              result.meals.forEach(item => {
                item.finished = (item['count'] === item['finish'])
              })
              result.combos.forEach(item => {
                item.finished = (item['count'] === item['finish'])
              })

              tableItem.order.meals = result.meals
              tableItem.order.combos = result.combos
            } else {
              tableItem.order = null
            }
          })
      }

      this.detailTableItem = tableItem
    }
  }
}

</script>

<style>
.box-card {
  margin-bottom: 16px;
}
</style>

```