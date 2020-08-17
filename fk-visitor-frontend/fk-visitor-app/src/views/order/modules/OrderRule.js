import * as RuleUtils from '@/utils/RuleUtils'

const OrderRuleBuilder = {
  build: data => {
    return {
      name: RuleUtils.buildRules('您的姓名', [{ required: true }]),
      mobile: RuleUtils.buildRules('您的电话', [{ required: true }, RuleUtils.builder.cnMobile()]),
      idCard: RuleUtils.buildRules('您的证件', [{ required: true }]),
      company: RuleUtils.buildRules('您的公司', [{ required: true }]),
      job: RuleUtils.buildRules('您的职务', [{ required: true }]),
      purposeId: RuleUtils.buildRules('拜访事由', [{ required: true, type: 'number' }])
    }
  }
}

export default OrderRuleBuilder
