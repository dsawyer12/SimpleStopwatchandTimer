package com.example.dsawyer.simplestopwatchandtimer;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class CircularArrayAdapter extends ArrayAdapter {

    public static final int HALF_MAX_VALUE = Integer.MAX_VALUE / 2;
    public final int MIDDLE;
    private ArrayList<Integer> objects;

    public CircularArrayAdapter(Context context, int textViewResourceId, ArrayList<Integer> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
        MIDDLE = HALF_MAX_VALUE - HALF_MAX_VALUE % objects.size();
    }

    @Override
    public int getCount()
    {
        return Integer.MAX_VALUE;
    }

    @Override
    public Integer getItem(int position)
    {
        return objects.get(position % objects.size());
    }
}
