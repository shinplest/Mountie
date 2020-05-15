package com.shinplest.mobiletermproject;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.Tm128;
import com.naver.maps.geometry.Utmk;
import com.shinplest.mobiletermproject.parsing.Geometry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import kr.hyosang.coordinate.*;
<<<<<<< HEAD
=======

>>>>>>> upstream/master

public class ApplicationClass extends Application {

    // 테스트 서버 주소
    public static String BASE_URL = "http://apis.newvement.com/";
    public static SharedPreferences sSharedPreferences = null;
    // SharedPreferences 키 값
    public static String TAG = "Mountie";
    // Retrofit 인스턴스
    public static Retrofit retrofit;

    public static ArrayList<List<LatLng>> testlatlng = new ArrayList<>();

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ArrayList<LatLng> convertTmToLatLng(Geometry geometry) {
        double N = 0;
        double E = 0;
        ArrayList<LatLng> latLngArrayList = new ArrayList<>();

        ArrayList<ArrayList<Double>> path = geometry.getPath().get(0);
        Log.d("ApplicationClass", "path size: " + path.size());

        for (int i = 0; i < path.size(); i++) {

            E = path.get(i).get(0);
            N = path.get(i).get(1);
            //파싱한 기존 데이터
            Log.d("ApplicationClass", "latitude: " + E);
            Log.d("ApplicationClass", "longtitude: " + N);

            //희진님dl 찾은 라이브러리로 좌표계 별 변환하는 코드
            CoordPoint pt = new CoordPoint(E, N);
            CoordPoint ktm = TransCoord.getTransCoord(pt, TransCoord.COORD_TYPE_TM, TransCoord.COORD_TYPE_KTM);
            Tm128 tm128 = new Tm128(ktm.x, ktm.y);
            Log.d("ApplicationClass", "ktm to tm latitude: " +tm128.toLatLng().latitude);
            Log.d("ApplicationClass", "ktm to tm longtitude: " + tm128.toLatLng().longitude);

            latLngArrayList.add(tm128.toLatLng());
        }
        return latLngArrayList;
    }

}
