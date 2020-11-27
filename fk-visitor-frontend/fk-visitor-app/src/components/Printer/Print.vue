<template>
  <div class="container">
    <webview id="printWebview" ref="printWebview" :src="fullPath" nodeintegration></webview>
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
    this.printRender()
  },
  create () {
    // eslint-disable-next-line no-undef
    const str = path.join(`${__static}`, 'print.html')
    console.log(str)
  },
  methods: {
    print (val) {
      this.htmlData = val
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
      } else if (printerName === '') {
        this.$message({
          message: '请先设置其他打印机',
          type: 'error',
          duration: 1000,
          onClose: () => {
            this.dialogVisible = true
          }
        })
        this.$emit('cancel')
      } else {
        this.$message({
          message: '当前打印机不可用,请重新设置',
          type: 'error',
          duration: 1000,
          onClose: () => {
            this.dialogVisible = true
          }
        })
      }
    },

    printRender () {
      console.log('printRender')
      const webview = this.$refs.printWebview
      webview.addEventListener('dom-ready', () => {
        console.log('dom-ready')
        // dom-ready---webview加载完成
        webview.openDevTools() // 这个方法可以打开print.html的控制台
        // 发送信息到<webview>里的页面
        webview.send('webview-print-render', {
          printName: this.printDeviceName // 打印机名称
          // imgSource: imgsrc // base64图片
        })
      })

      // 监听<webview>里面的消息，也就是监听print.html里面的ipcRenderer.sendToHost发送的事件，当该事件发送成功后就会进入下面的回调事件中执行打印操作。
      webview.addEventListener('ipc-message', (event) => {
        console.log('ipc-message')
        if (event.channel === 'webview-print-do') {
          // 如果收到<webview>传过来的事件，名为"webview-print-do"，就执行 webview.print打印方法，打印<webview>里面的内容。
          webview.print(
            {
              // 是否是静默打印
              silent: true,
              printBackground: true,
              // 打印机的名称，就是本文一开始获得的打印机列表其中一个
              deviceName: this.printDeviceName
            },
            (data) => {
              // 这个回调是打印后的回调事件，data为true就是打印成功，为false就打印失败
              console.log('webview success', data)
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
