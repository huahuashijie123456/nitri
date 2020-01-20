package com.fuhuitong.nitri.xgd.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户实体表
 * </p>
 *
 * @author wwj
 * @since 2019-10-09
 */
@Data
@ApiModel(value="NitriUser对象", description="用户实体表")
public class NitriUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "手机号码")
    private String mobilePhoneNo;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "收货地址")
    private String addressId;

    @ApiModelProperty(value = "银行卡号")
    private String cardNo;

    @ApiModelProperty(value = "用户角色")
    private Long roleId;

    @ApiModelProperty(value = "用户权限")
    private Long permissionId;

    @ApiModelProperty(value = "注册时间")
    private Long registerTime;

    @ApiModelProperty(value = "修改时间")
    private Long updateTime;

    @ApiModelProperty(value = "删除标记: 0-未删除  1-删除")
    private Integer deleteSign;


}
