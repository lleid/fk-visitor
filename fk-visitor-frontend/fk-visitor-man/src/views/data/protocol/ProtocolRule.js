import * as RuleUtils from '@/utils/RuleUtils'
import * as ProtocolService from '@/service/system/ProtocolService'
const ProtocolRuleBuilder = {
  build: data => {
    return {
      name: RuleUtils.buildRules('名称', [
        { required: true },
        {
          asyncValidator: (rule, value, callback) => {
            const param = { name: value }
            if (data !== undefined && data.id !== undefined) {
              param['id'] = data.id
            }
            ProtocolService.checkName(param, {
              showLoading: false,
              showSuccess: false,
              showError: false
            }).then(res => {
              if (!res) {
                callback(new Error('名称已存在'))
              } else {
                callback()
              }
            })
          }
        }
      ])
    }
  }
}

export default ProtocolRuleBuilder
