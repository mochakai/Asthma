package com.example.user.asthma;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;


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
        return ChartFactory.getTimeChartView(context, dataset, renderer, "yyyy-MM-dd");
    }
    public void setLines(Context context, String... fields){
        //chartView.repaint();
        dataset = DataStorage.getValues(context, fields);
        renderer = setRenderer(context, fields);
        for (int i=0; i<fields.length; i++){
            renderer.addYTextLabel(i+1, fields[i]);
            renderer.setYLabelsVerticalPadding(40);
        }
    }
    private XYMultipleSeriesRenderer setRenderer(Context context, String... fields){
        XYMultipleSeriesRenderer ret = new XYMultipleSeriesRenderer();
        for (String field: fields){
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(getColor(field));
            r.setPointStyle(PointStyle.CIRCLE);
            r.setLineWidth(5);
            r.setFillPoints(true);
            ret.addSeriesRenderer(r);
        }
        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(Color.BLACK);
        r.setPointStyle(PointStyle.CIRCLE);
        r.setLineWidth(5);
        r.setFillPoints(true);
        ret.addSeriesRenderer(r);


        ret.setChartTitle(context.getResources().getString(R.string.chartTitle));
        ret.setChartTitleTextSize(60);
        ret.setLegendTextSize(60);
        ret.setPointSize(15);
        ret.setApplyBackgroundColor(true);
        ret.setBackgroundColor(context.getResources().getColor(R.color.chart_background));
        ret.setMarginsColor(context.getResources().getColor(R.color.chart_background));
        ret.setPanEnabled(true, false);
        ret.setZoomEnabled(true, false); // x enable, y disable
        ret.setFitLegend(true);
        ret.setXTitle("Date");
        ret.setShowGrid(true);
        ret.setLabelsTextSize(30);
        ret.setXLabelsColor(Color.BLACK);
        ret.setXLabelsPadding(10);
        ret.setGridColor(Color.GREEN);

        return ret;
    }

    private int getColor(String field){
        return Color.BLACK;
    }

}
