package com.shinplest.mobiletermproject.main;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.shinplest.mobiletermproject.ApplicationClass;
import com.shinplest.mobiletermproject.BaseActivity;
import com.shinplest.mobiletermproject.NonSwipeableViewPager;
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

public class MainActivity extends BaseActivity {

    ArrayList<Course> mCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViewPager();
        //최초 실행시 저장한 모든 산의 리스트를 파싱해서 맵 위에 그려준다.
        //모든 산을 하지 않을것이기 때문에 오버헤드가 크지 않을것으로 판단됨 -> 하지만 해봐야 암..^^
        mCourse = getCourseList("구룡산.json");
        for (int i = 0; i < mCourse.size(); i++) {
           testlatlng = convertTmToLatLng(mCourse.get(1).getGeometry());
        }
    }

    private void setViewPager() {
        NonSwipeableViewPager viewPager = findViewById(R.id.vp_main);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(1);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(getString(R.string.record));
        tabLayout.getTabAt(1).setText(getString(R.string.map));
        tabLayout.getTabAt(2).setText(getString(R.string.dashboard));
    }

    private ArrayList<Course> getCourseList(String fileName) {
        ArrayList<Course> courses = new ArrayList<>();
        Gson gson = new Gson();
        try {
            InputStream is = getAssets().open(fileName);
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
