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

    @Column(name = "cn_name", length = 32)
    @ApiModelProperty(value = "中文名称")
    private String cnName;

    @Column(name = "en_name", length = 32)
    @ApiModelProperty(value = "英文名称")
    private String enName;
}
