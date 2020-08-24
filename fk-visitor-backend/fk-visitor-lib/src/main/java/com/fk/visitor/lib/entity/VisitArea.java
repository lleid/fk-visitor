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

@ApiModel(value = "参观区域", description = "参观区域")
@Entity
@Table(name = "t_visit_area")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class VisitArea extends PkNativeID {

    @Column(name = "cn_name", length = 32)
    @ApiModelProperty(value = "中文名称")
    private String cnName;

    @Column(name = "en_name", length = 32)
    @ApiModelProperty(value = "英文名称")
    private String enName;
}
