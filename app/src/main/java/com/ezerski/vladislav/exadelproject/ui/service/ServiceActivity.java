package com.ezerski.vladislav.exadelproject.ui.service;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ezerski.vladislav.exadelproject.adapter.PostsAdapter;
import com.ezerski.vladislav.exadelproject.model.Post;
import com.ezerski.vladislav.exadelproject.services.DataLoader;

import java.util.ArrayList;
import java.util.List;

import static com.ezerski.vladislav.exadelproject.constants.Constants.ACTION_INTENT;
import static com.ezerski.vladislav.exadelproject.constants.Constants.ACTION_RECEIVER;
import static com.ezerski.vladislav.exadelproject.constants.Constants.BROADCAST_MESSAGE;
import static com.ezerski.vladislav.exadelproject.constants.Constants.URL_KEY;
import static com.ezerski.vladislav.exadelproject.constants.Constants.URL_STRING;

public class ServiceActivity extends ListActivity {

    protected DataLoader dataLoader = new DataLoader();

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<Post> posts = new ArrayList<>();
            PostsAdapter adapter;

            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String text = bundle.getString(BROADCAST_MESSAGE);
                posts = dataLoader.parseData(text);
            }

            adapter = new PostsAdapter(context, posts);
            setListAdapter(adapter);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startingService();
    }

    private void startingService() {
        Intent intent = new Intent(this, DataDownloadService.class);
        intent.setAction(ACTION_INTENT);
        intent.putExtra(URL_KEY, URL_STRING);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(ACTION_RECEIVER));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}