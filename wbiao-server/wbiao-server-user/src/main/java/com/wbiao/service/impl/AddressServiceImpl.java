package com.wbiao.service.impl;

import com.wbiao.mapper.AddressMapper;
import com.wbiao.service.AddressService;
import com.wbiao.user.pojo.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<Address> selectAddressByUsername(String username) {
        return addressMapper.selectAddressByUsername(username);
    }

    @Override
    @Transactional
    public void addAddress(Address address) {
        if(address.getSend_status().equals(0)){
            addressMapper.addAddress(address);
        }else{
            Address ads = addressMapper.selectAddressByNameAndStatus(address.getUsername());
            if(ads == null){
                addressMapper.addAddress(address);
            }else{
                addressMapper.updateStatus(ads.getId());
                addressMapper.addAddress(address);
            }
        }
    }

    @Override
    @Transactional
    public void updateAddress(Address address) {
        if(address.getSend_status().equals(0)){
            addressMapper.updateAddressById(address);
        }else{
            Address ads = addressMapper.selectAddressByNameAndStatus(address.getUsername());
            if(ads == null){
                addressMapper.addAddress(address);
            }else{
                addressMapper.updateStatus(ads.getId());
                addressMapper.updateAddressById(address);
            }
        }
    }
}
