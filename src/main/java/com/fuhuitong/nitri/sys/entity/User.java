package com.fuhuitong.nitri.sys.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @Author Wang
 * @Date 2019/4/19 0019 10:34
 **/

@Data
public class User implements Serializable {

    @ApiModelProperty(value = "用户名", required = true)
    @Size(min = 4, max = 20)
    public String username;
    @ApiModelProperty(value = "密码", required = true)
    @Size(min = 4, max = 20)
    public String password;









}

