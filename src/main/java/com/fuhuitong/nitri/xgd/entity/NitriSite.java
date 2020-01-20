package com.fuhuitong.nitri.xgd.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 栏目管理
 * </p>
 *
 * @author wwj
 * @since 2019-10-09
 */
@Data
@ApiModel(value="NitriSite对象", description="栏目管理")
public class NitriSite implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "栏目中文名称，前端需根据栏目名称将栏目下内容调取至前端对应板块")
    private String name;

    @ApiModelProperty(value = "栏目标识。栏目统一标识。")
    private String nid;

    @ApiModelProperty(value = "栏目状态，0-隐藏，1-显示")
    private Integer statusSign;

    @ApiModelProperty(value = "页面展示类型，1-列表，2-单页，3-站内链接，4-站外链接。")
    private Integer typeSign;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "显示个数。该栏目下内容展示个数")
    private Integer size;

    @ApiModelProperty(value = "添加时间")
    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private Long updateTime;

    @ApiModelProperty(value = "删除标记: 0-未删除 1-已删除")
    @TableLogic(value = "0",delval = "1")
    private Integer deleteSign;

    /**
     * 记录子栏目
     */
    @TableField(exist = false)
    private List<NitriSite> subSites;

}
