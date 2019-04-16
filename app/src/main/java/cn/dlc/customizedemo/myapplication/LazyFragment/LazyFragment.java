package cn.dlc.customizedemo.myapplication.LazyFragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import cn.dlc.commonlibrary.ui.widget.TitleBar;
import cn.dlc.customizedemo.myapplication.R;

/**
 * Created by fengzimin  on  2019/03/31.
 * interface by
 */
public class LazyFragment extends BaseLazyFragment {


    @BindView(R.id.titlebar)
    TitleBar mTitlebar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lazy;
    }

    public static LazyFragment newInstance() {
        Bundle args = new Bundle();
        LazyFragment fragment = new LazyFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
