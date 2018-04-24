package com.ezerski.vladislav.utils.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ezerski.vladislav.utils.R;

import java.util.List;

public class PostsAdapter extends ArrayAdapter<Post> {

    private LayoutInflater inflater = null;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        super(context, R.layout.list_item3, posts);
        this.posts = posts;
        inflater = LayoutInflater.from(context);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = inflater.inflate(R.layout.list_item3, parent, false);
        } else {
            view = convertView;
        }
        TextView tvUserId = view.findViewById(R.id.tv_user_id3);
        TextView tvId = view.findViewById(R.id.tv_id3);
        TextView tvTitle = view.findViewById(R.id.tv_title3);
        TextView tvBody = view.findViewById(R.id.tv_body3);

        Post post = posts.get(position);

        tvUserId.setText("UserID: " + String.valueOf(post.getUserId()));
        tvId.setText("ID: " + String.valueOf(post.getId()));
        tvTitle.setText("Title: " + post.getTitle());
        tvBody.setText("Body: " + post.getBody());

        return view;
    }

    public void setPosts(List<Post> data) {
        posts.addAll(data);
        notifyDataSetChanged();
    }
}