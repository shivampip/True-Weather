package com.shivam.tree.mousam.graph;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.shivam.tree.mousam.Const;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Sittu Agrawal on 09-01-2017.
 */

public class WeatherGraph {

    Context context;
    LineChart lineChart;
    MyData data;
    long dt[];

    int uHi=-11;


    public WeatherGraph(Context context, LineChart lineChart, MyData data, long[] dt) {
        this.context = context;
        this.lineChart = lineChart;
        this.data = data;
        this.dt= dt;
        init();
    }

    public LineChart getLineChart() {
        return lineChart;
    }


    private void init() {
        int screenH = context.getResources().getDisplayMetrics().heightPixels;
        int graphH = screenH * 2 / 5;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, graphH);
        lineChart.setLayoutParams(params);
        setUI();
    }

    public void setUI() {
        final Description des = new Description();
        des.setText(".");
        lineChart.setDescription(des);
        lineChart.setDrawGridBackground(false);
        lineChart.setExtraBottomOffset(-50);

        XAxis x = lineChart.getXAxis();
        x.setPosition(XAxis.XAxisPosition.TOP);
        x.setDrawGridLines(false);
        x.setDrawAxisLine(false);
        x.setDrawLabels(true);
        x.setYOffset(25);


        x.setLabelRotationAngle(270);
        x.setTextSize(12);
        x.setTextColor(Color.WHITE);
        x.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
//                Date dt = new Date((long) value);
//                Log.d(Const.TAG2, "sVF Next is "+dt.toString());
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" hh:mm aaa");
//                String newValue = simpleDateFormat.format(dt);
//                return Const.NO_BREAK + newValue;

                if(uHi==(int)value){
                    return "";
                }else {
                    uHi= (int) value;
                }

                Date d= new Date(dt[(int) value]);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" hh:mm aaa");
                String newValue = simpleDateFormat.format(d);
                return Const.NO_BREAK + newValue;
            }
        });


        YAxis yLeft = lineChart.getAxis(YAxis.AxisDependency.LEFT);
        yLeft.setDrawGridLines(false);
        yLeft.setDrawAxisLine(false);
        yLeft.setDrawLabels(false);

        YAxis yRight = lineChart.getAxis(YAxis.AxisDependency.RIGHT);
        yRight.setDrawGridLines(false);
        yRight.setDrawAxisLine(false);
        yRight.setDrawLabels(false);

        lineChart.setDoubleTapToZoomEnabled(false);

        setData();
    }

    private void setData() {
        List<Entry> entries = data.getList();

        for(Entry e: entries){
            Date d= new Date((long) e.getX());
            Log.d(Const.TAG2, "Next Entry is "+d.toString()+" , "+e.getY());
        }

        LineDataSet dataSet = new LineDataSet(entries, "Hourly Weather report");
        dataSet.setColor(Color.rgb(184, 235, 161));
        dataSet.setDrawCircleHole(false);
        dataSet.setCircleColor(Color.CYAN);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(15);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.LTGRAY);
        dataSet.setDrawValues(true);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                int newValue = (int) value;
                return newValue + Const.DEGREE;
            }
        });

        LineData lineData = new LineData(dataSet);

        lineChart.setData(lineData);
        lineChart.invalidate();//refresh
    }

    public void invalidate() {
        lineChart.invalidate();
    }

    public void animateXY(int x, int y) {
        lineChart.animateXY(x, y);
    }


}//classEND
