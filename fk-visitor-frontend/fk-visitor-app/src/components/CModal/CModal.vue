<template>
  <a-modal
    :wrapClassName="'c-modal c-modal-' + size + (useTabs ? ' c-modal-use-tabs' : '')"
    v-bind="$props"
    :body-style="computedBodyStyle"
    :width="modalWidth"
    @ok="handleOk"
    @cancel="handleCancel"
  >
    <slot v-if="$props.title === undefined" name="title" slot="title" />
    <div
      v-if="$props.footer === undefined && $slots.footer !== undefined"
      ref="wrappedFooter"
      slot="footer"
    >
      <slot name="footer" />
    </div>
    <div v-if="loading" class="c-modal-loading">
      <a-spin size="large" :tip="loadingTip">
        <a-icon slot="indicator" type="loading" spin />
      </a-spin>
    </div>
    <slot v-else />
  </a-modal>
</template>

<script>
import VueTypes from 'vue-types'
import throttle from 'lodash.throttle'

import { Modal } from 'ant-design-vue'

VueTypes.sensibleDefaults = false

const ModalProps = {
  afterClose: VueTypes.func,
  bodyStyle: VueTypes.object,
  cancelText: VueTypes.any,
  cancelButtonProps: VueTypes.object,
  centered: VueTypes.bool.def(true),
  closable: VueTypes.bool.def(true),
  closeIcon: VueTypes.any,
  confirmLoading: VueTypes.bool,
  destroyOnClose: VueTypes.bool,
  footer: VueTypes.any,
  forceRender: VueTypes.bool,
  getContainer: VueTypes.func,
  keyboard: VueTypes.bool.def(true),
  mask: VueTypes.bool.def(true),
  maskClosable: VueTypes.bool.def(false),
  maskStyle: VueTypes.object,
  okText: VueTypes.string,
  okType: VueTypes.string,
  okButtonProps: VueTypes.object,
  title: VueTypes.string,
  visible: VueTypes.bool,
  width: VueTypes.oneOfType([VueTypes.string, VueTypes.number]),
  wrapClassName: VueTypes.string,
  zIndex: VueTypes.number
}

export const CModalProps = {
  ...ModalProps,
  size: VueTypes.oneOf(['small', 'default', 'large', 'fullscreen']).def('default'),
  maxHeight: VueTypes.oneOfType([VueTypes.string, VueTypes.number]).def('75%'),
  loading: VueTypes.bool.def(false),
  loadingTip: VueTypes.string,
  useTabs: VueTypes.bool.def(false)
}

const CModal = {
  name: 'CModal',
  props: {
    ...CModalProps
  },
  data () {
    return {
      modalWidth: this.calcWidth(),
      bodyHeight: this.calcBodyHeight(),
      bodyResizer: undefined
    }
  },
  created () {
  },
  watch: {
    visible (val) {
      if (val) {
        this.bodyHeight = this.calcBodyHeight()
        this.bodyResizer = throttle(() => {
          this.bodyHeight = this.calcBodyHeight()
        }, 100, { trailing: true, leading: true })
        window.addEventListener('resize', this.bodyResizer)
      } else {
        if (this.bodyResizer !== undefined) {
          this.bodyResizer.cancel()
          window.removeEventListener('resize', this.bodyResizer)
        }
      }
    },
    maxHeight () {
      this.bodyHeight = this.calcBodyHeight()
    }
  },
  computed: {
    computedBodyStyle () {
      if (this.size === 'fullscreen') {
        return {
          height: this.bodyHeight + 'px',
          overflowY: 'auto',
          ...this.bodyStyle
        }
      } else {
        return {
          maxHeight: this.bodyHeight + 'px',
          overflowY: 'auto',
          ...this.bodyStyle
        }
      }
    }
  },
  methods: {
    handleOk (e) {
      this.$emit('ok', e)
    },
    handleCancel (e) {
      this.$emit('cancel', e)
    },
    calcWidth () {
      if (this.width !== undefined) {
        return this.width
      } else {
        if (this.size === 'fullscreen') {
          return '98%'
        } else if (this.size === 'small') {
          return '40%'
        } else if (this.size === 'default') {
          return '60%'
        } else if (this.size === 'large') {
          return '80%'
        }
      }
    },
    calcBodyHeight () {
      let modalHeight = 0
      if (this.size === 'fullscreen') {
        modalHeight = parseInt(0.98 * document.documentElement.clientHeight)
      } else {
        if (typeof this.maxHeight === 'string') {
          if (this.maxHeight.endsWith('%')) {
            const percentHeight = parseFloat(this.maxHeight.substr(0, this.maxHeight.length - 1))
            if (!isNaN(percentHeight)) {
              modalHeight = parseInt((percentHeight / 100) * document.documentElement.clientHeight)
            }
          } else if (this.maxHeight.endsWith('px')) {
            const fixedHeight = parseInt(this.maxHeight.substr(0, this.maxHeight.length - 2))
            if (!isNaN(fixedHeight)) {
              modalHeight = fixedHeight
            }
          }
        } else if (typeof this.maxHeight === 'number') {
          modalHeight = this.maxHeight
        }
      }
      let bodyHeight = modalHeight - this.getHeaderHeight() - this.getFooterHeight()
      if (bodyHeight < 88) {
        bodyHeight = 88
      }
      return bodyHeight
    },
    getHeaderHeight () {
      return 55
    },
    getFooterHeight () {
      if (this.footer === undefined) {
        return 53
      } else if (this.footer === null) {
        return 0
      } else if (typeof this.footer === 'string') {
        if (this.footer.trim() === '') {
          return 21
        } else {
          return 43
        }
      } else if (this.$slots.footer !== undefined) {
        return this.$refs.wrappedFooter.clientHeight + 21
      }
    }
  }
}

CModal.install = function (Vue) {
  Vue.use(Modal)
  Vue.component(CModal.name, CModal)
}

export default CModal

</script>

<style lang="less">
@import '~ant-design-vue/lib/style/themes/default';

.c-modal {
  &.c-modal-small {
    .ant-modal {
      max-width: 480px;
    }
  }
  &.c-modal-default {
    .ant-modal {
      max-width: 720px;
    }
  }
  &.c-modal-large {
    .ant-modal {
      max-width: 960px;
    }
  }
  &.c-modal-use-tabs {
    .ant-modal-body {
      padding: 0;
      .ant-tabs-vertical {
        margin: 12px 0;
        .ant-tabs-content {
          padding: 12px 24px;
        }
      }
      .ant-tabs-top {
        margin: 0 12px;
        .ant-tabs-tabpane {
          padding: 8px 12px 24px 12px;
        }
      }
      .ant-tabs-bottom {
        margin: 0 12px;
        .ant-tabs-tabpane {
          padding: 24px 12px 8px 12px;
        }
      }
      .c-modal-loading {
        margin: 24px;
      }
    }
  }
  .c-modal-loading {
    text-align: center;
    .ant-spin {
      color: #e8e8e8;
      .anticon-spin {
        -webkit-animation-duration: 0.6s;
        animation-duration: 0.6s;
      }
    }
  }
  &.ant-modal {
    padding-bottom: 0;
    .ant-modal-header {
      background-color: transparent;
    }
  }
}
@media screen and (max-width: @screen-md) {
  .c-modal {
    &.c-modal-small,
    &.c-modal-default,
    &.c-modal-large {
      .ant-modal {
        width: 90% !important;
      }
    }
  }
}
</style>
