package com.simonfong.app2;

import android.provider.Settings;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.danikula.videocache.HttpProxyCacheServer;
import com.simonfong.app2.exoplayer.VideoCacheProxy;

/**
 * Created  on  2019/05/29.
 * interface by
 *
 * @author fengzimin
 */
public class App extends MultiDexApplication implements VideoCacheProxy.AppWrapper {

    private static App mInstances;
    private static String androidId;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstances = this;
    }

    public static App getInstances() {
        return mInstances;
    }

    public static String getMacno() {
        if (TextUtils.isEmpty(androidId)) {
            androidId = Settings.System.getString(App.getInstances().getContentResolver(), Settings.System.ANDROID_ID);
        }
        return androidId;
    }

    private HttpProxyCacheServer mProxy;

    private HttpProxyCacheServer newProxy() {
        // 这里的配置可以修改，参考上面的“这里”
        return new HttpProxyCacheServer.Builder(this)
                // 视频缓存文件所在的文件夹，可以进行修改
                .cacheDirectory(VideoCacheProxy.getVideoCacheDir(this))
                .build();
    }

    @Override
    public HttpProxyCacheServer getVideoCacheProxy() {
        return mProxy == null ? (mProxy = newProxy()) : mProxy;
    }
}
