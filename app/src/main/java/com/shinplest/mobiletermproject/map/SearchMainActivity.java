package com.shinplest.mobiletermproject.map;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.shinplest.mobiletermproject.R;
import com.shinplest.mobiletermproject.main.MainActivity;
import com.shinplest.mobiletermproject.parsing.Attributes;
import com.shinplest.mobiletermproject.parsing.Course;

import java.util.ArrayList;
import java.util.List;

public class SearchMainActivity extends AppCompatActivity {

    private List<String> list;
    private ListView listView;
    private EditText editSearch;
    private SearchAdapter adapter;
    private ArrayList<String> arrayList;

    String name;
    ArrayList<Course> cors;

    String tag = "check";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_main);

        editSearch = (EditText) findViewById(R.id.editSearch);
        listView = (ListView) findViewById(R.id.listView);

        list = new ArrayList<String>();
        settingList();

        arrayList = new ArrayList<String>();
        arrayList.addAll(list);

        adapter = new SearchAdapter(list,this);

        listView.setAdapter(adapter);
        adapter.addData(name,cors);

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
    private void settingList()
    {
        //일단은 구룡산만 넣어두기
        cors = ((MainActivity)MainActivity.mContext).getCourseList("구룡산.json");
        Attributes att = cors.get(0).getAttributes();
        name = att.getMNTN_NM();

        list.add(name);
        list.add("이건테스트");
        list.add("ㅎㅎ");

    }
}
