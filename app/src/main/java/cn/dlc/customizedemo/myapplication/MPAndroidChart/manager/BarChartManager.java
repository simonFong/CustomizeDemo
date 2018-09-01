package cn.dlc.customizedemo.myapplication.MPAndroidChart.manager;


import android.content.Context;
import android.graphics.RectF;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

import cn.dlc.commonlibrary.utils.ResUtil;
import cn.dlc.customizedemo.myapplication.MPAndroidChart.MyMarkerView;
import cn.dlc.customizedemo.myapplication.R;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

/**
 * Created by fengzimin   on  2018/5/14.
 */

public class BarChartManager implements OnChartValueSelectedListener {

    private BarChart mChart;
    private Context mContext;
    private XAxis mXAxis;
    private YAxis mLeftAxis;

    public BarChartManager(BarChart chart, Context context) {
        mContext = context;
        mChart = chart;
        initLineChar();
    }

    /**
     * 设置图表的样式
     */
    public void initLineChar() {
        mChart.setDrawBarShadow(false);
        //设置柱状图Value值显示在柱状图上方 true 为显示上方，默认false value值显示在柱状图里面
        mChart.setDrawValueAboveBar(true);
        //设置描述
        mChart.getDescription().setEnabled(false);
        mChart.setTouchEnabled(true);

        MyMarkerView mv = new MyMarkerView(mContext, R.layout.custom_marker_view);
        //        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv);

        // scaling can now only be done on x- and y-axis separately
        //按比例
        mChart.setPinchZoom(false);
        //是否能拖动
        mChart.setDragEnabled(false);
        //是否能缩放
        mChart.setScaleEnabled(false);
        //是否绘制网格背景
        mChart.setDrawGridBackground(false);
        mChart.setOnChartValueSelectedListener(this);

        mXAxis = mChart.getXAxis();
        //设置x轴文本显示位置
        mXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //绘制x轴网格线
        mXAxis.setDrawGridLines(false);
        //缩放的时候有用,比如放大的时候,我不想把横轴的月份再细分
        mXAxis.setGranularity(1f); // only intervals of 1 day
        //强制有多少个刻度
        mXAxis.setLabelCount(7);
        //设置问题的旋转角度
        mXAxis.setLabelRotationAngle(0);
        //        mXAxis.setAxisLineColor(ResUtil.getColor(R.color.color_333));
        //        mXAxis.setTextColor(ResUtil.getColor(R.color.color_333));

        mLeftAxis = mChart.getAxisLeft();
        mLeftAxis.setDrawLabels(true);
        mLeftAxis.setDrawGridLines(true);
        mLeftAxis.setDrawAxisLine(false);
        mLeftAxis.setLabelCount(6, false);
        mLeftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        //        mLeftAxis.setAxisLineColor(ResUtil.getColor(R.color.color_999));
        //        mLeftAxis.setTextColor(ResUtil.getColor(R.color.color_999));
        mLeftAxis.setSpaceTop(15f);
        mLeftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        mLeftAxis.setAxisMaximum(2000f);
        mLeftAxis.setDrawZeroLine(false);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
        mChart.getLegend().setEnabled(false);

        mChart.animateXY(2000, 2000);
    }

    public static final int[] MATERIAL_COLORS = {
            rgb("#54D6B0"), rgb("#BED3FE"), rgb("#F0C33B"), rgb("#F8964E")
    };

    /**
     * 设置数据
     *
     * @param count
     * @param range
     */
    public void showBarChart(int count, float range) {
        //模拟数据
        final String[] mDate = new String[4];
        mDate[0] = "凌晨";
        mDate[1] = "6:00";
        mDate[2] = "中午";
        mDate[3] = "18:00";

        Float[] mScore = new Float[4];
        mScore[0] = 1400f;
        mScore[1] = 1000f;
        mScore[2] = 200f;
        mScore[3] = 300f;

        //设置横坐标值
        mXAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (mDate.length == 0) {
                    return "";
                }
                if (value < 0 || value > mDate.length) {
                    return "";
                }
                return mDate[(int) value % mDate.length];
            }
        });
        //        mLeftAxis.setValueFormatter(new IAxisValueFormatter() {
        //            @Override
        //            public String getFormattedValue(float value, AxisBase axis) {
        //                return null;
        //            }
        //        });

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        for (int i = 0; i < mScore.length; i++) {
            yVals1.add(new BarEntry(i, mScore[i]));
        }


        BarDataSet set1;
        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
            mChart.invalidate();
        } else {

            set1 = new BarDataSet(yVals1, "");
            set1.setDrawIcons(false);
            //设置柱的颜色
            set1.setColors(MATERIAL_COLORS);
            //设置高亮选中透明值
            set1.setHighLightAlpha(50);
            //绘制显示数据
            set1.setDrawValues(true);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(mContext.getResources().getDimensionPixelOffset(R.dimen.normal_7sp));
            data.setValueTextColor(ResUtil.getColor(R.color.colorAccent));
            data.setBarWidth(0.5f);

            mChart.setData(data);
            mChart.invalidate();
        }

    }

    protected RectF mOnValueSelectedRectF = new RectF();

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;

        RectF bounds = mOnValueSelectedRectF;
        mChart.getBarBounds((BarEntry) e, bounds);
        MPPointF position = mChart.getPosition(e, YAxis.AxisDependency.LEFT);

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        Log.i("x-index",
                "low: " + mChart.getLowestVisibleX() + ", high: " + mChart.getHighestVisibleX());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {

    }


}