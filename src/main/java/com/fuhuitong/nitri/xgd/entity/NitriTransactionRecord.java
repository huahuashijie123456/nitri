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
 * 交易记录
 * </p>
 *
 * @author wwj
 * @since 2019-10-09
 */
@Data
@ApiModel(value="NitriTransactionRecord对象", description="交易记录")
public class NitriTransactionRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "交易流水号。交易第三方反馈流水号")
    private String transactionNo;

    @ApiModelProperty(value = "客户姓名。客户绑卡时输入的真实姓名")
    private String userName;

    @ApiModelProperty(value = "手机号码。客户绑卡时，所绑定银行卡预留手机号码，用于接收短信验证码。")
    private String mobilePhoneNo;

    @ApiModelProperty(value = "交易金额。第三方扣款金额。")
    private BigDecimal amount;

    @ApiModelProperty(value = "交易类型: 0-支付宝 1:微信 2:银行卡 3:其它")
    private Integer type;

    @ApiModelProperty(value = "交易时间。由第三方完成交易后反馈。")
    private Long buyTime;

    @ApiModelProperty(value = "手续费")
    private BigDecimal serviceFee;

    @ApiModelProperty(value = "交易状态: 0-处理中 1-成功 2:失败")
    private Integer status;

    @ApiModelProperty(value = "备注。交易失败备注第三方返回结果")
    private String 备注;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @ApiModelProperty(value = "更新时间")
    private Long updateTime;

    @ApiModelProperty(value = "删除标记:0-未删除 1-已删除")
    private Integer deleteSign;


}
