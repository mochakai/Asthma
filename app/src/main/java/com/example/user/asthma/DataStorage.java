package com.example.user.asthma;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Donny on 2017/9/19
 */

class DataStorage extends SQLiteOpenHelper{
    private static DataStorage instance = null;
    private static DataStorage getInstance(Context context){
        if (instance==null)
            instance = new DataStorage(context, "userData.db", null, 3);
        return instance;
    }

    private DataStorage(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // when a db is not created
        db.execSQL("CREATE TABLE main" +
                    "(id INTEGER PRIMARY KEY  NOT NULL ," +
                    " date DATETIME  NOT NULL , "+
                    " fever BOOLEAN ," +
                    " sleep INTEGER , daytime INTEGER ," +
                    " nose1 INTEGER , nose2 INTEGER , nose3 INTEGER , nose4 INTEGER ,"  +
                    " eye INTEGER ,skin INTEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //when users database is not upgraded after updating app
        if (newVersion == 3){
            db.execSQL("DROP TABLE main");
            onCreate(db);
        }
    }

    static boolean insert(Context context, dataFormat data){
        DataStorage ds = getInstance(context);
        long id = ds.getWritableDatabase().insert("main", null, data.toContentValues());
        Log.d("db", ds.getDatabaseName()+" insert with id: "+id);
        return id != -1;
    }

    static XYMultipleSeriesDataset getValues(Context context, String... fields){
        DataStorage ds = getInstance(context);

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();


        for (String field : fields) {
            TimeSeries series = new TimeSeries(field);

            /*Calendar c = Calendar.getInstance();
            c.set(2017, 9, 24, 0, 0, 0);
            series.add(c.getTime(), 4);
            c.add(Calendar.DATE, -1);
            series.add(c.getTime(), 1);
            c.add(Calendar.DATE, -1);
            series.add(c.getTime(), 2);*/
            try (Cursor cursor = ds.getReadableDatabase().query("main", new String[] {field, "date"}, null, null, null, null, null)) {
                String log = "";
                for (String tt: cursor.getColumnNames()) log += tt;
                Log.d("database", log + " " + field);
                if (cursor.moveToFirst()) do {
                    double val = cursor.getDouble(0);
                    Date date = null;
                    try {
                        String tmp = cursor.getString(cursor.getColumnIndex("date"));
                        date = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.US).parse(tmp);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    if (date == null) continue;
                    series.add(date, val);
                    Log.d("date and val", date.toString() + Double.toString(val));
                } while (cursor.moveToNext());
            } finally {
                Log.d("read value", field);

            }
            dataset.addSeries(series);
        }

        /*TimeSeries serie = new TimeSeries("test");

        Calendar c = Calendar.getInstance();
        c.set(2017, 9, 24, 0, 0, 0);
        serie.add(c.getTime(), 1);
        c.add(Calendar.DATE, -1);
        serie.add(c.getTime(), 2);
        c.add(Calendar.DATE, -1);
        serie.add(c.getTime(), 4);
        dataset.addSeries(serie);*/
        return dataset;
    }
}

