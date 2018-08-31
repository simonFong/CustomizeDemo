package cn.dlc.zizhuyinliaoji.myapplication;

import android.app.Application;

import com.mob.MobSDK;

import cn.dlc.commonlibrary.utils.PrefUtil;
import cn.dlc.commonlibrary.utils.ResUtil;
import cn.dlc.commonlibrary.utils.ScreenUtil;

/**
 * Created by fengzimin  on  2018/6/3.
 * interface by
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this);
        ScreenUtil.init(this); // 获取屏幕尺寸
        ResUtil.init(this); // 资源
        PrefUtil.init(this); // SharedPreference
    }
}
