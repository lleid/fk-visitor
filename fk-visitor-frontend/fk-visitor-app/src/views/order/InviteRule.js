import * as RuleUtils from '@/utils/RuleUtils'

const InviteRuleBuilder = {
  build: data => {
    return {
      name: RuleUtils.buildRules('姓名', [{ required: true }]),
      mobile: RuleUtils.buildRules('手机号', [{ required: true }, RuleUtils.builder.cnMobile()]),
      idCard: RuleUtils.buildRules('证件号', [{ required: true }]),
      company: RuleUtils.buildRules('证件号', [{ required: true }]),
      job: RuleUtils.buildRules('职务', [{ required: true }]),
      purpose: RuleUtils.buildRules('拜访事由', [{ required: true }])
    }
  }
}

export default InviteRuleBuilder
