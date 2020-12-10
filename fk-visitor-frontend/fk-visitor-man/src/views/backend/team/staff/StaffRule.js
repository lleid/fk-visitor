import * as RuleUtils from '@/utils/RuleUtils'
const StaffRuleBuilder = {
  build: data => {
    return {
      name: RuleUtils.buildRules('姓名', [{ required: true }]),
      mobile: RuleUtils.buildRules('手机号', [RuleUtils.builder.cnMobile()]),
      email: RuleUtils.buildRules('邮箱', [{ type: 'email' }]),
      department: RuleUtils.buildRules('职务', [{ required: true }])
    }
  }
}

export default StaffRuleBuilder
