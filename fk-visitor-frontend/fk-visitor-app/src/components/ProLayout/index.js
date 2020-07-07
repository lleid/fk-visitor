import ProLayout from './ProLayout'
// import PageHeaderWrapper from './PageHeaderWrapper/PageHeaderWrapper'

ProLayout.install = function (Vue) {
    Vue.component(ProLayout.name, ProLayout)
}

// PageHeaderWrapper.install = function (Vue) {
//     Vue.component(PageHeaderWrapper.name, PageHeaderWrapper)
// }

// export PageHeaderWrapper
export default ProLayout
