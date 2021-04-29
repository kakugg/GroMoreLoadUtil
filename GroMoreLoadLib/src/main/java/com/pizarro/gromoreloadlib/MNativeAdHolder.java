package com.pizarro.gromoreloadlib;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;

import com.bytedance.msdk.api.AdError;
import com.bytedance.msdk.api.AdSlot;
import com.bytedance.msdk.api.TTAdSize;
import com.bytedance.msdk.api.TTDislikeCallback;
import com.bytedance.msdk.api.nativeAd.TTNativeAd;
import com.bytedance.msdk.api.nativeAd.TTNativeAdLoadCallback;
import com.bytedance.msdk.api.nativeAd.TTNativeExpressAdListener;
import com.bytedance.msdk.api.nativeAd.TTUnifiedNativeAd;
import com.pizarro.gromoreloadlib.listener.OnAdClickEvent;
import com.pizarro.gromoreloadlib.model.NativeExpressPlacementModel;

import java.util.List;

/**
 * Created by Irving
 */
public class MNativeAdHolder {
    private static final String TAG = MNativeAdHolder.class.getSimpleName();

    public static MNativeAdHolder sMNativeAdHolder;

    public OnAdClickEvent mOnAdClickEvent;

    public void setOnAdClickEvent(OnAdClickEvent onAdClickEvent) {
        mOnAdClickEvent = onAdClickEvent;
    }

    public static MNativeAdHolder getInstance() {
        if (sMNativeAdHolder == null) {
            sMNativeAdHolder = new MNativeAdHolder();
        }
        return sMNativeAdHolder;
    }

    public void loadNativeAd(Activity activity,
                             FrameLayout frameLayout,
                             NativeExpressPlacementModel model,
                             TTNativeAdLoadCallback ttNativeAdLoadCallback,
                             boolean showAd) {
        TTUnifiedNativeAd ttUnifiedNativeAd = new TTUnifiedNativeAd(activity, model.getPlacementId());

        AdSlot adSlot = new AdSlot.Builder()
                .setAdStyleType(AdSlot.TYPE_EXPRESS_AD)
                .setAdCount(1)
                .setImageAdSize(model.getWidth(), TTAdSize.AUTO_HEIGHT)// 必选参数 单位dp ，详情见上面备注解释
                .build();

        ttUnifiedNativeAd.loadAd(adSlot, new TTNativeAdLoadCallback() {
            @Override
            public void onAdLoaded(List<TTNativeAd> list) {
                if (ttNativeAdLoadCallback != null) {
                    ttNativeAdLoadCallback.onAdLoaded(list);
                }

                if (!showAd) {
                    return;
                }


                if (list != null && list.size() > 0) {
                    TTNativeAd ad = list.get(0);

                    if (ad.isExpressAd()) {
                        if (frameLayout.getVisibility() != View.VISIBLE) {
                            frameLayout.setVisibility(View.VISIBLE);
                        }

                        if (ad.hasDislike()) {
                            ad.setDislikeCallback(activity, new TTDislikeCallback() {
                                @Override
                                public void onSelected(int i, String s) {
                                    frameLayout.removeAllViews();
                                    frameLayout.setVisibility(View.GONE);
                                }

                                @Override
                                public void onCancel() {

                                }

                                @Override
                                public void onRefuse() {

                                }
                            });
                        }

                        ad.setTTNativeAdListener(new TTNativeExpressAdListener() {
                            @Override
                            public void onRenderFail(View view, String s, int i) {
                            }

                            @Override
                            public void onRenderSuccess(View view, float v, float v1) {
                                View adView = ad.getExpressView();
                                if (adView != null && adView.getParent() == null) {
                                    frameLayout.removeAllViews();
                                    frameLayout.addView(adView);
                                }
                                //渲染成功后，加载下一个
//                                if (model.isCacheNext()) {
//                                    loadNativeAd(activity, frameLayout, model, ttNativeAdLoadCallback, false);
//                                }
                            }

                            @Override
                            public void onAdClick() {
                                if (mOnAdClickEvent != null) {
                                    mOnAdClickEvent.onAdClick();
                                }
                            }

                            @Override
                            public void onAdShow() {
                            }
                        });

                        ad.render();
                    }
                }
            }

            @Override
            public void onAdLoadedFial(AdError adError) {
                if (ttNativeAdLoadCallback != null) {
                    ttNativeAdLoadCallback.onAdLoadedFial(adError);
                }
            }
        });
    }


}
