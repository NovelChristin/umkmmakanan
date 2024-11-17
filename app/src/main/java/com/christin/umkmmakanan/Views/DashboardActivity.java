package com.christin.umkmmakanan.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.christin.umkmmakanan.Helpers.ProductAdapter;
import com.christin.umkmmakanan.Helpers.Utils;
import com.christin.umkmmakanan.Retrofit.Product;
import com.christin.umkmmakanan.Retrofit.ProductResponse;
import com.christin.umkmmakanan.Retrofit.RestApi;
import com.christin.umkmmakanan.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fabResetAdmin;

    private String adminId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        setBottomNavigationListener();

        fabResetAdmin = findViewById(R.id.fab_reset_admin);
        fabResetAdmin.setOnClickListener(v -> resetAdminId());

        // Ambil adminId dari Intent
        adminId = getIntent().getStringExtra("ADMIN_ID");
        updateFabVisibility();
        // Panggil API untuk mendapatkan daftar produk
        fetchProducts(null);
    }

    private void updateFabVisibility() {
        if (adminId != null) {
            fabResetAdmin.setVisibility(View.VISIBLE);
        } else {
            fabResetAdmin.setVisibility(View.GONE);
        }
    }

    private void resetAdminId() {
        adminId = null; // Reset adminId
        updateFabVisibility(); // Perbarui tampilan FAB
        fetchProducts(null); // Panggil ulang produk jika perlu
    }

    private void setBottomNavigationListener() {
        // Set listener for bottom navigation
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            // Ambil status login dari SharedPreferences
            SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
            boolean isLoggedIn = prefs.getBoolean("is_logged_in", false);

            switch (item.getItemId()) {
                case R.id.nav_home:
                    // Tidak perlu berpindah, karena kita sudah di DashboardActivity
                    return true;
                case R.id.nav_cart:
                    // Buka CartActivity
                    Utils.openActivity(DashboardActivity.this, CartActivity.class);
                    return true;
                case R.id.nav_toko:
                    // Buka CartActivity
                    Utils.openActivity(DashboardActivity.this, TokoActivity.class);
                    return true;
                case R.id.nav_order:
                    // Buka OrderListActivity
                    if (!isLoggedIn) {
                        // Jika belum login, arahkan ke SignInActivity
                        Utils.openActivity(DashboardActivity.this, SignInActivity.class);
                    } else {
                        // Buka OrderListActivity
                        Utils.openActivity(DashboardActivity.this, OrderListActivity.class);
                    }
                    return true;
                case R.id.nav_info:
                    // Buka InfoActivity
                    Utils.openActivity(DashboardActivity.this, InfoActivity.class);
                    return true;
            }
            return false;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);

        // Menambahkan SearchView
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        // Set listener untuk SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchProducts(query);
                return false; // Tidak perlu mengimplementasikan ini
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchProducts(newText); // Memanggil filter di ProductAdapter
                return true;
            }
        });

        // Mengambil item profil
        MenuItem profileItem = menu.findItem(R.id.action_profile);

        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("is_logged_in", false);

        // Update ikon menu berdasarkan status login
        if (isLoggedIn) {
            profileItem.setIcon(R.drawable.ic_profile); // Ganti dengan ikon profil
        } else {
            profileItem.setIcon(R.drawable.ic_login); // Ganti dengan ikon login
        }

        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
                boolean isLoggedIn = prefs.getBoolean("is_logged_in", false);

                if (isLoggedIn) {
                    // Arahkan ke ProfileActivity
                    Utils.openActivity(DashboardActivity.this, ProfilActivity.class);
                } else {
                    // Arahkan ke SignInActivity
                    Utils.openActivity(DashboardActivity.this, SignInActivity.class);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void fetchProducts(String query) {
        RestApi restApi = Utils.getClient().create(RestApi.class);
        Call<ProductResponse> call = restApi.retrieveProduct(adminId, query);
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> products = response.body().getData().getProduct();

                    productAdapter = new ProductAdapter(products, DashboardActivity.this);
                    recyclerView.setAdapter(productAdapter);
                } else {
                    // Log error untuk respons yang tidak sukses
                    Log.e("ProductFetchError", "Response not successful: " + response.message());
                    Utils.show(DashboardActivity.this, "Gagal memuat daftar produk: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                // Tangani kesalahan
                Log.e("ProductFetchError", "Network Error: " + t.getMessage());
                Utils.show(DashboardActivity.this, "Kesalahan jaringan: " + t.getMessage());
            }
        });
    }
}