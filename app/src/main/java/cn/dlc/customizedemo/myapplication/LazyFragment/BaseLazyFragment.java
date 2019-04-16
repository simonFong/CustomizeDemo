package cn.dlc.customizedemo.myapplication.LazyFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.dlc.commonlibrary.utils.CountDownHelper;

/**
 * 可以实现懒加载的Fragment，通过实现onVisiable()和onInvisiable()方法，可以实现在Fragment显示或者隐藏时进行相应操作
 *
 * @author Administrator
 */
public abstract class BaseLazyFragment extends BaseFragment {
    private Boolean isViewInitiated = false; // 标识fragment视图已经初始化完毕
    protected Boolean isDataInitiated = false; // 标识已经触发过懒加载数据
    protected CountDownHelper countDownHelper;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        prepareFetchData();
    }

    public void prepareFetchData() {
        if (getUserVisibleHint() && isViewInitiated && !isDataInitiated) {
            isDataInitiated = true;
            lazyFetchData();
        }
    }

    protected void lazyFetchData() {

    }
}
