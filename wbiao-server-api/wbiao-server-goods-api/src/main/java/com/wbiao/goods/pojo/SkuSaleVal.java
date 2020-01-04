package com.wbiao.goods.pojo;

import java.io.Serializable;

public class SkuSaleVal implements Serializable {
    private Integer id;
    private String spuid;
    private String skuid;
    private String image;
    private String salaVal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpuid() {
        return spuid;
    }

    public void setSpuid(String spuid) {
        this.spuid = spuid;
    }

    public String getSkuid() {
        return skuid;
    }

    public void setSkuid(String skuid) {
        this.skuid = skuid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSalaVal() {
        return salaVal;
    }

    public void setSalaVal(String salaVal) {
        this.salaVal = salaVal;
    }

    @Override
    public String toString() {
        return "SkuSaleVal{" +
                "id=" + id +
                ", spuid='" + spuid + '\'' +
                ", skuid='" + skuid + '\'' +
                ", image='" + image + '\'' +
                ", salaVal='" + salaVal + '\'' +
                '}';
    }
}
