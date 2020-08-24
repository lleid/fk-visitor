import * as RuleUtils from '@/utils/RuleUtils'
import * as VisitAreaService from '@/service/system/VisitAreaService'
const VisitAreaRuleBuilder = {
  build: data => {
    return {
      cnName: RuleUtils.buildRules('中文名称', [
        { required: true },
        {
          asyncValidator: (rule, value, callback) => {
            const param = { name: value }
            if (data !== undefined && data.id !== undefined) {
              param['id'] = data.id
            }
            VisitAreaService.checkCnName(param, {
              showLoading: false,
              showSuccess: false,
              showError: false
            }).then(res => {
              if (!res) {
                callback(new Error('中文名称已存在'))
              } else {
                callback()
              }
            })
          }
        }
      ]),
      enName: RuleUtils.buildRules('英文名称', [
        { required: true },
        {
          asyncValidator: (rule, value, callback) => {
            const param = { name: value }
            if (data !== undefined && data.id !== undefined) {
              param['id'] = data.id
            }
            VisitAreaService.checkEnName(param, {
              showLoading: false,
              showSuccess: false,
              showError: false
            }).then(res => {
              if (!res) {
                callback(new Error('英文名称已存在'))
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

export default VisitAreaRuleBuilder
