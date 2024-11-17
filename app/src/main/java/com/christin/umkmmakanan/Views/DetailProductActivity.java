package com.christin.umkmmakanan.Views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.christin.umkmmakanan.Helpers.Utils;
import com.christin.umkmmakanan.Retrofit.Product;
import com.christin.umkmmakanan.R;
import com.squareup.picasso.Picasso;

public class DetailProductActivity extends AppCompatActivity {

    private ImageView productImage;
    private TextView productName;
    private TextView productDescription;
    private TextView productPrice;

    private TextView textQuantity;
    private Button buttonIncrease; // Tombol tambah
    private Button buttonDecrease; // Tombol kurang

    private int quantity = 1; // Inisialisasi kuantitas

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        productImage = findViewById(R.id.detail_product_image);
        productName = findViewById(R.id.detail_product_name);
        productDescription = findViewById(R.id.detail_product_description);
        productPrice = findViewById(R.id.detail_product_price);

        textQuantity = findViewById(R.id.text_quantity); // Inisialisasi TextView kuantitas
        buttonIncrease = findViewById(R.id.button_increase); // Inisialisasi tombol tambah
        buttonDecrease = findViewById(R.id.button_decrease); // Inisialisasi tombol kurang


        // Ambil objek Product dari Intent
        Product product = Utils.receiveProduct(getIntent(), this);

        // Tampilkan data produk
        if (product != null) {
            productName.setText(product.getName());
            productDescription.setText(product.getDescription());
            productPrice.setText("Rp " + product.getPrice());
            Picasso.get().load(Utils.base_url + product.getPicture()).into(productImage);
        } else {
            Log.e("DetailProductActivity", "Product is null");
            Utils.show(DetailProductActivity.this, "Produk tidak valid.");
        }

        // Set listener untuk tombol tambah
        buttonIncrease.setOnClickListener(v -> {
            quantity++;
            textQuantity.setText(String.valueOf(quantity));
        });

        // Set listener untuk tombol kurang
        buttonDecrease.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                textQuantity.setText(String.valueOf(quantity));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_product, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                // Arahkan ke CartActivity atau lakukan tindakan lain
                Utils.openActivity(DetailProductActivity.this, CartActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}