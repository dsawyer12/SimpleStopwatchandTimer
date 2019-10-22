package com.example.dsawyer.simplestopwatchandtimer;

import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TimerFragment extends Fragment implements View.OnClickListener, NumberPicker.OnValueChangeListener {
    private static final String TAG = "TAG";

    TextView mTime;
    NumberPicker hoursList, minutesList, secondsList;
    Button start, pause, restart;
    LinearLayout lin1, lin2;

    final int maxHourDuration = 99, maxMinuteDuration = 59, maxSecondDuration = 59;
    private int h = 0, m = 0, s = 0;
    private int seconds = 0;
    private boolean isRunning = false, isPaused = false;
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTime = view.findViewById(R.id.mTime);
        hoursList = view.findViewById(R.id.hoursList);
        minutesList = view.findViewById(R.id.minutesList);
        secondsList = view.findViewById(R.id.secondsList);
        lin1 = view.findViewById(R.id.linearLayout);
        lin2 = view.findViewById(R.id.linearLayout2);

        start = view.findViewById(R.id.start);
        pause = view.findViewById(R.id.pause);
        restart = view.findViewById(R.id.restart);

        start.setOnClickListener(this);
        pause.setOnClickListener(this);
        restart.setOnClickListener(this);

        setLists();

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        mediaPlayer = MediaPlayer.create(getActivity(), notification);

        hoursList.setOnValueChangedListener(this);
        minutesList.setOnValueChangedListener(this);
        secondsList.setOnValueChangedListener(this);

        startTimer();
    }

    private void startTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                mTime.setText(String.format("%02d:%02d:%02d", hours, minutes, secs));
                if (isRunning) {
                    if (seconds > 0)
                        seconds--;
                    else {
                        isRunning = false;
                        start.setText(getString(R.string.stop));
                        mediaPlayer.start();
                    }
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void setLists() {

        hoursList.setMinValue(0);
        minutesList.setMinValue(0);
        secondsList.setMinValue(0);

        hoursList.setMaxValue(maxHourDuration);
        minutesList.setMaxValue(maxMinuteDuration);
        secondsList.setMaxValue(maxSecondDuration);

        hoursList.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }
        });
        minutesList.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }
        });
        secondsList.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }
        });

        hoursList.setWrapSelectorWheel(true);
        minutesList.setWrapSelectorWheel(true);
        secondsList.setWrapSelectorWheel(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.start):
                if (isRunning) {
                    isRunning = false;

                    mTime.setVisibility(View.GONE);
                    pause.setVisibility(View.GONE);
                    lin1.setVisibility(View.VISIBLE);
                    lin2.setVisibility(View.VISIBLE);
                    restart.setVisibility(View.VISIBLE);
                    start.setText(getString(R.string.start));
                }
                else {
                    if (seconds == 0 && mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        isRunning = false;
                        isPaused = true;
//                        hoursList.setValue(0);
//                        minutesList.setValue(0);
//                        secondsList.setValue(0);
//                        seconds = 0;
//                        h = 0;
//                        m = 0;
//                        s = 0;

                        lin1.setVisibility(View.VISIBLE);
                        lin2.setVisibility(View.VISIBLE);
                        mTime.setVisibility(View.GONE);
                        pause.setVisibility(View.GONE);
                        restart.setVisibility(View.VISIBLE);
                        start.setText(getString(R.string.start));
                    }
                    else {
                        if (h == 0 && m == 0 && s == 0) {
                            Toast.makeText(getActivity(), "Select a duration", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            seconds = (h * 3600) + (m * 60) + s;
                            isRunning = true;
                            isPaused = false;
                            lin1.setVisibility(View.GONE);
                            lin2.setVisibility(View.GONE);
                            restart.setVisibility(View.GONE);
                            mTime.setVisibility(View.VISIBLE);
                            pause.setVisibility(View.VISIBLE);
                            start.setText(getString(R.string.stop));
                            pause.setText(getString(R.string.pause));
                        }
                    }
                }
                break;

            case (R.id.pause):
                if (isRunning && !isPaused) {
                    isRunning = false;
                    isPaused = true;
                    pause.setText(getString(R.string.resume));
                    start.setText(getString(R.string.stop));
                }

                else {
                    isRunning = true;
                    isPaused = false;
                    pause.setText(getString(R.string.pause));
                    start.setText(getString(R.string.stop));
                }
                break;

            case(R.id.restart):
                hoursList.setValue(0);
                minutesList.setValue(0);
                secondsList.setValue(0);
                seconds = 0;
                h = 0;
                m = 0;
                s = 0;
                break;
        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Log.d(TAG, "onValueChange: ");
        switch (picker.getId()) {
            case(R.id.hoursList):
                h = newVal;
                seconds = (h * 3600) + (m * 60) + s;
                break;
            case(R.id.minutesList):
                m = newVal;
                seconds = (h * 3600) + (m * 60) + s;
                break;
            case(R.id.secondsList):
                s = newVal;
                seconds = (h * 3600) + (m * 60) + s;
                break;
        }
    }
}