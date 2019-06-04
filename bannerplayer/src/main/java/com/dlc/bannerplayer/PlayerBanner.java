package com.dlc.bannerplayer;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlc.bannerplayer.data.BannerData;
import com.dlc.bannerplayer.data.UrlBannerData;
import com.dlc.bannerplayer.listener.OnBanneClickrListener;
import com.dlc.bannerplayer.view.BannerPlayerView;
import com.dlc.bannerplayer.view.BannerPlayerViewImpl;
import com.dlc.bannerplayer.view.BannerViewPager;
import com.dlc.bannerplayer.view.PlayAttrs;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.support.v4.view.ViewPager.OnPageChangeListener;
import static android.support.v4.view.ViewPager.PageTransformer;

public class PlayerBanner extends FrameLayout implements OnPageChangeListener {
    public String tag = "BannerPlayer";

    private int mIndicatorMargin = BannerConfig.PADDING_SIZE;
    private int mIndicatorWidth;
    private int mIndicatorHeight;
    private int mIndicatorSize;
    private int mBannerBackgroundImage;
    private int mBannerIndicatorStyle = BannerConfig.CIRCLE_INDICATOR;
    private int mDelayTime = BannerConfig.TIME;
    private int mScrollTime = BannerConfig.DURATION;
    private boolean mAutoPlay = BannerConfig.IS_AUTO_PLAY;
    private boolean mAllowScroll = BannerConfig.IS_SCROLL;
    private int mIndicatorSelectedResId = R.drawable.gray_radius;
    private int mIndicatorUnselectedResId = R.drawable.white_radius;
    private int mLayoutResId = R.layout.default_banner_layout;
    private int mTitleHeight;
    private int mTitleBackground;
    private int mTitleTextColor;
    private int mTitleTextSize;
    private int mGravity = -1;
    private int mScaleType = 1;

    private Context mContext;

    private BannerViewPager mViewPager;
    private TextView mBannerTitle, mNumIndicatorInside, mNumIndicator;
    private LinearLayout mIndicator, mIndicatorInside, mTitleView;
    private ImageView mBannerDefaultImage;

    private BannerPagerAdapter mPagerAdapter;
    private OnPageChangeListener mOnPageChangeListener;
    private OnBanneClickrListener mBannerListener;

    private WeakHandler mHandler = new WeakHandler();

    private boolean mCacheAllPage;

    private PlayAttrs mPlayAttrs; // 播放属性

    private List<String> mTitles; // 标题数据
    private List<ImageView> mIndicatorImages; // 指示器图
    private ArrayList<BannerData> mBannerData; // 轮播图数据
    private ArrayList<BannerData> mBannerDataEx; // 前后加了辅助项的数据
    private ArrayList<BannerPlayerView> mViewCacheList; // 视图缓存数组（所有都在这里）

    private int mCount = 0;
    private int mCountEx = 0;

    private int mCurrentItem;
    private int mErrorDelayTime;

    public PlayerBanner(Context context) {
        this(context, null);
    }

    public PlayerBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;

        mPlayAttrs = new PlayAttrs();

        mTitles = new ArrayList<>();
        mBannerData = new ArrayList<>();
        mBannerDataEx = new ArrayList<>();
        mIndicatorImages = new ArrayList<>();

