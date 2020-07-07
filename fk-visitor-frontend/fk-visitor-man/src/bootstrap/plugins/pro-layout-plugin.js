
import ProLayout from '@/components/ProLayout'
import PageHeaderWrapper from '@/components/ProLayout/PageHeaderWrapper'

const install = (Vue) => {
    Vue.use(ProLayout)
    Vue.use(PageHeaderWrapper)
}

export default {
    install
}
