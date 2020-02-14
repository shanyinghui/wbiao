package com.wbiao.user.pojo;

import java.io.Serializable;

public class Address implements Serializable {
    private Long id;
    private String username;
    private String name;
    private String phone;
    private String province;
    private String city;
    private String region;
    private String detail_address;
    private Integer send_status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDetail_address() {
        return detail_address;
    }

    public void setDetail_address(String detail_address) {
        this.detail_address = detail_address;
    }

    public Integer getSend_status() {
        return send_status;
    }

    public void setSend_status(Integer send_status) {
        this.send_status = send_status;
    }
}
