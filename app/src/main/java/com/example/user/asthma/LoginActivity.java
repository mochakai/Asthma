package com.example.user.asthma;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements ServerConnection.ServerResponse{
    public static final String defaultAccount = "Account";
    public static final int FUNC_REG = 1;
    public void loginVerify(String uid, String pw){
        if (uid.equals("") || pw.equals(""))
            return;
        ServerConnection askServer = new ServerConnection("login", this);
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
        startActivityForResult(intent, FUNC_REG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FUNC_REG){
            if (resultCode == RESULT_OK){//fill in registered account
                EditText edAccount = (EditText) findViewById(R.id.account);
                edAccount.setText(data.getStringExtra("register_account"));
            }else if (resultCode == RESULT_CANCELED){
                Log.d("Login Activity", "register cancelled");
            }
        }
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
    public void onBackPressed(){
        //return login fail to mainactivity
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    public void onServerResponse(String result) {
        //parse result as json
        String success = "";
        String msg = "";
        try{
            JSONObject json = new JSONObject(result);
            success = json.getString("success");
            msg = json.getString("msg");
            ServerConnection.uid = json.getString("uid");
        }catch(JSONException e){
            e.printStackTrace();
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        if (success.equals("true")){
            Log.d("login procedure", "success");
            setResult(RESULT_OK);
            finish();
        }
    }
}
