package com.christin.umkmmakanan.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.christin.umkmmakanan.Helpers.OrderAdapter;
import com.christin.umkmmakanan.Helpers.ProductAdapter;
import com.christin.umkmmakanan.Helpers.Utils;
import com.christin.umkmmakanan.R;
import com.christin.umkmmakanan.Retrofit.Order;
import com.christin.umkmmakanan.Retrofit.OrderResponse;
import com.christin.umkmmakanan.Retrofit.Product;
import com.christin.umkmmakanan.Retrofit.ProductResponse;
import com.christin.umkmmakanan.Retrofit.RestApi;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderListActivity extends AppCompatActivity {

    private String adminId;
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        recyclerView = this.findViewById(R.id.listOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adminId = getIntent().getStringExtra("ADMIN_ID");
        fetchOrders();
    }
    private void fetchOrders() {

        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userId = prefs.getString("user_id", null);
        Utils.show(OrderListActivity.this, "Getting Data : "+userId);
        RestApi restApi = Utils.getClient().create(RestApi.class);
        Call<com.christin.umkmmakanan.Retrofit.OrderResponse> call = restApi.getOrderList(userId);
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Order> orders = response.body().getData().getOrders();

                    orderAdapter = new OrderAdapter(orders, OrderListActivity.this);
                    recyclerView.setAdapter(orderAdapter);
                    Utils.show(OrderListActivity.this,"Data fetched successfully: " + orders.size() + " items");
                } else {
                    // Log error untuk respons yang tidak sukses
                    Log.e("ProductFetchError", "Response not successful: " + response.message());
                    Utils.show(OrderListActivity.this, "Gagal memuat daftar produk: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                // Tangani kesalahan
                Log.e("ProductFetchError", "Network Error: " + t.getMessage());
                Utils.show(OrderListActivity.this, "Kesalahan jaringan: " + t.getMessage());
            }
        });
    }
}