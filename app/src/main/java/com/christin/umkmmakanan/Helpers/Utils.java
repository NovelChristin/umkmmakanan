package com.christin.umkmmakanan.Helpers;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.christin.umkmmakanan.Retrofit.Admin;
import com.christin.umkmmakanan.Retrofit.Order;
import com.christin.umkmmakanan.Retrofit.Product;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {
    public static  final String base_url = "http://10.0.2.2:5002/";
    private static Retrofit retrofit;
    public static Retrofit getClient(){
        if(retrofit == null){
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return  retrofit;
    }

    public static void openActivity(Context c, Class clazz){
        Intent intent = new Intent(c, clazz);
        c.startActivity(intent);
    }

    public static void sendProductToActivity(Context c, Product product,
                                                    Class clazz){
        Intent i=new Intent(c,clazz);
        i.putExtra("PRODUCT_KEY", product);
        c.startActivity(i);
    }

    public  static Product receiveProduct(Intent intent, Context c){
        try {
            return (Product) intent.getSerializableExtra("PRODUCT_KEY");
        }catch (Exception e){
            e.printStackTrace();
            show(c,"PRODUCT_KEY ERROR: "+e.getMessage());
        }
        return null;
    }

    public static void sendOrderToActivity(Context c, Order order,
                                           Class clazz){
        Intent i=new Intent(c,clazz);
        i.putExtra("ORDER_KEY", order);
        c.startActivity(i);
    }

    public  static Order receiveOrder(Intent intent, Context c){
        try {
            return (Order) intent.getSerializableExtra("ORDER_KEY");
        }catch (Exception e){
            e.printStackTrace();
            show(c,"ORDER_KEY ERROR: "+e.getMessage());
        }
        return null;
    }

    public static void sendAdminToActivity(Context c, Admin admin,
                                           Class clazz){
        Intent i=new Intent(c,clazz);
        i.putExtra("ADMIN_KEY", admin);
        c.startActivity(i);
    }

    public  static Admin receiveAdmin(Intent intent, Context c){
        try {
            return (Admin) intent.getSerializableExtra("ADMIN_KEY");
        }catch (Exception e){
            e.printStackTrace();
            show(c,"ADMIN_KEY ERROR: "+e.getMessage());
        }
        return null;
    }

    public static void showProgressBar(ProgressBar pb){
        pb.setVisibility(View.VISIBLE);
    }

    public static void show(Context c, String message){
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }

    public static void hideProgressBar(ProgressBar pb){
        pb.setVisibility(View.GONE);
    }
}
