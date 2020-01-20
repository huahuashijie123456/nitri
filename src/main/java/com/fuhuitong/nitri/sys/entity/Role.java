package com.fuhuitong.nitri.sys.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

/**
 * @Author Wang
 * @Date 2019/4/19 0019 10:12
 **/
@Data
@Builder
public class Role {


    @TableId(type = IdType.INPUT)
    private String id;
    private String name;
    private String nameZh;
    private String roleDescribe;

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


    public Role() {
    }

    public Role(String id, String name, String nameZh) {
        this.id = id;
        this.name = name;
        this.nameZh = nameZh;
    }


    public Role(String id, String name, String nameZh, String roleDescribe, Long createTime, Long updateTime) {
        this.id = id;
        this.name = name;
        this.nameZh = nameZh;
        this.roleDescribe = roleDescribe;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Role(String id, String name, String nameZh, String roleDescribe) {
        this.id = id;
        this.name = name;
        this.nameZh = nameZh;
        this.roleDescribe = roleDescribe;
    }
}
