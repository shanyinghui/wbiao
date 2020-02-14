package com.wbiao.user.feignService;

import com.wbiao.user.config.ServiceFeignConfiguration;
import com.wbiao.user.pojo.User;
import com.wbiao.util.ResultUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "wbiao-user",configuration = ServiceFeignConfiguration.class)
public interface UserFeign {
    @GetMapping("/{username}")
    ResultUtil<User> findUserByUsername(@PathVariable("username") String username);

    /**
     *  手机登录
     * @param phone
     * @param code
     * @return
     */
    @PostMapping("/phoneLogin")
    ResultUtil<User> phoneLogin(@RequestParam("phone")String phone,@RequestParam("code")String code);

    /**
     *  微博登录时，把登录信息保存到数据库
     * @param
     */
    @PostMapping("/user")
    ResultUtil<User> addUserByUid(@RequestBody User user);

    /**
     * 获取当前登录用户信息
     * @return
     */
    @GetMapping("/loginUserinfo")
    ResultUtil<User> loginUserinfo();


}
