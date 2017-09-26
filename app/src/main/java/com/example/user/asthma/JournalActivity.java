package com.example.user.asthma;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class JournalActivity extends AppCompatActivity implements ServerConnection.ServerResponse{

    private SeekBar sleepBar, daytimeBar, nose1Bar, nose2Bar, nose3Bar, nose4Bar, eyeBar, skinBar;
    private TextView sleepText, daytimeText, nose1Text, nose2Text, nose3Text, nose4Text, eyeText, skinText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        seekBarInitial();
    }

    public void journalSaved(View v){
        //send to server
        ServerConnection askServer = new ServerConnection("predict/" + ServerConnection.uid, this);

        //prepare data
        dataFormat df = new dataFormat();
        df.date = new Date();
        df.fever = ((CheckBox) findViewById(R.id.feverT)).isChecked()? 1:0;
        df.sleep = sleepBar.getProgress();
        df.daytime = daytimeBar.getProgress();
        df.nose1 = nose1Bar.getProgress();
        df.nose2 = nose2Bar.getProgress();
        df.nose3 = nose3Bar.getProgress();
        df.nose4 = nose4Bar.getProgress();
        df.eye = eyeBar.getProgress();
        df.skin = skinBar.getProgress();

        //send to server
        askServer.execute(df.toJSON());
        //insert to database
        if (!DataStorage.insert(this, df)) Log.w("database insert", "failed");
        ((Button)findViewById(R.id.saveJournal)).setText(R.string.waitServerMsg);
    }

    @Override
    public void onServerResponse(String result) {
        String predictMsg = "", prescription = "";
        try{
            JSONObject tmp = new JSONObject(result);
            if (tmp.getBoolean("success")) {
                JSONObject json = new JSONObject(tmp.getString("result"));
                predictMsg = json.getString("predict");
                prescription = json.getString("prescription");
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        Log.d("predict_msg", predictMsg);
        Log.d("prescription", prescription);
        //show dialog of response
        new AlertDialog.Builder(this)
                .setTitle(R.string.serverResult)
                .setMessage(predictMsg + "\n" + prescription)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                }).create().show();
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

        //daytimeBar setting
        daytimeBar = (SeekBar) findViewById(R.id.daytimeBar);
        daytimeText = (TextView) findViewById(R.id.daytimeText);
        seekBarReact(daytimeBar.getProgress(), daytimeText, "daytime");
        daytimeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                seekBarReact(curProgress, daytimeText, "daytime");
            }
        });

        //nose1Bar setting
        nose1Bar = (SeekBar) findViewById(R.id.nose1Bar);
        nose1Text = (TextView) findViewById(R.id.nose1Text);
        seekBarReact(nose1Bar.getProgress(), nose1Text, "nose1");
        nose1Bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                seekBarReact(curProgress, nose1Text, "nose1");
            }
        });

        //nose2Bar setting
        nose2Bar = (SeekBar) findViewById(R.id.nose2Bar);
        nose2Text = (TextView) findViewById(R.id.nose2Text);
        seekBarReact(nose2Bar.getProgress(), nose2Text, "nose2");
        nose2Bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                seekBarReact(curProgress, nose2Text, "nose2");
            }
        });

        //nose3Bar setting
        nose3Bar = (SeekBar) findViewById(R.id.nose3Bar);
        nose3Text = (TextView) findViewById(R.id.nose3Text);
        seekBarReact(nose3Bar.getProgress(), nose3Text, "nose3");
        nose3Bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                seekBarReact(curProgress, nose3Text, "nose3");
            }
        });

        //nose4Bar setting
        nose4Bar = (SeekBar) findViewById(R.id.nose4Bar);
        nose4Text = (TextView) findViewById(R.id.nose4Text);
        seekBarReact(nose4Bar.getProgress(), nose4Text, "nose4");
        nose4Bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                seekBarReact(curProgress, nose4Text, "nose4");
            }
        });

        //eyeBar setting
        eyeBar = (SeekBar) findViewById(R.id.eyeBar);
        eyeText = (TextView) findViewById(R.id.eyeText);
        seekBarReact(eyeBar.getProgress(), eyeText, "eye");
        eyeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                seekBarReact(curProgress, eyeText, "eye");
            }
        });

        //skinBar setting
        skinBar = (SeekBar) findViewById(R.id.skinBar);
        skinText = (TextView) findViewById(R.id.skinText);
        seekBarReact(skinBar.getProgress(), skinText, "skin");
        skinBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                seekBarReact(curProgress, skinText, "skin");
            }
        });
    }

    public void seekBarReact(int Progress, TextView text, String type){
        switch (type) {
            case "sleep":
                switch (Progress) {
                    case 0:
                        text.setText("一夜安睡");
                        text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        break;
                    case 1:
                        text.setText("安睡\n但間段咳嗽");
                        text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        break;
                    case 2:
                        text.setText("因咳嗽而醒\n可吸藥後入睡");
                        text.setTextColor(getResources().getColor(R.color.warning));
                        break;
                    case 3:
                        text.setText("咳鳴厲害\n睡眠困難");
                        text.setTextColor(getResources().getColor(R.color.alert));
                        break;
                    default:
                        text.setText("N/A");
                        break;
                }
                break;
            case "daytime":
                switch (Progress) {
                    case 0:
                        text.setText("無咳嗽\n正常作息運動");
                        text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        break;
                    case 1:
                        text.setText("間段乾咳");
                        text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        break;
                    case 2:
                        text.setText("咳嗽有痰\n運動會咳");
                        text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        break;
                    case 3:
                        text.setText("有喘鳴聲\n需使用吸入型擴張劑");
                        text.setTextColor(getResources().getColor(R.color.warning));
                        break;
                    case 4:
                        text.setText("呼吸喘促、咳不止\n需吃急救藥送醫");
                        text.setTextColor(getResources().getColor(R.color.alert));
                        break;
                    default:
                        text.setText("N/A");
                        break;
                }
                break;
            case "nose1":
            case "nose2":
            case "nose3":
            case "nose4":
                switch (Progress) {
                    case 0:
                        text.setText("正常");
                        text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        break;
                    case 1:
                        text.setText("偶爾(早晚)");
                        text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        break;
                    case 2:
                        text.setText("經常(整日)");
                        text.setTextColor(getResources().getColor(R.color.warning));
                        break;
                    default:
                        text.setText("N/A");
                        break;
                }
                break;
            case "eye":
                switch (Progress) {
                    case 0:
                        text.setText("正常");
                        text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        break;
                    case 1:
                        text.setText("揉眼");
                        text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        break;
                    case 2:
                        text.setText("眼腫畏光");
                        text.setTextColor(getResources().getColor(R.color.warning));
                        break;
                    default:
                        text.setText("N/A");
                        break;
                }
                break;
            case "skin":
                switch (Progress) {
                    case 0:
                        text.setText("正常");
                        text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        break;
                    case 1:
                        text.setText("搔癢但無紅腫");
                        text.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        break;
                    case 2:
                        text.setText("局部紅腫");
                        text.setTextColor(getResources().getColor(R.color.warning));
                        break;
                    case 3:
                        text.setText("超過2部位以上的紅疹塊");
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
