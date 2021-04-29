package com.pizarro.gromoreloadlib;

/**
 * Created by Irving
 */
public class ErrorUploadUtil {
    private static final String TAG = ErrorUploadUtil.class.getSimpleName();

    private static ErrorUploadUtil sErrorUploadUtil;

    private ErrorUploadUtil() {
    }

    public static ErrorUploadUtil getInstance() {
        if (sErrorUploadUtil == null) {
            sErrorUploadUtil = new ErrorUploadUtil();
        }
        return sErrorUploadUtil;
    }

    private String androidId;
    private String token;

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 上传广告sdk错误日志
     */
    public void upLoadSdkError(String message, int code, int thirdSdkErrorCode, String thirdSdkErrorMessage) {

    }

}
