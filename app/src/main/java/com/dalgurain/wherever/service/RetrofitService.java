package com.dalgurain.wherever.service;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitService {
    @Multipart
    @POST("/api/report/register")
    Call<ResponseBody> ReportUpload(
        @Part MultipartBody.Part record,
        @Part MultipartBody.Part location
    );
}
