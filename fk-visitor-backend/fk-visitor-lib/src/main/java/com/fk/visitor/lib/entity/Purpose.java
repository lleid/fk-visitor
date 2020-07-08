package com.fk.visitor.lib.entity;

import cn.kinkii.novice.framework.entity.PkNativeID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@ApiModel(value = "拜访事由", description = "拜访事由")
@Entity
@Table(name = "t_purpose")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Purpose extends PkNativeID {

    @Column(name = "name", length = 32)
    @ApiModelProperty(value = "姓名")
    private String name;
}
