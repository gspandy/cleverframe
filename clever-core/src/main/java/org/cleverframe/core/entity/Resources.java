package org.cleverframe.core.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 系统资源表
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-7-14 16:33 <br/>
 */
@Entity
@Table(name = "core_resources")
@DynamicInsert
@DynamicUpdate
public class Resources implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * URL资源（1：URL资源；2：UI资源）
     */
    public static final Character URL_RESOURCES = '1';

    /**
     * UI资源（1：URL资源；2：UI资源）
     */
    public static final Character UI_RESOURCES = '2';

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 资源标题
     */
    private String title;

    /**
     * 资源URL地址
     */
    private String resourcesUrl;

    /**
     * 资源访问所需要的权限标识字符串
     */
    private String permission;

    /**
     * 资源类型（1：URL资源；2：UI资源）
     */
    private Character resourcesType;

    /**
     * 资源说明
     */
    private String description;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResourcesUrl() {
        return resourcesUrl;
    }

    public void setResourcesUrl(String resourcesUrl) {
        this.resourcesUrl = resourcesUrl;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Character getResourcesType() {
        return resourcesType;
    }

    public void setResourcesType(Character resourcesType) {
        this.resourcesType = resourcesType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
