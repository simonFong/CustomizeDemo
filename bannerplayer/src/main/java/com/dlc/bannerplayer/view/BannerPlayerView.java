package com.dlc.bannerplayer.view;

import android.view.View;
import android.widget.ImageView;
import com.dlc.bannerplayer.WeakHandler;
import com.dlc.bannerplayer.data.BannerData;

public interface BannerPlayerView<P extends View> extends Comparable<BannerPlayerView> {

    interface PlayListener {

        /**
         * 一个媒体播放完毕
         */
        void onPlayComplete(BannerPlayerView view);

        /**
         * 播放出现异常
         *
         * @param view
         * @param throwable
         */
        void onPlayError(BannerPlayerView view, Throwable throwable);
    }

    /**
     * 设置监听
     *
     * @param listener
     */
    void setListener(PlayListener listener);

    /**
     * 获取图片控件
     *
     * @return
     */
    ImageView getImageView();

    /**
     * 获取播放器控件
     *
     * @return
     */
    P getPlayerView();

    /**
     * 设置数据
     *
     * @param data
     */
    void setData(BannerData data);

    void setIndex(int index);

    int getIndex();

    /**
     * 获取数据
     *
     * @return
     */
    BannerData getData();

    /**
     * 准备加载图片/视频
     */
    void prepare();

    /**
     * 播放
     */
    void play();

    /**
     * 延迟播放
     */
    void delayPlay(long delay);

    /**
     * 暂停
     *
     * @param seek0 回到进度0
     */
    void pause(boolean seek0);

    /**
     * 停止
     */
    void stop();

    /**
     * 销毁
     */
    void release();

    /**
     * 获取这个控件
     *
     * @return
     */
    View getSelf();

    /**
     * 配置播放属性
     *
     * @param playAttrs 播放属性
     * @param handler 控制的Handler
     */
    void setConfig(PlayAttrs playAttrs, WeakHandler handler);

    /**
     * 是否在播放
     *
     * @return
     */
    boolean isPlaying();

    /**
     * 是否已经准备好
     * @return
     */
    boolean isPrepared();
}
