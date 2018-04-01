package com.ezerski.vladislav.exadelproject.ui.fragmentscreen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezerski.vladislav.exadelproject.R;

public class ColorFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_color, container, false);
    }

    public void setText(String text) {
        TextView textView = getView().findViewById(R.id.tv_color);
        textView.setText(text);
    }

    public void setColor(int color) {
        ImageView imageView = getView().findViewById(R.id.imv_color);
        imageView.setBackgroundColor(color);
    }
}