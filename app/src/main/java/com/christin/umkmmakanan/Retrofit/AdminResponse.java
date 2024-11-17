package com.christin.umkmmakanan.Retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdminResponse {

    @SerializedName("success")
    private Boolean success;

    @SerializedName("status")
    private Integer status;

    @SerializedName("data")
    private AdminData data;

    public static class AdminData {
        @SerializedName("admin")  // Pastikan nama ini sesuai dengan JSON yang diterima
        private List<Admin> admin; // Ubah 'admins' menjadi 'admin' untuk konsistensi

        public List<Admin> getAdmin() {
            return admin;
        }

        public void setAdmin(List<Admin> admin) {
            this.admin = admin;
        }
    }

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

    public AdminData getData() {
        return data;
    }

    public void setData(AdminData data) {
        this.data = data;
    }
}

