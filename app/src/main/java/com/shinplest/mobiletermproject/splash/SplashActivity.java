package com.shinplest.mobiletermproject.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.shinplest.mobiletermproject.BaseActivity;
import com.shinplest.mobiletermproject.R;
import com.shinplest.mobiletermproject.main.MainActivity;
import com.shinplest.mobiletermproject.record.RecordItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {

    //private Window mWindow;
    public static ArrayList<RecordItem> recordItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //나중에 스플래쉬에 색 넣으면 주석 풀면 상단 아이콘 색이 변합니당
        //mWindow = getWindow();
        //mWindow.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("SharedPreference", MODE_PRIVATE);
                String json = sharedPreferences.getString("RecordItems", "");
                if (json == "") {
                    recordItems = new ArrayList<>();
                } else {
                    Gson gson = new GsonBuilder().create();
                    Type listType = new TypeToken<ArrayList<RecordItem>>() {}.getType();
                    recordItems = gson.fromJson(json, listType);
                }
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        };
        new Timer().schedule(timerTask, 1500);
    }
}

