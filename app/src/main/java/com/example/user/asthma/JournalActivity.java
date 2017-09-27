package com.example.user.asthma;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class JournalActivity extends AppCompatActivity implements ServerConnection.ServerResponse{

    private ArrayList<SeekBar> Bars = new ArrayList<>();

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
        df.sleep = Bars.get(0).getProgress();
        df.daytime = Bars.get(1).getProgress();
        df.nose1 = Bars.get(2).getProgress();
        df.nose2 = Bars.get(3).getProgress();
        df.nose3 = Bars.get(4).getProgress();
        df.nose4 = Bars.get(5).getProgress();
        df.eye = Bars.get(6).getProgress();
        df.skin = Bars.get(7).getProgress();

        //send to server
        askServer.execute(df.toJSON());
        //insert to database
        if (!DataStorage.insert(this, df)) Log.w("database insert", "failed");
        ((Button)findViewById(R.id.saveJournal)).setText(R.string.waitServerMsg);
    }

    @Override
    public void onServerResponse(String result) {
        String predictMsg = "", prescription = "";
        boolean suc = false;
        try{
            JSONObject tmp = new JSONObject(result);
            if (suc = tmp.getBoolean("success")) {
                JSONObject json = new JSONObject(tmp.getString("result"));
                predictMsg = json.getString("predict");
                prescription = json.getString("prescription");
            }
            Toast.makeText(this, tmp.getString("msg"), Toast.LENGTH_LONG).show();
        }catch (JSONException e){
            e.printStackTrace();
        }

        Log.d("predict_msg", predictMsg);
        Log.d("prescription", prescription);
        //show dialog of response

        if (!suc) predictMsg = "Upload not success";

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
        SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarReact(seekBar);
            }
        };

        int[] ids = {R.id.sleepBar, R.id.daytimeBar, R.id.nose1Bar, R.id.nose2Bar,
                     R.id.nose3Bar, R.id.nose4Bar, R.id.eyeBar, R.id.skinBar};

        for (int id: ids){
            SeekBar seekBar = (SeekBar) findViewById(id);
            Bars.add(seekBar);
            seekBarReact(seekBar);
            seekBar.setOnSeekBarChangeListener(listener);
        }
    }

    public void seekBarReact(SeekBar seekBar){
        TextView text;
        switch (seekBar.getId()) {
            case R.id.sleepBar:
                text = (TextView) findViewById(R.id.sleepText);
                switch (seekBar.getProgress()) {
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
            case R.id.daytimeBar:
                text = (TextView) findViewById(R.id.daytimeText);
                switch (seekBar.getProgress()) {
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
            case R.id.nose1Bar:
            case R.id.nose2Bar:
            case R.id.nose3Bar:
            case R.id.nose4Bar:
                switch (seekBar.getId()){
                    case R.id.nose1Bar:
                        text = (TextView) findViewById(R.id.nose1Text);
                        break;
                    case R.id.nose2Bar:
                        text = (TextView) findViewById(R.id.nose2Text);
                        break;
                    case R.id.nose3Bar:
                        text = (TextView) findViewById(R.id.nose3Text);
                        break;
                    case R.id.nose4Bar:
                        text = (TextView) findViewById(R.id.nose4Text);
                        break;
                    default:
                        text = (TextView) findViewById(R.id.nose1Text);
                }
                switch (seekBar.getProgress()) {
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
            case R.id.eyeBar:
                text = (TextView) findViewById(R.id.eyeText);
                switch (seekBar.getProgress()) {
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
            case R.id.skinBar:
                text = (TextView) findViewById(R.id.skinText);
                switch (seekBar.getProgress()) {
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
