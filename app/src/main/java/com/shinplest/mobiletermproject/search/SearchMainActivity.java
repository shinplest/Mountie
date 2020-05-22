package com.shinplest.mobiletermproject.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;


import com.shinplest.mobiletermproject.BaseActivity;
import com.shinplest.mobiletermproject.R;

import java.util.ArrayList;
import java.util.List;

public class SearchMainActivity extends BaseActivity {

    private List<String> list;
    private ListView listView;
    private EditText editSearch;
    private SearchAdapter adapter;
    private ArrayList<String> arrayList;

    String name;

    String tag = "check";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_main);

        editSearch =  findViewById(R.id.editSearch);
        listView =  findViewById(R.id.listView);

        list = new ArrayList<>();
        adapter = new SearchAdapter(list,this);
        listView.setAdapter(adapter);
        arrayList = new ArrayList<>();
        arrayList.addAll(list);

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String text = editSearch.getText().toString();
                search(text);
            }
        });

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void search(String charText)
    {
        list.clear();

        if(charText.length() == 0)
        {
            list.addAll(arrayList);
        }
        else
        {
            for(int i = 0; i < arrayList.size(); i++)
            {
                if(arrayList.get(i).toLowerCase().contains(charText))
                {
                    list.add(arrayList.get(i));
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}
