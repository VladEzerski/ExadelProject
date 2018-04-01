package com.ezerski.vladislav.exadelproject.ui.fragmentscreen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ezerski.vladislav.exadelproject.R;

public class FragmentActivity extends android.support.v4.app.FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
    }
}