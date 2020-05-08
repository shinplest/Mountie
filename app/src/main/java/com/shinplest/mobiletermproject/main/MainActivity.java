package com.shinplest.mobiletermproject.main;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
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

public class MainActivity extends BaseActivity {
    private NonSwipeableViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViewPager();
        getCourseList("PMNTN_구룡산_116500101.json"); //임시로 파일이름 바로 넣었으니 변경 부탁드립니다.
    }

    private void setViewPager() {
        viewPager = findViewById(R.id.vp_main);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(1);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(getString(R.string.record));
        tabLayout.getTabAt(1).setText(getString(R.string.map));
        tabLayout.getTabAt(2).setText(getString(R.string.dashboard));
    }

    //사용할사람이 함수 가져다가 사용하세요!!!
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
