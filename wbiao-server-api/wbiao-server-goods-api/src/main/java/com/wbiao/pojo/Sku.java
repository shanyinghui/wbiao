package com.wbiao.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Sku implements Serializable {
    private String id;
    private String spu_id;
    private String title;
    private String brand;
    private BigDecimal price;
    private String image;
    private String images;
    private Long stock;
    private String ownSpec;
    private Long sale_num; //销量
    private Long comment_num; //评论数
    private Boolean enable; //0：下架 1：上架
    private String indexes;
    private Date createtime;
    private Date last_updatetime ;

    @Override
    public String toString() {
        return "Sku{" +
                "id='" + id + '\'' +
                ", spu_id='" + spu_id + '\'' +
                ", title='" + title + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", images='" + images + '\'' +
                ", stock=" + stock +
                ", ownSpec='" + ownSpec + '\'' +
                ", sale_num=" + sale_num +
                ", comment_num=" + comment_num +
                ", enable=" + enable +
                ", indexes='" + indexes + '\'' +
                ", createtime=" + createtime +
                ", last_updatetime=" + last_updatetime +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpu_id() {
        return spu_id;
    }

    public void setSpu_id(String spu_id) {
        this.spu_id = spu_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public String getOwnSpec() {
        return ownSpec;
    }

    public void setOwnSpec(String ownSpec) {
        this.ownSpec = ownSpec;
    }

    public Long getSale_num() {
        return sale_num;
    }

    public void setSale_num(Long sale_num) {
        this.sale_num = sale_num;
    }

    public Long getComment_num() {
        return comment_num;
    }

    public void setComment_num(Long comment_num) {
        this.comment_num = comment_num;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getIndexes() {
        return indexes;
    }

    public void setIndexes(String indexes) {
        this.indexes = indexes;
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
}

