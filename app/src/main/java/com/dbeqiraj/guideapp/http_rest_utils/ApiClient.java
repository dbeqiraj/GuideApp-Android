package com.dbeqiraj.guideapp.http_rest_utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    private static final String API_URL = "https://tiranaguide.000webhostapp.com/guide/";
    private static APIPlug REST_CLIENT;

    static {
        setupRestClient();
    }

    private ApiClient() {}

    public static APIPlug getClient() {
        return REST_CLIENT;
    }

    private static void setupRestClient() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(new OkHttpClient.Builder().build())
                .build();

        REST_CLIENT = retrofit.create(APIPlug.class);
    }
}