package com.shinplest.mobiletermproject.map;

import android.util.Log;

import com.shinplest.mobiletermproject.map.interfaces.MapFragmentView;
import com.shinplest.mobiletermproject.map.interfaces.PathRetrofitInterface;
import com.shinplest.mobiletermproject.map.models.PathResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shinplest.mobiletermproject.ApplicationClass.getRetrofit;

public class MapService {
    private final MapFragmentView mMapFragmentView;
    private final PathRetrofitInterface mPathRetrofitInterface = getRetrofit().create(PathRetrofitInterface.class);
    private final String TAG = MapService.class.getSimpleName();

    public MapService(MapFragmentView mMapFragmentView) {
        this.mMapFragmentView = mMapFragmentView;
    }

    //범위를 가지고 등산로 가져오는 메소드
    void getPathData(Double x1, Double y1, Double x2, Double y2) {
        Log.d(TAG, "BOX(" + x1 + ", " + y1 + ", " + x2 + ", " + y2 + ")");
        mPathRetrofitInterface.getPathInfo("data", "GetFeature", "LT_L_FRSTCLIMB", "5899463B-A474-3502-AD62-14F8C481A43C",
                "http://com.shinplest.mobiletermproject", "BOX(" + x1 + ", " + y1 + ", " +  x2 + ", " + y2 + ")", 300).enqueue(new Callback<PathResponse>() {
            @Override
            public void onResponse(Call<PathResponse> call, Response<PathResponse> response) {
                final PathResponse pathResponse = response.body();
                if(pathResponse != null){
                    if (pathResponse.getResponse().getResult() != null)
                        mMapFragmentView.getPathdataSuccess(pathResponse);
                    else {
                        Log.d(TAG, "반경 내 등산로가 없음");
                    }
                }
            }

            @Override
            public void onFailure(Call<PathResponse> call, Throwable t) {
                Log.d("TAG", "api 통신 상태 문제 있음, 오류 검토"+t.getMessage());
            }
        });
    }
}
