package com.wbiao.pojo;

import java.io.Serializable;
import java.util.Date;

public class Spu implements Serializable {
    private String id;
    private String brand;
    private String series;
    private String subTitle;
    private String specs;
    private Date createtime;
    private Date last_updatetime;
    private Integer category_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getLast_updatetime() {
        return last_updatetime;
    }

    public void setLast_updatetime(Date last_updatetime) {
        this.last_updatetime = last_updatetime;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    @Override
    public String toString() {
        return "Spu{" +
                "id='" + id + '\'' +
                ", brand='" + brand + '\'' +
                ", series='" + series + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", specs='" + specs + '\'' +
                ", createtime=" + createtime +
                ", last_updatetime=" + last_updatetime +
                ", category_id=" + category_id +
                '}';
    }
}
