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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViewPager();
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
}