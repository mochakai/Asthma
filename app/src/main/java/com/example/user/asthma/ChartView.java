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
        int count = 1;
        for (String field: fields){
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(getColor(context, field, count++));
            r.setPointStyle(PointStyle.CIRCLE);
            r.setLineWidth(5);
            r.setFillPoints(true);
            ret.addSeriesRenderer(r);
        }
        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(context.getResources().getColor(R.color.chart_colors0));
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
        ret.setMargins(new int[] {20, 20, 60, 20});
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

    private int getColor(Context context, String field, int count){
        switch (count){
            case 1:
                return context.getResources().getColor(R.color.chart_colors1);
            case 2:
                return context.getResources().getColor(R.color.chart_colors2);
            case 3:
                return context.getResources().getColor(R.color.chart_colors3);
            case 4:
                return context.getResources().getColor(R.color.chart_colors4);
            case 5:
                return context.getResources().getColor(R.color.chart_colors5);
            case 6:
                return context.getResources().getColor(R.color.chart_colors6);
            case 7:
                return context.getResources().getColor(R.color.chart_colors7);
            case 8:
                return context.getResources().getColor(R.color.chart_colors8);
            case 9:
                return context.getResources().getColor(R.color.chart_colors9);
            case 10:
                return context.getResources().getColor(R.color.chart_colors10);
            case 11:
                return context.getResources().getColor(R.color.chart_colors11);
            case 12:
                return context.getResources().getColor(R.color.chart_colors12);
            case 13:
                return context.getResources().getColor(R.color.chart_colors13);
            case 14:
                return context.getResources().getColor(R.color.chart_colors14);
            case 15:
                return context.getResources().getColor(R.color.chart_colors15);
            case 16:
                return context.getResources().getColor(R.color.chart_colors16);
            case 17:
                return context.getResources().getColor(R.color.chart_colors17);
            case 18:
                return context.getResources().getColor(R.color.chart_colors18);
            case 19:
                return context.getResources().getColor(R.color.chart_colors19);
            case 20:
                return context.getResources().getColor(R.color.chart_colors20);
            case 21:
                return context.getResources().getColor(R.color.chart_colors21);
            case 22:
                return context.getResources().getColor(R.color.chart_colors22);
            case 23:
                return context.getResources().getColor(R.color.chart_colors23);
            case 24:
                return context.getResources().getColor(R.color.chart_colors24);
            case 25:
                return context.getResources().getColor(R.color.chart_colors25);
            case 26:
                return context.getResources().getColor(R.color.chart_colors26);
            case 27:
                return context.getResources().getColor(R.color.chart_colors27);
            case 28:
                return context.getResources().getColor(R.color.chart_colors28);
            case 29:
                return context.getResources().getColor(R.color.chart_colors29);
            case 30:
                return context.getResources().getColor(R.color.chart_colors30);
            case 31:
                return context.getResources().getColor(R.color.chart_colors31);
            case 32:
                return context.getResources().getColor(R.color.chart_colors32);
            case 33:
                return context.getResources().getColor(R.color.chart_colors33);
            case 34:
                return context.getResources().getColor(R.color.chart_colors34);
            case 35:
                return context.getResources().getColor(R.color.chart_colors35);
            case 36:
                return context.getResources().getColor(R.color.chart_colors36);
            case 37:
                return context.getResources().getColor(R.color.chart_colors37);
            case 38:
                return context.getResources().getColor(R.color.chart_colors38);
            case 39:
                return context.getResources().getColor(R.color.chart_colors39);
            case 40:
                return context.getResources().getColor(R.color.chart_colors40);
            case 41:
                return context.getResources().getColor(R.color.chart_colors41);
            case 42:
                return context.getResources().getColor(R.color.chart_colors42);
            case 43:
                return context.getResources().getColor(R.color.chart_colors43);
            default:
                return Color.BLACK;
        }
    }

}
