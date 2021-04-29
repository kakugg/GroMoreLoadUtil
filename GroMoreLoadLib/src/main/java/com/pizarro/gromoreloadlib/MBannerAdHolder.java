package com.pizarro.gromoreloadlib;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;

import com.bytedance.msdk.api.AdError;
import com.bytedance.msdk.api.AdSlot;
import com.bytedance.msdk.api.TTAdSize;
import com.bytedance.msdk.api.banner.TTAdBannerListener;
import com.bytedance.msdk.api.banner.TTAdBannerLoadCallBack;
import com.bytedance.msdk.api.banner.TTBannerViewAd;
import com.pizarro.gromoreloadlib.listener.OnAdClickEvent;
import com.pizarro.gromoreloadlib.listener.OnAdLoadErrorEvent;

/**
 * Created by Irving
 */
public class MBannerAdHolder {
    private static final String TAG = MBannerAdHolder.class.getSimpleName();

    private static MBannerAdHolder sMBannerAdHolder;

    public OnAdClickEvent mOnAdClickEvent;
    public OnAdLoadErrorEvent mOnAdLoadErrorEvent;

    public void setOnAdLoadErrorEvent(OnAdLoadErrorEvent onAdLoadErrorEvent) {
        mOnAdLoadErrorEvent = onAdLoadErrorEvent;
    }

    public void setOnAdClickEvent(OnAdClickEvent onAdClickEvent) {
        mOnAdClickEvent = onAdClickEvent;
    }

    private MBannerAdHolder() {
    }

    public static MBannerAdHolder getInstance() {
        if (sMBannerAdHolder == null) {
            sMBannerAdHolder = new MBannerAdHolder();
        }
        return sMBannerAdHolder;
    }

    public void loadBannerAd(Activity activity, String placementId, FrameLayout frameLayout, int width, int height, TTAdBannerLoadCallBack ttAdBannerLoadCallBack, TTAdBannerListener ttAdBannerListener) {
        TTBannerViewAd ttBannerViewAd = new TTBannerViewAd(activity, placementId);
        ttBannerViewAd.setRefreshTime(10);
        ttBannerViewAd.setAllowShowCloseBtn(true);
        ttBannerViewAd.setTTAdBannerListener(new TTAdBannerListener() {
            @Override
            public void onAdOpened() {
                if (ttAdBannerListener != null) {
                    ttAdBannerListener.onAdOpened();
                }
            }

            @Override
            public void onAdLeftApplication() {
                if (ttAdBannerListener != null) {
                    ttAdBannerListener.onAdLeftApplication();
                }
            }

            @Override
            public void onAdClosed() {
                if (ttAdBannerListener != null) {
                    ttAdBannerListener.onAdClosed();
                }
            }

            @Override
            public void onAdClicked() {
                if (ttAdBannerListener != null) {
                    ttAdBannerListener.onAdClicked();
                }
                if (mOnAdClickEvent != null) {
                    mOnAdClickEvent.onAdClick();
                }
            }

            @Override
            public void onAdShow() {
                if (ttAdBannerListener != null) {
                    ttAdBannerListener.onAdShow();
                }
            }
        });

        AdSlot adSlot = new AdSlot.Builder()
                .setAdStyleType(AdSlot.TYPE_EXPRESS_AD)
                .setBannerSize(TTAdSize.BANNER_CUSTOME)
                .setImageAdSize(width, height)
                .build();

        ttBannerViewAd.loadAd(adSlot, new TTAdBannerLoadCallBack() {
            @Override
            public void onAdFailedToLoad(AdError adError) {
                if (ttAdBannerLoadCallBack != null) {
                    ttAdBannerLoadCallBack.onAdFailedToLoad(adError);
                }
            }

            @Override
            public void onAdLoaded() {
                if (ttAdBannerLoadCallBack != null) {
                    ttAdBannerLoadCallBack.onAdLoaded();
                }

                if (ttBannerViewAd != null) {
                    View view = ttBannerViewAd.getBannerView();
                    if (view != null) {
                        if (frameLayout.getVisibility() != View.VISIBLE) {
                            frameLayout.setVisibility(View.VISIBLE);
                        }

                        frameLayout.removeAllViews();
                        frameLayout.addView(view);
                    }
                }
            }
        });

    }

}
