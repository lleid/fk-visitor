package com.fk.visitor.api.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "novice.k-accessory.sms.template")
public class SmsConfig {
    /**
     * 【复星凯特】尊敬的{1}先生（女士），您好！诚挚的邀请您于{2}日莅临我司，邀请码为：{3}。如因故不能按时到达，请提前与我们联系，祝您在生活愉快！
     */
    private String t1;
}
