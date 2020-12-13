package com.fk.visitor.lib.beans;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Pie {
    private Long value;
    private String name;

    public Pie() {
    }

    public Pie(String name, Long value) {
        this.value = value;
        this.name = name;
    }
}
