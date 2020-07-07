package com.fk.visitor.api.controller.global.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PersonalPasswordUpdateReq {

    @NotEmpty
    private String oldPassword;

    @NotEmpty
    private String newPassword;

}
