package cn.dlc.customizedemo.myapplication.leakcanary;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;
import cn.dlc.customizedemo.myapplication.R;

/**
 * Created by fengzimin  on  2018/09/01.
 * interface by
 */
public class LeakcanaryActivity extends BaseCommonActivity {
    @BindView(R.id.tv_test)
    TextView mTvTest;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_leakcanary;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        XXXHelper.getInstance(this).setRetainedTextView(mTvTest);
    }
}
