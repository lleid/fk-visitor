package com.fk.visitor.lib.entity;

import cn.kinkii.novice.framework.entity.PkNativeID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@ApiModel(value = "协议", description = "协议")
@Entity
@Table(name = "t_protocol")
@Getter
@Setter
@NoArgsConstructor
public class Protocol extends PkNativeID {

    @ApiModelProperty(value = "类型")
    @Column(name = "type ", length = 32)
    private String type;

    @ApiModelProperty(value = "名称")
    @Column(name = "name ", length = 32)
    private String name;

    @ApiModelProperty(value = "简介")
    @Column(name = "description ", columnDefinition = "mediumtext")
    private String description;
}
