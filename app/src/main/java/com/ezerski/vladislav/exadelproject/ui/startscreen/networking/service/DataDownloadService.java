package com.ezerski.vladislav.exadelproject.ui.startscreen.networking.service;

import android.app.IntentService;
import android.content.Intent;

import com.ezerski.vladislav.utils.network.DataLoader;

import static com.ezerski.vladislav.utils.constants.Constants.ACTION_INTENT;
import static com.ezerski.vladislav.utils.constants.Constants.ACTION_RECEIVER;
import static com.ezerski.vladislav.utils.constants.Constants.BROADCAST_MESSAGE;
import static com.ezerski.vladislav.utils.constants.Constants.URL_KEY;

public class DataDownloadService extends IntentService {

    public DataDownloadService() {
        super("DataDownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INTENT.equals(action)) {
                final String url = intent.getStringExtra(URL_KEY);
                dataDownload(url);
            }
        }
    }

    private void dataDownload(String urlString) {
        Intent backIntent = new Intent(ACTION_RECEIVER);
        DataLoader dataLoader = new DataLoader();
        backIntent.putExtra(BROADCAST_MESSAGE, dataLoader.stringPostsReturner(urlString));
        sendBroadcast(backIntent);
    }
}