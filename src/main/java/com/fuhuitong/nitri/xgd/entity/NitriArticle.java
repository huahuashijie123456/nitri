package com.fuhuitong.nitri.xgd.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 文章管理
 * </p>
 *
 * @author wwj
 * @since 2019-10-09
 */
@Data
@ApiModel(value="NitriArticle对象", description="文章管理")
public class NitriArticle implements Serializable {

    private static final long serialVersionUID = 3376258782099875074L;
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "栏目编号")
    private Long siteId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "状态，0:隐藏，1:显示")
    private Integer statusSign;

    @ApiModelProperty(value = "推荐标识，0:否，1:是")
    private Integer recommendSign;

    @ApiModelProperty(value = "简介")
    private String introduction;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "点击量")
    private Long clicks;

    @ApiModelProperty(value = "添加时间")
    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private Long updateTime;

    @ApiModelProperty(value = "删除标记: 0-未删除 1-已删除")
    @TableLogic(value = "0", delval = "1")
    private Integer deleteSign;

    @ApiModelProperty(value = "图片路径")
    private String picPath;
}
