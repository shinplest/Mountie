package com.shinplest.mobiletermproject.record;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import java.util.ArrayList;

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

        //예시
        //addItem(ContextCompat.getDrawable(getActivity(), R.drawable.map), "  기록1");
        updateRecord();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.map);

        //수환님 recordItems 가 전역변수고, 앱이 켜질때마다 내부저장소에서 불러옵니다. 바로 recorditems로 리스트 사용하시면 됩니다
        //addItem(bitmap, "text", "text", "text", "text", "text");

        return view;
    }

    //데이터 추가 + 거리, 시간, 속도, 고도 추가해야함
    public void addItem(Bitmap imageRecord, String text, String altitude, String speed, String distance, String time) {
        RecordItem item = new RecordItem();

        item.setRecord_txt(text);
        item.setRecord_img(imageRecord);
        item.setMaxAltitude(altitude);
        item.setTime(time);
        item.setAvgSpeed(speed);
        item.setTotalDistance(distance);

        recordItems.add(item);
    }

    //파일을 불러와 레코드에 보여주기
    private void updateRecord() {
        String filename;
        double maxAltitude;
        float avgSpeed;
        float totalDistance;
        int time;

        Bundle bundle = getArguments();
        SharedPreferences pref = getActivity().getSharedPreferences("SharedPreference", Context.MODE_PRIVATE);
        pref.getString("RecordItems", "");

//        if (bundle != null) {
//            filename = bundle.getString("newRecord");
//            File file = getActivity().getFileStreamPath(filename);
//
//            if (file.exists()) {
//                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//                addItem(bitmap, filename, altitude, speed, distance, hour);
//            }
//        }
    }
}
