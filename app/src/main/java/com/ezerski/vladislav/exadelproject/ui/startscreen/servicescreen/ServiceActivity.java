package com.ezerski.vladislav.exadelproject.ui.startscreen.servicescreen;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ezerski.vladislav.exadelproject.R;
import com.ezerski.vladislav.exadelprojectserver.IAidlInterface;

import java.util.List;

import static com.ezerski.vladislav.utils.constants.Constants.BROADCAST_ACTION;
import static com.ezerski.vladislav.utils.constants.Constants.LOG_TAG;
import static com.ezerski.vladislav.utils.constants.Constants.TASK1;
import static com.ezerski.vladislav.utils.constants.Constants.TASK2;

public class ServiceActivity extends AppCompatActivity {

    public static final String ACTION_AIDL = "com.ezerski.vladislav.aidl.IAidlInterface";

    protected LocalService localService;

    protected boolean connect = false;

    protected ServiceConnection serviceConnection, localServiceConnection;

    protected TextView tvTask1, tvTask2, tvTask3, tvTask4;

    protected String string;

    protected IAidlInterface aidlSumService;

    public int result;

    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int incremNumber = 1, decremNumber = 2;
            int task1Result = returnIntentResult(intent, incremNumber, TASK1);
            int task2Result = returnIntentResult(intent, decremNumber, TASK2);
            tvTask1.setText(string + task1Result);
            tvTask2.setText(string + task2Result);
            result = task1Result;
        }
    };

    public int returnIntentResult(Intent intent, int number, String task) {
        return intent.getIntExtra(task, number);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent aidlIntent = new Intent(ACTION_AIDL);
        Intent updateIntent = createExplicitIntent(this, aidlIntent);
        if (updateIntent != null) {
            bindService(updateIntent, serviceConnection, Context.BIND_AUTO_CREATE);
            Log.d(LOG_TAG, "Binding from AIDL");
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        tvTask1 = findViewById(R.id.tv_task1);
        tvTask2 = findViewById(R.id.tv_task2);
        tvTask3 = findViewById(R.id.tv_task3);
        tvTask4 = findViewById(R.id.tv_task4);

        string = getString(R.string.text_in_service);

        localServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.d(LOG_TAG, "ServiceActivity onServiceConnected(Local Binding)");
                LocalService.MyBinder binder = (LocalService.MyBinder) iBinder;
                localService = binder.getService();
                connect = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.d(LOG_TAG, "ServiceActivity onServiceDisconnected(Local Binding)");
                connect = false;
            }
        };

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.d(LOG_TAG, "ServiceActivity onServiceConnected(Binding from AIDL)");
                aidlSumService = IAidlInterface.Stub.asInterface(iBinder);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.d(LOG_TAG, "ServiceActivity onServiceDisconnected(Binding from AIDL)");
                aidlSumService = null;
            }
        };
    }

    public void btnOnClick(View view) {
        Intent intent = new Intent(view.getContext(), LocalService.class);
        switch (view.getId()) {
            case R.id.btn_start_service:
                startService(intent);
                registerReceiver(broadcastReceiver, new IntentFilter(BROADCAST_ACTION));
                bindService(intent, localServiceConnection, BIND_AUTO_CREATE);
                Log.d(LOG_TAG, "Local Binding");
                break;
            case R.id.btn_stop_service:
                if (!connect) return;
                unbindService(localServiceConnection);
                connect = false;
                localService.stopLocalService();
                Toast.makeText(this, "Service is stopped", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_bind_service:
                if (connect) {
                    int number = localService.numberGenerator();
                    tvTask3.setText(string + number);
                    break;
                } else {
                    Toast.makeText(this, "First run the service", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_aidl_server:
                int firstNum = result;
                int sqr = calculateSum(firstNum);
                tvTask4.setText("Square of number = " + sqr);
        }
    }

    private int calculateSum(int firstNum) {
        int sum = 0;
        try {
            sum = aidlSumService.numberSquare(firstNum);
        } catch (RemoteException e) {
            Log.d(LOG_TAG, "RemoteException (AIDL working)");
        }
        return sum;
    }

    public Intent createExplicitIntent(Context context, Intent intent) {
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(intent, 0);

        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        Intent explicitIntent = new Intent(intent);
        explicitIntent.setComponent(component);

        return explicitIntent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}