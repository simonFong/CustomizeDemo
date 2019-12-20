package cn.dlc.customizedemo.myapplication.radio;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;
import cn.dlc.customizedemo.myapplication.R;

/**
 * Created by fengzimin  on  2019/04/11.
 * interface by
 */
public class RadioActivity extends BaseCommonActivity {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.rb_boy_id)
    RadioButton mRbBoyId;
    @BindView(R.id.rb_girl_id)
    RadioButton mRbGirlId;
    @BindView(R.id.rg_group)
    RadioGroup mRgGroup;
    @BindView(R.id.radio_clear)
    Button mRadioClear;
    @BindView(R.id.radio_add_child)
    Button mRadioAddChild;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_demo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.radio_clear, R.id.radio_add_child})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_clear:
                mRgGroup.clearCheck();
                break;
            case R.id.radio_add_child:
                RadioButton radioButton = new RadioButton(RadioActivity.this);
                radioButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                radioButton.setText("新增选项");
                mRgGroup.addView(radioButton);
                break;
        }
    }
}
