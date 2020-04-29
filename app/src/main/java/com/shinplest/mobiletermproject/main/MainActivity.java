package com.shinplest.mobiletermproject.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.shinplest.mobiletermproject.BaseActivity;
import com.shinplest.mobiletermproject.R;
import com.shinplest.mobiletermproject.dashboard.DashBoardFragment;
import com.shinplest.mobiletermproject.map.MapFragment;
import com.shinplest.mobiletermproject.record.RecordFragment;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends BaseActivity {
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.vp_main);
        viewPager.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private class pagerAdapter extends FragmentStatePagerAdapter {
        public pagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new RecordFragment();
                case 1:
                    return new MapFragment();
                case 2:
                    return new DashBoardFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
