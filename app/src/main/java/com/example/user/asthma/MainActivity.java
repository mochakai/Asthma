package com.example.user.asthma;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import layout.HomeFragment;
import layout.NotiFragment;
import layout.SettingFragment;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homepage;
    private NotiFragment notipage;
    private SettingFragment setpage;
    boolean logon = false;
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
                    trans.replace(R.id.fragment_container, notipage).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == FUNC_LOGIN){
            if(resultCode == RESULT_OK){
                String uid = data.getStringExtra("LOGIN_USERID");
                String pw = data.getStringExtra("LOGIN_PASSWD");
                Log.d("RESULT", uid + " / " + pw);
                Snackbar.make(findViewById(R.id.message), "welcome "+uid, Snackbar.LENGTH_LONG).show();
            }
            else{
                finish();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(1).setChecked(true);

        homepage = HomeFragment.newInstance();
        notipage = NotiFragment.newInstance("a", "b");
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

        // change fragment
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.fragment_container, homepage).commit();
        Snackbar.make(findViewById(R.id.message), "profile saved", Snackbar.LENGTH_LONG).show();
    }
}
