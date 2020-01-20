package com.fuhuitong.nitri.xgd.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 采购纪录
 * </p>
 *
 * @author wwj
 * @since 2019-10-09
 */
@Data
@ApiModel(value="NitriPurchaseRecord对象", description="采购纪录")
public class NitriPurchaseRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "采购数量")
    private Long purchaseQuantity;

    @ApiModelProperty(value = "状态: 0-待发货 1-已发货")
    private Integer status;

    @ApiModelProperty(value = "收货人姓名。前端客户收货信息填写处获取")
    private String userName;

    @ApiModelProperty(value = "收货人手机号码。")
    private String mobilePhoneNo;

    @ApiModelProperty(value = "收货地址。")
    private String shippingAddress;

    @ApiModelProperty(value = "快递公司。后台工作人员添加")
    private String expressCompany;

    @ApiModelProperty(value = "快递单号。后台工作人员添加")
    private String expressNo;

    @ApiModelProperty(value = "采购时间。客户前端完成交易时间")
    private Long buyTime;

    @ApiModelProperty(value = "发货时间。后台工作人员发货时间")
    private Long shippingTime;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @ApiModelProperty(value = "更新时间")
    private Long updateTime;

    @ApiModelProperty(value = "删除标记:0-未删除 1-已删除")
    private Integer deleteSign;


}
