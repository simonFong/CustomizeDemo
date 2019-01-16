package cn.dlc.customizedemo.myapplication.shopcar;

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

import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;
import cn.dlc.commonlibrary.ui.widget.EmptyView;
import cn.dlc.commonlibrary.ui.widget.TitleBar;
import cn.dlc.commonlibrary.utils.rv_tool.EmptyRecyclerView;
import cn.dlc.customizedemo.myapplication.R;

/**
 * Created by fengzimin  on  2019/01/16.
 * interface by
 */
public class ShopCarActivity extends BaseCommonActivity {
    @BindView(R.id.titlebar)
    TitleBar mTitlebar;
    @BindView(R.id.EmptyView)
    EmptyView mEmptyView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout mRefreshLayout;
    @BindView(R.id.iv_select)
    ImageView mIvSelect;
    @BindView(R.id.ll_all_select)
    LinearLayout mLlAllSelect;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_delete)
    TextView mTvDelete;
    @BindView(R.id.tv_settlement)
    TextView mTvSettlement;
    private int page;
    private ShopCarGoodsAdapter mAdapter;
    public static String defaultImg = "http://img2.imgtn.bdimg.com/it/u=3635552077,3551223167&fm=26&gp=0.jpg";

    @Override
    protected int getLayoutID() {
        return R.layout.activity_shop_car;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRecycler();
    }


    private void initRecycler() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(20));
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new ShopCarGoodsAdapter(getActivity());
        mAdapter.setOnShopCarGoodsAdapterListener(new ShopCarGoodsAdapter.OnShopCarGoodsAdapterListener() {
            @Override
            public void setAllSelect(boolean allSelectState, String sumPrice, int sumNum) {
                mIvSelect.setSelected(allSelectState);
                mTvPrice.setText("￥" + sumPrice);
            }
        });
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
        ArrayList<ShopCarGoodsBean> mData = new ArrayList<ShopCarGoodsBean>();
        for (int i = 0; i < 3; i++) {
            ShopCarGoodsBean simpleFakeBean = new ShopCarGoodsBean();
            simpleFakeBean.shopName = ("猪猪侠");
            simpleFakeBean.rongId = ("2");
            ArrayList<ShopCarGoodsItemBean> shopCarGoodsItemBeans = new ArrayList<>();
            for (int j = 0; j < i + 1; j++) {
                ShopCarGoodsItemBean shopCarGoodsItemBean = new ShopCarGoodsItemBean();
                shopCarGoodsItemBean.price = "13.00";
                shopCarGoodsItemBean.img = defaultImg;
                shopCarGoodsItemBean.num = j + 1;
                shopCarGoodsItemBean.title = "牛肉粉" + i;
                shopCarGoodsItemBean.type = "口味：不辣";
                shopCarGoodsItemBeans.add(shopCarGoodsItemBean);
            }
            simpleFakeBean.list = shopCarGoodsItemBeans;
            mData.add(simpleFakeBean);
        }
        if (page == 1) {

            mAdapter.setNewData(mData);
            mRefreshLayout.finishRefreshing();
        } else {
            mAdapter.appendData(mData);
            mRefreshLayout.finishLoadmore();
        }
    }


    @OnClick({R.id.ll_all_select, R.id.tv_delete, R.id.tv_settlement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_all_select:
                boolean b = mIvSelect.isSelected();
                mIvSelect.setSelected(!b);
                mAdapter.setAllSelect(!b);
                break;
            case R.id.tv_delete:
                break;
            case R.id.tv_settlement:

                break;
        }
    }
}
