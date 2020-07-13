<template>
  <div class="step3-wrapper">
    <div v-html="protocol.description"></div>
    <div class="agree-checked">
      <a-radio :checked="checked" @click="handleCheck">是否同意以上协议</a-radio>
    </div>
  </div>
</template>

<script>
import * as ProtocolService from '@/service/system/ProtocolService'

export default {
  props: {
    isChecked: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      protocol: {},
      checked: this.isChecked
    }
  },
  async created () {
    const protocol = await ProtocolService.query({
      type: 'CN'
    }, {
      showLoading: false
    })
    if (protocol) {
      this.protocol = protocol[0]
    }
  },
  methods: {
    handleSubmit () {
    },
    handleCheck () {
      this.checked = !this.checked
      this.$emit('change', this.checked)
    }
  }
}
</script>

<style scoped>
.step3-wrapper {
  height: 300px;
  overflow: auto;
  border: 1px solid #ccc;
  padding: 10px;
  border-radius: 8px;
}

.agree-checked {
  position: absolute;
  bottom: 20px;
}
</style>
