<template>
  <div class="container">
    <webview id="printWebview" ref="printWebview" :src="fullPath" nodeintegration></webview>
  </div>
</template>

<script>
// 引入ipcRenderer对象
import { ipcRenderer } from 'electron'
import path from 'path'
import logger from '../../utils/LogUtils'

window.ipcRenderer = ipcRenderer

export default {
  data () {
    return {
      htmlData: '',
      printList: [],
      printDeviceName: process.env.VUE_APP_PRINTER_NAME,
      // eslint-disable-next-line no-undef
      fullPath: path.join(`${__static}`, 'print.html')
    }
  },
  mounted () {
    const webview = this.$refs.printWebview
    webview.addEventListener('ipc-message', (event) => {
      try {
        if (event.channel === 'webview-print-do') {
          webview.print({
            silent: true,
            printBackground: true,
            deviceName: this.printDeviceName
          })
        }
      } catch (e) {
        logger.error(e)
      }
    })
  },
  methods: {
    print (val, printName) {
      logger.info('--------print start----------')
      this.htmlData = val
      this.printDeviceName = printName
      logger.info('print name =' + printName)
      this.getPrintListHandle()
    },
    // 1.获取打印机列表
    getPrintListHandle () {
      try {
        // 改用ipc异步方式获取列表，解决打印列数量多的时候导致卡死的问题
        ipcRenderer.send('getPrinterList')
        ipcRenderer.once('getPrinterList', (event, data) => {
          // 过滤可用打印机
          this.printList = data.filter(element => element.status === 0)
          // 1.判断是否有打印服务
          if (this.printList.length <= 0) {
            logger.info('printlist size == 0 ')
            logger.info('--------print end----------')
            this.$message({
              message: '打印服务异常,请尝试重启电脑',
              type: 'error'
            })
          } else {
            const printArr = []
            this.printList.forEach(e => {
              const obj = (({ name, status }) => ({ name, status }))(e)
              printArr.push(obj)
            })
            logger.info('printlist =' + JSON.stringify(printArr))
            this.checkPrinter()
          }
        })
      } catch (e) {
        logger.error(e)
      }
    },
    // 2.判断打印机状态
    checkPrinter () {
      const printerName = this.printDeviceName
      const printer = this.printList.find(device => device.name === printerName)
      // 有打印机设备并且状态正常直接打印
      if (printer && printer.status === 0) {
        logger.info('printer =' + JSON.stringify(printer))
        this.printRender()
      } else {
        logger.info('printer is null')
        logger.info('--------print end----------')
        this.$message.error('打印服务异常，没有搜索到可用打印机')
      }
    },
    printRender () {
      try {
        const webview = this.$refs.printWebview
        webview.send('webview-print-render', {
          printName: this.printDeviceName,
          imgSource: this.htmlData,
          imgWidth: 268,
          imgHeight: 420
        })
      } catch (e) {
        logger.error(e)
      }
      logger.info('--------print end----------')
    }
  }
}
</script>

<style>
</style>
