package cn.dlc.customizedemo.myapplication;

import android.app.Application;

import com.mob.MobSDK;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import cn.dlc.commonlibrary.utils.PrefUtil;
import cn.dlc.commonlibrary.utils.ResUtil;
import cn.dlc.commonlibrary.utils.ScreenUtil;
import cn.dlc.customizedemo.myapplication.SearchHistory.SharedPreferencesUtil;

/**
 * Created by fengzimin  on  2018/6/3.
 * interface by
 */
public class App extends Application {

    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this);
        ScreenUtil.init(this); // 获取屏幕尺寸
        ResUtil.init(this); // 资源
        PrefUtil.init(this); // SharedPreference
        SharedPreferencesUtil.getInstance(this, "sp_data");
        mRefWatcher = LeakCanary.install(this);
    }
}
