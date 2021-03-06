package com.example.danielmurphy.kiddoemrclient;

import android.database.Cursor;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TipsDbAdpater {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_TEXT = "code";
    public static final String KEY_DATE = "name";
    private static final String TAG = "TipsDbAdapter";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "HealthTips";
    private static final String SQLITE_TABLE = "Tips";
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;

    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    KEY_TEXT + "," +
                    KEY_DATE + ");";

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            onCreate(db);
        }
    }

    public TipsDbAdpater(Context ctx) {
        this.mCtx = ctx;
    }

    public TipsDbAdpater open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public long createMessage(String text, String date) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TEXT, text);
        initialValues.put(KEY_DATE, date);

        return mDb.insert(SQLITE_TABLE, null, initialValues);
    }

    public boolean deleteAllEntries() {
        int doneDelete = 0;
        doneDelete = mDb.delete(SQLITE_TABLE, null , null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;
    }

    public Cursor fetchTipsByName(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,
                            KEY_TEXT, KEY_DATE},
                    null, null, null, null, null);

        }
        else {
            mCursor = mDb.query(true, SQLITE_TABLE, new String[] {KEY_ROWID,
                            KEY_TEXT, KEY_DATE},
                    KEY_DATE + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor fetchAllTips() {
        Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,
                        KEY_TEXT, KEY_DATE },
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void insertMessage(String message, long date) {
        createMessage(message, String.valueOf(date));
    }
}