package com.wak.sentinel.authorize;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 * @author wuankang
 * @date 2024/1/21 10:51
 * @Description TODO 授权参数解析
 * @Version 1.0
 */
@Component
public class CustomizeRequestOriginParser implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest httpServletRequest) {
        //对参数进行黑白名单设置
        return httpServletRequest.getParameter("serverName");
    }
}
