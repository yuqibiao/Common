package com.afid.utils;

import android.app.Activity;
import android.content.Intent;

import com.smartdevice.aidltestdemo.CaptureActivity;

/**
 * 功能：
 *
 * @author yu
 * @version 1.0
 * @date 2017/9/7
 */

public class ScanManager {

    public static int REQUEST_CODE_SCAN=1001;

    private ScanManager(){

    }

    public static class SingletonHolder{
        static ScanManager INSTANCE = new ScanManager();
    }

    public static  ScanManager getInstance(){
        return SingletonHolder.INSTANCE;
    }

    /**
     * 跳转到扫码界面
     * @param activity
     */
    public void toScanAct(Activity activity){
        Intent intent = new Intent(activity, CaptureActivity.class);
        activity.startActivityForResult(intent , REQUEST_CODE_SCAN);
    }

}
