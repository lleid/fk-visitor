import * as RuleUtils from '@/utils/RuleUtils'
const StaffRuleBuilder = {
  build: data => {
    return {
      name: RuleUtils.buildRules('姓名', [{ required: true }]),
      mobile: RuleUtils.buildRules('联系方式', [{ required: true }]),
      title: RuleUtils.buildRules('职务', [{ required: true }])
    }
  }
}

export default StaffRuleBuilder
