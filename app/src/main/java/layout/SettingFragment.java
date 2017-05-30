package layout;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.user.asthma.MainActivity;
import com.example.user.asthma.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {
    public String name = "";
    public int sex = 0;
    public String birth = "";
    public String height = "";
    public String weight = "";
    public boolean settingsComplete = false;


    public SettingFragment() {
        // Required empty public constructor
    }
    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private int mYear, mMonth, mDay;
    private TextView datebutton;
    Context thiscontext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        datebutton = (TextView) view.findViewById(R.id.birthE);
        thiscontext = container.getContext();

        // Show a datePicker when the dateButton is clicked
        datebutton.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(thiscontext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        datebutton.setText(year + " / " + (month+1) + " / " + day);
                    }

                }, mYear,mMonth, mDay).show();
            }

        });

        // Show default value
        SharedPreferences sp = this.getActivity().getSharedPreferences(MainActivity.settingFile, 0);
        name = sp.getString("name", name);
        sex = sp.getInt("sex", sex);
        birth = sp.getString("birth", birth);
        height = sp.getString("height", height);
        weight = sp.getString("weight", weight);

        EditText nameE = (EditText) view.findViewById(R.id.nameE);
        nameE.setText(name);
        RadioGroup rg = (RadioGroup) view.findViewById(R.id.sexCheck);
        rg.check(sex);
        datebutton.setText(birth);
        EditText heightE = (EditText) view.findViewById(R.id.heightE);
        heightE.setText(height);
        EditText weightE = (EditText) view.findViewById(R.id.weightE);
        weightE.setText(weight);

        return view;

    }

    public boolean verifySettings(){
        settingsComplete = name.equals("") && birth.equals("") && height.equals("") && weight.equals("");
        return settingsComplete;
    }

}
