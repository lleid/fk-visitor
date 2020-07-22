package com.fk.visitor.lib.entity;

import cn.kinkii.novice.framework.entity.PkUUID;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@ApiModel(value = "操作日志", description = "操作日志")
@Entity
@Table(name = "t_operate_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperateLog extends PkUUID {

    @ApiModelProperty(value = "操作人")
    @Column(name = "operator_name", length = 64)
    private String operatorName;

    @ApiModelProperty(value = "操作时间")
    @Column(name = "operate_time", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date operateTime;

    @ApiModelProperty(value = "操作URL")
    @Column(name = "operate_url", nullable = false)
    private String operateUrl;

    @ApiModelProperty(value = "请求类型")
    @Column(name = "request_method", nullable = false)
    private String requestMethod;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @ApiModelProperty(value = "请求参数")
    @Column(name = "request_param", columnDefinition = "Text")
    private String requestParam;

    @ApiModelProperty(value = "ip地址")
    @Column(name = "operate_ip", nullable = false)
    private String operateIp;

}
