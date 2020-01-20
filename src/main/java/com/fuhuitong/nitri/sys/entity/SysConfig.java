package com.fuhuitong.nitri.sys.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author wwj
 * @since 2019-09-23
 */
@Data
@NoArgsConstructor
@ApiModel(value="SysConfig", description="")
public class SysConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "pk")
    private String id;

    @ApiModelProperty(value = "配置名称")
    private String configName;

    @ApiModelProperty(value = "配置编号")
    private String configCode;

    @ApiModelProperty(value = "配置值")
    private String configValue;

    @ApiModelProperty(value = "配置描述")
    private String configDesc;

    @ApiModelProperty(value = "配置状态  0：禁用；1：启用")
    private String configState;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "修改时间")
    private Long updateTime;

    @ApiModelProperty(value = "修改人")
    private String updateUser;

    @ApiModelProperty(value = "删除标记    0未删除  1删除")
    private Integer deleteSign;


}
