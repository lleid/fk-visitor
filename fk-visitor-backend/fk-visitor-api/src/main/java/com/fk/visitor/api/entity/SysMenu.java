package com.fk.visitor.api.entity;

import cn.kinkii.novice.framework.entity.AssemblyNode;
import cn.kinkii.novice.framework.entity.PkNativeID;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "t_man_menu")
@Getter
@Setter
@NoArgsConstructor
public class SysMenu extends PkNativeID implements AssemblyNode<String> {

    private static final char SEPARATOR = ',';

    public SysMenu(Long id) {
        setId(id);
    }

    @Column(name = "code", nullable = false, length = 16, unique = true)
    @ApiModelProperty(value = "菜单code")
    private String code;

    @Column(name = "parent_code", length = 16)
    @ApiModelProperty(value = "父菜单code")
    private String parentCode;

    @Column(name = "name", nullable = false, length = 32)
    @ApiModelProperty("名称")
    private String name;

    @Column(name = "alias", nullable = false, unique = true, length = 64)
    @ApiModelProperty("别名")
    private String alias;

    @Column(name = "icon", length = 32)
    @ApiModelProperty("图标样式")
    private String icon;

    @Column(name = "url", length = 128)
    @ApiModelProperty("URL")
    private String url;

    @Column(name = "related_urls", columnDefinition = "text")
    @ApiModelProperty("相关URL")
    private String relatedUrlValues;

    @Transient
    private List<String> relatedUrlList;

    public List<String> getRelatedUrls() {
        return Arrays.asList(StringUtils.commaDelimitedListToStringArray(relatedUrlValues));
    }

    public void setRelatedUrls(List<String> relatedUrls) {
        if (relatedUrls != null) {
            this.relatedUrlValues = StringUtils.collectionToCommaDelimitedString(relatedUrls);
        }
    }

    @Transient
    @ApiModelProperty("子菜单")
    private List<SysMenu> subMenus;

    @Override
    public String getSelfId() {
        return getCode();
    }

    @Override
    public String getParentId() {
        return getParentCode();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setChildren(List<? extends AssemblyNode<String>> children) {
        setSubMenus((List<SysMenu>) children);
    }

}
