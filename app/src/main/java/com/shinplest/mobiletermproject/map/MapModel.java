package com.shinplest.mobiletermproject.map;

import android.util.Log;

import com.shinplest.mobiletermproject.map.interfaces.MapFragmentView;
import com.shinplest.mobiletermproject.map.interfaces.PathRetrofitInterface;
import com.shinplest.mobiletermproject.map.models.PathResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shinplest.mobiletermproject.ApplicationClass.getRetrofit;

public class MapModel {
    private final MapFragmentView mMapFragmentView;
    private final PathRetrofitInterface mPathRetrofitInterface = getRetrofit().create(PathRetrofitInterface.class);
    private final String TAG = MapModel.class.getSimpleName();

    public MapModel(MapFragmentView mMapFragmentView) {
        this.mMapFragmentView = mMapFragmentView;
    }

    void getPathData() {
        mPathRetrofitInterface.getPathInfo("data", "GetFeature", "LT_L_FRSTCLIMB", "5899463B-A474-3502-AD62-14F8C481A43C",
                "http://com.shinplest.mobiletermproject", "BOX(127.057, 37.47, 127.06, 37.5)").enqueue(new Callback<PathResponse>() {
            @Override
            public void onResponse(Call<PathResponse> call, Response<PathResponse> response) {
                final PathResponse pathResponse = response.body();
                Log.d(TAG, "api 통신 상태 " + pathResponse.getResponse().getStatus());
                mMapFragmentView.getPathdataSuccess(pathResponse);
            }

            @Override
            public void onFailure(Call<PathResponse> call, Throwable t) {
                Log.d("test", "fail");
            }
        });
    }
}
