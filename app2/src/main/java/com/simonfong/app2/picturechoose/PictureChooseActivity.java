package com.simonfong.app2.picturechoose;

import android.os.Bundle;
import android.support.v4.math.MathUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.library.banner.BannerLayout;
import com.simonfong.app2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;

/**
 * Created by fengzimin  on  2019/11/26.
 * interface by
 */
public class PictureChooseActivity extends BaseCommonActivity {
    @BindView(R.id.recycler)
    BannerLayout mRecycler;
    @BindView(R.id.btn_click)
    Button mBtnClick;
    private List<String> mDataList;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_picture_choose;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mDataList = new ArrayList<>();
        mDataList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574755553818&di=2557e39363e5e1e713ab44a96550cee9&imgtype=0&src=http%3A%2F%2Fpic165.nipic.com%2Ffile%2F20180517%2F26983149_173750510000_2.jpg");
        mDataList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574755553817&di=1fd075eb849a0a74cb6b4c8712c2cf6e&imgtype=0&src=http%3A%2F%2Fcy.89178.com%2Fupload%2Farticle%2F20170622%2F84957798171498119032.jpg");
        mDataList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574755553834&di=040e73d274fd49a9e5d403ae75e17460&imgtype=0&src=http%3A%2F%2Fimgsa.baidu.com%2Fexp%2Fw%3D500%2Fsign%3D8ec4724e3c87e9504217f36c2039531b%2Fb8389b504fc2d562bdf1ad60e11190ef77c66c45.jpg");
        mDataList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574755553834&di=88abb8fb85eb70a12040d065a9e18549&imgtype=0&src=http%3A%2F%2Fimg13.360buyimg.com%2Fn0%2Fjfs%2Ft2554%2F206%2F811407440%2F336170%2Fd7278d42%2F566901aaNbe493966.jpg");
        mDataList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574755553812&di=2ac6d769036098471b52afb4020d3379&imgtype=0&src=http%3A%2F%2Fimg1.juimg.com%2F170224%2F330556-1F224233P559.jpg");
        mDataList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574755553834&di=8d78300d8342af38e925e1b229b8246b&imgtype=0&src=http%3A%2F%2Fimg009.hc360.cn%2Fy4%2FM00%2F82%2F36%2FwKhQiFWNjgGEH5WQAAAAAN-C9Jk662.jpg");
        WebBannerAdapter webBannerAdapter = new WebBannerAdapter(this, mDataList);
        webBannerAdapter.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(PictureChooseActivity.this, "点击了第  " + position + "  项", Toast.LENGTH_SHORT).show();
            }
        });
        mRecycler.setAdapter(webBannerAdapter);
    }

    @OnClick({R.id.btn_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_click:
                Random random1 = new Random();
                int i = random1.nextInt(mDataList.size() - 1);
                mRecycler.scrollToPosition(i);
                break;
        }
    }
}
