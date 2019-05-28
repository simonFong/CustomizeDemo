package com.simonfong.app2.exoplayer;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
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

import java.util.Formatter;
import java.util.Locale;

import butterknife.BindView;
import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;

/**
 * Created by fengzimin  on  2019/05/22.
 * interface by
 */
public class ExoplayerActivity extends BaseCommonActivity {


    @BindView(R.id.exo_play)
    PlayerView mExoPlay;
    private SimpleExoPlayer mSimpleExoPlayer;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_exo_player;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initExo();
    }

    private void initExo() {
        // step1. 创建一个默认的TrackSelector
        Handler mainHandler = new Handler();

        // 创建带宽
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        // 创建轨道选择工厂
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);

        // 创建轨道选择器实例
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        //step2. 创建播放器
        mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        mExoPlay.setPlayer(mSimpleExoPlayer);
        playVideo();
    }

    private void playVideo() {
        //测量播放过程中的带宽。 如果不需要，可以为null。
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        // 生成加载媒体数据的DataSource实例。
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "useExoplayer"), bandwidthMeter);
        // 生成用于解析媒体数据的Extractor实例。
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();


        // MediaSource代表要播放的媒体。
        MediaSource videoSource = new ExtractorMediaSource(Uri.parse("http://sdjsnmj.app.xiaozhuschool" +
                ".com/public/uploads/imgs/20190429/7a5a39ed74e4916e1bc6e8f2c927c1dc.mp4"), dataSourceFactory, extractorsFactory,
                null, null);
        //Prepare the player with the source.
        mSimpleExoPlayer.prepare(videoSource);
        //添加监听的listener
        //        mSimpleExoPlayer.setVideoListener(mVideoListener);
        mSimpleExoPlayer.addListener(mListener);
        //        mSimpleExoPlayer.setTextOutput(mOutput);
        mSimpleExoPlayer.setPlayWhenReady(true);

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
            switch (playbackState) {
                case ExoPlayer.STATE_ENDED:
                    LogPlus.e("Playback ended!");
                    //Stop playback and return to start position
                    setPlayPause(false);
                    mSimpleExoPlayer.seekTo(0);
                    break;
                case ExoPlayer.STATE_READY:
                    LogPlus.e("ExoPlayer ready! pos: " + mSimpleExoPlayer.getCurrentPosition()
                            + " max: " + stringForTime((int) mSimpleExoPlayer.getDuration()));
                    setProgress(0);
                    break;
                case ExoPlayer.STATE_BUFFERING:
                    LogPlus.e("Playback buffering!");
                    break;
                case ExoPlayer.STATE_IDLE:
                    LogPlus.e("ExoPlayer idle!");
                    break;
            }

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

    private void setPlayPause(boolean play){
        mSimpleExoPlayer.setPlayWhenReady(play);
    }

    private String stringForTime(int timeMs) {
        StringBuilder mFormatBuilder;
        Formatter mFormatter;
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        int totalSeconds =  timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours   = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }


    @Override
    protected void onPause() {
        LogPlus.e("MainActivity.onPause.");
        super.onPause();
        mSimpleExoPlayer.stop();
    }

    @Override
    protected void onStop() {
        LogPlus.e("MainActivity.onStop.");
        super.onStop();
        mSimpleExoPlayer.release();
    }
}
