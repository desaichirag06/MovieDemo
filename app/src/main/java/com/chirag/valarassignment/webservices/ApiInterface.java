package com.chirag.valarassignment.webservices;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiInterface {
    @GET
    Call<ResponseBody> callGetMethod(@Url String url);
}
