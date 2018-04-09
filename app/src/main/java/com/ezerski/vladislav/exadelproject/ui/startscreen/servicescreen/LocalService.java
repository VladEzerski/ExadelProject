package com.ezerski.vladislav.exadelproject.ui.startscreen.servicescreen;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

import java.util.Random;

import static com.ezerski.vladislav.exadelproject.constants.Constants.BROADCAST_ACTION;
import static com.ezerski.vladislav.exadelproject.constants.Constants.LOG_TAG;
import static com.ezerski.vladislav.exadelproject.constants.Constants.TASK1;
import static com.ezerski.vladislav.exadelproject.constants.Constants.TASK2;

public class LocalService extends Service {

    private IBinder binder = new MyBinder();

    private Random generator = new Random();

    protected boolean isRunning = false;

    protected HandlerThread handlerThread;

    protected Looper looper;

    protected Thread mainThread = Thread.currentThread();

    protected MyLocalServiceHandler localServiceHandler;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "LocalService onCreate");
        handlerThread = new HandlerThread("MyHandlerThread", Process.THREAD_PRIORITY_BACKGROUND);
        handlerThread.start();
        looper = handlerThread.getLooper();
        localServiceHandler = new MyLocalServiceHandler(looper);
        isRunning = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        Log.d(LOG_TAG, "LocalService onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message msg = localServiceHandler.obtainMessage();
        msg.arg1 = startId;
        localServiceHandler.sendMessage(msg);

        Log.d(LOG_TAG, "LocalService onStartCommand");

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "LocalService onBind");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(LOG_TAG, "LocalService onUnBind");
        return true;
    }

    public int numberGenerator() {
        return generator.nextInt(100) + 1;
    }

    public void stopLocalService() {
        isRunning = false;
        mainThread.interrupt();
    }

    public class MyBinder extends Binder {
        LocalService getService() {
            return LocalService.this;
        }
    }

    private class MyLocalServiceHandler extends Handler {
        public MyLocalServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d(LOG_TAG, "Thread is running");
            Intent intent = new Intent(BROADCAST_ACTION);
            int incremNumber = 0, decremNumber = 100;
            while (true) {
                if (!mainThread.isInterrupted()) {
                    Log.d(LOG_TAG, "" + incremNumber + ", " + decremNumber);
                    intent.putExtra(TASK1, incremNumber);
                    intent.putExtra(TASK2, decremNumber);
                    sendBroadcast(intent);
                    incremNumber++;
                    decremNumber--;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.d(LOG_TAG, "InterruptedException");
                    }
                } else {
                    Log.d(LOG_TAG, "Service is stopped ");
                    break;
                }
            }
            stopSelfResult(msg.arg1);
            Log.d(LOG_TAG, "stopSelfResult = " + stopSelfResult(msg.arg1));
        }
    }
}