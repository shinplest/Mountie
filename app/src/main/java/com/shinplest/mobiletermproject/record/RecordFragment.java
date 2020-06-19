package com.shinplest.mobiletermproject.record;

import android.content.Context;
import android.content.Intent;
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


public class RecordFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    public ArrayList<RecordItem> rList = new ArrayList<>();

    public RecordFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.record_rv);

        RecordAdapter adapter = new RecordAdapter(rList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //예시
        //addItem(ContextCompat.getDrawable(getActivity(), R.drawable.map), "  기록1");
        updateRecord();

        return view;
    }

    //데이터 추가
    public void addItem(Bitmap imageRecord, String text) {
        RecordItem item = new RecordItem();

        item.setRecord_txt(text);
        item.setRecord_img(imageRecord);

        rList.add(item);
    }

    //파일을 불러와 레코드에 보여주기
    private void updateRecord() {
        String filename;

        Bundle bundle = getArguments();

        if(bundle != null){
            filename = bundle.getString("newRecord");
            File file = getActivity().getFileStreamPath(filename);

            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                addItem(bitmap, "record");
            }
        }
    }
}
