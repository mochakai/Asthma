package layout;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.asthma.ChartView;
import com.example.user.asthma.R;


public class ChartFragment extends DialogFragment{

    boolean[] checkedItem = null;

    public ChartFragment() {
        // Required empty public constructor
    }

    public static ChartFragment newInstance() {
        return new ChartFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int len = getResources().getStringArray(R.array.fields).length;
        checkedItem = new boolean[len];
    }

    ChartView chartView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_noti, container, false);
        Button setBt = (Button) view.findViewById(R.id.setContent);
        setBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//button is clicked
                // Create an adapter using string resource
                //ArrayAdapter<CharSequence> ad = ArrayAdapter.createFromResource(view.getContext(), R.array.fields, android.R.layout.simple_spinner_item);
                final boolean[] tmpCheckedItem = checkedItem;
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.setContent)
                        .setMultiChoiceItems(R.array.fields, tmpCheckedItem, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                tmpCheckedItem[which] = isChecked;
                            }
                        })
                        .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                checkedItem = tmpCheckedItem;
                                refreshChart();
                            }
                        })
                        .create().show();
            }
        });

        chartView = new ChartView();

        LinearLayout chartLayout = (LinearLayout) view.findViewById(R.id.chartLayout);
        chartLayout.addView(chartView.getChartView(getContext()));
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshChart();
    }

    public void refreshChart(){
        TextView tv = (TextView) getActivity().findViewById(R.id.tmpText);
        String text ="Chart of : ";
        String[] arr = getResources().getStringArray(R.array.fields);
        for (int i=0; i< arr.length; i++){
            if (checkedItem[i]){
                text += arr[i]+" ";
            }
        }
        tv.setText(text);

    }
}
