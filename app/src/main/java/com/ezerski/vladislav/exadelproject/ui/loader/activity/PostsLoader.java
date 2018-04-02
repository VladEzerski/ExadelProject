package com.ezerski.vladislav.exadelproject.ui.loader.activity;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.ezerski.vladislav.exadelproject.services.DataLoader;
import com.ezerski.vladislav.exadelproject.model.Post;

import java.util.List;

import static com.ezerski.vladislav.exadelproject.constants.Constants.URL_STRING;

public class PostsLoader extends AsyncTaskLoader<List<Post>> {

    PostsLoader(Context context) {
        super(context);
    }

    @Override
    public List<Post> loadInBackground() {
        return new DataLoader().postsReturner(URL_STRING);
    }
}