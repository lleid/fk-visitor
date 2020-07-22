import * as RuleUtils from '@/utils/RuleUtils'

const OperatorRuleBuiler = {
  build: data => {
    return {
      username: RuleUtils.buildRules('用户名', [{ required: true }, RuleUtils.builder.moreWordCharacters()]),
      name: RuleUtils.buildRules('姓名', [{ required: true }]),
      password: RuleUtils.buildRules('密码', [
        { required: !(data !== undefined && data.id !== undefined) },
        RuleUtils.builder.moreWordCharacters(),
        { min: 8 }
      ]),
      plainPassword: RuleUtils.buildRules('确认密码', [
        { required: !(data !== undefined && data.id !== undefined) },
        {
          validator: (rule, value, callback) => {
            if (value !== data.password) {
              callback(new Error('密码输入不一致'))
            } else {
              callback()
            }
          }
        }
      ]),
      mobile: RuleUtils.buildRules('手机号', [{ required: true }, RuleUtils.builder.cnMobile()])
    }
  }
}

export default OperatorRuleBuiler
