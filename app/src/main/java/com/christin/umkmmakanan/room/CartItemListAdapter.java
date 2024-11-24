package com.christin.umkmmakanan.room;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.christin.umkmmakanan.Helpers.Utils;
import com.christin.umkmmakanan.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartItemListAdapter extends RecyclerView.Adapter<CartItemViewHolder> {
    public CartItemListAdapter(CartItemViewHolder.CartClickedListener cartClickedListener) {
        this.cartClickedListener = cartClickedListener;
    }
    TextView txtProductName;
    TextView txtQuantity;
    TextView txtPrice;
    TextView txtToko;
    ImageView imgProduct;
    Button btnAdd,btnSubstract,btnRemove;
    List<CartItem> items;
    private CartItemViewHolder.CartClickedListener cartClickedListener;

    public void setCartClickedListener(CartItemViewHolder.CartClickedListener cartClickedListener) {
        this.cartClickedListener = cartClickedListener;
    }
    public void setItems(List<CartItem> items){
        this.items = items;
    }
    public List<CartItem> getItems(){
        return this.items;
    }

    public interface CartClickedListener{
        void onDeleteClicked(CartItem cartItem);
        void onPlusClicked(CartItem cartItem);
        void onMinusClicked(CartItem cartItem);
    }

    @Override
    public CartItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart,parent,false);
        txtProductName = itemView.findViewById(R.id.cart_product_name);
        txtQuantity = itemView.findViewById(R.id.cart_product_quantity);
        imgProduct = itemView.findViewById(R.id.cart_product_image);
        txtPrice = itemView.findViewById(R.id.cart_product_price);
        txtToko = itemView.findViewById(R.id.cart_nama_toko);
        btnAdd = itemView.findViewById(R.id.button_increase);
        btnSubstract = itemView.findViewById(R.id.button_decrease);
        btnRemove = itemView.findViewById(R.id.button_remove);
        return new CartItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartItemViewHolder holder, int position) {
        CartItem current = getItem(position);
        txtProductName.setText(current.getProductName());
        txtQuantity.setText(String.valueOf(current.getQuantity()));
        txtPrice.setText(String.valueOf(current.getPrice()));
        txtToko.setText(current.getAdminName());
        Picasso.get()
                .load(Utils.base_url + current.getPicture())
                .placeholder(R.drawable.image_not_found)
                .error(R.drawable.image_not_found)
                .into(imgProduct);
        btnAdd.setOnClickListener( v->{
            cartClickedListener.onPlusClicked(current);
        });
        btnSubstract.setOnClickListener( v->{
            cartClickedListener.onMinusClicked(current);
        });
        btnRemove.setOnClickListener( v->{
            cartClickedListener.onDeleteClicked(current);
        });
    }

    private CartItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        if(items==null){
            return 0;
        }
        return items.size();
    }


}