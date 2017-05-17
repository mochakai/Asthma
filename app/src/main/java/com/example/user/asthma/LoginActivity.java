package com.example.user.asthma;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    String lg_account = "donny";
    String lg_pwd = "donny";
    public static final String defaultAccount = "Account";

    private boolean loginVerify(String uid, String pw){
        //dummy function
        return (uid.equals(lg_account)&& pw.equals(lg_pwd));
    }

    public void login(View v){
        String uid = ((EditText) findViewById(R.id.account)).getText().toString();
        String pw = ((EditText) findViewById(R.id.password)).getText().toString();

        if (loginVerify(uid, pw)){
            Toast.makeText(this, "login success", Toast.LENGTH_LONG).show();
            getIntent().putExtra("LOGIN_USERID", uid);
            getIntent().putExtra("LOGIN_PASSWD", pw);
            setResult(RESULT_OK, getIntent());

            SharedPreferences accounts = getSharedPreferences(defaultAccount, 0);
            SharedPreferences.Editor spEditor = accounts.edit();
            spEditor.putString("account", uid);
            spEditor.putString("password", pw);
            spEditor.apply();

            finish();
        }
        else {
            Toast.makeText(this, "login fail", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText edAccount = (EditText) findViewById(R.id.account);
        EditText edPwd = (EditText) findViewById(R.id.password);
        SharedPreferences accounts = getSharedPreferences(defaultAccount, 0);
        edAccount.setText(accounts.getString("account", ""));
        edPwd.setText(accounts.getString("password", ""));
    }

    @Override
    protected void onStop(){
        super.onStop();

    }
}
