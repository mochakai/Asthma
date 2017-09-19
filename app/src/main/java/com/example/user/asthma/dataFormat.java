package com.example.user.asthma;

import android.content.ContentValues;

import java.util.Date;

/**
 * Created by Donny on 2017/9/19
 */

public class dataFormat {
    Date date;

    ContentValues toContentValues(){
        ContentValues val = new ContentValues();
        val.put("date", date.toString());
        return val;
    }
}
