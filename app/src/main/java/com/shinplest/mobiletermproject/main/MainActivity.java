package com.shinplest.mobiletermproject.main;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.shinplest.mobiletermproject.BaseActivity;
import com.shinplest.mobiletermproject.R;

public class MainActivity extends BaseActivity {
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViewPager();
    }

    private void setViewPager(){
        viewPager = findViewById(R.id.vp_main);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(1);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(getString(R.string.record));
        tabLayout.getTabAt(1).setText(getString(R.string.map));
        tabLayout.getTabAt(2).setText(getString(R.string.dashboard));
    }
}
