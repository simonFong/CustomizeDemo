package cn.dlc.customizedemo.myapplication.map.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dlc.commonlibrary.utils.DialogUtil;
import cn.dlc.customizedemo.myapplication.R;

/**
 * Created by lixukang   on  2018/5/9.
 */

public class MapNavigationDialog extends Dialog {
    @BindView(R.id.baiduditu_tv)
    TextView mBaidudituTv;
    @BindView(R.id.gaode_tv)
    TextView mGaodeTv;
    @BindView(R.id.quxiao_Tv)
    TextView mQuxiaoTv;
    private  MapCallBack mMapCallBack;

    public MapNavigationDialog(@NonNull Context context) {
        super(context, R.style.CommonDialogStyle);
        DialogUtil.adjustDialogLayout(this, true, false);
        DialogUtil.setGravity(this, Gravity.BOTTOM);
        setContentView(R.layout.dialog_map_navigation);
        ButterKnife.bind(this);
    }

    @OnClick({ R.id.baiduditu_tv, R.id.gaode_tv, R.id.quxiao_Tv })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.baiduditu_tv:
                mMapCallBack.baidu();
                dismiss();
                break;
            case R.id.gaode_tv:
                mMapCallBack.gaode();
                dismiss();
                break;
            case R.id.quxiao_Tv:
                dismiss();
                break;
        }
    }
    public  void setOnMapCallBack(MapCallBack mapCallBack){
        mMapCallBack  = mapCallBack;
    }
    public  interface  MapCallBack{
        void baidu();
        void gaode();
    }
}
