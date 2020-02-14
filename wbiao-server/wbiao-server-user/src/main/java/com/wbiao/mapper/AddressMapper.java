package com.wbiao.mapper;

import com.wbiao.user.pojo.Address;

import java.util.List;

public interface AddressMapper {

    List<Address> selectAddressByUsername(String username);

    void addAddress(Address address);

    Address selectAddressByNameAndStatus(String username);

    void updateStatus(Long id);

    void updateAddressById(Address address);
}
