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
  img: {
    order: {
      type: String,
      default: '123'
    }
  },
  data () {
    return {
      htmlData: '',
      printDeviceName: 'Canon SELPHY CP1300 WS',
      // eslint-disable-next-line no-undef
      fullPath: path.join(`${__static}`, 'print.html')
    }
  },
  mounted () {
    const webview = this.$refs.printWebview
    webview.addEventListener('dom-ready', () => {
      console.log('dom-ready')
      // dom-ready---webview加载完成
      webview.openDevTools() // 这个方法可以打开print.html的控制台
      // 发送信息到<webview>里的页面
      webview.send('webview-print-render', {
        printName: this.printDeviceName, // 打印机名称
        title: '123' // base64图片
      })
    })

    webview.addEventListener('ipc-message', async (event) => {
      console.log(event)
      if (event.channel === 'webview-print-do') {
        console.log('webview-print-do')
        console.log(this.printDeviceName)
        await webview.print({
          silent: true,
          printBackground: true,
          deviceName: 'Canon SELPHY CP1300 WS'
        }
        )
      }
    })
  },
  create () {
  },
  methods: {
  }
}
</script>

<style>
</style>
