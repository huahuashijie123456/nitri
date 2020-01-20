package com.fuhuitong.nitri.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuhuitong.nitri.common.entity.ResultJson;
import com.fuhuitong.nitri.common.utils.ContextHolder;
import com.fuhuitong.nitri.common.utils.RedisUtil;
import com.fuhuitong.nitri.common.utils.SnowflakeIdWorker;
import com.fuhuitong.nitri.common.utils.sms.SendSms;
import com.fuhuitong.nitri.sys.entity.ResponseUserToken;
import com.fuhuitong.nitri.sys.entity.Role;
import com.fuhuitong.nitri.sys.entity.User;
import com.fuhuitong.nitri.sys.entity.UserDetail;
import com.fuhuitong.nitri.sys.enums.ResultCode;
import com.fuhuitong.nitri.sys.mapper.UserDetailMapper;
import com.fuhuitong.nitri.sys.service.AuthService;
import com.fuhuitong.nitri.sys.service.UserDetailServiceImpl;
import com.fuhuitong.nitri.sys.service.UserRoleServiceImpl;;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.base.Captcha;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @Author Wang
 * @Date 2019/4/19 0019 14:21
 **/
@RestController
@Api(description = "登陆注册及刷新token")
@RequestMapping("/auth")
public class AuthController {
    @Value("${jwt.header}")
    private String tokenHeader;

    private final AuthService authService;
    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private UserDetailMapper userMapper;



    @Autowired
    private RedisUtil redisUtil;


    @Value("${SMS_VERIFICATION_TIME}")
    private Long SMS_VERIFICATION_TIME;

    @Autowired
    private UserRoleServiceImpl userRoleService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login")
    @ApiOperation(value = "登陆", notes = "登陆成功返回token,测试账号:admin,123456")
    public ResultJson<ResponseUserToken> login(User user) {
        final ResponseUserToken response = authService.login(user.getUsername(), user.getPassword());
        return ResultJson.ok(response);
    }

    public ResultJson sign(@Valid User user) {
        if (StringUtils.isAnyBlank(user.getUsername(), user.getPassword())) {
            return ResultJson.failure(ResultCode.BAD_REQUEST);
        }
        UserDetail userDetail = new UserDetail(user.getUsername(), user.getPassword(), Role.builder().id("1").build());
        userDetail.setMoney(new BigDecimal(0));
        int number=new Random().nextInt(9999);
        userDetail.setIdCard("622202****00746"+number);
        return ResultJson.ok(authService.register(userDetail));
    }



