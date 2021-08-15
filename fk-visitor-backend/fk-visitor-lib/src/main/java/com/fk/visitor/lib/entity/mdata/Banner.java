package com.fk.visitor.lib.entity.mdata;

import cn.kinkii.novice.framework.entity.PkNativeID;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fk.visitor.lib.entity.system.Station;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@ApiModel(value = "横幅图片", description = "横幅图片")
@Entity
@Table(name = "t_banner")
@Getter
@Setter
@NoArgsConstructor
public class Banner extends PkNativeID {

    @ApiModelProperty(value = "图片名称")
    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @ApiModelProperty(value = "图片路径")
    @Column(name = "url", length = 128, nullable = false)
    private String url;

    @ApiModelProperty("创建时间")
    @Column(name = "create_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt = new Date();

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "station_id")
    @ApiModelProperty("位置")
    private Station station;

    public Banner(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
