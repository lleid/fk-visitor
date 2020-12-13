import * as RuleUtils from '@/utils/RuleUtils'

const OrderRuleBuilder = {
  build: data => {
    return {
      name: RuleUtils.buildRules('您的姓名', [{ required: true }]),
      mobile: RuleUtils.buildRules('联系方式', [{ required: true }]),
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
      mobile: RuleUtils.buildRules('Contact', [{ required: true }]),
      company: RuleUtils.buildRules('Company', [{ required: true }]),
      title: RuleUtils.buildRules('Post', [{ required: true }]),
      interviewer: RuleUtils.buildRules('Interviewer', [{ required: true }]),
      purposeId: RuleUtils.buildRules('Visit purpost', [{ required: true, type: 'number' }]),
      visitAreaId: RuleUtils.buildRules('Content', [{ required: true, type: 'number' }])
    }
  }
}

export default OrderRuleBuilder
