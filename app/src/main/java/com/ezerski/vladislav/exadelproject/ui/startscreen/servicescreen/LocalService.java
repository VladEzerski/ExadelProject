package com.ezerski.vladislav.exadelproject.ui.startscreen.servicescreen;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.ezerski.vladislav.exadelproject.constants.Constants.BROADCAST_ACTION;
import static com.ezerski.vladislav.exadelproject.constants.Constants.LOG_TAG;
import static com.ezerski.vladislav.exadelproject.constants.Constants.PARAM_RESULT;
import static com.ezerski.vladislav.exadelproject.constants.Constants.PARAM_STATUS;
import static com.ezerski.vladislav.exadelproject.constants.Constants.PARAM_TASK;
import static com.ezerski.vladislav.exadelproject.constants.Constants.PARAM_TIME;
import static com.ezerski.vladislav.exadelproject.constants.Constants.STATUS_FINISH;
import static com.ezerski.vladislav.exadelproject.constants.Constants.STATUS_START;

public class LocalService extends Service {

    protected ExecutorService executorService;

    private IBinder binder = new MyBinder();

    private Random generator = new Random();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "LocalService onCreate");
        executorService = Executors.newFixedThreadPool(1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "LocalService onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "LocalService onStartCommand");

        int time = intent.getIntExtra(PARAM_TIME, 1);
        int task = intent.getIntExtra(PARAM_TASK, 0);

        MyRun myRun = new MyRun(startId, time, task);
        executorService.execute(myRun);

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "LocalService onBind");
        return binder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(LOG_TAG, "LocalService onReBind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(LOG_TAG, "LocalService onUnBind");
        return true;
    }

    public int numberGenerator() {
        return generator.nextInt(100) + 1;
    }

    public class MyBinder extends Binder {
        LocalService getService() {
            return LocalService.this;
        }
    }

    private class MyRun implements Runnable {

        private int time, startId, task;

        protected MyRun(int time, int startId, int task) {
            this.time = time;
            this.startId = startId;
            this.task = task;
        }

        @Override
        public void run() {
            int number = numberGenerator();

            Intent intent = new Intent(BROADCAST_ACTION);
            try {
                intent.putExtra(PARAM_TASK, task);
                intent.putExtra(PARAM_STATUS, STATUS_START);
                sendBroadcast(intent);

                TimeUnit.SECONDS.sleep(time);

                intent.putExtra(PARAM_STATUS, STATUS_FINISH);
                intent.putExtra(PARAM_RESULT, number);
                sendBroadcast(intent);
            } catch (InterruptedException e) {
                Log.e(LOG_TAG, "Broadcast error");
            }
            stopSelfResult(startId);
        }
    }
}