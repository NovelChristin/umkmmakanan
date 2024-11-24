package com.christin.umkmmakanan.Views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.christin.umkmmakanan.room.CartItem;
import com.christin.umkmmakanan.room.CartItemViewModel;
import com.squareup.picasso.Picasso;

public class DetailProductActivity extends AppCompatActivity {

    private ImageView productImage;
    private TextView productName;
    private TextView productDescription;
    private TextView productPrice;

    private TextView textQuantity;
    private Button buttonIncrease; // Tombol tambah
    private Button buttonDecrease; // Tombol kurang
    private Button btnAddToCart;
    private int quantity = 1; // Inisialisasi kuantitas

    public static final String EXTRA_REPLY = "com.christin.umkmmakanan.REPLY";

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
        btnAddToCart = findViewById(R.id.btnAddCart);
        Product product = Utils.receiveProduct(getIntent(), this);
        btnAddToCart.setOnClickListener(view ->{
            SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
            boolean isLoggedIn = prefs.getBoolean("is_logged_in", false);
            if (!isLoggedIn) {
                // Jika belum login, arahkan ke SignInActivity
                Utils.openActivity(DetailProductActivity.this, SignInActivity.class);
            } else {
                Intent replyIntent = new Intent();
//            Toast.makeText(this, "Testing", Toast.LENGTH_SHORT).show();
                if (product != null) {
                    int total = product.getPrice() * quantity;
                    CartItem item = new CartItem(
                            0, product.getId(), product.getName(), quantity,
                            product.getPrice(), total, product.getAdminId(), product.getAdmin(), product.getPicture()
                    );

                    CartItemViewModel viewModel = new CartItemViewModel(getApplication());
                    viewModel.insert(item);
//                notifyAll();
                    Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
//                replyIntent.putExtra("productId",product.getId());
//                replyIntent.putExtra("productName",product.getName());
//                replyIntent.putExtra("price",product.getPrice());
//                replyIntent.putExtra("picture",product.getPicture());
//                replyIntent.putExtra("quantity",quantity);
                    setResult(RESULT_OK, replyIntent);
                } else {
                    setResult(RESULT_CANCELED, replyIntent);
                }
            }
        });
        // Ambil objek Product dari Intent


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