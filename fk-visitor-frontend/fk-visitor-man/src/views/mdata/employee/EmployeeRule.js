import * as RuleUtils from '@/utils/RuleUtils'
const EmployeeRuleBuilder = {
  build: data => {
    return {
      name: RuleUtils.buildRules('姓名', [{ required: true }]),
      enName: RuleUtils.buildRules('英文名', [{ required: true }]),
      department: RuleUtils.buildRules('部门', [{ required: true }])
    }
  }
}

export default EmployeeRuleBuilder
