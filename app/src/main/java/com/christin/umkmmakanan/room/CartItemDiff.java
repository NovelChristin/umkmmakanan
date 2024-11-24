package com.christin.umkmmakanan.room;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class CartItemDiff extends DiffUtil.ItemCallback<CartItem>{
        @Override
        public boolean areItemsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem){
            return oldItem == newItem;
        }
        @Override
        public boolean areContentsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem){
            return oldItem.getProductId().equals(newItem.getProductId());
        }
    }
