package com.ezerski.vladislav.database.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.ezerski.vladislav.database.R;
import com.ezerski.vladislav.utils.model.Post;
import com.ezerski.vladislav.utils.network.DataLoader;

import java.util.ArrayList;
import java.util.List;

import static com.ezerski.vladislav.database.model.PostsInfo.COLUMN_BODY;
import static com.ezerski.vladislav.database.model.PostsInfo.COLUMN_TITLE;
import static com.ezerski.vladislav.database.model.PostsInfo.CONTENT_URI;
import static com.ezerski.vladislav.database.model.PostsInfo.DEFAULT_DATA;
import static com.ezerski.vladislav.utils.constants.Constants.LOG_TAG;
import static com.ezerski.vladislav.utils.constants.Constants.URL_STRING;

public class DBMainActivity extends FragmentActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 100;

    protected CursorAdapter cursorAdapter;

    protected TextView txtInfo;
    protected Button btnAddData, btnDelData;
    protected ListView postsList;

    protected DataLoader dataLoader = new DataLoader();

    protected List<Post> listPosts = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_main_db);

        txtInfo = findViewById(R.id.tv_loading_info);

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        int[] items = new int[]{R.id.tv_id2, R.id.tv_title2, R.id.tv_body2};

        cursorAdapter = new SimpleCursorAdapter(this, R.layout.list_item2, null,
                DEFAULT_DATA, items, Adapter.NO_SELECTION);

        postsList = findViewById(R.id.list_view);
        postsList.setAdapter(cursorAdapter);

        btnAddData = findViewById(R.id.btn_add_data);
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postsLoader();
                txtInfo.setVisibility(View.VISIBLE);
            }
        });
        btnDelData = findViewById(R.id.btn_del_data);
        btnDelData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContentResolver().delete(CONTENT_URI, null, null);
            }
        });
    }

    private ContentValues setContentValues(Post post) {
        ContentValues cv = new ContentValues();
        String title = post.getTitle();
        String body = post.getBody();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_BODY, body);
        return cv;
    }

    public void dbFilling(String postsString) {
        listPosts.addAll(dataLoader.parseData(postsString));
        ContentValues cv;
        for (int i = 0; i < listPosts.size(); i++) {
            Post post = listPosts.get(i);
            cv = setContentValues(post);
            Uri newUri = getContentResolver().insert(CONTENT_URI, cv);
            Log.d(LOG_TAG, "insert data, URI: " + newUri.toString());
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void postsLoader() {
        new AsyncTask<String, String, String>() {

            @Override
            protected void onPostExecute(String postsString) {
                dbFilling(postsString);
                txtInfo.setVisibility(View.GONE);
            }

            @Override
            protected String doInBackground(String... strings) {
                return new DataLoader().stringPostsReturner(URL_STRING);
            }
        }.execute();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, Uri.parse(String.valueOf(CONTENT_URI)),
                DEFAULT_DATA, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }
}