package com.example.gaurav.gitfetchapp;

import android.app.Service;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by GAURAV on 22-07-2016.
 */
public class ServiceGenerator {
    public static final String WEB_BASE_URL = "https://github.com/"; //"http://api.github.com/";
    public static final String API_BASE_URL = "https://api.github.com";
    private static final String TAG = ServiceGenerator.class.getName();
    private static final int CONNECTION_TIMEOUT = 20;

    // Add the interceptor to OkHttpClient
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    //builder.interceptors().add(interceptor);
    //OkHttpClient client = builder.build();

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    public static <S> S createService(Class<S> serviceClass, String username, String password) {
        if (username != null && password != null) {
            String credentials = username + ":" + password;
            final String basic =
                    "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

            /*Request request = new Request.Builder()
                    .header("Authorization", basic)
                    .header("Accept", "application/json")
                    .build();*/

            //Log.v(TAG,basic);
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", basic)
                            .header("Accept", "application/json")
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    //Log.v(TAG,request.header("Authorization"));
                    return chain.proceed(request);
                }
            });
            //httpClient.readTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);
        }
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = httpClient.addInterceptor(interceptor).build();
        Retrofit retrofit = builder
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass, AccessToken token) {
        if (token != null) {
            final AccessToken newToken = token;
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Log.v(TAG,"In Access service");
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Accept", "application/json")
                            .header("Authorization",
                                    " token " + newToken.getAccessToken())
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    Log.v(TAG,request.header("Authorization"));
                    return chain.proceed(request);
                }
            });
        }

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = httpClient.addInterceptor(interceptor).build();
        Retrofit retrofit = builder
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build();
        return retrofit.create(serviceClass);
    }



}
