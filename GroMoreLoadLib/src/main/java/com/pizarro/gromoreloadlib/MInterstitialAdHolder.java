package com.pizarro.gromoreloadlib;

import android.app.Activity;

import com.bytedance.msdk.api.AdError;
import com.bytedance.msdk.api.AdSlot;
import com.bytedance.msdk.api.interstitial.TTInterstitialAd;
import com.bytedance.msdk.api.interstitial.TTInterstitialAdListener;
import com.bytedance.msdk.api.interstitial.TTInterstitialAdLoadCallback;
import com.pizarro.gromoreloadlib.listener.OnAdClickEvent;
import com.pizarro.gromoreloadlib.listener.OnAdLoadErrorEvent;

/**
 * Created by Irving
 */
public class MInterstitialAdHolder {
    private static final String TAG = MInterstitialAdHolder.class.getSimpleName();

    public OnAdClickEvent mOnAdClickEvent;
    public OnAdLoadErrorEvent mOnAdLoadErrorEvent;

    public void setOnAdLoadErrorEvent(OnAdLoadErrorEvent onAdLoadErrorEvent) {
        mOnAdLoadErrorEvent = onAdLoadErrorEvent;
    }

    public void setOnAdClickEvent(OnAdClickEvent onAdClickEvent) {
        mOnAdClickEvent = onAdClickEvent;
    }

    private static MInterstitialAdHolder sMInterstitialAdHolder;

    private MInterstitialAdHolder() {
    }

    public static MInterstitialAdHolder getInstance() {
        if (sMInterstitialAdHolder == null) {
            sMInterstitialAdHolder = new MInterstitialAdHolder();
        }
        return sMInterstitialAdHolder;
    }

    public void loadInterstitialAd(Activity activity, String placementId, TTInterstitialAdLoadCallback ttInterstitialAdLoadCallback) {
        TTInterstitialAd ttInterstitialAd = new TTInterstitialAd(activity, placementId);
        AdSlot adSlot = new AdSlot.Builder()
                .setAdStyleType(AdSlot.TYPE_EXPRESS_AD) // 注意：插屏暂时支持模版类型，必须手动设置为AdSlot.TYPE_EXPRESS_AD
                .setImageAdSize(600, 600) //根据广告平台选择的尺寸（目前该比例规格仅对穿山甲SDK生效，插屏广告支持的广告尺寸：  1:1, 3:2, 2:3）
                .build();

        ttInterstitialAd.setTTAdInterstitialListener(new TTInterstitialAdListener() {
            @Override
            public void onInterstitialShow() {

            }

            @Override
            public void onInterstitialAdClick() {
                if (mOnAdClickEvent != null) {
                    mOnAdClickEvent.onAdClick();
                }
            }

            @Override
            public void onInterstitialClosed() {

            }

            @Override
            public void onAdOpened() {

            }

            @Override
            public void onAdLeftApplication() {

            }
        });

        //请求广告，调用插屏广告异步请求接口
        ttInterstitialAd.loadAd(adSlot, new TTInterstitialAdLoadCallback() {
            @Override
            public void onInterstitialLoadFail(AdError adError) {
                if (ttInterstitialAdLoadCallback != null) {
                    ttInterstitialAdLoadCallback.onInterstitialLoadFail(adError);
                }
            }

            @Override
            public void onInterstitialLoad() {
                if (ttInterstitialAdLoadCallback != null) {
                    ttInterstitialAdLoadCallback.onInterstitialLoad();
                }
                ttInterstitialAd.showAd(activity);
            }
        });
    }
}
