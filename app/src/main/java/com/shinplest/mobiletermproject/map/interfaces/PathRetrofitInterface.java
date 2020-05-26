package com.shinplest.mobiletermproject.map.interfaces;

import com.shinplest.mobiletermproject.map.models.PathResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PathRetrofitInterface {
    @GET("data")
    Call<PathResponse> getPathInfo(@Query("service") String service, @Query("request") String request, @Query("data") String data,
                                   @Query("key") String key, @Query("domain") String domain, @Query("geomFilter") String geomFilter, @Query("size") int size);
}
