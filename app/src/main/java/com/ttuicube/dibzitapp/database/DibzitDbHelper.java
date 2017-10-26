package com.ttuicube.dibzitapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.ttuicube.dibzitapp.database.DibzitContract.*;

/**
 * Created by Zeejfps on 10/26/17.
 */

public class DibzitDbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_WORKING_HOURS_TABLE =
        "CREATE TABLE " + WorkingHoursEntry.TABLE_NAME + " (" +
        WorkingHoursEntry._ID + " INTEGER PRIMARY KEY," +
        WorkingHoursEntry.COLUMN_ROOM_ID + " INTEGER,"  +
        WorkingHoursEntry.COLUMN_DATE + " TEXT," +
        WorkingHoursEntry.COLUMN_START_TIME +  " TEXT," +
        WorkingHoursEntry.COLUMN_END_TIME + " TEXT)";

    private static final String SQL_DROP_WORKING_HOURS_TABLE =
        "DROP TABLE IF EXISTS " + WorkingHoursEntry.TABLE_NAME;

    public static final String DATABASE_NAME = "Dibzit.db";
    public static final int DATABASE_VERSION = 1;

    public DibzitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_WORKING_HOURS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_WORKING_HOURS_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
