package com.ezerski.vladislav.uicomponents.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezerski.vladislav.uicomponents.model.ProfileModel;
import com.ezerski.vladislav.uicomponents.R;
import com.squareup.picasso.Picasso;

import jp.wasabeef.blurry.Blurry;

import static com.ezerski.vladislav.uicomponents.constants.Constants.USER_PROFILE_IMAGE_URL;
import static com.ezerski.vladislav.uicomponents.constants.Constants.USER_PROFILE_SUBTITLE;
import static com.ezerski.vladislav.uicomponents.constants.Constants.USER_PROFILE_TITLE;

public class ProfileFragment extends Fragment {

    private FrameLayout layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView tvTitle = view.findViewById(R.id.tv_profile_title);
        TextView tvSubtitle = view.findViewById(R.id.tv_profile_subtitle);
        ImageView imgProfile = view.findViewById(R.id.profile_image);
        ImageView imgFirst = view.findViewById(R.id.iv_topview);

        ProfileModel profileData = new ProfileModel(USER_PROFILE_TITLE, USER_PROFILE_SUBTITLE);

        tvTitle.setText(profileData.getTitle());
        tvSubtitle.setText(profileData.getSubTitle());

        Picasso.get().load(USER_PROFILE_IMAGE_URL).into(imgProfile);

        Picasso.get().load(USER_PROFILE_IMAGE_URL).into(imgFirst);

        layout = view.findViewById(R.id.frag_layout);
        layout.post(new Runnable() {
            @Override
            public void run() {
                Blurry.with(getContext())
                        .radius(15)
                        .async()
                        .sampling(8)
                        .onto(layout);
            }
        });
        return view;
    }
}