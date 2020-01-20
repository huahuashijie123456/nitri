package com.fuhuitong.nitri.sys.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author Wang
 * @Date 2019/4/22 0022 9:44
 **/
@Data
public class UserRole {

    @TableId(type = IdType.INPUT)
    private String id;
    private String userId;
    private String roleId;

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

    public UserRole() {

    }

    public UserRole(String userId, String roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }


}
