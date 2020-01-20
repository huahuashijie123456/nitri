package com.fuhuitong.nitri.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author Wang
 * @Date 2019/4/24 0024 11:04
 **/
@Data
public class Department implements Serializable {
    /**
     * ID
     */
    @ApiModelProperty(hidden = true)
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 商户ID
     */
    @ApiModelProperty(hidden = true)
    private String merchantsId;

    /**
     * 父ID
     */
    @ApiModelProperty(value = "父id")
    private String parentId;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    private String name;


    /**
     * 员工
     */
    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private List<UserDetail> users;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(hidden = true)
    private LocalDateTime updateTime;

    // 子菜单
    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private List<Department> subs;


}
