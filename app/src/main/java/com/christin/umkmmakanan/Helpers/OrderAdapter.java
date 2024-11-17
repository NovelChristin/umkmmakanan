package com.christin.umkmmakanan.Helpers;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.christin.umkmmakanan.R;
import com.christin.umkmmakanan.Retrofit.Order;
import com.christin.umkmmakanan.Retrofit.OrderProduct;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView invoiceIdTextView;
        TextView totalPaymentTextView;
        TextView productNameTextView; // Tambahkan TextView untuk nama produk
        TextView productQuantityTextView; // Tambahkan TextView untuk kuantitas produk

        public OrderViewHolder(View itemView) {
            super(itemView);
            invoiceIdTextView = itemView.findViewById(R.id.text_invoice_id);
            totalPaymentTextView = itemView.findViewById(R.id.text_total_payment);
            productNameTextView = itemView.findViewById(R.id.text_product_name); // Misalnya, nama produk
            productQuantityTextView = itemView.findViewById(R.id.text_product_quantity); // Misalnya, kuantitas produk
        }
    }
}

