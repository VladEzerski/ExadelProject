package com.ezerski.vladislav.exadelproject.ui.startscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ezerski.vladislav.exadelproject.R;
import com.ezerski.vladislav.exadelproject.ui.async.activity.AsyncTaskActivity;
import com.ezerski.vladislav.exadelproject.ui.loader.activity.LoaderActivity;
import com.ezerski.vladislav.exadelproject.ui.service.ServiceActivity;

public class StartActivity extends AppCompatActivity {

    protected Button btnAsynkActivity;
    protected Button btnLoaderActivity;
    protected Button btnServiceActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnAsynkActivity = findViewById(R.id.btn_asynk_activity);
        btnAsynkActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AsyncTaskActivity.class));
            }
        });
        btnLoaderActivity = findViewById(R.id.btn_loader_activity);
        btnLoaderActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoaderActivity.class));
            }
        });
        btnServiceActivity = findViewById(R.id.btn_service_activity);
        btnServiceActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ServiceActivity.class));
            }
        });
    }
}