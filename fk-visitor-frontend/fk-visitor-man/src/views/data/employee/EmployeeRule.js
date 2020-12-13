import * as RuleUtils from '@/utils/RuleUtils'
const EmployeeRuleBuilder = {
  build: data => {
    return {
      name: RuleUtils.buildRules('名称', [{ required: true }]),
      department: RuleUtils.buildRules('部门', [{ required: true }])
    }
  }
}

export default EmployeeRuleBuilder
