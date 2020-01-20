package com.fuhuitong.nitri.sys.service;


import com.fuhuitong.nitri.common.entity.ResultJson;
import com.fuhuitong.nitri.sys.entity.*;
import com.fuhuitong.nitri.sys.enums.ResultCode;
import com.fuhuitong.nitri.sys.exception.AsException;
import com.fuhuitong.nitri.sys.mapper.AuthMapper;
import com.fuhuitong.nitri.common.utils.ContextHolder;
import com.fuhuitong.nitri.common.utils.JwtUtils;
import com.fuhuitong.nitri.common.utils.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @Author Wang
 * @Date 2019/4/19 0019 10:36
 **/
@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtTokenUtil;
    private final AuthMapper authMapper;

    @Autowired
    private UserRoleServiceImpl userRoleService;
    @Autowired
    private MenuServiceImpl menuService;
    @Autowired
    private RedisUtil redisUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

   /* @Autowired
    private CustomerMapper customerMapper;*/

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, @Qualifier("CustomUserDetailsService") UserDetailsService userDetailsService, JwtUtils jwtTokenUtil, AuthMapper authMapper) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authMapper = authMapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserDetail register(UserDetail userDetail) {
        final String username = userDetail.getUsername();
        if (authMapper.findByUsername(username) != null) {
            throw new AsException(ResultJson.failure(ResultCode.REGISTER_ERROR));
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userDetail.getPassword();
        userDetail.setPassword(encoder.encode(rawPassword));
        //  userDetail.setLastPasswordResetDate(new Date());
       // authMapper.insertUser(userDetail);
        authMapper.insert(userDetail);
        String roleId=userDetail.getRole().getId();
        Role role = authMapper.findRoleById(roleId);
        userDetail.setRole(role);
        UserRole userRole = new UserRole(userDetail.getId(), roleId);
        userRoleService.addUserRole(userRole);
        //authMapper.insertRole(userDetail.getId(), roleId);
        redisUtil.del(userDetail.getUsername());
        return userDetail;
    }


    @Override
    public ResponseUserToken login(String username, String password) {
        //用户类型 1 商户 2 员工 3客户
        //用户验证
        final Authentication authentication = authenticate(username, password);
        //存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成token
        final UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        List<String> s = menuService.getCurrentPermission(userDetail.getRole().getId());
        userDetail.setQx(s);
       // UserDetail us = new UserDetail();
      /*  authMapper.update(us, new QueryWrapper<UserDetail>().eq("id", userDetail.getId())); */
        final String token = userDetail.getId() + "^" + jwtTokenUtil.generateAccessToken(userDetail);
        jwtTokenUtil.putToken(JSON.toJSONString(userDetail), token);
        return new ResponseUserToken(token, userDetail);
    }




    @Override
    public ResponseUserToken mobileLogin(String username, String password) {
        //用户类型 1 商户 2 员工 3客户
        if (authMapper.selectOne(new QueryWrapper<UserDetail>().eq("username", username).eq("user_type", "3")) == null) {
            throw new AsException(ResultJson.failure(ResultCode.LOGIN_ERROR));
        }
        //用户验证
        final Authentication authentication = authenticate(username, password);
        //存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        String token = userDetail.getId() + "^" + jwtTokenUtil.generateAccessMobileToken(userDetail);
        jwtTokenUtil.putToken(JSON.toJSONString(userDetail), token);
        return new ResponseUserToken(token, userDetail);
    }

  /*  @Override
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserDetail mobileRegister(UserDetail userDetail) {
        final String username = userDetail.getUsername();
        if(authMapper.findByUsername(username)!=null) {
            throw new AsException(ResultJson.failure(ResultCode.REGISTER_ERROR));
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDetail.setUserType("3");
        userDetail.setStatus(1);
        userDetail.setPassword(encoder.encode(userDetail.getPassword()));
        authMapper.insertUser(userDetail);

        */

    /**
     * 这块没考虑多商户 ，写死了一个商户
     *//*
        Customer customer=new Customer();
        if(customerMapper.selectList(new QueryWrapper<Customer>().eq("merchant_id","1cf1a9af06838fa2b49d16c7df0c2c04").eq("phone",username)).size()>0){
                customer.setStatus(1);
                customerMapper.update(customer,new QueryWrapper<>());
        }
        else {
            customer.setPhone(username);
            customer.setName(username);
            customer.setUserId(userDetail.getId());
            customer.setStatus(1);
            customer.setMerchantId("1cf1a9af06838fa2b49d16c7df0c2c04");
            customerMapper.insert(customer);
        }
        return userDetail;
    }
*/




    /*public ResponseUserToken mobileResisterOrLogin(String username,String password) {
        //用户类型 1 商户 2 员工 3客户
        if(authMapper.selectOne(new QueryWrapper<UserDetail>().eq("username",username))==null){ //注册
            UserDetail userDetail=new UserDetail();
            userDetail.setUsername(username);
            userDetail.setPassword(password);
            userDetail.setPhone(username);
            mobileRegister(userDetail);
            return mobileLogin(username,password);
            //throw new AsException(ResultJson.failure(ResultCode.LOGIN_ERROR));
        }
        else {  //登陆
         return mobileLogin(username,password);
        }
    }*/


    @Override
    public void logout(String token) {
        token = token.substring(tokenHead.length());
        //  String userName = jwtTokenUtil.getUsernameFromToken(token);
        jwtTokenUtil.deleteToken(token);
    }


    @Override
    public void logoutAll() {
        String id = ContextHolder.getCurrentUser().getId()+"";
        redisUtil.deleteByPrex(id);
    }


    @Override
    public ResponseUserToken refresh(String oldToken) {
        String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        UserDetail userDetail = (UserDetail) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token, null)) { //userDetail.getLastPasswordResetDate()
            token = jwtTokenUtil.refreshToken(token);
            return new ResponseUserToken(token, userDetail);
        }
        return null;
    }

    @Override
    public UserDetail getUserByToken(String token) {
        token = token.substring(tokenHead.length());
        return jwtTokenUtil.getUserFromToken(token);
    }

    private Authentication authenticate(String username, String password) {
        try {
        //    该方法会去调用
        //userDetailsService.loadUserByUsername()去验证用户名和密码，如果正确，则存储该用户名密码到“security 的 context中”
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new AsException(ResultJson.failure(ResultCode.LOGIN_ERROR, e.getMessage()));
        }
    }









}
