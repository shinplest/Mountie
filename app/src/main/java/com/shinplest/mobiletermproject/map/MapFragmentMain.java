package com.shinplest.mobiletermproject.map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.CircleOverlay;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.PathOverlay;
import com.naver.maps.map.util.FusedLocationSource;
import com.shinplest.mobiletermproject.BaseFragment;
import com.shinplest.mobiletermproject.R;
import com.shinplest.mobiletermproject.map.interfaces.MapFragmentView;
import com.shinplest.mobiletermproject.map.models.PathResponse;
import com.shinplest.mobiletermproject.map.models.data.Feature;
import com.shinplest.mobiletermproject.map.models.data.Properties;
import com.shinplest.mobiletermproject.search.SearchMainActivity;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

import static com.naver.maps.map.CameraUpdate.REASON_LOCATION;


public class MapFragmentMain extends BaseFragment implements OnMapReadyCallback, MapFragmentView {
    private final String TAG = MapFragmentMain.class.getSimpleName();

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private NaverMap mNaverMap;
    public static ArrayList<ArrayList<LatLng>> allPaths;
    public static ArrayList<Properties> allProperties = null;
    private ArrayList<Feature> mFeature = null;
    public static List<LatLng> selectedPath;
    public static PathOverlay selectedPathOL;
    private int changeReason;
    private Long mLastMapUpdateTime = 0L;


    Button startNavi;
    Button searchBtn;
    TextView mCatNam;
    TextView mDownMin;
    TextView mSecLen;
    TextView mUpMin;
    MapService mapService;
    List<PathOverlay> pathOverlays;
    LatLng target;
    PathOverlay previousOL;
    SlidingUpPanelLayout sulMap;

    public MapFragmentMain() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // 권한 거부됨
                mNaverMap.setLocationTrackingMode(LocationTrackingMode.None);
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

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        mapFragment.getMapAsync(this);

