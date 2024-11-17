package com.christin.umkmmakanan.Retrofit;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Admin implements Serializable {
    @SerializedName("_id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("description")
    private String description;
    @SerializedName("certificatePIRT")
    private String certificatePIRT;
    @SerializedName("certificateHalal")
    private String certificateHalal;
    @SerializedName("isActive")
    private boolean isActive;
    @SerializedName("isOpen")
    private boolean isOpen;
    @SerializedName("address")
    private String address;
    @SerializedName("rated")
    private String rated;
    @SerializedName("rating")
    private List<Integer> rating;

    // Getters dan Setters


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCertificatePIRT() {
        return certificatePIRT;
    }

    public void setCertificatePIRT(String certificatePIRT) {
        this.certificatePIRT = certificatePIRT;
    }

    public String getCertificateHalal() {
        return certificateHalal;
    }

    public void setCertificateHalal(String certificateHalal) {
        this.certificateHalal = certificateHalal;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public List<Integer> getRating() {
        return rating;
    }

    public void setRating(List<Integer> rating) {
        this.rating = rating;
    }
}

