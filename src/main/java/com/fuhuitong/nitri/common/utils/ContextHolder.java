package com.fuhuitong.nitri.common.utils;

import com.fuhuitong.nitri.common.entity.ResultJson;
import com.fuhuitong.nitri.sys.entity.UserDetail;
import com.fuhuitong.nitri.sys.enums.ResultCode;
import com.fuhuitong.nitri.sys.exception.AsException;
import com.alibaba.fastjson.JSON;
import com.fuhuitong.nitri.sys.service.SysConfigServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author Wang
 * @Date 2019/4/23 0023 18:06
 **/
@Component
public class ContextHolder {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SysConfigServiceImpl sysConfigService;
    public static ContextHolder logTool;

    @PostConstruct
    public void init() {
        logTool = this;
    }

    public static UserDetail getCurrentUser() {
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        String auth_token = request.getHeader("Authorization");
        final String auth_token_start = "Bearer ";
        if (StringUtils.isNotEmpty(auth_token) && auth_token.startsWith(auth_token_start)) {
            auth_token = auth_token.substring(auth_token_start.length());
        }
        UserDetail userDetails = null;
        try {
            userDetails = JSON.parseObject(logTool.redisUtil.get(auth_token).toString(), UserDetail.class);
        } catch (Exception e) {
            throw new AsException(ResultJson.failure(ResultCode.UNAUTHORIZED));
        }
        return userDetails;
    }


    public static String getPayChannel(){
        return logTool.sysConfigService.selectOneConfig("sys_pay_channel").getConfigValue();
    }

}
