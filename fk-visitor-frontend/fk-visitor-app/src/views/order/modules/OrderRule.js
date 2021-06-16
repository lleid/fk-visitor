import * as RuleUtils from '@/utils/RuleUtils'

const OrderRuleBuilder = {
  build: data => {
    return {
      name: RuleUtils.buildRules('您的姓名', [{ required: true }]),
      mobile: RuleUtils.buildRules('联系方式', [{ required: true }, {
        asyncValidator: (rule, value, callback) => {
          let flag = false
          var reg1 = /^1\d{10}$/
          var reg2 = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/
          if (reg1.test(value) || reg2.test(value)) {
            flag = true
          }
          if (flag) {
            callback()
          } else {
            callback(new Error('请输入正确的手机号或者邮箱'))
          }
        },
        trigger: 'blur'
      }]),
      company: RuleUtils.buildRules('您的公司', [{ required: true }]),
      title: RuleUtils.buildRules('您的职务', [{ required: true }]),
      interviewer: RuleUtils.buildRules('受访人', [{ required: true }]),
      purposeId: RuleUtils.buildRules('拜访事由', [{ required: true, type: 'number' }]),
      visitAreaId: RuleUtils.buildRules('参观区域', [{ required: true, type: 'number' }])
    }
  },
  build_EN: data => {
    return {
      name: RuleUtils.buildRules('Name', [{ required: true }]),
      mobile: RuleUtils.buildRules('Contact', [{ required: true }, {
        asyncValidator: (rule, value, callback) => {
          let flag = false
          var reg1 = /^1\d{10}$/
          var reg2 = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/
          if (reg1.test(value) || reg2.test(value)) {
            flag = true
          }
          if (flag) {
            callback()
          } else {
            callback(new Error('Please input the correct mobile phone number or email'))
          }
        },
        trigger: 'blur'
      }]),
      company: RuleUtils.buildRules('Company', [{ required: true }]),
      title: RuleUtils.buildRules('Title', [{ required: true }]),
      interviewer: RuleUtils.buildRules('Interviewer', [{ required: true }]),
      purposeId: RuleUtils.buildRules('Visit purpost', [{ required: true, type: 'number' }]),
      visitAreaId: RuleUtils.buildRules('Content', [{ required: true, type: 'number' }])
    }
  }
}

export default OrderRuleBuilder
