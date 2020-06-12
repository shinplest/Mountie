package com.shinplest.mobiletermproject.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.naver.maps.map.Projection;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.PathOverlay;
import com.naver.maps.map.util.FusedLocationSource;
import com.shinplest.mobiletermproject.R;
import com.shinplest.mobiletermproject.record.RecordFragment;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.naviMap);
        mapFragment.getMapAsync(this);

        Button back = findViewById(R.id.backToMap);
        back.setOnClickListener(v -> finish());
        pathCoords = allPaths.get(index);
        pathOverlay = new PathOverlay();
        pathOverlay.setCoords(pathCoords);
        pathOverlay.setWidth(10);
        pathOverlay.setPassedColor(Color.BLUE);
        pathOverlay.setOutlineWidth(2);
        Log.d("path", String.valueOf(pathCoords));

        //Record capture
        Button record = findViewById(R.id.record);
        FrameLayout navigationView = findViewById(R.id.navigation);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = getBitmapFromView(navigationView);
                File file = saveBitmapToJpg(bitmap, setFileName());
                readRecord(file);
            }
        });
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

        //////////////////////////////////
        //// test용 코드////
        List<LatLng> passed = new ArrayList<>();
        List<LatLng> goingTo = new ArrayList<>();

        double lat = naverMap.getLocationOverlay().getPosition().latitude;
        double lng = naverMap.getLocationOverlay().getPosition().longitude;
        LatLng currentPos = new LatLng(lat, lng);
        double distance = 10000;
        int closestIdx = 0;

        for (int i = 0; i < pathCoords.size(); i++) {
            Double lng_on_path = pathCoords.get(i).longitude;
            Double lat_on_path = pathCoords.get(i).latitude;
            double tmp = distance_Between_LatLong(lat_on_path, lng_on_path, lat, lng);
            if (distance > tmp) {
                distance = tmp;
                closestIdx = i;
            }

        }
        pathCoords.add(closestIdx + 1, currentPos);
        for (int i = 0; i < pathCoords.size(); i++) {
            if (i <= closestIdx) passed.add(pathCoords.get(i));
            if (i >= closestIdx) goingTo.add(pathCoords.get(i));
        }

        passedOverLay.setCoords(passed);
        goingToOverLay.setCoords(goingTo);

        passedOverLay.setMap(naverMap);
        goingToOverLay.setMap(naverMap);

        /////////////////////////////////

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
                .animate(CameraAnimation.Fly, 1200)
                .finishCallback(() -> {
                    Log.d("navigation start", "camera update finished");
                })
                .cancelCallback(() -> {
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
                LatLng currentPos = new LatLng(lat, lng);
                double distance = 10000;
                int closestIdx = 0;

                for (int i = 0; i < pathCoords.size(); i++) {
                    Double lng_on_path = pathCoords.get(i).longitude;
                    Double lat_on_path = pathCoords.get(i).latitude;
                    double tmp = distance_Between_LatLong(lat_on_path, lng_on_path, lat, lng);
                    if (distance > tmp) {
                        distance = tmp;
                        closestIdx = i;
                    }

                }
                pathCoords.add(closestIdx + 1, currentPos);
                for (int i = 0; i < pathCoords.size(); i++) {
                    if (i <= closestIdx) passed.add(pathCoords.get(i));
                    if (i >= closestIdx) goingTo.add(pathCoords.get(i));
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
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }

    //////////////////////////////////////////////

    //현재 화면을 비트맵으로 캡쳐(저장)
    public Bitmap getBitmapFromView(@NotNull View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    //이미지 파일 이름 설정
    public String setFileName() {
        //파일이름은 현재시간+앱이름으로 설정
        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = format.format(date) + "Mountie";

        return filename;
    }

    //비트맵 파일을 이미지파일로 저장
    private File saveBitmapToJpg(Bitmap bitmap, String name) {
        File storage = getFilesDir();
        String filename = name + ".jpg";
        File f = new File(storage, filename);

        try{
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
        }catch(FileNotFoundException ffe){
            ffe.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        return f;
    }

    //파일을 불러와 레코드에 보여주기
    private void readRecord(File file){
        RecordFragment recordFragment = new RecordFragment();

        if(file.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            recordFragment.addItem(bitmap, "Record1");
        }
    }
}
