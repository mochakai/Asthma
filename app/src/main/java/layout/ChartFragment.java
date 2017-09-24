package layout;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.asthma.ChartView;
import com.example.user.asthma.R;

import java.util.ArrayList;

public class ChartFragment extends DialogFragment{

    boolean[] checkedItem = null;

    public ChartFragment() {
        // Required empty public constructor
    }

    public static ChartFragment newInstance() {
        return new ChartFragment();
    }

    ChartView chartView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int len = getResources().getStringArray(R.array.fields).length;
        checkedItem = new boolean[len];
        chartView = new ChartView();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_noti, container, false);
        Button setBt = (Button) view.findViewById(R.id.setContent);
        setBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//button is clicked
                // Create an adapter using string resource
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

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshChart();
    }

    public void refreshChart(){
        TextView tv = (TextView) getActivity().findViewById(R.id.tmpText);
        String text ="Chart of : ";
        String[] arr = getResources().getStringArray(R.array.fields);
        ArrayList<String> tar = new ArrayList<>();
        for (int i=0; i< arr.length; i++){
            if (checkedItem[i]){
                text += arr[i]+" ";
                tar.add(arr[i]);
            }
        }
        Log.d("Refresh text", text);
        tv.setText(text);

        chartView.setLines(getContext(), tar.toArray(new String[tar.size()]));
        LinearLayout chartLayout = (LinearLayout) getActivity().findViewById(R.id.chartLayout);
        chartLayout.removeAllViews();
        chartLayout.addView(chartView.getChartView(getContext()));
    }
}
