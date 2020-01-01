package com.wbiao.goods.pojo;

import java.io.Serializable;

public class Spec_val implements Serializable {
    private Integer id;
    private String spec_name;
    private String spec_val;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getSpec_name() {
        return spec_name;
    }

    public void setSpec_name(String spec_name) {
        this.spec_name = spec_name;
    }

    public String getSpec_val() {
        return spec_val;
    }

    public void setSpec_val(String spec_val) {
        this.spec_val = spec_val;
    }

    @Override
    public String toString() {
        return "Spec_val{" +
                "id=" + id +
                ", spec_name='" + spec_name + '\'' +
                ", spec_val='" + spec_val + '\'' +
                '}';
    }
}
