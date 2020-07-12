package com.fk.visitor.api.controller.global.resp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UploadResp {
    private Long uid;
    private String name;
    private String url;
    private String state;

    public UploadResp() {
    }

    public UploadResp(Long uid, String name, String url, String state) {
        this.uid = uid;
        this.name = name;
        this.url = url;
        this.state = state;
    }


}
