import CIcon from '@/components/CIcon'
import CModal from '@/components/CModal'
import CTable from '@/components/CTable'

import RightContent from '@/components/RightContent'

const install = Vue => {
  Vue.use(CIcon)
  Vue.use(CModal)
  Vue.use(CTable)
  Vue.use(RightContent)
}

export default {
  install
}
