package com.shinplest.mobiletermproject.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.PathOverlay;
import com.shinplest.mobiletermproject.R;

import java.util.ArrayList;

import static com.shinplest.mobiletermproject.map.MapFragmentMain.allPaths;

public class Navigation extends AppCompatActivity implements OnMapReadyCallback {
    private PathOverlay pathOverlay;
    private ArrayList<LatLng> pathCoords;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation);
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

    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        pathOverlay.setMap(naverMap);
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
}
