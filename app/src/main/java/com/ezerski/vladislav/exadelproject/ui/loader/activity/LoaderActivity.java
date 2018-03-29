package com.ezerski.vladislav.exadelproject.ui.loader.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.widget.ListView;

import com.ezerski.vladislav.exadelproject.model.Post;
import com.ezerski.vladislav.exadelproject.R;
import com.ezerski.vladislav.exadelproject.adapter.PostsAdapter;

import java.util.ArrayList;
import java.util.List;

public class LoaderActivity extends FragmentActivity
        implements LoaderManager.LoaderCallbacks<List<Post>> {

    private PostsAdapter adapter;

    private List<Post> posts = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);

        ListView listView = findViewById(R.id.list_loader_view_posts);
        adapter = new PostsAdapter(this, posts);
        listView.setAdapter(adapter);
        getSupportLoaderManager().initLoader(1, null, this).forceLoad();
    }

    @Override
    public Loader<List<Post>> onCreateLoader(int i, Bundle bundle) {
        return new PostsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Post>> loader, List<Post> data) {
        adapter.setPosts(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Post>> loader) {
        adapter.setPosts(new ArrayList<Post>());
    }
}