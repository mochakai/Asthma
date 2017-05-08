package com.example.user.asthma;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public void login(View v){
        String uid = ((EditText) findViewById(R.id.account)).getText().toString();
        String pw = ((EditText) findViewById(R.id.password)).getText().toString();

        Toast.makeText(this, "login success", Toast.LENGTH_LONG).show();
        getIntent().putExtra("LOGIN_USERID", uid);
        getIntent().putExtra("LOGIN_PASSWD", pw);
        setResult(RESULT_OK, getIntent());
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
