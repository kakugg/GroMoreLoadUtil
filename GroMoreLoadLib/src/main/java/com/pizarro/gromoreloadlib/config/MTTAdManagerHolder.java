package com.pizarro.gromoreloadlib.config;

import android.content.Context;

import com.bytedance.msdk.api.TTAdConfig;
import com.bytedance.msdk.api.TTAdConstant;
import com.bytedance.msdk.api.TTMediationAdSdk;
import com.pizarro.gromoreloadlib.tools.AppUtils;

/**
 * 可以用一个单例来保存TTAdManager实例，在需要初始化sdk的时候调用
 */
public class MTTAdManagerHolder {

    private static boolean sInit;

    /**
     * 不写用户id，默认使用androidId
     *
     * @param context
     * @param appId
     * @param debug
     */
    public static void init(Context context, String appId, boolean debug) {
        String deviceId = AppUtils.getAndroidId(context);
        doInit(context, appId, deviceId, debug);
    }

    public static void init(Context context, String appId, String deviceId, boolean debug) {
        doInit(context, appId, deviceId, debug);
    }

    private static void doInit(Context context, String appId, String deviceId, boolean debug) {
        if (!sInit) {
            TTMediationAdSdk.initialize(context, buildConfig(context, appId, deviceId, debug));
            sInit = true;
        }
    }

    private static TTAdConfig buildConfig(Context context, String appId, String deviceId, boolean debug) {
        return new TTAdConfig.Builder()
                .appId(appId)
                .appName(AppUtils.getAppName(context))
                .openAdnTest(false)//开启第三方ADN测试时需要设置为true，会每次重新拉去最新配置，release 包情况下必须关闭.默认false
                .isPanglePaid(false)//是否为费用户
                .setPublisherDid(deviceId) //用户自定义device_id
                .openDebugLog(debug) //测试阶段打开，可以通过日志排查问题，上线时去除该调用
                .usePangleTextureView(true) //使用TextureView控件播放视频,默认为SurfaceView,当有SurfaceView冲突的场景，可以使用TextureView
                .setPangleTitleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
                .allowPangleShowNotify(true) //是否允许sdk展示通知栏提示
                .allowPangleShowPageWhenScreenLock(true) //是否在锁屏场景支持展示广告落地页
                .setPangleDirectDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI, TTAdConstant.NETWORK_STATE_4G) //允许直接下载的网络状态集合
                .needPangleClearTaskReset()//特殊机型过滤，部分机型出现包解析失败问题（大部分是三星）。参数取android.os.Build.MODEL
                .build();
    }
}
