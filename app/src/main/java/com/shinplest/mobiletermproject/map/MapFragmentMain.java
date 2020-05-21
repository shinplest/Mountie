package com.shinplest.mobiletermproject.map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.PathOverlay;
import com.naver.maps.map.overlay.PolylineOverlay;
import com.naver.maps.map.util.FusedLocationSource;
import com.shinplest.mobiletermproject.BaseFragment;
import com.shinplest.mobiletermproject.R;
import com.shinplest.mobiletermproject.map.interfaces.MapFragmentView;
import com.shinplest.mobiletermproject.map.models.PathResponse;
import com.shinplest.mobiletermproject.map.models.data.Feature;

import java.util.ArrayList;
import java.util.List;


public class MapFragmentMain extends BaseFragment implements OnMapReadyCallback, MapFragmentView {
    private final String TAG = MapFragmentMain.class.getSimpleName();

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    ArrayList<ArrayList<LatLng>> allPaths;

    private ArrayList<Feature> mFeature = null;

    public MapFragmentMain() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //test
        MapService mapService = new MapService(this);
        mapService.getPathData();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // 권한 거부됨
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.map_fragment_main, container, false);
        FragmentManager fm = getChildFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);

        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        mapFragment.getMapAsync(this);

        Button searchBtn = view.findViewById(R.id.editSearchBar);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SearchMainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_MOUNTAIN, true);

        //길 그려주는 부분
        if (mFeature != null) {
            Log.d(TAG, "all path size" + allPaths.size());
            Log.d(TAG, "all path size" + allPaths.get(1));

            ArrayList<List<LatLng>> testlatlng = new ArrayList<>();
            testlatlng.add(allPaths.get(0));
            PathOverlay path = new PathOverlay();
            path.setCoords(testlatlng.get(0));
            path.setMap(naverMap);

        } else {
            showCustomToast("정보를 가져왔으나 맵이 준비될때 보여주지 않았습니다!");
        }
    }

    @Override
    public void getPathdataSuccess(PathResponse pathResponse) {
        mFeature = (ArrayList<Feature>) pathResponse.getResponse().getResult().getFeatureCollection().getFeatures();
        showCustomToast(getString(R.string.map_success_message));
        makeCoodList();
    }

    @Override
    public void getPathdataFailure() {
        showCustomToast(getString(R.string.map_failure_message));
    }

    private void makeCoodList() {
        allPaths = new ArrayList<>();
        for (int i = 0; i < mFeature.size(); i++) {
            ArrayList<LatLng> latlngs = new ArrayList<>();
            List<List<List<Double>>> coordinates = mFeature.get(i).getGeometry().getCoordinates();
            for (int j = 0; j < coordinates.get(0).size(); j++) {
                //위도 경도 추가
                latlngs.add(new LatLng(coordinates.get(0).get(i).get(1), coordinates.get(0).get(i).get(0)));
            }
            allPaths.add(latlngs);
        }
    }
}
