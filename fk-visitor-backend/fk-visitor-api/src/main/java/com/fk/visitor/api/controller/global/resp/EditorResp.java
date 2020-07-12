package com.fk.visitor.api.controller.global.resp;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class EditorResp {
    private Integer errno = 0;
    private List<String> data;

    public EditorResp() {
    }

    public EditorResp(Integer errno, List<String> data) {
        this.errno = errno;
        this.data = data;
    }
}
