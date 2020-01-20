package com.fuhuitong.nitri.sys.controller;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuhuitong.nitri.common.entity.ResultJson;
import com.fuhuitong.nitri.common.utils.ContextHolder;
import com.fuhuitong.nitri.common.utils.RedisUtil;
import com.fuhuitong.nitri.common.utils.sms.SendSms;
import com.fuhuitong.nitri.sys.entity.UserDetail;
import com.fuhuitong.nitri.sys.enums.ResultCode;
import com.fuhuitong.nitri.sys.mapper.UserDetailMapper;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "注册详情相关")
@RestController
@RequestMapping("userDetail")
@Log4j2
public class UserDetailController {

    @Autowired
    UserDetailMapper userDetailMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${SMS_VERIFICATION_TIME}")
    private Long SMS_VERIFICATION_TIME;

    @Value("${pay-password-suffix}")
    private String salt;

    @ApiOperation(value = "根据id查找用户")
    @PostMapping("findUserDetail")
    public ResultJson findUserDetail(){
        String id = ContextHolder.getCurrentUser().getId()+"";
        UserDetail userDetail = userDetailMapper.selectById(id);
        return  ResultJson.ok(userDetail);
    }

    @ApiOperation(value = "设置支付密码")
    @PostMapping("setPayPassword")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "payPassword", value = "支付密码", paramType = "query", dataType = "String", required = true)
    })
    public ResultJson setPayPassword(String payPassword){
        if(!StringUtils.isNotBlank(payPassword)){
            return ResultJson.failure(ResultCode.BAD_REQUEST);
        }
        String id = ContextHolder.getCurrentUser().getId()+"";
        UserDetail userDetail = userDetailMapper.selectById(id);
        String  md5Password = SecureUtil.md5(payPassword+salt);
        userDetail.setPayPassword(md5Password);
        if(userDetailMapper.updateById(userDetail)>0){
            return  ResultJson.ok();
        }else {
            return ResultJson.failure(ResultCode.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "修改支付密码")
    @PostMapping("updatePayPassword")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword", value = "原始支付密码", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "newPassword", value = "新支付密码", paramType = "query", dataType = "String", required = true)
    })
    public ResultJson updatePayPassword(String oldPassword,String newPassword){
        if (StringUtil.isNullOrEmpty(oldPassword) || StringUtil.isNullOrEmpty(newPassword)) {
            return ResultJson.failure(ResultCode.BAD_REQUEST);
        }
        String id = ContextHolder.getCurrentUser().getId()+"";
        UserDetail userDetail = userDetailMapper.selectById(id);
        if(StringUtil.isNullOrEmpty(userDetail.getPayPassword())){
            return ResultJson.failure(ResultCode.SERVER_ERROR.getCode(),"请先设置密码");
        }
        String md5OldPassword = SecureUtil.md5(oldPassword+salt);
        if(!userDetail.getPayPassword().equals(md5OldPassword)){
            return ResultJson.failure(ResultCode.SERVER_ERROR.getCode(),"原始密码不正确");
        }
        String md5NewPassword = SecureUtil.md5(newPassword+salt);
        userDetail.setPayPassword(md5NewPassword);
        if(userDetailMapper.updateById(userDetail)>0){
            return  ResultJson.ok();
        }else {
            return ResultJson.failure(ResultCode.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "获取忘记支付密码短信")
    @PostMapping("sendPaySms")
    public ResultJson sendPaySms(){
        //设置信息模板为 短信验证码模板
        String tplid ="179999";
        String id = ContextHolder.getCurrentUser().getId()+"";
        UserDetail userDetail = userDetailMapper.selectById(id);
        //String code= SendSms.sendMsg(userDetail.getUsername());
        //根据用户手机号 发送短信验证码  金融云短信验证码
        String code = SendSms.juhesendMsg(userDetail.getUsername(), tplid);
        redisUtil.setExpire(userDetail.getUsername()+"pay",code+"",SMS_VERIFICATION_TIME);
        return ResultJson.ok();
    }

    @ApiOperation(value = "忘记支付密码")
    @PostMapping("forgetPayPassword")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "payPassword", value = "支付密码", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "code", value = "手机验证码", paramType = "query", dataType = "String", required = true)
    })
    public ResultJson forgetPayPassword(String payPassword,String code){
        String id = ContextHolder.getCurrentUser().getId()+"";
        UserDetail userDetail = userDetailMapper.selectById(id);
        if(StringUtil.isNullOrEmpty(payPassword) ){
            return ResultJson.failure(ResultCode.SERVER_ERROR.getCode(),"请输入新密码");
        }
        if(StringUtil.isNullOrEmpty(code) ){
            return ResultJson.failure(ResultCode.SERVER_ERROR.getCode(),"请输入验证码");
        }
        Object yzm=redisUtil.get(userDetail.getUsername()+"pay");
        if(yzm==null){
            return ResultJson.failure(ResultCode.SMS_NOTEXIST);
        }
        else if(!code.equals(yzm.toString())){
            return ResultJson.failure(ResultCode.SMS_REQUEST);
        }else{
            String  md5PayPassword= SecureUtil.md5(payPassword+salt);
            userDetail.setPayPassword(md5PayPassword);
            if( userDetailMapper.updateById(userDetail)>0){
                redisUtil.del(userDetail.getUsername()+"pay");
                return ResultJson.ok();
            }
            return ResultJson.failure(ResultCode.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "是否设置支付密码")
    @PostMapping("isSetupPayPassword")
    public ResultJson  isSetupPayPassword(){
        String id = ContextHolder.getCurrentUser().getId()+"";
        UserDetail userDetail = userDetailMapper.selectById(id);
        if(StringUtils.isNotBlank(userDetail.getPayPassword())){
            return ResultJson.ok(true);
        }
        return ResultJson.ok(false);
    }

    @ApiOperation(value = "注册信息列表")
    @PostMapping("pageUserDetail")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer", required = true),
            @ApiImplicitParam(name = "size", value = "大小", paramType = "query", dataType = "Integer", required = true)
    })
    public ResultJson pageUserDetail(int page, int size, UserDetail userDetail){
        Page<UserDetail> ipage = new Page<>(page, size);
        LambdaQueryWrapper<UserDetail> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(userDetail.getName())){
            wrapper.like(UserDetail::getUsername,userDetail.getName());
        }
        IPage iPage= userDetailMapper.selectPage(ipage,wrapper);
        return ResultJson.ok(iPage);
    }

//    @ApiOperation(value = "修改用户余额")
//    @PostMapping("updateUserDetailMoney")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "用户id", paramType = "query", dataType = "String", required = true),
//            @ApiImplicitParam(name = "money", value = "余额", paramType = "query", dataType = "BigDecimal", required = true)
//    })
//    public ResultJson updateUserDetailMoney(String id, BigDecimal money){
//        if(!StringUtils.isNotBlank(id) || null == money){
//            return    ResultJson.failure(ResultCode.BAD_REQUEST);
//        }
//        UserDetail userDetail = userDetailMapper.selectById(id);
//        BigDecimal balance = userDetail.getMoney().add(money);
//        userDetail.setMoney(balance);
//        userDetailMapper.updateById(userDetail);
//        return ResultJson.ok(userDetail);
//    }


}
