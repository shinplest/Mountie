package com.shinplest.mobiletermproject.dashboard;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.shinplest.mobiletermproject.BaseFragment;
import com.shinplest.mobiletermproject.R;
import com.shinplest.mobiletermproject.splash.SplashActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StatisticsFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener {

    private Activity mStatisticsActivity;
    private Context mStatisticsContext;

    // Statistics view components
    private LineChart mLineChart;

    // Date format
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
    // Today : Date -> String
    private String mTodayStr;
    // A week ago: Date -> String
    private String mAWeekAgoStr;
    // A month ago: Date -> String
    private String mAMonthAgoStr;

    private ArrayList<Integer> sumOfDayTotalUnitArrayList;

    // Save date in selected period
    private ArrayList<String> dateArrayList = new ArrayList<>();

    // Accent color for drawing unit chart
    private String mColorAccentStr = "#FF5722";
    private int mColorAccentInt = Color.parseColor(mColorAccentStr);

    // Primary dark color for text
    private String mColorDarkStr = "#6AA5A9";
    private int mColorDarkInt = Color.parseColor(mColorDarkStr);

    // Primary color for drawing total unit chart
    private String mColorStr = "#9E9E9E";
    private int mColorInt = Color.parseColor(mColorStr);

    ArrayList<String>sampleTime = new ArrayList<>();
    ArrayList<String>sampleDate = new ArrayList<>();
    TextView totaltime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        View mStatisticsView = inflater.inflate(R.layout.fragment_statistics, container, false);
        mStatisticsActivity = getActivity();
        mStatisticsContext = mStatisticsView.getContext();
        totaltime = mStatisticsView.findViewById(R.id.totalTime);
        mLineChart = mStatisticsView.findViewById(R.id.chart);

        makeData();
        calculateTime();

        //monthly 그리기
        drawPeriodStatisticsChart();
        // Get chart view

        return mStatisticsView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }

    /**
     * Set data to chart view
     */
    private void addData() {
        // Unit data
        List<Entry> unitList = new ArrayList<>();

        // Total unit data
        List<Entry> totalUnitEntries = new ArrayList<>();

        for (int i = 0; i < sumOfDayTotalUnitArrayList.size(); i++) {
            totalUnitEntries.add(new Entry(i, sumOfDayTotalUnitArrayList.get(i)));
        }

        // Total units line data set
        LineDataSet totalUnitDataSet = new LineDataSet(totalUnitEntries,
                getString(R.string.total_unit_label));
        customLineDataSet(totalUnitDataSet, mColorDarkInt);

        // Set x-axis
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularityEnabled(true);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (dateArrayList.size() > (int) value) {
                    return dateArrayList.get((int) value).replace(".", "/").substring(5);
                } else return null;
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });
        // Customize the chart
        customChart();

        // Consist line data sets
        LineData data = new LineData();
        data.addDataSet(totalUnitDataSet);
        data.notifyDataChanged();

        // Set data to chart view
        mLineChart.setData(data);
        mLineChart.notifyDataSetChanged();
        mLineChart.invalidate();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    private void drawPeriodStatisticsChart() {
        // Get Date
        getToday();
        getAWeekAgo();

        getPeriod(mAWeekAgoStr, mTodayStr);

        // Add data
        addData();
    }

    /**
     * Get today's date
     */
    void getToday() {
        Date mTodayDate = new Date();
        mTodayStr = mDateFormat.format(mTodayDate);
    }

    /**
     * Get a month ago's date
     */
    void getAMonthAgo() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date mAMonthAgoDate = calendar.getTime();
        mAMonthAgoStr = mDateFormat.format(mAMonthAgoDate);
    }

    void getAWeekAgo() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -6);
        Date mAWeekAgoDate = calendar.getTime();
        mAWeekAgoStr = mDateFormat.format(mAWeekAgoDate);
    }

    void getPeriodFromSample(Date startDate, Date endDate) {

        int itemTotalUnit = 0;
        sumOfDayTotalUnitArrayList = new ArrayList<>();
        int sumOfWholeTotalUnits = 0;

        dateArrayList = new ArrayList<>();
        Date currentDate = startDate;

        while (currentDate.compareTo(endDate) <= 0) {
            dateArrayList.add(mDateFormat.format(currentDate));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            currentDate = calendar.getTime();
        }

        if(sampleDate.isEmpty())
        {

            initializeChart();
        }
        for (String date : dateArrayList) {
            String[] dateStr = {date};

            for(int i = 0; i < sampleDate.size(); i++)
            {

                if(sampleDate.get(i).equals(date))
                {
                    itemTotalUnit += 1;
                }

            }

            sumOfDayTotalUnitArrayList.add(itemTotalUnit);
            itemTotalUnit = 0;
        }

        // Sum total units
        for (int i : sumOfDayTotalUnitArrayList) {
            sumOfWholeTotalUnits += i;
        }

    }

    void getTimeFromRecords()
    {
        int hour = 0;
        int min = 0;

        for(int i = 0; i < SplashActivity.recordItems.size(); i++)
        {
            String time = SplashActivity.recordItems.get(i).getTime();
            String t[] = time.split(":");
            hour = hour + Integer.parseInt(t[1]);
            min = min + Integer.parseInt(t[2]);
        }

        if(min > 60)
        {
            hour = hour + (min / 60);
            min = min % 60;
        }

        totaltime.setText(String.valueOf(hour) + ":" + String.valueOf(min));

    }
    void getPeriodFromRecords(Date startDate, Date endDate) {

        int itemTotalUnit = 0;
        sumOfDayTotalUnitArrayList = new ArrayList<>();
        int sumOfWholeTotalUnits = 0;

        dateArrayList = new ArrayList<>();
        Date currentDate = startDate;

        while (currentDate.compareTo(endDate) <= 0) {
            dateArrayList.add(mDateFormat.format(currentDate));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            currentDate = calendar.getTime();
        }

        if(SplashActivity.recordItems.size() == 0)
        {
            initializeChart();
        }
        for (String date : dateArrayList) {
            String[] dateStr = {date};

            for(int i = 0; i < SplashActivity.recordItems.size(); i++)
            {
                if(date == mDateFormat.format(SplashActivity.recordItems.get(i).getDate()))
                {
                    itemTotalUnit += i;
                }

            }

            sumOfDayTotalUnitArrayList.add(itemTotalUnit);
            itemTotalUnit = 0;
        }

        // Sum total units
        for (int i : sumOfDayTotalUnitArrayList) {
            sumOfWholeTotalUnits += i;
        }

    }

    /**
     * Set design to line data set object
     *
     * @param lineDataSet lineDataSet object
     */
    void customLineDataSet(LineDataSet lineDataSet, int color) {
        lineDataSet.setLineWidth(2f);
        lineDataSet.setValueTextSize(0);
        lineDataSet.setCircleRadius(6f);
        lineDataSet.setCircleColorHole(Color.WHITE);
        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setHighLightColor(color);
        lineDataSet.setValueTextColor(color);
    }

    /**
     * Customizing line chart
     */
    void customChart() {
        // Set padding
        mLineChart.setExtraRightOffset(40f);
        mLineChart.setExtraBottomOffset(20f);
        // Set color
        mLineChart.setBorderColor(mColorAccentInt);
        mLineChart.setBackgroundColor(Color.WHITE);
        // Set line design
        mLineChart.setDrawGridBackground(false);
        mLineChart.getDescription().setEnabled(false);
        mLineChart.setDrawBorders(false);

        // Y - right - Axis
        mLineChart.getAxisRight().setEnabled(false);

        // X - Axis
        mLineChart.getXAxis().setYOffset(15f);
        mLineChart.getXAxis().setTextSize(11f);
        mLineChart.getXAxis().setTextColor(Color.BLACK);
        mLineChart.getXAxis().setDrawAxisLine(false);
        mLineChart.getXAxis().setDrawGridLines(false);

        // Y - left - Axis
        mLineChart.getAxisLeft().setXOffset(15f);
        mLineChart.getAxisLeft().setTextSize(14f);
        mLineChart.getAxisLeft().setGranularity(1f);
        mLineChart.getAxisLeft().setAxisMinimum(0);
        mLineChart.getAxisLeft().setTextColor(mColorInt);
        mLineChart.getAxisLeft().setAxisLineColor(mColorInt);
        mLineChart.getAxisLeft().setDrawGridLines(false);

        // enable touch gestures
        mLineChart.setTouchEnabled(true);

        // enable scaling and dragging
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mLineChart.setPinchZoom(false);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(mStatisticsContext, R.layout.custom_marker_view);
        mv.setChartView(mLineChart); // For bounds control
        mLineChart.setMarker(mv); // Set the marker to the chart

        // Show dynamic animation
        mLineChart.animateXY(2000, 2000);

        // Customizing Legend label design
        Legend l = mLineChart.getLegend();
        l.setXEntrySpace(20f);
        l.setTextSize(11f);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
    }

    void getPeriod(String start, String end) {
        try {
            getPeriodFromSample(mDateFormat.parse(start),mDateFormat.parse(end));
            //getPeriodFromRecords(mDateFormat.parse(start), mDateFormat.parse(end));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    void initializeChart() {
        mLineChart.setData(null);
        mLineChart.invalidate();

        Paint paint = mLineChart.getPaint(Chart.PAINT_INFO);
        paint.setTextSize(32f);
        mLineChart.setNoDataText("No data");
        totaltime.setText("00:00");
    }

    //일단 임의로 데이터 만듭니당
    public void makeData(){

        sampleTime.add("1:00");
        sampleTime.add("3:35");
        sampleTime.add("2:00");
        sampleTime.add("2:30");
        sampleTime.add("1:23");
        sampleTime.add("3:50");
        sampleTime.add("1:20");
        sampleTime.add("4:00");
        sampleTime.add("3:30");
        sampleDate.add("2020.06.26");
        sampleDate.add("2020.06.23");
        sampleDate.add("2020.06.23");
        sampleDate.add("2020.06.21");
        sampleDate.add("2020.06.19");
        sampleDate.add("2020.05.30");

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

        totaltime.setText(String.valueOf(hour) + "시간 " + String.valueOf(min) + "분");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}