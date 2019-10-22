package com.example.dsawyer.simplestopwatchandtimer;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TAG";

    MyService myService;
    boolean isBound = false;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.MyBinder binder = (MyService.MyBinder) service;
            myService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpTabLayout();

        Intent intent = new Intent(this, MyService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    private void setUpTabLayout() {
        ViewPager viewPager = findViewById(R.id.viewpager);
        TabLayout tabs = findViewById(R.id.tabs);

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new StopwatchFragment());
        adapter.addFragment(new TimerFragment());

        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        tabs.getTabAt(0).setText("stopwatch");
        tabs.getTabAt(1).setText("timer");

        View root = tabs.getChildAt(0);
        if (root instanceof LinearLayout){
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.colorPrimaryDark));
            drawable.setSize(2,0);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout)root).setDividerDrawable(drawable);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
