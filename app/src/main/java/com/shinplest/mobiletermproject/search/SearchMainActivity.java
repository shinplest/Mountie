package com.shinplest.mobiletermproject.search;

import android.annotation.SuppressLint;
import android.content.Intent;
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
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        adapter.setOnItemClickListner(new RecyclerViewAdapter.OnItemClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onItemClick(String value, Double x1, Double y1, Double x2, Double y2) {

                Bundle bundle = new Bundle();
                bundle.putString("mountainName",value);
                bundle.putDouble("x1",x1);
                bundle.putDouble("y1",y1);
                bundle.putDouble("x2",x2);
                bundle.putDouble("y2",y2);

                Intent intent = new Intent();
                intent.putExtra("bundle",bundle);
                setResult(RESULT_OK, intent);
                finish();
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
        mNameList.add("대현산");
        mountainArrayList.add(new mountain("대현산",127.019835,37.55883,127.023736,37.554792));
        mNameList.add("도봉산");
        mountainArrayList.add(new mountain("도봉산",126.982162,37.737278,127.027282,37.666401));
        mNameList.add("망우산");
        mountainArrayList.add(new mountain("망우산",127.106171,37.596075,127.105007,37.561187));
        mNameList.add("매봉산(강남구)");
        mountainArrayList.add(new mountain("매봉산(강남구)",127.040701,37.491478,127.051004,37.486758));
        mNameList.add("매봉산(마포구)");
        mountainArrayList.add(new mountain("매봉산(마포구)",126.890337,37.574686,127.897873,37.568369));
        mNameList.add("매봉산(구로구)");
        mountainArrayList.add(new mountain("매봉산(구로구)",126.829498,37.506202,126.843906,37.498562));
        mNameList.add("배봉산");
        mountainArrayList.add(new mountain("배봉산",127.062902,37.587189,126.066193,37.578636));
        mNameList.add("백련산");
        mountainArrayList.add(new mountain("백련산",126.926472,37.596925,126.932995,37.586213));
        mNameList.add("인릉산");
        mountainArrayList.add(new mountain("인릉산",127.065615,37.448510,127.093261,37.444746));
        mNameList.add("범바위산");
        mountainArrayList.add(new mountain("범바위산",127.091491,37.457926,127.095250,37.454071));
        mNameList.add("봉산");
        mountainArrayList.add(new mountain("봉산",126.897318,37.616630,126.900517,37.588971));
        mNameList.add("봉제산");
        mountainArrayList.add(new mountain("봉제산",126.851344,37.547693,126.861612,37.537617));
        mNameList.add("봉화산");
        mountainArrayList.add(new mountain("봉화산",127.084876,37.610157,127.088614,37.607598));
        mNameList.add("북한산");
        mountainArrayList.add(new mountain("북한산",126.949507,37.687164,126.994879,37.613566));
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
