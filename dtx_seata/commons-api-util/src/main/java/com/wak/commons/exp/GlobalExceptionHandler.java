package com.wak.commons.exp;

import com.wak.commons.resp.ApiResponse;
import com.wak.commons.resp.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author wuankang
 * @date 2023/12/18 21:53
 * @Description TODO
 * @Version 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理异常
     *
     * @param e e
     * @return {@code String}
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<String> handleException(Exception e) {
        log.error("global exception : {}", e.getMessage());
        return ApiResponse.fail(ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
    }
}
