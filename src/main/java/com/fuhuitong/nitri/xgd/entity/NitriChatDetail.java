package com.fuhuitong.nitri.xgd.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 聊天详情表
 * </p>
 *
 * @author wwj
 * @since 2019-10-09
 */
@Data
@ApiModel(value="NitriChatDetail对象", description="聊天详情表")
public class NitriChatDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "聊天主表编号")
    private Long chatId;

    @ApiModelProperty(value = "用户编号")
    private Long userId;

    @ApiModelProperty(value = "聊天内容")
    private String content;

    @ApiModelProperty(value = "发送时间")
    private Long sendTime;


}
