<template>
  <div class="step3-wrapper">
    <div class="protocols">
      <a-collapse>
        <a-collapse-panel :header="item.name" v-for="(item, index) in protocols" :key="index">
          <div v-html="item.description"></div>
        </a-collapse-panel>
      </a-collapse>
    </div>
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
      protocols: [],
      checked: this.isChecked
    }
  },
  async created () {
    const protocols = await ProtocolService.query({
      type: 'CN'
    }, {
      showLoading: false
    })
    this.protocols = protocols
  },
  methods: {
    handleCheck () {
      this.checked = !this.checked
      this.$emit('change', this.checked)
    }
  }
}
</script>

<style scoped>
.step3-wrapper {
  height: 100%;
  padding-bottom: 50px;
}

.step3-wrapper .protocols {
  overflow: auto;
  height: 100%;
}

.agree-checked {
  position: absolute;
  bottom: 80px;
}
</style>
