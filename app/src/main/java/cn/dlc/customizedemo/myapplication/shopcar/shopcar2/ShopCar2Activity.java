package cn.dlc.customizedemo.myapplication.shopcar.shopcar2;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;
import cn.dlc.commonlibrary.ui.widget.EmptyView;
import cn.dlc.commonlibrary.ui.widget.TitleBar;
import cn.dlc.commonlibrary.utils.rv_tool.EmptyRecyclerView;
import cn.dlc.customizedemo.myapplication.R;
import cn.dlc.customizedemo.myapplication.shopcar.ShopCarGoodsAdapter;
import cn.dlc.customizedemo.myapplication.shopcar.ShopCarGoodsBean;
import cn.dlc.customizedemo.myapplication.shopcar.ShopCarGoodsItemBean;
import cn.dlc.customizedemo.myapplication.shopcar.SpacesItemDecoration;

import static cn.dlc.customizedemo.myapplication.shopcar.shopcar1.ShopCarActivity.defaultImg;

public class ShopCar2Activity extends BaseCommonActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.EmptyView)
    cn.dlc.commonlibrary.ui.widget.EmptyView mEmptyView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout mRefreshLayout;
    @BindView(R.id.iv_select)
    ImageView mIvSelect;
    @BindView(R.id.ll_all_select)
    LinearLayout llAllSelect;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_delete)
    TextView mTvDelete;
    @BindView(R.id.tv_settlement)
    TextView mTvSettlement;
    private ShopCar2GoodsAdapter mAdapter;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initRecycler();
    }

    private void initRecycler() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(20));
        mRecyclerView.setNestedScrollingEnabled(false);
        List<GoodsBean> objects = new ArrayList<>();
        mAdapter = new ShopCar2GoodsAdapter(getActivity(), objects);
//        mAdapter.setOnShopCarGoodsAdapterListener(new ShopCarGoodsAdapter.OnShopCarGoodsAdapterListener() {
//            @Override
//            public void setAllSelect(boolean allSelectState, String sumPrice, int sumNum) {
//                mIvSelect.setSelected(allSelectState);
//                mTvPrice.setText("￥" + sumPrice);
//            }
//        });
        mRecyclerView.setAdapter(mAdapter);
        EmptyRecyclerView.bind(mRecyclerView, mEmptyView);
        initRefresh();
    }

    private void initRefresh() {
        ProgressLayout mProgressLayout = new ProgressLayout(getActivity());
        mProgressLayout.setColorSchemeResources(R.color.color_1d1d25);
        mRefreshLayout.setHeaderView(mProgressLayout);
        mRefreshLayout.setFloatRefresh(true);
        mRefreshLayout.setEnableOverScroll(false);
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                getData(true);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                getData(false);
            }
        });
        mRefreshLayout.startRefresh();
    }


    private void getData(boolean isPullDown) {
        page = isPullDown == true ? 1 : ++page;
        if (isPullDown) {//刷新
            initData();
        } else {//加载更多
            initData();
        }
    }


    private void initData() {
        ArrayList<GoodsBean> mData = new ArrayList<GoodsBean>();
        for (int i = 0; i < 3; i++) {
            GoodsBean simpleFakeBean = new GoodsBean();
            simpleFakeBean.itemType = GoodsBean.SHOP_DETAIL;
            simpleFakeBean.shopName = ("猪猪侠");
            simpleFakeBean.rongId = ("2");

            mData.add(simpleFakeBean);
            for (int j = 0; j < 3 + 1; j++) {
                GoodsBean shopCarGoodsItemBean = new GoodsBean();
                shopCarGoodsItemBean.itemType = GoodsBean.GOOD_DETAIL;
                shopCarGoodsItemBean.price = "13.00";
                shopCarGoodsItemBean.img = defaultImg;
                shopCarGoodsItemBean.num = j + 1;
                shopCarGoodsItemBean.title = "牛肉粉" + j;
                shopCarGoodsItemBean.type = "口味：不辣";

                mData.add(shopCarGoodsItemBean);
            }

        }


        if (page == 1) {
            mAdapter.setNewData(mData);
            mRefreshLayout.finishRefreshing();
        } else {
            mAdapter.appendData(mData);
            mRefreshLayout.finishLoadmore();
        }
    }


    @Override
    protected int getLayoutID() {
        return R.layout.activity_shop_car_2;
    }

    @OnClick({R.id.ll_all_select, R.id.tv_delete, R.id.tv_settlement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_all_select:
                showToast("全选");
                break;
            case R.id.tv_delete:
                showToast("删除");
                break;
            case R.id.tv_settlement:
                showToast("结算");
                break;
        }
    }


}
