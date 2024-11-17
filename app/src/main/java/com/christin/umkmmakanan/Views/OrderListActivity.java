package com.christin.umkmmakanan.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.christin.umkmmakanan.Helpers.OrderAdapter;
import com.christin.umkmmakanan.Helpers.Utils;
import com.christin.umkmmakanan.R;
import com.christin.umkmmakanan.Retrofit.Order;
import com.christin.umkmmakanan.Retrofit.OrderResponse;
import com.christin.umkmmakanan.Retrofit.RestApi;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;

public class OrderListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
    }
}