package com.fuhuitong.nitri.sys.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Wang
 * @Date 2019/4/19 0019 15:09
 **/

@Data
@TableName("sys_menu")
public class Menu implements Serializable {
    /**
     * 菜单
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "名称")
    private String title;

    /**
     * 上级菜单ID
     */
    @ApiModelProperty(value = "上级菜单id")
    private String parentId;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Long sort;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String icon;

    /**
     * 路由地址
     */
    @ApiModelProperty(value = "路由地址")
    private String url;

    /**
     * 是否菜单 1菜单 2权限
     */
    @ApiModelProperty(value = "菜单OR按钮 1 菜单  2按钮")
    private Integer ismenu;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识")
    private String permission;


    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Long updateTime;


    // 子菜单
    @TableField(exist = false)
    private List<Menu> subs;

    public Menu() {
    }

    public Menu(String id, String title, String parentId) {
        this.id = id;
        this.title = title;
        this.parentId = parentId;
    }

}