import * as RuleUtils from '@/utils/RuleUtils'

const OrderRuleBuilder = {
  build: data => {
    return {
      name: RuleUtils.buildRules('您的姓名', [{ required: true }]),
      mobile: RuleUtils.buildRules('联系方式', [{ required: true }]),
      company: RuleUtils.buildRules('您的公司', [{ required: true }]),
      job: RuleUtils.buildRules('您的职务', [{ required: true }]),
      interviewer: RuleUtils.buildRules('受访人', [{ required: true }]),
      purposeId: RuleUtils.buildRules('拜访事由', [{ required: true, type: 'number' }]),
      visitAreaId: RuleUtils.buildRules('拜访区域', [{ required: true, type: 'number' }])
    }
  }
}

export default OrderRuleBuilder
