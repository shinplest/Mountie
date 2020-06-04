package com.shinplest.mobiletermproject;

import android.app.Application;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.naver.maps.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApplicationClass extends Application {

    // 테스트 서버 주소
    public static String BASE_URL = "http://api.vworld.kr/req/";
    public static SharedPreferences sSharedPreferences = null;
    // SharedPreferences 키 값
    public static String TAG = "Mountie";
    // Retrofit 인스턴스
    public static Retrofit retrofit;

    public static ArrayList<List<LatLng>> testlatlng = new ArrayList<>();

    public static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.level(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .addNetworkInterceptor(interceptor)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
