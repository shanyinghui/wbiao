package com.wbiao.order.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
    private String id;
    private String username;
    private BigDecimal totalMoney; //订单总金额
    private BigDecimal payMoney;    //应付金额
    private BigDecimal freightMoney; //运费金额
    private Integer source_type; //订单来源：0->PC订单；1->app订单
    private Integer status; //订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
    private String receiver_name;
    private String receiver_phone;
    private String receiver_post_code;
    private String receiver_province;
    private String receiver_city;
    private String receiver_region;
    private String receiver_detail_address;
    private String note;    //卖家留言
    private Integer confirm_status; //确认收货状态：0->未确认；1->已确认
    private Integer delete_status;  //删除状态：0->未删除；1->已删除
    private Integer pay_status; //交易状态，1 未支付, 2已支付, 3 支付失败
    private Date payment_time;   //支付时间
    private Date delivery_time;  //发货时间
    private Date receive_time;   //确认收货时间
    private Date comment_time;   //评价时间
    private Date create_time;    //提交时间
    private Date modify_time;    //修改时间

    private List<String> skuids;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public BigDecimal getFreightMoney() {
        return freightMoney;
    }

    public void setFreightMoney(BigDecimal freightMoney) {
        this.freightMoney = freightMoney;
    }

    public Integer getSource_type() {
        return source_type;
    }

    public void setSource_type(Integer source_type) {
        this.source_type = source_type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_phone() {
        return receiver_phone;
    }

    public void setReceiver_phone(String receiver_phone) {
        this.receiver_phone = receiver_phone;
    }

    public String getReceiver_post_code() {
        return receiver_post_code;
    }

    public void setReceiver_post_code(String receiver_post_code) {
        this.receiver_post_code = receiver_post_code;
    }

    public String getReceiver_province() {
        return receiver_province;
    }

    public void setReceiver_province(String receiver_province) {
        this.receiver_province = receiver_province;
    }

    public String getReceiver_city() {
        return receiver_city;
    }

    public void setReceiver_city(String receiver_city) {
        this.receiver_city = receiver_city;
    }

    public String getReceiver_region() {
        return receiver_region;
    }

    public void setReceiver_region(String receiver_region) {
        this.receiver_region = receiver_region;
    }

    public String getReceiver_detail_address() {
        return receiver_detail_address;
    }

    public void setReceiver_detail_address(String receiver_detail_address) {
        this.receiver_detail_address = receiver_detail_address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getConfirm_status() {
        return confirm_status;
    }

    public void setConfirm_status(Integer confirm_status) {
        this.confirm_status = confirm_status;
    }

    public Integer getDelete_status() {
        return delete_status;
    }

    public void setDelete_status(Integer delete_status) {
        this.delete_status = delete_status;
    }

    public Date getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(Date payment_time) {
        this.payment_time = payment_time;
    }

    public Date getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(Date delivery_time) {
        this.delivery_time = delivery_time;
    }

    public Date getReceive_time() {
        return receive_time;
    }

    public void setReceive_time(Date receive_time) {
        this.receive_time = receive_time;
    }

    public Date getComment_time() {
        return comment_time;
    }

    public void setComment_time(Date comment_time) {
        this.comment_time = comment_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getModify_time() {
        return modify_time;
    }

    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
    }

    public List<String> getSkuids() {
        return skuids;
    }

    public void setSkuids(List<String> skuids) {
        this.skuids = skuids;
    }

    public Integer getPay_status() {
        return pay_status;
    }

    public void setPay_status(Integer pay_status) {
        this.pay_status = pay_status;
    }
}
