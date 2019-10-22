package com.example.dsawyer.simplestopwatchandtimer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TimerDurationListAdapter extends BaseAdapter {
    private static final String TAG = "TAG";

    Context context;
    ArrayList<Integer> durations;
    LayoutInflater inflater;

    public TimerDurationListAdapter(Context context, ArrayList<Integer> durations) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.durations = durations;
    }

    public class ViewHolder {
        TextView duration;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder = null;
       if (convertView == null) {
           holder = new ViewHolder();
           convertView = inflater.inflate(R.layout.snippet_centered_time_duration, null);

           holder.duration = convertView.findViewById(R.id.duration);
           convertView.setTag(holder);
       }
       else{
           holder = (ViewHolder) convertView.getTag();
       }

       holder.duration.setText(String.valueOf(durations.get(position)));
       return convertView;
    }

    @Override
    public int getCount() {
        return durations.size();
    }

    @Override
    public Object getItem(int position) {
        return (position % durations.size());
    }

    @Override
    public long getItemId(int position) {
        return (position % durations.size());
    }
}
