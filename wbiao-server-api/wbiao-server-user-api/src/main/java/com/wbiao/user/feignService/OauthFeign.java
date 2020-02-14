package com.wbiao.user.feignService;

import com.wbiao.user.config.ServiceFeignConfiguration;
import com.wbiao.util.AuthToken;
import com.wbiao.util.ResultUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "wbiao-user-auth",configuration = ServiceFeignConfiguration.class)
public interface OauthFeign {
    /**
     *
     *  微博登录
     * @param code
     * @return
     */
    @RequestMapping("/wbLogin")
    ResultUtil<AuthToken> wbLogin(@RequestParam("code")String code);
}
