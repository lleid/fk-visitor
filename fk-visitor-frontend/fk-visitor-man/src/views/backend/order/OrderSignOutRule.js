import * as RuleUtils from '@/utils/RuleUtils'
const StationRuleBuilder = {
  build: data => {
    return {
      reasonId: RuleUtils.buildRules('签出原因', [{ required: true, type: 'string' }])
    }
  }
}

export default StationRuleBuilder
