<template>
  <el-main>
    <el-row :gutter="16">
      <el-col span="18">
        <!-- 画布 -->
        <div id="canvas" ref="canvas" />
      </el-col>
      <el-col span="6">
        <el-card style="height: 500px">
          <el-alert style="margin: 8px auto; " title="请在左侧框中签名" type="info" :closable="false" />
          <el-divider />
          <el-form label-width="64px">
            <el-form-item label="姓名">
              <el-input v-model="name" />
            </el-form-item>
            <el-form-item label="电话">
              <el-input v-model="phone" />
            </el-form-item>
            <el-form-item label="单位">
              <el-input v-model="unit" />
            </el-form-item>
            <el-form-item label="操作">
              <el-button-group>
                <el-button type="primary" @click="handleClear()">重写签名</el-button>
                <el-button type="warning" @click="handleCommit()">提交挂单</el-button>
              </el-button-group>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </el-main>
</template>

<script>
export default {
  name: 'Credit',
  props: {
    callback: {
      type: Function,
      default: () => console.log('default')
    }
  },
  data: () => ({
    name: '',
    unit: '',
    phone: '',
    signature: String,
    context: null,
    canvas: null
  }),
  mounted() {
    this.lineCanvas()
  },
  methods: {
    lineCanvas() {
      const lineWidth = 1
      const color = '#000000'
      const background = '#fff'
      const that = this

      // 添加 canvas 并挂载
      this.canvas = document.createElement('canvas')
      this.$refs.canvas.appendChild(this.canvas)

      this.context = this.canvas.getContext('2d')

      this.canvas.width = this.$refs.canvas.clientWidth
      this.canvas.height = this.$refs.canvas.clientHeight

      this.context.fillStyle = background
      this.context.fillRect(0, 0, this.canvas.width, this.canvas.width)
      this.context.strokeStyle = color
      this.context.lineWidth = lineWidth
      this.context.lineCap = 'round'
      // 开始绘制
      this.canvas.addEventListener('touchstart', (e) => {
        this.context.beginPath()
        const rect = that.canvas.getBoundingClientRect()
        const x = e.changedTouches[0].clientX - rect.left * (that.canvas.width / rect.width)
        const y = e.changedTouches[0].clientY - rect.top * (that.canvas.height / rect.height)
        this.context.moveTo(x, y)
      }, false)

      // 绘制中
      this.canvas.addEventListener('touchmove', (e) => {
        const rect = that.canvas.getBoundingClientRect()
        const x = e.changedTouches[0].clientX - rect.left * (that.canvas.width / rect.width)
        const y = e.changedTouches[0].clientY - rect.top * (that.canvas.height / rect.height)
        this.context.lineTo(x, y)
        this.context.stroke()
      }, false)

      // 结束绘制
      this.canvas.addEventListener('touchend', () => {
        this.context.closePath()
      }, false)
    },
    handleClear() {
      this.context.clearRect(0, 0, this.canvas.width, this.canvas.height)
    },
    handleCommit() {
      this.signature = this.canvas.toDataURL()
      this.callback({
        name: this.name,
        unit: this.unit,
        phone: this.phone,
        signature: this.signature
      })
      this.handleClear()
      this.name = ''
      this.unit = ''
      this.phone = ''
      this.signature = ''
    }
  }
}
</script>

<style scoped>
#canvas {
  display: block;
  width: 95%;
  height: 500px;
  border: 1px solid #c5cad7 !important;
  border-radius: 3px;
}
</style>
