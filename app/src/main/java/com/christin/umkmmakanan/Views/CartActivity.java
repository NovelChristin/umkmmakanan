package com.christin.umkmmakanan.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.christin.umkmmakanan.Helpers.Utils;
import com.christin.umkmmakanan.R;
import com.christin.umkmmakanan.Retrofit.Order;
import com.christin.umkmmakanan.Retrofit.OrderProduct;
import com.christin.umkmmakanan.Retrofit.Response;
import com.christin.umkmmakanan.Retrofit.RestApi;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;

public class CartActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
    }
}