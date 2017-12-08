package com.example.krishnaveni.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by KRISHNAVENI on 2017-10-11.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME="Messages.db";
    private static int VERSION_NUM=3;
    public final static String KEY_ID ="_ID";
    public final static String KEY_MESSAGE ="MESSAGES";
    public static String TABLE_NAME="ChatMessages";

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TABLE_NAME  + "( _ID INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MESSAGE +" TEXT)";
        Log.i("ChatDatabaseHelper", "Calling onCreate");

        db.execSQL(sql);}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        Log.i(TAG, "onOpen: Krishna ");
        super.onOpen(db);
    }
}

