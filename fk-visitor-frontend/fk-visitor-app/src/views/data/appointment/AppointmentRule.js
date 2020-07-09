import * as RuleUtils from '@/utils/RuleUtils'

const AppointmentRuleBuilder = {
  build: data => {
    return {
      name: RuleUtils.buildRules('姓名', [{ required: true }]),
      mobile: RuleUtils.buildRules('手机号', [{ required: true }, RuleUtils.builder.cnMobile()])
    }
  }
}

export default AppointmentRuleBuilder
