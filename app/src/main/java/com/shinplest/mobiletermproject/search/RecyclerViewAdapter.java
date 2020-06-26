package com.shinplest.mobiletermproject.search;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.shinplest.mobiletermproject.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements Filterable {

    Context context;
    ArrayList<String> unFilteredlist;
    ArrayList<String> filteredList;

    private OnItemClickListener mListner = null;
    private ArrayList<mountain> listViewItemList;

    public RecyclerViewAdapter(Context context, ArrayList<mountain> list, ArrayList<String> mList) {
        super();
        this.context = context;
        this.unFilteredlist = mList;
        this.filteredList = mList;
        this.listViewItemList = list;
    }

    public void setOnItemClickListner(OnItemClickListener listner) {
        this.mListner = listner;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_recyclerview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(filteredList.get(position));
        holder.imageView.setImageResource(R.drawable.mountain);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.labelList);
            imageView = itemView.findViewById(R.id.iv_search_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    for (int i = 0; i < listViewItemList.size(); i++) {
                        if (filteredList.get(position).equals(listViewItemList.get(i).getContent())) {
                            //여기서 이제 listViewItemList 정보 넘겨줘야 함
                            mListner.onItemClick(listViewItemList.get(i).getContent(), listViewItemList.get(i).getTopLeft_lng(),
                                    listViewItemList.get(i).getTopLeft_lat(), listViewItemList.get(i).getRightBelow_lng(),
                                    listViewItemList.get(i).getRightBelow_lat());
                        }
                    }

                }
            });
        }

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    filteredList = unFilteredlist;
                } else {
                    ArrayList<String> filteringList = new ArrayList<>();
                    for (String name : unFilteredlist) {
                        if (name.toLowerCase().contains(charString.toLowerCase())) {
                            filteringList.add(name);
                        }
                    }
                    filteredList = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (ArrayList<String>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface OnItemClickListener {
        void onItemClick(String value, Double x1, Double y1, Double x2, Double y2);
    }

}
