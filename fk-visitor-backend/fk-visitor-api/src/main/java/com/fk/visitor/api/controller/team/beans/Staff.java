package com.fk.visitor.api.controller.team.beans;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Staff {
    private String name;
    private String idCard;
    private String email;
    private String mobile;
    private String title;
    private Boolean isMessage;
}
