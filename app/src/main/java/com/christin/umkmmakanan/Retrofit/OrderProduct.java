package com.christin.umkmmakanan.Retrofit;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderProduct implements Serializable {
    @SerializedName("productId")
    private String productId;
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private Integer price;
    @SerializedName("quantity")
    private Integer quantity;

    @SerializedName("productTotal")
    private Integer productTotal;
    @SerializedName("picture")
    private String picture;

    @SerializedName("_id")
    private String _id;
    public OrderProduct(String productId, String name, Integer price, Integer quantity, Integer productTotal, String picture){
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.productTotal = productTotal;
        this.picture = picture;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(Integer productTotal) {
        this.productTotal = productTotal;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
