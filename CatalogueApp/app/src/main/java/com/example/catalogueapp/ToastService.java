package com.example.catalogueapp;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class ToastService extends Service {
    public static final int notify = 20000;         //Run every 20 seconds.
    private Handler mHandler = new Handler();       //Avoid collisions with handler.
    private Timer mTimer = null;

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        //If already created, cancel it.
        if (mTimer != null)
            mTimer.cancel();
        else
            //Else, create it.
            mTimer = new Timer();
        //Execute task.
        mTimer.scheduleAtFixedRate(new TimeDisplay(), 0, notify);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }

    class TimeDisplay extends TimerTask {
        @Override
        public void run() {
            //Execute on the thread.
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    //Display the toast.
                    Toast.makeText(ToastService.this, "Executing service...", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
}
