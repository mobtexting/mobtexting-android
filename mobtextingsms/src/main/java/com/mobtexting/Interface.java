package com.mobtexting;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Interface {

    @FormUrlEncoded
    @POST("/v1/sms/")
    Call<ServerResponse> post(
            @Field("api_key") String method,
            @Field("message") String username,
            @Field("mobile_no") String password,
            @Field("sender_id") String sender_id
    );

    @GET("/v1/sms/")
    Call<ServerResponse> get(
            @Query("method") String method,
            @Query("username") String username,
            @Query("password") String password
    );

}
