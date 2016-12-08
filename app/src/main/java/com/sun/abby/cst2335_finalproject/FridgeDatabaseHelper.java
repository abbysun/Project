package com.sun.abby.cst2335_finalproject;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * This is a database helper class for accessing the fridge items database
 * Created by Abby on 2016-12-04.
 */
public class FridgeDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "FoodDatabase";
    public static int DATABASE_VERSION = 1;
    public static final String TABLE_FRIDGE = "FridgeItem";
    public static final String TABLE_FREEZER = "FreezerItem";
    public static final String COLUMN1 = "_id";
    public static final String COLUMN2 = "name";
    public static final String COLUMN3 = "description";

    public FridgeDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("FridgeDatabaseHelper", "create tables in database" );
        String CREATE_FRIDGE_TABLE = "CREATE TABLE " + TABLE_FRIDGE + " (" + COLUMN1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN2 + " TEXT, " + COLUMN3 + " TEXT)";
        Log.i("FridgeDatabaseHelper", "Testing: " + CREATE_FRIDGE_TABLE);
        db.execSQL(CREATE_FRIDGE_TABLE);

        String CREATE_FREEZER_TABLE = "CREATE TABLE " + TABLE_FREEZER + " (" + COLUMN1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN2 + " TEXT, " + COLUMN3 + " TEXT)";
        Log.i("FridgeDatabaseHelper", "Testing: " + CREATE_FREEZER_TABLE);
        db.execSQL(CREATE_FREEZER_TABLE);

        //add data
        db.execSQL("INSERT INTO " + TABLE_FRIDGE + " (" + COLUMN2 + ", " + COLUMN3 + ") VALUES('milk', '2%')");
        db.execSQL("INSERT INTO " + TABLE_FRIDGE + " (" + COLUMN2 + ", " + COLUMN3 + ") VALUES('eggs', 'large')");
        db.execSQL("INSERT INTO " + TABLE_FRIDGE + " (" + COLUMN2 + ", " + COLUMN3 + ") VALUES('cheddar cheese', '50 grams')");

        db.execSQL("INSERT INTO " + TABLE_FREEZER + " (" + COLUMN2 + ", " + COLUMN3 + ") VALUES('frozen fruit', 'strawberries')");
        db.execSQL("INSERT INTO " + TABLE_FREEZER + " (" + COLUMN2 + ", " + COLUMN3 + ") VALUES('ground beef', '1 pound')");
        db.execSQL("INSERT INTO " + TABLE_FREEZER + " (" + COLUMN2 + ", " + COLUMN3 + ") VALUES('dumplings', '20 medium')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIDGE);
        onCreate(db);
        Log.i("FridgeDatabaseHelper", "onUpdate version " + oldVersion +" to new database version: " +  newVersion );
    }

    public void insertFridgeData(String name, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN2, name);
        contentValues.put(COLUMN3, description);
        long insertResult = db.insert(TABLE_FRIDGE, null, contentValues);
        Log.i("FridgeDatabaseHelper", "insert fridge data result: " + insertResult );
    }

    public void removeFridgeData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_FRIDGE + " WHERE " + COLUMN1 + "=" + id);
    }

    public void removeFreezerData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_FREEZER + " WHERE " + COLUMN1 + "=" + id);
    }

    public void insertFreezerData(String name, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN2, name);
        contentValues.put(COLUMN3, description);
        long insertResult = db.insert(TABLE_FREEZER, null, contentValues);
        Log.i("FridgeDatabaseHelper", "insert freezer data result: " + insertResult );
    }

    public Cursor getFridgeData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FRIDGE, null);
        Log.i("FridgeDatabaseHelper", "check fridge value " + cursor.getCount());
        return cursor;
    }

    public Cursor getFreezerData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FREEZER, null);
        Log.i("FridgeDatabaseHelper", "check freezer value " + cursor.getCount());
        return cursor;
    }
}

