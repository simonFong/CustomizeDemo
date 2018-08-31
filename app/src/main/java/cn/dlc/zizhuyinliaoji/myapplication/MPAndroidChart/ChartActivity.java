package cn.dlc.zizhuyinliaoji.myapplication.MPAndroidChart;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;
import cn.dlc.zizhuyinliaoji.myapplication.MPAndroidChart.manager.BarChartManager;
import cn.dlc.zizhuyinliaoji.myapplication.MPAndroidChart.manager.LineChartManager;
import cn.dlc.zizhuyinliaoji.myapplication.R;

/**
 * Created by fengzimin  on  2018/08/31.
 * interface by
 */
public class ChartActivity extends BaseCommonActivity {
    @BindView(R.id.lineChart)
    LineChart mLineChart;
    @BindView(R.id.barChart)
    BarChart mBarChart;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_chart;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initLineChart();
        initBarChart();
    }

    private void initBarChart() {
        BarChartManager barChartManager = new BarChartManager(mBarChart, this);
        barChartManager.showBarChart(4,5);
    }

    private void initLineChart() {
        LineChartManager lineChartManager = new LineChartManager(mLineChart, this);
        lineChartManager.showLineChart(45, 100);
    }
}
