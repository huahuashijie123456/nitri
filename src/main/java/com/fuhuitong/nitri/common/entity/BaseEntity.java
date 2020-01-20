package com.fuhuitong.nitri.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * pk
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    /**
     * 创建人
     */

    private String createUser;

    /**
     * 修改时间
     */
    private Long updateTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 删除标记    0未删除  1删除
     */
    @TableLogic
    private Integer deleteSign;
}
