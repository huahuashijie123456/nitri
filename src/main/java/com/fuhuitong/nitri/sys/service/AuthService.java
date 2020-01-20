package com.fuhuitong.nitri.sys.service;

import com.fuhuitong.nitri.sys.entity.ResponseUserToken;
import com.fuhuitong.nitri.sys.entity.UserDetail;

/**
 * @Author Wang
 * @Date 2019/4/19 0019 10:33
 **/
public interface AuthService {
    /**
     * 注册用户
     *
     * @param userDetail
     * @return
     */
    UserDetail register(UserDetail userDetail);




    /**
     * 登陆
     *
     * @param username
     * @param password
     * @return
     */
    ResponseUserToken login(String username, String password);


    ResponseUserToken mobileLogin(String username, String password);


    /*    ResponseUserToken mobileResisterOrLogin(String username,String password);*/

    /**
     * 登出
     *
     * @param token
     */
    void logout(String token);


    void logoutAll();

    /**
     * 刷新Token
     *
     * @param oldToken
     * @return
     */
    ResponseUserToken refresh(String oldToken);

    /**
     * 根据Token获取用户信息
     *
     * @param token
     * @return
     */
    UserDetail getUserByToken(String token);
}

