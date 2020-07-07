<template>
  <div style="margin: 40px auto 0;">
    <a-form-model-item
      label="CPU"
      prop="template.vCPUs"
    >
      <a-radio-group v-model="form.template.vCPUs" buttonStyle="solid" @change="vcpuChange">
        <a-radio-button v-for="(item, v) in vCPUs" :key="v" :value="item" :checked="v === 0 ? true:false">{{ item }}核</a-radio-button>
      </a-radio-group>
    </a-form-model-item>
    <a-form-model-item
      label="内存"
      prop="template.ram"
    >
      <a-radio-group v-model="form.template.ram" buttonStyle="solid" @change="ramChange">
        <template v-for="r in rams">
          <template v-if="r >= 1024">
            <a-radio-button :key="r" :value="r">{{ r/1024 }}GB</a-radio-button>
          </template>
          <template v-else>
            <a-radio-button :key="r" :value="r">{{ r }}MB</a-radio-button>
          </template>
        </template>

      </a-radio-group>
    </a-form-model-item>
    <a-form-model-item label="系统盘">
      <a-radio-group v-model="form.template.vCPUs" buttonStyle="solid" @change="vcpuChange">
        <a-radio-button v-for="(item, v) in vCPUs" :key="v" :value="item" :checked="v === 0 ? true:false">{{ item }}核</a-radio-button>
      </a-radio-group>
    </a-form-model-item>
  </div>
</template>

<script>
import * as TemplateService from '@/service/vm/TemplateService'

export default {
  name: 'Step2',
  props: {
    form: {
      type: Object,
      default: null
    }
  },
  data () {
    return {
      labelCol: { lg: { span: 5 }, sm: { span: 5 } },
      wrapperCol: { lg: { span: 19 }, sm: { span: 19 } },
      vCPUs: [],
      rams: [],
      sizeMax: 0,
      marks: {},
      loadTemplate: []
    }
  },
  created () {
    TemplateService.queryAll({
      showLoading: false
    }).then(res => {
      this.loadTemplate = res
    })
    TemplateService.findCpuDistinctAll({
      showLoading: false
    }).then(res => {
      this.vCPUs = res
      this.form.template.vCPUs = res[0]
      this.vcpuChange()
    })
  },
  methods: {
    vcpuChange (e) {
      if (this.form.template.vCPUs !== undefined && this.form.template.vCPUs !== '') {
        TemplateService.findRamDistinctByCpu({ cpu: this.form.template.vCPUs }, {
          showLoading: false
        }).then(res => {
          this.rams = res
          this.form.template.ram = res[0]
          this.ramChange()
        })
      }
    },
    ramChange (e) {
      if (this.form.template.ram !== undefined && this.form.template.ram !== '') {
        TemplateService.findSizeDistinctByCpuAndRam({ cpu: this.form.template.vCPUs, ram: this.form.template.ram }, {
          showLoading: false
        }).then(res => {
          const mark = {}
          const len = res.length
          this.sizeMax = Number(res[len - 1])
          this.form.template.size = res[0]
          res.map(item => {
            mark[item] = item + 'GB'
          })
          this.marks = mark
        })
      }
    }
  }
}
</script>

<style lang="less" scoped>
.step-form-style-desc {
  padding: 0 56px;
  color: rgba(0,0,0,.45);

  h3 {
    margin: 0 0 12px;
    color: rgba(0,0,0,.45);
    font-size: 16px;
    line-height: 32px;
  }

  h4 {
    margin: 0 0 4px;
    color: rgba(0,0,0,.45);
    font-size: 14px;
    line-height: 22px;
  }

  p {
    margin-top: 0;
    margin-bottom: 12px;
    line-height: 22px;
  }
}
</style>
