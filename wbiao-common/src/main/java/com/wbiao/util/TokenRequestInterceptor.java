package com.wbiao.util;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

public class TokenRequestInterceptor implements RequestInterceptor {


/**
     *  获取请求的令牌，在把令牌封装到头信息中
     *      因为feign调用其他微服务时头信息是不会向下游微服务传递
     * @param requestTemplate
     */

    @Override
    public void apply(RequestTemplate requestTemplate) {

        //包含当前用户请求的所有数据
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(requestAttributes!=null){
            Enumeration<String> headerNames = requestAttributes.getRequest().getHeaderNames();
            while (headerNames.hasMoreElements()){
                //请求头的key
                String key = headerNames.nextElement();

                //获取请求头的value
                String value = requestAttributes.getRequest().getHeader(key);

                requestTemplate.header(key,value);
            }
        }

    }
}

