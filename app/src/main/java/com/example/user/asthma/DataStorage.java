package com.example.user.asthma;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Donny on 2017/9/19
 */

public class DataStorage extends SQLiteOpenHelper{

    public DataStorage(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // when a db is not created
        db.execSQL("CREATE TABLE main" +
                    "(id INTEGER PRIMARY KEY  NOT NULL ," +
                    " date DATETIME  NOT NULL)");//TODO: database fields
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //when users database is not upgraded after updating app
    }

    public boolean insert(dataFormat data){
        long id = getWritableDatabase().insert("daily", null, data.toContentValues());
        Log.d("db", getDatabaseName()+" insert with id: "+id);
        return id != -1;
    }

    public double[] getValues(String field){
        return null;//TODO: what to return when queried
    }
}

