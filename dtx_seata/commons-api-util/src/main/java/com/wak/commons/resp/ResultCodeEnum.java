package com.wak.commons.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author wuankang
 * @date 2023/12/18 22:08
 * @Description TODO
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum ResultCodeEnum {
    /**
     * 成功
     */
    SUCCESS(200, "成功"),
    /**
     * 参数异常
     */
    BAD_REQUEST(400, "请求参数错误"),
    /**
     * 无权限
     */
    UNAUTHORIZED(401, "未经授权"),
    /**
     * 禁止访问
     */
    FORBIDDEN(403, "禁止访问"),
    /**
     * 没有资源
     */
    NOT_FOUND(404, "资源未找到"),
    /**
     * 服务器错误
     */
    INTERNAL_SERVER_ERROR(500, "内部服务器错误");

    /**
     * 响应码
     */
    private final Integer code;
    /**
     * 相应描述
     */
    private final String message;

    /**
     * 遍历获取值
     */
    public static ResultCodeEnum findByCode(int code) {
        //获取values
        ResultCodeEnum[] collection = ResultCodeEnum.values();
        Optional<ResultCodeEnum> optional = Arrays.stream(collection).filter(r -> r.getCode() == code).findFirst();
        return optional.orElse(null);
    }
}
