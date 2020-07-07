import * as RuleUtils from '@/utils/RuleUtils'

const OperatorGroupRuleBuilder = {
    build: (data) => {
        return {
            name: RuleUtils.buildRules('用户组名', [
                { required: true }
            ])
        }
    }
}

export default OperatorGroupRuleBuilder
