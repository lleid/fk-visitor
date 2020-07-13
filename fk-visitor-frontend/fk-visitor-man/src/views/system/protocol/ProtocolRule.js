import * as RuleUtils from '@/utils/RuleUtils'
import * as ProtocolService from '@/service/system/ProtocolService'
const ProtocolRuleBuilder = {
  build: data => {
    return {
      type: RuleUtils.buildRules('类型', [
        { required: true },
        {
          asyncValidator: (rule, value, callback) => {
            console.log('asyncValidator')
            const param = { type: value }
            if (data !== undefined && data.id !== undefined) {
              param['id'] = data.id
            }
            ProtocolService.checkType(param, {
              showLoading: false,
              showSuccess: false,
              showError: false
            }).then(res => {
              if (!res) {
                callback(new Error('类型已存在'))
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
