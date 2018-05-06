package com.ezerski.vladislav.uicomponents.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ezerski.vladislav.uicomponents.R;

public class ColorFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_colors, container, false);

        ImageView imageView = view.findViewById(R.id.imv_item_color);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int color = bundle.getInt("color");
            imageView.setBackgroundColor(color);
        }

        return view;
    }
}