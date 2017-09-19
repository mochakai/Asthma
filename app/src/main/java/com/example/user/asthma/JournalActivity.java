package com.example.user.asthma;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class JournalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
    }

    public void journalSaved(View v){
        this.finish();
    }
}
