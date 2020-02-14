package com.wbiao.controller;

import com.wbiao.user.feignService.OauthFeign;
import com.wbiao.util.AuthToken;
import com.wbiao.util.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private OauthFeign oauthFeign;


    @RequestMapping("/login.html")
    public String toLogin(@RequestParam(required = false) String code, Model model){
        if(StringUtils.isNotBlank(code)){
            ResultUtil<AuthToken> authTokenResultUtil = oauthFeign.wbLogin(code);
            if(authTokenResultUtil.getCode().equals(500)){
                return "/login";
            }
            model.addAttribute("code",authTokenResultUtil.getData());
        }
        return "/login";
    }

    @RequestMapping("/register.html")
    public String register(){
        return "/register";
    }
}
