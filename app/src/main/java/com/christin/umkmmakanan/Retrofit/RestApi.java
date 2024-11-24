package com.christin.umkmmakanan.Retrofit;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RestApi {
    @GET("product")
    Call<ProductResponse> retrieveProduct(
            @Query("adminId") String adminId,
            @Query("search") String search
    );


    @GET("admin")
    Call<AdminResponse> retrieveAdmin();

    @FormUrlEncoded
    @PATCH("api/order")
    Call<Response> ratingOrder(@Field("orderId") String orderId,
                               @Field("adminId") String adminId,
                               @Field("rating") String rating);

        @GET("api/order")
    Call<OrderResponse> getOrderList(
            @Query("userId") String userId
    );

    @POST("api/order")
    Call<Response> createOrder(@Body Order order);

    @Multipart
    @PUT("api/order")
    Call<Response> updateOrderStatus(
            @Part("id") RequestBody id,
            @Part("status") RequestBody status,
            @Part MultipartBody.Part paymentProof
    );


    @FormUrlEncoded
    @POST("api/user/signin")
    Call<Response> signin(@Field("email") String email,
                          @Field("password") String password);

    @FormUrlEncoded
    @POST("api/user/signup")
    Call<Response> signup(@Field("name") String name,
                          @Field("email") String email,
                          @Field("phone") String phone,
                          @Field("password") String password,
                          @Field("passwordConfirmation") String passwordConfirmation);
}
