<template>
  <div class="container">
    <webview
      id="printWebview"
      ref="printWebview"
      :src="fullPath"
      nodeintegration
    ></webview>
  </div>
</template>

<script>
// 引入ipcRenderer对象
import { ipcRenderer } from 'electron'
import path from 'path'

window.ipcRenderer = ipcRenderer

export default {
  data () {
    return {
      htmlData: '',
      printDeviceName: '',
      // eslint-disable-next-line no-undef
      fullPath: path.join(`${__static}`, 'print.html')
    }
  },
  mounted () {
  },
  methods: {
    print (val) {
      this.htmlData = val
      this.getPrintListHandle()
    },
    // 获取打印机列表
    getPrintListHandle () {
      // 改用ipc异步方式获取列表，解决打印列数量多的时候导致卡死的问题
      ipcRenderer.send('getPrinterList')
      ipcRenderer.once('getPrinterList', (event, data) => {
        // 过滤可用打印机
        this.printList = data.filter(element => element.status === 0)
        // 1.判断是否有打印服务
        if (this.printList.length <= 0) {
          this.$message({
            message: '打印服务异常,请尝试重启电脑',
            type: 'error'
          })
          this.$emit('cancel')
        } else {
          this.checkPrinter()
        }
      })
    },

    // 2.判断打印机状态
    checkPrinter () {
      // 本地获取打印机
      const printerName = 'Canon SELPHY CP1300 WS'
      const printer = this.printList.find(device => device.name === printerName)
      // 有打印机设备并且状态正常直接打印
      if (printer && printer.status === 0) {
        this.printDeviceName = printerName
        this.printRender()
      } else {
        this.$message.error('打印服务异常，没有搜索到可用打印机')
      }
    },
    printRender () {
      // this.checkPrinter()
      const webview = this.$refs.printWebview
      webview.send('webview-print-render', {
        printName: this.printDeviceName, // 打印机名称
        imgSource: this.htmlData,
        imgWidth: 268,
        imgHeight: 420
      })

      webview.addEventListener('ipc-message', async (event) => {
        if (event.channel === 'webview-print-do') {
          webview.print({
            silent: true,
            printBackground: true,
            deviceName: this.printDeviceName
          }
          )
        }
      })
    }
  }
}
</script>

<style>
</style>
