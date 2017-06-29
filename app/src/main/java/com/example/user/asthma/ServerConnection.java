package com.example.user.asthma;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.user.asthma.LoginActivity.defaultAccount;

/**
 * internet connection to server
 * Network cannot run in main(UI) thread, so use asyncTask
**/

class ServerConnection extends AsyncTask<JSONObject, Void, String> {
    private static String server_url = "http://140.113.123.156:33333";
    private String type;
    private Context caller;
    private ServerResponse responseFunc;

    static JSONObject loginJson(String uid, String pw){
        try{
            JSONObject packet = new JSONObject();
            packet.accumulate("account", uid).accumulate("password", pw);
            return packet;
        }catch (JSONException e){
            Log.e("Json error", e.toString());
        }
        return null;
    }
    static JSONObject registerJson(String uid, String pw){
        try{
            JSONObject packet = new JSONObject();
            packet.accumulate("account", uid).accumulate("password", pw);
            return packet;
        }catch (JSONException e){
            Log.e("Json error", e.toString());
        }
        return null;
    }

    private String sendToServer (JSONObject json){
        Log.i("login", json.toString());
        OkHttpClient client = new OkHttpClient();
        RequestBody reqBody = RequestBody.create(MediaType.parse("application/json"), json.toString());
        Headers header = new Headers.Builder().add("Content-Type", "application/json").build();
        Request req = new Request.Builder().url(server_url+ "/" + type).post(reqBody).headers(header).build();
        Log.d("req body", reqBody.toString());
        Response res;
        String result;
        result = "";
        try {
            res = client.newCall(req).execute();
            result = res.body().string();
            if (result == null) result="";
        }catch (IOException e){
            e.printStackTrace();
            Log.w("login error", "no internet or timeout");
        }
        return result;
    }

    ServerConnection(String connectType, Context caller, ServerResponse responseFunc){
        this.type = connectType;
        this.caller = caller;
        this.responseFunc = responseFunc;
    }

    @Override
    protected String doInBackground(JSONObject... params) {
        return sendToServer(params[0]);
    }

    @Override
    protected void onPostExecute(String result){
        Log.d("Async Task result", result);
        responseFunc.onServerResponse(result, caller);
    }
}
