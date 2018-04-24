package com.ezerski.vladislav.database.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "postsDatabase.db";
    private static final int DATABASE_VERSION = 1;
    protected static final String TABLE = "posts";

    protected static final String COLUMN_USER_ID = "_user_id";
    protected static final String COLUMN_ID = "_id";
    protected static final String COLUMN_TITLE = "title";
    protected static final String COLUMN_BODY = "body";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDB) {
        sqLiteDB.execSQL("CREATE TABLE " + TABLE + "( _id INTEGER, _user_id INTEGER, title TEXT, body TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDB, int i, int i1) {
        sqLiteDB.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(sqLiteDB);
    }
}
