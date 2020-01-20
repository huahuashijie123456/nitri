package com.fuhuitong.nitri.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author Wang
 * @Date 2019/5/10 0010 16:05
 **/
@Data
@NoArgsConstructor
public class SysArea implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 区域编码
     */
    private String areaCode;

    /**
     * 父级编号
     */
    private String parentCode;

    /**
     * 所有父级编号
     */
    private String parentCodes;

    /**
     * 本级排序号（升序）
     */
    private BigDecimal treeSort;

    /**
     * 所有级别排序号
     */
    private String treeSorts;

    /**
     * 是否最末级
     */
    private String treeLeaf;

    /**
     * 层次级别
     */
    private BigDecimal treeLevel;

    /**
     * 全节点名
     */
    private String treeNames;

    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 区域类型
     */
    private String areaType;

    /**
     * 状态（0正常 1删除 2停用）
     */
    private String status;

    /**
     * 热门城市
     */
    private String hotCity;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 拼音ABC
     */
    private String pinyin;

    /**
     * 更新时间
     */

    private LocalDateTime updateTime;

    /**
     * 备注信息
     */
    private String remarks;


    // 子菜单
    @TableField(exist = false)
    private List<SysArea> subs;


}

