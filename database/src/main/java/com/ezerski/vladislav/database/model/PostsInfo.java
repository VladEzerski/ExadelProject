package com.ezerski.vladislav.database.model;

import android.net.Uri;

public class PostsInfo {
    public static final String PROVIDER_NAME =
            "com.ezerski.vladislav.database.provider.PostsContentProvider";
    private static final String URL = "content://" + PROVIDER_NAME + "/posts";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." +
            PROVIDER_NAME + ".posts";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." +
            PROVIDER_NAME + ".posts";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_BODY = "body";

    public static final String[] DEFAULT_DATA = new String[]{
            COLUMN_ID,
            COLUMN_TITLE,
            COLUMN_BODY
    };
}