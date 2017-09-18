package com.smartdevice.aidltestdemo.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Camera;

public class SystemUtil {
	
	public static int getScreenOrientent(Context mContext){
	    Configuration newConfig = mContext.getResources().getConfiguration();
	    int direct = 0;
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){  
            //横屏 
        	direct = 0;
        }else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){  
            //竖屏 
        	direct = 1;
        }else if(newConfig.hardKeyboardHidden == Configuration.KEYBOARDHIDDEN_NO){  
            //键盘没关闭。屏幕方向为横屏 
        	direct = 2;
        }else if(newConfig.hardKeyboardHidden == Configuration.KEYBOARDHIDDEN_YES){  
            //键盘关闭。屏幕方向为竖屏
        	direct = 3;
        } 
        return direct;
	}

	/**
	 * 通过包名跳转
	 * @param context
	 * @param activityName
	 */
	public static void startActivityForName(Context context, String activityName) {
		try {
			Class clazz = Class.forName(activityName);
			Intent intent = new Intent(context,clazz);
			context.startActivity(intent);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean isCameraCanUse() {
		boolean canUse = true;
		Camera mCamera = null;
		try {
			// TODO camera驱动挂掉,处理??
			mCamera = Camera.open();
		} catch (Exception e) {
			canUse = false;
		}
		if (canUse) {
			mCamera.release();
			mCamera = null;
		}
		return canUse;
	}

}
