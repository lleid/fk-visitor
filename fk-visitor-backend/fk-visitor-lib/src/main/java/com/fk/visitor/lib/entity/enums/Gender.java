package com.fk.visitor.lib.entity.enums;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lizz
 * @date 2020/6/5 0005 15:36
 */
@Getter
@JsonSerialize(using = GenderJsonSerializer.class)
public enum Gender {
    UNKNOWN(0, "未知"),
    MALE(1, "男"),
    FEMALE(2, "女");

    public int value;

    public String display;

    Gender(int value, String display) {
        this.value = value;
        this.display = display;
    }

    public Map<String, Object> toMap() {
        return new HashMap<String, Object>() {{
            put("name", name());
            put("value", value);
            put("display", display);
        }};
    }
}
