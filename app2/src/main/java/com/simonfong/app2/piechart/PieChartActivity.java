package com.simonfong.app2.piechart;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.simonfong.app2.R;
import com.simonfong.app2.wifiserver.BaseActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;

/**
 * Created by fengzimin  on  2020/6/8.
 * interface by
 */
public class PieChartActivity extends BaseCommonActivity {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_pie_chart;
    }


    public static float div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PieChartView pieChartView = findViewById(R.id.pie_chart_view);
        List<PieChartView.PieData> pieChart = new ArrayList<>();
        float div = div(1, 24, 5);
        pieChart.add(new PieChartView.PieData("1", div, R.color.time_no_select));
        pieChart.add(new PieChartView.PieData("2", div, R.color.time_no_select));
        pieChart.add(new PieChartView.PieData("3", div, R.color.time_no_select));
        pieChart.add(new PieChartView.PieData("4", div, R.color.time_no_select));
        pieChart.add(new PieChartView.PieData("5", div, R.color.time_no_select));
        pieChart.add(new PieChartView.PieData("6", div, R.color.time_no_select));
        pieChart.add(new PieChartView.PieData("7", div, R.color.time_no_select));
        pieChart.add(new PieChartView.PieData("8", div, R.color.time_no_select));
        pieChart.add(new PieChartView.PieData("9", div, R.color.time_no_select));
        pieChart.add(new PieChartView.PieData("10", div, R.color.time_no_select));
        pieChart.add(new PieChartView.PieData("11", div, R.color.time_no_select));
        pieChart.add(new PieChartView.PieData("12", div, R.color.time_no_select));
        pieChart.add(new PieChartView.PieData("1", div, R.color.time_no_select));
        pieChart.add(new PieChartView.PieData("2", div, R.color.time_no_select));
        pieChart.add(new PieChartView.PieData("3", div, R.color.time_no_select));
        pieChart.add(new PieChartView.PieData("4", div, R.color.time_no_select));
        pieChart.add(new PieChartView.PieData("5", div, R.color.time_no_select));
        pieChart.add(new PieChartView.PieData("6", div, R.color.time_no_select));
        pieChart.add(new PieChartView.PieData("7", div, R.color.time_no_select));
        pieChart.add(new PieChartView.PieData("8", div, R.color.time_no_select));
        pieChart.add(new PieChartView.PieData("9", div, R.color.time_no_select));
        pieChart.add(new PieChartView.PieData("10", div, R.color.time_no_select));
        pieChart.add(new PieChartView.PieData("11", div, R.color.time_no_select));
        pieChart.add(new PieChartView.PieData("12", div, R.color.time_no_select));
        pieChartView.setPieDataList(pieChart);
        pieChartView.setOnSpecialTypeClickListener(new PieChartView.OnSpecialTypeClickListener() {
            @Override
            public void onSpecialTypeClick(String type) {
                showToast(type);
            }
        });
    }
}
