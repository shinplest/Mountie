package com.shinplest.mobiletermproject.record;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.altitude.setText(item.getMaxAltitude());
        holder.speed.setText(item.getAvgSpeed());
        holder.distance.setText(item.getTotalDistance());
        holder.time.setText(item.getTime());
        switch (position % 5) {
            case 0:
                holder.img.setImageResource(R.drawable.sample1);
                break;
            case 1:
                holder.img.setImageResource(R.drawable.sample2);
                break;
            case 2:
                holder.img.setImageResource(R.drawable.sample3);
                break;
            case 3:
                holder.img.setImageResource(R.drawable.sample4);
                break;
            case 4:
                holder.img.setImageResource(R.drawable.sample5);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt;
        TextView altitude;
        TextView speed;
        TextView distance;
        TextView time;
        ImageView img;

        ViewHolder(View itemView) {
            super(itemView);

            txt = itemView.findViewById(R.id.record_txt);
            img = itemView.findViewById(R.id.record_img);
            altitude = itemView.findViewById(R.id.txt_maxAltitude);
            speed = itemView.findViewById(R.id.txt_avgSpeed);
            distance = itemView.findViewById(R.id.txt_totalDistance);
            time = itemView.findViewById(R.id.txt_time);
        }
    }
}