package com.fuhuitong.nitri.xgd.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单
 * </p>
 *
 * @author wwj
 * @since 2019-10-09
 */
@Data
@ApiModel(value="NitriOrder对象", description="订单")
public class NitriOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "产品编号")
    private Long productId;

    @ApiModelProperty(value = "下单时间")
    private Long orderTime;

    @ApiModelProperty(value = "订单状态: 0:待发货 1-已发货")
    private Integer orderStatus;

    @ApiModelProperty(value = "物流单号")
    private String logistics;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @ApiModelProperty(value = "更新时间")
    private Long updateTime;

    @ApiModelProperty(value = "删除标记: 0-未删除 1-已删除")
    private Integer deleteSign;


}
