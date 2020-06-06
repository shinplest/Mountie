package com.shinplest.mobiletermproject.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.health.PackageHealthStats;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.PathOverlay;
import com.naver.maps.map.util.FusedLocationSource;
import com.shinplest.mobiletermproject.R;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.shinplest.mobiletermproject.map.MapFragmentMain.allPaths;

public class Navigation extends AppCompatActivity implements OnMapReadyCallback {
    private PathOverlay pathOverlay;
    private ArrayList<LatLng> pathCoords;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // 권한 거부됨
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation);
        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        int index = getIntent().getExtras().getInt("pathIndex");

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.naviMap);
        mapFragment.getMapAsync(this);

        Button back = findViewById(R.id.backToMap);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        back.setOnClickListener(v->finish());
        pathCoords = allPaths.get(index);
        pathOverlay = new PathOverlay();
        pathOverlay.setCoords(pathCoords);
        pathOverlay.setWidth(10);
        pathOverlay.setPassedColor(Color.BLUE);
        pathOverlay.setOutlineWidth(2);

        //이쪽에다 캡쳐 버튼 생성과 스토리지 저장 구현
        Button record = findViewById(R.id.record);
        FrameLayout navigationView = findViewById(R.id.navigation);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBitmapFromView(navigationView);
            }
        });
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;

        List<LatLng> passed = new ArrayList<>();
        List<LatLng> goingTo;

        naverMap.setLocationSource(locationSource);
        pathOverlay.setMap(naverMap);
        pathOverlay.setProgress(0);
        pathOverlay.setColor(Color.BLUE);
        pathOverlay.setPassedColor(Color.GRAY);
        LocationOverlay locationOverlay = naverMap.getLocationOverlay();
        locationOverlay.setPosition(pathCoords.get(0));
        locationOverlay.setVisible(true);
//
//        PathOverlay passedOverLay = new PathOverlay();
//        PathOverlay goingToOverLay = new PathOverlay();
//        passedOverLay.setColor(Color.GRAY);
//        goingToOverLay.setColor(Color.BLUE);
//
//
//        passed.add(naverMap.getLocationOverlay().getPosition());
//        passed.add(naverMap.getLocationOverlay().getPosition());
//        goingTo = pathCoords;
//        for(int i=0;i<pathCoords.size();i++){
//            if(pathCoords.get(i).latitude<=naverMap.getLocationOverlay().getPosition().latitude||pathCoords.get(i).longitude<=naverMap.getLocationOverlay().getPosition().longitude){
//                goingTo.remove(i);
//            }
//        }
//
//
//        passedOverLay.setCoords(passed);
//        goingToOverLay.setCoords(goingTo);
//
//        passedOverLay.setMap(naverMap);
//        goingToOverLay.setMap(naverMap);

        CameraUpdate cameraUpdate = CameraUpdate.fitBounds(pathOverlay.getBounds())
                .animate(CameraAnimation.Fly,1200)
                .finishCallback(()->{
                    Log.d("navigation start","camera update finished");
                })
                .cancelCallback(()->{
                    Log.d("navagation start", "camera update canceled");
                });
        naverMap.moveCamera(cameraUpdate);
        naverMap.addOnLocationChangeListener(new NaverMap.OnLocationChangeListener() {
            @Override
            public void onLocationChange(@NonNull Location location) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                LatLng loc = new LatLng(lat,lng);

                Log.d("location class", String.valueOf(location));
            }
        });
    }

    //현재 화면을 비트맵으로 캡쳐
    public Bitmap getBitmapFromView(View view){
        Bitmap bitmap =Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}
