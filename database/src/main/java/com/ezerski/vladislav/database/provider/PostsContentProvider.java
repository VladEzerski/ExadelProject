package com.ezerski.vladislav.database.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.ezerski.vladislav.database.db.DatabaseHelper;

import static com.ezerski.vladislav.database.db.DatabaseHelper.TABLE;
import static com.ezerski.vladislav.database.model.PostsInfo.COLUMN_ID;
import static com.ezerski.vladislav.database.model.PostsInfo.CONTENT_ITEM_TYPE;
import static com.ezerski.vladislav.database.model.PostsInfo.CONTENT_TYPE;
import static com.ezerski.vladislav.database.model.PostsInfo.CONTENT_URI;
import static com.ezerski.vladislav.database.model.PostsInfo.PROVIDER_NAME;


public class PostsContentProvider extends ContentProvider {

    private static final int POSTS = 1;
    private static final int POSTS_ID = 2;

    protected static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "posts", POSTS);
        uriMatcher.addURI(PROVIDER_NAME, "posts/#", POSTS_ID);
    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        database = dbHelper.getReadableDatabase();
        return database != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE);

        switch (uriMatcher.match(uri)) {
            case POSTS:
                break;
            case POSTS_ID:
                String id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(COLUMN_ID + "=" + id);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI : " + uri);
        }
        Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs,
                null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case POSTS:
                return CONTENT_TYPE;
            case POSTS_ID:
                return CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        long postID = database.insert(TABLE, "", values);

        if (postID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, postID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a new record " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count;
        switch (uriMatcher.match(uri)) {
            case POSTS:
                count = database.delete(TABLE, selection, selectionArgs);
                database.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE + "'");
                break;
            case POSTS_ID:
                String id = uri.getPathSegments().get(1);
                count = database.delete(TABLE, COLUMN_ID + " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection +
                                ')' : ""), selectionArgs);
                database.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE + "'");
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case POSTS:
                count = database.update(TABLE, values, selection, selectionArgs);
                break;
            case POSTS_ID:
                String id = uri.getPathSegments().get(1);
                count = database.update(TABLE, values,
                        COLUMN_ID + " = " + id +
                                (!TextUtils.isEmpty(selection) ? " AND (" + selection +
                                        ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}