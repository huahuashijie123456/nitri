package com.fuhuitong.nitri.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Wang
 * @Date 2019/4/22 0022 17:07
 **/
@TableName("role_menu")
@Data
public class RoleMenu implements Serializable {

    /**
     * ID
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 菜单ID
     */
    private String menuId;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long updateTime;

}
