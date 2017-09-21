package com.example.user.asthma;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class JournalActivity extends AppCompatActivity {

    private SeekBar sleepBar, breathBar;
    private TextView sleepText, breathText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        seekBarInitial();
    }

    public void journalSaved(View v){
        this.finish();
    }

    public void seekBarInitial(){
        //sleepBar setting
        sleepBar = (SeekBar) findViewById(R.id.sleepBar);
        sleepText = (TextView) findViewById(R.id.sleepText);
        seekBarReact(sleepBar.getProgress(), sleepText, "sleep");
        sleepBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int curProgress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                curProgress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarReact(curProgress, sleepText, "sleep");
            }
        });

        //breathBar setting
        breathBar = (SeekBar) findViewById(R.id.breathBar);
        breathText = (TextView) findViewById(R.id.breathText);
        seekBarReact(breathBar.getProgress(), breathText, "breath");
        breathBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int curProgress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                curProgress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarReact(curProgress, breathText, "breath");
            }
        });

    }

    public void seekBarReact(int Progress, TextView text, String type){
        switch (type) {
            case "sleep":
                switch (Progress) {
                    case 0:
                        text.setText("安穩");
                        text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        break;
                    case 1:
                        text.setText("不太好");
                        text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        break;
                    case 2:
                        text.setText("極差");
                        text.setTextColor(getResources().getColor(R.color.alert));
                        break;
                    default:
                        text.setText("N/A");
                        break;
                }
                break;
            case "breath":
                switch (Progress) {
                    case 0:
                        text.setText("順暢");
                        text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        break;
                    case 1:
                        text.setText("不太好");
                        text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        break;
                    case 2:
                        text.setText("困難");
                        text.setTextColor(getResources().getColor(R.color.alert));
                        break;
                    default:
                        text.setText("N/A");
                        break;
                }
                break;

        }
    }
}
