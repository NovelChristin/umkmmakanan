package com.christin.umkmmakanan.Retrofit;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product  implements Serializable{
    @SerializedName("_id")
    private String id;
    @SerializedName("adminId")
    private String adminId;
    @SerializedName("admin")
    private String admin;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("price")
    private int price;
    @SerializedName("picture")
    private String picture;
    @SerializedName("available")
    private boolean available;

    // Constructor
    public Product(String id, String adminId, String admin, String name, String description, int price, String picture, boolean available) {
        this.id = id;
        this.adminId = adminId;
        this.admin = admin;
        this.name = name;
        this.description = description;
        this.price = price;
        this.picture = picture;
        this.available = available;
    }

    // Getter methods
    public String getId() { return id; }
    public String getAdminId() { return adminId; }
    public String getAdmin() { return admin; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getPrice() { return price; }
    public String getPicture() { return picture; }
    public boolean isAvailable() { return available; }
}

