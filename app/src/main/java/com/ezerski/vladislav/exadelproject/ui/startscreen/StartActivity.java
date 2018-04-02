package com.ezerski.vladislav.exadelproject.ui.startscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ezerski.vladislav.exadelproject.R;
import com.ezerski.vladislav.exadelproject.ui.async.activity.AsyncTaskActivity;
import com.ezerski.vladislav.exadelproject.ui.fragmentscreen.activity.FragmentActivity;
import com.ezerski.vladislav.exadelproject.ui.loader.activity.LoaderActivity;
import com.ezerski.vladislav.exadelproject.ui.service.ServiceActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void buttonOnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fragment_activity:
                startActivity(new Intent(getApplicationContext(), FragmentActivity.class));
                break;
            case R.id.btn_async_activity:
                startActivity(new Intent(getApplicationContext(), AsyncTaskActivity.class));
                break;
            case R.id.btn_loader_activity:
                startActivity(new Intent(getApplicationContext(), LoaderActivity.class));
                break;
            case R.id.btn_service_activity:
                startActivity(new Intent(getApplicationContext(), ServiceActivity.class));
                break;
        }
    }
}