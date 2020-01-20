package com.fuhuitong.nitri.xgd.queryObject;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 栏目管理
 * </p>
 *
 * @author wwj
 * @since 2019-10-09
 */
@Data
@ApiModel(value="NitriSite对象", description="栏目管理")
public class NitriSiteQo implements Serializable {

    private static final long serialVersionUID = -6340185550609013143L;
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "栏目中文名称，前端需根据栏目名称将栏目下内容调取至前端对应板块")
    private String name;

    @ApiModelProperty(value = "栏目标识。栏目统一标识。")
    private String nid;

    @ApiModelProperty(value = "栏目状态，0-隐藏，1-显示")
    private Integer status;

    @ApiModelProperty(value = "页面展示类型，1-列表，2-单页，3-站内链接，4-站外链接。")
    private Integer typeSign;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "显示个数。文章内容展示个数")
    private Integer size;

    /**
     * 记录子栏目
     */
    @TableField(exist = false)
    private List<NitriSiteQo> subSites;

}
