package com.ezerski.vladislav.exadelproject.ui.startscreen.fragmentscreen.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.ezerski.vladislav.exadelproject.R;

public class ColorListFragment extends ListFragment implements AdapterView.OnItemClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_color_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.Colors, android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        ColorFragment fragment = (ColorFragment) getFragmentManager().findFragmentById(R.id.fragment_color);
        fragment.setFragmentItemText(item);
        setFragmentColor(position, fragment);
    }

    private void setFragmentColor(int position, ColorFragment fragment) {
        switch (position) {
            case 0:
                fragment.setFragmentItemColor(Color.RED);
                break;
            case 1:
                fragment.setFragmentItemColor(Color.BLUE);
                break;
            case 2:
                fragment.setFragmentItemColor(Color.GREEN);
                break;
            case 3:
                fragment.setFragmentItemColor(Color.YELLOW);
                break;
            case 4:
                fragment.setFragmentItemColor(Color.BLACK);
                break;
            case 5:
                fragment.setFragmentItemColor(Color.GRAY);
                break;
        }
    }
}