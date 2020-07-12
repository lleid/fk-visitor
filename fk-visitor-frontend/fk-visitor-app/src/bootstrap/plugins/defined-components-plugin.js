import CIcon from '@/components/CIcon'
import CModal from '@/components/CModal'
import CTable from '@/components/CTable'

import RightContent from '@/components/RightContent'
import UploadPlugin from '@/components/UploadPlugin'

const install = (Vue) => {
    Vue.use(CIcon)
    Vue.use(CModal)
    Vue.use(CTable)
    Vue.use(RightContent)
    Vue.use(UploadPlugin)
}

export default {
    install
}
