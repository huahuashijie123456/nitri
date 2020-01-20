package com.fuhuitong.nitri.sys.entity;

import com.fuhuitong.nitri.common.utils.Excel.ExcelField;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author Wang
 * @Date 2019/4/19 0019 10:14
 **/

@Data
@TableName("user")
@NoArgsConstructor
@JsonIgnoreProperties(value = {"qx","password","payPassword"})
public class UserDetail  implements UserDetails {

    private static final long serialVersionUID = 1L;


    @TableId(type = IdType.INPUT)
    private String id;


    private String name;

    /**
     * 用户名
     */
    @ExcelField(name = "用户名", sort = 5)
    private String username;

    /**
     * 密码
     */

    @TableField(strategy = FieldStrategy.NOT_EMPTY)
    private String password;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Long updateTime;


    /**
     * 是否实名
     */
    private Integer isRealname;

    /**
     * 是否锁定
     */
    private Integer isLocked;


    private BigDecimal money;

    private String idCard;

    /**
     * 支付密码
     */
    private String payPassword;

    /**
     * 用户类型 0 app用户 1 后台用户 2 渠道用户
     */
    private Integer userType;

    /**
     * 渠道ID
     */
    private String channelId;

    /**
     * 模板ID
     */
    private String templateId;

    /**
     * 用户照片
     */
    private String userPhoto;

    /**
     * 邀请链接
     */
    private String invitationLink;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    /**
     * 角色
     */
    @TableField(exist = false)
    private Role role;
    /**
     * 规则
     */
//    @TableField(exist = false)
//    private ChannelRule channelRule;

    /**
     * 模板名称
     */
    @TableField(exist = false)
    private String tempName;

    @TableField(exist = false)
    private String roleId;

    @TableField(exist = false)
    @ExcelField(name = "角色", sort = 10)
    private String roleName;


    @TableField(exist = false)
    private Collection authorities;

    @TableField(exist = false)
    private List<String> qx;

    //返回分配给用户的角色列表
    @Override
    @JSONField(serialize = false)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (role != null) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        if (qx != null) {
            qx.stream().map(SimpleGrantedAuthority::new).forEach(authorities::add);
        }
        return authorities;
    }


    public UserDetail(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public UserDetail(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
