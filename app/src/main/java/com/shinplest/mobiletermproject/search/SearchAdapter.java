package com.shinplest.mobiletermproject.search;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.shinplest.mobiletermproject.R;
import com.shinplest.mobiletermproject.map.MapFragmentMain;

import java.util.ArrayList;
import java.util.List;
public class SearchAdapter extends BaseAdapter {

    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

    private Context context;
    private List<String> list;
    private LayoutInflater inflate;
    private ViewHolder viewHolder;

    String tag = "check";
    public SearchAdapter(List<String> list, Context context)
    {
        this.list = list;
        this.context = context;
        this.inflate = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final int pos = position;
        if(convertView == null)
        {
            convertView = inflate.inflate(R.layout.search_listview,null);

            viewHolder = new ViewHolder();
            viewHolder.label = (TextView) convertView.findViewById(R.id.labelList);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.label.setText(list.get(position));

        LinearLayout mountainList = (LinearLayout)convertView.findViewById(R.id.mountain_list);
        mountainList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new MapFragmentMain();
                Bundle bundle = new Bundle(1);
                bundle.putString("name",listViewItemList.get(position).getContent());
                fragment.setArguments(bundle);

                Toast.makeText(v.getContext(),listViewItemList.get(position).getContent(), Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    public void addData(String content)
    {
        ListViewItem item = new ListViewItem();
        item.setContent(content);

        listViewItemList.add(item);

    }

    class ViewHolder
    {
        public TextView label;
    }
}
