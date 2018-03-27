package com.ezerski.vladislav.exadelproject.UI.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ezerski.vladislav.exadelproject.R;
import com.ezerski.vladislav.exadelproject.Post;
import com.ezerski.vladislav.exadelproject.adapters.PostsAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends android.app.Fragment {

    ListView postsListView;

    PostsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        postsListView = view.findViewById(R.id.list_view_posts);

        Button btnLoadData = view.findViewById(R.id.btn_load_data);
        btnLoadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GettingListOfPosts().execute();
            }
        });
        return view;
    }

    class GettingListOfPosts extends AsyncTask<String, String, List<Post>> {

        List<Post> posts = new ArrayList<>();

        @Override
        protected void onPostExecute(List<Post> posts) {
            adapter = new PostsAdapter(getContext(), posts);
            postsListView.setAdapter(adapter);
        }

        @Override
        protected List<Post> doInBackground(String... strings) {
            List<Post> posts = new ArrayList<>();
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL("https://jsonplaceholder.typicode.com/posts/");
                urlConnection = (HttpURLConnection) url.openConnection();

                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    String responseString = readStream(urlConnection.getInputStream());
                    posts = parseData(responseString);
                } else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return posts;
        }

        private String readStream(InputStream in) {
            BufferedReader reader = null;
            StringBuilder response = new StringBuilder();
            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return response.toString();
        }

        private List<Post> parseData(String jsonString) {
            Type listType = new TypeToken<ArrayList<Post>>() {
            }.getType();
            posts = new Gson().fromJson(jsonString, listType);
            return posts;
        }
    }
}