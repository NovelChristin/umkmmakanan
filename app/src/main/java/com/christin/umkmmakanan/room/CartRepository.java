package com.christin.umkmmakanan.room;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class CartRepository {

    private CartItemDao mCartItemDao;
    private LiveData<List<CartItem>> mAllCartItem;

    public CartRepository(Application application){
        CartRoomDatabase db = CartRoomDatabase.getDatabase(application);
        mCartItemDao = db.cartItemDao();
        mAllCartItem = mCartItemDao.getAllCartItem();
    }

    LiveData<List<CartItem>> getAllCartItem(){
        return mAllCartItem;
    }

    public void deleteAll(){
        CartRoomDatabase.databaseWriteExecutor.execute(()->{
            mCartItemDao.deleteAll();
        });
    }

    void insert(CartItem cartItem){
        CartRoomDatabase.databaseWriteExecutor.execute(()->{
            mCartItemDao.insert(cartItem);
        });
    }
    void delete(CartItem cartItem){
        CartRoomDatabase.databaseWriteExecutor.execute(()->{
            mCartItemDao.delete(Integer.toString(cartItem.get_id()));
        });
    }
    public List<Integer> getTotal(){
        List<Integer> total = new ArrayList<Integer>();
       CartRoomDatabase.databaseWriteExecutor.execute(()->{
           total.addAll(mCartItemDao.getTotal());
       });
       return total;
    }
    public void updateQty(Integer Quantity, Integer Total, Integer Id) {
        mCartItemDao.updateQuantity(Quantity, Total, Id);
    }

    public LiveData<List<CartItem>> getCartItemOrderByAdminId(){
        LiveData<List<CartItem>> cartItems;
        cartItems=mCartItemDao.getCartItemsByAdminId();
        return cartItems;
    }
}
