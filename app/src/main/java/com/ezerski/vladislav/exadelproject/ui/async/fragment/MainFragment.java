package com.ezerski.vladislav.exadelproject.ui.async.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ezerski.vladislav.exadelproject.services.DataLoader;
import com.ezerski.vladislav.exadelproject.model.Post;
import com.ezerski.vladislav.exadelproject.R;
import com.ezerski.vladislav.exadelproject.adapter.PostsAdapter;

import java.util.List;

import static com.ezerski.vladislav.exadelproject.constants.Constants.URL_STRING;

public class MainFragment extends Fragment {

    protected ListView postsListView;

    protected PostsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_async, container, false);

        postsListView = view.findViewById(R.id.list_view_posts);

        new GettingListOfPosts().execute();

        return view;
    }

    @SuppressLint("StaticFieldLeak")
    class GettingListOfPosts extends AsyncTask<String, String, List<Post>> {
        @Override
        protected void onPostExecute(List<Post> posts) {
            adapter = new PostsAdapter(getContext(), posts);
            postsListView.setAdapter(adapter);
        }

        @Override
        protected List<Post> doInBackground(String... strings) {
            return new DataLoader().postsReturner(URL_STRING);
        }
    }
}