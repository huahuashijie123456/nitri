package com.fuhuitong.nitri.xgd.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 院企介绍
 * </p>
 *
 * @author wwj
 * @since 2019-10-09
 */
@Data
@ApiModel(value="NitriIntro对象", description="院企介绍")
public class NitriIntro implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "组建与发展情况")
    private String establishment;

    @ApiModelProperty(value = "成果与荣誉")
    private String achievement;

    @ApiModelProperty(value = "产业发展情况")
    private String industryIntro;

    @ApiModelProperty(value = "研发孵化、服务平台发展情况")
    private String incubatorIntro;

    @ApiModelProperty(value = "理事单位")
    private String directorUnit;

    @ApiModelProperty(value = "删除标记: 0-未删除 1-已删除")
    private Integer deleteSign;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @ApiModelProperty(value = "更新时间")
    private Long updateTime;


}
