package cn.dlc.customizedemo.myapplication.MPAndroidChart.manager;


import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

import cn.dlc.customizedemo.myapplication.MPAndroidChart.MyMarkerView;
import cn.dlc.customizedemo.myapplication.R;

/**
 * Created by fengzimin   on  2018/5/14.
 */

public class LineChartManager implements OnChartValueSelectedListener, OnChartGestureListener {

    private LineChart mChart;
    private Context mContext;
    private XAxis mXAxis;

    public LineChartManager(LineChart chart, Context context) {
        mContext = context;
        mChart = chart;
        initLineChar();
    }

    /**
     * 设置图标的样式
     */
    public void initLineChar() {
        //手势监听
        mChart.setOnChartGestureListener(this);
        //监听数据单击事件
        mChart.setOnChartValueSelectedListener(this);
        //是否显示网格线
        mChart.setDrawGridBackground(false);

        //是否显示描述
        mChart.getDescription().setEnabled(false);

        // 是否能触摸
        mChart.setTouchEnabled(true);

        //能否拖动
        mChart.setDragEnabled(true);
        //是否缩放
        mChart.setScaleEnabled(true);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately  按比例
        mChart.setPinchZoom(true);

        // set an alternative background color
        //         mChart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        //提示器
        MyMarkerView mv = new MyMarkerView(mContext, R.layout.custom_marker_view);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart

        // x-axis limit line
        //x轴限制线
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        mXAxis = mChart.getXAxis();
        //设置网格线和样式
        mXAxis.setDrawGridLines(false);
        mXAxis.enableGridDashedLine(10f, 10f, 0f);
        //        xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
        //        xAxis.addLimitLine(llXAxis); // add x-axis limit line

        //        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        LimitLine ll1 = new LimitLine(150f, "Upper Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);
        //        ll1.setTypeface(tf);

        LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);
        //        ll2.setTypeface(tf);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.setAxisMaximum(200f);
        leftAxis.setAxisMinimum(-50f);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        //绘制0度线
        leftAxis.setDrawZeroLine(false);
        //绘制Y轴线
        leftAxis.setDrawAxisLine(false);

        // limit lines are drawn behind data (and not on top)允许控制LimitLines之间的z轴上的实际的数据顺序。如果设置为true,LimitLines在真实数据后边绘制，,
        // 否则在上面。默认false
        leftAxis.setDrawLimitLinesBehindData(true);
        //绘制右边y轴
        mChart.getAxisRight().setEnabled(false);
        //限定可以缩放倍数
        //        mChart.getViewPortHandler().setMaximumScaleY(2f);
        //        mChart.getViewPortHandler().setMaximumScaleX(2f);

        // add data
        //        setData(45, 100);

        //        mChart.setVisibleXRange(20);
        //        mChart.setVisibleYRange(20f, AxisDependency.LEFT);
        //        mChart.centerViewTo(20, 50, AxisDependency.LEFT);

        //动画时间
        mChart.animateX(2500);
        //mChart.invalidate();

        //标注
        Legend l = mChart.getLegend();
        //标注类型
        l.setForm(Legend.LegendForm.EMPTY);
        // // dont forget to refresh the drawing
        // mChart.invalidate();
    }


    public void showLineChart(int count, float range) {


        //模拟数据
        ArrayList<Entry> values = new ArrayList<Entry>();
        final String[] mDate = new String[count];
        for (int i = 0; i < count; i++) {

            float val = (float) (Math.random() * range) + 3;
            //添加数据
            values.add(new Entry(i, val, mContext.getResources().getDrawable(R.drawable.star)));
            mDate[i] = "rri" + i;
        }

        mXAxis.setLabelCount(count - 1);
        //        mXAxis.setValueFormatter(new IndexAxisValueFormatter(mDate));

        //设置横坐标值
        mXAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (mDate.length == 1) {
                    if (value == mXAxis.mEntries[1]) {
                        return "只有一个数据";
                    } else {
                        return "";
                    }
                }
                if (mDate.length == 2) {
                    if (value == mXAxis.mEntries[1]) {
                        return "";
                    }
                }
                return "123";
            }
        });

        LineDataSet set1;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            //标注内容
            set1 = new LineDataSet(values, "DataSet 1");
            //绘制数据点的图标
            set1.setDrawIcons(false);

            //连接虚线
            set1.enableDashedLine(10f, 5f, 0f);
            //            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);

            if (Utils.getSDKInt() >= 18) {
                //填充颜色
                Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            mChart.setData(data);
        }
    }


    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            mChart.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + mChart.getLowestVisibleX() + ", high: " + mChart.getHighestVisibleX());
        Log.i("MIN MAX", "xmin: " + mChart.getXChartMin() + ", xmax: " + mChart.getXChartMax() + ", ymin: " + mChart
                .getYChartMin() + ", ymax: " + mChart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }
}
