package com.example.anurag145.hackdtu;

import android.app.Application;
import android.os.SystemClock;

import java.util.concurrent.TimeUnit;

/**
 * Created by anurag145 on 10/2/17.
 */

public class App extends Application {

    @Override
    public void onCreate()
    {
        super.onCreate();
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(3));
    }
}