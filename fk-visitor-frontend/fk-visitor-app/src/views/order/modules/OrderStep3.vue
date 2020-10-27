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
      <a-radio :checked="checked" @click="handleCheck" v-if="language === 'CN'">是否同意以上协议</a-radio>
      <a-radio :checked="checked" @click="handleCheck" v-else>Do you agree to the above agreement</a-radio>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

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
  computed: {
    ...mapState({
      language: state => state.app.language
    })
  },
  watch: {
    language (val) {
      this.handleProtocol(val)
    }
  },
  async created () {
    this.handleProtocol(this.language)
  },
  methods: {
    handleCheck () {
      this.checked = !this.checked
      this.$emit('change', this.checked)
    },
    async handleProtocol (type) {
      const protocols = await ProtocolService.query({ type: type }, { showLoading: false })
      this.protocols = protocols
    }
  }
}
</script>

<style scoped>
.step3-wrapper {
  height: 100%;
  position: relative;
  padding-bottom: 50px;
}

.step3-wrapper .protocols {
  overflow: auto;
  height: 100%;
}

.agree-checked {
  position: absolute;
  bottom: 0px;
  height: 50px;
  line-height: 50px;
}
</style>
