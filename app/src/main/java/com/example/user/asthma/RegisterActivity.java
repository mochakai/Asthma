package com.example.user.asthma;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    public void registerVerify(String uid, String pw){
        JSONObject registerData = ServerConnection.registerJson(uid, pw);
        ServerConnection askServer = new ServerConnection("register", this, new ServerResponse() {
            @Override
            public void onServerResponse(String result, Context caller) {
                String status = "";
                String account = "";
                try{
                    JSONObject json = new JSONObject(result);
                    status = json.getString("status");
                    String msg = json.getString("msg");
                    account = json.getString("account");
                    Toast.makeText(caller, msg, Toast.LENGTH_LONG).show();
                }catch(JSONException e){
                    e.printStackTrace();
                }
                if (status.equals("success")){
                    Log.d("register response", "success");

                    Intent i = new Intent();
                    i.putExtra("register_account", account);
                    ((Activity)caller).setResult(RESULT_OK, i);
                    ((Activity)caller).finish();
                }
            }
        });
        askServer.execute(registerData);
    }

    public void register(View v){
        String uid = ((EditText) findViewById(R.id.registerAccount)).getText().toString();
        EditText pwE = (EditText) findViewById(R.id.registerPassword);
        String pw = pwE.getText().toString();
        EditText pwConE = (EditText) findViewById(R.id.registerPasswordConfirm);
        String pwCon = pwConE.getText().toString();

        if (!pw.equals(pwCon)){
            Toast.makeText(this, "Re-enter your password", Toast.LENGTH_SHORT).show();
            pwE.clearComposingText();
            pwConE.clearComposingText();
        }else{
            //ask server to register
            registerVerify(uid, pw);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public void onBackPressed(){
        setResult(RESULT_CANCELED);
        finish();
    }

}
