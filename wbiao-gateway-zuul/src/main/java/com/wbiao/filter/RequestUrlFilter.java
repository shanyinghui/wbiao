package com.wbiao.filter;

import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Optional;

/**
 *  判断访问路径是否需要校验
 */
public class RequestUrlFilter {

    public static Boolean hasURLAuthorize(String uri){
        ArrayList<String> list = new ArrayList<>();
        list.add("/api/auth/*");
        /*list.add("/api/auth/smsLogin");
        list.add("/api/auth/wbLogin");*/
        list.add("/api/searchWeb/*");
        list.add("/api/userWeb/login.html");
        list.add("/api/userWeb/register.html");
        list.add("/api/item/brand");
        list.add("/api/item/brand/all");
        list.add("/api/item/goods");
        list.add("/api/item/goods/enable");
        list.add("/api/item/goods/goodsDetails/*");
        list.add("/api/item/goods/sku/*");
        list.add("/api/item/spec");
        list.add("/api/goodsDetails/*");
        list.add("/api/user/sendCode");
        list.add("/api/user/phoneLogin");
        list.add("/api/user/register/*");
        /*list.add("/api/user/register/user");*/
        /*list.add("/api/user/register/validateUsername");
        list.add("/api/user/register/validatePhone");*/
        list.add("/api/orderWeb/cart");
        AntPathMatcher matcher = new AntPathMatcher();
        Optional<String> optional =list.stream().filter(t->matcher.match(t,uri)).findFirst();
        return optional.isPresent();
    }
}
