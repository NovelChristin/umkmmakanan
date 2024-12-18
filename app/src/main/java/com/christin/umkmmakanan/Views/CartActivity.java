package com.christin.umkmmakanan.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.christin.umkmmakanan.Helpers.Utils;
import com.christin.umkmmakanan.R;
import com.christin.umkmmakanan.Retrofit.Order;
import com.christin.umkmmakanan.Retrofit.Product;
import com.christin.umkmmakanan.Retrofit.OrderProduct;
import com.christin.umkmmakanan.Retrofit.Response;
import com.christin.umkmmakanan.Retrofit.RestApi;
import com.christin.umkmmakanan.room.CartItem;
import com.christin.umkmmakanan.room.CartItemDao;
import com.christin.umkmmakanan.room.CartItemListAdapter;
import com.christin.umkmmakanan.room.CartItemViewHolder;
import com.christin.umkmmakanan.room.CartItemViewModel;
import com.christin.umkmmakanan.room.CartRepository;
import com.christin.umkmmakanan.room.CartRoomDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class CartActivity extends AppCompatActivity implements CartItemViewHolder.CartClickedListener {
    private RecyclerView cartData;
    private TextView txtCartTotal;
    private CheckBox chkCOD;
    private Button btnCheckout;
    private CartItemViewModel viewModel;
    private CartItemListAdapter adapter ;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        adapter = new CartItemListAdapter(this );
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userId = prefs.getString("user_id", null);
        setTitle(userId);
        viewModel = new CartItemViewModel(getApplication());
//        Toast.makeText(this, Integer.toString(adapter.getItemCount()), Toast.LENGTH_SHORT).show();
        cartData = findViewById(R.id.cartData);
        cartData.setLayoutManager(new LinearLayoutManager(this));
        cartData.setAdapter(adapter);
        adapter.setCartClickedListener(this);
        viewModel.getAllCartItem().observe( this, new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                adapter.setItems(cartItems);
                adapter.notifyDataSetChanged();
                int total = 0;
                for(int i=0;i<cartItems.size();i++){
                    total += cartItems.get(i).getTotal();
                }

                txtCartTotal.setText("Total : Rp."+Integer.toString(total));
            }
        });

        txtCartTotal = findViewById(R.id.txtCartTotal);

        chkCOD = findViewById(R.id.chkCOD);
        btnCheckout = findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener( view ->
                createOrder());
    }
    public void createOrder(){
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userId = prefs.getString("user_id", null);
        String userName = prefs.getString("user_name",null);
        String userPhone = prefs.getString("user_phone",null);
        List<CartItem> cartItems = adapter.getItems();
        String adminId = "";
        String adminName = "";
        List<Order> orders = new ArrayList<Order>();
        Order order = new Order();
        order.setUserId(userId);
        boolean isCod = chkCOD.isChecked();
        order.setCOD(isCod);
        order.setUserName(userName);
        order.setUserPhone(userPhone);
        order.setAdminId(cartItems.get(0).getAdminId());
        order.setAdminName(cartItems.get(0).getAdminName());
        order.setAdminPhone("0000000");
        order.setStatus("Menunggu Pembayaran");
        adminId = cartItems.get(0).getAdminId();
        adminName = cartItems.get(0).getAdminName();
        List<OrderProduct> products = new ArrayList<OrderProduct>();
//        Utils.show(CartActivity.this,"Cart Size : "+cartItems.size());
        for(int i=0;i<cartItems.size();i++){

            if(!adminId.equals(cartItems.get(i).getAdminId())) {
                products.clear();;
                orders.add(order);
                order = new Order();
                order.setUserId(userId);
                order.setCOD(isCod);
                order.setUserName(userName);
                order.setUserPhone(userPhone);
                order.setAdminId(cartItems.get(i).getAdminId());
                order.setAdminName(cartItems.get(i).getAdminName());
                order.setAdminPhone("0000000");
                order.setStatus("Menunggu Pembayaran");
                adminId = cartItems.get(i).getAdminId();
                adminName = cartItems.get(i).getAdminName();
                CartItem cart = cartItems.get(i);
                OrderProduct product =new OrderProduct(cart.getProductId(),cart.getProductName(),cart.getPrice(),
                        cart.getQuantity(),cart.getTotal(),cart.getPicture());


                products.add(product);
//                Utils.show(CartActivity.this, "Price : "+order.getTotalPayment());
            }else{
                double tmp = order.getTotalPayment();
                order.setTotalPayment(tmp+cartItems.get(i).getTotal());
                OrderProduct product =new OrderProduct(cartItems.get(i).getProductId(),cartItems.get(i).getProductName(),cartItems.get(i).getPrice(),cartItems.get(i).getQuantity(),cartItems.get(i).getTotal(),cartItems.get(i).getPicture());
                products.add(product);
                order.setProduct(products);
//                Utils.show(CartActivity.this, "Price : "+order.getTotalPayment());
            }
        }
        orders.add(order);
        Utils.show(CartActivity.this,"Total Order : "+orders.size());
        postToServer(orders);
    }
    public void postToServer(List<Order> orders){
        RestApi restApi = Utils.getClient().create(RestApi.class);
        for(int i=0;i<orders.size();i++) {
            Call<Response> call = restApi.createOrder(orders.get(i));
            final int j=i;
            call.enqueue(new Callback<Response>(){

                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    CartRepository cartRepository = new CartRepository(getApplication());
                    cartRepository.deleteAll();
                    Toast.makeText(CartActivity.this, "Order berhasil dikirim Untuk : "+orders.get(j).getAdminName(), Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    Toast.makeText(CartActivity.this, "Gagal : "+call.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onDeleteClicked(CartItem cartItem) {
//        CartItemDao dao = CartRoomDatabase.getDatabase(getApplication()).cartItemDao();
//        dao.delete(Integer.toString(cartItem.get_id()));
        viewModel.delete(cartItem);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPlusClicked(CartItem cartItem) {
        cartItem.setQuantity(cartItem.getQuantity() + 1);

//        CartItemDao dao = CartRoomDatabase.getDatabase(getApplication()).cartItemDao();
//        dao.updateQuantity(cartItem.getQuantity(), cartItem.getPrice()*cartItem.getQuantity(), cartItem.get_id());
//                notifyAll();
        viewModel.updateQty(cartItem.getQuantity(),cartItem.getPrice()*cartItem.getQuantity(),cartItem.get_id());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onMinusClicked(CartItem cartItem) {
        int qty = cartItem.getQuantity();
//        CartItemDao dao = CartRoomDatabase.getDatabase(getApplication()).cartItemDao();
        if(qty>1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            viewModel.updateQty(cartItem.getQuantity(),cartItem.getPrice()*cartItem.getQuantity(),cartItem.get_id());
//                dao.updateQuantity(cartItem.getQuantity(), cartItem.getPrice() * cartItem.getQuantity(), cartItem.get_id());
        }else{
            viewModel.delete(cartItem);
//            dao.delete(String.valueOf(cartItem.get_id()));
        }
        adapter.notifyDataSetChanged();
    }
}
