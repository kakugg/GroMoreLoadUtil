package com.pizarro.gromoreloadlib;

import android.app.Activity;

import com.bytedance.msdk.api.AdError;
import com.bytedance.msdk.api.AdSlot;
import com.bytedance.msdk.api.TTAdConstant;
import com.bytedance.msdk.api.fullVideo.TTFullVideoAd;
import com.bytedance.msdk.api.fullVideo.TTFullVideoAdListener;
import com.bytedance.msdk.api.fullVideo.TTFullVideoAdLoadCallback;
import com.pizarro.gromoreloadlib.listener.OnAdClickEvent;

/**
 * Created by Irving
 */
public class MFullScreenVideoAdHolder {
    private static final String TAG = MFullScreenVideoAdHolder.class.getSimpleName();

    private String userId;

    private static MFullScreenVideoAdHolder sMFullScreenVideoAdHolder;

    public OnAdClickEvent mOnAdClickEvent;

    public void setOnAdClickEvent(OnAdClickEvent onAdClickEvent) {
        mOnAdClickEvent = onAdClickEvent;
    }
    private MFullScreenVideoAdHolder(String userId) {
        this.userId = userId;
    }

    public static MFullScreenVideoAdHolder getInstance(String userId) {
        if (sMFullScreenVideoAdHolder == null) {
            sMFullScreenVideoAdHolder = new MFullScreenVideoAdHolder(userId);
        }
        return sMFullScreenVideoAdHolder;
    }

    public void loadFullVideoAd(Activity activity, String placementId, TTFullVideoAdLoadCallback ttFullVideoAdLoadCallback, TTFullVideoAdListener ttFullVideoAdListener) {
        TTFullVideoAd ttInterstitialAd = new TTFullVideoAd(activity, placementId);
        AdSlot adSlot = new AdSlot.Builder()
                .setAdStyleType(AdSlot.TYPE_EXPRESS_AD)//AdSlot.TYPE_EXPRESS_AD 标识pangle使用动态模板全屏视频;AdSlot.TYPE_NATIVE_AD:使用原生全屏视频
                .setTTVideoOption(VideoOptionUtil.getTTVideoOption()) //设置声音控制
                .setUserID(userId)//用户id,必传参数
                .setOrientation(TTAdConstant.VERTICAL) //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                .build();

        ttInterstitialAd.loadFullAd(adSlot, new TTFullVideoAdLoadCallback() {
            @Override
            public void onFullVideoLoadFail(AdError adError) {
                if (ttFullVideoAdLoadCallback != null) {
                    ttFullVideoAdLoadCallback.onFullVideoLoadFail(adError);
                }
                ErrorUploadUtil.getInstance().upLoadSdkError(adError.message,adError.code,adError.thirdSdkErrorCode,adError.thirdSdkErrorMessage);
            }

            @Override
            public void onFullVideoAdLoad() {
                if (ttFullVideoAdLoadCallback != null) {
                    ttFullVideoAdLoadCallback.onFullVideoAdLoad();
                }
            }

            @Override
            public void onFullVideoCached() {
                if (ttFullVideoAdLoadCallback != null) {
                    ttFullVideoAdLoadCallback.onFullVideoCached();
                }
                ttInterstitialAd.showFullAd(activity, new TTFullVideoAdListener() {
                    @Override
                    public void onFullVideoAdShow() {
                        if (ttFullVideoAdListener != null) {
                            ttFullVideoAdListener.onFullVideoAdShow();
                        }
                    }

                    @Override
                    public void onFullVideoAdClick() {
                        if (ttFullVideoAdListener != null) {
                            ttFullVideoAdListener.onFullVideoAdClick();
                        }
                        if (mOnAdClickEvent != null) {
                            mOnAdClickEvent.onAdClick();
                        }
                    }

                    @Override
                    public void onFullVideoAdClosed() {
                        if (ttFullVideoAdListener != null) {
                            ttFullVideoAdListener.onFullVideoAdClosed();
                        }
                    }

                    @Override
                    public void onVideoComplete() {
                        if (ttFullVideoAdListener != null) {
                            ttFullVideoAdListener.onVideoComplete();
                        }
                    }

                    @Override
                    public void onVideoError() {
                        if (ttFullVideoAdListener != null) {
                            ttFullVideoAdListener.onVideoError();
                        }
                    }

                    @Override
                    public void onSkippedVideo() {
                        if (ttFullVideoAdListener != null) {
                            ttFullVideoAdListener.onSkippedVideo();
                        }
                    }
                });
            }
        });
    }
}
