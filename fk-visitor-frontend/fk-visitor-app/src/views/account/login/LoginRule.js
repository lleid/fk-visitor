import * as RuleUtils from '@/utils/RuleUtils'

const LoginRules = {
    username: RuleUtils.buildRules('账号', [
        { required: true }
    ]),
    password: RuleUtils.buildRules('密码', [
        { required: true }
    ])
}

export default LoginRules
