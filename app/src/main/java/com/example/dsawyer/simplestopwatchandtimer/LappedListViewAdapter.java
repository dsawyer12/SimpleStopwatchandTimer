package com.example.dsawyer.simplestopwatchandtimer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LappedListViewAdapter extends BaseAdapter {
    private static final String TAG = "TAG";

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<String> lappedTimes;

    public class ViewHolder {
        TextView lapNumber;
    }

    public LappedListViewAdapter(Context context, ArrayList<String> lappedTimes){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.lappedTimes = lappedTimes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.snippet_lapped_time, null);
            holder.lapNumber = convertView.findViewById(R.id.lapNUmber);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.lapNumber.setText("Lap" + String.valueOf(position + 1) + "   " + lappedTimes.get(position));

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        Log.d(TAG, String.valueOf(lappedTimes.size()));
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return lappedTimes.size();
    }

    @Override
    public Object getItem(int position) {
        return lappedTimes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
