package com.simonfong.app2.exoplayer;

import android.content.Context;
import android.net.Uri;

import com.danikula.videocache.HttpProxyCacheServer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created  on  2019/05/29.
 * interface by
 *
 * @author fengzimin
 */
public class SimpleExoPlayerHelper {

    private final Context mContext;
    private SimpleExoPlayer mSimpleExoPlayer;
    private ConcatenatingMediaSource mConcatenatingMediaSource;
    private static SimpleExoPlayerHelper simpleExoPlayerHelper;

    public SimpleExoPlayerHelper(Context context, PlayerView playerView) {
        mContext = context;
        // 创建带宽
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        // 创建轨道选择工厂
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);

        // 创建轨道选择器实例
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        //step2. 创建播放器
        mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

        playerView.setPlayer(mSimpleExoPlayer);

        mConcatenatingMediaSource = new ConcatenatingMediaSource();

    }

    public static SimpleExoPlayerHelper createMySimpleExoPlayer(Context context, PlayerView playerView) {
        simpleExoPlayerHelper = new SimpleExoPlayerHelper(context, playerView);
        return simpleExoPlayerHelper;
    }

    /**
     * @return 提供simpleExoPlayer对象，实现其他功能
     */
    public SimpleExoPlayer getSimpleExoPlayer() {
        return mSimpleExoPlayer;
    }

    public SimpleExoPlayerHelper prepare(List<String> urls) {
        for (String url : urls) {
            MediaSource mediaSource = buildMediaSource(url);
            mConcatenatingMediaSource.addMediaSource(mediaSource);
        }
        mSimpleExoPlayer.prepare(mConcatenatingMediaSource);
        return simpleExoPlayerHelper;
    }

    public SimpleExoPlayerHelper prepare(String url) {
        List<String> strings = new ArrayList<>();
        strings.add(url);
        prepare(strings);
        return simpleExoPlayerHelper;
    }

    public enum RepeatMode {
        REPEAT_MODE_ALL(Player.REPEAT_MODE_ALL),
        REPEAT_MODE_ONE(Player.REPEAT_MODE_ONE),
        REPEAT_MODE_OFF(Player.REPEAT_MODE_OFF);

        private int mMode;

        RepeatMode(int repeatMode) {
            mMode = repeatMode;
        }

        public int getMode() {
            return mMode;
        }

        public void setMode(int mode) {
            mMode = mode;
        }
    }


    /**
     * 设置循环样式
     *
     * @param repeatMode
     * @return
     */
    public SimpleExoPlayerHelper setRepeatMode(RepeatMode repeatMode) {
        mSimpleExoPlayer.setRepeatMode(repeatMode.getMode());
        return simpleExoPlayerHelper;
    }

    public SimpleExoPlayerHelper setPlayWhenReady(boolean playWhenReady) {
        mSimpleExoPlayer.setPlayWhenReady(playWhenReady);
        return simpleExoPlayerHelper;
    }

    public void stop() {
        mSimpleExoPlayer.stop();
    }

    public void pause() {
        boolean playWhenReady = mSimpleExoPlayer.getPlayWhenReady();
        if(playWhenReady) {
            mSimpleExoPlayer.setPlayWhenReady(false);
        }
    }

    public void start() {
        boolean playWhenReady = mSimpleExoPlayer.getPlayWhenReady();
        if(!playWhenReady) {
            mSimpleExoPlayer.setPlayWhenReady(true);
        }
    }

    public void release() {
        mSimpleExoPlayer.release();
    }

    private MediaSource buildMediaSource(String url) {
        String proxyUrl = getProxyUrl(url);
        return newVideoSource(proxyUrl);
    }

    /**
     * 获取带缓存的视频url
     *
     * @param originalUrl
     * @return
     */
    private String getProxyUrl(String originalUrl) {
        HttpProxyCacheServer proxy = VideoCacheProxy.getProxy(mContext);
        return proxy.getProxyUrl(originalUrl);
    }

    /**
     * 构造播放数据
     *
     * @param url
     * @return
     */
    private MediaSource newVideoSource(String url) {
        //测量播放过程中的带宽。 如果不需要，可以为null。
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        // 生成加载媒体数据的DataSource实例。
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mContext,
                Util.getUserAgent(mContext, "useExoplayer"), bandwidthMeter);
        // 生成用于解析媒体数据的Extractor实例。
        ExtractorMediaSource.Factory factory = new ExtractorMediaSource.Factory(dataSourceFactory);
        ExtractorMediaSource mediaSource = factory.createMediaSource(Uri.parse(url));
        //        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        //        return new ExtractorMediaSource(Uri.parse(url), dataSourceFactory, extractorsFactory, null,
        //                null);
        return mediaSource;
    }
}
