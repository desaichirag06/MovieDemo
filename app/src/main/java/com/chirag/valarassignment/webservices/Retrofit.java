package com.chirag.valarassignment.webservices;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {

    private RequestBody bodyRequest;
    private Context context;
    private String baseURL;
    private String endPoint, endPointExtra = "";
    private HashMap<String, String> params = new HashMap<>();
    private HashMap<String, String> headerMap = new HashMap<>();
    JSONObject jsonObject;

    private Retrofit(Context context) {
        this.context = context;
    }

    /**
     * @param context
     * @return Instance of this class
     * create instance of this class
     */
    public static Retrofit with(Context context) {
        return new Retrofit(context);
    }

    private static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor(message -> Log.d("Log", message));
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    /**
     * @param baseUrl
     * @return Instance
     * set Base Url for temporary
     * optional method if you set default Base URL in APIs class
     */
    public Retrofit setUrl(String baseUrl) {
        this.baseURL = baseUrl;
        return this;
    }

    /**
     * @param endPoint
     * @return Instance
     * set Endpoint when call every time
     */
    public Retrofit setAPI(String endPoint) {
        this.endPoint = endPoint;
        Log.e("URL", APIs.BASE_URL + endPoint);
        return this;
    }

    /**
     * @param token
     * @return Instance
     * set Header when call every time
     */
    public Retrofit setHeader(String token) {
        headerMap.put("Authorization", token);
        Log.e("header", token);
        return this;
    }

    /**
     * @param headerMap
     * @return Instance
     * set Header when call every time
     */
    public Retrofit setHeader(HashMap<String, String> headerMap) {
        this.headerMap = headerMap;
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            Log.e("header", entry.getKey() + "\t" + entry.getValue());
        }
        return this;
    }

    /**
     * @param params
     * @return Call
     * to set request parameter
     */
    public Retrofit setGetParameters(HashMap<String, String> params) {
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                Log.e("params", entry.getKey() + "\t" + entry.getValue());
                endPointExtra = endPointExtra.concat(endPointExtra.contains("?") ? "&" : "?").concat(entry.getKey()).concat("=").concat(entry.getValue());
            }
            Log.e("EndpointExtra: ", endPointExtra);
        }
        return this;
    }

    /**
     * @param params
     * @return Call
     * to set request parameter
     */
    public Retrofit setParameters(HashMap<String, String> params) {
        this.params = params;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            Log.e("params", entry.getKey() + "\t" + entry.getValue());
        }
        return this;
    }

    /**
     * @param jsonObject
     * @return Call
     * to set request parameter
     */
    public Retrofit setParameters(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        return this;
    }

    public ApiInterface getAPIInterface() {
        return new retrofit2.Retrofit.Builder()
                .baseUrl(baseURL != null ? baseURL : APIs.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiInterface.class);
    }

    public void setCallBackListener(JSONCallback listener) {
        makeCall().enqueue(listener);
    }

    private Call<ResponseBody> makeCall() {
        Call<ResponseBody> call;

        OkHttpClient client = new OkHttpClient
                .Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(provideHttpLoggingInterceptor())
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = chain.request().newBuilder()
                                //.header("Authorization", "token " + generateToken(original.url().toString(), original.method()))
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();

        ApiInterface APIInterface = new retrofit2.Retrofit.Builder()
                .baseUrl(baseURL != null ? baseURL : APIs.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiInterface.class);

        call = APIInterface.callGetMethod(endPoint.concat(endPointExtra));

        return call;
    }
}
