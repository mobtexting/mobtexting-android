package com.mobtexting;
import android.util.Log;

import com.mobtexting.event.ErrorEvent;
import com.mobtexting.event.ServerEvent;
import com.squareup.otto.Produce;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Communicator {
    private static  final String TAG = "Communicator";
    private static final String SERVER_URL = "http://api.mobtexting.com/v1/sms";

    public void loginPost(String apikey, String message,String mobile_no,String senderId){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();

        Interface service = retrofit.create(Interface.class);

        Call<ServerResponse> call = service.post(apikey,message,mobile_no,senderId);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                // response.isSuccessful() is true if the response code is 2xx
                BusProvider.getInstance().post(new ServerEvent(response.body()));
                Log.e(TAG,"Success");
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                BusProvider.getInstance().post(new ErrorEvent(-2,t.getMessage()));
            }
        });
    }

    public void loginGet(String username, String password){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();

        Interface service = retrofit.create(Interface.class);

        Call<ServerResponse> call = service.get("login",username,password);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                // response.isSuccessful() is true if the response code is 2xx
                BusProvider.getInstance().post(new ServerEvent(response.body()));
                Log.e(TAG,"Success");
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                BusProvider.getInstance().post(new ErrorEvent(-2,t.getMessage()));
            }
        });
    }



    @Produce
    public ServerEvent produceServerEvent(ServerResponse serverResponse) {
        return new ServerEvent(serverResponse);
    }

    @Produce
    public ErrorEvent produceErrorEvent(int errorCode, String errorMsg) {
        return new ErrorEvent(errorCode, errorMsg);
    }
}

