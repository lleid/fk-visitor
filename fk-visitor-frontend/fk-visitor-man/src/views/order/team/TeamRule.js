import * as RuleUtils from '@/utils/RuleUtils'

const TeamRuleBuilder = {
  build: data => {
    return {
      orderAt: RuleUtils.buildRules('预约时间', [{ required: true, type: 'date', trigger: 'blur' }]),
      company: RuleUtils.buildRules('团队名称', [{ required: true }]),
      interviewer: RuleUtils.buildRules('受访人', [{ required: true }]),
      purposeId: RuleUtils.buildRules('拜访事由', [{ required: true, type: 'number' }]),
      visitAreaId: RuleUtils.buildRules('参观区域', [{ required: true, type: 'number' }])
    }
  }
}

export default TeamRuleBuilder
