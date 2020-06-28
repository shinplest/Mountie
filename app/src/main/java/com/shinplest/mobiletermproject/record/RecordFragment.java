package com.shinplest.mobiletermproject.record;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shinplest.mobiletermproject.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.shinplest.mobiletermproject.splash.SplashActivity.recordItems;


public class RecordFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    public RecordFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.record_rv);

        RecordAdapter adapter = new RecordAdapter(recordItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //sample image
        Drawable one = getResources().getDrawable(R.drawable.map_sample);
        Drawable two = getResources().getDrawable(R.drawable.map_two);
        Drawable three = getResources().getDrawable(R.drawable.map_three);
        Drawable four = getResources().getDrawable(R.drawable.map_four);

        addItem(0, 0, 0, 0,  one);
        addItem(0, 0, 0, 0,  two);
        addItem(0, 0, 0, 0,  three);
        addItem(0, 0, 0, 0,  four);

        return view;
    }

    private void addItem(float distance, float avgSpeed, float time, double altitude,  Drawable drawable) {

        //반올림 및 string value 처리.
        String distanceS = String.format("%.2f", distance);
        String avgSpeedS = String.format("%.2f", avgSpeed);
        String timeS = String.format("%.1f", time);
        String altitudeS = String.format("%.2f", altitude);
        String filename = setFileName() + ".jpg";

        RecordItem hikingRecord = new RecordItem();
//        File file = getFileStreamPath(filename);
//        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//        hikingRecord.setRecord_img(bitmap);

        hikingRecord.setAvgSpeed(avgSpeedS);
        hikingRecord.setMaxAltitude(altitudeS);
        hikingRecord.setTotalDistance(distanceS);
        hikingRecord.setTime(timeS);
        hikingRecord.setDate(new Date());
        hikingRecord.setRecord_txt(filename);
        hikingRecord.setRecord_img(drawable);

        recordItems.add(hikingRecord);
    }

    public String setFileName() {
        //파일이름은 현재시간+앱이름으로 설정
        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = format.format(date) + "Mountie";

        return filename;
    }
}
