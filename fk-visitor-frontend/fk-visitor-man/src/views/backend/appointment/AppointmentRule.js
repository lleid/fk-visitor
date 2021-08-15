import * as RuleUtils from '@/utils/RuleUtils'

const AppointmentRuleBuilder = {
  build: data => {
    return {
      name: RuleUtils.buildRules('姓名', [{ required: true }]),
      orderAt: RuleUtils.buildRules('预约时间', [{ required: true, type: 'date' }]),
      company: RuleUtils.buildRules('公司', [{ required: true }]),
      interviewer: RuleUtils.buildRules('受访人', [{ required: true }]),
      title: RuleUtils.buildRules('职务', [{ required: true }]),
      purposeId: RuleUtils.buildRules('拜访事由', [{ required: true, type: 'number' }]),
      visitAreaId: RuleUtils.buildRules('参观区域', [{ required: true, type: 'number' }]),
      mobile: RuleUtils.buildRules('联系方式', [{ required: true }])
    }
  }
}

export default AppointmentRuleBuilder
