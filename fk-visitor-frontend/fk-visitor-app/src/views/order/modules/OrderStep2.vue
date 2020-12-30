<template>
  <div class="step3-wrapper">
    <div class="protocols">
      <a-collapse @change="handleChange">
        <a-collapse-panel :header="item.name" v-for="(item, index) in protocols" :key="index">
          <div v-html="item.description"></div>
        </a-collapse-panel>
      </a-collapse>
    </div>
    <div class="agree-checked">
      <a-radio :checked="isChecked" v-if="language === 'CN'">是否同意以上协议</a-radio>
      <a-radio :checked="isChecked" v-else>Do you agree to the above agreement</a-radio>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

import * as ProtocolService from '@/service/system/ProtocolService'

export default {
  props: {
    form: {
      type: Object,
      default: null
    }
  },
  data () {
    return {
      protocols: [],
      checked: []
    }
  },
  computed: {
    ...mapState({
      language: state => state.app.language
    }),
    isChecked () {
      return this.checked.length === this.protocols.length
    }
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
      const protocols = await ProtocolService.query({ type: type, isDeleted: false }, { showLoading: false })
      this.protocols = protocols
    },
    handleChange (array) {
      array.forEach(element => {
        if (this.checked.indexOf(element) === -1) {
          this.checked.push(element)
        }
      })
      if (this.checked.length === this.protocols.length) {
        this.form.isChecked = true
      }
    }
  }
}
</script>

<style scoped>
.step3-wrapper {
  background: #ffffff;
  height: 100%;
  position: relative;
  padding: 24px;
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
