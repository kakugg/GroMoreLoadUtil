package com.pizarro.gromoreloadlib.listener;

import com.bytedance.msdk.api.AdError;

/**
 * Created by Irving
 */
public interface OnAdLoadErrorEvent {

    void onAdLoadError(String adType, AdError adError);

}
