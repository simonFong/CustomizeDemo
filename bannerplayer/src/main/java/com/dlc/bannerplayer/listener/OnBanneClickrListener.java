package com.dlc.bannerplayer.listener;

import com.dlc.bannerplayer.PlayerBanner;

public interface OnBanneClickrListener {

    /**
     * 下标从0开始
     *
     * @param banner
     * @param position
     */
    void OnBannerClick(PlayerBanner banner, int position);
}
