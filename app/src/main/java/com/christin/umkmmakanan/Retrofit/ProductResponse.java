package com.christin.umkmmakanan.Retrofit;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProductResponse {
    private boolean success;
    private int status;
    private ProductData data;

    public boolean isSuccess() {
        return success;
    }

    public int getStatus() {
        return status;
    }

    public ProductData getData() {
        return data;
    }

    public static class ProductData {
        private List<Product> product;

        public List<Product> getProduct() {
            return product;
        }
    }
}
