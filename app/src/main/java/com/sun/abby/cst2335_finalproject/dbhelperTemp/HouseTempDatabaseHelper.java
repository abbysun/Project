package com.sun.abby.cst2335_finalproject.dbhelperTemp;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Created by yun on 2016-12-05.
 */

public class HouseTempDatabaseHelper extends SQLiteOpenHelper {
    public static String ACTIVITY_NAME = "HouseTempDatabaseHelper";
    public static final String DATABASE_NAME = "temps.db";
    private static final int VERSION_NUM = 3;
    private static final String TAG = "HouseTempDatabaseHelper";
    public static final String KEY_ID = "_id";
    public static final String KEY_TEMPERATURE = "temperature";
    public static final String KEY_TIME = "time";
    public static final String TABLE_NAME = "temperature_time";

    private HouseTempDatabaseHelper houseTempDatabaseHelper;
    private SQLiteDatabase db;

    private static final String DATABASE_CREATE = "Create table " + TABLE_NAME +
            "( " + KEY_ID + " integer primary key autoincrement, "
            + KEY_TIME + " text not null, "
            + KEY_TEMPERATURE + " text not null);";

    public HouseTempDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "Calling onCreate");

        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVision, int newVersion) {

        Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldVersion=" + oldVision + " newVersion=" + newVersion);
        Log.i(TAG, "Updating database from version " + oldVision + " to " + newVersion
                + ", while old data will be destroyed. ");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public void close() {
        if(houseTempDatabaseHelper != null){
            houseTempDatabaseHelper.close();
        }

    }
}
