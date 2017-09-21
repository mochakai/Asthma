package com.example.user.asthma;

import android.content.Context;
import android.view.View;

import org.achartengine.ChartFactory;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import java.util.List;
import java.util.Vector;

/**
 * Created by Donny on 2017/9/20
 */

public class ChartView {
    private XYMultipleSeriesDataset dataset;
    private XYMultipleSeriesRenderer renderer;

    public ChartView() {
        dataset = new XYMultipleSeriesDataset();
        renderer = new XYMultipleSeriesRenderer();
    }

    public View getChartView(Context context){ // chart
        return ChartFactory.getTimeChartView(context, dataset, renderer, null);
    }
    public void setLines(Context context, List<String> fields){
        for (String field: fields) {
            DataStorage.getValues(context, field);
        }
    }
}
