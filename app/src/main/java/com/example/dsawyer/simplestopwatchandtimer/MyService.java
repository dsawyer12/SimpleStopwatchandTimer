package com.example.dsawyer.simplestopwatchandtimer;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyService extends Service {
    private static final String TAG = "TAG";

    private IBinder binder = new MyBinder();
    private boolean isRunning = false;
    private int seconds = 0;
    private int milliseconds = 0;
    private String mTime;

    @Override
    public void onCreate() {
        super.onCreate();
        startStopwatch();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

    public void startStopwatch() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

//                int hours, minutes, secs, millisecs;
//                hours = milliseconds / 216000;
//                minutes = (milliseconds / 3600) % 60;
//                secs = (milliseconds % 3600) / 60;
//                millisecs = ((milliseconds * 100) / 60) % 100;

                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                mTime = String.format("%02d:%02d:%02d", hours, minutes, secs);
                if (isRunning)
                    seconds++;
                handler.postDelayed(this, 1000);


//                mTime = String.format("%02d:%02d:%02d:%02d", hours, minutes, secs, milliseconds);
//
//                if (isRunning) {
//                    milliseconds++;
////                    if (milliseconds == 10368000) {
////                        milliseconds = 0;
////                    } else {
////                        milliseconds++;
////                    }
//                }
//                handler.postDelayed(this, 0);
            }
        });
    }

    public String getTime() {
        return mTime;
    }

    public void stopRunning() {
        isRunning = false;
    }

    public void startRunning() {
        isRunning = true;
    }

    public void reset() {
        isRunning = false;
//        seconds = 0;
        milliseconds = 0;
    }

    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        stopSelf();
    }
}