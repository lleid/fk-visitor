import * as RuleUtils from '@/utils/RuleUtils'

const AppointmentRuleBuilder = {
  build: data => {
    return {
      name: RuleUtils.buildRules('姓名', [{ required: true }]),
      orderAt: RuleUtils.buildRules('预约时间', [{ required: true, type: 'date' }]),
      company: RuleUtils.buildRules('公司', [{ required: true }]),
      interviewer: RuleUtils.buildRules('受访人', [{ required: true }]),
      job: RuleUtils.buildRules('职务', [{ required: true }]),
      purposeId: RuleUtils.buildRules('拜访事由', [{ required: true, type: 'number' }]),
      visitAreaId: RuleUtils.buildRules('拜访区域', [{ required: true, type: 'number' }]),
      mobile: RuleUtils.buildRules('手机号', [{ required: true }, RuleUtils.builder.cnMobile()])
    }
  }
}

export default AppointmentRuleBuilder
