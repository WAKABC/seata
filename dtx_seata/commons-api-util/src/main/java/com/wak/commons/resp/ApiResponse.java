package com.wak.commons.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuankang
 * @date 2023/12/18 22:04
 * @Description TODO 统一回复
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    /**
     * 代码
     */
    private Integer code;
    /**
     * 消息
     */
    private String message;
    /**
     * 数据
     */
    private T data;

    /**
     * 成功
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ResultCodeEnum.SUCCESS.getCode(),
                ResultCodeEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 失败
     */
    public static <T> ApiResponse<T> fail(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }
}
