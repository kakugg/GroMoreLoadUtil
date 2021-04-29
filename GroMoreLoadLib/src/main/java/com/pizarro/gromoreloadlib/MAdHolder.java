package com.pizarro.gromoreloadlib;

import android.app.Activity;
import android.widget.FrameLayout;

import com.bytedance.msdk.api.fullVideo.TTFullVideoAdListener;
import com.bytedance.msdk.api.fullVideo.TTFullVideoAdLoadCallback;
import com.bytedance.msdk.api.nativeAd.TTNativeAdLoadCallback;
import com.bytedance.msdk.api.reward.TTRewardedAdListener;
import com.bytedance.msdk.api.reward.TTRewardedAdLoadCallback;
import com.bytedance.msdk.api.splash.TTSplashAdListener;
import com.bytedance.msdk.api.splash.TTSplashAdLoadCallback;
import com.pizarro.gromoreloadlib.listener.OnAdClickEvent;
import com.pizarro.gromoreloadlib.listener.OnAdLoadErrorEvent;
import com.pizarro.gromoreloadlib.model.NativeExpressPlacementModel;

/**
 * Created by Irving
 */
public class MAdHolder {
    private static final String TAG = MAdHolder.class.getSimpleName();

    private static MAdHolder sMAdHolder;

    private String mUserId = "unknown";

    private boolean isAdOpened = true;

    public OnAdClickEvent mOnAdClickEvent;
    public OnAdLoadErrorEvent mOnAdLoadErrorEvent;

    public void init() {

    }

    public void setOnAdClickEvent(OnAdClickEvent onAdClickEvent) {
        mOnAdClickEvent = onAdClickEvent;
        MBannerAdHolder.getInstance().setOnAdClickEvent(mOnAdClickEvent);
        MInterstitialAdHolder.getInstance().setOnAdClickEvent(mOnAdClickEvent);
        MRewardVideoAdHolder.getInstance(mUserId).setOnAdClickEvent(mOnAdClickEvent);
        MSplashAdHolder.getInstance().setOnAdClickEvent(mOnAdClickEvent);
        MFullScreenVideoAdHolder.getInstance(mUserId).setOnAdClickEvent(mOnAdClickEvent);
        MNativeAdHolder.getInstance().setOnAdClickEvent(mOnAdClickEvent);
    }

    public void setOnAdLoadErrorEvent(OnAdLoadErrorEvent onAdLoadErrorEvent) {
        mOnAdLoadErrorEvent = onAdLoadErrorEvent;
        MBannerAdHolder.getInstance().setOnAdLoadErrorEvent(mOnAdLoadErrorEvent);
        MInterstitialAdHolder.getInstance().setOnAdLoadErrorEvent(mOnAdLoadErrorEvent);
        MRewardVideoAdHolder.getInstance(mUserId).setOnAdLoadErrorEvent(mOnAdLoadErrorEvent);
        MSplashAdHolder.getInstance().setOnAdLoadErrorEvent(mOnAdLoadErrorEvent);
        MFullScreenVideoAdHolder.getInstance(mUserId).setOnAdLoadErrorEvent(mOnAdLoadErrorEvent);
        MNativeAdHolder.getInstance().setOnAdLoadErrorEvent(mOnAdLoadErrorEvent);
    }


    public void setAdOpened(boolean adOpened) {
        isAdOpened = adOpened;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    private MAdHolder() {
    }

    public static MAdHolder getInstance() {
        if (sMAdHolder == null) {
            sMAdHolder = new MAdHolder();
        }
        return sMAdHolder;
    }

    /**
     * 加载开屏页广告
     *
     * @param activity
     * @param placementId
     * @param width
     * @param height
     * @param frameLayout
     * @param ttSplashAdLoadCallback
     * @param ttSplashAdListener
     */
    public void loadSplashAd(Activity activity,
                             String placementId,
                             int width,
                             int height,
                             FrameLayout frameLayout,
                             TTSplashAdLoadCallback ttSplashAdLoadCallback,
                             TTSplashAdListener ttSplashAdListener) {
        if (!isAdOpened) {
            if (ttSplashAdLoadCallback != null) {
                ttSplashAdLoadCallback.onSplashAdLoadFail(null);
            }
            return;
        }

        MSplashAdHolder.getInstance().loadSplashAd(activity,
                placementId,
                width,
                height,
                frameLayout,
                ttSplashAdLoadCallback,
                ttSplashAdListener);
    }

    /**
     * 加载激励视频
     *
     * @param activity
     * @param placementId
     * @param ttRewardedAdLoadCallback
     * @param ttRewardedAdListener
     */
    public void loadRewardVideoAd(Activity activity,
                                  String placementId,
                                  TTRewardedAdLoadCallback ttRewardedAdLoadCallback,
                                  TTRewardedAdListener ttRewardedAdListener) {
        if (!isAdOpened) {
//            if (ttRewardedAdLoadCallback != null){
//                ttRewardedAdLoadCallback.onRewardVideoLoadFail(null);
//            }
            return;
        }

        MRewardVideoAdHolder.getInstance(mUserId).loadRewardVideoAd(activity,
                placementId,
                ttRewardedAdLoadCallback,
                ttRewardedAdListener);
    }

    /**
     * 加载信息流广告
     *
     * @param activity
     * @param frameLayout
     * @param model
     * @param ttNativeAdLoadCallback
     */
    public void loadNativeExpressAd(Activity activity,
                                    FrameLayout frameLayout,
                                    NativeExpressPlacementModel model,
                                    TTNativeAdLoadCallback ttNativeAdLoadCallback) {
        if (!isAdOpened) {
            return;
        }

        MNativeAdHolder.getInstance().loadNativeAd(activity, frameLayout, model, ttNativeAdLoadCallback, true);
    }

    /**
     * 加载全屏视频广告
     *
     * @param activity
     * @param placementId
     * @param ttFullVideoAdLoadCallback
     * @param ttFullVideoAdListener
     */
    public void loadFullScreenVideoAd(Activity activity, String placementId, TTFullVideoAdLoadCallback ttFullVideoAdLoadCallback, TTFullVideoAdListener ttFullVideoAdListener) {
        if (!isAdOpened) {
            return;
        }

        MFullScreenVideoAdHolder.getInstance(mUserId).loadFullVideoAd(activity, placementId, ttFullVideoAdLoadCallback, ttFullVideoAdListener);
    }

}