        searchBtn = view.findViewById(R.id.editSearchBar);
        searchBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), SearchMainActivity.class);
            super.onActivityResult(1, 1, intent);
            startActivityForResult(intent, 1);
        });

        startNavi = view.findViewById(R.id.start_navi);
        startNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Navigation.class);
                startActivity(intent);
            }
        });

        mCatNam = view.findViewById(R.id.mCatNam);
        mDownMin = view.findViewById(R.id.mDownMin);
        mSecLen = view.findViewById(R.id.mSecLen);
        mUpMin = view.findViewById(R.id.mUpMin);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sulMap = view.findViewById(R.id.sul_map);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                Bundle bundle = data.getBundleExtra("bundle");

                String name = bundle.getString("mountainName");
                Double x1 = bundle.getDouble("x1");
                Double y1 = bundle.getDouble("y1");
                Double x2 = bundle.getDouble("x2");
                Double y2 = bundle.getDouble("y2");
                showCustomToast(name + " " + x1 + " " + y1);
                changeMapLocation(x1, y1, x2, y2);
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {

        }
    }

    public void removeOverLay() {
        if (allPaths != null && allPaths.size() != 0) {
            for (int i = 0; i < allPaths.size(); i++) {
                pathOverlays.get(i).setMap(null);
            }
        } else {
            Log.d("removeOverLay", "지울 오버레이가 없어요");
        }

    }


    public void changeMapLocation(Double x1, Double y1, Double x2, Double y2) {
        removeOverLay();
        target = new LatLng((y1 + y2) / 2, (x1 + x2) / 2);
        mapService.getPathData(x1, y1, x2, y2);
        CameraUpdate cameraUpdate = CameraUpdate.toCameraPosition(new CameraPosition(target, 12))
                .animate(CameraAnimation.Easing);
        mNaverMap.moveCamera(cameraUpdate);

    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        mapService = new MapService(this);
        mNaverMap = naverMap;

        naverMap.addOnCameraChangeListener((reason, animated) -> {
            Log.i("NaverMap", "카메라 변경 - reson: " + reason + ", animated: " + animated);
            changeReason = reason;
        });

        naverMap.addOnCameraIdleListener(() -> {
            //위치변화일경우 10초가 지났을때만 업데이트 되도록 변경
            if (changeReason == REASON_LOCATION) {
                if (System.currentTimeMillis() - mLastMapUpdateTime >= 10000) {
                    CameraPosition cameraPosition = naverMap.getCameraPosition();
                    target = new LatLng(((cameraPosition.target.latitude - 0.01) + (cameraPosition.target.latitude + 0.01)) / 2, ((cameraPosition.target.longitude - 0.01) + (cameraPosition.target.longitude + 0.01)) / 2);
                    mapService.getPathData(cameraPosition.target.longitude - 0.02, cameraPosition.target.latitude - 0.01, cameraPosition.target.longitude + 0.02, cameraPosition.target.latitude + 0.01);
                } else {
                }
            }
            CameraPosition cameraPosition = naverMap.getCameraPosition();
            target = new LatLng(((cameraPosition.target.latitude - 0.01) + (cameraPosition.target.latitude + 0.01)) / 2, ((cameraPosition.target.longitude - 0.01) + (cameraPosition.target.longitude + 0.01)) / 2);
            mapService.getPathData(cameraPosition.target.longitude - 0.02, cameraPosition.target.latitude - 0.01, cameraPosition.target.longitude + 0.02, cameraPosition.target.latitude + 0.01);
        });

        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_MOUNTAIN, true);
    }


    //등산로 가져오기 성공했을때
    @Override
    public void getPathdataSuccess(PathResponse pathResponse) {
        mFeature = (ArrayList<Feature>) pathResponse.getResponse().getResult().getFeatureCollection().getFeatures();
        makeCoodList();

        //길 그려주는 부분
        if (allPaths != null) {
            Log.d(TAG, "all path size" + allPaths.size());
            pathOverlays = getPathsOverLayList();
            //마커와 등산로 맵에 표시
            for (int i = 0; i < pathOverlays.size(); i++) {
                pathOverlays.get(i).setMap(mNaverMap);
                pathOverlays.get(i).setOnClickListener(new Overlay.OnClickListener() {
                    @Override
                    public boolean onClick(@NonNull Overlay overlay) {
                        selectedPathOL = (PathOverlay) overlay;
                        selectedPath = ((PathOverlay) overlay).getCoords();
                        getCheckOVColor();
                        sulMap.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);


                        Log.d(TAG, pathOverlays.indexOf(overlay)+"");
                        if(pathOverlays.indexOf(overlay)!= -1){
                            Properties properties = allProperties.get(pathOverlays.indexOf(overlay));
                            mCatNam.setText("난이도 : " + properties.getCatNam());
                            mSecLen.setText("구간거리 : " + properties.getSecLen() + "m");
                            mUpMin.setText("상행속도 : " + properties.getUpMin() + "분");
                            mDownMin.setText("하행속도 : " + properties.getDownMin() + "분");
                        }



//                        ///본인 위치 확인
//                        if (checkCurrentLocation()) {
//                            startNavi.setEnabled(true);
//                        } else {
//                            startNavi.setEnabled(false);
//                        }
                        return true;
                    }
                });
            }

        } else {
            showCustomToast("정보를 가져왔으나 맵이 준비될때 보여주지 않았습니다!");
        }
    }

    @Override
    public void getPathdataFailure() {
        showCustomToast(getString(R.string.map_failure_message));
    }

    private void makeCoodList() {
        allPaths = new ArrayList<>();
        allProperties = new ArrayList<>();

        for (int i = 0; i < mFeature.size(); i++) {
            //프로퍼티 배열 추가
            allProperties.add(mFeature.get(i).getProperties());

            //길 데이터 추가
            ArrayList<LatLng> latlngs = new ArrayList<>();
            ArrayList<ArrayList<ArrayList<Double>>> coordinates = mFeature.get(i).getGeometry().getCoordinates();
            for (int j = 0; j < coordinates.get(0).size(); j++) {
                //위도 경도 추가
                latlngs.add(new LatLng(coordinates.get(0).get(j).get(1), coordinates.get(0).get(j).get(0)));
            }
            allPaths.add(latlngs);
        }
    }

    public void getCheckOVColor() {
        if (previousOL == null) {
            selectedPathOL.setColor(Color.BLUE);
            previousOL = selectedPathOL;
        } else if (previousOL.equals(selectedPathOL)) {
            previousOL = selectedPathOL;
        } else if (previousOL.equals(selectedPathOL) == false) {
            previousOL.setColor(Color.WHITE);
            selectedPathOL.setColor(Color.BLUE);
            previousOL = selectedPathOL;
        }
    }


    private boolean checkCurrentLocation() {
        LatLng current = mNaverMap.getLocationOverlay().getPosition();
        LatLng startPoint = selectedPath.get(0);
        LatLng endPoint = selectedPath.get(selectedPath.size() - 1);
        CircleOverlay start = new CircleOverlay();
        CircleOverlay end = new CircleOverlay();
        start.setCenter(startPoint);
        end.setCenter(endPoint);
        start.setRadius(100);//100m 반경.
        end.setRadius(100);
        LatLngBounds SPbounds = start.getBounds();
        LatLngBounds EPbounds = end.getBounds();
        return SPbounds.contains(current) || EPbounds.contains(current);
    }


    private List<PathOverlay> getPathsOverLayList() {
        List<PathOverlay> paths = new ArrayList<>();
        for (int i = 0; i < allPaths.size(); i++) {

            //selected path와 같은 애를 가지고 온 경우 걔는 따로 그리지 않음.
            if (selectedPath != null) {
                if (allPaths.get(i).equals(selectedPath) == false) {
                    PathOverlay path = new PathOverlay();
                    path.setCoords(allPaths.get(i));
                    path.setWidth(30);
                    path.setOutlineWidth(5);
                    paths.add(path);
                }
            } else {
                PathOverlay path = new PathOverlay();
                path.setCoords(allPaths.get(i));
                path.setWidth(30);
                path.setOutlineWidth(5);
                paths.add(path);
            }
        }
        return paths;
    }
}