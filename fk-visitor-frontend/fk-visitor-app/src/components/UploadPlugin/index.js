// index.js
import Vue from 'vue'
import uploadComponent from './upload-basic.vue'

// 使用Vue构造器去创建一个上传组件的子类
const UploadBasic = Vue.extend(uploadComponent)
// 创建一个div
const createDiv = () => document.createElement('div')

function useUpload (callback) {
  // 创建一个上传组件类的实例化对象，这个uploadVm你可以认为就是上面那个组件
  const uploadVm = new UploadBasic()
  // 挂载目标
  let uploadEl = createDiv()
  // 将实例化对象挂载到uploadEl中
  uploadVm.$mount(uploadEl)
  // 触发上传操作，这个upload()方法就是我们刚刚封装的
  uploadVm.upload()
  // 绑定一个finish事件去通知我们上传完成了
  uploadVm.$on('finish', data => {
    uploadVm.$nextTick(() => {
      // 上传完成后，做销毁工作
      uploadVm.$destroy()
      uploadEl = null
    })
    // 通过callback回调函数将我们需要的内容回调出去，供外部环境使用
    if (callback) callback(data)
  })
}

// 注册这个插件，挂载到prototype上
export default {
  install: function (Vue, options) {
    Vue.prototype.$useUpload = function () {
      // [].splice.call(类数组对象, 0) 这个方法是将一个类数组对象拷贝一份转换成一个Array
      // 通过...解构变成这个函数的参数
      useUpload(...[].splice.call(arguments, 0))
    }
  }
}
