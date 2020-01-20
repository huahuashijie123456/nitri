package com.fuhuitong.nitri.sys.exception;

import com.fuhuitong.nitri.common.entity.ResultJson;
import com.fuhuitong.nitri.sys.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

/**
 * @Author Wang
 * @Date 2019/4/19 0019 10:41
 **/
@RestControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    /**
     * 处理所有自定义异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MultipartException.class)
    public ResultJson handleAll(Throwable t) {
        // TODO do sth
        System.out.println("====================================");
        return ResultJson.failure(ResultCode.SERVER_ERROR, "大小");
    }

    @ExceptionHandler(AsException.class)
    public ResultJson handleCustomException(AsException e) {
        log.error(e.getResultJson().getMsg().toString());
        return e.getResultJson();
    }

    /**
     * 处理参数校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultJson handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getBindingResult().getFieldError().getField() + e.getBindingResult().getFieldError().getDefaultMessage());
        return ResultJson.failure(ResultCode.BAD_REQUEST, e.getBindingResult().getFieldError().getDefaultMessage());
    }
}
