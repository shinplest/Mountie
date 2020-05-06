package com.shinplest.mobiletermproject.record;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shinplest.mobiletermproject.R;

import java.util.ArrayList;


public class RecordFragment extends Fragment {

    private ArrayList<RecordData> arrayList;
    private RecordAdapter recordAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    public RecordFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record_fragment, container, false);
        arrayList = new ArrayList<>();

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;

            recyclerView.setHasFixedSize(true);

            linearLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);

            recordAdapter = new RecordAdapter(arrayList);
            recyclerView.setAdapter(recordAdapter);
        }

        return view;

    }

}
