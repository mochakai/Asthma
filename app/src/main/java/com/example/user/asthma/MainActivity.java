package com.example.user.asthma;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import layout.HomeFragment;
import layout.ChartFragment;
import layout.SettingFragment;

public class MainActivity extends AppCompatActivity implements ServerConnection.ServerResponse{

    private HomeFragment homepage;
    private ChartFragment chpage;
    private SettingFragment setpage;
    public static final int FUNC_LOGIN = 1;
    public static final String settingFile = "Settings";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    trans.replace(R.id.fragment_container, homepage).commit();
                    return true;
                case R.id.navigation_setting:
                    trans.replace(R.id.fragment_container, setpage).commit();
                    return true;
                case R.id.navigation_notifications:
                    trans.replace(R.id.fragment_container, chpage).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == FUNC_LOGIN){
//            if(resultCode == RESULT_CANCELED){
//                Toast.makeText(this, "Please login!", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, FUNC_LOGIN);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(1).setChecked(true);

        homepage = HomeFragment.newInstance();
        chpage = ChartFragment.newInstance();
        setpage = SettingFragment.newInstance();
        if(!setpage.verifySettings()){
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.add(R.id.fragment_container, setpage).commit();
        }
        else {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.add(R.id.fragment_container, homepage).commit();
        }
    }

    public void editSave(View v) {
        // get settings
        String name = ((EditText)findViewById(R.id.nameE)).getText().toString();
        int sex = ((RadioGroup) findViewById(R.id.sexCheck)).getCheckedRadioButtonId();
        String Date = ((TextView) findViewById(R.id.birthE)).getText().toString();
        String height = ((EditText) findViewById(R.id.heightE)).getText().toString();
        String weight = ((EditText) findViewById(R.id.weightE)).getText().toString();
        // save data to internal storage
        SharedPreferences settings = getSharedPreferences(settingFile, 0);
        SharedPreferences.Editor stEditor = settings.edit();
        stEditor.putString("name", name);
        stEditor.putInt("sex", sex);
        stEditor.putString("birth", Date);
        stEditor.putString("height", height);
        stEditor.putString("weight", weight);
        stEditor.apply();
        // update settings
        setpage.name = name;
        setpage.sex = sex;
        setpage.birth = Date;
        setpage.height = height;
        setpage.weight = weight;
        setpage.verifySettings();

        // send to server
        ServerConnection askServer = new ServerConnection("setting/" + ServerConnection.uid, this);
        JSONObject json = new JSONObject();
        try {
            json.accumulate("name", name).accumulate("sex", (sex==R.id.radioButton)).accumulate("age", getAge(Date))
                    .accumulate("height", height).accumulate("weight", weight);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        askServer.execute(json);

        // change fragment
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.fragment_container, homepage).commit();
        Snackbar.make(findViewById(R.id.message), "profile saved", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onServerResponse(String result) {
        String msg = "";
        boolean suc = false;
        try {
            JSONObject json = new JSONObject(result);
            suc = json.getBoolean("success");
            msg = json.getString("msg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void OpenJournal(View v){
        Intent intent = new Intent(this, JournalActivity.class);
        startActivity(intent);
    }
    private int getAge(String birth){
        Date birthdate = new Date();
        try {
            birthdate = new SimpleDateFormat("yyyy/MM/dd").parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(birthdate);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        return age;
    }
}
