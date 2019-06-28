package com.example.inventory.SQLiteHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.inventory.SQLiteHelpers.DatabaseContract.*;
import com.example.inventory.Models.ComponentModel;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Inventory.db";
//    public static Cursor cursor;



    final String CREATE_TABLE_COMPONENTS =
            " CREATE TABLE "+ DatabaseEntry.TABLE_COMPONENTS+" ("
             +DatabaseEntry.COMPONENTS_ID+" INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, "
             +DatabaseEntry.COMPONENTS_COMP+" TEXT, "
             +DatabaseEntry.COMPONENTS_CAT+" TEXT, "
             +DatabaseEntry.COMPONENTS_COUNT+" INTEGER, "
             +DatabaseEntry.COMPONENTS_DATE+" TEXT, "
             +DatabaseEntry.COMPONENTS_ADMIN+" TEXT" + ");" ;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_COMPONENTS =
                " CREATE TABLE "+ DatabaseEntry.TABLE_COMPONENTS+" ("
                        +DatabaseEntry.COMPONENTS_ID+" INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, "
                        +DatabaseEntry.COMPONENTS_COMP+" TEXT, "
                        +DatabaseEntry.COMPONENTS_CAT+" TEXT, "
                        +DatabaseEntry.COMPONENTS_COUNT+" INTEGER, "
                        +DatabaseEntry.COMPONENTS_DATE+" TEXT, "
                        +DatabaseEntry.COMPONENTS_ADMIN+" TEXT" + ");" ;
        db.execSQL(CREATE_TABLE_COMPONENTS);
        Log.d("DATABASE","Table Created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DatabaseContract.DatabaseEntry.TABLE_COMPONENTS);
        onCreate(db);
    }

    public boolean insertComponent (ComponentModel components){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseEntry.COMPONENTS_COMP,components.getComponents());
        contentValues.put(DatabaseEntry.COMPONENTS_CAT,components.getCategory());
        contentValues.put(DatabaseEntry.COMPONENTS_COUNT,components.getCount());
        contentValues.put(DatabaseEntry.COMPONENTS_DATE,components.getDate());
        contentValues.put(DatabaseEntry.COMPONENTS_ADMIN,components.getAdmin());
        long result = db.insert(DatabaseEntry.TABLE_COMPONENTS,null,contentValues);
        db.close();
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllComponents(){
        SQLiteDatabase db= getWritableDatabase();
        return db.query(DatabaseEntry.TABLE_COMPONENTS,
                null,
                null,
                null,
                null,
                null,
                DatabaseEntry.COMPONENTS_ID + " DESC"
                );
    }

    public Cursor searchComponents(String keyword) {
        Cursor cursor;

            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            return sqLiteDatabase.rawQuery("select * from "
                    + DatabaseEntry.TABLE_COMPONENTS + " where " +
                    DatabaseEntry.COMPONENTS_COMP + " like ?", new String[]{"%" + keyword + "%"});


    }







}
