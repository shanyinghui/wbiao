package com.wbiao.service;

import com.wbiao.user.pojo.Address;

import java.util.List;

public interface AddressService {

    List<Address> selectAddressByUsername(String username);

    void addAddress(Address address);

    void updateAddress(Address address);
}
