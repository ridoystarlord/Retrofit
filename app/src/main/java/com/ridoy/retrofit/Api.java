package com.ridoy.retrofit;

import com.ridoy.retrofit.Model.FetchUsersResponse;
import com.ridoy.retrofit.Model.LoginResponse;
import com.ridoy.retrofit.Model.PasswordResponse;
import com.ridoy.retrofit.Model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterResponse> register(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("updateuser.php")
    Call<LoginResponse> updateuser(
            @Field("id") int id,
            @Field("username") String username
    );


    @FormUrlEncoded
    @POST("updatepassword.php")
    Call<PasswordResponse> updatepass(
            @Field("email") String email,
            @Field("current") String password,
            @Field("new") String newpass
    );

    @FormUrlEncoded
    @POST("deleteaccount.php")
    Call<PasswordResponse> delete(
            @Field("id") int id
    );


    @GET("fetchusers.php")
    Call<FetchUsersResponse> fetchusers();
}
