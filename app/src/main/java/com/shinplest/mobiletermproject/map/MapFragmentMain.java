package com.shinplest.mobiletermproject.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.PathOverlay;
import com.naver.maps.map.util.FusedLocationSource;
import com.shinplest.mobiletermproject.BaseFragment;
import com.shinplest.mobiletermproject.R;
import com.shinplest.mobiletermproject.parsing.Attributes;
import com.shinplest.mobiletermproject.parsing.Course;
import com.shinplest.mobiletermproject.parsing.Geometry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static com.shinplest.mobiletermproject.ApplicationClass.convertTmToLatLng;
import static com.shinplest.mobiletermproject.ApplicationClass.testlatlng;


public class MapFragmentMain extends BaseFragment implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    private ArrayList<Course> mCourse;

    public MapFragmentMain() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //최초 실행시 저장한 모든 산의 리스트를 파싱해서 맵 위에 그려준다.
        //모든 산을 하지 않을것이기 때문에 오버헤드가 크지 않을것으로 판단됨 -> 하지만 해봐야 암..^^
        mCourse = getCourseList("구룡산.json");
        for (int i = 0; i < mCourse.size(); i++) {
            testlatlng.add(convertTmToLatLng(mCourse.get(i).getGeometry()));
        }
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

        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

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

        //path overlay -> 함수화 예정
        for (int i = 0; i < testlatlng.size(); i++) {
            PathOverlay path = new PathOverlay();
            path.setCoords(testlatlng.get(i));
            path.setMap(naverMap);
        }
    }


    private ArrayList<Course> getCourseList(String fileName) {
        ArrayList<Course> courses = new ArrayList<>();
        Gson gson = new Gson();
        try {
            InputStream is = getActivity().getAssets().open(fileName);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONObject jsonObject = new JSONObject(json);
            JSONArray features = jsonObject.getJSONArray("features");

            for (int i = 0; i < features.length(); i++) {
                JSONObject courseJs = features.getJSONObject(i);
                JSONObject attrJs = courseJs.getJSONObject("attributes");
                JSONObject geoJs = courseJs.getJSONObject("geometry");
                Attributes course_attr = gson.fromJson(attrJs.toString(), Attributes.class);
                Geometry paths = gson.fromJson(geoJs.toString(), Geometry.class);
                Course course = new Course(course_attr, paths);
                courses.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

}
