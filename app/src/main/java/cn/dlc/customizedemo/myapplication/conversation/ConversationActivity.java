package cn.dlc.customizedemo.myapplication.conversation;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;
import cn.dlc.customizedemo.myapplication.R;

/**
 * Created by fengzimin  on  2018/09/07.
 * interface by
 */
public class ConversationActivity extends BaseCommonActivity {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout mRefreshLayout;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.btn_send)
    Button mBtnSend;
    private MessageConversationAdapter mAdapter;
    private int page;
    private List<MessageConversationBean> mData;

    @Override
    protected void beforeSetContentView() {
        super.beforeSetContentView();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTranslucentStatus();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_conversation;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidBug5497Workaround.assistActivity(this);
        initRecycler();
        initEvent();
    }

    private void initEvent() {
        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight,
                                       int oldBottom) {
                //获取View可见区域的bottom
                Rect rect = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                if (bottom != 0 && oldBottom != 0 && bottom - rect.bottom <= 0) {
                    //                    Toast.makeText(Main3Activity.this, "隐藏", Toast.LENGTH_SHORT).show();
//                    if (mAdapter != null) {
//                        List<MessageConversationBean> data = mAdapter.getData();
//                        mRecyclerView.scrollToPosition(data.size() - 1);
//                    }
                } else {

                    //                    Toast.makeText(Main3Activity.this, "弹出", Toast.LENGTH_SHORT).show();
                    if (mAdapter != null) {
                        List<MessageConversationBean> data = mAdapter.getData();
                        mRecyclerView.scrollToPosition(data.size() - 1);
                    }
                }
            }
        });

    }

    private void initRecycler() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new MessageConversationAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        initRefresh();
    }

    private void initRefresh() {
        ProgressLayout mProgressLayout = new ProgressLayout(this);
        mProgressLayout.setColorSchemeResources(R.color.colorAccent);
        mRefreshLayout.setHeaderView(mProgressLayout);
        mRefreshLayout.setFloatRefresh(true);
        mRefreshLayout.setEnableOverScroll(false);
        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                getData(true);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                //getData();
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

        mData = new ArrayList<MessageConversationBean>();
        for (int i = 0; i < 5; i++) {
            MessageConversationBean simpleFakeBean = new MessageConversationBean();
            if (i % 2 == 0) {
                simpleFakeBean.time = ("");
                simpleFakeBean.showLeft = true;
                simpleFakeBean.name = ("羽咲绫乃");
                simpleFakeBean.portrait = "http://img3.imgtn.bdimg.com/it/u=3616173625,1936555086&fm=11&gp=0.jpg";
                simpleFakeBean.content = "打到你自闭哦";
            } else {
                simpleFakeBean.time = ("2018-9-7 14:18:19");
                simpleFakeBean.showLeft = false;
                simpleFakeBean.name = ("我");
                simpleFakeBean.portrait = "http://img0.imgtn.bdimg.com/it/u=711152993,827611855&fm=11&gp=0.jpg";
                simpleFakeBean.content = "来单挑";
            }
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


    @OnClick({R.id.btn_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                String s = mEtContent.getText().toString();

                if (TextUtils.isEmpty(s)) {
                    return;
                }
                MessageConversationBean simpleFakeBean = new MessageConversationBean();
                simpleFakeBean.time = ("2018-9-7 14:18:19");
                simpleFakeBean.showLeft = false;
                simpleFakeBean.name = ("我");
                simpleFakeBean.portrait = "http://img0.imgtn.bdimg.com/it/u=711152993,827611855&fm=11&gp=0.jpg";
                simpleFakeBean.content = s;
                mData.add(simpleFakeBean);
                mAdapter.addData(simpleFakeBean);
                mEtContent.setText("");
                break;
        }
    }
}
