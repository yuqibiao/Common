package com.yyyu.baselibrary.utils;

import android.content.Context;

/**
 * 功能：资源文件获取相关工具类
 *
 * @author yu
 * @version 1.0
 * @date 2017/8/7
 */

public class ResourceUtils {


    private static Context mContext;

    private ResourceUtils() {

    }

    private static class SingletonHolder{
        public static ResourceUtils INSTANCE = new ResourceUtils();
    }

    public static ResourceUtils getInstance(Context context){
        mContext = context;
        return SingletonHolder.INSTANCE;
    }

    public String getStr(int strId){
        return mContext.getResources().getString(strId);
    }

}
