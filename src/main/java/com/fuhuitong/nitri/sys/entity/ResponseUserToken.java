package com.fuhuitong.nitri.sys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author Wang
 * @Date 2019/4/19 0019 10:33
 **/
@Data
@AllArgsConstructor
public class ResponseUserToken {
    private String token;
    private UserDetail userDetail;
}
