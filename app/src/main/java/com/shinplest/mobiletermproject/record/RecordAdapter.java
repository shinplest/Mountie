package com.shinplest.mobiletermproject.record;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shinplest.mobiletermproject.R;

import java.util.ArrayList;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {
    private ArrayList<RecordItem> mData = null;

    RecordAdapter(ArrayList<RecordItem> list) {
        mData = list;
    }

    @NonNull
    @Override
    public RecordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.record_item, parent, false);
        RecordAdapter.ViewHolder vh = new RecordAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordAdapter.ViewHolder holder, int position) {

        RecordItem item = mData.get(position);

        holder.txt.setText(item.getRecord_txt());
        holder.img.setImageDrawable(item.getRecord_img());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt;
        ImageView img;

        ViewHolder(View itemView) {
            super(itemView);

            txt = itemView.findViewById(R.id.record_txt);
            img = itemView.findViewById(R.id.record_img);
        }
    }
}