import * as RuleUtils from '@/utils/RuleUtils'
import * as ImageService from '@/service/vm/ImageService'

const ImageRuleBuilder = {
  build: (data) => {
    return {
      name: RuleUtils.buildRules('镜像名称', [{
          required: true
        },
        {
          asyncValidator: (rule, value, callback) => {
            const param = {
              name: value
            }
            if (data !== undefined && data.id !== undefined) {
              param['id'] = data.id
            }
            ImageService.checkName(param, {
              showLoading: false,
              showSuccess: false,
              showError: false
            }).then(res => {
              if (!res) {
                callback(new Error('镜像名已存在'))
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

export default ImageRuleBuilder
