<template>
  <el-main>
    <el-tabs class="main" type="border-card">
      <!-- tabs -->
      <el-tab-pane v-for="(areaItem,i) in areaList" :key="i" :label="areaItem.name">
        <!-- form line -->
        <el-row :gutter="16">
          <el-dialog :modal="false" :visible.sync="showAdd" title="添加餐桌">
            <OperateTable
              :area-id="areaItem['id']"
              :op-name="'添加'"
              :on-submit="handleTableAdd"
              :on-cancel="handleTableAddCancel"
            />
          </el-dialog>
          <el-form inline>
            <div style="float:right">
              <el-form-item>
                <el-button type="primary" @click="showAdd = true"><i class="el-icon-dish" />添加餐桌</el-button>
              </el-form-item>
              <el-form-item>
                <OperateArea :on-change="handleAreaList" :area-id="areaItem['id']" />
              </el-form-item>
            </div>
          </el-form>
        </el-row>
        <el-row :gutter="16">
          <!-- tables -->
          <el-col v-for="(tableItem,j) in tableList[areaItem.name]" :key="j" :span="6">
            <div class="grid-content">
              <!-- table card -->
              <el-card class="box-card">
                <!-- table card header -->
                <div slot="header" class="clearfix">
                  <span>
                    {{ tableItem['name'] }}
                  </span>
                  <!-- 餐桌操作 -->
                  <div style="float: right">

                    <el-button
                      class="title-button"
                      type="primary"
                      icon="el-icon-edit"
                      @click="handleTableEditShow(tableItem)"
                    />

                    <el-popconfirm title="确定要删除餐桌吗？" @confirm="handleTableDelete(tableItem['id'])">
                      <el-button slot="reference" class="title-button" type="danger" icon="el-icon-delete" />
                    </el-popconfirm>

                    <el-button
                      class="title-button"
                      type="success"
                      icon="el-icon-download"
                      @click="handleDownload(tableItem.pic, tableItem.name)"
                    />
                  </div>
                </div>

                <el-row :gutter="16">
                  <el-col :span="10">
                    <el-image style="width: 100%" fit="cover" :src="tableItem.pic" @click="showMessage" />
                  </el-col>
                  <el-col :span="14">
                    <el-descriptions :column="1">
                      <el-descriptions-item label="容量">{{ tableItem['capacity'] }}人桌</el-descriptions-item>
                      <el-descriptions-item label="区域"> {{ areaItem['name'] }}</el-descriptions-item>
                      <el-descriptions-item label="桌费">￥{{ tableItem['price'] }}</el-descriptions-item>
                    </el-descriptions>
                  </el-col>
                </el-row>

              </el-card>
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>
    </el-tabs>
    <el-dialog :modal="false" :visible.sync="showEdit" title="修改餐桌">
      <OperateTable
        :table-data="editedTable"
        :op-name="'保存'"
        :on-submit="handleTableEdit"
        :on-cancel="handleTableEditCancel"
      />
    </el-dialog>
  </el-main>
</template>

<script>
import { addTable, deleteTable, getAreaList, getTableList, updateTable } from '@/api/tables'
import { getRestaurantInfo } from '@/utils/auth'
import OperateTable from '@/views/tables/operateTable'
import OperateArea from '@/views/tables/operateArea'

export default {
  components: { OperateArea, OperateTable },
  data: () => ({
    info: {
      restaurantId: Number
    },
    editedTable: Object,
    areaList: [],
    tableList: [],
    showEdit: false,
    showAdd: false
  }),
  mounted() {
    this.info = getRestaurantInfo()
    this.handleAreaList()
    this.handleTableList()
  },
  methods: {
    showMessage() {
      this.$message.error('右击图片后点击 “图片另存为” 即可下载图片')
    },
    handleAreaList() {
      getAreaList(this.info.restaurantId)
        .then(resp => {
          if (!resp.data['flag']) {
            this.$notify.error({
              title: '错误',
              message: resp.data['message']
            })
          } else {
            this.areaList = resp.data.data
          }
        })
    },
    handleTableList() {
      getTableList(this.info.restaurantId)
        .then(resp => {
          if (!resp.data['flag']) {
            this.$notify.error({
              title: '错误',
              message: resp.data['message']
            })
          } else {
            this.tableList = resp.data.data
            for (const area in this.tableList) {
              this.tableList[area].forEach(table => {
                table.pic = `https://bfjdkm.com:9002/open/wx/getwxacodeunlimit?scene=1%3B${table.id}&page=pages%2Fdiancan%2Fdiancan`
                return table
              })
            }
          }
        })
    },
    arrayBufferToBase64(buffer) {
      var binary = ''
      var bytes = new Uint8Array(buffer)
      var len = bytes.byteLength
      for (var i = 0; i < len; i++) {
        binary += String.fromCharCode(bytes[i])
      }
      return window.btoa(binary)
    },
    handleTableDelete(tableId) {
      deleteTable(this.info.restaurantId, tableId)
        .then(resp => {
          const flag = resp.data['flag']
          this.$notify({
            name: `操作${flag ? '成功' : '失败'}`,
            type: flag ? 'success' : 'error',
            message: resp.data.message
          })

          if (flag) {
            this.handleTableList()
          }
        })
    },
    handleTableAdd(tableItem) {
      const reqBody = {
        name: tableItem['name'],
        size: tableItem['capacity'],
        price: tableItem['price'],
        remark: tableItem['remark'],
        tableTypeId: tableItem['tableTypeId']
      }
      addTable(this.info.restaurantId, reqBody)
        .then(resp => {
          const flag = resp.data['flag']
          this.$notify({
            name: `操作${flag ? '成功' : '失败'}`,
            type: flag ? 'success' : 'error',
            message: resp.data.message
          })

          if (flag) {
            this.handleTableList()
          }
        })

      this.showAdd = false
    },
    handleTableAddCancel() {
      this.showAdd = false
    },
    handleTableEdit(tableItem) {
      const reqBody = {
        id: tableItem['id'],
        name: tableItem['name'],
        size: tableItem['capacity'],
        price: tableItem['price'],
        remark: tableItem['remark'],
        tableTypeId: tableItem['tableTypeId']
      }
      updateTable(this.info.restaurantId, reqBody)
        .then(resp => {
          const flag = resp.data['flag']
          this.$notify({
            name: `操作${flag ? '成功' : '失败'}`,
            type: flag ? 'success' : 'error',
            message: resp.data.message
          })

          if (flag) {
            this.handleTableList()
          }
        })

      this.showEdit = false
    },
    handleTableEditShow(tableItem) {
      this.editedTable = tableItem
      this.showEdit = true
    },
    handleTableEditCancel() {
      this.showEdit = false
    },
    handleDownload(url, tableName) {
      const element = document.createElement('a')
      element.href = url
      element.download = `${tableName}.jfif`
      element.click()
    }
  }
}
</script>

<style>
.box-card {
  margin-bottom: 16px;
}

.title-button {
  padding: 3px;
  margin: 0 3px;
}
</style>
