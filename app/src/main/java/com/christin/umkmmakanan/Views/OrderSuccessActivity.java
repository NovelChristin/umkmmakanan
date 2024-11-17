package com.christin.umkmmakanan.Views;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.christin.umkmmakanan.Helpers.OrderProductAdapter;
import com.christin.umkmmakanan.Retrofit.Order;
import com.christin.umkmmakanan.Retrofit.OrderProduct;
import com.christin.umkmmakanan.R;

import java.util.List;

public class OrderSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Ambil data order dari Intent
        Order order = (Order) getIntent().getSerializableExtra("ORDER_KEY");
        List<OrderProduct> orderProducts = order.getProduct();

        // Set Adapter
        OrderProductAdapter adapter = new OrderProductAdapter(orderProducts);
        recyclerView.setAdapter(adapter);
    }
}