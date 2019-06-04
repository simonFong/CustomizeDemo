package com.simonfong.app2;

import android.support.multidex.MultiDexApplication;

import com.danikula.videocache.HttpProxyCacheServer;
import com.simonfong.app2.exoplayer.VideoCacheProxy;

/**
 * Created  on  2019/05/29.
 * interface by
 *
 * @author fengzimin
 */
public class App extends MultiDexApplication implements VideoCacheProxy.AppWrapper {

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
