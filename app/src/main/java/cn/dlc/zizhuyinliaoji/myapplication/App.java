package cn.dlc.zizhuyinliaoji.myapplication;

import android.app.Application;

import com.mob.MobSDK;

/**
 * Created by fengzimin  on  2018/6/3.
 * interface by
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this);
    }
}
