package com.shinplest.mobiletermproject.map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class MapFragmentMain extends BaseFragment implements OnMapReadyCallback, MapFragmentView {
    private final String TAG = MapFragmentMain.class.getSimpleName();

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private static final int NUMBER_OF_THREAD = 50;
    private FusedLocationSource locationSource;
    private NaverMap mNaverMap;
    public static ArrayList<ArrayList<LatLng>> allPaths;
    public static ArrayList<Properties> allProperties = null;
    private ArrayList<Feature> mFeature = null;

    Button startNavi;
    LinearLayout pathInfoView;
    Button searchBtn;
    TextView mCatNam;
    TextView mDownMin;
    TextView mSecLen;
    TextView mUpMin;
    MapService mapService;
    List<PathOverlay> pathOverlays;
    NaverMap.OnLocationChangeListener locationChangeListener;
    LatLng target;
    PathOverlay currentPathOverlay;

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
        TextView catNam = view.findViewById(R.id.mCatNam);

//        if (mapFragment == null) {
//            mapFragment = MapFragment.newInstance();
//            fm.beginTransaction().add(R.id.map, mapFragment).commit();
//        }

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        mapFragment.getMapAsync(this);

        searchBtn = view.findViewById(R.id.editSearchBar);
        searchBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), SearchMainActivity.class);
            super.onActivityResult(1,1,intent);
            startActivityForResult(intent,1);
        });

        startNavi = view.findViewById(R.id.start_navi);
        pathInfoView = view.findViewById(R.id.pathInforView);
        mCatNam = view.findViewById(R.id.mCatNam);
        mDownMin = view.findViewById(R.id.mDownMin);
        mSecLen = view.findViewById(R.id.mSecLen);
        mUpMin = view.findViewById(R.id.mUpMin);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle bundle = data.getBundleExtra("bundle");
        if(resultCode == Activity.RESULT_OK)
        {
            if(requestCode == 1)
            {
                String name = bundle.getString("mountainName");
                Double x1 = bundle.getDouble("x1");
                Double y1 = bundle.getDouble("y1");
                Double x2 = bundle.getDouble("x2");
                Double y2 = bundle.getDouble("y2");
                showCustomToast(name + " " + x1 + " " + y1);
                changeMapLocation(x1,y1,x2,y2);
            }
        }
    }
    public void removeOverLay(){
        if(allPaths!=null&&allPaths.size()!=0){
            for (int i = 0; i < allPaths.size(); i++) {
                pathOverlays.get(i).setMap(null);
            }
        }else{
            Log.d("removeOverLay","지울 오버레이가 없어요");
        }

    }
    public void changeMapLocation(Double x1, Double y1, Double x2, Double y2){
        removeOverLay();
        mNaverMap.removeOnLocationChangeListener(locationChangeListener);
        target = new LatLng( (y1+y2)/2,(x1+x2)/2);
        mapService.getPathData(x1, y1, x2, y2);

    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        mapService = new MapService(this);
        mNaverMap = naverMap;

        //위치가 바뀔때 마다 자동으로 데이터 불러오도록
       locationChangeListener = new NaverMap.OnLocationChangeListener() {
            @Override
            public void onLocationChange(@NonNull Location location) {
                Double x1 = location.getLongitude()-0.1;
                Double x2 =location.getLongitude()+0.1;
                Double y1 = location.getLatitude()-0.1;
                Double y2 = location.getLatitude()+0.1;
                target = new LatLng( (y1+y2)/2,(x1+x2)/2);
                mapService.getPathData(x1, y1, x2, y1);
            }
        };

       mNaverMap.addOnLocationChangeListener(locationChangeListener);
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
        showCustomToast(getString(R.string.map_success_message));
        makeCoodList();

        //길 그려주는 부분
        if (allPaths != null) {
            Log.d(TAG, "all path size" + allPaths.size());
            Log.d(TAG, "all path size" + allPaths.get(1));
            drawOverLayWithThread();
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
    public void getCheckOVColor(PathOverlay current){
        if(currentPathOverlay==null){
            current.setColor(Color.BLUE);
            currentPathOverlay = current;
        }else if(currentPathOverlay.equals(current)){
            currentPathOverlay = current;
        }else if(currentPathOverlay.equals(current)==false){
            currentPathOverlay.setColor(Color.WHITE);
            current.setColor(Color.BLUE);
            currentPathOverlay = current;
        }
    }
    private void drawOverLayWithThread(){
        Executor executor = Executors.newFixedThreadPool(NUMBER_OF_THREAD);
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(()->{

           pathOverlays = getPathsOverLayList();

            handler.post(()->{
                //마커와 등산로 맵에 표시
//                marker.setMap(mNaverMap);
                for(int i=0;i<pathOverlays.size();i++){
                    pathOverlays.get(i).setMap(mNaverMap);
                    pathOverlays.get(i).setOnClickListener(new Overlay.OnClickListener() {
                        @Override
                        public boolean onClick(@NonNull Overlay overlay) {
                            getCheckOVColor((PathOverlay) overlay);
//                            ((PathOverlay) overlay).setColor(Color.BLUE);
                            pathInfoView.setVisibility(View.VISIBLE);
                            ///본인 위치 확인
                            if(checkCurrentLocation(pathOverlays.indexOf(overlay))){
                                startNavi.setEnabled(true);
                            }else{
                                startNavi.setEnabled(false);
                            }
                            startNavi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(getActivity(),Navigation.class);
                                    intent.putExtra("pathIndex",pathOverlays.indexOf(overlay));
                                    startActivity(intent);
                                }
                            });
                            return true;
                        }
                    });
                }

                CameraUpdate cameraUpdate = CameraUpdate.toCameraPosition(new CameraPosition(target,12))
                        .animate(CameraAnimation.Easing);
                mNaverMap.moveCamera(cameraUpdate);
            });
        });

    }

    private boolean checkCurrentLocation(int index) {
        LatLng current = mNaverMap.getLocationOverlay().getPosition();
        LatLng startPoint = allPaths.get(index).get(0);
        LatLng endPoint = allPaths.get(index).get(allPaths.get(index).size()-1);
        CircleOverlay start = new CircleOverlay();
        CircleOverlay end = new CircleOverlay();
        start.setCenter(startPoint);
        end.setCenter(endPoint);
        start.setRadius(100);//100m 반경.
        end.setRadius(100);
        LatLngBounds SPbounds = start.getBounds();
        LatLngBounds EPbounds = end.getBounds();
        return SPbounds.contains(current)||EPbounds.contains(current);
    }


    private List<PathOverlay> getPathsOverLayList(){
        List<PathOverlay> paths = new ArrayList<>();
        for (int i = 0; i < allPaths.size(); i++) {
            PathOverlay path = new PathOverlay();
            path.setCoords(allPaths.get(i));
            path.setWidth(10);
            path.setOutlineWidth(5);
            paths.add(path);
//                    중간점에 마커 좌표
//                Marker marker = new Marker();
//                marker.setPosition(allPaths.get(i).get(allPaths.get(i).size() / 2));

        }
        return paths;
    }


}
