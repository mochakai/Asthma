package com.example.user.asthma;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    public static final String defaultAccount = "Account";
    public void loginVerify(String uid, String pw){
        ServerConnection askServer = new ServerConnection("login", this, new ServerResponse() {
            @Override
            public void onServerResponse(String result, Context caller) {
                //parse result as json
                String status = "";
                try{
                    JSONObject json = new JSONObject(result);
                    status = json.getString("status");
                    String msg = json.getString("msg");
                    Toast.makeText(caller, msg, Toast.LENGTH_SHORT).show();
                }catch(JSONException e){
                    e.printStackTrace();
                }
                if (status.equals("success")){
                    Log.d("login procedure", "success");
                    ((Activity)caller).finish();
                }
            }
        });

        JSONObject loginData = ServerConnection.loginJson(uid, pw);
        askServer.execute(loginData);
    }


    public void login(View v){
        String uid = ((EditText) findViewById(R.id.account)).getText().toString();
        String pw = ((EditText) findViewById(R.id.password)).getText().toString();
        //ask server
        loginVerify(uid, pw);
        //store login records
        SharedPreferences accounts = getSharedPreferences(defaultAccount, 0);
        SharedPreferences.Editor spEditor = accounts.edit();
        spEditor.putString("account", uid);
        spEditor.putString("password", pw);
        spEditor.apply();
    }

    public void goRegister(View v){
        //start registerActivity
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // show saved login information
        EditText edAccount = (EditText) findViewById(R.id.account);
        EditText edPwd = (EditText) findViewById(R.id.password);
        SharedPreferences accounts = getSharedPreferences(defaultAccount, 0);
        String uid = accounts.getString("account", "");
        String pw = accounts.getString("password", "");
        edAccount.setText(uid);
        edPwd.setText(pw);
        //test if can login
        loginVerify(uid, pw);
    }

    @Override
    protected void onStop(){
        super.onStop();
    }
}
