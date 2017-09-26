package com.example.user.asthma;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Donny on 2017/9/19
 */

class dataFormat {
    Date date;
    int fever, sleep, daytime, nose1, nose2, nose3, nose4, eye, skin;

    ContentValues toContentValues(){
        ContentValues val = new ContentValues();
        val.put("date", date.toString());
        val.put("fever", fever);val.put("sleep", sleep);val.put("daytime", daytime);
        val.put("nose1", nose1);val.put("nose2", nose2);val.put("nose3", nose3);val.put("nose4", nose4);
        val.put("eye", eye);val.put("skin", skin);
        return val;
    }
    JSONObject toJSON(){
        JSONObject ret = new JSONObject();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ret.accumulate("time", sdf.format(date))
                .accumulate("fever", fever).accumulate("sleep", sleep).accumulate("daytime", daytime)
                .accumulate("nose1", nose1).accumulate("nose2", nose2).accumulate("nose3", nose3).accumulate("nose4", nose4)
                .accumulate("eye", eye).accumulate("skin", skin);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
