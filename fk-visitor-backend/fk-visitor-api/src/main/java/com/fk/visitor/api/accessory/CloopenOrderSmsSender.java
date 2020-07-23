package com.fk.visitor.api.accessory;


import cn.kinkii.novice.accessory.cloopen.CloopenSmsSender;
import cn.kinkii.novice.accessory.cloopen.exception.CloopenApiException;
import cn.kinkii.novice.accessory.vcode.exception.SendFailureException;
import cn.kinkii.novice.accessory.vcode.exception.VCodeException;
import com.fk.visitor.lib.ProjectConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CloopenOrderSmsSender {

    @Autowired
    private ProjectConfig projectConfig;
    @Autowired
    private CloopenSmsSender cloopenSmsSender;

    public void send(String mobile, String templateId, String... params) throws VCodeException {
        try {
            if (!projectConfig.getIsDebug()) {
                cloopenSmsSender.send(mobile, templateId, params);
            }
        } catch (CloopenApiException e) {
            throw new SendFailureException(e);
        }
    }
}
