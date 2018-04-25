package com.ezerski.vladislav.database.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ezerski.vladislav.database.db.DBAdapter;
import com.ezerski.vladislav.database.R;
import com.ezerski.vladislav.utils.network.DataLoader;
import com.ezerski.vladislav.utils.model.Post;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.ezerski.vladislav.utils.constants.Constants.URL_STRING;

public class PostActivity extends AppCompatActivity implements Serializable {

    protected List<Post> listPosts = new ArrayList<>();
    protected Button btnDelete, btnLoad;
    protected TextView tvInfo;
    protected ProgressBar prBar;

    protected DBAdapter dbAdapter;
    protected long postId = -1;

    protected DataLoader dataLoader = new DataLoader();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        prBar = findViewById(R.id.pr_bar);
        tvInfo = findViewById(R.id.tv_info);

        btnDelete = findViewById(R.id.btn_delete);

        dbAdapter = new DBAdapter(this);
        listPosts.addAll(dbAdapter.getPosts());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listPosts.size() != 0) {
                    dbAdapter.open();
                    dbAdapter.delete(postId);
                    listPosts.clear();
                    listPosts.addAll(dbAdapter.getPosts());
                    Post post = listPosts.get((int) postId);
                    dbAdapter.update(post);
                    dbAdapter.close();
                    Toast.makeText(getBaseContext(), "Data are deleted", Toast.LENGTH_SHORT).show();
                    goHome();
                } else {
                    Toast.makeText(getBaseContext(), "Is impossible deleting", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dbAdapter = new DBAdapter(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            postId = bundle.getLong("post ID");
        }
        if (postId > -1) {
            tvInfo.setText("You select " + postId + " post");
        } else {
            btnDelete.setVisibility(View.GONE);
        }

        btnLoad = findViewById(R.id.btn_load_data);
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoaderData().execute();
                tvInfo.setText("Task is running...");
                prBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void goHome() {
        Intent intent = new Intent(this, DBMainActivity.class);
        startActivity(intent);
    }

    public void dbFilling(String postsString) {
        listPosts.addAll(dataLoader.parseData(postsString));
        dbAdapter.open();
        for (int i = 0; i < listPosts.size(); i++) {
            Post post = listPosts.get(i);
            dbAdapter.insert(post);
        }
        dbAdapter.close();
    }

    @SuppressLint("StaticFieldLeak")
    class LoaderData extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String postsString) {
            prBar.setVisibility(View.GONE);
            tvInfo.setText("Task finished successful!");

            dbFilling(postsString);
            goHome();
        }

        @Override
        protected String doInBackground(String... strings) {
            return new DataLoader().stringPostsReturner(URL_STRING);
        }
    }
}