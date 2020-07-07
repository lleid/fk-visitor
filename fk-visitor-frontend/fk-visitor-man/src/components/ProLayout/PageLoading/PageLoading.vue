<template>
  <div :style="{ paddingTop: '100px', textAlign: 'center' }">
    <a-spin size="large" :tip="tip" />
  </div>
</template>

<script>
import VueTypes from 'vue-types'

import { Spin } from 'ant-design-vue'

export default {
  name: 'PageLoading',
  components: {
    ASpin: Spin
  },
  props: {
    tip: VueTypes.string.def('')
  },
  data () {
    return {
    }
  }
}
</script>
