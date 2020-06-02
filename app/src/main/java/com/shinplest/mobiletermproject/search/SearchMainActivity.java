package com.shinplest.mobiletermproject.search;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shinplest.mobiletermproject.R;
import com.shinplest.mobiletermproject.map.MapFragmentMain;

import java.util.ArrayList;

public class SearchMainActivity extends AppCompatActivity implements TextWatcher {

    private RecyclerView recyclerView;
    private EditText editText;
    private RecyclerViewAdapter adapter;
    ArrayList<mountain> mountainArrayList = new ArrayList<mountain>();
    ArrayList<String> mNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        editText =  findViewById(R.id.editSearch);
        editText.addTextChangedListener(this);

        addData();
        adapter = new RecyclerViewAdapter(getApplicationContext(),mountainArrayList,mNameList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter.setOnItemClickListner(new RecyclerViewAdapter.OnItemClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onItemClick(String value, Double x1, Double y1, Double x2, Double y2) {

                MapFragmentMain mapFragmentMain = new MapFragmentMain();

                Bundle bundle = new Bundle();
                bundle.putString("mountainName",value);
                bundle.putDouble("x1",x1);
                bundle.putDouble("y1",y1);
                bundle.putDouble("x2",x2);
                bundle.putDouble("y2",y2);
                mapFragmentMain.setArguments(bundle);

                //여기서 mapFragmentMain 띄우기

            }
        });

    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        adapter.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void addData()
    {
        mNameList.add("개운산");
        mountainArrayList.add(new mountain("개운산",127.024799,37.601496,127.032894,37.591374));
        mNameList.add("개웅산");
        mountainArrayList.add(new mountain("개웅산",126.84141,37.493266,126.850852,37.483561));
        mNameList.add("개화산");
        mountainArrayList.add(new mountain("개화산",126.80205,37.587808,126.807073,37.575803));
        mNameList.add("고덕산");
        mountainArrayList.add(new mountain("고덕산",127.142303,37.566264,127.154749,37.562707));
        mNameList.add("관악산");
        mountainArrayList.add(new mountain("관악산",126.921866,37.456792,126.97903,37.421575));
        mNameList.add("구룡산");
        mountainArrayList.add(new mountain("구룡산",127.054241,37.470729,127.065211,37.468311));
        mNameList.add("구릉산");
        mountainArrayList.add(new mountain("구릉산",127.108464,37.622158,127.1222206,37.612713));
        mNameList.add("남산");
        mountainArrayList.add(new mountain("남산",126.979725,37.558249,127.002803,37.544616));
        mNameList.add("남한산");
        mountainArrayList.add(new mountain("남한산",127.167506,37.510995,127.238733,37.440152));
        mNameList.add("노고산");
        mountainArrayList.add(new mountain("노고산",126.950058,37.710151,126.950668,37.668816));
        mNameList.add("대모산");
        mountainArrayList.add(new mountain("대모산",127.074352,37.476578,127.473858,37.473858));
        mNameList.add("봉화산");
        mountainArrayList.add(new mountain("봉화산",127.084876,37.610157,127.088614,37.607598));
        mNameList.add("북한산");
        mountainArrayList.add(new mountain("북한산",126.969155,37.741071,127.007182,37.62109));
        mNameList.add("상암산");
        mountainArrayList.add(new mountain("상암산",126.890733,37.580576,126.889682,37.575075));
        mNameList.add("새터산");
        mountainArrayList.add(new mountain("새터산",126.907119,37.567278,126.907926,37.566366));
        mNameList.add("서달산");
        mountainArrayList.add(new mountain("서달산",126.958987,37.49859,126.966057,37.493049));
        mNameList.add("성산");
        mountainArrayList.add(new mountain("성산",126.910259,37.563179,126.915302,37.560142));
        mNameList.add("아차산");
        mountainArrayList.add(new mountain("아차산",127.105822,37.595853,127.104964,37.560209));
        mNameList.add("안산");
        mountainArrayList.add(new mountain("안산",126.937598,37.582809,126.948939,37.571706));
        mNameList.add("오패산");
        mountainArrayList.add(new mountain("오패산",127.027708,37.633761,127.029994,37.626377));
        mNameList.add("와우산");
        mountainArrayList.add(new mountain("와우산",126.927158,37.552061,126.929872,37.550929));
        mNameList.add("용왕산");
        mountainArrayList.add(new mountain("용왕산",126.876288,37.543747,126.879388,37.541603));

    }

}
