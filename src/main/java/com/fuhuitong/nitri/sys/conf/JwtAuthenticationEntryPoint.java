package com.fuhuitong.nitri.sys.conf;

import com.fuhuitong.nitri.common.entity.ResultJson;
import com.fuhuitong.nitri.sys.enums.ResultCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * @Author Wang
 * @Date 2019/4/19 0019 10:20
 **/
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        //验证为未登陆状态会进入此方法，认证错误
        System.out.println("认证失败：" + authException.getMessage());
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        String body = "";
        if ("User account is locked".equals(authException.getMessage())) {
            body = ResultJson.failure(ResultCode.ACCOUNTLOCKED, authException.getMessage()).toString();
        } else if ("User is disabled".equals(authException.getMessage())) {
            body = ResultJson.failure(ResultCode.NOTACTIVATED, authException.getMessage()).toString();
        } else {
            body = ResultJson.failure(ResultCode.UNAUTHORIZED, authException.getMessage()).toString();
        }
        printWriter.write(body);
        printWriter.flush();
    }
}

