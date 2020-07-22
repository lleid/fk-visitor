package com.fk.visitor.api.controller.global.req;

import cn.kinkii.novice.framework.controller.request.GenericRequest;
import cn.kinkii.novice.framework.controller.request.annotations.RequestProperty;
import com.fk.visitor.lib.entity.Operator;
import com.fk.visitor.lib.entity.enums.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class PersonalBasicUpdateReq extends GenericRequest<Operator> {

    private String name;

    @RequestProperty(targetProperty = "gender")
    private Gender genderValue;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdayValue;

    private String getBirthday() {
        if (birthdayValue != null) {
            return DateFormatUtils.format(birthdayValue, "yyyy-MM-dd");
        }
        return null;
    }

    private String mobile;

    private String email;

}
