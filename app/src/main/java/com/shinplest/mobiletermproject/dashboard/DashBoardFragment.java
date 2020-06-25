package com.shinplest.mobiletermproject.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.shinplest.mobiletermproject.BaseFragment;
import com.shinplest.mobiletermproject.R;

import java.util.ArrayList;


public class DashBoardFragment extends BaseFragment {

    ArrayList<String>sampleTime = new ArrayList<>();

    TextView totaltime;

    public DashBoardFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_fragment, container, false);

        LineChart lineChart = (LineChart) view.findViewById(R.id.chart);
        totaltime = (TextView) view.findViewById(R.id.totalTime);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(4f, 0));
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(2f, 3));
        entries.add(new Entry(18f, 4));
        entries.add(new Entry(9f, 5));
        entries.add(new Entry(16f, 6));
        entries.add(new Entry(5f, 7));
        entries.add(new Entry(3f, 8));
        entries.add(new Entry(7f, 10));
        entries.add(new Entry(9f, 11));

        LineDataSet dataset = new LineDataSet(entries, "# of Calls");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("July");
        labels.add("August");
        labels.add("September");
        labels.add("October");
        labels.add("November");
        labels.add("December");

        LineData data = new LineData(dataset);
        dataset.setLabel(String.valueOf(labels));

        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        //dataset.setDrawCubic(true); //선 둥글게 만들기
        //dataset.setDrawFilled(true); //그래프 밑부분 색칠

        lineChart.setData(data);
        lineChart.animateY(5000);

        makeData();
        calculateTime();

        return view;
    }

    //일단 임의로 데이터 만듭니당
    public void makeData()
    {
        sampleTime.add("1:00");
        sampleTime.add("3:35");
        sampleTime.add("2:00");
        sampleTime.add("2:30");
        sampleTime.add("1:23");
        sampleTime.add("3:50");
        sampleTime.add("1:20");
        sampleTime.add("4:00");
        sampleTime.add("3:30");

    }
    public void calculateTime()
    {
        int hour = 0;
        int min = 0;

        for(String time: sampleTime)
        {
            String[] t = time.split(":");
            hour = hour + Integer.parseInt(t[0]);
            min = min + Integer.parseInt(t[1]);

        }
        if(min > 60)
        {
            hour = hour + (min / 60);
            min = min % 60;
        }

        totaltime.setText(String.valueOf(hour) + ":" + String.valueOf(min));
    }
}
