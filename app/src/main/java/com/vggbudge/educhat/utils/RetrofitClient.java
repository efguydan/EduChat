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

    public static Retrofit buildDatabaseRetrofit() {
        HashMap<String, String> queryParams = new HashMap<>();
        //TODO Setup authentication for Database Calls
        return build(Constants.AuthParameters.DATABASE_BASE_URL, queryParams);
    }

    public static Retrofit buildAuthenticationRetrofit() {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", Constants.AuthParameters.WEB_API_KEY);
        return build(Constants.AuthParameters.DATABASE_BASE_URL, queryParams);
    }

    public static Retrofit build(String baseUrl, HashMap<String, String> queryParams) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getHttpClient(queryParams))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static OkHttpClient getHttpClient(HashMap<String, String> queryParams) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    HttpUrl originalHttpUrl = original.url();
                    HttpUrl.Builder urlBuilder = originalHttpUrl.newBuilder();
                    for (String key : queryParams.keySet()) {
                        urlBuilder.addQueryParameter(key, queryParams.get(key));
                    }
                    HttpUrl url = originalHttpUrl.newBuilder()
                            .build();
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
