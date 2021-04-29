package com.pizarro.gromoreloadlib;

import android.app.Activity;
import android.widget.FrameLayout;

import com.bytedance.msdk.adapter.pangle.PangleNetworkRequestInfo;
import com.bytedance.msdk.api.AdError;
import com.bytedance.msdk.api.AdSlot;
import com.bytedance.msdk.api.TTNetworkRequestInfo;
import com.bytedance.msdk.api.splash.TTSplashAd;
import com.bytedance.msdk.api.splash.TTSplashAdListener;
import com.bytedance.msdk.api.splash.TTSplashAdLoadCallback;
import com.pizarro.gromoreloadlib.listener.OnAdClickEvent;
import com.pizarro.gromoreloadlib.listener.OnAdLoadErrorEvent;

/**
 * Created by Irving
 */
public class MSplashAdHolder {
    private static final String TAG = MSplashAdHolder.class.getSimpleName();

    private static MSplashAdHolder sMSplashAdHolder;

    public OnAdClickEvent mOnAdClickEvent;
    public OnAdLoadErrorEvent mOnAdLoadErrorEvent;

    public void setOnAdLoadErrorEvent(OnAdLoadErrorEvent onAdLoadErrorEvent) {
        mOnAdLoadErrorEvent = onAdLoadErrorEvent;
    }

    public void setOnAdClickEvent(OnAdClickEvent onAdClickEvent) {
        mOnAdClickEvent = onAdClickEvent;
    }

    public static MSplashAdHolder getInstance() {
        if (sMSplashAdHolder == null) {
            sMSplashAdHolder = new MSplashAdHolder();
        }
        return sMSplashAdHolder;
    }

    public void loadSplashAd(Activity activity, String placementId, int width, int height, FrameLayout frameLayout, TTSplashAdLoadCallback ttSplashAdLoadCallback, TTSplashAdListener ttSplashAdListener) {
        TTSplashAd ttSplashAd = new TTSplashAd(activity, placementId);
        AdSlot adSlot = new AdSlot.Builder().setImageAdSize(width, height).build();

        //注：自定义兜底方案 选择使用
        TTNetworkRequestInfo ttNetworkRequestInfo;
        //穿山甲兜底
        ttNetworkRequestInfo = new PangleNetworkRequestInfo("5149608", "887448802");

        ttSplashAd.loadAd(adSlot, ttNetworkRequestInfo, new TTSplashAdLoadCallback() {
            @Override
            public void onSplashAdLoadFail(AdError adError) {
                if (ttSplashAdLoadCallback != null) {
                    ttSplashAdLoadCallback.onSplashAdLoadFail(adError);
                }
                if (mOnAdLoadErrorEvent != null){
                    mOnAdLoadErrorEvent.onAdLoadError("splash",adError);
                }
            }

            @Override
            public void onSplashAdLoadSuccess() {
                if (ttSplashAdLoadCallback != null) {
                    ttSplashAdLoadCallback.onSplashAdLoadSuccess();
                }
                ttSplashAd.setTTAdSplashListener(new TTSplashAdListener() {
                    @Override
                    public void onAdClicked() {
                        if (ttSplashAdListener != null) {
                            ttSplashAdListener.onAdClicked();
                        }
                        if (mOnAdClickEvent != null) {
                            mOnAdClickEvent.onAdClick();
                        }
                    }

                    @Override
                    public void onAdShow() {
                        if (ttSplashAdListener != null) {
                            ttSplashAdListener.onAdShow();
                        }
                    }

                    @Override
                    public void onAdSkip() {
                        if (ttSplashAdListener != null) {
                            ttSplashAdListener.onAdSkip();
                        }
                    }

                    @Override
                    public void onAdDismiss() {
                        if (ttSplashAdListener != null) {
                            ttSplashAdListener.onAdDismiss();
                        }
                    }
                });
                ttSplashAd.showAd(frameLayout);
            }

            @Override
            public void onAdLoadTimeout() {
                if (ttSplashAdLoadCallback != null) {
                    ttSplashAdLoadCallback.onAdLoadTimeout();
                }
            }
        }, 3000);
    }
}
