package com.example.user.asthma;

import android.content.ContentValues;

import java.util.Date;

/**
 * Created by Donny on 2017/9/19
 */

class dataFormat {
    private Date date;
    private int temp;

    ContentValues toContentValues(){
        ContentValues val = new ContentValues();
        val.put("date", date.toString());
        val.put("temp", temp);
        return val;
    }
}
