package com.fuhuitong.nitri.xgd.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author wwj
 * @since 2019-10-09
 */
@Data
@ApiModel(value="NitriRole对象", description="角色")
public class NitriRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "名称_中文")
    private String nameZh;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "名称_英文")
    private String name;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @ApiModelProperty(value = "更新时间")
    private Long updateTime;

    @ApiModelProperty(value = "删除标记:0-未删除  1-删除")
    private Integer deleteSign;


}
