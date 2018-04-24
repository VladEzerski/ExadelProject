package com.ezerski.vladislav.database.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.ezerski.vladislav.utils.model.Post;

import java.util.ArrayList;
import java.util.List;

public class DBAdapter {

    public DBHelper dbHelper;
    public SQLiteDatabase database;

    public DBAdapter(Context context) {
        dbHelper = new DBHelper(context.getApplicationContext());
        database = dbHelper.getReadableDatabase();
    }

    public DBAdapter open() {
        database = dbHelper.getReadableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public Cursor getAllEntries() {
        String[] columns = new String[]{DBHelper.COLUMN_ID, DBHelper.COLUMN_USER_ID,
                DBHelper.COLUMN_TITLE, DBHelper.COLUMN_BODY};
        return database.query(DBHelper.TABLE, columns, null, null, null, null, null);
    }

    public List<Post> getPosts() {
        List<Post> posts = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if (cursor.moveToFirst()) {
            do {
                int userId = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_USER_ID));
                int id = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TITLE));
                String body = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_BODY));
                posts.add(new Post(userId, title, body));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return posts;
    }

    public long getCount() {
        return DatabaseUtils.queryNumEntries(database, DBHelper.TABLE);
    }

    public Post getPost(long id) {
        Post post = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?", DBHelper.TABLE, DBHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            int userId = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_USER_ID));
            String title = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TITLE));
            String body = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_BODY));
            post = new Post(userId, title, body);
        }
        cursor.close();
        return post;
    }

    public long insert(Post post) {
        ContentValues contVal = new ContentValues();
        contVal.put(DBHelper.COLUMN_USER_ID, post.getUserId());
        contVal.put(DBHelper.COLUMN_TITLE, post.getTitle());
        contVal.put(DBHelper.COLUMN_BODY, post.getBody());

        return database.insert(DBHelper.TABLE, null, contVal);
    }

    public long delete(long postId) {
        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(postId)};
        return database.delete(DBHelper.TABLE, whereClause, whereArgs);
    }

    public long update(Post post) {
        String whereClause = DBHelper.COLUMN_ID + "=" + String.valueOf(post.getId());
        ContentValues contVal = new ContentValues();
        contVal.put(DBHelper.COLUMN_USER_ID, post.getUserId());
        contVal.put(DBHelper.COLUMN_TITLE, post.getTitle());
        contVal.put(DBHelper.COLUMN_BODY, post.getBody());
        return database.update(DBHelper.TABLE, contVal, whereClause, null);
    }
}