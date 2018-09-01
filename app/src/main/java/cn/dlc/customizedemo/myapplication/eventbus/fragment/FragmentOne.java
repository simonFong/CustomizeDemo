package cn.dlc.customizedemo.myapplication.eventbus.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.dlc.commonlibrary.ui.base.BaseCommonFragment;
import cn.dlc.commonlibrary.utils.ToastUtil;
import cn.dlc.customizedemo.myapplication.R;
import cn.dlc.customizedemo.myapplication.eventbus.bean.ComEventBean;

/**
 * Created by fengzimin  on  2018/07/25.
 * interface by
 */
public class FragmentOne extends BaseCommonFragment {

    @BindView(R.id.btn_1)
    Button mBtn1;
    @BindView(R.id.btn_2)
    Button mBtn2;
    @BindView(R.id.btn_3)
    Button mBtn3;
    @BindView(R.id.receiver1)
    TextView mReceiver1;
    @BindView(R.id.receiver2)
    TextView mReceiver2;
    @BindView(R.id.receiver3)
    TextView mReceiver3;
    Unbinder unbinder;
    private int mType;


    public static FragmentOne newInstance(int type) {
        FragmentOne f = new FragmentOne();
        Bundle args = new Bundle();
        args.putInt("type", type);
        f.setArguments(args);
        return f;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_eventbus_one;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mType = arguments.getInt("type");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 注册订阅者
        EventBus.getDefault().register(this);
        initView();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initView() {
        mBtn1.setVisibility(mType == 1 ? View.VISIBLE : View.GONE);
        mBtn2.setVisibility(mType == 1 ? View.VISIBLE : View.GONE);
        mBtn3.setVisibility(mType == 2 ? View.VISIBLE : View.GONE);
        mReceiver1.setVisibility(mType == 1 ? View.VISIBLE : View.GONE);
        mReceiver2.setVisibility(mType == 1 ? View.VISIBLE : View.GONE);
        mReceiver3.setVisibility(mType == 2 ? View.VISIBLE : View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        // 注销订阅者
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.btn_1, R.id.btn_2, R.id.btn_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                EventBus.getDefault().post(new ComEventBean("fragment1", "fragment1"));
                break;
            case R.id.btn_2:
                EventBus.getDefault().post(new ComEventBean("fragment1", "fragment1"));
                break;
            case R.id.btn_3:
                EventBus.getDefault().post(new ComEventBean("fragment2", "fragment2"));
                break;
        }
    }

    /**
     * 接收方法只能有一个参数，订阅者是根据发送的参数类型来判断接收的，发送的方法只能带一个参数
     *
     * @param comEventBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventbusReceive(ComEventBean comEventBean) {

        switch (mType) {
            case 1:
                if (comEventBean.getFrom().equals("fragment2")) {
                    ToastUtil.show(getActivity(), comEventBean.getData().toString());
                    mReceiver1.setText(comEventBean.getData().toString());
                    mReceiver2.setText(comEventBean.getData().toString());
                }
                break;
            case 2:
                if (comEventBean.getFrom().equals("fragment1")) {
                    ToastUtil.show(getActivity(), comEventBean.getData().toString());
                    mReceiver3.setText(comEventBean.getData().toString());
                }
                break;
        }
    }


}
