package com.fuhuitong.nitri.xgd.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 职位详情
 * </p>
 *
 * @author wwj
 * @since 2019-10-09
 */
@Data
@ApiModel(value="NitriJob对象", description="职位详情")
public class NitriJob implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "企业编号")
    private Long companyId;

    @ApiModelProperty(value = "职位名称")
    private String title;

    @ApiModelProperty(value = "招聘人数")
    private Integer hireNo;

    @ApiModelProperty(value = "招聘状态: 0-进行中  1-已结束")
    private Integer jobStatus;

    @ApiModelProperty(value = "薪资待遇")
    private Integer salary;

    @ApiModelProperty(value = "工作经验")
    private Integer experience;

    @ApiModelProperty(value = "学历")
    private Integer education;

    @ApiModelProperty(value = "工作类型")
    private Integer type;

    @ApiModelProperty(value = "五险一金")
    private Integer insurance;

    @ApiModelProperty(value = "绩效奖励")
    private Integer performanceRewards;

    @ApiModelProperty(value = "年终分红")
    private Integer bonus;

    @ApiModelProperty(value = "企业区域编号")
    private Long areaId;

    @ApiModelProperty(value = "企业详细地址")
    private String detailedAddress;

    @ApiModelProperty(value = "岗位职责")
    private String responsibilities;

    @ApiModelProperty(value = "任职要求")
    private String qualifications;

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
