package com.fuhuitong.nitri.common.utils;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取UA
 *
 * @Author Wang
 * @Date 2019/5/5 0005 16:50
 **/
public class UserAgentUtils {
    public static String getCurrentUserAgent() {
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        Browser browser = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getBrowser();
        Version version = browser.getVersion(request.getHeader("User-Agent"));
        String info = browser.getName() + "/" + version.getVersion();
        return info;
    }
}
