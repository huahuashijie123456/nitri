package com.fuhuitong.nitri.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @Author Wang
 * @Date 2019/4/27 0027 13:08
 **/
public class RequestHolder {

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }


    public static String getRequestPayload() {
        HttpServletRequest req=getHttpServletRequest();
        StringBuilder sb = new StringBuilder();
        try(BufferedReader reader = req.getReader();) {
            char[]buff = new char[1024];
            int len;
            while((len = reader.read(buff)) != -1) {
                sb.append(buff,0, len);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}

