package com.shinplest.mobiletermproject.record;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shinplest.mobiletermproject.R;

import java.util.ArrayList;
import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    private List<RecordData> list;

    public RecordAdapter(ArrayList<RecordData> arrayList) {
        this.list = arrayList;
    }

    @NonNull
    @Override
    public RecordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordAdapter.ViewHolder holder, int position) {

        RecordData recordData= list.get(position);

        holder.mainRecord.setImageResource(recordData.getMainRecord());
        holder.recentRecord.setText(recordData.toString());
        holder.lastRecord.setText(recordData.toString());
        holder.subRecord1.setImageResource(recordData.getSubRecord1());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView recentRecord;
        protected ImageView mainRecord;
        protected TextView lastRecord;
        protected ImageView subRecord1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recentRecord = (TextView) itemView.findViewById(R.id.recentRecord);
            mainRecord = (ImageView) itemView.findViewById(R.id.mainRecord);
            lastRecord = (TextView) itemView.findViewById(R.id.lastRecord);
            subRecord1= (ImageView) itemView.findViewById(R.id.subRecord1);

        }
    }
}
