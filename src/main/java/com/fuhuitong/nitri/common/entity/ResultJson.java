package com.fuhuitong.nitri.common.entity;

import com.fuhuitong.nitri.sys.enums.ResultCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Wang
 * @Date 2019/4/19 0019 10:23
 **/
@Data
public class ResultJson<T> implements Serializable {

    private static final long serialVersionUID = 783015033603078674L;
    private int code;
    private String msg;
    private T data;

    public static ResultJson ok() {
        return ok("");
    }

    public static ResultJson ok(Object o) {
        return new ResultJson(ResultCode.SUCCESS, o);
    }

    public static ResultJson failure(ResultCode code) {
        return failure(code, "");
    }

    public static ResultJson failure(ResultCode code, Object o) {
        return new ResultJson(code, o);
    }

    public static ResultJson failure(int code ,String msg) {
        return new ResultJson(code, msg);
    }

    public ResultJson(ResultCode resultCode) {
        setResultCode(resultCode);
    }


    public ResultJson(int code ,String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultJson(ResultCode resultCode, T data) {
        setResultCode(resultCode);
        this.data = data;
    }

    public void setResultCode(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    @Override
    public String toString() {
        return "{" +
                "\"code\":" + code +
                ", \"msg\":\"" + msg + '\"' +
                ", \"data\":\"" + data + '\"' +
                '}';
    }
}
