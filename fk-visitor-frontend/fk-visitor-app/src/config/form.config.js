export default {
    labelCol: {
        sm: { span: 24 },
        md: { span: 4, offset: 2 }
    },
    wrapperCol: {
        sm: { span: 24 },
        md: { span: 16 }
    },
    validateMessages: {
        default: '%s校验失败',
        required: '%s是必填字段',
        enum: '%s必须是 %s 中的一个值',
        whitespace: '%s不得为空',
        types: {
            string: '%s必须是一个有效的字符串%!',
            method: '%s必须是一个有效的方法/函数%!',
            array: '%s必须是一个有效的数组%!',
            object: '%s必须是一个有效的对象%!',
            number: '%s必须是一个有效的数字%!',
            date: '%s必须是一个有效的日期%!',
            boolean: '%s必须是一个有效的布尔值%!',
            integer: '%s必须是一个有效的整数%!',
            float: '%s必须是一个有效的浮点数%!',
            regexp: '%s必须是一个有效的正则表达式%!',
            email: '%s必须是一个有效的电子邮件%!',
            url: '%s必须是一个有效的网址%!',
            hex: '%s必须是一个有效的十六进制数字%!'
        },
        string: {
            len: '%s长度必须是%s个字符',
            min: '%s长度不得少于%s个字符',
            max: '%s长度不得超过%s个字符',
            range: '%s长度必须是%s～%s个字符'
        },
        number: {
            len: '%s must equal %s',
            min: '%s cannot be less than %s',
            max: '%s cannot be greater than %s',
            range: '%s must be between %s and %s'
        },
        array: {
            len: '%s must be exactly %s in length',
            min: '%s cannot be less than %s in length',
            max: '%s cannot be greater than %s in length',
            range: '%s must be between %s and %s in length'
        },
        pattern: {
            mismatch: '%s value %s does not match pattern %s'
        },
        clone () {
            const cloned = JSON.parse(JSON.stringify(this))
            cloned.clone = this.clone
            return cloned
        }
    },
    validateMessages_EN: {
        default: '%s check failure',
        required: '%s iss a required field',
        enum: '%s must be %s one of the values',
        whitespace: '%s must not be empty',
        types: {
            string: '%s must be a valid string%!',
            method: '%s must be a valid functional%!',
            array: '%s must be a valid array%!',
            object: '%s must be a valid object%!',
            number: '%s must be a valid number%!',
            date: '%s must be a valid date%!',
            boolean: '%s must be a valid boolean%!',
            integer: '%s must be a valid int%!',
            float: '%s must be a valid float%!',
            regexp: '%s must be a valid regular expression%!',
            email: '%s must be a valid email%!',
            url: '%s must be a valid url%!',
            hex: '%s must be a valid hexadecimal digit%!'
        },
        string: {
            len: 'The length of %s must be %s characters',
            min: 'The length of %s must be not less than %s characters',
            max: 'The length of %s must be not greater than %s characters',
            range: 'The length of % must be arrage %s～%s characters'
        },
        number: {
            len: '%s must equal %s',
            min: '%s cannot be less than %s',
            max: '%s cannot be greater than %s',
            range: '%s must be between %s and %s'
        },
        array: {
            len: '%s must be exactly %s in length',
            min: '%s cannot be less than %s in length',
            max: '%s cannot be greater than %s in length',
            range: '%s must be between %s and %s in length'
        },
        pattern: {
            mismatch: '%s value %s does not match pattern %s'
        },
        clone () {
            const cloned = JSON.parse(JSON.stringify(this))
            cloned.clone = this.clone
            return cloned
        }
    }
}
