package com.dlc.bannerplayer;

import android.support.v4.view.ViewPager.PageTransformer;
import com.dlc.bannerplayer.transformer.AccordionTransformer;
import com.dlc.bannerplayer.transformer.BackgroundToForegroundTransformer;
import com.dlc.bannerplayer.transformer.CubeInTransformer;
import com.dlc.bannerplayer.transformer.CubeOutTransformer;
import com.dlc.bannerplayer.transformer.DefaultTransformer;
import com.dlc.bannerplayer.transformer.DepthPageTransformer;
import com.dlc.bannerplayer.transformer.FlipHorizontalTransformer;
import com.dlc.bannerplayer.transformer.FlipVerticalTransformer;
import com.dlc.bannerplayer.transformer.ForegroundToBackgroundTransformer;
import com.dlc.bannerplayer.transformer.RotateDownTransformer;
import com.dlc.bannerplayer.transformer.RotateUpTransformer;
import com.dlc.bannerplayer.transformer.ScaleInOutTransformer;
import com.dlc.bannerplayer.transformer.StackTransformer;
import com.dlc.bannerplayer.transformer.TabletTransformer;
import com.dlc.bannerplayer.transformer.ZoomInTransformer;
import com.dlc.bannerplayer.transformer.ZoomOutSlideTransformer;
import com.dlc.bannerplayer.transformer.ZoomOutTranformer;

public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
