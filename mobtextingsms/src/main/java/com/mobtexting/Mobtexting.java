package com.mobtexting;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Mobtexting {
    private static final String TAG = "Communicator";
    private static final String SERVER_URL = "http://api.mobtexting.com";
    private Retrofit retrofit;
    private String api_key1, sender_id;
    private Context context;

    /**
     * @param context
     */
    public Mobtexting(Context context) {
        this.context = context;
    }

    /**
     * send SMS using POST method
     *
     * @param message
     * @param mobile_no
     * @param mobtextingInterface
     */
    public void sendSMS(String message, String mobile_no, final MobtextingInterface mobtextingInterface) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            api_key1 = bundle.getString("mobtexting.api_key");
            sender_id = bundle.getString("mobtexting.sender_id");
        } catch (Exception e) {
            mobtextingInterface.onError(new ModelError("100", "API key", "Dear developer. Don't forget to configure <meta-data android:name=\"mobtexting.api_key\" android:value=\"testValue\"/> in your AndroidManifest.xml file."));
            return;
        }
        if (api_key1 != null && !api_key1.equals("")) {
            if (sender_id != null && !sender_id.equals("")) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                httpClient.addInterceptor(logging);

                retrofit = new Retrofit.Builder()
                        .client(httpClient.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(SERVER_URL)
                        .build();

                Interface service = retrofit.create(Interface.class);

                Call<ServerResponse> call = service.post(api_key1, message, mobile_no, sender_id);

                call.enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                        // response.isSuccessful() is true if the response code is 2xx
                        if (response.isSuccessful()) {
                            Log.e(TAG, response.body().toString());
                            mobtextingInterface.onResponse(response.body());
                        } else {
                            try {
                                Converter<ResponseBody, ModelError> errorConverter = retrofit.responseBodyConverter(ModelError.class, new Annotation[0]);
                                ModelError error = errorConverter.convert(response.errorBody());
                                mobtextingInterface.onError(error);
                            } catch (IOException e) {
                                e.printStackTrace();
                                mobtextingInterface.onError(new ModelError("500", "Something Went Wrong!", "Check your internet connection!/parsing Json exception"));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ServerResponse> call, Throwable t) {
                        mobtextingInterface.onError(new ModelError("500", "No Internet", "Check your internet connection!"));
                    }
                });
            } else {
                mobtextingInterface.onError(new ModelError("100", "Sender ID", "Dear developer. Don't forget to configure <meta-data android:name=\"mobtexting.sender_id\" android:value=\"testValue\"/> in your AndroidManifest.xml file."));
            }
        } else {
            mobtextingInterface.onError(new ModelError("100", "API key", "Dear developer. Don't forget to configure <meta-data android:name=\"mobtexting.api_key\" android:value=\"testValue\"/> in your AndroidManifest.xml file."));
        }
    }
}

