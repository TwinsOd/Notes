package com.github.twinsod.notes.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 08-Jun-17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, DatabaseConst.DATABASE.NAME, null, DatabaseConst.DATABASE.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createNewOne(db);
    }

    private void createNewOne(SQLiteDatabase db) {
        db.execSQL(DatabaseConst.QUERY.CREATE_TABLE_NOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        deleteOld(db);
        createNewOne(db);
    }

    private void deleteOld(SQLiteDatabase db) {
        db.execSQL(DatabaseConst.QUERY.CREATE_TABLE_NOTES);
    }
}
