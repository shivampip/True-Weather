package com.shivam.tree.mousam.graph;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sittu Agrawal on 09-01-2017.
 */

public class MyData {

    int index;
    List<Entry> list;

    public MyData() {
        list = new ArrayList<>();
        index = 0;
    }

    public void add(float value) {
        int newValue = (int) (Math.round(value * 1f) / 1f);
        Entry entry = new Entry(index, newValue);

//        value= Math.round(value * 10f) / 10f;
//        Entry entry= new Entry(index, value);

        list.add(entry);
        index++;
    }

    public void add(float x, float y){
        int newY = (int) (Math.round(y * 1f) / 1f);
        Entry entry= new Entry(x, newY);
        list.add(entry);
    }

    public List<Entry> getList() {
        return list;
    }

}//classEND
