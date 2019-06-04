package com.dlc.bannerplayer.view;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.danikula.videocache.HttpProxyCacheServer;
import com.dlc.bannerplayer.R;
import com.dlc.bannerplayer.VideoCacheProxy;
import com.dlc.bannerplayer.WeakHandler;
import com.dlc.bannerplayer.data.BannerData;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
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
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class BannerPlayerViewImpl extends FrameLayout
    implements BannerPlayerView<SimpleExoPlayerView> {

    private static final String TAG = "BannerPlayerView";
    private static final boolean SHOW_LOG = false;

    private SimpleExoPlayerView mPlayerView;
    private ImageView mImageView;
    private BannerData mData;
    private SimpleExoPlayer mExoPlayer;
    private MediaSource mMediaSource;

    private PlayListener mListener;
    private int mIndex;

    private WeakHandler mHandler;
    private PlayAttrs mPlayAttrs;
    /**
     * 是否在播放，只要调用了{@link #play()}就为true
     */
    private boolean mIsPlaying;
    private Context mContext;

    public BannerPlayerViewImpl(@NonNull Context context) {
        this(context, null);
    }

    private BannerPlayerViewImpl(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private BannerPlayerViewImpl(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        // 播放控件
        mPlayerView = new SimpleExoPlayerView(context);
        // 占满全屏
        mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        mPlayerView.setUseController(false); // 不使用默认控件
        addView(mPlayerView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        mImageView = new ImageView(context);
        addView(mImageView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        mExoPlayer = newSimpleExoPlayer();
        mExoPlayer.addListener(mExoListener);
        mPlayerView.setPlayer(mExoPlayer);

        VideoCacheProxy.getProxy(context);

        mIsPlaying = false;
    }

    private ExoPlayer.EventListener mExoListener = new ExoPlayer.EventListener() {
        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest) {

        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups,
            TrackSelectionArray trackSelections) {

        }

        @Override
        public void onLoadingChanged(boolean isLoading) {

        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            switch (playbackState) {
                case ExoPlayer.STATE_BUFFERING:
                    break;
                case ExoPlayer.STATE_ENDED: // 播放结束
                    // 如果是强制播放时间，就设置重新播放
                    if (mPlayAttrs.forcePlayDuration) {
                        if (mIsPlaying) {
                            mExoPlayer.seekTo(0);
                        }
                        return;
                    }

                    // 执行停止
                    onStop();

                    break;
                case ExoPlayer.STATE_IDLE:
                    break;
                case ExoPlayer.STATE_READY:
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {

            log(Log.ERROR, "index=" + getIndex() + ",执行onPlayerError");

            mMediaSource = null;
            mIsPlaying = false;
            // 播放异常
            if (mListener != null && mPlayAttrs.currentPlayingIndex == getIndex()) {
                mListener.onPlayError((BannerPlayerView) getSelf(), error);
            }
        }

        @Override
        public void onPositionDiscontinuity() {

        }
    };

    @Override
    public void setListener(PlayListener listener) {
        mListener = listener;
    }

    @Override
    public ImageView getImageView() {
        return mImageView;
    }

    @Override
    public SimpleExoPlayerView getPlayerView() {
        return mPlayerView;
    }

    @Override
    public void setData(BannerData data) {
        mData = data;
        updateChildViewVisible(data);
    }

    private void updateChildViewVisible(BannerData data) {
        switch (data.getType()) {
            case BannerData.TYPE_IMAGE:
                mImageView.setVisibility(VISIBLE);
                mPlayerView.setVisibility(GONE);
                break;
            case BannerData.TYPE_VIDEO:
                mImageView.setVisibility(GONE);
                mPlayerView.setVisibility(VISIBLE);
                break;
            default:
                mImageView.setVisibility(VISIBLE);
                mPlayerView.setVisibility(GONE);
                break;
        }
    }

    @Override
    public void setIndex(int index) {
        mIndex = index;
    }

    @Override
    public int getIndex() {
        return mIndex;
    }

    @Override
    public BannerData getData() {
        return mData;
    }

    @Override
    public void prepare() {

        log(Log.ERROR, "index=" + getIndex() + ",执行prepare");

        if (mData == null) {
            return;
        }

        switch (mData.getType()) {
            case BannerData.TYPE_IMAGE:
                // 如果是图片类型，则直接加载图片
                loadImage();
                break;
            case BannerData.TYPE_VIDEO:
                // 视频的话，就准备载入，当不播放
                prepareVideo();
                break;
            default:
                // 其他类型就不管了
                loadImageEmpty();
                break;
        }
    }

    private void loadImageEmpty() {
        Glide.with(this)
            .asBitmap()
            .load(R.drawable.no_banner)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL).dontAnimate())
            .into(mImageView);
    }

    /**
     * 加载图片
     */
    private void loadImage() {
        Glide.with(this)
            .asBitmap()
            .load(mData.getUrl())
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL).dontAnimate())
            .into(mImageView);
    }

    /**
     * 准备视频
     */
    private void prepareVideo() {
        mExoPlayer.stop();
        mExoPlayer.seekTo(0);
        // 获取缓存的代理地址
        buildMediaSource();
        mExoPlayer.prepare(mMediaSource);
    }

    private void buildMediaSource() {
        String proxyUrl = getProxyUrl(mData.getUrl());
        mMediaSource = newVideoSource(proxyUrl);
    }

    @Override
    public void play() {

        log(Log.INFO, "index=" + getIndex() + ",执行play");

        mIsPlaying = true;

        if (mData.getType() == BannerData.TYPE_VIDEO) {

            if (mMediaSource == null) {
                buildMediaSource();
                mExoPlayer.prepare(mMediaSource);
            }
            // 设置准备好就播放播放
            mExoPlayer.seekTo(0);
            mExoPlayer.setPlayWhenReady(true);
        } else if (mData.getType() == BannerData.TYPE_IMAGE) {
            loadImage();
        } else {
            loadImageEmpty();
        }

        if (mData.getType() == BannerData.TYPE_IMAGE || mPlayAttrs.forcePlayDuration) {
            // 强制播放时间的话，延时执行停止
            mHandler.postDelayed(mRunStopPlay, mPlayAttrs.playDuration);
        }
    }

    private Runnable mRunPlay = new Runnable() {
        @Override
        public void run() {
            play();
        }
    };

    @Override
    public void delayPlay(final long delay) {

        mHandler.postDelayed(mRunPlay, delay);
    }

    @Override
    public void pause(boolean seek0) {

        log(Log.DEBUG, "index=" + getIndex() + ",执行pause");

        mIsPlaying = false;

        mHandler.removeCallbacks(mRunPlay);
        mHandler.removeCallbacks(mRunStopPlay);
        mExoPlayer.setPlayWhenReady(false);
        if (seek0) {
            mExoPlayer.seekTo(0);
        }
    }

    private Runnable mRunStopPlay = new Runnable() {
        @Override
        public void run() {
            if (mIsPlaying) {
                onStop();
            }
        }
    };

    private void onStop() {
        mIsPlaying = false;

        log(Log.WARN, "index=" + getIndex() + ",执行onStop");

        if (mListener != null && mPlayAttrs.currentPlayingIndex == getIndex()) {

            stopVideo();
            mListener.onPlayComplete((BannerPlayerView) getSelf());
        }
    }

    /**
     * 停止播放视频
     */
    private void stopVideo() {
        mMediaSource = null;
        mExoPlayer.stop();
    }

    @Override
    public void stop() {
        log(Log.WARN, "index=" + getIndex() + ",执行stop");
        mHandler.removeCallbacks(mRunPlay);
        mHandler.removeCallbacks(mRunStopPlay);
        mIsPlaying = false;
        stopVideo();
    }

    @Override
    public void release() {
        try {
            mHandler.removeCallbacks(mRunPlay);
            mHandler.removeCallbacks(mRunStopPlay);
            stopVideo();
            mExoPlayer.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View getSelf() {
        return this;
    }

    @Override
    public void setConfig(PlayAttrs playAttrs, WeakHandler handler) {
        mPlayAttrs = playAttrs;
        mHandler = handler;
    }

    @Override
    public boolean isPlaying() {
        return mIsPlaying;
    }

    @Override
    public boolean isPrepared() {
        if (mData.getType() == BannerData.TYPE_VIDEO) {
            return mExoPlayer.getPlaybackState() == ExoPlayer.STATE_READY;
        }
        return false;
    }

    /**
     * 获取带缓存的视频url
     *
     * @param originalUrl
     * @return
     */
    private String getProxyUrl(String originalUrl) {
        HttpProxyCacheServer proxy = VideoCacheProxy.getProxy(getContext());
        return proxy.getProxyUrl(originalUrl);
    }

    /**
     * 构建ExoPlayer实例
     *
     * @return
     */
    private SimpleExoPlayer newSimpleExoPlayer() {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
            new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();
        return ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
    }

    /**
     * 构造播放数据
     *
     * @param url
     * @return
     */
    private MediaSource newVideoSource(String url) {
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        String userAgent = Util.getUserAgent(getContext(), "BannerPlayerViewImpl");
        DataSource.Factory dataSourceFactory =
            new DefaultDataSourceFactory(getContext(), userAgent, bandwidthMeter);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        return new ExtractorMediaSource(Uri.parse(url), dataSourceFactory, extractorsFactory, null,
            null);
    }

    @Override
    public int compareTo(@NonNull BannerPlayerView o) {
        return this.getIndex() - o.getIndex();
    }

    private void log(int logLevel, String msg) {

        if (SHOW_LOG) {

            switch (logLevel) {
                case Log.VERBOSE:
                    Log.v(TAG, msg);
                    break;
                case Log.DEBUG:
                    Log.d(TAG, msg);
                    break;
                case Log.INFO:
                    Log.i(TAG, msg);
                    break;
                case Log.WARN:
                    Log.w(TAG, msg);
                    break;
                case Log.ERROR:
                    Log.e(TAG, msg);
                    break;
            }
        }
    }
}
