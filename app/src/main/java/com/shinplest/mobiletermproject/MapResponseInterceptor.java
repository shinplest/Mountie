package com.shinplest.mobiletermproject;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MapResponseInterceptor implements Interceptor {
    private final String TAG = MapResponseInterceptor.class.getSimpleName();

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Log.d(TAG, "intercept request method" + request.method());
        Log.d(TAG, "intercept request url" + request.headers());
        Log.d(TAG, "intercept connection" + chain.connection());

        Response response = chain.proceed(request);

        Log.d(TAG, "intercept response body" + response.body());

        return response;
    }
}
