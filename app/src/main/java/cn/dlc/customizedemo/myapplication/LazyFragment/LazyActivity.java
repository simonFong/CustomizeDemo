package cn.dlc.customizedemo.myapplication.LazyFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;

import butterknife.BindView;
import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;
import cn.dlc.commonlibrary.ui.widget.TitleBar;
import cn.dlc.commonlibrary.utils.ResUtil;
import cn.dlc.customizedemo.myapplication.R;

/**
 * Created by fengzimin  on  2019/03/31.
 * interface by
 */
public class LazyActivity extends BaseCommonActivity {


    @BindView(R.id.titlebar)
    TitleBar mTitlebar;
    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_lazy;
    }

    private void initViewPager() {
        String[] stringArray = ResUtil.getResources().getStringArray(R
                .array.work_order_manager);
        Fragment[] fragments = new Fragment[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            fragments[i] = LazyFragment.newInstance();
        }

        //取消预加载
        mViewPager.setAdapter(new TabsAdapter(getSupportFragmentManager(), fragments, stringArray));
        //        mViewPager.setAdapter(new TabsAdapter(getSupportFragmentManager()) {
        //            @Override
        //            public Fragment getItem(int position) {
        //                //根据后台给的数据也可以这边传过去  现在我传了一个type  体侧的界面没有出来  这个产品还在做着
        //                WorkOrderManagerFragment fragment = WorkOrderManagerFragment.newInstance(1);
        //                return fragment;
        //            }
        //
        //            @Override
        //            public int getCount() {
        //                return mTabs.size();
        //            }
        //
        //            @Override
        //            public CharSequence getPageTitle(int position) {
        //                return mTabs.get(position);
        //            }
        //        });
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewPager();

    }
}
