package com.example.dsawyer.simplestopwatchandtimer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class StopwatchFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "TAG";

    Button start, reset;
    TextView timeView;
    ListView list;

    private ArrayList<String> lappedTimes = new ArrayList<>();
    private LappedListViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stopwatch, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        start = view.findViewById(R.id.start);
        reset = view.findViewById(R.id.reset);
        timeView = view.findViewById(R.id.time);
        list = view.findViewById(R.id.listView);

        start.setOnClickListener(this);
        reset.setOnClickListener(this);


        startStopwatch();
    }

    private void startStopwatch(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if ( ((MainActivity) getActivity()).isBound ){
                    timeView.setText( ((MainActivity) getActivity()).myService.getTime());
                }
//                handler.postDelayed(this, 1000);
                handler.postDelayed(this, 1);
            }
        });
    }

    public void getLappedTime(){
        if (adapter != null){
            String lap = timeView.getText().toString();
            lappedTimes.add(lap);
            adapter.notifyDataSetChanged();
        }
        else{
            String lap = timeView.getText().toString();
            lappedTimes.add(lap);
            adapter = new LappedListViewAdapter(getActivity(), lappedTimes);
            list.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case(R.id.start):

                if ( ((MainActivity) getActivity()).myService.isRunning() ){
                    ((MainActivity) getActivity()).myService.stopRunning();
                    start.setText(getResources().getString(R.string.start));
                    reset.setText(getString(R.string.reset));
                }
                else{
                    ((MainActivity) getActivity()).myService.startRunning();
                    start.setText(getString(R.string.stop));
                    reset.setText(getString(R.string.lap));
                }
                break;
            case(R.id.reset):
                if ( ((MainActivity) getActivity()).myService.isRunning()){
                    getLappedTime();
                }
                else{
                    ((MainActivity) getActivity()).myService.reset();
                    if (adapter != null){
                        lappedTimes.clear();
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
        }
    }
}
