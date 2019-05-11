package com.simonfong.app2.mqtt;


import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.simonfong.app2.R;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;

/**
 * Created by fengzimin  on  2019/04/24.
 * interface by
 */
public class MqttActivity extends BaseCommonActivity {
    @BindView(R.id.btn_send)
    Button mBtnSend;
    @BindView(R.id.tv_text)
    TextView mTvText;
    private MQTTManager mManager;
    private StringBuffer mStringBuffer;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_mqtt;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStringBuffer = new StringBuffer();
        mManager = MQTTManager.getInstance(this);
        mManager.connect();
        mManager.subscribeMsg("text", 0);
        mManager.setMessageHandlerCallBack((topicName, message) -> {
            Log.e(TAG, "MqttActivity:" + topicName + "----" + message);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    mStringBuffer.append(message + "\\n");
                    mTvText.setText(Html.fromHtml(mStringBuffer.toString().replace("\\n","\n")));
                }
            });
        });
    }

    private static final String TAG = "MqttActivity";

    @OnClick({R.id.btn_send, R.id.tv_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                mManager.publish("text2", "我是客户端", false, 1);
                break;
        }
    }
}
