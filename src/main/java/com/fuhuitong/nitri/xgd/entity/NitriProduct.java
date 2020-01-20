package com.fuhuitong.nitri.xgd.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品
 * </p>
 *
 * @author wwj
 * @since 2019-10-09
 */
@Data
@ApiModel(value="NitriProduct对象", description="商品")
public class NitriProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "商品类别")
    private Long categoryId;

    @ApiModelProperty(value = "研发单位")
    private String companyId;

    @ApiModelProperty(value = "商品状态: 0-出售中 1-已下架")
    private Integer productStatus;

    @ApiModelProperty(value = "上架时间")
    private Long onSaleTime;

    @ApiModelProperty(value = "下架时间")
    private Long offShelfTime;

    @ApiModelProperty(value = "产品介绍")
    private String introduction;

    @ApiModelProperty(value = "实例照片")
    private String picPath;

    @ApiModelProperty(value = "推荐标识，0:否，1:是")
    private Integer recommendSign;

    @ApiModelProperty(value = "商品售价")
    private BigDecimal price;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @ApiModelProperty(value = "提交人员")
    private Long submitter;

    @ApiModelProperty(value = "审核人员")
    private Long auditor;

    @ApiModelProperty(value = "审核意见")
    private String auditOpinion;

    @ApiModelProperty(value = "更新人员")
    private Long updateUser;

    @ApiModelProperty(value = "审核状态: 0-已通过  1-未通过 2-审核中")
    private Integer auditStarus;

    @ApiModelProperty(value = "删除标记: 0-未删除 1-已删除")
    private Integer deleteSign;

    @ApiModelProperty(value = "更新时间")
    private Long updateTime;


}
