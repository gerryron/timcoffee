package com.example.timcoffee.api;

import com.example.timcoffee.model.LoginRequestBody;
import com.example.timcoffee.model.RegisterRequestBody;
import com.example.timcoffee.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIInterface {

    @POST("users/login")
    Call<User> loginResponse(
            @Body LoginRequestBody requestBody
    );

    @POST
    Call<User> registerResponse(
            @Body RegisterRequestBody requestBody
    );

}
