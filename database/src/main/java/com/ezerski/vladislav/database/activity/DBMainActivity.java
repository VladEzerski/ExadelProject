package com.ezerski.vladislav.database.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.ezerski.vladislav.database.db.DBAdapter;
import com.ezerski.vladislav.database.db.DBPostsAdapter;
import com.ezerski.vladislav.database.R;
import com.ezerski.vladislav.utils.model.Post;

import java.util.ArrayList;
import java.util.List;

public class DBMainActivity extends FragmentActivity {

    public static final int REQUEST_CODE = 1;

    protected Button btnAddData;
    protected ListView postsList;

    protected SQLiteDatabase sqLiteDB;

    protected DBPostsAdapter postsAdapter;
    protected DBAdapter dbAdapter;

    protected List<Post> posts = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_main_db);

        btnAddData = findViewById(R.id.btn_add_data);
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PostActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        postsList = findViewById(R.id.list_view);
        postsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), PostActivity.class);
                intent.putExtra("post ID", id);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        posts = new DBAdapter(getApplicationContext()).getPosts();
        postsAdapter = new DBPostsAdapter(this, posts);
        postsList.setAdapter(postsAdapter);
        dbAdapter.close();
    }

    @Override
    protected void onDestroy() {
        sqLiteDB.close();
        super.onDestroy();
    }
}