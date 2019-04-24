package cn.dlc.customizedemo.myapplication.TaskAndTimeOut;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;
import cn.dlc.customizedemo.myapplication.R;

/**
 * Created by fengzimin  on  2019/04/20.
 * interface by
 */
public class TaskAndTimeOutActivity extends BaseCommonActivity {
    @BindView(R.id.btn)
    Button mBtn;
    Handler mHandler;
    HandlerThread serial_port = new HandlerThread("serial_port");

    @Override
    protected int getLayoutID() {
        return R.layout.activity_task_and_timeout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        serial_port.start();
        mHandler = new Handler(serial_port.getLooper(), msg -> {
            int num = (int) msg.obj;
            try {
                Thread.sleep(3000);
                Log.e("测试: ", "执行了3s的耗时操作" + num);
                if (num == 4) {
                    serial_port.quit();
                    throw new IllegalAccessException();
                }
                if (num == 6) {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        });

    }

    int num = 0;
    private static final String TAG = "TaskAndTimeOutActivity";
    @OnClick(R.id.btn)
    public void onViewClicked() {
        if(!serial_port.isAlive()) {
            Log.e(TAG, "dsfasdfjad");
            serial_port.run();
        }
        mHandler.obtainMessage(0x1, num++).sendToTarget();
    }
}
