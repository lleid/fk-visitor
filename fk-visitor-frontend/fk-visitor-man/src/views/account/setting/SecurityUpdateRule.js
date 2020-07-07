import * as RuleUtils from '@/utils/RuleUtils'

const SecurityUpdateRuleBuiler = {
    build: (data) => {
        return {
            oldPassword: RuleUtils.buildRules('原密码', [
                { required: true }
            ]),
            newPassword: RuleUtils.buildRules('新密码', [
                { required: true },
                { min: 8 },
                RuleUtils.builder.password()
            ]),
            repeatPassword: RuleUtils.buildRules('确认密码', [
                { required: true },
                {
                    validator: (rule, value, callback) => {
                        if (value !== data.newPassword) {
                            callback(new Error('确认密码输入不一致'))
                        } else {
                            callback()
                        }
                    }
                }
            ])
        }
    }
}

export default SecurityUpdateRuleBuiler
