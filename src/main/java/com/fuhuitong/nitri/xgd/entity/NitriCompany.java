package com.fuhuitong.nitri.xgd.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 企业
 * </p>
 *
 * @author wwj
 * @since 2019-10-09
 */
@Data
@ApiModel(value="NitriCompany对象", description="企业")
public class NitriCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "企业简称")
    private String referName;

    @ApiModelProperty(value = "企业全称")
    private String fullName;

    @ApiModelProperty(value = "企业人数")
    private Integer employees;

    @ApiModelProperty(value = "企业法人")
    private String legalPerson;

    @ApiModelProperty(value = "所在位置")
    private String areaId;

    @ApiModelProperty(value = "详细地址")
    private String detailedAddress;

    @ApiModelProperty(value = "联系电话")
    private String telphone;

    @ApiModelProperty(value = "主营业务")
    private String mainBusiness;

    @ApiModelProperty(value = "产品图片")
    private String productPic;

    @ApiModelProperty(value = "企业简介")
    private String profile;

    @ApiModelProperty(value = "企业文化")
    private String culture;

    @ApiModelProperty(value = "企业愿景")
    private String vision;

    @ApiModelProperty(value = "企业口号")
    private String slogan;

    @ApiModelProperty(value = "入驻描述")
    private String enterDescription;

    @ApiModelProperty(value = "提交人员")
    private String submitter;

    @ApiModelProperty(value = "审核人员")
    private String auditor;

    @ApiModelProperty(value = "审核意见")
    private String auditOpinion;

    @ApiModelProperty(value = "审核状态: 0-已通过  1-未通过 2-审核中")
    private Integer auditStarus;






    @ApiModelProperty(value = "企业类型 0：院属企业 1：孵化企业")
    private Integer companyType;

    @ApiModelProperty(value = "是否上架 0：下架 1：下架")
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人")
    private Long createUser;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Long updateTime;

    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "更新人员")
    private Long updateUser;

    @TableLogic
    @ApiModelProperty(value = "删除标记:0-未删除 1-已删除")
    private Integer deleteSign;

    @ApiModelProperty(value = "是否推荐 0：是 1：否")
    private Integer recommendedSign;



    @ApiModelProperty(value = "起始日期")
    @TableField(exist = false)
    private Long startTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "结束日期")
    private Long endTime;
}
