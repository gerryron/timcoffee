package com.example.timcoffee.api;

import com.example.timcoffee.model.LoginRequestBody;
import com.example.timcoffee.model.Order;
import com.example.timcoffee.model.Product;
import com.example.timcoffee.model.RegisterRequestBody;
import com.example.timcoffee.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIInterface {

    @POST("users/login")
    Call<User> loginResponse(@Body LoginRequestBody requestBody);

    @POST("users/register")
    Call<User> registerResponse(@Body RegisterRequestBody requestBody);

    @GET("products/getAllProduct")
    Call<List<Product>> getAllProductResponse();

    @POST("order/add")
    Call<Order> postOrder(@Body Order requestBody);

    @PUT("order/updateStatus/{id}")
    Call<Order> updateOrder(
            @Path("id") int id,
            @Body String requestBody
     );

    @GET("order/getOrderQueue/{phoneNumber}")
    Call<List<Order>> getOrderByPhoneNumber(@Path("phoneNumber") String phoneNumber);
}
