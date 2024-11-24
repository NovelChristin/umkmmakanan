package com.christin.umkmmakanan.room;

import static androidx.room.Room.databaseBuilder;

import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CartItem.class}, version = 1, exportSchema = false)
public abstract class CartRoomDatabase extends RoomDatabase {
    public abstract CartItemDao cartItemDao();

    private static volatile CartRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static CartRoomDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (CartRoomDatabase.class){
                if(INSTANCE==null){
                    INSTANCE = databaseBuilder(context.getApplicationContext(), CartRoomDatabase.class, "cart_database").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
