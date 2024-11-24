package com.christin.umkmmakanan.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_table")
public class CartItem {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "_id")
    private int _id;

    @NonNull
    @ColumnInfo(name = "productId")
    private String productId;

    @NonNull
    @ColumnInfo(name="productName")
    private String productName;

    @NonNull
    @ColumnInfo(name="quantity")
    private Integer quantity;

    @NonNull
    @ColumnInfo(name = "price")
    private Integer price;

    @NonNull
    @ColumnInfo(name = "total")
    private Integer total;

    @ColumnInfo(name = "picture")
    private String picture;

    @ColumnInfo(name = "adminId")
    private String adminId;

    @ColumnInfo(name = "adminName")
    private String adminName;

    public CartItem(
            int _id,
            @NonNull String productId,
            @NonNull String productName,
            @NonNull Integer quantity,
            @NonNull Integer price,
            @NonNull Integer total,
            @NonNull String adminId,
            @NonNull String adminName,
            String picture
    ){
        this._id = _id;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.picture = picture;
        this.adminId = adminId;
        this.adminName = adminName;

    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


    @NonNull
    public String getProductId() {
        return productId;
    }

    public void setProductId(@NonNull String productId) {
        this.productId = productId;
    }

    @NonNull
    public String getProductName() {
        return productName;
    }

    public void setProductName(@NonNull String productName) {
        this.productName = productName;
    }

    @NonNull
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(@NonNull Integer quantity) {
        this.quantity = quantity;
    }

    @NonNull
    public Integer getPrice() {
        return price;
    }

    public void setPrice(@NonNull Integer price) {
        this.price = price;
    }

    @NonNull
    public Integer getTotal() {
        return total;
    }

    public void setTotal(@NonNull Integer total) {
        this.total = total;
    }

    @NonNull
    public int get_id() {
        return _id;
    }

    public void set_id(@NonNull int _id) {
        this._id = _id;
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
}
