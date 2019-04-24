package cn.dlc.customizedemo.myapplication;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Button;

import com.youth.banner.Banner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;

/**
 * Created by fengzimin  on  2019/04/17.
 * interface by
 */
public class LinkedListActivity extends BaseCommonActivity {
    @BindView(R.id.btn)
    Button mBtn;
    @BindView(R.id.banner)
    Banner mBanner;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_linked_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBanner.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<String> images = new ArrayList<>();
        images.add("http://img4.imgtn.bdimg.com/it/u=2224320184,4176140572&fm=26&gp=0.jpg");
        images.add("http://img4.imgtn.bdimg.com/it/u=1736123906,3216972414&fm=26&gp=0.jpg");
        images.add("http://img1.imgtn.bdimg.com/it/u=1741553089,922349130&fm=26&gp=0.jpg");

        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setDelayTime(5000);
        //设置图片集合
        mBanner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
        mBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    mBanner.stopAutoPlay();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    private static final String TAG = "LinkedListActivity";

    @OnClick(R.id.btn)
    public void onViewClicked() {
        mBanner.stopAutoPlay();
        int abcabcbb = lengtheofkjldkjf(" ");
        Log.e(TAG, "num:" + abcabcbb);
    }

    public int lengtheofkjldkjf(String s) {
        //        "abcabcbb"
        int maxNum = 0;
        char[] chars = s.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            for (int j = i + 1; j <= chars.length; j++) {

            }
        }
        return maxNum;
    }

}
