package com.simonfong.app2.exoplayer;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.danikula.videocache.HttpProxyCacheServer;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.licheedev.myutils.LogPlus;
import com.simonfong.app2.R;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;

/**
 * Created by fengzimin  on  2019/05/22.
 * interface by
 */
public class ExoplayerActivity extends BaseCommonActivity {


    @BindView(R.id.exo_play)
    PlayerView mExoPlay;
    @BindView(R.id.exo_play_2)
    PlayerView mExoPlay2;
    @BindView(R.id.btn_stop)
    Button mBtnStop;
    private SimpleExoPlayer mSimpleExoPlayer;
    private SimpleExoPlayerHelper mSimpleExoPlayerHelper1;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_exo_player;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initExo();
        initExo2();
    }

    private void initExo2() {
        //播放的数据源
        List<String> strings = new ArrayList<>();
        strings.add("http://zsxyylsb.app.xiaozhuschool.com/public/uploads/imgs/20190528/835ad2bb1f56f525311b980291805cff.mp3");
        strings.add("http://zsxyylsb.app.xiaozhuschool.com/public/uploads/imgs/20190528/6c48ac8e73bba9e044bc5e57d5d5106d.mp4");
        strings.add("http://zsxyylsb.app.xiaozhuschool.com/public/uploads/imgs/20190528/835ad2bb1f56f525311b980291805cff.mp3");
        strings.add("http://zsxyylsb.app.xiaozhuschool.com/public/uploads/imgs/20190610/a3fb4373a929a880685b977ea404194b.mp3");
        strings.add("http://zsxyylsb.app.xiaozhuschool.com/public/uploads/imgs/20190610/36a43a0cd8119f2da9c4f2ccd398816d.mp3");
        //设置
        mSimpleExoPlayerHelper1 = SimpleExoPlayerHelper.createMySimpleExoPlayer(this, mExoPlay2)
                .prepare(strings)
                .setRepeatMode(SimpleExoPlayerHelper.RepeatMode.REPEAT_MODE_OFF)
                .setPlayWhenReady(true);
        //是否显示默认进度条
        mExoPlay.setUseController(true);
    }

    private void initExo() {
        newSimpleExoPlayer();

        mExoPlay.setUseController(true);
        mExoPlay.setPlayer(mSimpleExoPlayer);
        playVideo();
    }

    private void newSimpleExoPlayer() {
        // 创建带宽
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        // 创建轨道选择工厂
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);

        // 创建轨道选择器实例
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        //step2. 创建播放器
        mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        //设置重复模式
        mSimpleExoPlayer.setRepeatMode(Player.REPEAT_MODE_ALL);
    }

    private void playVideo() {

        //        MediaSource mediaSource2 = newVideoSource("http://pzxtj.a.xiaozhuschool.com/statics/MP3/123.mp3");
        //        MediaSource mediaSource2 = newVideoSource("https://lixiang.https.xiaozhuschool.com/statics/images/shiping/1
        //        .mp3");
        MediaSource mediaSource2 = buildMediaSource("https://lixiang.https.xiaozhuschool.com/statics/images/shiping/net1.mp3");
        //        MediaSource mediaSource2 = newVideoSource("https://d33av21req61rl.cloudfront
        //        .net/track-files/34ef163a-2389-461f-9c99-07806befc439.mp3");
        //        MediaSource mediaSource2 = newVideoSource("https://d33av21req61rl.cloudfront
        //        .net/track-files/636febce-7840-4d3f-9486-049c10afeb48.mp3");
        MediaSource mediaSource = buildMediaSource("http://sdjsnmj.app.xiaozhuschool" +
                ".com/public/uploads/imgs/20190429/7a5a39ed74e4916e1bc6e8f2c927c1dc.mp4");
        MediaSource mediaSource1 = buildMediaSource("http://sdjsnmj.app.xiaozhuschool" +
                ".com/public/uploads/imgs/20190424/6ab5a9bdf662862fcc26e00525b9288d.mp4");
        ConcatenatingMediaSource concatenatingMediaSource = new ConcatenatingMediaSource(mediaSource2, mediaSource, mediaSource1);

        //Prepare the player with the source.
        mSimpleExoPlayer.prepare(concatenatingMediaSource);
        //添加监听的listener
        //        mSimpleExoPlayer.setVideoListener(mVideoListener);
        mSimpleExoPlayer.addListener(mListener);
        //        mSimpleExoPlayer.setTextOutput(mOutput);
        mSimpleExoPlayer.setPlayWhenReady(true);

    }

    private MediaSource buildMediaSource(String url) {
        String proxyUrl = getProxyUrl(url);
        return newVideoSource(proxyUrl);
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
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "useExoplayer"), bandwidthMeter);
        // 生成用于解析媒体数据的Extractor实例。
        ExtractorMediaSource.Factory factory = new ExtractorMediaSource.Factory(dataSourceFactory);
        ExtractorMediaSource mediaSource = factory.createMediaSource(Uri.parse(url));
        //        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        //        return new ExtractorMediaSource(Uri.parse(url), dataSourceFactory, extractorsFactory, null,
        //                null);
        return mediaSource;
    }

    /**
     * 获取带缓存的视频url
     *
     * @param originalUrl
     * @return
     */
    private String getProxyUrl(String originalUrl) {
        HttpProxyCacheServer proxy = VideoCacheProxy.getProxy(this);
        return proxy.getProxyUrl(originalUrl);
    }

    Player.EventListener mListener = new Player.EventListener() {
        @Override
        public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
            LogPlus.e("onTimelineChanged");
        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            LogPlus.e("onTracksChanged");
        }

        @Override
        public void onLoadingChanged(boolean isLoading) {
            LogPlus.e("onLoadingChanged----" + isLoading);
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            LogPlus.e("onPlayerStateChanged: playWhenReady = " + String.valueOf(playWhenReady)
                    + " playbackState = " + playbackState);
        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {
            LogPlus.e("onRepeatModeChanged----" + repeatMode);
        }

        @Override
        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
            LogPlus.e("onShuffleModeEnabledChanged----" + shuffleModeEnabled);
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            LogPlus.e("onPlayerError----" + error.toString());
        }

        @Override
        public void onPositionDiscontinuity(int reason) {
            LogPlus.e("onPositionDiscontinuity----" + reason);
        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            LogPlus.e("onPlaybackParametersChanged----" + playbackParameters.toString());
        }

        @Override
        public void onSeekProcessed() {
            LogPlus.e("onSeekProcessed----");
        }
    };

    private void setPlayPause(boolean play) {
        mSimpleExoPlayer.setPlayWhenReady(play);
    }

    private String stringForTime(int timeMs) {
        StringBuilder mFormatBuilder;
        Formatter mFormatter;
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }


    @Override
    protected void onPause() {
        LogPlus.e("MainActivity2.onPause.");
        super.onPause();
        if (mSimpleExoPlayerHelper1 != null) {
            mSimpleExoPlayerHelper1.stop();
        }
    }

    @Override
    protected void onStop() {
        LogPlus.e("MainActivity2.onStop.");
        super.onStop();
        if (mSimpleExoPlayerHelper1 != null) {
            mSimpleExoPlayerHelper1.release();
        }
    }

    @OnClick({R.id.btn_stop, R.id.btn_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                mSimpleExoPlayerHelper1.start();
                break;
            case R.id.btn_stop:
                mSimpleExoPlayerHelper1.pause();
                break;

        }
    }
}
