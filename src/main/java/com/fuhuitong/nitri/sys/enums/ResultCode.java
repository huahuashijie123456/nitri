package com.fuhuitong.nitri.sys.enums;

/**
 * @Author Wang
 * @Date 2019/4/19 0019 10:22
 **/

public enum ResultCode {
    /*
    请求返回状态码和说明信息
     */
    SUCCESS(200, "成功"),
    SMS_REQUEST(402, "验证码输入有误"),
    SMS_NOTEXIST(402, "验证码不存在,请先获取验证码"),
    BAD_REQUEST(400, "参数或者语法不对"),
    UNAUTHORIZED(401, "认证失败"),
    ACCOUNTLOCKED(402, "该账号已被禁用"),
    NOTACTIVATED(403, "该账号尚未激活"),
    LOGIN_ERROR(402, "登陆失败，用户名或密码无效"),
    REGISTER_ERROR(405, "用户已存在"),
    ACCOUNT_ERROR(405, "登陆账户已存在"),
    BANK_NOTEXIST(405, "银行卡信息不存在"),
    USER_MONEYMIN(405, "用户余额不足"),
    USEEOLDPASSWORD_ERROR(405, "旧密码输入错误"),
    MERCHANTS_ERROR(405, "商户名称已存在"),
    MOBILEFORBIDDEN(405, "手机端用户禁止访问"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "请求的资源不存在"),
    OPERATE_ERROR(405, "操作失败，请求操作的资源不存在"),
    COUPONS_EXIST(406, "该优惠券已领取过了"),
    COUPONS_NOTEXIST(406, "优惠券领完了"),
    FEED_EXIST(406, "你之前有提交过的内容还未回复"),
    shoucang_EXIST(406, "收藏过了"),
    RED_NOTEXIST(406, "红包领完了"),
    TIME_OUT(408, "请求超时"),
    SERVER_ERROR(500, "服务器内部错误"),
    NOT_SHARE(407, "非跟进人禁止操作"),
    IMPORT_ERROR(408, "导入失败"),
    FILE_SIZE(406, "文件不能大于10M"),

    NO_PAY_PASSWORD(505, "请先设置支付密码")
    ;
    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}

