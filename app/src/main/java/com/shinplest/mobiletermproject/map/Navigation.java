package com.shinplest.mobiletermproject.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.health.PackageHealthStats;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.Projection;
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
        back.setOnClickListener(v->finish());
        pathCoords = allPaths.get(index);
        pathOverlay = new PathOverlay();
        pathOverlay.setCoords(pathCoords);
        pathOverlay.setWidth(10);
        pathOverlay.setPassedColor(Color.BLUE);
        pathOverlay.setOutlineWidth(2);
        Log.d("path",String.valueOf(pathCoords));

    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;

        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
        pathOverlay.setMap(naverMap);
        pathOverlay.setProgress(0);
        pathOverlay.setColor(Color.BLUE);
        pathOverlay.setPassedColor(Color.GRAY);
        LocationOverlay locationOverlay = naverMap.getLocationOverlay();
        locationOverlay.setPosition(pathCoords.get(0));

        locationOverlay.setVisible(true);

        PathOverlay passedOverLay = new PathOverlay();
        PathOverlay goingToOverLay = new PathOverlay();
        passedOverLay.setColor(Color.GRAY);
        goingToOverLay.setColor(Color.BLUE);

//        //////////////////////////////////
//        //// test용 코드////
//        List<LatLng> passed = new ArrayList<>();
//        List<LatLng> goingTo = new ArrayList<>();
//
//        double lat = naverMap.getLocationOverlay().getPosition().latitude;
//        double lng = naverMap.getLocationOverlay().getPosition().longitude;
//        LatLng currentPos = new LatLng(lat,lng);
//        double distance =10000;
//        int closestIdx=0;
//
//        for(int i=0;i<pathCoords.size();i++){
//            Double lng_on_path = pathCoords.get(i).longitude;
//            Double lat_on_path = pathCoords.get(i).latitude;
//            double tmp = distance_Between_LatLong(lat_on_path,lng_on_path, lat,lng);
//            if(distance > tmp) {
//                distance = tmp;
//                closestIdx = i;
//            }
//
//        }
//        pathCoords.add(closestIdx+1,currentPos);
//        for(int i=0;i<pathCoords.size();i++){
//            if(i<=closestIdx) passed.add(pathCoords.get(i));
//            if(i>=closestIdx) goingTo.add(pathCoords.get(i));
//        }
//
//        passedOverLay.setCoords(passed);
//        goingToOverLay.setCoords(goingTo);
//
//        passedOverLay.setMap(naverMap);
//        goingToOverLay.setMap(naverMap);
//
//        /////////////////////////////////


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

                List<LatLng> passed = new ArrayList<>();
                passed.add(pathCoords.get(0));
                passed.add(pathCoords.get(1));
                List<LatLng> goingTo = new ArrayList<>();

                double lat = location.getLatitude();
                double lng = location.getLongitude();
                LatLng currentPos = new LatLng(lat,lng);
                double distance =10000;
                int closestIdx=0;

                for(int i=0;i<pathCoords.size();i++){
                    Double lng_on_path = pathCoords.get(i).longitude;
                    Double lat_on_path = pathCoords.get(i).latitude;
                    double tmp = distance_Between_LatLong(lat_on_path,lng_on_path, lat,lng);
                    if(distance > tmp) {
                        distance = tmp;
                        closestIdx = i;
                    }

                }
                pathCoords.add(closestIdx+1,currentPos);
                for(int i=0;i<pathCoords.size();i++){
                    if(i<=closestIdx) passed.add(pathCoords.get(i));
                    if(i>=closestIdx) goingTo.add(pathCoords.get(i));
                }

                passedOverLay.setCoords(passed);
                goingToOverLay.setCoords(goingTo);

                passedOverLay.setMap(naverMap);
                goingToOverLay.setMap(naverMap);

                Log.d("location class", String.valueOf(location));
            }
        });
    }
    public static double distance_Between_LatLong(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371.01; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1)*Math.sin(lat2) + Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon1 - lon2));
    }

}
