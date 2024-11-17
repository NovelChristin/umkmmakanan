package com.christin.umkmmakanan.Retrofit;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {
    @SerializedName("product")
    private List<Product> product;
}
