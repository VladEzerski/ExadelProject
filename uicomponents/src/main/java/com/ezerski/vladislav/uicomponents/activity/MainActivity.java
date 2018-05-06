package com.ezerski.vladislav.uicomponents.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezerski.vladislav.uicomponents.R;
import com.ezerski.vladislav.uicomponents.fragment.ColorFragment;
import com.ezerski.vladislav.uicomponents.fragment.ProfileFragment;
import com.squareup.picasso.Picasso;

import static com.ezerski.vladislav.uicomponents.constants.Constants.USER_PROFILE_IMAGE_URL;
import static com.ezerski.vladislav.uicomponents.constants.Constants.USER_PROFILE_SUBTITLE;
import static com.ezerski.vladislav.uicomponents.constants.Constants.USER_PROFILE_TITLE;

public class MainActivity extends FragmentActivity {

    private DrawerLayout drawer;

    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activity);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navView = findViewById(R.id.nvView);
        setupDrawerContent(navView);

        View headerLayout = navView.getHeaderView(0);

        TextView headTitle = headerLayout.findViewById(R.id.tv_header_title);
        headTitle.setText(USER_PROFILE_TITLE);
        TextView headSubTitle = headerLayout.findViewById(R.id.tv_header_subtitle);
        headSubTitle.setText(USER_PROFILE_SUBTITLE);
        ImageView profileView = headerLayout.findViewById(R.id.header_profile_image);
        Picasso.get().load(USER_PROFILE_IMAGE_URL).into(profileView);
        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileFragment fragment = new ProfileFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
                drawer.closeDrawers();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.home) {
            drawer.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        ColorFragment fragment = new ColorFragment();
        int color = 0;
        Bundle bundle = new Bundle();
        int i = menuItem.getItemId();
        if (i == R.id.nav_first_fragment) {
            color = Color.RED;
            bundle.putInt("color", color);
            fragment.setArguments(bundle);
        } else if (i == R.id.nav_second_fragment) {
            color = Color.BLUE;
            bundle.putInt("color", color);
            fragment.setArguments(bundle);
        } else if (i == R.id.nav_third_fragment) {
            color = Color.BLACK;
            bundle.putInt("color", color);
            fragment.setArguments(bundle);
        } else if (i == R.id.nav_fourth_fragment) {
            color = Color.GREEN;
            bundle.putInt("color", color);
            fragment.setArguments(bundle);
        } else if (i == R.id.nav_fifth_fragment) {
            color = Color.YELLOW;
            bundle.putInt("color", color);
            fragment.setArguments(bundle);
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.flContent, fragment).commit();

        drawer.closeDrawers();
    }
}