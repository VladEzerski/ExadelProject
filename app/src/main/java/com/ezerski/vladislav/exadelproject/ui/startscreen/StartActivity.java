package com.ezerski.vladislav.exadelproject.ui.startscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ezerski.vladislav.exadelproject.R;
import com.ezerski.vladislav.exadelproject.ui.startscreen.fragmentscreen.activity.FragmentActivity;
import com.ezerski.vladislav.exadelproject.ui.startscreen.networking.NetworkingActivity;
import com.ezerski.vladislav.exadelproject.ui.startscreen.servicescreen.ServiceActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void buttonOnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_services:
                startActivity(new Intent(getApplicationContext(), ServiceActivity.class));
                break;
            case R.id.btn_fragment_activity:
                startActivity(new Intent(getApplicationContext(), FragmentActivity.class));
                break;
            case R.id.btn_networking:
                startActivity(new Intent(getApplicationContext(), NetworkingActivity.class));
                break;
        }
    }
}