package com.pizarro.gromoreloadlib;

import com.bytedance.msdk.api.BaiduExtraOptions;
import com.bytedance.msdk.api.GDTExtraOption;
import com.bytedance.msdk.api.TTVideoOption;

public class VideoOptionUtil {

    public static TTVideoOption getTTVideoOption() {
        //广点通（GDT）单独使用配置
        GDTExtraOption gdtExtraOption = new GDTExtraOption.Builder()
                .setGDTAutoPlayMuted(true)//自动播放是否静音，默认true
                .setGDTDetailPageMuted(false)//详情页是否静音，默认值为false，即有声播放；
                .setGDTEnableDetailPage(true)//是否能跳转详情页，默认true
                .setGDTEnableUserControl(false)//是否能控制是否暂停与播放，默认false
//                .setGDTMaxVideoDuration(0)//视频最大长度，单位:秒 此设置会影响广告填充，请谨慎设置
//                .setGDTMinVideoDuration(0)//视频最小长度，单位:秒 此设置会影响广告填充，请谨慎设置
                // VideoOption.AutoPlayPolicy.WIFI表示只在WiFi下自动播放；
                // VideoOption.AutoPlayPolicy.ALWAYS表示始终自动播放，不区分当前网络；
                // VideoOption.AutoPlayPolicy.NEVER表示始终都不自动播放，不区分当前网络，但在WiFi时会预下载视频资源；
                // 默认为始终自动播放；模板渲染视频、插屏2.0视频、自渲染2.0视频都可使用
                .setAutoPlayPolicy(GDTExtraOption.AutoPlayPolicy.WIFI)//自动播放策略--
                .setDownAPPConfirmPolicy(GDTExtraOption.DownAPPConfirmPolicy.TYPE_DEFAULT)//指定点击 APP 广告后是否展示二次确认，可选项包括 Default（wifi 不展示，非wifi 展示），NoConfirm（所有情况不展示）
                .setBrowserType(GDTExtraOption.BrowserType.TYPE_DEFAULT)//指定普链广告点击后用于展示落地页的浏览器类型，可选项包括：InnerBrowser（APP 内置浏览器），Sys（系统浏览器），Default（默认），SDK 按照默认逻辑选择
                .build();

        //针对百度SDK信息流设置
        BaiduExtraOptions baiduExtraOptions = new BaiduExtraOptions.Builder()
                .setGDTExtraOption(BaiduExtraOptions.DOWNLOAD_APP_CONFIRM_ALWAYS)
                .setCacheVideoOnlyWifi(true).build();

        //视频声音控制
        TTVideoOption videoOption = new TTVideoOption.Builder()
                .setMuted(false) //所有类型生效，GDT除外
                .setAdmobAppVolume(1f)//admob 声音配置，与setMuted配合使用
                .setGDTExtraOption(gdtExtraOption)//GDT 单独
                .setBaiduExtraOption(baiduExtraOptions)//百度SDK配置
                .build();

        return videoOption;
    }

    public static TTVideoOption getTTVideoOption2() {
        //广点通（GDT）单独使用配置
        GDTExtraOption gdtExtraOption = new GDTExtraOption.Builder()
                .setGDTAutoPlayMuted(true)//自动播放是否静音，默认true
                .setGDTDetailPageMuted(false)//详情页是否静音，默认值为false，即有声播放；
                .setGDTEnableDetailPage(true)//是否能跳转详情页，默认true
                .setGDTEnableUserControl(false)//是否能控制是否暂停与播放，默认false
                .setGDTMaxVideoDuration(0)//视频最大长度，单位:秒 此设置会影响广告填充，请谨慎设置
                .setGDTMinVideoDuration(0)//视频最小长度，单位:秒 此设置会影响广告填充，请谨慎设置
                .setFeedExpressType(GDTExtraOption.FeedExpressType.FEED_EXPRESS_TYPE_2)//确定使用GDT FEED 模板渲染2.0功能；需要向广点通商务申请2.0权限
                // VideoOption.AutoPlayPolicy.WIFI表示只在WiFi下自动播放；
                // VideoOption.AutoPlayPolicy.ALWAYS表示始终自动播放，不区分当前网络；
                // VideoOption.AutoPlayPolicy.NEVER表示始终都不自动播放，不区分当前网络，但在WiFi时会预下载视频资源；
                // 默认为始终自动播放；模板渲染视频、插屏2.0视频、自渲染2.0视频都可使用
                .setAutoPlayPolicy(GDTExtraOption.AutoPlayPolicy.WIFI)//自动播放策略--
                .setDownAPPConfirmPolicy(GDTExtraOption.DownAPPConfirmPolicy.TYPE_DEFAULT)//指定点击 APP 广告后是否展示二次确认，可选项包括 Default（wifi 不展示，非wifi 展示），NoConfirm（所有情况不展示）
                .setBrowserType(GDTExtraOption.BrowserType.TYPE_DEFAULT).build();//指定普链广告点击后用于展示落地页的浏览器类型，可选项包括：InnerBrowser（APP 内置浏览器），Sys（系统浏览器），Default（默认），SDK 按照默认逻辑选择


        //针对百度SDK信息流设置
        BaiduExtraOptions baiduExtraOptions = new BaiduExtraOptions.Builder()
                .setGDTExtraOption(BaiduExtraOptions.DOWNLOAD_APP_CONFIRM_ALWAYS)
                .setCacheVideoOnlyWifi(true).build();

        //视频声音控制
        TTVideoOption videoOption = new TTVideoOption.Builder()
                .setMuted(false) //所有类型生效，GDT除外
                .setAdmobAppVolume(1f)//admob 声音配置，与setMuted配合使用
                .setGDTExtraOption(gdtExtraOption)//GDT 单独
                .setBaiduExtraOption(baiduExtraOptions)//百度SDK配置
                .build();

        return videoOption;
    }
}
