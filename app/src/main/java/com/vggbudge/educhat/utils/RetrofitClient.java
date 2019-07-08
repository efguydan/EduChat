package com.vggbudge.educhat.utils;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static Retrofit build() {
        return new Retrofit.Builder()
                .baseUrl(Constants.AuthParameters.API_BASE_URL)
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static OkHttpClient getHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    HttpUrl originalHttpUrl = original.url();
                    HttpUrl url = originalHttpUrl.newBuilder().build();
                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .url(url);
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                })
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();
    }
}
