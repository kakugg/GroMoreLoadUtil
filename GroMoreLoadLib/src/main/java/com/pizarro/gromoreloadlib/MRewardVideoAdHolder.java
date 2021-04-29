package com.pizarro.gromoreloadlib;

import android.app.Activity;

import com.bytedance.msdk.api.AdError;
import com.bytedance.msdk.api.AdSlot;
import com.bytedance.msdk.api.TTAdConstant;
import com.bytedance.msdk.api.TTVideoOption;
import com.bytedance.msdk.api.reward.RewardItem;
import com.bytedance.msdk.api.reward.TTRewardAd;
import com.bytedance.msdk.api.reward.TTRewardedAdListener;
import com.bytedance.msdk.api.reward.TTRewardedAdLoadCallback;
import com.pizarro.gromoreloadlib.listener.OnAdClickEvent;

/**
 * Created by Irving
 */
public class MRewardVideoAdHolder {
    private static final String TAG = MRewardVideoAdHolder.class.getSimpleName();

    private static MRewardVideoAdHolder sMRewardVideoAdHolder;

    private String mUserId;

    public OnAdClickEvent mOnAdClickEvent;

    public void setOnAdClickEvent(OnAdClickEvent onAdClickEvent) {
        mOnAdClickEvent = onAdClickEvent;
    }

    public MRewardVideoAdHolder(String userId) {
        mUserId = userId;
    }

    public static MRewardVideoAdHolder getInstance(String userId) {
        if (sMRewardVideoAdHolder == null) {
            sMRewardVideoAdHolder = new MRewardVideoAdHolder(userId);
        }
        return sMRewardVideoAdHolder;
    }

    public void loadRewardVideoAd(Activity activity,
                                  String placementId,
                                  TTRewardedAdLoadCallback ttRewardedAdLoadCallback,
                                  TTRewardedAdListener ttRewardedAdListener) {
        TTRewardAd ttRewardVideoAd = new TTRewardAd(activity, placementId);

        TTVideoOption videoOption = new TTVideoOption.Builder()
                .setMuted(false)//对所有SDK的激励广告生效，除需要在平台配置的SDK，如穿山甲SDK
                .setAdmobAppVolume(0f)//配合Admob的声音大小设置[0-1]
                .build();
        AdSlot adSlot = new AdSlot.Builder()
                .setTTVideoOption(videoOption)
                .setAdStyleType(AdSlot.TYPE_EXPRESS_AD)
                .setRewardName("金币")
                .setRewardAmount(3)
                .setUserID(mUserId)
                .setOrientation(TTAdConstant.VERTICAL)
                .build();

        ttRewardVideoAd.loadRewardAd(adSlot, new TTRewardedAdLoadCallback() {
            @Override
            public void onRewardVideoLoadFail(AdError adError) {
                if (ttRewardedAdLoadCallback != null) {
                    ttRewardedAdLoadCallback.onRewardVideoLoadFail(adError);
                }
                ErrorUploadUtil.getInstance().upLoadSdkError(adError.message, adError.code, adError.thirdSdkErrorCode, adError.thirdSdkErrorMessage);
            }

            @Override
            public void onRewardVideoAdLoad() {
                if (ttRewardedAdLoadCallback != null) {
                    ttRewardedAdLoadCallback.onRewardVideoAdLoad();
                }
            }

            @Override
            public void onRewardVideoCached() {
                if (ttRewardedAdLoadCallback != null) {
                    ttRewardedAdLoadCallback.onRewardVideoCached();
                }

                ttRewardVideoAd.showRewardAd(activity, new TTRewardedAdListener() {
                    @Override
                    public void onRewardedAdShow() {
                        if (ttRewardedAdListener != null) {
                            ttRewardedAdListener.onRewardedAdShow();
                        }
                    }

                    @Override
                    public void onRewardClick() {
                        if (ttRewardedAdListener != null) {
                            ttRewardedAdListener.onRewardClick();
                        }
                        if (mOnAdClickEvent != null) {
                            mOnAdClickEvent.onAdClick();
                        }
                    }

                    @Override
                    public void onRewardedAdClosed() {
                        if (ttRewardedAdListener != null) {
                            ttRewardedAdListener.onRewardedAdClosed();
                        }
                    }

                    @Override
                    public void onVideoComplete() {
                        if (ttRewardedAdListener != null) {
                            ttRewardedAdListener.onVideoComplete();
                        }
                    }

                    @Override
                    public void onVideoError() {
                        if (ttRewardedAdListener != null) {
                            ttRewardedAdListener.onVideoError();
                        }
                    }

                    @Override
                    public void onRewardVerify(RewardItem rewardItem) {
                        if (ttRewardedAdListener != null) {
                            ttRewardedAdListener.onRewardVerify(rewardItem);
                        }
                    }

                    @Override
                    public void onSkippedVideo() {
                        if (ttRewardedAdListener != null) {
                            ttRewardedAdListener.onSkippedVideo();
                        }
                    }
                });

            }
        });
    }
}
