package com.afrid.swingu.utils;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.Random;

import static android.content.ContentValues.TAG;
import static com.afrid.swingu.utils.Constant.MESSAGE_TAG;

/**
 * 功能：手持机相关操作
 *
 * @author yu
 * @version 1.0
 * @date 2017/6/28
 */

public class SwingUManager {

    private Context mContext;
    private final SwingAPI mSwing;
    private boolean isStart = false;

    private static class InstanceHolder {
        public static SwingUManager INSTANCE = new SwingUManager();
    }

    private SwingUManager() {
        mSwing = new SwingAPI(mContext, mHandler);

    }

    public static SwingUManager getInstance(Context context) {
        return InstanceHolder.INSTANCE;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_TAG:
                    byte[] readBuf = (byte[]) msg.obj;
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    if (mOnReadResultListener!=null){
                        mOnReadResultListener.onRead(readMessage);
                    }
                    break;
            }
        }
    };

    /**
     * 销毁时调用
     */
    public void destoryReader() {
        mSwing.swing_setPower(0);
        if (mSwing != null) mSwing.stop();
    }

    /**
     * 清空Reader
     */
    public void resetReader() {
        mSwing.swing_clear_inventory();
    }

    /**
     * 结束读取
     */
    public void stopReader() {
        mSwing.swing_readStop();
        isStart = false;
    }

    /**
     * 开始读取
     */
    public void startReader() {
        mSwing.swing_set_inventory_mode(SwingAPI.InventoryMode.INVENTORY_NORMAL);
        mSwing.swing_readStart();
        isStart = true;
    }

    public boolean isConnected(){
        return mSwing.isConnected();
    }

    public boolean isStart(){
        return isStart;
    }

    /**
     * 连接设备
     */
    public void connectDevice(BluetoothDevice device) {
        if (mSwing.isConnected() == true) {
            mSwing.stop();
        }
        mSwing.connect(device);
    }


    OnReadResultListener mOnReadResultListener;

    public  void setOnReadResultListener(OnReadResultListener onReadResultListener){
        this.mOnReadResultListener = onReadResultListener;
    }

    public  interface  OnReadResultListener{
       void onRead(String tagId);
    }



}
