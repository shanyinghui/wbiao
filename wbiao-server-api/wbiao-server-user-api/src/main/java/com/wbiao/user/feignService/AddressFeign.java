package com.wbiao.user.feignService;

import com.wbiao.user.pojo.Address;
import com.wbiao.util.ResultUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "wbiao-user")
@RequestMapping("/address")
public interface AddressFeign {

    /**
     *  获取收货地址
     * @return
     */
    @GetMapping("/username")
    ResultUtil<List<Address>> getAddressByUsername();
}
