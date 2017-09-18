package com.afid.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.smartdevice.aidl.IZKCService;

/**
 * 功能：
 *
 * @author yu
 * @version 1.0
 * @date 2017/9/6
 */
public class ZKCManager {

    private static Context mContext;

    private  ZKCManager(){

    }

    private static class SingletonHolder{
        public static ZKCManager INSTANCE = new ZKCManager();
    }

    public static  ZKCManager getInstance(Context context){
        mContext = context;
        return SingletonHolder.INSTANCE;
    }

    public PrinterManager getPrintManager(){
        return PrinterManager.getInstance(mIzkcService);
    }

    public ScanManager getScanManager(){
        return ScanManager.getInstance();
    }



    public void bindService() {
        //com.zkc.aidl.all为远程服务的名称，不可更改
        //com.smartdevice.aidl为远程服务声明所在的包名，不可更改，
        // 对应的项目所导入的AIDL文件也应该在该包名下
        Intent intent = new Intent("com.zkc.aidl.all");
        intent.setPackage("com.smartdevice.aidl");
        mContext.bindService(intent, mServiceConn, Context.BIND_AUTO_CREATE);
    }

    public void unbindService() {
        mContext.unbindService(mServiceConn);
    }

    public static IZKCService mIzkcService;
    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("client", "onServiceDisconnected");
            mIzkcService = null;
            Toast.makeText(mContext, "服务连接失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("client", "onServiceConnected");
            mIzkcService = IZKCService.Stub.asInterface(service);
            Toast.makeText(mContext, "服务连接成功", Toast.LENGTH_SHORT).show();
        }
    };


}
