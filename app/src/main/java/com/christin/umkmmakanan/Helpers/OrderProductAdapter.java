package com.christin.umkmmakanan.Helpers;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.christin.umkmmakanan.R;
import com.christin.umkmmakanan.Retrofit.OrderProduct;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.ViewHolder> {
    private List<OrderProduct> orderProducts;

    public OrderProductAdapter(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_product, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderProduct orderProduct = orderProducts.get(position);
        holder.productName.setText(orderProduct.getName());
        holder.productPrice.setText("Rp. : " + String.valueOf(orderProduct.getPrice()));
        holder.productQuantity.setText("Jumlah : " + String.valueOf(orderProduct.getQuantity()));
        Picasso.get().load(Utils.base_url + orderProduct.getPicture()).into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return orderProducts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productQuantity;
        ImageView productImage;

        ViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productQuantity = itemView.findViewById(R.id.productQuantity);
            productImage = itemView.findViewById(R.id.productImage);
        }
    }
}
