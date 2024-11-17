package com.christin.umkmmakanan.Helpers;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.christin.umkmmakanan.R;
import com.christin.umkmmakanan.Retrofit.Admin;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminViewHolder> {
    private List<Admin> adminList;
    private OnAdminClickListener listener;

    public interface OnAdminClickListener {
        void onAdminClick(Admin admin);
    }

    public AdminAdapter(List<Admin> adminList, OnAdminClickListener listener) {
        this.adminList = adminList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin, parent, false);
        return new AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, int position) {
        Admin admin = adminList.get(position);
        holder.textName.setText(admin.getName());
        holder.textEmail.setText(admin.getEmail());
        holder.textAddress.setText(admin.getAddress());

        String status = admin.isOpen() ? "Buka" : "Tutup";
        holder.statusTextView.setText(status);
        holder.itemView.setOnClickListener(v -> {
            Log.i("AdminAdapter", "adminId " + admin.getId());
            listener.onAdminClick(admin);
        });
    }

    @Override
    public int getItemCount() {
        return adminList.size();
    }

    static class AdminViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textEmail, textAddress;
        TextView statusTextView;

        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);
            statusTextView = itemView.findViewById(R.id.text_view_status);
            textName = itemView.findViewById(R.id.text_name);
            textEmail = itemView.findViewById(R.id.text_email);
            textAddress = itemView.findViewById(R.id.text_address);
        }
    }
}

