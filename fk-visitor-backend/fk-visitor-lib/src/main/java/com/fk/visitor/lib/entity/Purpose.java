package com.fk.visitor.lib.entity;

import cn.kinkii.novice.framework.entity.LogicalDeleteable;
import cn.kinkii.novice.framework.entity.PkNativeID;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@ApiModel(value = "拜访事由", description = "拜访事由")
@Entity
@Table(name = "t_purpose")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Purpose extends PkNativeID implements LogicalDeleteable {

    @Column(name = "cn_name", length = 64)
    @ApiModelProperty(value = "中文名称")
    private String cnName;

    @Column(name = "en_name", length = 64)
    @ApiModelProperty(value = "英文名称")
    private String enName;

    @ApiModelProperty("创建时间")
    @Column(name = "create_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt=new Date();

    @ApiModelProperty(value = "创建人")
    @Column(name = "create_username", length = 16)
    private String createUsername;

    @ApiModelProperty("最后更改时间")
    @Column(name = "update_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateAt;

    @Column(name = "update_username", length = 16)
    @ApiModelProperty(value = "最后更改人")
    private String updateUsername;

    @ApiModelProperty("作废时间")
    @Column(name = "delete_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deleteAt;

    @Column(name = "delete_username", length = 16)
    @ApiModelProperty(value = "作废人")
    private String deleteUsername;

    @ApiModelProperty("是否删除")
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Override
    public String getDelFlag() {
        return "isDeleted";
    }

    @Override
    public String getDelTimeFlag() {
        return "deleteAt";
    }
}
