package com.ezerski.vladislav.exadelproject.ui.startscreen.servicescreen;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ezerski.vladislav.exadelproject.R;

import static com.ezerski.vladislav.exadelproject.constants.Constants.BROADCAST_ACTION;
import static com.ezerski.vladislav.exadelproject.constants.Constants.LOG_TAG;
import static com.ezerski.vladislav.exadelproject.constants.Constants.TASK1;
import static com.ezerski.vladislav.exadelproject.constants.Constants.TASK2;

public class ServiceActivity extends AppCompatActivity {

    protected LocalService localService;

    protected boolean connect = false;

    protected ServiceConnection serviceConnection;

    protected TextView tvTask1, tvTask2, tvTask3, tvTask4;

    protected String string;

    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int incremNumber = 1, decremNumber = 2;
            int task1Result = intent.getIntExtra(TASK1, incremNumber);
            int task2Result = intent.getIntExtra(TASK2, decremNumber);
            tvTask1.setText(string + task1Result);
            tvTask2.setText(string + task2Result);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        tvTask1 = findViewById(R.id.tv_task1);
        tvTask2 = findViewById(R.id.tv_task2);
        tvTask3 = findViewById(R.id.tv_task3);
        tvTask4 = findViewById(R.id.tv_task4);

        string = getString(R.string.text_in_service);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.d(LOG_TAG, "ServiceActivity onServiceConnected");
                LocalService.MyBinder binder = (LocalService.MyBinder) iBinder;
                localService = binder.getService();
                connect = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.d(LOG_TAG, "ServiceActivity onServiceDisconnected");
                connect = false;
            }
        };
    }

    public void btnOnClick(View view) {
        Intent intent = new Intent(view.getContext(), LocalService.class);
        switch (view.getId()) {
            case R.id.btn_start_service:
                startService(intent);
                registerReceiver(broadcastReceiver, new IntentFilter(BROADCAST_ACTION));
                bindService(intent, serviceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_stop_service:
                if (!connect) return;
                unbindService(serviceConnection);
                connect = false;
                localService.stopLocalService();
                Toast.makeText(this, "Service is stopped", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_bind_service:
                if (connect) {
                    int number = localService.numberGenerator(),
                    number1 = localService.numberGenerator();
                    tvTask3.setText(string + number);
                    tvTask4.setText(string + number1);
                } else {
                    Toast.makeText(this, "First run the service", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}