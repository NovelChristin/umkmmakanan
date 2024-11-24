package com.christin.umkmmakanan.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.Collection;
import java.util.List;
@Dao
public interface CartItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CartItem cartItem);

    @Query("DELETE FROM cart_table")
    void deleteAll();

    @Query("SELECT * FROM cart_table")
    LiveData<List<CartItem>> getAllCartItem();

    @Query("DELETE FROM cart_table WHERE _id = :id")
    void delete(String id);

    @Query("SELECT SUM(total) FROM cart_table")
    List<Integer> getTotal();

    @Query("UPDATE cart_table SET quantity = :quantity,total = :total WHERE _id = :id")
    void updateQuantity(Integer quantity, Integer total,Integer id);

    @Query("SELECT * FROM cart_table ORDER BY adminId ASC")
    LiveData<List<CartItem>> getCartItemsByAdminId();

    @Query("SELECT SUM(total) FROM cart_table WHERE adminId=:adminId")
    List<Integer> getTotalByAdminId(String adminId);
}
