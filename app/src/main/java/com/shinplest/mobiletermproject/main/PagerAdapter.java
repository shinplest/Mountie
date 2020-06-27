package com.shinplest.mobiletermproject.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.shinplest.mobiletermproject.dashboard.StatisticsFragment;
import com.shinplest.mobiletermproject.map.MapFragmentMain;
import com.shinplest.mobiletermproject.record.RecordFragment;

class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RecordFragment();
            case 1:
                return new MapFragmentMain();
            case 2:
                return new StatisticsFragment();
        }
        return new MapFragmentMain();
    }

    @Override
    public int getCount() {
        return 3;
    }
}