    @ApiOperation(value = "手机注册发送短信")
    @PostMapping(value = "/smsSend")
    public ResultJson smsSign(String mobile){
        //设置信息模板为 注册短信模板
        String tplid ="179997";
        if(StringUtil.isNullOrEmpty(mobile)){
            return ResultJson.failure(ResultCode.BAD_REQUEST);
        }
        else {
            LambdaQueryWrapper<UserDetail> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserDetail::getUsername,mobile);
            List<UserDetail> list = userMapper.selectList(wrapper);
            if(list.size()>0){
                return ResultJson.failure(ResultCode.REGISTER_ERROR);
            }
            //调用金融云短信发送方法
            String code=SendSms.juhesendMsg(mobile,tplid);
            redisUtil.setExpire(mobile,code+"",SMS_VERIFICATION_TIME);
            return ResultJson.ok();
        }
    }

    @ApiOperation(value = "验证验证码是否正确")
    @PostMapping(value = "/verificationCode")
    public ResultJson verificationCode (String mobile,String code){
        if(StringUtil.isNullOrEmpty(mobile) ||  StringUtil.isNullOrEmpty(code)){
            return ResultJson.failure(ResultCode.BAD_REQUEST);
        }else{
            Object yzm=redisUtil.get(mobile);
            if(yzm==null){
                return ResultJson.failure(ResultCode.SMS_NOTEXIST);
            } else if(!code.equals(yzm.toString())){
                return ResultJson.failure(ResultCode.SMS_REQUEST);
            }else{
                return ResultJson.ok(true);
            }
        }
    }


    @ApiOperation(value = "手机注册")
    @PostMapping(value = "/smsRegister")
    public ResultJson smsRegister(String mobile,String password,String code){
        if(StringUtil.isNullOrEmpty(mobile) || StringUtil.isNullOrEmpty(password) || StringUtil.isNullOrEmpty(code)){
            return ResultJson.failure(ResultCode.BAD_REQUEST);
        }
        else {
            Object yzm=redisUtil.get(mobile);
            if(yzm==null){
                return ResultJson.failure(ResultCode.SMS_NOTEXIST);
            }
            else if(!code.equals(yzm.toString())){
                return ResultJson.failure(ResultCode.SMS_REQUEST);
            }
            else {
                User u = new User();
                u.setPassword(password);
                u.setUsername(mobile);
                return sign(u);
            }
        }
    }

    @ApiOperation(value = "忘记密码发送验证码")
    @PostMapping(value = "/forgetPasswordSms")
    public ResultJson forgetPasswordSms(String mobile){
        //设置信息模板为 短信验证码模板
        String tplid ="179999";
        if(StringUtil.isNullOrEmpty(mobile)){
            return ResultJson.failure(ResultCode.BAD_REQUEST);
        }
        else {
            LambdaQueryWrapper<UserDetail> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserDetail::getUsername,mobile);
            List<UserDetail> list = userMapper.selectList(wrapper);
            if(list.size()==0){
                return ResultJson.failure(ResultCode.NOT_FOUND,"此手机号未注册，请先注册");
            }
            //调用金融云短信发送方法
            String code=SendSms.juhesendMsg(mobile,tplid);
            redisUtil.setExpire(mobile,code+"",SMS_VERIFICATION_TIME);
            return ResultJson.ok();
        }
    }

    @ApiOperation(value = "忘记密码")
    @PostMapping(value = "/forgetPassword")
    public ResultJson forgetPassword(String mobile,String password,String code){
        if(StringUtil.isNullOrEmpty(mobile) || StringUtil.isNullOrEmpty(password) || StringUtil.isNullOrEmpty(code)){
            return ResultJson.failure(ResultCode.BAD_REQUEST);
        }else{
            Object yzm=redisUtil.get(mobile);
            if(yzm==null){
                return ResultJson.failure(ResultCode.SMS_NOTEXIST);
            }
            else if(!code.equals(yzm.toString())){
                return ResultJson.failure(ResultCode.SMS_REQUEST);
            }
            LambdaQueryWrapper<UserDetail> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserDetail::getUsername,mobile);
            List<UserDetail> list = userMapper.selectList(wrapper);
            if(list.size()==0){
                return ResultJson.failure(ResultCode.NOT_FOUND,"此手机号未注册，请先注册");
            }
            UserDetail userDetail = list.get(0);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            userDetail.setPassword(encoder.encode(password));
            if( userMapper.updateById(userDetail)>0){
                redisUtil.del(mobile);
                return  ResultJson.ok();
            }
            return ResultJson.failure(ResultCode.SERVER_ERROR);
        }

    }









    @PostMapping(value = "/mobileLogin")
    @ApiOperation(value = "H5登陆", notes = "登陆成功返回token,测试账号:customer,123456")
    public ResultJson<ResponseUserToken> mobileLogin(
            @Valid @RequestBody User user) {
        final ResponseUserToken response = authService.mobileLogin(user.getUsername(), user.getPassword());
        return ResultJson.ok(response);
    }



