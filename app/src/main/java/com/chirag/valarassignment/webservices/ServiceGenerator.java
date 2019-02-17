package com.chirag.valarassignment.webservices;

import android.support.annotation.NonNull;

import com.chirag.valarassignment.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CertificatePinner;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    // region Constants
    private static final int DISK_CACHE_SIZE = 10 * 1024 * 1024; // 10MB
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    // endregion
    private static Retrofit.Builder retrofitBuilder
            = new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create());
    private static OkHttpClient defaultOkHttpClient
            = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .build();

    // No need to instantiate this class.
    private ServiceGenerator() {

        // used to print logs
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.interceptors().add(logging);
    }

    @NonNull
    private static CertificatePinner getCertificatePinner() {
        return new CertificatePinner.Builder()
                //.add(PARTNER_API_SANDBOX_HOST, CERTIFICATE_1)
                //.add(PARTNER_API_LIVE_HOST, CERTIFICATE_LIVE)
//                .add(PARTNER_API_LIVE_HOST, CERTIFICATE_LIVE)
                .build();
    }


    public static <S> S createService(Class<S> serviceClass, String baseUrl) {
        return createService(serviceClass, baseUrl, (Interceptor) null);
    }

    public static <S> S createService(Class<S> serviceClass, String baseUrl, String token) {
        return createService(serviceClass, baseUrl, (Interceptor) new AuthenticationInterceptor(token));
    }

    public static <S> S createService(Class<S> serviceClass, String baseUrl, Interceptor networkInterceptor) {
        OkHttpClient.Builder okHttpClientBuilder = defaultOkHttpClient.newBuilder();

        if (networkInterceptor != null) {
            okHttpClientBuilder.addNetworkInterceptor(networkInterceptor);
        }

        OkHttpClient modifiedOkHttpClient = okHttpClientBuilder
                .addInterceptor(getHttpLoggingInterceptor())
                .build();

        retrofitBuilder.client(modifiedOkHttpClient);
        retrofitBuilder.baseUrl(baseUrl);

        Retrofit retrofit = retrofitBuilder.build();
        return retrofit.create(serviceClass);
    }

    private static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return httpLoggingInterceptor;
    }


    public static class AuthenticationInterceptor implements Interceptor {

        private String authToken;

        public AuthenticationInterceptor(String token) {
            this.authToken = token;
        }

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            Request.Builder builder = original.newBuilder()
                    .header("Authorization", "Bearer " + authToken);

            Request request = builder.build();
            return chain.proceed(request);
        }
    }
}

