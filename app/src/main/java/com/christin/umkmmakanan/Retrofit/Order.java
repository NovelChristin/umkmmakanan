package com.christin.umkmmakanan.Retrofit;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    @SerializedName("_id")
    private String _id;
    @SerializedName("invoiceId")
    private String invoiceId;
    @SerializedName("userId")
    private String userId;
    @SerializedName("userName")
    private String userName;

    @SerializedName("userPhone")
    private String userPhone;

    @SerializedName("adminPhone")
    private String adminPhone;

    @SerializedName("adminId")
    private String adminId;

    @SerializedName("adminName")
    private String adminName;

    @SerializedName("totalPayment")
    private double totalPayment;

    @SerializedName("status")
    private String status;

    @SerializedName("paymentProof")
    private String paymentProof;

    @SerializedName("isCOD")
    private Boolean isCOD;

    @SerializedName("isRated")
    private Boolean isRated;

    @SerializedName("rated")
    private Integer rated;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("updatedAt")
    private String updatedAt;

    @SerializedName("product")
    private List<OrderProduct> product;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentProof() {
        return paymentProof;
    }

    public void setPaymentProof(String paymentProof) {
        this.paymentProof = paymentProof;
    }

    public Boolean getCOD() {
        return isCOD;
    }

    public void setCOD(Boolean COD) {
        isCOD = COD;
    }

    public Boolean getRated() {
        return isRated;
    }

    public Integer getRating() {
        return rated;
    }

    public void setRated(Integer rated) {
        this.rated = rated;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<OrderProduct> getProduct() {
        return product;
    }

    public void setProduct(List<OrderProduct> product) {
        this.product = product;
    }

    public void setRated(Boolean rated) {
        isRated = rated;
    }


}
