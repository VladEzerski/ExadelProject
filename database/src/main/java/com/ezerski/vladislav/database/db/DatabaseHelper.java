package com.ezerski.vladislav.database.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "postsDatabase.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE = "posts";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDB) {
        sqLiteDB.execSQL(" CREATE TABLE IF NOT EXISTS " + TABLE +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT, _user_id INTEGER, title TEXT, body TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDB, int i, int i1) {
        sqLiteDB.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(sqLiteDB);
    }
}
