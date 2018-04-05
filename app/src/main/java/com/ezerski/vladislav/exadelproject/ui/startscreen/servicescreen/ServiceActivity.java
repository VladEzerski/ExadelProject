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
import static com.ezerski.vladislav.exadelproject.constants.Constants.PARAM_RESULT;
import static com.ezerski.vladislav.exadelproject.constants.Constants.PARAM_STATUS;
import static com.ezerski.vladislav.exadelproject.constants.Constants.PARAM_TASK;
import static com.ezerski.vladislav.exadelproject.constants.Constants.PARAM_TIME;
import static com.ezerski.vladislav.exadelproject.constants.Constants.STATUS_FINISH;
import static com.ezerski.vladislav.exadelproject.constants.Constants.STATUS_START;
import static com.ezerski.vladislav.exadelproject.constants.Constants.TASK1_CODE;
import static com.ezerski.vladislav.exadelproject.constants.Constants.TASK2_CODE;

public class ServiceActivity extends AppCompatActivity {

    LocalService localService;

    protected boolean connect = false;

    protected ServiceConnection serviceConnection;

    protected TextView tvTask1, tvTask2, tvTask3, tvTask4;

    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int task = intent.getIntExtra(PARAM_TASK, 0);
            int status = intent.getIntExtra(PARAM_STATUS, 0);
            Log.d(LOG_TAG, "onReceive: task = " + task + ", status = " + status);

            checkedStatus(status, task, intent);
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
                intent.putExtra(PARAM_TIME, 2).putExtra(PARAM_TASK, TASK1_CODE);
                startService(intent);

                intent.putExtra(PARAM_TIME, 3).putExtra(PARAM_TASK, TASK2_CODE);
                startService(intent);

                registerReceiver(broadcastReceiver, new IntentFilter(BROADCAST_ACTION));

                bindService(intent, serviceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_stop_service:
                if (!connect) return;
                unbindService(serviceConnection);
                connect = false;
                stopService(intent);
                break;
            case R.id.btn_bind_service:
                if (connect) {
                    int number = localService.numberGenerator();
                    int number1 = localService.numberGenerator();
                    tvTask3.setText("Your number = " + number);
                    tvTask4.setText("Your number = " + number1);
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

    public void checkedStatus(int status, int task, Intent intent) {
        if (status == STATUS_START) {
            switch (task) {
                case TASK1_CODE:
                    tvTask1.setText("First number generating ...");
                    break;
                case TASK2_CODE:
                    tvTask2.setText("Second number generating ...");
                    break;
            }
        }
        if (status == STATUS_FINISH) {
            int result = intent.getIntExtra(PARAM_RESULT, 0);
            switch (task) {
                case TASK1_CODE:
                    tvTask1.setText("Your number = " + result);
                    break;
                case TASK2_CODE:
                    tvTask2.setText("Your number = " + result);
                    break;
            }
        }
    }
}