//        @PostMapping(value = "/mobileRegister")
//        @ApiOperation(value = "H5注册", notes = "")
//        public ResultJson<ResponseUserToken> mobileRegister(String username,String password){
//            UserDetail userDetail=new UserDetail(null,username,password);
//            return ResultJson.ok(authService.mobileRegister(userDetail));
//        }



      /*  @PostMapping(value = "/mobileRegisterOrLogin")
        @ApiOperation(value = "H5注册或登陆")
        public ResultJson<ResponseUserToken> mobileRegisterOrLogin(@RequestBody String token){
            if(StringUtil.isNullOrEmpty(token)){
               return ResultJson.failure(ResultCode.BAD_REQUEST);
            }
            Map map=new HashMap();
            token=JSONObject.parseObject(token).getString("token");
            map.put("token",token);
          //  map.put("token","eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJDVVNUMjAxOTA3MTIxNTQzMjE5NTU4MCIsImF1ZCI6IlJPTEVfQ1VTVE9NRVJfTVlHIiwiaXNzIjoibHhsIiwiaWF0IjoxNTYyOTE3NDAyLCJleHAiOjE1NjM1MjIyMDIsImF1dGhvcml0aWVzIjpbIjAwMDAwIl0sImFwcGlkcyI6WyJhcHAyMDE5MDQtMDIxIiwiYXBwMjAxOTA0LTAxNiIsImFwcDIwMTkwNC0wMTkiLCJhcHAyMDE5MDQtMDI2Il19.x9l1Uf49RqxNVoEDb056xN7QZesFyVfdoVZffI5AmO6iBpGLIO_RWWuZ7spGwvQfxoLIaGWiCBCLIUIm-ara7Q");
            System.out.println("token--------->"+token);
            String str=HttpClientPoolUtil.execute("http://login.juxinck.net/api/user/someInfo",map);
            System.out.println("str--------->"+str);
            String phone=JSONObject.parseObject(str).getJSONObject("data").getString("phone");

            final ResponseUserToken response = authService.mobileResisterOrLogin(phone,"parse");
            return ResultJson.ok(response);
        }*/





    @PostMapping(value = "/logout")
    @ApiOperation(value = "登出", notes = "退出登陆")
    public ResultJson logout(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        if (token == null) {
            return ResultJson.failure(ResultCode.UNAUTHORIZED);
        }
        authService.logout(token);
        return ResultJson.ok();
    }


    @PostMapping(value = "/logoutAll")
    @ApiOperation(value = "登出所有同名账号", notes = "注销所有同名账号")
    public ResultJson logoutAll(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        if (token == null) {
            return ResultJson.failure(ResultCode.UNAUTHORIZED);
        }
        authService.logoutAll();
        return ResultJson.ok();
    }


    @PostMapping(value = "/updatePassword")
    @ApiOperation(value = "修改密码", notes = "修改密码")
    public ResultJson checkPassword(String password, String newpassword) {
        if (StringUtil.isNullOrEmpty(newpassword) || StringUtil.isNullOrEmpty(password)) {
            return ResultJson.failure(ResultCode.BAD_REQUEST);
        }
        UserDetail userDetail = ContextHolder.getCurrentUser();
        if (userDetailService.checkPassword(password, userDetail.getPassword())) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            userDetail.setPassword(encoder.encode(newpassword));
            if (userDetailService.updateUser(userDetail) > 0) {
                authService.logoutAll();
                return ResultJson.ok();
            } else {
                ResultJson.failure(ResultCode.SERVER_ERROR);
            }
        }
        return ResultJson.failure(ResultCode.USEEOLDPASSWORD_ERROR);
    }


    @GetMapping(value = "/user")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @ApiOperation(value = "根据token获取用户信息", notes = "根据token获取用户信息")
    //@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
    public ResultJson getUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        if (token == null) {
            return ResultJson.failure(ResultCode.UNAUTHORIZED);
        }
        UserDetail userDetail = authService.getUserByToken(token);
        return ResultJson.ok(userDetail);
    }

      /*  @PostMapping(value = "/sign")
        @ApiOperation(value = "用户注册")
        public ResultJson sign(@RequestBody User user) {
            if (StringUtils.isAnyBlank(user.getUsername(), user.getPassword())) {
                return ResultJson.failure(ResultCode.BAD_REQUEST);
            }
            UserDetail userDetail = new UserDetail(user.getUsername(), user.getPassword(), Role.builder().id("1").build());
            return ResultJson.ok(authService.register(userDetail));
        }*/
    //    @GetMapping(value = "refresh")
    //    @ApiOperation(value = "刷新token")
    //    public ResultJson refreshAndGetAuthenticationToken(
    //            HttpServletRequest request){
    //        String token = request.getHeader(tokenHeader);
    //        ResponseUserToken response = authService.refresh(token);
    //        if(response == null) {
    //            return ResultJson.failure(ResultCode.BAD_REQUEST, "token无效");
    //        } else {
    //            return ResultJson.ok(response);
    //        }
    //    }


    ////////////////////////////////////////后台用户管理//////////////////////////////////////////////////////

    @PostMapping(value = "/loginadmin")
    @ApiOperation(value = "后台管理登陆", notes = "登陆成功返回token,测试账号:admin,123456")
    public ResultJson<ResponseUserToken> loginadmin(User user) {
        UserDetail userDetail = userMapper.selectOne(new LambdaQueryWrapper<UserDetail>().eq(UserDetail::getUsername,user.getUsername()));
        if(userDetail.getUserType()==1){
            final ResponseUserToken response = authService.login(user.getUsername(), user.getPassword());
            return ResultJson.ok(response);
        }
        return ResultJson.failure(ResultCode.MOBILEFORBIDDEN);
    }




    @ApiOperation(value = "添加/修改后台用户", notes = "")
    @PostMapping(value = "/addStaff")
    public ResultJson addStaff(String id,String username,String password,String name,String roleId) {
        if (StringUtils.isAnyBlank(username,roleId)) {
            return ResultJson.failure(ResultCode.BAD_REQUEST);
        }
        LambdaQueryWrapper<UserDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDetail::getUsername,username);
        wrapper.eq(UserDetail::getUserType,"1"); // 0app用户 2后台用户
        if(StringUtil.isNullOrEmpty(id)) {
            List<UserDetail> list = userMapper.selectList(wrapper);
            if (list.size() > 0) {
                return ResultJson.failure(ResultCode.ACCOUNT_ERROR);
            }
        }
        if(userRoleService.addStaff(id,username,password,name,roleId)>0){
            return ResultJson.ok();
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }



    @ApiOperation(value = "删除后台用户", notes = "")
    @PostMapping(value = "/delStaff")
    public ResultJson delStaff(String id) {
        if(userRoleService.delStaff(id)>0){
            return ResultJson.ok();
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST);
    }

  ///////////////////////////////////////////pc版///////////////////////////////////////////////////////////

    private static SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 0);

    /**
     * 获取验证码（Gif版本）
     *
     * @param response
     */
    @ApiOperation(value = "获取验证码")
    @RequestMapping(value = "getGifCode", method = RequestMethod.GET)
    public ResultJson  getGifCode(HttpServletResponse response) {
        String uuid = snowflakeIdWorker.nextId()+"";
        try {

            GifCaptcha captcha = new GifCaptcha(130, 48);
            //数字和大写字母
            captcha.setCharType(Captcha.TYPE_NUM_AND_UPPER);
            Map map = new HashMap();
            String img = captcha.toBase64();
            String verCode = captcha.text().toLowerCase();
            redisUtil.setExpire(uuid,verCode,2*60);
            map.put("base64",img);
            map.put("uuid",uuid);
            return  ResultJson.ok(map);
        } catch (Exception e) {
            return  ResultJson.failure(ResultCode.COUPONS_EXIST);
        }

    }


    @ApiOperation(value = "pc端发送验手机证码")
    @PostMapping(value = "/pcSendMessage")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="mobile",value = "手机号",paramType = "query",dataType="String",required = true),
            @ApiImplicitParam(name ="code",value = "图片验证码",paramType = "query",dataType="String",required = true),
            @ApiImplicitParam(name ="uuid",value = "uuid",paramType = "query",dataType="String",required = true)
    })
    public ResultJson pcSendMessage(String mobile,String code,String uuid){
            if(StringUtils.isNotBlank(mobile) && StringUtils.isNotBlank(code)){
                Object yzm=redisUtil.get(uuid);
                if(yzm==null){
                    return ResultJson.failure(ResultCode.SMS_NOTEXIST.getCode(),"验证码已过期");
                }else if(!yzm.equals(code.trim().toLowerCase())){
                    return ResultJson.failure(ResultCode.SMS_REQUEST);
                }else{
                    LambdaQueryWrapper<UserDetail> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(UserDetail::getUsername,mobile);
                    List<UserDetail> list = userMapper.selectList(wrapper);
                    if(list.size()>0){
                        return ResultJson.failure(ResultCode.REGISTER_ERROR);
                    }
                    //调用金融云短信发送方法
                    //设置信息模板为 注册短信模板
                    String tplid ="179997";
                    String mobileCode=SendSms.juhesendMsg(mobile,tplid);
                    redisUtil.del(uuid);
                    redisUtil.setExpire(mobile,mobileCode+"",SMS_VERIFICATION_TIME);
                    return ResultJson.ok();
                }
            }else{
                return ResultJson.failure(ResultCode.BAD_REQUEST);
            }
    }

    @ApiOperation(value = "pc端忘记密码发送验证码")
    @PostMapping(value = "/pcForgetPasswordSms")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="mobile",value = "手机号",paramType = "query",dataType="String",required = true),
            @ApiImplicitParam(name ="code",value = "图片验证码",paramType = "query",dataType="String",required = true),
            @ApiImplicitParam(name ="uuid",value = "uuid",paramType = "query",dataType="String",required = true)
    })
    public ResultJson pcForgetPasswordSms(String mobile,String code,String uuid){
        if(StringUtils.isNotBlank(mobile) && StringUtils.isNotBlank(code)){
            Object yzm=redisUtil.get(uuid);
            if(yzm==null){
                return ResultJson.failure(ResultCode.SMS_NOTEXIST.getCode(),"验证码已过期");
            }else if(!yzm.equals(code.trim().toLowerCase())){
                return ResultJson.failure(ResultCode.SMS_REQUEST);
            }else{
                String tplid ="179997";
                LambdaQueryWrapper<UserDetail> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(UserDetail::getUsername,mobile);
                List<UserDetail> list = userMapper.selectList(wrapper);
                if(list.size()==0){
                    return ResultJson.failure(ResultCode.NOT_FOUND,"此手机号未注册，请先注册");
                }
                //调用金融云短信发送方法
                String mobileCode=SendSms.juhesendMsg(mobile,tplid);
                redisUtil.setExpire(mobile,mobileCode+"",SMS_VERIFICATION_TIME);
                return ResultJson.ok();

            }
        }else{
            return ResultJson.failure(ResultCode.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "pc端注册")
    @PostMapping(value = "/pcRegister")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="mobile",value = "手机号",paramType = "query",dataType="String",required = true),
            @ApiImplicitParam(name ="password",value = "密码",paramType = "query",dataType="String",required = true),
            @ApiImplicitParam(name ="mobileCode",value = "手机验证码",paramType = "query",dataType="String",required = true)
    })
    public ResultJson pcRegister(String mobile,String password,String mobileCode){
        if(StringUtil.isNullOrEmpty(mobile) || StringUtil.isNullOrEmpty(password) || StringUtil.isNullOrEmpty(mobileCode)){
            return ResultJson.failure(ResultCode.BAD_REQUEST);
        }
        else {
            Object yzm=redisUtil.get(mobile);
            if(yzm==null){
                return ResultJson.failure(ResultCode.SMS_NOTEXIST);
            }
            else if(!mobileCode.equals(yzm.toString())){
                return ResultJson.failure(ResultCode.SMS_REQUEST);
            }
            else {
                User u = new User();
                u.setPassword(password);
                u.setUsername(mobile);
                return sign(u);
            }
        }
    }



}
