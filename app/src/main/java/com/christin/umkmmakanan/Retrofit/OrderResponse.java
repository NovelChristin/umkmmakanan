package com.christin.umkmmakanan.Retrofit;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderResponse {
    @SerializedName("success")
    private Boolean success;

    @SerializedName("status")
    private Integer status;

    @SerializedName("data")
    private OrderData data; // Menyesuaikan dengan struktur JSON

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public OrderData getData() {
        return data;
    }

    public void setData(OrderData data) {
        this.data = data;
    }

    public static class OrderData {
        @SerializedName("order")
        private List<Order> orders;

        public List<Order> getOrders() {
            return orders;
        }

        public void setOrders(List<Order> orders) {
            this.orders = orders;
        }
    }
}



