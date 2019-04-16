package cn.dlc.customizedemo.myapplication.RecyclerviewAddItemDecoration;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;
import cn.dlc.customizedemo.myapplication.R;
import cn.dlc.customizedemo.myapplication.RecyclerviewAddItemDecoration.adapter.DecorationAdapter;

/**
 * Created by fengzimin  on  2019/02/28.
 * interface by
 */
public class RecyclerviewAddItemDecoration extends BaseCommonActivity {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private DecorationAdapter mAdapter;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_add_decoration;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRecycler();
        initData();
    }

    private void initData() {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            strings.add(i + "");
        }
        mAdapter.setNewData(strings);
    }

    private void initRecycler() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mAdapter = new DecorationAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }
}
