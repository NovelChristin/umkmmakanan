package com.christin.umkmmakanan.Views;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.christin.umkmmakanan.Helpers.AdminAdapter;
import com.christin.umkmmakanan.Helpers.OrderAdapter;
import com.christin.umkmmakanan.Helpers.Utils;
import com.christin.umkmmakanan.R;
import com.christin.umkmmakanan.Retrofit.Admin;
import com.christin.umkmmakanan.Retrofit.AdminResponse;
import com.christin.umkmmakanan.Retrofit.AdminResponse;
import com.christin.umkmmakanan.Retrofit.RestApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class TokoActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdminAdapter adminAdapter;
    private List<Admin> adminList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko);

        // Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.recycler_view_admin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inisialisasi Adapter
        adminAdapter = new AdminAdapter(adminList, this::onAdminSelected);
        recyclerView.setAdapter(adminAdapter);

        // Ambil data admin dari API
        retrieveAdminData();
    }

    private void onAdminSelected(Admin admin) {
        Intent intent = new Intent(TokoActivity.this, DashboardActivity.class);
        intent.putExtra("ADMIN_ID", admin.getId()); // Mengirim adminId
        startActivity(intent);
    }

    private void retrieveAdminData() {
        RestApi apiService = Utils.getClient().create(RestApi.class);
        Call<AdminResponse> call = apiService.retrieveAdmin();

        call.enqueue(new Callback<AdminResponse>() {
            @Override
            public void onResponse(Call<AdminResponse> call, retrofit2.Response<AdminResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AdminResponse adminResponse = response.body();
                    if (adminResponse.getSuccess()) {
                        adminList.clear(); // Bersihkan daftar sebelum menambahkan data baru
                        adminList.addAll(adminResponse.getData().getAdmin()); // Tambahkan data admin
                        adminAdapter.notifyDataSetChanged(); // Notifikasi perubahan di adapter
                    } else {
                        Utils.show(TokoActivity.this, "Tidak ada admin ditemukan.");
                    }
                } else {
                    Utils.show(TokoActivity.this, "Gagal memuat daftar admin.");
                }
            }

            @Override
            public void onFailure(Call<AdminResponse> call, Throwable t) {
                Utils.show(TokoActivity.this, "Kesalahan jaringan: " + t.getMessage());
            }
        });
    }
}

