package com.ezerski.vladislav.database.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ezerski.vladislav.database.R;
import com.ezerski.vladislav.utils.model.Post;

import java.util.List;

public class DBPostsAdapter extends ArrayAdapter<Post> {
    private LayoutInflater inflater = null;
    private List<Post> posts;

    public DBPostsAdapter(Context context, List<Post> posts) {
        super(context, R.layout.list_item2, posts);
        this.posts = posts;
        inflater = LayoutInflater.from(context);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = inflater.inflate(R.layout.list_item2, parent, false);
        } else {
            view = convertView;
        }
        TextView tvUserId = view.findViewById(R.id.tv_user_id2);
        TextView tvTitle = view.findViewById(R.id.tv_title2);
        TextView tvBody = view.findViewById(R.id.tv_body2);

        Post post = posts.get(position);

        tvUserId.setText("UserID: " + String.valueOf(post.getUserId()));
        tvTitle.setText("Title: " + post.getTitle());
        tvBody.setText("Body: " + post.getBody());

        return view;
    }

    public void setPosts(List<Post> data) {
        posts.addAll(data);
        notifyDataSetChanged();
    }
}
