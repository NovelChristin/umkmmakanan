package com.christin.umkmmakanan.room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.christin.umkmmakanan.Helpers.Utils;
import com.christin.umkmmakanan.R;
import com.christin.umkmmakanan.Views.CartActivity;
import com.christin.umkmmakanan.Views.MainActivity;
import com.squareup.picasso.Picasso;

public class CartItemViewHolder extends RecyclerView.ViewHolder{
    TextView txtProductName;
    TextView txtQuantity;
    TextView txtPrice;
    ImageView imgProduct;
    Button btnAdd,btnSubstract,btnRemove;

    private CartClickedListener cartClickedListener;

    public void setCartClickedListener(CartClickedListener cartClickedListener) {
        this.cartClickedListener = cartClickedListener;
    }

    public interface CartClickedListener{
        void onDeleteClicked(CartItem cartItem);
        void onPlusClicked(CartItem cartItem);
        void onMinusClicked(CartItem cartItem);
    }

    public CartItemViewHolder(@NonNull View itemView) {
        super(itemView);
        txtProductName = itemView.findViewById(R.id.cart_product_name);
        txtQuantity = itemView.findViewById(R.id.cart_product_quantity);
        imgProduct = itemView.findViewById(R.id.cart_product_image);
        txtPrice = itemView.findViewById(R.id.cart_product_price);
        btnAdd = itemView.findViewById(R.id.button_increase);
        btnSubstract = itemView.findViewById(R.id.button_decrease);
        btnRemove = itemView.findViewById(R.id.button_remove);

    }

    public void bind(CartItem cartItem){
        txtProductName.setText(cartItem.getProductName());
        txtQuantity.setText(String.valueOf(cartItem.getQuantity()));
        txtPrice.setText(String.valueOf(cartItem.getPrice()));
        Picasso.get()
                .load(Utils.base_url + cartItem.getPicture())
                .placeholder(R.drawable.image_not_found)
                .error(R.drawable.image_not_found)
                .into(imgProduct);
        btnAdd.setOnClickListener( v->{
            cartClickedListener.onPlusClicked(cartItem);
        });
        btnSubstract.setOnClickListener( v->{
            cartClickedListener.onMinusClicked(cartItem);
        });
        btnRemove.setOnClickListener( v->{
            cartClickedListener.onDeleteClicked(cartItem);
        });
    }

    static CartItemViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart,parent,false);
        return new CartItemViewHolder(view);
    }
}

