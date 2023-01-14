package com.dalgurain.wherever.controller;

import com.dalgurain.wherever.service.RetrofitService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitController {
    Retrofit retrofit;
    RetrofitService service;
    static String BASE_URL = "http://3.37.21.131:51695/";

    public static RetrofitController retrofitController = new RetrofitController();

    private RetrofitController() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(RetrofitService.class);
    }

    public static RetrofitController getInstance() {
        return retrofitController;
    }

    public RetrofitService getService() {
        return service;
    }
}
