<template>
  <div>
    <webview ref="printWebview" src="./print.html" nodeintegration></webview>
    <a-button @click="printRender">打印</a-button>
  </div>
</template>

<script>
// 引入ipcRenderer对象
import { ipcRenderer } from 'electron'
window.ipcRenderer = ipcRenderer

export default {
  data () {
    return {
      printDeviceName: ''
    }
  },
  mounted () {
    this.getPrinterList() // 首先先获取
  },
  methods: {
    printRender () {
      console.log(111)
      console.log(this.printDeviceName)
      const webview = this.$refs.printWebview
      // console.log(webview)

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
    },
    getPrinterList () {
      ipcRenderer.send('getPrinterList')
      // 监听主线程获取到打印机列表后的回调
      ipcRenderer.once('getPrinterList', (event, data) => {
        console.log(data)
        // data就是打印机列表
        this.printDeviceName = data[0].name
      })
    }
  }
}
</script>

<style>
</style>
