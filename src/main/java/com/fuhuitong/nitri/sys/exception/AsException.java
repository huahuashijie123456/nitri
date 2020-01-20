package com.fuhuitong.nitri.sys.exception;

import com.fuhuitong.nitri.common.entity.ResultJson;
import lombok.Getter;

/**
 * @Author Wang
 * @Date 2019/4/19 0019 10:40
 **/
@Getter
public class AsException extends RuntimeException {
    private ResultJson resultJson;


    public AsException(ResultJson resultJson) {
        this.resultJson = resultJson;
    }
}