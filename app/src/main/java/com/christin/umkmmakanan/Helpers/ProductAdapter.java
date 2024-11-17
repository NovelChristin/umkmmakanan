package com.christin.umkmmakanan.Helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.christin.umkmmakanan.Retrofit.Product;
import com.christin.umkmmakanan.Views.DetailProductActivity;
import com.christin.umkmmakanan.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;// Menyimpan salinan lengkap dari daftar produk
    private Context context;

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product, parent, false);
        return new ProductViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productAdmin.setText(product.getAdmin());
        holder.productPrice.setText("Rp. " + product.getPrice());

        // Menggunakan Picasso untuk memuat gambar
        Picasso.get()
                .load(Utils.base_url + product.getPicture())
                .placeholder(R.drawable.image_not_found)
                .error(R.drawable.image_not_found)
                .into(holder.productImage);

        holder.itemView.setOnClickListener(v -> {
            Utils.sendProductToActivity(context, product, DetailProductActivity.class);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productPrice;
        ImageView productImage;
        TextView productAdmin;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productAdmin = itemView.findViewById(R.id.product_admin);
            productPrice = itemView.findViewById(R.id.product_price);
            productImage = itemView.findViewById(R.id.product_image);
        }
    }
}


