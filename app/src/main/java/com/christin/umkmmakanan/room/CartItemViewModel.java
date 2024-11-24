package com.christin.umkmmakanan.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CartItemViewModel extends AndroidViewModel {
    private CartRepository mRepository;
    private LiveData<List<CartItem>> mAllCartItem;
    private int total;

    public CartItemViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CartRepository(application);
        mAllCartItem = mRepository.getCartItemOrderByAdminId();

    }

    public LiveData<List<CartItem>> getAllCartItem(){
        return mAllCartItem;
    }

    public void insert(CartItem cartItem){
        mRepository.insert(cartItem);
    }
    public void delete(CartItem cartItem){
        mRepository.delete(cartItem);
    }
    public void updateQty(Integer Quantity, Integer Total, Integer Id){
        mRepository.updateQty(Quantity,Total,Id);
    }

}
