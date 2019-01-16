package cn.dlc.customizedemo.myapplication.SearchHistory;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaofeng.flowlayoutmanager.Alignment;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.commonlibrary.ui.adapter.BaseRecyclerAdapter;
import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;
import cn.dlc.customizedemo.myapplication.R;

/**
 * Created by fengzimin  on  2019/01/10.
 * interface by
 */
public class SearchShopActivity extends BaseCommonActivity {

    @BindView(R.id.leftImage)
    ImageButton mLeftImage;
    @BindView(R.id.et_search_key)
    EditText mEtSearchKey;
    @BindView(R.id.iv_search_close)
    ImageView mIvSearchClose;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.iv_delete)
    ImageView mIvDelete;
    @BindView(R.id.recyclerview_hot)
    RecyclerView mRecyclerviewHot;
    @BindView(R.id.recyclerview_history)
    RecyclerView mRecyclerviewHistory;
    @BindView(R.id.tv_clear_history)
    TextView mTvClearHistory;
    @BindView(R.id.layout_history)
    LinearLayout mLayoutHistory;
    private SearchRecordAdapter mHotRecordAdapter;
    private SearchHistoryRecordAdapter mSearchHistoryRecordAdapter;
    private List<String> mHistoryListData;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_search_shop;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHotRecord();
        initHistoryRecord();
        initData();
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initHistoryRecordData();
    }

    /**
     * 获取本地的搜索记录
     */
    private void initHistoryRecordData() {
        mHistoryListData = SearchHistoryKeySpUtil.getHistoryKeyList();
        if (mHistoryListData.size() != 0) {
            mSearchHistoryRecordAdapter.setNewData(mHistoryListData);
            mLayoutHistory.setVisibility(View.VISIBLE);
        } else {
            mLayoutHistory.setVisibility(View.GONE);
        }
    }

    private void initEvent() {
        mEtSearchKey.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String key = v.getText().toString();
                    if (!TextUtils.isEmpty(key)) {
                        startSearchDetailActivity(key);
                    } else {
                        showToast("请输入搜索内容");
                    }

                }
                return false;
            }
        });

        mEtSearchKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    mIvSearchClose.setVisibility(View.VISIBLE);
                } else {
                    mIvSearchClose.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }




    private void initData() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("保温杯2");
        strings.add("保温杯杯2");
        strings.add("保温杯2");
        strings.add("保温保温杯2");
        strings.add("保温温杯2");
        strings.add("保温杯温杯2");
        strings.add("保温杯2温杯温杯2");
        strings.add("保温杯2温杯2");
        strings.add("保温杯温杯2");
        strings.add("保温温杯2");
        mHotRecordAdapter.setNewData(strings);

    }

    private void initHistoryRecord() {
        mRecyclerviewHistory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.shape_decoration_line));
        mRecyclerviewHistory.addItemDecoration(dividerItemDecoration);
        mRecyclerviewHistory.setNestedScrollingEnabled(false);
        mSearchHistoryRecordAdapter = new SearchHistoryRecordAdapter();
        mRecyclerviewHistory.setAdapter(mSearchHistoryRecordAdapter);
        mSearchHistoryRecordAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, BaseRecyclerAdapter.CommonHolder holder, int position) {
                startSearchDetailActivity(mSearchHistoryRecordAdapter.getItem(position));
            }
        });
        mSearchHistoryRecordAdapter.setOnSearchHistoryRecordAdapterListener(new SearchHistoryRecordAdapter
                .OnSearchHistoryRecordAdapterListener() {
            @Override
            public void deleteItemHistory(int position) {
                SearchHistoryKeySpUtil.deleteSingleHistory(mSearchHistoryRecordAdapter.getItem(position));
                initHistoryRecordData();
            }
        });
    }

    private void initHotRecord() {
        FlowLayoutManager mFlowLayoutManager = new FlowLayoutManager().singleItemPerLine().setAlignment(Alignment.LEFT);
        mFlowLayoutManager.setAutoMeasureEnabled(true);
        mRecyclerviewHot.setLayoutManager(mFlowLayoutManager);
        mRecyclerviewHot.addItemDecoration(new RecyclerView.ItemDecoration() {
            int space = 20;

            @Override
            public void getItemOffsets(Rect outRect, View view,
                                       RecyclerView parent, RecyclerView.State state) {
                outRect.right = space;
                outRect.bottom = space;

            }
        });
        mHotRecordAdapter = new SearchRecordAdapter();
        mRecyclerviewHot.setAdapter(mHotRecordAdapter);
        mHotRecordAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, BaseRecyclerAdapter.CommonHolder holder, int position) {
                startSearchDetailActivity(mHotRecordAdapter.getItem(position));
            }
        });
    }


    @OnClick({R.id.tv_cancel, R.id.iv_search_close, R.id.tv_clear_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel://取消
                finish();
                break;
            case R.id.iv_search_close://删除搜索内容
                mEtSearchKey.setText("");
                break;
            case R.id.tv_clear_history://清除搜索历史
                SearchHistoryKeySpUtil.clearHistoryList();
                initHistoryRecordData();
                break;
        }
    }

    /**
     * 跳转到搜索详情
     *
     * @param key
     */
    private void startSearchDetailActivity(String key) {
        SearchHistoryKeySpUtil.refreshHistroy(key);
        initHistoryRecordData();
    }
}