        mViewCacheList = new ArrayList<>();

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mIndicatorSize = dm.widthPixels / 80;
        initView(context, attrs);
    }

    //region 初始化控件
    private void initView(Context context, AttributeSet attrs) {

        handleTypedArray(context, attrs);
        View view = LayoutInflater.from(context).inflate(mLayoutResId, this, true);
        mBannerDefaultImage = (ImageView) view.findViewById(R.id.bannerDefaultImage);
        mViewPager = (BannerViewPager) view.findViewById(R.id.bannerViewPager);
        mTitleView = (LinearLayout) view.findViewById(R.id.titleView);
        mIndicator = (LinearLayout) view.findViewById(R.id.circleIndicator);
        mIndicatorInside = (LinearLayout) view.findViewById(R.id.indicatorInside);
        mBannerTitle = (TextView) view.findViewById(R.id.bannerTitle);
        mNumIndicator = (TextView) view.findViewById(R.id.numIndicator);
        mNumIndicatorInside = (TextView) view.findViewById(R.id.numIndicatorInside);
        mBannerDefaultImage.setImageResource(mBannerBackgroundImage);
        initViewPagerScroll();
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PlayerBanner);
        mIndicatorWidth =
                typedArray.getDimensionPixelSize(R.styleable.PlayerBanner_banner_indicator_width,
                        mIndicatorSize);
        mIndicatorHeight =
                typedArray.getDimensionPixelSize(R.styleable.PlayerBanner_banner_indicator_height,
                        mIndicatorSize);
        mIndicatorMargin =
                typedArray.getDimensionPixelSize(R.styleable.PlayerBanner_banner_indicator_margin,
                        BannerConfig.PADDING_SIZE);
        mIndicatorSelectedResId =
                typedArray.getResourceId(R.styleable.PlayerBanner_banner_indicator_drawable_selected,
                        R.drawable.gray_radius);
        mIndicatorUnselectedResId =
                typedArray.getResourceId(R.styleable.PlayerBanner_banner_indicator_drawable_unselected,
                        R.drawable.white_radius);
        mScaleType =
                typedArray.getInt(R.styleable.PlayerBanner_banner_image_scale_type, mScaleType);
        mDelayTime =
                typedArray.getInt(R.styleable.PlayerBanner_banner_delay_time, BannerConfig.TIME);
        mErrorDelayTime =
                typedArray.getInt(R.styleable.PlayerBanner_banner_error_delay_time, BannerConfig.TIME);
        mPlayAttrs.playDuration = mDelayTime;

        mScrollTime =
                typedArray.getInt(R.styleable.PlayerBanner_banner_scroll_time, BannerConfig.DURATION);
        mAutoPlay = typedArray.getBoolean(R.styleable.PlayerBanner_banner_is_auto_play,
                BannerConfig.IS_AUTO_PLAY);
        mTitleBackground = typedArray.getColor(R.styleable.PlayerBanner_banner_title_background,
                BannerConfig.TITLE_BACKGROUND);
        mTitleHeight =
                typedArray.getDimensionPixelSize(R.styleable.PlayerBanner_banner_title_height,
                        BannerConfig.TITLE_HEIGHT);
        mTitleTextColor = typedArray.getColor(R.styleable.PlayerBanner_banner_title_text_color,
                BannerConfig.TITLE_TEXT_COLOR);
        mTitleTextSize =
                typedArray.getDimensionPixelSize(R.styleable.PlayerBanner_banner_title_text_size,
                        BannerConfig.TITLE_TEXT_SIZE);
        mLayoutResId =
                typedArray.getResourceId(R.styleable.PlayerBanner_banner_override_layout, mLayoutResId);
        mBannerBackgroundImage =
                typedArray.getResourceId(R.styleable.PlayerBanner_banner_empty_image,
                        R.drawable.no_banner);
        mAllowScroll = typedArray.getBoolean(R.styleable.PlayerBanner_banner_allow_scroll, false);

        mCacheAllPage =
                typedArray.getBoolean(R.styleable.PlayerBanner_banner_cache_all_page, false);

        // 强制播放时长
        mPlayAttrs.forcePlayDuration =
                typedArray.getBoolean(R.styleable.PlayerBanner_banner_force_show_duration, false);

        typedArray.recycle();
    }

    private void initViewPagerScroll() {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            BannerScroller scroller = new BannerScroller(mViewPager.getContext());
            scroller.setDuration(mScrollTime);
            mField.set(mViewPager, scroller);
        } catch (Exception e) {
            Log.e(tag, e.getMessage());
        }
    }
    //endregion

    //region 配置轮播图属性

    /**
     * 是否自动播放
     *
     * @param autoPlay
     * @return
     */
    public PlayerBanner setAutoPlay(boolean autoPlay) {
        this.mAutoPlay = autoPlay;
        return this;
    }

    /**
     * 设置播放时间（默认为图片的时间）
     *
     * @param delayTime
     * @return
     */
    public PlayerBanner setDelayTime(int delayTime) {
        this.mDelayTime = delayTime;
        mPlayAttrs.playDuration = this.mDelayTime;
        return this;
    }

    /**
     * 强制播放时间（视频无论多长，都跟图片一样显示相同的时间）
     *
     * @param forcePlayDuration
     * @return
     */
    public PlayerBanner setForceShowDuration(boolean forcePlayDuration) {
        mPlayAttrs.forcePlayDuration = forcePlayDuration;
        return this;
    }

    /**
     * 设置指示器位置
     *
     * @param type
     * @return
     */
    public PlayerBanner setIndicatorGravity(@BannerConfig.Gravity int type) {
        switch (type) {
            case BannerConfig.LEFT:
                this.mGravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
                break;
            case BannerConfig.CENTER:
                this.mGravity = Gravity.CENTER;
                break;
            case BannerConfig.RIGHT:
                this.mGravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
                break;
        }
        return this;
    }

    public PlayerBanner setBannerAnimation(Class<? extends PageTransformer> transformer) {
        try {
            setPageTransformer(true, transformer.newInstance());
        } catch (Exception e) {
            Log.e(tag, "Please set the PageTransformer class");
        }
        return this;
    }

    /**
     * Set the number of pages that should be retained to either side of the
     * current page in the view hierarchy in an idle state. Pages beyond this
     * limit will be recreated from the mPagerAdapter when needed.
     *
     * @param limit How many pages will be kept offscreen in an idle state.
     * @return Banner
     */
    public PlayerBanner setOffscreenPageLimit(int limit) {
        if (mViewPager != null) {
            mViewPager.setOffscreenPageLimit(limit);
        }
        return this;
    }

    /**
     * Set a {@link PageTransformer} that will be called for each attached page whenever
     * the scroll position is changed. This allows the application to apply custom property
     * transformations to each page, overriding the default sliding look and feel.
     *
     * @param reverseDrawingOrder true if the supplied PageTransformer requires page views
     *                            to be drawn from last to first instead of first to last.
     * @param transformer         PageTransformer that will modify each page's animation properties
     * @return Banner
     */
    public PlayerBanner setPageTransformer(boolean reverseDrawingOrder,
                                           PageTransformer transformer) {
        mViewPager.setPageTransformer(reverseDrawingOrder, transformer);
        return this;
    }

    /**
     * 设置指示器样式
     *
     * @param bannerIndicatorStyle
     * @return
     */
    public PlayerBanner setBannerIndicatorStyle(@BannerConfig.Indicator int bannerIndicatorStyle) {
        this.mBannerIndicatorStyle = bannerIndicatorStyle;
        return this;
    }

    public PlayerBanner setViewPagerScrollable(boolean allowScroll) {
        this.mAllowScroll = allowScroll;
        return this;
    }
    //endregion

    //region 指示器相关代码
    private void applyBannerStyleUI() {
        int visibility = mCount > 1 ? View.VISIBLE : View.GONE;
        switch (mBannerIndicatorStyle) {
            case BannerConfig.CIRCLE_INDICATOR:
                mIndicator.setVisibility(visibility);
                break;
            case BannerConfig.NUM_INDICATOR:
                mNumIndicator.setVisibility(visibility);
                break;
            case BannerConfig.NUM_INDICATOR_TITLE:
                mNumIndicatorInside.setVisibility(visibility);
                applyTitleStyleUI();
                break;
            case BannerConfig.CIRCLE_INDICATOR_TITLE:
                mIndicator.setVisibility(visibility);
                applyTitleStyleUI();
                break;
            case BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE:
                mIndicatorInside.setVisibility(visibility);
                applyTitleStyleUI();
                break;
        }
    }

    private void applyTitleStyleUI() {
        if (mTitles.size() != mBannerData.size()) {
            throw new RuntimeException(
                    "[Banner] --> The number of mTitles and images is different");
        }
        if (mTitleBackground != -1) {
            mTitleView.setBackgroundColor(mTitleBackground);
        }
        if (mTitleHeight != -1) {
            mTitleView.setLayoutParams(
                    new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mTitleHeight));
        }
        if (mTitleTextColor != -1) {
            mBannerTitle.setTextColor(mTitleTextColor);
        }
        if (mTitleTextSize != -1) {
            mBannerTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleTextSize);
        }
        if (mTitles != null && mTitles.size() > 0) {
            mBannerTitle.setText(mTitles.get(0));
            mBannerTitle.setVisibility(View.VISIBLE);
            mTitleView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化指示器
     */
    private void initIndicators() {
        if (mBannerIndicatorStyle == BannerConfig.CIRCLE_INDICATOR
                || mBannerIndicatorStyle == BannerConfig.CIRCLE_INDICATOR_TITLE
                || mBannerIndicatorStyle == BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE) {
            createIndicator();
        } else if (mBannerIndicatorStyle == BannerConfig.NUM_INDICATOR_TITLE) {
            mNumIndicatorInside.setText("1/" + mCount);
        } else if (mBannerIndicatorStyle == BannerConfig.NUM_INDICATOR) {
            mNumIndicator.setText("1/" + mCount);
        }
    }

    /**
     * 设置ImageView缩放方式
     *
     * @param imageView
     */
    private void setImageViewScaleType(View imageView) {
        if (imageView instanceof ImageView) {
            ImageView view = ((ImageView) imageView);
            switch (mScaleType) {
                case 0:
                    view.setScaleType(ScaleType.CENTER);
                    break;
                case 1:
                    view.setScaleType(ScaleType.CENTER_CROP);
                    break;
                case 2:
                    view.setScaleType(ScaleType.CENTER_INSIDE);
                    break;
                case 3:
                    view.setScaleType(ScaleType.FIT_CENTER);
                    break;
                case 4:
                    view.setScaleType(ScaleType.FIT_END);
                    break;
                case 5:
                    view.setScaleType(ScaleType.FIT_START);
                    break;
                case 6:
                    view.setScaleType(ScaleType.FIT_XY);
                    break;
                case 7:
                    view.setScaleType(ScaleType.MATRIX);
                    break;
            }
        }
    }

    /**
     * 创建指示器
     */
    private void createIndicator() {
        mIndicatorImages.clear();
        mIndicator.removeAllViews();
        mIndicatorInside.removeAllViews();
        for (int i = 0; i < mCount; i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(mIndicatorWidth, mIndicatorHeight);
            params.leftMargin = mIndicatorMargin;
            params.rightMargin = mIndicatorMargin;
            if (i == 0) {
                imageView.setImageResource(mIndicatorSelectedResId);
            } else {
                imageView.setImageResource(mIndicatorUnselectedResId);
            }
            mIndicatorImages.add(imageView);
            if (mBannerIndicatorStyle == BannerConfig.CIRCLE_INDICATOR
                    || mBannerIndicatorStyle == BannerConfig.CIRCLE_INDICATOR_TITLE) {
                mIndicator.addView(imageView, params);
            } else if (mBannerIndicatorStyle == BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE) {
                mIndicatorInside.addView(imageView, params);
            }
        }
    }
    //endregion

    /**
     * 设置轮播数据
     *
     * @param data
     * @return
     */
    public PlayerBanner setBannerData(List<? extends BannerData> data) {

        this.mBannerData.clear();
        if (data != null) {
            this.mBannerData.addAll(data);
        }
        this.mCount = mBannerData.size();

        mBannerDataEx.clear();
        if (mCount < 1) {
            mCountEx = 0;
        } else {
            mCountEx = mCount + 2;
            for (int i = 0; i < mCountEx; i++) {
                if (i == 0) {
                    mBannerDataEx.add(mBannerData.get(mCount - 1));
                } else if (i == mCountEx - 1) {
                    mBannerDataEx.add(mBannerData.get(0));
                } else {
                    mBannerDataEx.add(mBannerData.get(i - 1));
                }
            }
        }
        return this;
    }

    /**
     * 设置url形式的轮播数据
     *
     * @param data
     * @return
     */
    public PlayerBanner setBannerDataWithUrls(List<String> data) {
        ArrayList<UrlBannerData> urlBannerData = null;
        if (data != null) {
            urlBannerData = new ArrayList<>();
            for (String url : data) {
                urlBannerData.add(new UrlBannerData(url));
            }
        }
        setBannerData(urlBannerData);
        return this;
    }

    /**
     * 设置标题数据
     *
     * @param titles
     * @return
     */
    public PlayerBanner setBannerTitles(List<String> titles) {
        this.mTitles.clear();
        if (titles != null) {
            this.mTitles.addAll(titles);
        }
        return this;
    }

    /**
     * 更新数据
     *
     * @param bannerData
     * @param titles
     */
    public void update(List<? extends BannerData> bannerData, List<String> titles) {
        // 设置标题数据
        setBannerTitles(titles);
        // 是指轮播数据
        update(bannerData);
    }

    /**
     * 更新数据
     *
     * @param bannerData
     * @param titles
     */
    public void updateWithUrls(List<String> bannerData, List<String> titles) {
        // 设置标题数据
        setBannerTitles(titles);
        // 是指轮播数据
        updateWithUrls(bannerData);
    }

    /**
     * 更新数据
     *
     * @param bannerData
     */
    public void update(List<? extends BannerData> bannerData) {
        setBannerData(bannerData);
        prepare();
        //startPlay();
    }

    /**
     * 更新数据
     *
     * @param bannerData
     */
    public void updateWithUrls(List<String> bannerData) {
        setBannerDataWithUrls(bannerData);
        prepare();
        //startPlay();
    }

    /**
     * 准备
     *
     * @return
     */
    public PlayerBanner prepare() {

        // 先停止
        stopAllBannerPlayerView();

        applyBannerStyleUI();
        initIndicators();

        // 初始化播放控件
        checkAndInitViewCaches();

        if (mCountEx <= 0) {
            mBannerDefaultImage.setVisibility(VISIBLE);
            Log.e(tag, "The image data set is empty.");
        } else {
            mBannerDefaultImage.setVisibility(GONE);
        }

        setViewPagerData();
        return this;
    }

    /**
     * 设置Viewpager数据
     */
    private void setViewPagerData() {

        if (mPagerAdapter == null) {
            mPagerAdapter = new BannerPagerAdapter();
            mViewPager.addOnPageChangeListener(this);
            mViewPager.setAdapter(mPagerAdapter);
        }

        mPagerAdapter.notifyDataSetChanged();

        if (mCacheAllPage) {
            mViewPager.setOffscreenPageLimit(mCount);
        } else {
            mViewPager.setOffscreenPageLimit(1);
        }

        mViewPager.setFocusable(true);

        if (mGravity != -1) {
            mIndicator.setGravity(mGravity);
        }

        if (mAllowScroll) {
            mViewPager.setScrollable(true);
        } else {
            mViewPager.setScrollable(false);
        }

        if (mCount > 0) {
            mCurrentItem = 1;
            mViewPager.setCurrentItem(mCurrentItem, false);
        }
    }

    class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mBannerDataEx.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            BannerData bannerData = mBannerDataEx.get(position);
            BannerPlayerView bannerPlayerView = getBannerPlayerView(position);
            bannerPlayerView.setData(bannerData);

            View view = bannerPlayerView.getSelf();

            container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);

            bannerPlayerView.prepare(); // 准备播放

            if (mBannerListener != null) {
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBannerListener.OnBannerClick(PlayerBanner.this, toRealPosition(position));
                    }
                });
            }
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

            BannerPlayerView view = (BannerPlayerView) object;
            view.stop();
        }
    }

    @Override
    public void onPageSelected(int position) {

        Log.i("ViewPagerTag", "onPageSelected,pos=" + mCurrentItem);
        mCurrentItem = position;
        mPlayAttrs.currentPlayingIndex = position;
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(toRealPosition(position));
        }

        // 首尾两项额外的不播放
        if (position > 0 && position < mCount + 1) {
            // 先暂停所有的
            pauseAllBannerPlayerView(true);
            getBannerPlayerView(position).delayPlay(100);
        }
        // 更新指示器
        updateIndicators(position);
    }

    /**
     * 开始播放
     */
    public void startPlay() {
        mPlayAttrs.playing = true;
        mHandler.removeCallbacks(mPlayCurrentTask);
        mHandler.postDelayed(mPlayCurrentTask, 50);
    }

    /**
     * 停止播放
     */
    public void reprepare() {
        try {
            if (mCacheAllPage) {
                for (int i = 0; i < mCountEx; i++) {
                    mViewCacheList.get(i).prepare();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止播放
     */
    public void stopPlay() {
        mPlayAttrs.playing = false;
        mHandler.removeCallbacks(mPlayCurrentTask);
        mHandler.removeCallbacks(mPlayNextTask);
        stopAllBannerPlayerView();
    }

    /**
     * 释放
     */
    public void releaseBanner() {
        mPlayAttrs.playing = false;
        mHandler.removeCallbacksAndMessages(null);
        // 释放控件
        releaseAllBannerPlayerView();
    }

    /**
     * 获取播放控件
     *
     * @return
     */
    private BannerPlayerView getBannerPlayerView(int index) {
        return mViewCacheList.get(index);
    }

    /**
     * 检查播放控件个数，不够数就补充实例化
     */
    private void checkAndInitViewCaches() {
        while (mViewCacheList.size() < mCountEx) {
            BannerPlayerView view = new BannerPlayerViewImpl(mContext);
            setImageViewScaleType(view.getImageView());
            view.setIndex(mViewCacheList.size());
            view.setConfig(mPlayAttrs, mHandler); // 配置
            view.setListener(new BannerPlayerView.PlayListener() {
                @Override
                public void onPlayComplete(BannerPlayerView view) {

                    if (mPlayAttrs.playing) {
                        mHandler.postDelayed(mPlayNextTask, 100);
                    }
                }

                @Override
                public void onPlayError(BannerPlayerView view, Throwable throwable) {
                    if (mPlayAttrs.playing) {
                        mHandler.postDelayed(mPlayNextTask, mErrorDelayTime);
                    }
                }
            });
            mViewCacheList.add(view);
        }

        for (int i = 0; i < mBannerDataEx.size(); i++) {
            mViewCacheList.get(i).setData(mBannerDataEx.get(i));
        }
    }

    /**
     * 停止轮播控件
     */
    private void stopAllBannerPlayerView() {
        for (BannerPlayerView view : mViewCacheList) {
            view.stop();
        }
    }

    /**
     * 暂停
     */
    private void pauseAllBannerPlayerView(boolean seek0) {
        for (BannerPlayerView view : mViewCacheList) {
            view.pause(seek0);
        }
    }

    /**
     * 释放所有的播放控件
     */
    private void releaseAllBannerPlayerView() {
        Iterator<BannerPlayerView> iterator = mViewCacheList.iterator();
        while (iterator.hasNext()) {
            BannerPlayerView next = iterator.next();
            next.release();
            iterator.remove();
        }
    }

    /**
     * 执行播放下一个
     */
    private final Runnable mPlayNextTask = new Runnable() {
        @Override
        public void run() {
            int pos = (mCurrentItem + 1) % mCountEx;
            mViewPager.setCurrentItem(pos, true);
        }
    };

    /**
     * 执行播放当前条目
     */
    private final Runnable mPlayCurrentTask = new Runnable() {
        @Override
        public void run() {
            if (mCountEx > 0) {
                try {
                    BannerPlayerView playerView = getBannerPlayerView(mCurrentItem);
                    if (!playerView.isPlaying()) {
                        playerView.play();
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }
    };

    @Override
    public void onPageScrollStateChanged(int state) {

        Log.i("ViewPagerTag", "onPageScrollStateChanged,state=" + state + ",pos=" + mCurrentItem);

        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }

        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE://No operation
            case ViewPager.SCROLL_STATE_DRAGGING://prepare Sliding
                if (mCurrentItem == 0) {
                    mCurrentItem = mCount;
                    mViewPager.setCurrentItem(mCurrentItem, false);
                } else if (mCurrentItem == mCount + 1) {
                    mCurrentItem = 1;
                    mViewPager.setCurrentItem(mCurrentItem, false);
                }
                break;
            case ViewPager.SCROLL_STATE_SETTLING://end Sliding
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrolled(toRealPosition(position), positionOffset,
                    positionOffsetPixels);
        }
    }

    /**
     * 更新指示器
     *
     * @param position
     */
    private void updateIndicators(int position) {
        if (mCount > 0) {

            int realDataPos = getDataPosition(position);

            if (mBannerIndicatorStyle == BannerConfig.CIRCLE_INDICATOR
                    || mBannerIndicatorStyle == BannerConfig.CIRCLE_INDICATOR_TITLE
                    || mBannerIndicatorStyle == BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE) {

                for (int i = 0; i < mIndicatorImages.size(); i++) {
                    if (i == realDataPos) {
                        mIndicatorImages.get(i).setImageResource(mIndicatorSelectedResId);
                    } else {
                        mIndicatorImages.get(i).setImageResource(mIndicatorUnselectedResId);
                    }
                }
            }

            switch (mBannerIndicatorStyle) {
                case BannerConfig.CIRCLE_INDICATOR:
                    break;
                case BannerConfig.NUM_INDICATOR:
                    mNumIndicator.setText((realDataPos + 1) + "/" + mCount);
                    break;
                case BannerConfig.NUM_INDICATOR_TITLE:
                    mNumIndicatorInside.setText((realDataPos + 1) + "/" + mCount);
                    mBannerTitle.setText(mTitles.get(realDataPos));
                    break;
                case BannerConfig.CIRCLE_INDICATOR_TITLE:
                    mBannerTitle.setText(mTitles.get(realDataPos));
                    break;
                case BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE:
                    mBannerTitle.setText(mTitles.get(realDataPos));
                    break;
            }
        }
    }

    /**
     * 获取数据的下标（没有前后附加两项的）
     *
     * @param position
     * @return
     */
    private int getDataPosition(int position) {
        int dataPos;
        if (position == 0) {
            dataPos = mCount - 1;
        } else if (position == mCount + 1) {
            dataPos = 0;
        } else {
            dataPos = position - 1;
        }
        return dataPos;
    }

    /**
     * 返回真实的位置
     *
     * @param position
     * @return 下标从0开始
     */
    public int toRealPosition(int position) {
        int realPosition = (position - 1) % mCount;
        if (realPosition < 0) {
            realPosition += mCount;
        }
        return realPosition;
    }

    public PlayerBanner setOnBannerClickListener(OnBanneClickrListener listener) {
        this.mBannerListener = listener;
        return this;
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    public void selectPosition(int position, boolean smoothScroll) {
        mViewPager.setCurrentItem(position, smoothScroll);
        mHandler.postDelayed(mPlayCurrentTask, 50);
    }
    public List<BannerData> getBannerData(){
        return mBannerData;
    }
}
